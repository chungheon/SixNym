package com.looting.sixnym;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewManager {
    private TextView handCards;
    private ExpandableListView rows;
    private ExpandableListAdapter listAdapter;
    private TextView points;
    private Button playBtn;
    private Button selectBtn;
    private RecyclerView rv;
    private RecyclerViewAdapter adapter;
    private int rowSelected;
    private boolean rowSelection;
    private Context ct;
    private String msg;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;


    ViewManager(TextView hands, final ExpandableListView rows, TextView points, Button playBtn, Button selectBtn,
                RecyclerView rv, Context c){
        this.handCards = hands;
        this.rows = rows;
        this.points = points;
        this.playBtn = playBtn;
        this.rv = rv;
        ct = c;
        this.selectBtn = selectBtn;
        this.selectBtn.setVisibility(View.GONE);
        rowSelection = false;
        rowSelected = -1;

        rows.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if(rowSelection) {
                    rowSelected = groupPosition;
                    handCards.setText('\n' + msg + "Row selected: ROW " + (rowSelected+1));
                }
            }
        });
    }


    public int getCardPlayed(){
        return adapter.getCardPlayed();
    }


    public void displayRows(ArrayList<CardRow> cardRows) {
        prepareData(cardRows);

        listAdapter = new ExpandableListAdapter(ct, listDataHeader, listDataChild);

        rows.setAdapter(listAdapter);
    }

    private void prepareData(ArrayList<CardRow> cardRows){
        this.listDataHeader = new ArrayList<>();
        this.listDataChild = new HashMap<String, List<String>>();
        int size = 0;
        int count = 0;
        for(CardRow cr: cardRows){
            String data = cr.displayRow();
            String[] info = data.split("\n");
            String topCard = "FV: " + cr.getTopCard() + '\n' + info[info.length-1];
            this.listDataHeader.add(topCard);
            List<String> cards = new ArrayList<>();
            for(int i = info.length-3; i >= 0; i--){
                cards.add(info[i]);
            }
            listDataChild.put(listDataHeader.get(count), cards);
            count++;
        }
    }

    public void displayPlayerHands(String playerName, ArrayList<String> playersHand, String points){
        this.handCards.setText(playerName + "'s turn\n");
        ArrayList<String> cardNo = new ArrayList<>();
        for(int i = 1; i <= playersHand.size(); i++){
            cardNo.add("Card " + Integer.toString(i));
        }
        this.adapter =  new RecyclerViewAdapter(ct, cardNo, playersHand, handCards);
        this.rv.setAdapter(adapter);
        this.points.setText(points);
    }

    public boolean checkCard(int size){
        return true;
    }

    public void selectRowDialog(String playerName, String displayMessage) {
        msg = displayMessage + "\nPlayer \'" + playerName + "\'\nPlease select a row!\n";
        rv.setVisibility(View.GONE);
        handCards.setText(msg);
        playBtn.setVisibility(View.GONE);
        selectBtn.setVisibility(View.VISIBLE);
        this.rowSelection = true;
    }

    public void endRowDialog() {
        playBtn.setVisibility(View.VISIBLE);
        selectBtn.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        this.rowSelection = false;
    }

    public void endRound(){
        handCards.setText("Now to disseminate the cards to the rows");
    }

    public int getRowSelected(){
        int row = this.rowSelected;
        this.rowSelected = -1;
        return row;
    }

    public void printToast(String message){
        Toast.makeText(ct, message, Toast.LENGTH_SHORT).show();
    }

    public void endGame(ArrayList<String> playerScores){
        this.rv.setVisibility(View.GONE);
        this.selectBtn.setVisibility(View.GONE);
        this.rows.setVisibility(View.GONE);
        this.points.setVisibility(View.GONE);
        String finalMessage = "End of game! Calculating SCORE...\n\n";
        for(String s: playerScores){
            finalMessage += s + "\n\n";
        }
        playBtn.setText("Play new game");
        this.handCards.setText(finalMessage);
    }

    public void startGame(){
        this.rv.setVisibility(View.VISIBLE);
        playBtn.setText("Play Card");
        this.rows.setVisibility(View.VISIBLE);
        this.points.setVisibility(View.VISIBLE);
    }

}
