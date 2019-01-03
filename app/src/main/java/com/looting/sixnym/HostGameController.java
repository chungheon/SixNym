package com.looting.sixnym;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class HostGameController {

    private TableCards tb;
    private Deck deck;
    private ViewManager vm;
    private ArrayList<Player> pArray;
    private ArrayList<GameController.CardPlayed> cardsPlayed;
    private int turn;


    public HostGameController(TableCards tb, ViewManager vm, ArrayList<Player> pArray){
        this.tb = tb;
        this.vm = vm;
        this.pArray = pArray;
        deck = new Deck();
        cardsPlayed = new ArrayList<GameController.CardPlayed>();
        deck.fillDeck(pArray.size());
        deck.shuffleDeck();
        Log.d("PlayerSize", pArray.size() + "", null);
    }

    public void dealCards(){
        for(int i = 0; i < 10; i++){
            for(Player p: pArray){
                p.getCard(deck.dealCard());
            }
        }
        tb.addToRow(deck.dealCard(), 0);
        tb.addToRow(deck.dealCard(), 1);
        tb.addToRow(deck.dealCard(), 2);
        tb.addToRow(deck.dealCard(), 3);
    }

    public ArrayList<CardRow> getCardRows(){
        return tb.getCardRows();
    }

    public ArrayList<String> getPlayerCards(int i){
        return pArray.get(i).getPlayerCards();
    }

    public String getCards(int player){
        return pArray.get(player).getCards();
    }

    public void showCards(int[] cardPlayed) {

    }

    private void placeCardOnRow(Card cardToBePlaced){
        int cntRow = closestRow(cardToBePlaced);

        if (cntRow != -1) {
            CardRow selectedRow = this.tb.getCardRow(cntRow);
            int cardRowSize = selectedRow.getSize();
            if (cardRowSize == 5) {
                for (int i = 0; i < cardRowSize;i++ ) {
                    pArray.get(cardsPlayed.get(0).playerIndex).addPoints(selectedRow.removeFromRow());
                }
                selectedRow.addToRow(cardToBePlaced);
            } else {
                this.tb.getCardRows().get(cntRow).addToRow(cardToBePlaced);
            }
        }
    }

    private int closestRow(Card cardToBePlaced){
        ArrayList<Integer> topCards = this.tb.getTopCardsOnAllRows();
        int cTBP = cardToBePlaced.getFaceValue();
        int closestLowerCard = 0;
        int cnt = 0;
        int cntRow = -1;
        for (int tC: topCards) {
            if (cTBP > tC && tC > closestLowerCard) {
                closestLowerCard = tC;
                cntRow = cnt;
            }
            cnt++;
        }

        return cntRow;
    }

    public String playCard(int[] cardsPlayed){
        ArrayList<CardPlayed> cpArray = new ArrayList<>();
        int count = 0;
        for(int i: cardsPlayed){
            Card c = pArray.get(count).playCard(i);
            CardPlayed cp = new CardPlayed(c, count);
            cpArray.add(cp);
            count++;
        }
            Collections.sort(cpArray);
            if (closestRow(cpArray.get(0).card) == -1) {
                String message = "SelectRow " + cpArray.get(0).playerIndex + " ";
                for (CardPlayed cardP : cpArray) {
                    message += pArray.get(cardP.playerIndex).getName() + " played " + cardP.card.displayCard() + " ";
                }
                return message;
            } else {
                String message = "PlayCards ";
                for (CardPlayed cardP : cpArray) {
                    message += pArray.get(cardP.playerIndex).getName() + " played " + cardP.card.displayCard() + " ";
                }
                return message;
            }
    }

    public class CardPlayed implements Comparable<CardPlayed> {
        public Card card;
        public int playerIndex;
        public CardPlayed(Card c, int p){
            card = c;
            playerIndex = p;
        }

        public int getCard(){return card.getFaceValue();}

        public int compareTo(CardPlayed otherCard) {
            return (this.getCard() - otherCard.getCard());
        }
    }
}
