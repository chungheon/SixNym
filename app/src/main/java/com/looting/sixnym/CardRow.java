//CardRow, a class to simulate an actual
//the row of cards on the table.
//At the start of the game 4 cards are dealt
//to each row. If the row has 6 cards
//the cards are all forfeited to the player
//that played the 6th card.
//File Name: CardRow.java
//Author: Loo Ting Xian
package com.looting.sixnym;

import android.widget.TextView;

import java.util.ArrayList;

public class CardRow {

    private ArrayList<Card> cardArray;

    public CardRow(){
        cardArray = new ArrayList<>();
    }

    public void addToRow(Card c) {
        cardArray.add(c);
    }

    public Card removeFromRow() {
        return cardArray.remove(0);
    }

    public int getTopCard() {
        return cardArray.get(cardArray.size() - 1).getFaceValue();
    }

    public int getPoints(){
        int total = 0;
        for(Card c: cardArray){
            total += c.getPointValue();
        }
        return total;
    }

    public String displayRow(){
        String cards = "";
        for(Card c: cardArray){
            cards += "Face Value: " + Integer.toString(c.getFaceValue());
            cards += " Point Value:" + Integer.toString(c.getPointValue());
            cards += '\n';
        }
        return cards;
    }

    public int getSize() { return cardArray.size(); }

}
