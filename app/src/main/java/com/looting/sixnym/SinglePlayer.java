package com.looting.sixnym;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class SinglePlayer extends Activity {

    private GameController gameController;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImg = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);
        Intent intent = getIntent();
        TableCards tb = new TableCards();
        ArrayList<Player> players = new ArrayList<Player>();
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        Player p3 = new Player("Misun");
        Player p4 = new Player("HELLO!");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        TextView hands = (TextView) findViewById(R.id.handCards);
        TextView row1 = (TextView) findViewById(R.id.firstRow);
        TextView row2 = (TextView) findViewById(R.id.secondRow);
        TextView row3 = (TextView) findViewById(R.id.thirdRow);
        TextView row4 = (TextView) findViewById(R.id.fourthRow);
        EditText editText =(EditText) findViewById(R.id.cardPlayed);
        Button playBtn = (Button) findViewById(R.id.playCard);
        Button selectBtn = (Button) findViewById(R.id.selectButton);
        HorizontalScrollView sv = (HorizontalScrollView) findViewById(R.id.cardDisplay);
        RecyclerView rv = findViewById(R.id.cardDisplay);
        LinearLayoutManager lyM = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(lyM);
        ViewManager vm = new ViewManager(hands, row1, row2, row3, row4, editText, playBtn, selectBtn, rv, this);
        gameController = new GameController(tb, vm, players);
        gameController.startGame();
    }

    public void playCard(View view){
        gameController.playCard();
    }

    public void selectRow(View view) {
        gameController.selectRow();
    }

    @Override
    public void onBackPressed(){ }
}
