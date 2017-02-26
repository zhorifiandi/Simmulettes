package com.zhorifiandi.simmulettes;

import io.realm.RealmObject;

/**
 * Created by Sashi Novitasari on 2/24/2017.
 */

public class LastScore extends RealmObject{
    private int theoryTestSimulation;
    private int theoryTestPractice;
    private int id;

    public LastScore(){}

    public void setID(int i){id=i;}

    public int getId() { return id;}

    public int getSimulationScore() {return theoryTestSimulation;}

    public int getSimulationPractice() {return theoryTestPractice;}

    public void setSimulation(int s){ theoryTestSimulation=s;}

    public void setPractice(int s){ theoryTestPractice=s;}
}
