package com.looting.sixnym;

import android.widget.TextView;

import java.util.ArrayList;

public class ViewManager {
    TextView handCards;
    TextView row1;
    TextView row2;
    TextView row3;
    TextView row4;

    ViewManager(TextView hands, TextView one, TextView two, TextView three, TextView four){
        handCards = hands;
        row1 = one;
        row2 = two;
        row3 = three;
        row4 = four;
    }

    public void displayRows(ArrayList<CardRow> cardRows){
        row1.setText(cardRows.get(0).displayRow());
        row2.setText(cardRows.get(1).displayRow());
        row3.setText(cardRows.get(2).displayRow());
        row4.setText(cardRows.get(3).displayRow());
    }

    public void displayPlayerHands(String playersHand){
        handCards.setText(playersHand);
    }
}
