package com.zhorifiandi.simmulettes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private String sender;
    private String message;
    private TextView textSender;
    private TextView textMessage;
    private Button replyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);

        Intent intent = getIntent();
        sender = intent.getStringExtra("sender");
        message = intent.getStringExtra("message");

        textSender = (TextView) findViewById(R.id.sendercontent);
        textMessage = (TextView) findViewById(R.id.messagecontent);
        replyButton = (Button) findViewById(R.id.reply_button);

        textSender.setText(sender);
        textMessage.setText(message);

        replyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra("target", sender);
        startActivity(intent);
    }
}
