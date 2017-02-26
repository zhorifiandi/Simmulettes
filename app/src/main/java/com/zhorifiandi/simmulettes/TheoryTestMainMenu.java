/* Created by Sashi Novitasari (25th Feb 2017)
 * Activity to display knowledge test's main menu.
 * Usage: user choose the mode (test/practice) to start the simulation.
 */

package com.zhorifiandi.simmulettes;

import android.content.Intent;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import  io.realm.Realm;
import io.realm.RealmConfiguration;

public class TheoryTestMainMenu extends Fragment {
    private TextView lastP;
    private TextView lastS;
    private TextView lastBP;
    private TextView lastBS;
    private GridView mPortrait;
    private GridView mLandscape;
    private ViewGroup vg;
    private LayoutInflater infl;
    /*@Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE)  populateViewForOrientationLS(inflater, (ViewGroup) getView());
        else  populateViewForOrientationPort(inflater, (ViewGroup) getView());
    }

    public void populateViewForOrientationLS(LayoutInflater inflater, ViewGroup viewGroup){
        viewGroup.removeAllViewsInLayout();
        View subview = inflater.inflate(R.layout.portrait_theory_test_menu, viewGroup,false);
       viewGroup.addView(subview);
    }

    public void populateViewForOrientationPort(LayoutInflater inflater, ViewGroup viewGroup){
        viewGroup.removeAllViewsInLayout();
        View subview = inflater.inflate(R.layout.activity_theory_test_menu, viewGroup,false);
        viewGroup.addView(subview);
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.activity_theory_test_menu, container, false);
        vg=container;
        infl=inflater;
        //mPortrait =

        Button starttest = (Button) view.findViewById(R.id.test);
        starttest.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TheoryTest.class);
                intent.putExtra("message","test");
                startActivity(intent);
            }
        });

        Button startpractice = (Button) view.findViewById(R.id.practice);
        startpractice.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TheoryTest.class);
                intent.putExtra("message","practice");
                startActivity(intent);
            }
        });
        getElementInfo(view);

        Realm.init(getActivity().getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(config);

        LastScore lscore = realm.where(LastScore.class).findFirst();
        showPointHistory(lscore.getId(),lscore.getSimulationScore());

        return view;
    }

    public void getElementInfo(View view){
        lastP = (TextView) view.findViewById(R.id.lastP);
        lastBP = (TextView) view.findViewById(R.id.lastBP);
        lastS = (TextView) view.findViewById(R.id.lastS);
        lastBS = (TextView) view.findViewById(R.id.lastBS);
    }

    public void showPointHistory(int p, int bp){
        String status;
        String bestStatus;

        if (p>=86) status = "PASSED";
        else status = "NOT PASSED";
        if (bp>=86) bestStatus = "PASSED";
        else bestStatus = "NOT PASSED";

        lastP.setText(p+"");
        lastBP.setText(bp+"");
        lastS.setText(status);
        lastBS.setText(bestStatus);
    }
}
