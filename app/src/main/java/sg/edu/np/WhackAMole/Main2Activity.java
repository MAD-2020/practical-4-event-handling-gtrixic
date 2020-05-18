package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    final String TAG = "Whack-A-Mole 2.0!";
    CountDownTimer myReadyTimer;
    CountDownTimer myMoleTimer;
    int advancedScore = 0;
    private TextView AdvancedScore;
    ArrayList<Button> buttonList = new ArrayList<>();
    final Toast[] checkToast = {null};


    private void readyTimer(){
        myReadyTimer = new CountDownTimer(10000,1000) {

            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready Countdown!" + millisUntilFinished/1000);
                if (checkToast[0] != null){
                    checkToast[0].cancel();
                }
                checkToast[0] = Toast.makeText(getApplicationContext(), "Get Ready In " + millisUntilFinished/1000 + " seconds.", Toast.LENGTH_SHORT);
                checkToast[0].show();
            }

            public void onFinish() {
                Log.v(TAG, "Ready countdown complete!");
                if (checkToast[0] != null)
                {
                    checkToast[0].cancel();
                }
                checkToast[0] = Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT);
                checkToast[0].show();
                placeMoleTimer();
            }
        };
        myReadyTimer.start();
    }

    private void placeMoleTimer(){
        myMoleTimer = new CountDownTimer(1000, 1000) {

            public void onTick(long l) {
                Log.v(TAG, "New Mole Location!");
                setNewMole();
            }

            @Override
            public void onFinish() {
                myMoleTimer.start();
            }
        };
        myMoleTimer.start();
    }
    private static final int[] BUTTON_IDS = {
        R.id.button1a, R.id.button1b, R.id.button1c, R.id.button2a, R.id.button2b, R.id.button2c,
            R.id.button3a, R.id.button3b, R.id.button3c
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        AdvancedScore = findViewById(R.id.adscore);
        advancedScore = getIntent().getIntExtra("score", 10);
        AdvancedScore.setText("" + advancedScore);

        Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));


        for(final int id : BUTTON_IDS){
            final Button buttonMaker = findViewById(id);
            buttonList.add(buttonMaker);
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        Button rb = buttonList.get(randomLocation);
        for (Button b : buttonList) {
            if (b == rb) {
                b.setText("*");
            } else {
                b.setText("O");
            }
        }
    }

    public void OnClickButton(View v) {
        Button b = (Button) v;
        if (Mole(b)) {
            advancedScore++;
            AdvancedScore.setText("" + advancedScore);
            Log.v(TAG, "Hit, score added!");
        } else {
            if (advancedScore > 0) {
                advancedScore--;
                AdvancedScore.setText("" + advancedScore);
                Log.v(TAG, "Missed, score deducted!");
            }
            else
            {
                advancedScore = 0;
                AdvancedScore.setText("" + advancedScore);
                Log.v(TAG, "Missed, score deducted!");
            }
        }

    }

    public boolean Mole(Button b) {
        if (b.getText() == "*") {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }
}

