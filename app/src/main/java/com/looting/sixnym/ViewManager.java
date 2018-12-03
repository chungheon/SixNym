package com.looting.sixnym;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewManager {
    TextView handCards;
    TextView row1;
    TextView row2;
    TextView row3;
    TextView row4;
    EditText cardPlayed;
    Button playBtn;
    Button selectBtn;
    RecyclerView rv;
    Context ct;


    ViewManager(TextView hands, TextView one, TextView two, TextView three, TextView four, EditText editText, Button playBtn, Button selectBtn4,
    RecyclerView rv, Context c){
        this.handCards = hands;
        this.row1 = one;
        this.row2 = two;
        this.row3 = three;
        this.row4 = four;
        this.cardPlayed = editText;
        this.playBtn = playBtn;
        this.rv = rv;
        ct = c;
        this.selectBtn = selectBtn;
        this.selectBtn.setVisibility(View.GONE);
    }


    public int getCardPlayed(){
        try{
            return Integer.parseInt(cardPlayed.getText().toString());
        }catch(NumberFormatException nfe){
            return -1;
        }

    }


    public void displayRows(ArrayList<CardRow> cardRows){
        row1.setText("Row 1: " + cardRows.get(0).displayRow());
        row2.setText("Row 2: " + cardRows.get(1).displayRow());
        row3.setText("Row 3: " + cardRows.get(2).displayRow());
        row4.setText("Row 4: " + cardRows.get(3).displayRow());
    }

    public void displayPlayerHands(String playerName, ArrayList<String> playersHand){
        handCards.setText(playerName + "'s turn");
        RecyclerViewAdapter adapter =  new RecyclerViewAdapter(ct, playersHand);
        rv.setAdapter(adapter);
    }

    public boolean checkCard(int size){
        int cardPlay = this.getCardPlayed();

        if(cardPlay > size || cardPlay < 1){
            return false;
        }else{
            return true;
        }

    }

    public void selectRowDialog(String displayMessage) {
        handCards.setText(displayMessage);
        playBtn.setVisibility(View.GONE);
        selectBtn.setVisibility(View.VISIBLE);
    }

    public void endRowDialog() {
        playBtn.setVisibility(View.VISIBLE);
        selectBtn.setVisibility(View.GONE);
    }

    public void nextTurn(){
        cardPlayed.setText("");
    }

    public void endRound(){
        handCards.setText("Now to disseminate the cards to the rows");
    }

}
