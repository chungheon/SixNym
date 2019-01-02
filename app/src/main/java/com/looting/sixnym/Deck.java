//Deck, a class to simulate an actual
//deck of playing cards for the game.
//The number of players affects the number of cards
//required for the game, each player has 10 cards
//and each game require min. 2 players.
//Each game has 4 cards on the playing field at the start
//File Name: Deck.java
//Author: Loo Ting Xian
package com.looting.sixnym;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    //Array of cards to simulate the deck
    private ArrayList<Card> deckOfCards;

    //Default constructor
    public Deck(){
        deckOfCards = new ArrayList<>();
    }

    //Return a card to the deck, or add a created card to deck
    public void addCard(Card c){
        deckOfCards.add(c);
    }

    //Deal the 'top' of the deck
    public Card dealCard(){
        return deckOfCards.remove(0);
    }

    //Shuffle the cards in the deck
    public void shuffleDeck(){ Collections.shuffle(deckOfCards); }

    public int getSize(){ return deckOfCards.size();}

    public void fillDeck(int numPlayers){
        deckOfCards.clear();
        for(int i = 1; i <= (numPlayers*10) + 4 ; i++){
            Card card = new Card(i);
            deckOfCards.add(card);
        }
    }
}
