package com.zhorifiandi.simmulettes;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import java.util.List;

public class TheoryTest extends AppCompatActivity {
    private int problemSize=15;
    protected Button choice_A ;
    protected Button choice_B;
    protected Button choice_C ;
    protected Button choice_D;
    protected Button verif;
    protected Button Next;
    protected TextView Number ;
    protected TextView Problem ;
    protected TextView Score;
    protected ImageView Img;

    protected int totalPoint=0; //Total point
    protected int numProb=0; //The number of problem
    protected int numberofProb=0;
    protected List<ProblemModel>  problemData = new ArrayList<>();
    protected List<Integer> listofProblem; //List of problems that will be asked
    protected String message ="";
    protected String answer="0";
/*
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        8
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE) setContentView(R.layout.portrait_theory_test_menu);
        else  setContentView(R.layout.activity_test_sim_activity);
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int init;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sim_activity);

        //INITIALIZATION
        listofProblem = new ArrayList<Integer>();
        Random rand = new Random();
        getElementInfo();
        initProblemList();

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm myRealm = Realm.getInstance(config);

        //GET INTENT
        Intent intent = getIntent();
        message = intent.getStringExtra("message");// get mode

        //INITIATE LIST OF PROBLEM
        if (message.equals("practice")) numberofProb = 10;
        else if (message.equals("test")) {
            numberofProb = 10;
            verif.setVisibility(View.GONE);
        }

        for (int i=0; i< numberofProb ;i++){
           do {
               numProb = rand.nextInt((problemSize) + 1);
            }while(listofProblem.contains(numProb) || numProb<1 || numProb>=15);
            listofProblem.add(numProb);
            ProblemModel samp = myRealm.where(ProblemModel.class).equalTo("probID",numProb).findFirst();
//                    contains("probID",numProb+"").findFirst();
            problemData.add(samp);
        }
        numProb=0;

        //SHOW FIRST PROBLEM
        ProblemModel firstPr = problemData.get(numProb);

        Number.setText("1");
        Problem.setText(firstPr.getProblem());
        choice_A.setText("A. "+firstPr.getChoiceA());
        choice_B.setText("B. "+firstPr.getChoiceB());
        choice_C.setText("C. "+firstPr.getChoiceC());
        choice_D.setText("D. "+firstPr.getChoiceD());

        Next.setEnabled(false);
        setImage(firstPr);
    }

    public void setImage(ProblemModel p){
        if (!p.getPict().equals("0")) {
            Img.setVisibility(View.VISIBLE);
            //int id =getResources().getIdentifier(p.getPict()+".png","drawable", getPackageName());
            if (p.getPict().equals("prob_trafficlight")) Img.setImageResource(R.mipmap.prob_trafficlight);
            else if (p.getPict().equals("a3")) Img.setImageResource(R.mipmap.a3);
            else if (p.getPict().equals("a2")) Img.setImageResource(R.mipmap.a2);
        }
        else Img.setVisibility(View.GONE);
    }

    public void getElementInfo(){
        Next = (Button) findViewById(R.id.next);
        choice_A = (Button) findViewById(R.id.choice_a);
        choice_B = (Button) findViewById(R.id.choice_b);
        choice_C = (Button) findViewById(R.id.choice_c);
        choice_D = (Button) findViewById(R.id.choice_d);
        Number = (TextView) findViewById(R.id.number);
        Problem = (TextView) findViewById(R.id.problem);
        verif = (Button) findViewById(R.id.verify);
        Score = (TextView) findViewById(R.id.score);
        Img = (ImageView) findViewById(R.id.img);
    }

    public void changeAnsBtnColor(String ans1, String ans2,String ans3){
        if (ans1=="A"|ans2=="A"|ans3=="A") choice_A.setBackgroundColor(Color.parseColor("#D3D3D3"));
        if (ans1=="B"|ans2=="B"|ans3=="B") choice_B.setBackgroundColor(Color.parseColor("#D3D3D3"));
        if (ans1=="C"|ans2=="C"|ans3=="C") choice_C.setBackgroundColor(Color.parseColor("#D3D3D3"));
        if (ans1=="D"|ans2=="D"|ans3=="D") choice_D.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

    public void getAnswer(View view) {
        Next.setEnabled(true);
        if (view==choice_A){
            answer="A";
            choice_A.setBackgroundColor(Color.parseColor("#5c5c5c"));
            changeAnsBtnColor("B","C","D");
        }
        else if (view==choice_B){
            answer="B";
            choice_B.setBackgroundColor(Color.parseColor("#5c5c5c"));
            changeAnsBtnColor("A","C","D");
        }
        else if (view==choice_C) {
            answer="C";
            choice_C.setBackgroundColor(Color.parseColor("#5c5c5c"));
            changeAnsBtnColor("B","A","D");
        }
        else if (view==choice_D) {
            answer="D";
            choice_D.setBackgroundColor(Color.parseColor("#5c5c5c"));
            changeAnsBtnColor("B","C","A");
        }

        if (message.equals("practice") && totalPoint>0) verif.setEnabled(true);
    }

    public void showAnswer(String submittedA_Answer){
        String ans = problemData.get(numProb).getAnswer();
        String color = "#842F49";
        Score.setText(totalPoint+"");
        if (ans.equals(submittedA_Answer)) color = "#42F498";
        else color = "#F44259";

        if (ans.equals("A")) choice_A.setBackgroundColor(Color.parseColor(color));
        else if (ans.equals("B")) choice_B.setBackgroundColor(Color.parseColor(color));
        else if (ans.equals("C")) choice_C.setBackgroundColor(Color.parseColor(color));
        else if (ans.equals("D")) choice_D.setBackgroundColor(Color.parseColor(color));
    }

    public void verify (View view) {
        totalPoint--;
        showAnswer(answer);
        verif.setEnabled(false);
    }

    public void proceed(View view) {
        if (!answer.equals("0")) {
            //sementara
            if (answer.equals(problemData.get(numProb).getAnswer())) totalPoint++;
            numProb++;

            //get prob from DB & render
            if (numProb == numberofProb) {
                Intent intent = new Intent(this, TheoryTestResult.class);
                if (message.equals("practice")) intent.putExtra("mode", "practice");
                else if (message.equals("test")) intent.putExtra("mode", "test");
                intent.putExtra("score", totalPoint + "");
                if (totalPoint <= (int) (numberofProb / 2)) intent.putExtra("status", "NOT PASSED");
                else intent.putExtra("status", "PASSED");
                intent.putExtra("numProb", numProb+"");
                startActivity(intent);
            } else {
                ProblemModel firstPr = problemData.get(numProb);
                Number.setText((numProb + 1) + "");
                Problem.setText(firstPr.getProblem());
                choice_A.setText("A. "+firstPr.getChoiceA());
                choice_B.setText("B. "+firstPr.getChoiceB());
                choice_C.setText("C. "+firstPr.getChoiceC());
                choice_D.setText("D. "+firstPr.getChoiceD());

                choice_A.setBackgroundColor(Color.parseColor("#D3D3D3"));
                choice_B.setBackgroundColor(Color.parseColor("#D3D3D3"));
                choice_C.setBackgroundColor(Color.parseColor("#D3D3D3"));
                choice_D.setBackgroundColor(Color.parseColor("#D3D3D3"));
                Score.setText(totalPoint + "");
                verif.setEnabled(false);
                Next.setEnabled(false);
                setImage(firstPr);
            }
        }
    }

    public void gotoMenu(View view){
        Intent intent = new Intent(this, SideMenu.class);
        intent.putExtra("activity","TheoryTest");
        startActivity(intent);
    }

    public void initProblemList(){
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm myRealm = Realm.getInstance(config);

        ProblemModel pMdl = myRealm.where(ProblemModel.class).findFirst();

        if (pMdl==null) {
            ProblemModel prob = new ProblemModel();
            prob.setProbMod(1,
                    "Under what condition  can a person drive a motor cycle?",
                    "The driver must have a valid license for the same class of vehicle.",
                    "According to the conditions, laid down in the annual test.",
                    "The driver should be the registered owner or have legal control.",
                    "According to the conditions laid down in the Manufacturer’s manual.",
                    "A");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(2,
                    "Is a policeman allowed to detain without a warrant a driver who committed an offence in front of him?",
                    "Not under any condition",
                    "Yes, but only under a court certificate",
                    "Yes, if the driver doesn’t give his name and address or doesn’t present his license as required",
                    "Yes, only if the driver committed an offence that actually endangered road users",
                    "C");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(3,
                    "Define: one way street",
                    "Any path in which vehicle traffic is allowed in one direction only",
                    "Any roadway in which vehicle traffic is allowed in one direction only",
                    "Any roadway in which only motor vehicle traffic is allowed in one direction only",
                    "Any roadway in which vehicle traffic is allowed in both directions",
                    "B");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(4,
                    "What is the penalty for driving whilst disqualified?",
                    "A fine of 80,000 NIS",
                    "6 yrs in jail and 2 yrs on probation.",
                    "None, if the driver presents a valid driver’s license within 7 days",
                    "Up to 3 years in jail, plus other penalties.",
                    "D");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(5,
                    "Is it obligatory to obey the directions and signals of a municipal inspector in uniform?",
                    "No. It is only obligatory to obey as regards to parking directions within city limits",
                    "No. It is only obligatory to obey as regards to the presentation of a driver’s license",
                    "Usually not",
                    "Yes, if the directions are related to traffic control",
                    "D");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(6,
                    "When driving in reverse:",
                    "You must use another person to guide you",
                    "You must use another person to guide you on highways",
                    "You must use another person to guide you during \"lighting up time\"",
                    "You are permitted to use another person to guide you",
                    "D");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(7,
                    "When is it permitted to perform an emergency stop?",
                    "After signaling a distress sign to the vehicle behind you only",
                    "In order to prevent a road accident that cannot be prevented in any other way",
                    "When the driving speed doesn’t fit with the road conditions",
                    "When the road is distorted",
                    "B");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(8,
                    "What is the order of actions to be taken in order to stop or park?",
                    "Check if it is permitted; make a peripheral check and slow down carefully",
                    "The order of actions before parking is of no significance",
                    "Slow down, deviate, stop and signal",
                    "The order of actions doesn’t matter. The important thing is to do it carefully",
                    "A");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(9,
                    "Does drinking alcohol cause sleepiness?",
                    "Drinking alcohol causes sleepiness among young drivers only",
                    "No",
                    "Yes",
                    "Only while driving during daytime",
                    "C");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(10,
                    "The danger of skidding on a wet road is greater:",
                    "The deeper are the grooves in the tires",
                    "The more worn-out are the tires",
                    "The higher is the tire air pressure",
                    "The heavier is the vehicle",
                    "B");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(11,
                    "Wheels that are installed on the same axle must be:",
                    "Of the same manufacture date always",
                    "Of the same size and in compliance with the manufacturer’s manual",
                    "Of the same color",
                    "Of the same manufacturer",
                    "B");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(12,
                    "What is the function of the vehicle’s starter?",
                    "To turn the engine and cause it to ignite",
                    "Connect the engine to the wheels",
                    "Control the engine’s operation",
                    "Disconnect the engine from the wheels",
                    "A");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(13,
                    "In a properly functioning traffic light – how many times does the green light blinks before it turns yellow?",
                    "Four times",
                    "Three times",
                    "One time",
                    "It varies according to traffic loads.",
                    "B");
            prob.setPict("prob_trafficlight");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(14,
                    "What is the meaning of the traffic sign in the following picture?",
                    "It is a speed restriction that is intended for private vehicles only.",
                    "The vehicle’s driving speed should not be lesser than 40 KPH.",
                    "It is a speed restriction that is intended for two-wheelers and trolleys.",
                    "The vehicle’s driving speed should not exceed 40 KPH.",
                    "D");
            prob.setPict("a2");
            problemData.add(prob);

            prob = new ProblemModel();
            prob.setProbMod(15,
                    "How would you conduct yourself when approaching an intersection with a yellow flashing traffic light?",
                    "Slow down and even stop if necessary - according to the surrounding traffic.",
                    "In any case – stop before the pedestrian crossing.",
                    "Speed up before the light turns red.",
                    "Slow down and give right-of-way to vehicles coming from your left only.",
                    "A");
            prob.setPict("a3");
            problemData.add(prob);

           for (int i=0;i<problemData.size();i++){
               myRealm.beginTransaction();
               pMdl = new ProblemModel();
               pMdl =  myRealm.createObject(ProblemModel.class);
               pMdl.assignPM(problemData.get(i));
               myRealm.commitTransaction();
           }
        }
        myRealm.close();





        //problem source:
        //tgev

    }

}
