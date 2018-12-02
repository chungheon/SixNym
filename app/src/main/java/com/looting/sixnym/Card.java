//Card, a class to simulate an actual playing
//cards for the game.
//For the game Six Nym, each card should have a unique face value
//Each card should also contain a point value that the player
//accumulates as the game goes on.
//File Name: Card.java
//Author: Loo Ting Xian
package com.looting.sixnym;

public class Card {

    //The face value of the card, cards played would be compared using
    //this value
    private int faceValue;
    //The point value of the card, cards accumulated would be counting
    //points using this value
    private int pointValue;

    //Default constructor, attributes of negative value
    public Card(){
        faceValue = -1;
        pointValue = -1;
    }

    //Constructor, passes both faceValue and pointValue
    public Card(int faceValue, int pointValue){
        this.faceValue = faceValue;
        this.pointValue = pointValue;
    }

    //Set the face and point value of the card
    public void setCard(int faceValue, int pointValue){
        this.faceValue = faceValue;
        this.pointValue = pointValue;
    }

    //Get the face value of the card
    public int getFaceValue(){
        return faceValue;
    }

    //Get the point value of the card
    public int getPointValue() {
        return pointValue;
    }
}
