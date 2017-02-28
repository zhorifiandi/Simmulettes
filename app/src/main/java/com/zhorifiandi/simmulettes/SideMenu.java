package com.zhorifiandi.simmulettes;

/**
 * Created by Sashi Novitasari on 2/25/2017.
 */


        import android.content.Intent;
        import android.os.Bundle;
        import android.app.Activity;
        import android.app.Fragment;
        import android.app.FragmentManager;
        import android.support.v4.widget.DrawerLayout;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.RelativeLayout;

        import io.realm.Realm;
        import io.realm.RealmConfiguration;

public class SideMenu extends Activity {

    String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    RelativeLayout dCont;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_menu);
        initScoreDB();

        menu = new String[]{"Home","Driving Test Simulation","Send a Message"};
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dList = (ListView) findViewById(R.id.left_drawer);
        dCont = (RelativeLayout) findViewById(R.id.left_cont);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu);
        dList.setAdapter(adapter);
        dList.setSelector(android.R.color.holo_blue_dark);

        Intent intent = getIntent();
        Fragment defaultFrag=null;

       if (intent.getExtras()==null) {
           defaultFrag = new SimulationMainMenu();
       }else{
           String fromAct = intent.getStringExtra("activity");
           if (fromAct.equals("TheoryTest")||fromAct.equals("TheoryTestResult")) defaultFrag = new TheoryTestMainMenu();
           else defaultFrag = new SimulationMainMenu();

       }
        FragmentManager frgm = getFragmentManager();
        frgm.beginTransaction().replace(R.id.content_frame, defaultFrag).commit();

        dList.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                Fragment detail=null;
                FragmentManager fragmentManager = getFragmentManager();
                Bundle args = new Bundle();

                dLayout.closeDrawers();
                args.putString("Menu", menu[position]);

                if(menu[position].equals("Driving Test Simulation")) {//Test Simulation
                    detail = new SimulationMainMenu();
                    detail.setArguments(args);
                    if (detail!=null) fragmentManager.beginTransaction().replace(R.id.content_frame, detail).commit();
                }else if (menu[position].equals("Home")){            //Friend list
                    Intent intent = new Intent(getApplication(), HomeActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplication(), ChatActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    public void initScoreDB(){
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm myRealm = Realm.getInstance(config);

        LastScore lscore = myRealm.where(LastScore.class).findFirst();

        if (lscore==null) {
            myRealm.beginTransaction();
            lscore = myRealm.createObject(LastScore.class);
            lscore.setID(0);
            lscore.setPractice(0);
            lscore.setSimulation(0);
            myRealm.commitTransaction();
            myRealm.setDefaultConfiguration(config);
        }

        myRealm.close();
    }
}
