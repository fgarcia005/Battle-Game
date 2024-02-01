package com.example.finallab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Game game;
    private EditText etName, etWeapon;
    private TextView tvOutput,tvEnemy,tvPlayer;
    private Button btnNextOrStart, btnAttack, btnDodge, btnHeal, btnBoost, btnRestart;
    private ImageView enemyPic, playerPic, ivRestart;
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game();
        onInit();
        toggleUI(true,false,false,false);
        updateOutput("Enter your name");
    }

    private void toggleUI(boolean info,boolean info2, boolean game, boolean restart) {
        int infoSwitch = info ? View.VISIBLE:View.GONE;
        int info2Switch = info2 ? View.VISIBLE:View.GONE;
        int gameSwitch = game ? View.VISIBLE:View.GONE;
        int restartSwitch = restart ? View.VISIBLE:View.GONE;

        etName.setVisibility(infoSwitch);
        etWeapon.setVisibility(info2Switch);
        btnNextOrStart.setVisibility(infoSwitch);
        if(info == false){btnNextOrStart.setVisibility(info2Switch);}

        tvEnemy.setVisibility(gameSwitch);
        tvPlayer.setVisibility(gameSwitch);
        enemyPic.setVisibility(gameSwitch);
        playerPic.setVisibility(gameSwitch);
        btnAttack.setVisibility(gameSwitch);
        btnDodge.setVisibility(gameSwitch);
        btnHeal.setVisibility(gameSwitch);
        btnBoost.setVisibility(gameSwitch);

        btnRestart.setVisibility(restartSwitch);
        ivRestart.setVisibility(restartSwitch);
    }

    private void onInit() {
        tvOutput = findViewById(R.id.tvOutput);
        tvEnemy = findViewById(R.id.tvEnemy);
        tvPlayer = findViewById(R.id.tvPlayer);

        etName = findViewById(R.id.etName);
        etWeapon = findViewById(R.id.etWeapon);

        btnNextOrStart = findViewById(R.id.btnNextOrStart);
        btnAttack = findViewById(R.id.btnAttack);
        btnDodge = findViewById(R.id.btnDodge);
        btnHeal = findViewById(R.id.btnHeal);
        btnBoost = findViewById(R.id.btnBoost);
        btnRestart = findViewById(R.id.btnRestart);

        enemyPic = findViewById(R.id.enemyPic);
        playerPic = findViewById(R.id.playerPic);
        ivRestart = findViewById(R.id.ivRestart);
    }

    private void updateOutput(String output){
        tvOutput.setText(output);
    }

    private void updateUI(){
        tvEnemy.setText(game.displayEnemyText());
        tvPlayer.setText(game.displayPlayerText());
        displayEnemy();
        displayPlayer();
    }

    private void displayPlayer() {playerPic.setImageResource(game.displayPlayerImage());}

    private void displayEnemy() {enemyPic.setImageResource(game.displayEnemyImage());}

    private void displayGameOver() {ivRestart.setImageResource(R.drawable.screenshot_2023_12_05_105536);}

    public void onNextOrStart(View v){
        String nextOrStart = btnNextOrStart.getText().toString();
        String name = etName.getText().toString();
        String weapon = etWeapon.getText().toString();
        try {
            if (nextOrStart.equalsIgnoreCase("Next")) {
                    game.addPlayerlife(name);
                    btnNextOrStart.setText("Start");
                    toggleUI(false, true, false, false);
                    updateOutput("Select your weapon\n(Sword, Claymore, Catalyst, Polearm, Bow)");
            } else if (nextOrStart.equalsIgnoreCase("Start")) {
                    game.findCurrentplayer().setCompact(weapon);
                    btnNextOrStart.setText("Next");
                    toggleUI(false, false, true, false);
                    toggleButton(true);
                    setButton();
                    updateOutput("Game has started");
                    updateUI();
            }
        }catch (InvalidNameException e){
            etName.setError("Name must be at least one character long");
        }catch (InvalidWeaponException ex){
            etWeapon.setError("Make sure you don't have spaces or check spelling or please select " +
                    "a weapon from the list");
        }
    }

    public void onAttack(View v) throws InvalidWeaponException, InvalidNameException {
        try {
            game.playerAttack();
            if (game.gameOver()){
                updateOutput(game.displayOutput());
                toggleUI(false,false,false,true);
                displayGameOver();
            }else {
                updateUI();
                updateOutput(game.displayOutput());
                enemyTurn();
            }
        }catch (InvalidNameException e){
            btnAttack.setError("Something went wrong please restart the app");
        }catch (InvalidWeaponException ex){
            btnAttack.setError("Something went wrong please restart the app");
        }
    }

    public void onHeal(View v) throws InterruptedException {
        game.playerHeal();
        updateUI();
        updateOutput(game.displayOutput());
        setButton();
        enemyTurn();
    }

    public void onDodge(View v) throws InterruptedException {
        game.playerDodge();
        setButton();
        enemyTurn();
    }

    public void onBoost(View v) throws InvalidDamageException {
        try {
            game.playerBoost();
            updateUI();
            updateOutput(game.displayOutput());
            enemyTurn();
            setButton();
        }catch (InvalidDamageException e){
            Toast.makeText(this, "You are already at the max damage output",
                    Toast.LENGTH_LONG).show();
        }

    }
    private void enemyTurn(){
        /*https://www.geeksforgeeks.org/countdowntimer-in-android-with-example/#
        I use this website for learning about CountDownTimer */
        timer = new CountDownTimer(1500,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                toggleButton(false);
            }

            @Override
            public void onFinish() {
                game.enemyTurn();
                if (game.gameOver()){
                    updateOutput(game.displayOutput());
                    toggleUI(false,false,false,true);
                    displayGameOver();
                }else {
                    updateUI();
                    updateOutput(game.displayOutput());
                    toggleButton(true);
                }
            }


        };
        timer.start();
    }

    private void toggleButton(boolean toggle) {
        btnAttack.setEnabled(toggle);
        btnDodge.setEnabled(toggle);
        btnHeal.setEnabled(toggle);
        btnBoost.setEnabled(toggle);
    }

    private void setButton(){
        btnBoost.setText("Boost (" + game.getOnBoostPoints() + ")");
        btnDodge.setText("Dodge (" + game.getOnDodgePoints() + ")");
        btnHeal.setText("Heal ("+ game.getOnHealPoints()+")");
    }


    public void onRestart(View v){
        etName.setText("");
        etWeapon.setText("");
        updateOutput("Enter your name");
        game = new Game();
        toggleUI(true,false,false,false);
    }
}