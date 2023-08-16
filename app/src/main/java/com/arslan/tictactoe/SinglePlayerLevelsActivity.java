package com.arslan.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SinglePlayerLevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayerlevels);

        Button normal = findViewById(R.id.normal);

        Button easy = findViewById(R.id.easy);

        Button hard = findViewById(R.id.hard);

        Button mainmenu = findViewById(R.id.mainMenu);

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SinglePlayerLevelsActivity.this, SinglePlayerNormal.class));
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SinglePlayerLevelsActivity.this, SinglePlayerEasy.class));
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SinglePlayerLevelsActivity.this, SinglePlayerHard.class));
            }
        });

        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SinglePlayerLevelsActivity.this, StartPageActivity.class));
            }
        });
    }
}
