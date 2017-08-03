package com.besafe.noubax.besafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Setting_LockScreen extends AppCompatActivity {
    TextView Pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__lock_screen);
        Pin = (TextView) findViewById(R.id.Pin);
        Pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Setting_Pin.class);
                startActivity(i);

            }
        });
    }
}
