package com.zhorifiandi.simmulettes;

import android.app.FragmentManager;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SimulationMainMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {

        View view = inflater.inflate(R.layout.activity_simulation_main_menu, container, false);

        /*Button drive = (Button) view.findViewById(R.id.drive);
        drive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), test_sim_activity.class);
                intent.putExtra("message","test");
                startActivity(intent);
            }
        });*/

        Button theory = (Button) view.findViewById(R.id.theory);
        theory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment theoryFrag = new TheoryTestMainMenu();
                FragmentManager trans = getFragmentManager();
                trans.beginTransaction().replace(R.id.content_frame, theoryFrag).commit();
            }
        });

        Button drive = (Button) view.findViewById(R.id.drive);
        drive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);


            }
        });

        return view;
    }
}
