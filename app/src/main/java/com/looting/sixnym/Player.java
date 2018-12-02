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
    private int points;

    public Player(String name){
        this.name = name;
        hand = new ArrayList<>();
        points = 0;
    }

    public Card playCard(int index) { return hand.remove(index); }

    public void getCard(Card c) { hand.add(c); }

    public void addPoints(int pointsGained){ points += pointsGained; }

    public Card displayCard(int index) { return hand.get(index); }

    public int handSize(){ return hand.size(); }

}
