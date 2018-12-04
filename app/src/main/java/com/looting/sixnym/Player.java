//Player, a class to simulate an actual
//player playing the game. Each player is given 10 cards
//At each round players have to select a card to play.
//Players accumulate points as the game goes on.
//File Name: Player.java
//Author: Loo Ting Xian
package com.looting.sixnym;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList<Card> hand;
    private ArrayList<Card> points;

    public Player(String name){
        this.name = name;
        hand = new ArrayList<>();
        points = new ArrayList<>();
    }

    public String getName(){ return name;}

    public Card playCard(int index) { return hand.remove(index); }

    public void getCard(Card c) { hand.add(c); }

    public void addPoints(Card cardG){ points.add(cardG); }

    public Card displayCard(int index) { return hand.get(index); }

    public ArrayList<String> getPlayerCards(){
        String playerCards = "";
        ArrayList<String> cards = new ArrayList<>();
        playerCards += "Please select a card to play: " + '\n';
        for(Card c: hand)
        {
            playerCards = "";
            String face =  Integer.toString(c.getFaceValue());
            String point = Integer.toString((c.getPointValue()));
            playerCards += "FV: " + face;
            playerCards += " PV: " + point;
            cards.add(playerCards);

        }

        return cards;
    }

    public int handSize(){ return hand.size(); }

}
