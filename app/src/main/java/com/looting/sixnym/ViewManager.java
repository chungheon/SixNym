package com.looting.sixnym;

import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewManager {
    TextView handCards;
    TextView row1;
    TextView row2;
    TextView row3;
    TextView row4;
    EditText cardPlayed;


    ViewManager(TextView hands, TextView one, TextView two, TextView three, TextView four, EditText editText){
        handCards = hands;
        row1 = one;
        row2 = two;
        row3 = three;
        row4 = four;
        cardPlayed = editText;
    }

    public void displayRows(ArrayList<CardRow> cardRows){
        row1.setText("Row 1: " + cardRows.get(0).displayRow());
        row2.setText("Row 2: " + cardRows.get(1).displayRow());
        row3.setText("Row 3: " + cardRows.get(2).displayRow());
        row4.setText("Row 4: " + cardRows.get(3).displayRow());
    }

    public void displayPlayerHands(String playerName, String playersHand){
        handCards.setText(playerName + playersHand);
    }

    public boolean checkCard(int size){
        int cardPlay = 0;
        try{
            cardPlay = Integer.parseInt(cardPlayed.getText().toString());
        }catch(NumberFormatException nfe){
            return false;
        }
        if(cardPlay > size || cardPlay < 1){
            return false;
        }else{
            return true;
        }

    }

    public void nextTurn(){
        cardPlayed.setText("");
    }
}
