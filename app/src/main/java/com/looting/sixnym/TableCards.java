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

    private Deck deck;
    private ArrayList<CardRow> cardRows;

    public TableCards() {
        deck = new Deck();
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

    public void dealCards(ArrayList<Player> pArray){
        int count = 0;
        for(int i = 0; i < (pArray.size() * 10); i++) {
            if (i % pArray.size() == 0) {
                pArray.get(0).getCard(deck.dealCard());
            } else if (i % pArray.size() == 1) {
                pArray.get(1).getCard(deck.dealCard());
            } else if (i % pArray.size() == 2) {
                pArray.get(2).getCard(deck.dealCard());
            } else if (i % pArray.size() == 3) {
                pArray.get(3).getCard(deck.dealCard());
            } else if (i % pArray.size() == 4) {
                pArray.get(4).getCard(deck.dealCard());
            } else if (i % pArray.size() == 5) {
                pArray.get(5).getCard(deck.dealCard());
            } else if (i % pArray.size() == 6) {
                pArray.get(6).getCard(deck.dealCard());
            } else if (i % pArray.size() == 7) {
                pArray.get(7).getCard(deck.dealCard());
            } else if (i % pArray.size() == 8) {
                pArray.get(8).getCard(deck.dealCard());
            } else if (i % pArray.size() == 9) {
                pArray.get(9).getCard(deck.dealCard());
            }
        }
        cardRows.get(0).addToRow(deck.dealCard());
        cardRows.get(1).addToRow(deck.dealCard());
        cardRows.get(2).addToRow(deck.dealCard());
        cardRows.get(3).addToRow(deck.dealCard());
    }

    public ArrayList<CardRow> getCardRows(){
        return cardRows;
    }

    public void fillDeck(int numPlayers){
        for(int i = 1; i <= (numPlayers*10) + 4 ; i++){
            Card card = new Card();
            if(i == 55) {
                card.setCard(55, 7);
            }else if(i%11 == 0){
                card.setCard(i, 5);
            }else if(i%10 == 0){
                card.setCard(i, 3);
            }else if(i%5 ==0){
                card.setCard(i, 2);
            }else {
                card.setCard(i,1);
            }
            deck.addCard(card);
        }
    }

    public void shuffleDeck(){
        Log.d("DECKSIZE", Integer.toString(deck.getSize()));
        deck.shuffleDeck();
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

    public void returnCard(Card c){
        deck.addCard(c);
    }

    public void getFromAllRows(){
        for(CardRow cr: cardRows){
            while(!cr.isEmpty()){
                deck.addCard(cr.removeFromRow());
            }
        }
    }
}
