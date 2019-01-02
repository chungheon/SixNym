package com.looting.sixnym;

import android.util.Log;

import java.util.ArrayList;

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
}
