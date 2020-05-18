package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private TextView Score;
    int score = 0;
    private static final String TAG = "Whack-A-Mole 1.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        Score = (TextView) findViewById(R.id.Score);
        Score.setText("" + score);
    }

    public void setNewMole() {
        Button[] buttons = {buttonA, buttonB, buttonC};
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        Button rb = buttons[randomLocation];
        for (Button b : buttons) {
            if (b == rb) {
                b.setText("*");
            } else {
                b.setText("O");
            }
        }
    }

    public void OnClickButton(View v) {
        Button b = (Button) v;
        if (Mole(b) == true) {
            score++;
            Score.setText("" + score);
            text(b);
            doCheck(score);
            Log.v(TAG, "Hit, score added!");
        } else {
            if (score > 0) {
                score--;
                Score.setText("" + score);
                text(b);
                Log.v(TAG, "Missed, score deducted!");
            }
            else
            {
                score = 0;
                Score.setText("" + score);
                text(b);
                Log.v(TAG, "Missed, score deducted!");
            }
        }

        setNewMole();
    }


    public boolean Mole(Button b) {
        if (b.getText() == "*") {
            return true;
        } else {
            return false;
        }
    }

    public void text(Button b)
    {
        if(b == buttonA)
        {
            Log.v(TAG, "Button Left Clicked!");
        }
        if(b == buttonB)
        {
            Log.v(TAG, "Button Middle Clicked!");
        }
        if (b == buttonC)
        {
            Log.v(TAG, "Button Right Clicked");
        }
    }


    private void doCheck(int score) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if (score % 10 == 0)
        {
            nextLevelQuery();
        }
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to advance to advanced mode?").setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG,"User accepts. Proceed to advanced page.");
                nextLevel();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User declines. Continue current game.");
            }
        });
        AlertDialog alert = builder.create();
        alert.setTitle("Warning! Insane Whack-A-Mole incoming!");
        alert.show();
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent nextLevel = new Intent(MainActivity.this, Main2Activity.class);
        nextLevel.putExtra("score", score);
        startActivity(nextLevel);
    }


    @Override
    protected void onStart(){
        super.onStart();
        Log.v(TAG, "Starting GUI!");
        setNewMole();
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