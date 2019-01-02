package com.looting.sixnym;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.renderscript.ScriptGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MultiplayerConnect extends Activity {

    Button btnOnOff, btnDiscover, btnStartGame;
    ListView listView;
    TextView connectionStatus;
    WifiManager wifiManager;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiever;
    IntentFilter mIntentFilter;
    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;
    boolean wait = true;
    final int MESSAGE_READ = 1;
    boolean clientUser = false;
    SendReceive sendReceive;
    ClientClass client;
    ServerClass host;
    TextView hands;
    Button playBtn;
    ExpandableListView rows;
    TextView points;
    Button selectBtn;
    RecyclerView rv;
    ViewManager vm;
    TableCards tb;
    Deck deck;
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<SendReceive> sendReceives = new ArrayList<>();
    String playerNum;
    HostGameController hostGame;
    boolean accept = true;
    boolean search = true;
    ServerSocket serverSocket;
    int[] cardPlay;
    boolean finish = false;

    Handler handler = new Handler(new Handler.Callback(){
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_READ:
                    byte[] readBuff = (byte[]) msg.obj;
                    String msgR = new String(readBuff, 0, msg.arg1);
                    Log.d("MessageReceived", msgR);
                    if(clientUser){
                        clientHandle(msgR);
                    }else{
                        hostHandle(msgR);
                    }
                    break;
            }
            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_multiplayer_connect);
        initialWork();
        exqListener();
    }

    private void exqListener(){
        btnOnOff.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(wifiManager.isWifiEnabled()){
                    wifiManager.setWifiEnabled(false);
                    btnOnOff.setText("ON");
                }else{
                    wifiManager.setWifiEnabled(true);
                    btnOnOff.setText("OFF");
                }
            }
        });

        btnDiscover.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        connectionStatus.setText("Discovery Started");
                    }

                    @Override
                    public void onFailure(int reason) {
                        connectionStatus.setText("Discovery Starting Failed");
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                final WifiP2pDevice device = deviceArray[i];
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;

                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Connected to " + device.deviceName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnStartGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }



    private void initialWork(){
        btnOnOff = (Button) findViewById(R.id.onOff);
        btnDiscover = (Button) findViewById(R.id.discover);
        btnStartGame = (Button) findViewById(R.id.startGame);
        btnStartGame.setVisibility(View.GONE);
        listView = (ListView) findViewById(R.id.peerListView);
        connectionStatus = (TextView) findViewById(R.id.connectionStatus);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

        mReceiever = new WifiDirectBroadcastReceiver(mManager, mChannel, this);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if(!peerList.getDeviceList().equals(peers)){
                peers.clear();
                peers.addAll(peerList.getDeviceList());

                deviceNameArray = new String[peerList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];
                int index = 0;
                for(WifiP2pDevice device: peerList.getDeviceList()){
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] = device;
                    index++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameArray);
                listView.setAdapter(adapter);
            }

            if(peers.size() == 0){
                Toast.makeText(getApplicationContext(), "No Device Found", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };

    WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            final InetAddress groupOwnerAddress = info.groupOwnerAddress;

            if(info.groupFormed && info.isGroupOwner){
                btnStartGame.setVisibility(View.VISIBLE);
                try{
                    serverSocket = new ServerSocket(8888);
                }catch (IOException e){ }
                connectionStatus.setText("Host");
                host = new ServerClass();
                host.run();
            }else{
                connectionStatus.setText("Client");
                clientUser = true;
                client = new ClientClass(groupOwnerAddress);
                client.run();
            }
        }
    };

    public void clientHandle(String msg){
        String[] info = msg.split(" ");
        if(clientUser){
            if(info[0].equals("StartGame")){
                playerNum = info[1];
                Player p = new Player(playerNum);
                players.add(p);
                startClient();
            }else if(info[0].equals("HandCards")){
                String cards;
                for(int i = 1; i < info.length - 1; i++){
                    Card c = new Card(Integer.parseInt(info[i]));
                    players.get(0).getCard(c);
                }
                vm.displayPlayerHands(playerNum, players.get(0).getPlayerCards(),info[info.length-1]);
                sendReceive.write(("HandDone " + playerNum).getBytes());
            }else if(info[0].equals("CardRow")){
                ArrayList<CardRow> cardRows = new ArrayList<>();
                for(int i = 0; i < 4; i++){
                    String[] rowInfo = info[i+1].split("a");
                     CardRow cardRow = new CardRow();
                    for(int j = 0; j < rowInfo.length; j++){
                        try{
                            int fv = Integer.parseInt(rowInfo[j]);
                            Card c = new Card(fv);
                            cardRow.addToRow(c);
                        }catch(NumberFormatException e){ }
                    }
                    cardRows.add(cardRow);
                }
                vm.displayRows(cardRows);
            }else if(info[0].equals("Placed")){
                hands.setText(info[1] + " has placed down card\n" + hands.getText().toString());
            }
        }
    }

    public void hostHandle(String msg){
        String[] info = msg.split(" ");
        if(info[0].equals("HandDone")){
            ArrayList<CardRow> cardRows = hostGame.getCardRows();
            String rowCards = "";
            for(CardRow cr: cardRows){
                rowCards += cr.getRowCards() + " ";
            }
            sendReceives.get(Integer.parseInt(info[1])-1).write(("CardRow " + rowCards).getBytes());
        }else if(info[0].equals("GetCards")){
            sendReceive.write(("HandCards " + hostGame.getCards(Integer.parseInt(info[1])) + "0").getBytes());
        }else if(info[0].equals("PlayCard")){
            broadcastMsg("Placed " + info[1]);
            hands.setText(info[1] + " has placed down card\n" + hands.getText().toString());
            cardPlay[Integer.parseInt(info[1])] = Integer.parseInt(info[2]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiever, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiever);
    }

    public void startClient(){
        setContentView(R.layout.activity_single_player);
        this.hands = (TextView) findViewById(R.id.handCards);
        this.rows = findViewById(R.id.rows);
        this.points = (TextView) findViewById(R.id.points);
        this.playBtn = (Button) findViewById(R.id.playCard);
        this.selectBtn = (Button) findViewById(R.id.selectButton);
        gameListeners();
        this.rv = findViewById(R.id.cardDisplay);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.rv.setLayoutManager(lm);
        this.tb = new TableCards();
        hands.setText("Player " + playerNum);
        vm = new ViewManager(hands, rows, points,playBtn, selectBtn, rv,this);
    }

    public void gameListeners(){
        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendReceive.write(("PlayCard " + playerNum + " " + vm.getCardPlayed()).getBytes());
                playBtn.setVisibility(View.GONE);
            }
        });
    }

    public void startGame(){
        setContentView(R.layout.activity_single_player);
        this.hands = (TextView) findViewById(R.id.handCards);
        this.rows = findViewById(R.id.rows);
        this.points = (TextView) findViewById(R.id.points);
        this.playBtn = (Button) findViewById(R.id.playCard);
        this.selectBtn = (Button) findViewById(R.id.selectButton);
        this.rv = findViewById(R.id.cardDisplay);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.rv.setLayoutManager(lm);
        this.tb = new TableCards();
        cardPlay = new int[players.size()];
        hostListener();
        playerNum = "0";
        players.add(new Player(playerNum));
        int count = 1;
        Log.d("CONNECTIONS", sendReceives.size() + " ", null);
        for(SendReceive sr: sendReceives){
            Player p = new Player(""+count);
            players.add(p);
            count++;
        }
        vm = new ViewManager(hands, rows, points, playBtn, selectBtn, rv, this);
        hostGame = new HostGameController(tb,vm, players);
        hostGame.dealCards();
        vm.displayRows(hostGame.getCardRows());
        vm.displayPlayerHands(playerNum,hostGame.getPlayerCards(0),"0");
        count = 1;
        for(SendReceive sr: sendReceives){
            sr.write(("HandCards " + hostGame.getCards(count) + "0").getBytes());
            count++;
        }
    }

    public void hostListener(){
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!finish){
                    broadcastMsg("Placed " + playerNum);
                    hands.setText("0 has placed down card\n" + hands.getText().toString());
                    cardPlay[0] = vm.getCardPlayed();
                    finish = true;
                }else{
                    //Play cards
                }

            }
        });
    }

    public void broadcastMsg(String msg){
        for(SendReceive sr: sendReceives){
            sr.write(msg.getBytes());
        }
    }

    public class ServerClass extends Thread{
        Socket socket;
        @Override
        public void run() {
            try {
                    socket = serverSocket.accept();
                    sendReceive = new SendReceive(socket);
                    sendReceive.start();
                    sendReceives.add(sendReceive);
                    Log.d("CONNECTED", "Player Connected", null);
                    sendReceive.write(("StartGame " + sendReceives.size()).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("SocketConnection", "Connection failed");
            }

        }
    }

    public class ClientClass extends Thread{
        Socket socket;
        String hostAdd;

        public ClientClass(InetAddress hostAddress){
            hostAdd = hostAddress.getHostAddress();
            socket = new Socket();
        }

        @Override
        public void run() {
            try{
                socket.connect(new InetSocketAddress(hostAdd, 8888), 500);
                sendReceive = new SendReceive(socket);
                sendReceive.start();
            }catch (IOException e){
                e.printStackTrace();
                Log.e("SocketConnection", "Connection failed");
            }
        }
    }

    public class SendReceive extends Thread {
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public SendReceive(Socket skt) {
            socket = skt;
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (socket != null) {
                try {
                    bytes = inputStream.read(buffer);
                    if (bytes > 0) {
                        handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
