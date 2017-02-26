/*
 * Created by Sashi Novitasari on 2/23/2017.
 * Class for problems for knowledge test.
 */

package com.zhorifiandi.simmulettes;

import io.realm.RealmObject;

public class ProblemModel extends RealmObject{
    private int probID;
    private String problem;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String answer;
    private String pict;

    public int getID(){
        return probID;
    }

    public String getProblem(){
        return problem;
    }

    public String getChoiceA(){
        return choiceA;
    }
    public String getChoiceB(){
        return choiceB;
    }
    public String getChoiceC(){
        return choiceC;
    }
    public String getChoiceD(){
        return choiceD;
    }
    public String getAnswer(){
        return answer;
    }

    public  void setID(int i){
        probID =i;
    }

    public void setProblem(String prob){
        problem=prob;
    }

    public void setChoiceA(String a){
        choiceA =a;
    }
    public void setChoiceB(String b){
        choiceB =b;
    }
    public void setChoiceC(String c){
        choiceC =c;
    }
    public void setChoiceD(String d){
        choiceD =d;
    }
    public void setAnswer(String ans){
        answer=ans;
    }

    public void setPict(String a){
        pict=a;
    }
    public String getPict(){
        return pict;
    }

    public void setProbMod(int i, String prob, String A, String B,String C, String D,String ans){
        probID=i;
        problem=prob;
        choiceA=A;
        choiceB=B;
        choiceC=C;
        choiceD=D;
        answer=ans;
        pict="0";
    }

    public void assignPM(ProblemModel p){
        probID=p.getID();
        problem=p.getProblem();
        choiceA=p.getChoiceA();
        choiceB=p.getChoiceB();
        choiceC=p.getChoiceC();
        choiceD=p.getChoiceD();
        answer=p.getAnswer();
        pict=p.getPict();

    }
}
