package com.looting.sixnym;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewManager {
    private TextView handCards;
    private TextView row1;
    private TextView row2;
    private TextView row3;
    private TextView row4;
    private TextView points;
    private Button playBtn;
    private Button selectBtn;
    private RecyclerView rv;
    private RecyclerViewAdapter adapter;
    private int rowSelected;
    private boolean rowSelection;
    private Context ct;
    private String msg;


    ViewManager(TextView hands, TextView one, TextView two, TextView three, TextView four, TextView points, Button playBtn, Button selectBtn,
    RecyclerView rv, Context c){
        this.handCards = hands;
        this.row1 = one;
        this.row2 = two;
        this.row3 = three;
        this.row4 = four;
        this.points = points;
        this.playBtn = playBtn;
        this.rv = rv;
        ct = c;
        this.selectBtn = selectBtn;
        this.selectBtn.setVisibility(View.GONE);
        rowSelection = false;
        rowSelected = -1;
        row1.setOnClickListener(new RowListener(0));
        row2.setOnClickListener(new RowListener(1));
        row3.setOnClickListener(new RowListener(2));
        row4.setOnClickListener(new RowListener(3));
    }

    public class RowListener implements View.OnClickListener {
        private int id;
        RowListener(int id){
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            if(rowSelection){
                rowSelected = id;
                handCards.setText('\n' + msg + "Row Selected: ROW " + Integer.toString(id+1) + "\n");
            }
        }
    }


    public int getCardPlayed(){
        return adapter.getCardPlayed();
    }


    public void displayRows(ArrayList<CardRow> cardRows){
        row1.setText(cardRows.get(0).displayRow());
        row2.setText(cardRows.get(1).displayRow());
        row3.setText(cardRows.get(2).displayRow());
        row4.setText(cardRows.get(3).displayRow());
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
        this.row1.setVisibility(View.GONE);
        this.row2.setVisibility(View.GONE);
        this.row3.setVisibility(View.GONE);
        this.row4.setVisibility(View.GONE);
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
        this.row1.setVisibility(View.VISIBLE);
        this.row2.setVisibility(View.VISIBLE);
        this.row3.setVisibility(View.VISIBLE);
        this.row4.setVisibility(View.VISIBLE);
        this.points.setVisibility(View.VISIBLE);
    }

}
