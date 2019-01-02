//TableCards, a class to simulate an actual
//the cards on the table.
//At the start of the game 4 cards are dealt to the
//table. And all cards played by players are shown
//on the table subsequently.
//File Name: TableCards.java
//Author: Loo Ting Xian
package com.looting.sixnym;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TableCards {

    private ArrayList<CardRow> cardRows;

    public TableCards() {
        cardRows = new ArrayList<CardRow>();
        for(int i = 0; i < 4; i++){
            CardRow newRow = new CardRow();
            cardRows.add(newRow);
        }
    }

    public boolean checkSmall(int cardPlayed){
        int smallestRow = getLowest();
        if(cardPlayed < cardRows.get(smallestRow).getTopCard()){
            return true;
        }
        return false;
    }

    public int playCard(Card cardPlayed){
        int pointsGained = 0;
        for(int i = 3; i >= 0; i--){
            if(cardRows.get(i).getTopCard() > cardPlayed.getFaceValue()){
                if(cardRows.get(i).getSize() == 5){
                    pointsGained = cardRows.get(i).getPoints();
                    cardRows.get(i).addToRow(cardPlayed);
                    return pointsGained;
                }else{
                    cardRows.get(i).addToRow(cardPlayed);
                    i = -1;
                }
            }
        }
        return 0;
    }

    public void setOrder(){
       Collections.sort(cardRows, new SortbyTopCard());
    }

    class SortbyTopCard implements Comparator<CardRow>
    {
        /*
        Used for sorting in ascending order of
        top card face value
        */
        public int compare(CardRow a, CardRow b)
        {
            return a.getTopCard() - b.getTopCard();
        }
    }

    public void addToRow(Card c, int row){
        this.cardRows.get(row).addToRow(c);
    }

    public ArrayList<CardRow> getCardRows(){
        return cardRows;
    }

    private int getHighest(){
        int highest = cardRows.get(0).getTopCard();
        int highestRow = 0;
        for(int i = 1; i < 4; i++){
            if(highest < cardRows.get(i).getTopCard()){
                highest = cardRows.get(i).getTopCard();
                highestRow = i;
            }
        }

        return highestRow;
    }

    private int getLowest(){
        int lowest = cardRows.get(0).getTopCard();
        int lowestRow = 0;

        for(int i = 1; i < 4; i++){
            if(lowest < cardRows.get(i).getTopCard()){
                lowest = cardRows.get(i).getTopCard();
                lowestRow = i;
            }
        }

        return lowestRow;
    }

    public ArrayList<Integer> getTopCardsOnAllRows(){
        ArrayList<Integer> topCards = new ArrayList<Integer>();
        for (CardRow cR : cardRows) {
            topCards.add(cR.getTopCard());
        }
        return topCards;
    }


    public CardRow getCardRow(int idx) {
        return cardRows.get(idx);
    }

}
