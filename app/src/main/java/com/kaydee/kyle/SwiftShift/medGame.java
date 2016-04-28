package com.kaydee.kyle.SwiftShift;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;

import static com.kaydee.kyle.SwiftShift.R.id.*;

public class medGame extends Activity{
    Button position0BT;
    Button position1BT;
    Button position2BT;
    Button position3BT;
    RelativeLayout backgroundLayout;
    Random rand = new Random();
    int[] positionTrack = {0,1,2,3};
    int score = 0;
    int currentColorShown;
    int shiftDirection;
    int shiftNumber;
    boolean countDisable = true;
    public static final String LAST_SCORE = "lastScore";
    public static final String HIGH_SCORE = "highScore";
    SharedPreferences scoreFile;
    TextView scoreTextView;
    TextView timeTextView;
    timer timeCounter;
    startTimer startTimeCounter;
    Animation rightAnim1, rightAnim2, rightAnim3;
    Animation leftAnim1, leftAnim2, leftAnim3;
    Animation upAnim1, upAnim2, upAnim3;
    Animation downAnim1, downAnim2, downAnim3;
    String timePrompt;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //for displaying score and time
        backgroundLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        scoreTextView = (TextView) findViewById(R.id.score);
        timeTextView = (TextView) findViewById(R.id.clock);

        //for high score tracking
        scoreFile = getSharedPreferences(LAST_SCORE, 0);

        //intiate timeCounter for beginning of game
        startTimeCounter = new startTimer(3000, 100);
        startTimeCounter.start();

        //buttons for color selection
        position0BT = (Button) findViewById(red);
        position1BT = (Button) findViewById(yellow);
        position2BT = (Button) findViewById(blue);
        position3BT = (Button) findViewById(green);

