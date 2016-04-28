package com.kaydee.kyle.SwiftShift;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;



public class score_screen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        //requestNewBanner();
            TextView lastScore = (TextView) findViewById(R.id.lastscore);
            TextView highScore = (TextView) findViewById(R.id.highscore);
            RelativeLayout background = (RelativeLayout) findViewById(R.id.relativeLayout);
            Button home = (Button) findViewById(R.id.home);
            Button tryagain = (Button) findViewById(R.id.tryagain);
            SharedPreferences scorePrefs = getSharedPreferences(medGame.LAST_SCORE, MODE_PRIVATE);
            int ls = scorePrefs.getInt("lastScore", MODE_PRIVATE);
            int hs = scorePrefs.getInt("highScore", MODE_PRIVATE);

            lastScore.setText("Last Score: " + ls);
            highScore.setText("High Score: " + hs);
            if (ls == hs) {
                background.setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                background.setBackgroundColor(getResources().getColor(R.color.red));
            }

            home.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SwiftShift.class);
                    startActivity(intent);
                }
            });

            tryagain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), medGame.class);
                    startActivity(intent);
                }
            });
    }

    private void requestNewBanner() {
    try{
        AdRequest bottomAdRequest = new AdRequest.Builder().build();
        AdRequest topAdRequest = new AdRequest.Builder().build();
        AdView topAdView = (AdView) findViewById(R.id.topAd);
        AdView bottomAdView = (AdView) findViewById(R.id.bottomAd);
        topAdView.loadAd(topAdRequest);
        bottomAdView.loadAd(bottomAdRequest);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
