package com.arslan.tictactoe;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class SinglePlayerHard extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int chancecount = 0;
    private TextView chance;

    private int player1Points = 0;
    private int player2Points = 0;
    private int draw = 0;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView textViewdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_hard);

        textViewPlayer1 = (TextView) findViewById(R.id.playerchance);
        chance = (TextView) findViewById(R.id.chancesetter);
        textViewPlayer2 = (TextView) findViewById(R.id.winview);
        textViewdraw = (TextView) findViewById(R.id.drawview);

        //connect all the buttons with their ids(giving reference to all buttons)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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
//                Intent backtomain = new Intent(hardcore.this,mainpage.class);
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

    void decidechance(Button obj) {

        chancecount++;
        obj.setText("x");
        obj.setTextColor(getResources().getColor(R.color.xcolor));

        disableClickOnAllBtns();

        chance.setText("Computer");
        obj.setEnabled(false);
        Log.d("chncecount", String.valueOf(chancecount));
        player1Turn = !player1Turn;
        result();
    }

    void result() {
        if (checkForWin()) {
            if (!player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (chancecount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
            chancecount++;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    compchance();
                }
            }, 400);

            Log.d("chancecount", String.valueOf(chancecount));
        }
    }

    void randombutton() {
        Random random = new Random();
        int i = random.nextInt(3);
        int j = random.nextInt(3);
        randomchance(buttons[i][j]);
    }

    void randomchance(Button obj) {
        if (obj.getText().toString().isEmpty()) {
            obj.setText("o");
            obj.setTextColor(getResources().getColor(R.color.ocolor));
            enableClickOnAllBtns();
            chance.setText("Human");
            obj.setEnabled(false);
        } else {
            randombutton();
        }
    }

    void randomcorner() {
        Random random = new Random();
        int i = random.nextInt(3);
        int j = random.nextInt(3);
        cornerchance(buttons[i][j], i, j);
    }

    void cornerchance(Button obj, int i, int j) {
        if ((i == 0 && j == 0) || (i == 0 && j == 2) || (i == 2 && j == 0) || (i == 2 && j == 2)) {
            if (obj.getText().toString().isEmpty())//this if is unnessacry cause hoga to khali hi
            {
                obj.setText("o");
                obj.setTextColor(getResources().getColor(R.color.ocolor));
                enableClickOnAllBtns();
                chance.setText("Human");
                obj.setEnabled(false);
            } else {
                randomcorner();
            }
        } else {
            randomcorner();
        }
    }

    void randomedge() {
        Random random = new Random();
        int i = random.nextInt(3);
        int j = random.nextInt(3);
        edgechance(buttons[i][j], i, j);
    }

    void edgechance(Button obj, int i, int j) {
        if ((i == 1 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 2) || (i == 2 && j == 1)) {
            if (obj.getText().toString().isEmpty())//this if is unnessacry cause hoga to khali hi
            {
                obj.setText("o");
                obj.setTextColor(getResources().getColor(R.color.ocolor));
                enableClickOnAllBtns();
                chance.setText("Human");
                obj.setEnabled(false);
            } else {
                randomedge();
            }
        } else {
            randomedge();
        }
    }

    void centerchance(Button obj) {
        obj.setText("o");
        obj.setTextColor(getResources().getColor(R.color.ocolor));
        enableClickOnAllBtns();
        chance.setText("Human");
        obj.setEnabled(false);
    }

    void contineouscheck() {
        if (checkForWin()) {
            if (!player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        }
    }

    void compchance() {
        if (buttons[1][1].getText().equals("x"))//means player played 1 chance in center
        {
            Log.d("center", "if  center me x hai");
            if (chancecount == 2)//to
            {
                randomcorner();//corner pe chalde
            } else if (chancecount == 4) {
                if (!buttons[0][0].getText().equals("") && !buttons[1][1].getText().equals("") && !buttons[2][2].getText().equals("")) {
                    randomcorner();
                } else if (!buttons[0][2].getText().equals("") && !buttons[1][1].getText().equals("") && !buttons[2][0].getText().equals("")) {
                    randomcorner();
                } else {
                    int flag2 = 0;
                    if (checkallo()) {

                        contineouscheck();
                    } else if (checkallx()) {
                        flag2 = 1;
                        contineouscheck();
                    } else {
                        randombutton();
                    }
                }
            } else {
                int flag2 = 0;
                if (checkallo()) {
                    flag2 = 1;
                    contineouscheck();
                } else if (checkallx()) {
                    flag2 = 1;
                    contineouscheck();
                } else {
                    randombutton();
                }
            }
        } else if ((buttons[0][0].getText().equals("x") || buttons[0][2].getText().equals("x") || buttons[2][0].getText().equals("x") || buttons[2][2].getText().equals("x")) || (buttons[1][1].getText().equals("o"))) {
            Log.d("corner", "if corner pe x hai");
            if (chancecount == 2) {
                centerchance(buttons[1][1]);
            } else if (chancecount == 4) {
                if (!buttons[0][0].getText().equals("") && !buttons[1][1].getText().equals("") && !buttons[2][2].getText().equals("")) {
                    randomedge();
                } else if (!buttons[0][2].getText().equals("") && !buttons[1][1].getText().equals("") && !buttons[2][0].getText().equals("")) {
                    randomedge();
                } else {
                    if (checkallx()) {
                        contineouscheck();
                    } else {
                        if ((buttons[0][0].getText().equals("")) && (((buttons[0][1].getText().equals("x")) || (buttons[0][2].getText().equals("x"))) && ((buttons[1][0].getText().equals("x")) || (buttons[2][0].getText().equals("x"))))) {
                            centerchance(buttons[0][0]);
                            contineouscheck();
                        } else if ((buttons[0][2].getText().equals("")) && (((buttons[0][1].getText().equals("x")) || (buttons[0][0].getText().equals("x"))) && ((buttons[1][2].getText().equals("x")) || (buttons[2][2].getText().equals("x"))))) {
                            centerchance(buttons[0][2]);
                            contineouscheck();
                        } else if ((buttons[2][2].getText().equals("")) && (((buttons[0][2].getText().equals("x")) || (buttons[1][2].getText().equals("x"))) && ((buttons[2][0].getText().equals("x")) || (buttons[2][1].getText().equals("x"))))) {
                            centerchance(buttons[2][2]);
                            contineouscheck();
                        } else {
                            centerchance(buttons[2][0]);
                            contineouscheck();
                        }
                        contineouscheck();
                    }
                }
            } else {
                int flag2 = 0;
                if (checkallo()) {
                    flag2 = 1;
                    contineouscheck();
                } else if (checkallx()) {
                    flag2 = 1;
                    contineouscheck();
                } else {
                    randombutton();
                }
            }
        } else {
            if (buttons[1][0].getText().equals("x") || buttons[0][1].getText().equals("x") || buttons[1][2].getText().equals("x") || buttons[2][1].getText().equals("x")) {
                Log.d("anywhere", "else part called");
                centerchance(buttons[1][1]);
                int flag2 = 0;
                if (checkallo()) {
                    flag2 = 1;
                    contineouscheck();
                } else if (checkallx()) {
                    flag2 = 1;
                    contineouscheck();
                } else {

                }
            }
        }
    }

    private boolean checkallo() {
        int flag = 0;//if flag 0 means chance nahi chali
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getText().equals("o") && buttons[i][1].getText().equals("o") && (buttons[i][2].getText().equals("")) && flag == 0)) {
                seto(buttons[i][2]);
                flag = 1;
                return true;
            }
            if ((buttons[i][0].getText().equals("o") && buttons[i][2].getText().equals("o") && (buttons[i][1].getText().equals("")) && flag == 0)) {
                seto(buttons[i][1]);
                flag = 1;
                return true;
            }
            if ((buttons[i][1].getText().equals("o") && buttons[i][2].getText().equals("o") && (buttons[i][0].getText().equals("")) && flag == 0)) {
                seto(buttons[i][0]);
                flag = 1;
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if ((buttons[0][i].getText().equals("o") && buttons[1][i].getText().equals("o") && (buttons[2][i].getText().equals("")) && flag == 0)) {
                seto(buttons[2][i]);
                flag = 1;
                return true;
            }
            if ((buttons[0][i].getText().equals("o") && buttons[2][i].getText().equals("o") && (buttons[1][i].getText().equals("")) && flag == 0)) {
                seto(buttons[1][i]);
                flag = 1;
                return true;
            }
            if ((buttons[1][i].getText().equals("o") && buttons[2][i].getText().equals("o") && (buttons[0][i].getText().equals("")) && flag == 0)) {
                seto(buttons[0][1]);
                flag = 1;
                return true;
            }
        }
        if ((buttons[0][0].getText().equals("o") && buttons[1][1].getText().equals("o") && (buttons[2][2].getText().equals("")) && flag == 0)) {
            seto(buttons[2][2]);
            flag = 1;
            return true;
        }
        if ((buttons[0][0].getText().equals("o") && buttons[2][2].getText().equals("o") && (buttons[1][1].getText().equals("")) && flag == 0)) {
            seto(buttons[1][1]);
            flag = 1;
            return true;
        }
        if ((buttons[2][2].getText().equals("o") && buttons[1][1].getText().equals("o") && (buttons[0][0].getText().equals("")) && flag == 0)) {
            seto(buttons[0][0]);
            flag = 1;
            return true;
        }
        if ((buttons[0][2].getText().equals("o") && buttons[1][1].getText().equals("o") && (buttons[2][0].getText().equals("")) && flag == 0)) {
            seto(buttons[2][0]);
            flag = 1;
            return true;
        }
        if ((buttons[1][1].getText().equals("o") && buttons[2][0].getText().equals("o") && (buttons[0][2].getText().equals("")) && flag == 0)) {
            seto(buttons[0][2]);
            flag = 1;
            return true;
        }
        if ((buttons[2][0].getText().equals("o") && buttons[0][2].getText().equals("o") && (buttons[1][1].getText().equals("")) && flag == 0)) {
            seto(buttons[1][1]);
            flag = 1;
            return true;
        }
        return false;
    }

    private boolean checkallx() {
        int flag = 0;//if flag 0 means chance nahi chali
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getText().equals("x") && buttons[i][1].getText().equals("x") && (buttons[i][2].getText().equals("")) && flag == 0)) {
                seto(buttons[i][2]);
                flag = 1;
                return true;
            }
            if ((buttons[i][0].getText().equals("x") && buttons[i][2].getText().equals("x") && (buttons[i][1].getText().equals("")) && flag == 0)) {
                seto(buttons[i][1]);
                flag = 1;
                return true;
            }
            if ((buttons[i][1].getText().equals("x") && buttons[i][2].getText().equals("x") && (buttons[i][0].getText().equals("")) && flag == 0)) {
                seto(buttons[i][0]);
                flag = 1;
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if ((buttons[0][i].getText().equals("x") && buttons[1][i].getText().equals("x") && (buttons[2][i].getText().equals("")) && flag == 0)) {
                seto(buttons[2][i]);
                flag = 1;
                return true;
            }
            if ((buttons[0][i].getText().equals("x") && buttons[2][i].getText().equals("x") && (buttons[1][i].getText().equals("")) && flag == 0)) {
                seto(buttons[1][i]);
                flag = 1;
                return true;
            }
            if ((buttons[1][i].getText().equals("x") && buttons[2][i].getText().equals("x") && (buttons[0][i].getText().equals("")) && flag == 0)) {
                seto(buttons[0][i]);
                flag = 1;
                return true;
            }
        }
        if ((buttons[0][0].getText().equals("x") && buttons[1][1].getText().equals("x") && (buttons[2][2].getText().equals("")) && flag == 0)) {
            seto(buttons[2][2]);
            flag = 1;
            return true;
        }
        if ((buttons[0][0].getText().equals("x") && buttons[2][2].getText().equals("x") && (buttons[1][1].getText().equals("")) && flag == 0)) {
            seto(buttons[1][1]);
            flag = 1;
            return true;
        }
        if ((buttons[2][2].getText().equals("x") && buttons[1][1].getText().equals("x") && (buttons[0][0].getText().equals("")) && flag == 0)) {
            seto(buttons[0][0]);
            flag = 1;
            return true;
        }
        if ((buttons[0][2].getText().equals("x") && buttons[1][1].getText().equals("x") && (buttons[2][0].getText().equals("")) && flag == 0)) {
            seto(buttons[2][0]);
            flag = 1;
            return true;
        }
        if ((buttons[1][1].getText().equals("x") && buttons[2][0].getText().equals("x") && (buttons[0][2].getText().equals("")) && flag == 0)) {
            seto(buttons[0][2]);
            flag = 1;
            return true;
        }
        if ((buttons[2][0].getText().equals("x") && buttons[0][2].getText().equals("x") && (buttons[1][1].getText().equals("")) && flag == 0)) {
            seto(buttons[1][1]);
            flag = 1;
            return true;
        }
        return false;
    }

    void seto(Button obj) {
        obj.setText("o");
        obj.setTextColor(getResources().getColor(R.color.ocolor));
        enableClickOnAllBtns();
        chance.setText("Human");
        obj.setEnabled(false);
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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
        updatePointsText();
        disableClickOnAllBtns();
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
        //use this code if you take users name also as input so that there is random chandom chance of each player first
        //Random random= new Random();
        //boolean randomoutcome=random.nextBoolean();
        player1Turn = true;
        chance.setText("Human");
    }

    private void disableClickOnAllBtns(){
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

    private void enableClickOnAllBtns(){
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