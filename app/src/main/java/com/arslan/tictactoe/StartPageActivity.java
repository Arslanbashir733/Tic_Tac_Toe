package com.arslan.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

       Button multiPlayer = findViewById(R.id.multiPlayer);

        multiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StartPageActivity.this, MultiPlayerLevelsActivity.class));
            }
        });

       Button singlePlayer = findViewById(R.id.singlePlayer);

        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StartPageActivity.this, SinglePlayerLevelsActivity.class));
            }
        });

       Button highscore = findViewById(R.id.highscore);

        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StartPageActivity.this, HighScoresActivity.class));
            }
        });
    }
}
