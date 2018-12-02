//GameController, a class to simulate an actual
//the game play on the table.
//At the start of the game 4 cards are dealt to the
//table, 10 cards dealth to each player.
//Each round players have to select 1 card to play,
//the card with the lowest face value is played first.
//The played card are added to its closest face value
//not exceeding, if played card is smaller than all
//row top value, player chooses a row to forfeit and
//gets all points value from the row chosen
//Repeats until cards in hands finishes and winner
//is the player with the least points
//File Name: GameController.java
//Author: Loo Ting Xian
package com.looting.sixnym;

import android.widget.TextView;

import java.util.ArrayList;

public class GameController {

    private TableCards tb;
    private ViewManager vm;
    private ArrayList<Player> pArray;
    private ArrayList<Card> cardsPlayed;

    public GameController(TableCards tb, ViewManager vm, ArrayList<Player> pArray){
        this.tb = tb;
        this.vm = vm;
        this.pArray = pArray;
        cardsPlayed = new ArrayList<Card>();
        tb.fillDeck(pArray.size());
        tb.shuffleDeck();
    }

    public void cardPhase(){
        vm.displayPlayerHands(getPlayerCards());
        vm.displayRows(tb.getCardRows());
    }

    public String getPlayerCards(){
        String playerCards = "";
        playerCards += "Please select a card to play: " + '\n';
        for(int i = 0; i < pArray.get(0).handSize(); i++)
        {
            Card card = pArray.get(0).displayCard(i);
            playerCards += "Card " + Integer.toString(i + 1) + " - face value: ";
            playerCards += Integer.toString(card.getFaceValue());
            playerCards += " Point value: " + Integer.toString(card.getPointValue());
            playerCards += '\n';
        }

        return playerCards;
    }

    public void dealCards(){
        tb.dealCards(pArray);
    }
}
