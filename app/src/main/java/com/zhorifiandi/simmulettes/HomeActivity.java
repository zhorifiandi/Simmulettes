package com.zhorifiandi.simmulettes;

import android.app.ProgressDialog;
import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
import com.zhorifiandi.simmulettes.firebase.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.zhorifiandi.simmulettes.Connection.EndPoints.URL_LOG_OUT;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private Button buttonChat;
    private Button buttonUjian;

    //defining views
    private ProgressDialog progressDialog;
    private FirebaseUser user;

    //URL to RegisterDevice.php



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        user = firebaseAuth.getCurrentUser();
        //sendTokenToServer();
        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonChat = (Button) findViewById(R.id.go_chat);
        buttonUjian = (Button) findViewById(R.id.go_ujian);
        //displaying logged in user name
        textViewUserEmail.setText("Welcome,\n"+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        buttonChat.setOnClickListener(this);
        buttonUjian.setOnClickListener(this);

        //DEBUG TOKEN
        //getting views from xml

    }

    //delete token on server
    private void logOutDevice() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging out..");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        final String email = user.getEmail();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOG_OUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            logOutDevice();
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        else if (view == buttonChat){
            startActivity(new Intent(getApplicationContext(), ChatActivity.class));
        }
        else if (view == buttonUjian) {
            startActivity(new Intent(getApplicationContext(), SideMenu.class));
        }

    }
}