        //Animations for first shift
        leftAnim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left);
        rightAnim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right);
        upAnim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up);
        downAnim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.down);

        //Animations for second shift
        leftAnim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left);
        rightAnim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right);
        upAnim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up);
        downAnim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.down);

        //Animations for third shift
        leftAnim3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left);
        rightAnim3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right);
        upAnim3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up);
        downAnim3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.down);



            /*Top left color button */
        position0BT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonAction(0);
            }
        });
            /*Top right color button */
        position1BT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonAction(1);
            }
        });
            /*Bottom right color button */
        position2BT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonAction(2);
            }
        });
            /*Bottom left color button */
        position3BT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonAction(3);
            }
        });

        upAnim1.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                if(shiftDirection == 0){
                    shiftLeft();
                }else{
                    shiftRight();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showCurrentPosition();
                if(shiftDirection == 0) {
                    position0BT.startAnimation(downAnim2);
                    position1BT.startAnimation(leftAnim2);
                    position2BT.startAnimation(upAnim2);
                    position3BT.startAnimation(rightAnim2);
                }else{
                    position0BT.startAnimation(rightAnim2);
                    position1BT.startAnimation(downAnim2);
                    position2BT.startAnimation(leftAnim2);
                    position3BT.startAnimation(upAnim2);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        upAnim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if(shiftDirection == 0){
                    shiftLeft();
                }else{
                    shiftRight();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showCurrentPosition();
                if(shiftDirection == 0) {
                    position0BT.startAnimation(downAnim3);
                    position1BT.startAnimation(leftAnim3);
                    position2BT.startAnimation(upAnim3);
                    position3BT.startAnimation(rightAnim3);
                }else{
                    position0BT.startAnimation(rightAnim3);
                    position1BT.startAnimation(downAnim3);
                    position2BT.startAnimation(leftAnim3);
                    position3BT.startAnimation(upAnim3);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        upAnim3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if(shiftDirection == 0){
                    shiftLeft();
                }else{
                    shiftRight();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                currentColorShown = rand.nextInt(4);
                setCurrentColor(currentColorShown);
                timeCounter = new timer(3000, 100);
                timeCounter.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void buttonAction(int buttonPressed){
        if(!countDisable){
            if (testColorPosition(buttonPressed)) {
                //pick shift direction
                shiftDirection = rand.nextInt(2);
                //select number of shifts
                shiftNumber = rand.nextInt(3) + 1;
                //shift left
                if(shiftDirection == 0) {
                    timeCounter.cancel();
                    addScore();
                    scoreTextView.setText(getScore());
                    multiShift(shiftDirection,shiftNumber);

                }else{//shift right
                    timeCounter.cancel();
                    addScore();
                    scoreTextView.setText(getScore());
                    multiShift(shiftDirection,shiftNumber);
                }
            } else {
                timeCounter.onFinish();

            }
        }
    }

    private void showCurrentPosition(){
        int redPosition = getCurrentRedPosition();
                position0BT.setText(null);
                position1BT.setText(null);
                position2BT.setText(null);
                position3BT.setText(null);
            if (redPosition == 0) {
                position0BT.setBackgroundResource(R.drawable.redgamebtn);
                position1BT.setBackgroundResource(R.drawable.yellowgamebtn);
                position2BT.setBackgroundResource(R.drawable.bluegamebtn);
                position3BT.setBackgroundResource(R.drawable.greengamebtn);
            } else if (redPosition == 1) {
                position0BT.setBackgroundResource(R.drawable.greengamebtn);
                position1BT.setBackgroundResource(R.drawable.redgamebtn);
                position2BT.setBackgroundResource(R.drawable.yellowgamebtn);
                position3BT.setBackgroundResource(R.drawable.bluegamebtn);
            } else if (redPosition == 2) {
                position0BT.setBackgroundResource(R.drawable.bluegamebtn);
                position1BT.setBackgroundResource(R.drawable.greengamebtn);
                position2BT.setBackgroundResource(R.drawable.redgamebtn);
                position3BT.setBackgroundResource(R.drawable.yellowgamebtn);
            } else {
                position0BT.setBackgroundResource(R.drawable.yellowgamebtn);
                position1BT.setBackgroundResource(R.drawable.bluegamebtn);
                position2BT.setBackgroundResource(R.drawable.greengamebtn);
                position3BT.setBackgroundResource(R.drawable.redgamebtn);
            }
        }

    private void setCurrentColor(int currentColor) {
        if (currentColor == 0) {
            position0BT.setBackgroundResource(R.drawable.redgamebtn);
            position1BT.setBackgroundResource(R.drawable.redgamebtn);
            position2BT.setBackgroundResource(R.drawable.redgamebtn);
            position3BT.setBackgroundResource(R.drawable.redgamebtn);
            position0BT.setText(R.string.question);
            position0BT.setTextSize(72);
            position1BT.setText(R.string.question);
            position1BT.setTextSize(72);
            position2BT.setText(R.string.question);
            position2BT.setTextSize(72);
            position3BT.setText(R.string.question);
            position3BT.setTextSize(72);
        } else if (currentColor == 1) {
            position0BT.setBackgroundResource(R.drawable.yellowgamebtn);
            position1BT.setBackgroundResource(R.drawable.yellowgamebtn);
            position2BT.setBackgroundResource(R.drawable.yellowgamebtn);
            position3BT.setBackgroundResource(R.drawable.yellowgamebtn);
            position0BT.setText(R.string.question);
            position0BT.setTextSize(72);
            position1BT.setText(R.string.question);
            position1BT.setTextSize(72);
            position2BT.setText(R.string.question);
            position2BT.setTextSize(72);
            position3BT.setText(R.string.question);
            position3BT.setTextSize(72);
        } else if (currentColor == 2) {
            position0BT.setBackgroundResource(R.drawable.bluegamebtn);
            position1BT.setBackgroundResource(R.drawable.bluegamebtn);
            position2BT.setBackgroundResource(R.drawable.bluegamebtn);
            position3BT.setBackgroundResource(R.drawable.bluegamebtn);
            position0BT.setText(R.string.question);
            position0BT.setTextSize(72);
            position1BT.setText(R.string.question);
            position1BT.setTextSize(72);
            position2BT.setText(R.string.question);
            position2BT.setTextSize(72);
            position3BT.setText(R.string.question);
            position3BT.setTextSize(72);
        } else {
            position0BT.setBackgroundResource(R.drawable.greengamebtn);
            position1BT.setBackgroundResource(R.drawable.greengamebtn);
            position2BT.setBackgroundResource(R.drawable.greengamebtn);
            position3BT.setBackgroundResource(R.drawable.greengamebtn);
            position0BT.setText(R.string.question);
            position0BT.setTextSize(72);
            position1BT.setText(R.string.question);
            position1BT.setTextSize(72);
            position2BT.setText(R.string.question);
            position2BT.setTextSize(72);
            position3BT.setText(R.string.question);
            position3BT.setTextSize(72);
        }
    }

    private void multiShift(int direction, int shifts) {
        if (direction == 0) {
            switch (shifts) {
                case 3:
                    showCurrentPosition();
                    position0BT.startAnimation(downAnim1);
                    position1BT.startAnimation(leftAnim1);
                    position2BT.startAnimation(upAnim1);
                    position3BT.startAnimation(rightAnim1);
                    break;
                case 2:
                    showCurrentPosition();
                    position0BT.startAnimation(downAnim2);
                    position1BT.startAnimation(leftAnim2);
                    position2BT.startAnimation(upAnim2);
                    position3BT.startAnimation(rightAnim2);
                    break;
                case 1:
                    showCurrentPosition();
                    position0BT.startAnimation(downAnim3);
                    position1BT.startAnimation(leftAnim3);
                    position2BT.startAnimation(upAnim3);
                    position3BT.startAnimation(rightAnim3);
                    break;
                default:
            }
        } else {
            switch (shifts) {
                case 3:
                    showCurrentPosition();
                    position0BT.startAnimation(rightAnim1);
                    position1BT.startAnimation(downAnim1);
                    position2BT.startAnimation(leftAnim1);
                    position3BT.startAnimation(upAnim1);
                case 2:
                    showCurrentPosition();
                    position0BT.startAnimation(rightAnim2);
                    position1BT.startAnimation(downAnim2);
                    position2BT.startAnimation(leftAnim2);
                    position3BT.startAnimation(upAnim2);
                case 1:
                    showCurrentPosition();
                    position0BT.startAnimation(rightAnim3);
                    position1BT.startAnimation(downAnim3);
                    position2BT.startAnimation(leftAnim3);
                    position3BT.startAnimation(upAnim3);
                default:
            }
        }
    }

    private void addScore(){
        score = score + 1;
    }

    private void setHighScore() {
        SharedPreferences.Editor editor = scoreFile.edit();
        editor.putInt(LAST_SCORE, score);
        int hs = scoreFile.getInt(HIGH_SCORE, 0);
        if (hs < score)
            editor.putInt(HIGH_SCORE, score);
        editor.apply();
    }

    private void shiftLeft(){
        if (positionTrack[0] == 0) {
            positionTrack[0] = 1;
            positionTrack[1] = 2;
            positionTrack[2] = 3;
            positionTrack[3] = 0;
        } else if (positionTrack[1] == 0) {
            positionTrack[0] = 0;
            positionTrack[1] = 1;
            positionTrack[2] = 2;
            positionTrack[3] = 3;
        } else if (positionTrack[2] == 0) {
            positionTrack[0] = 3;
            positionTrack[1] = 0;
            positionTrack[2] = 1;
            positionTrack[3] = 2;
        } else {
            positionTrack[0] = 2;
            positionTrack[1] = 3;
            positionTrack[2] = 0;
            positionTrack[3] = 1;
        }
    }

    private void shiftRight() {
        if (positionTrack[0] == 0) {
            positionTrack[0] = 3;
            positionTrack[1] = 0;
            positionTrack[2] = 1;
            positionTrack[3] = 2;
        } else if (positionTrack[1] == 0) {
            positionTrack[0] = 2;
            positionTrack[1] = 3;
            positionTrack[2] = 0;
            positionTrack[3] = 1;
        } else if (positionTrack[2] == 0) {
            positionTrack[0] = 1;
            positionTrack[1] = 2;
            positionTrack[2] = 3;
            positionTrack[3] = 0;
        } else {
            positionTrack[0] = 0;
            positionTrack[1] = 1;
            positionTrack[2] = 2;
            positionTrack[3] = 3;
        }
    }

    private String getScore(){
        return getString(R.string.score) + " " + score;
    }

    private int getCurrentRedPosition(){
        if(positionTrack[0] == 0){
            return 0;
        }else if(positionTrack[1] == 0){
            return 1;
        }else if(positionTrack[2] == 0){
            return 2;
        }else{
            return 3;
        }
    }

    private boolean testColorPosition(int buttonPressed) {
        return positionTrack[buttonPressed] == currentColorShown;
    }

    @Override
    public void onBackPressed() {
        if(timeCounter != null)
            timeCounter.cancel();
        if (startTimeCounter != null)
            startTimeCounter.cancel();
        Intent intent = new Intent(getApplicationContext(), score_screen.class);
        startActivity(intent);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }


    private class timer extends CountDownTimer {
        public timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //show game time
            timePrompt = getString(R.string.timeText) + ((millisUntilFinished / 1000) + 1);
            timeTextView.setText(timePrompt);
        }

        @Override
        public void onFinish() {
            timeTextView.setText(R.string.stopTime);
            countDisable = true;
            setHighScore();
            cancel();
            Intent intent = new Intent(getApplicationContext(), score_screen.class);
            startActivity(intent);
        }
    }

    private class startTimer extends CountDownTimer {
        String startText;
        public startTimer(long millisInFuture,long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            startText = getString(R.string.countDown) + " " + ((millisUntilFinished / 1000) + 1);
            timeTextView.setText(startText);
        }

        @Override
        public void onFinish() {
            timeTextView.setText(getString(R.string.start));
            countDisable = false;
            currentColorShown = rand.nextInt(4);
            setCurrentColor(currentColorShown);
            timeCounter = new timer(3000, 100);
            timeCounter.start();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == action_settings || super.onOptionsItemSelected(item);
    }
}

