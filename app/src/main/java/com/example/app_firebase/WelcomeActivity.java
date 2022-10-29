package com.example.app_firebase;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tWelcome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tWelcome = (TextView) findViewById(R.id.tWelcome);
        String theUser = getIntent().getExtras().get("WelcomeUser").toString();
        tWelcome.setText("Bienvenid@ %s".format(theUser));
    }


}
