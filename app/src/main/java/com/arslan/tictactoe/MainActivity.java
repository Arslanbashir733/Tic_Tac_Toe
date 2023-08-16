package com.arslan.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Button multiPlayer = findViewById(R.id.multiPlayer);

        multiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, MultiPlayerLevelsActivity.class));
            }
        });

       Button singlePlayer = findViewById(R.id.singlePlayer);

        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, SinglePlayerLevelsActivity.class));
            }
        });

       Button highscore = findViewById(R.id.highscore);

        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, HighScoresActivity.class));
            }
        });
    }
}