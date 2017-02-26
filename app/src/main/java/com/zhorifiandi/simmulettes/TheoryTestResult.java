/* Created by Sashi Novitasari (24th Feb 2017)
 * Activity to display the result of knowledge test - Simulletes
 */

package com.zhorifiandi.simmulettes;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import io.realm.Realm;

public class TheoryTestResult extends AppCompatActivity {
    protected TextView scoreText;
    protected TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory_test_result);
        getElementInfo();
        Intent intent = getIntent();

        String scoreMess = intent.getStringExtra("score");// get mode
        String statusMess = intent.getStringExtra("status");// get mode
        String modeMess = intent.getStringExtra("mode");// get
        String numProb =  intent.getStringExtra("numProb");

        float score = Integer.parseInt(scoreMess);
        score = score/(Integer.parseInt(numProb))*100;
        int finalScore = (int) score;

        scoreText.setText(finalScore+"");
        statusText.setText(statusMess);

        if (statusMess.equals("NOT PASSED")) statusText.setTextColor(Color.parseColor("#442598"));
        else statusText.setTextColor(Color.parseColor("#42F498"));

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        LastScore lscore = realm.where(LastScore.class).findFirst();

        if (modeMess.equals("test")){
            lscore.setID(finalScore);
            if (finalScore > lscore.getSimulationScore()) {
                lscore.setSimulation(finalScore);
            }
        }else{
            if (finalScore > lscore.getSimulationPractice()){
                lscore.setPractice(finalScore);
            }
        }
        realm.commitTransaction();
        realm.close();
    }

    public void getElementInfo(){
        scoreText = (TextView) findViewById(R.id.score);
        statusText = (TextView) findViewById(R.id.status);
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(this, SideMenu.class);
        intent.putExtra("activity","TheoryTestResult");
        startActivity(intent);
    }
}
