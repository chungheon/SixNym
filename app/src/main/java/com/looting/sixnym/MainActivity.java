package com.looting.sixnym;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view){
        Intent intent = new Intent(this, SinglePlayer.class);
        startActivity(intent);
    }

    public void showPeers(View view){
        Intent intent = new Intent (this, MultiplayerConnect.class);
        startActivity(intent);
    }

}
