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
import java.util.Collections;
import java.util.Comparator;

public class GameController {

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

    private TableCards tb;
    private ViewManager vm;
    private ArrayList<Player> pArray;
    private ArrayList<CardPlayed> cardsPlayed;
    private int turn;

    public GameController(TableCards tb, ViewManager vm, ArrayList<Player> pArray){
        this.tb = tb;
        this.vm = vm;
        this.pArray = pArray;
        cardsPlayed = new ArrayList<CardPlayed>();
        tb.fillDeck(pArray.size());
        tb.shuffleDeck();
    }

    public void startGame(){
        dealCards();
        updateRows();
        turn = 0;
        displayPlayerHands((turn % (pArray.size())));
    }



    public void playCard(){
        int player = turn%pArray.size();
        Card c = pArray.get(player).playCard(vm.getCardPlayed()-1);
        CardPlayed cp = new CardPlayed(c, player);
        this.cardsPlayed.add(cp);
        if(turn == (pArray.size() * 10) - 1) {
            //End game
        }else{
            if(vm.checkCard(pArray.get(player).handSize())){
                turn++;
                player = turn%pArray.size();
                if(player == 0){
                    Collections.sort(cardsPlayed);
                    playCards();
                    updateRows();
                    displayPlayerHands(player);
                    vm.nextTurn();
                }else{
                    displayPlayerHands(player);
                    vm.nextTurn();
                }
            }
        }

    }

    private void displayPlayerHands(int playerTurn){
        vm.displayPlayerHands(pArray.get(playerTurn).getName() + '\n', pArray.get(playerTurn).getPlayerCards());
    }

    private void updateRows(){
        vm.displayRows(tb.getCardRows());
    }

    private void dealCards(){
        tb.dealCards(pArray);
    }

    private void placeCardOnRow(Card cardToBePlaced){
        ArrayList<Integer> topCards = new ArrayList<Integer>();
        topCards = this.tb.getTopCardsOnAllRows();
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
        if (closestLowerCard == 0) {
            vm.selectRowDialog();
        } else {
            this.tb.getCardRows().get(cntRow).addToRow(cardToBePlaced);
        }
    }

    private void playCards() {
        for (CardPlayed cP : this.cardsPlayed) {
            this.placeCardOnRow(cP.card);
        }
    }

    public void selectRow(){
        int rowSelected = vm.getCardPlayed();
        int cardRowSize = tb.getCardRow(rowSelected-1).getSize();
        CardRow cardRow = tb.getCardRow(rowSelected-1);
        for (int i = 0; i < cardRowSize;i++ ) {
            pArray.get(cardsPlayed.get(0).playerIndex).addPoints(cardRow.removeFromRow());
        }
        this.tb.getCardRows().get(rowSelected).addToRow(cardsPlayed.get(0).card);
        vm.endRowDialog();
    }
}
