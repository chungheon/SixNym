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
import android.widget.Toast;

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
        if(vm.checkCard(pArray.get(player).handSize())) {
            int cardPlay = vm.getCardPlayed();
            if (cardPlay != -1) {
                cardPlay -= 1;
                Card c = this.pArray.get(player).playCard(cardPlay);
                CardPlayed cp = new CardPlayed(c, player);
                this.cardsPlayed.add(cp);
                player = (turn + 1) % pArray.size();
                if (player == 0) {
                    Collections.sort(cardsPlayed);
                    if (closestRow(cardsPlayed.get(0).card) == -1) {
                        String message = "";
                        for (CardPlayed cardP : this.cardsPlayed) {
                            message += pArray.get(cardP.playerIndex).getName() + " played " + cardP.card.displayCard() + '\n';
                        }
                        vm.selectRowDialog(pArray.get(cardsPlayed.get(0).playerIndex).getName(), message);
                    } else {
                        playCards();
                        updateRows();
                        displayPlayerHands(0);
                        if (this.turn == (pArray.size() * 10) - 1) {
                            this.turn++;
                            gameEnd();
                        } else {
                            this.turn++;
                        }
                    }
                } else {
                    displayPlayerHands(player);
                    this.turn++;
                }
            } else {
                vm.printToast("Please Select a CARD!");
            }
        }
    }

    private void displayPlayerHands(int playerTurn){
        vm.displayPlayerHands(pArray.get(playerTurn).getName(), pArray.get(playerTurn).getPlayerCards(), pArray.get(playerTurn).getTotalPoints());
    }

    private void updateRows(){
        vm.displayRows(tb.getCardRows());
    }

    private void dealCards(){
        tb.dealCards(pArray);
    }

    private void placeCardOnRow(Card cardToBePlaced){
        int cntRow = closestRow(cardToBePlaced);

        if (cntRow != -1) {
            this.tb.getCardRows().get(cntRow).addToRow(cardToBePlaced);
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

    private void playCards() {
        for (CardPlayed cP : this.cardsPlayed) {
            this.placeCardOnRow(cP.card);
        }
        this.cardsPlayed.clear();
    }

    public void selectRow(){
        int rowSelected = vm.getRowSelected();
        if(rowSelected != -1){
            int cardRowSize = tb.getCardRow(rowSelected).getSize();
            CardRow cardRow = tb.getCardRow(rowSelected);
            for (int i = 0; i < cardRowSize;i++ ) {
                pArray.get(cardsPlayed.get(0).playerIndex).addPoints(cardRow.removeFromRow());
            }
            this.tb.getCardRows().get(rowSelected).addToRow(cardsPlayed.get(0).card);
            this.cardsPlayed.remove(0);
            vm.endRowDialog();

            if(this.turn == (pArray.size() * 10) - 1) {
                this.turn++;
                playCards();
                gameEnd();
            }else{
                this.turn++;
                playCards();
                updateRows();
                displayPlayerHands(0);
            }
        }else{
            vm.printToast("Please Select a ROW!");
        }
    }

    public void gameEnd(){
        ArrayList<String> playerScores = new ArrayList<>();
        for(int i = 0; i < pArray.size(); i++){
            String s = pArray.get(i).getName() + " - ";
            s += pArray.get(i).getTotalPoints();
            playerScores.add(s);
        }
        for(Player p: pArray){
            while(!p.isPointEmpty()){
                tb.returnCard(p.returnCard());
            }
        }
        tb.getFromAllRows();
        vm.endGame(playerScores);
        tb.shuffleDeck();
    }

    public void startNewGame(){
        vm.startGame();
        Collections.shuffle(pArray);
        startGame();
    }

    public boolean getTurn(){
        if(turn == (pArray.size() * 10)){
            return false;
        }else{
            return true;
        }
    }
}
