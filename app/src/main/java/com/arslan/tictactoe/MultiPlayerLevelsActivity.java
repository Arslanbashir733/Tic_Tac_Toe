package com.arslan.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MultiPlayerLevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayerlevels);

       Button normal = findViewById(R.id.normal);

       Button easy = findViewById(R.id.easy);

       Button hard = findViewById(R.id.hard);

       Button mainmenu = findViewById(R.id.mainMenu);

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MultiPlayerLevelsActivity.this, MultiPlayerActivity.class));
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MultiPlayerLevelsActivity.this, MultiIPlayerEasyActivity.class));
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MultiPlayerLevelsActivity.this, MultiPlayerHardActivity.class));
            }
        });

        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MultiPlayerLevelsActivity.this, StartPageActivity.class));
            }
        });
    }
}
