package com.besafe.noubax.besafe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import DB.*;
import DB.System;

public class Setting_Pin extends AppCompatActivity {
    EditText editText;
    DataBaseHandler DB;
    Context c = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__pin);
        editText = (EditText)findViewById(R.id.editText);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        DB = new DataBaseHandler(this);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(editText.getText().length() < 3){
                        Toast.makeText(c , "Pin code Must be 4 digits or up" , Toast.LENGTH_LONG).show();
                    }else {
                        System _system = new System(1, "", "", "", editText.getText().toString(), "", "", "");
                        DB.updateSystemLock(_system);

                        Toast.makeText(c, "Pin code is ready", Toast.LENGTH_LONG).show();
                        editText.setText("");
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                        finish();
                    }
                }
                return false;
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
