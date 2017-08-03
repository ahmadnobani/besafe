package com.besafe.noubax.besafe;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import DB.DataBaseHandler;
import DB.System;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Pin extends AppCompatActivity  implements View.OnClickListener{
    TextView o0 , o1 , o2 , o3 , o4 , o5 , o6 , o7 , o8 , o9;
    EditText pass;
    String pp12;
    ImageButton imageButton;
    DataBaseHandler DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pin);
        DB = new DataBaseHandler(this);
        System _system= DB.getSystem();
        pp12 = _system.get_lock();
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        o0 = (TextView) findViewById(R.id.o0);
        o1 = (TextView) findViewById(R.id.o1);
        o2 = (TextView) findViewById(R.id.o2);
        o3 = (TextView) findViewById(R.id.o3);
        o4 = (TextView) findViewById(R.id.o4);
        o5 = (TextView) findViewById(R.id.o5);
        o6 = (TextView) findViewById(R.id.o6);
        o7 = (TextView) findViewById(R.id.o7);
        o8 = (TextView) findViewById(R.id.o8);
        o9 = (TextView) findViewById(R.id.o9);
        pass = (EditText)findViewById(R.id.pas);
        o0.setOnClickListener(this);
        o1.setOnClickListener(this);
        o2.setOnClickListener(this);
        o3.setOnClickListener(this);
        o4.setOnClickListener(this);
        o5.setOnClickListener(this);
        o6.setOnClickListener(this);
        o7.setOnClickListener(this);
        o8.setOnClickListener(this);
        o9.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.o0:
                pass.append("0");
                ch();
                break;
            case R.id .o1:
                pass.append("1");
                ch();
                break;
            case R.id .o2:
                pass.append("2");
                ch();
                break;
            case R.id .o3:
                pass.append("3");
                ch();
                break;
            case R.id .o4:
                pass.append("4");
                ch();
                break;
            case R.id .o5:
                pass.append("5");
                ch();
                break;
            case R.id .o6:
                pass.append("6");
                ch();
                break;
            case R.id .o7:
                pass.append("7");
                ch();
                break;
            case R.id .o8:
                pass.append("8");
                ch();
                break;
            case R.id .o9:
                pass.append("9");
                ch();
                break;
            case R.id.imageButton:
                if( pass.getText().toString().length() > 0) {
                    String string = pass.getText().toString().substring(0, pass.getText().toString().length() - 1);
                    pass.setText(string);
                    ch();
                }
                break;
        }
    }
    protected void ch(){
        if(pp12.equals(pass.getText().toString())){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isi1", true); // Storing string
            editor.commit();
            finish();
        }else{

        }
    }
    @Override
    public void onBackPressed() {

    }
}
