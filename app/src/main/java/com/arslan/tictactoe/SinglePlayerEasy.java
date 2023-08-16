package com.arslan.tictactoe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SinglePlayerEasy extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int chancecount = 0;
    private TextView chance;

    private int player1Points = 0;

    /// player2 is computer
    private int player2Points = 0;
    private int draw = 0;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView textViewdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_easy);

        textViewPlayer1 = (TextView) findViewById(R.id.playerchance);
        chance = (TextView) findViewById(R.id.chancesetter);
        textViewPlayer2 = (TextView) findViewById(R.id.winview);
        textViewdraw = (TextView) findViewById(R.id.drawview);

        //connect all the buttons with their ids(giving reference to all buttons)
        for (int i = 0; i < 3; i++) // row
        {
            for (int j = 0; j < 3; j++) // column
            {
                String buttonid = "b" + i + j;
                int resid = getResources().getIdentifier(buttonid, "id", getPackageName());
                buttons[i][j] = findViewById(resid);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.btn_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1Points = 0;
                player2Points = 0;
                draw = 0;
                updatePointsText();
                resetBoard();
            }
        });


        Button buttonback = findViewById(R.id.btn_back);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent backtomain = new Intent(easy.this,mainpage.class);
//                startActivity(backtomain);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!(chancecount == 10)) {
            if (v == findViewById(R.id.b00)) {
                decidechance(buttons[0][0]);
            } else if (v == findViewById(R.id.b01)) {
                decidechance(buttons[0][1]);
            } else if (v == findViewById(R.id.b02)) {
                decidechance(buttons[0][2]);
            } else if (v == findViewById(R.id.b10)) {
                decidechance(buttons[1][0]);
            } else if (v == findViewById(R.id.b11)) {
                decidechance(buttons[1][1]);
            } else if (v == findViewById(R.id.b12)) {
                decidechance(buttons[1][2]);
            } else if (v == findViewById(R.id.b20)) {
                decidechance(buttons[2][0]);
            } else if (v == findViewById(R.id.b21)) {
                decidechance(buttons[2][1]);
            } else if (v == findViewById(R.id.b22)) {
                decidechance(buttons[2][2]);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    void decidechance(Button obj) {
        obj.setText("x");
        obj.setTextColor(getResources().getColor(R.color.xcolor));
        disableClickOnAllBtns();

        // now computer chance
        chance.setText("Computer");
        obj.setEnabled(false);
        chancecount++;
        player1Turn = !player1Turn;
        result();
    }

    void randombutton() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int i = random.nextInt(3);
                int j = random.nextInt(3);
                compchance(buttons[i][j]);
            }
        }, 400);
    }


    void compchance(Button obj) {
        if (obj.getText().toString().isEmpty()) {
            obj.setText("c");

            enableClickOnAllBtns();

            obj.setTextColor(getResources().getColor(R.color.ocolor));
            chance.setText("1 PLAYER");
            obj.setEnabled(false);
            if (checkForWin()) {
                player2Wins();
            }
        } else {
            randombutton();
        }
    }


    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) { // row

            for (int j = 0; j < 3; j++) { // column
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Human wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        disableClickOnAllBtns();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetBoard();
            }
        }, 2000);

    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Computer wins!", Toast.LENGTH_SHORT).show();
        disableClickOnAllBtns();
        updatePointsText();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetBoard();
            }
        }, 2000);
    }

    private void draw() {
        draw++;
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        disableClickOnAllBtns();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetBoard();
            }
        }, 2000);
    }


    void result() {
        boolean hasWinner = checkForWin();

        if (hasWinner) {
            int[] winningLine = getWinningLine();
            if (!player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
            assert winningLine != null;
            highlightWinningLine(winningLine);
        } else if (chancecount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
            randombutton();
            chancecount++;
        }
    }

    private int[] getWinningLine() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Implement your logic to determine the winning line
        // Check for rows, columns, and diagonals just like in checkForWin()

        // Example: If you want to check for a winning line in rows
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return new int[]{i, 0, i, 1, i, 2};
            }
        }

        // Example: If you want to check for a winning line in columns
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return new int[]{0, i, 1, i, 2, i};
            }
        }

        // Example: If you want to check for a winning line in diagonals
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return new int[]{0, 0, 1, 1, 2, 2};
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return new int[]{0, 2, 1, 1, 2, 0};
        }

        return null;
    }

    private void highlightWinningLine(int[] winningLine) {
        for (int i = 0; i < winningLine.length; i += 2) {
            int row = winningLine[i];
            int col = winningLine[i + 1];

            // Apply AlphaAnimation for fading
            AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
            animation.setDuration(2000);
            // Set the duration of the animation in milliseconds

            // Set AnimationListener to handle animation events
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // Animation started
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Animation ended, you can reset the button appearance
//                    buttons[row][col].setBackgroundResource(0); // Remove the background resource
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // Animation repeated
                }
            });

            // Apply the animation to the button
            buttons[row][col].startAnimation(animation);
        }
    }

    @SuppressLint("SetTextI18n")
    private void updatePointsText() {
        textViewPlayer1.setText(player1Points + " wins");
        textViewPlayer2.setText(player2Points + " wins");
        textViewdraw.setText(draw + " draws");
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        chancecount = 0;
        //use this code if you take users name also as input so that there is random chance of each player first
//        Random random= new Random();
//        boolean randomoutcome=random.nextBoolean();
        player1Turn = true;
        chance.setText("1 PLAYER");
    }

    private void disableClickOnAllBtns() {
        //connect all the buttons with their ids(giving reference to all buttons)
        for (int i = 0; i < 3; i++) // row
        {
            for (int j = 0; j < 3; j++) // column
            {
                String buttonid = "b" + i + j;
                int resid = getResources().getIdentifier(buttonid, "id", getPackageName());
                buttons[i][j] = findViewById(resid);
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void enableClickOnAllBtns() {
        //connect all the buttons with their ids(giving reference to all buttons)
        for (int i = 0; i < 3; i++) // row
        {
            for (int j = 0; j < 3; j++) // column
            {
                String buttonid = "b" + i + j;
                int resid = getResources().getIdentifier(buttonid, "id", getPackageName());
                buttons[i][j] = findViewById(resid);
                buttons[i][j].setEnabled(true);
            }
        }
    }
}