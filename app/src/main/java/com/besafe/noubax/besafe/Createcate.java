package com.besafe.noubax.besafe;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import android.app.Activity;
import DB.Account;
import DB.DataBaseHandler;
import DB.Type;
import objects.spinnerObject;

public class Createcate extends AppCompatActivity {
    EditText catName;
    Button save;
    DataBaseHandler db;
    Context c = this;
    int edit_id , is_from_account = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createcate);
        db = new DataBaseHandler(c);
        catName = (EditText) findViewById(R.id.catNameEditText);
        save = (Button) findViewById(R.id.Save);
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bundle != null) {

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    if (!(catName.getText().toString().equals(""))) {
                        if (is_from_account == 1) {
                            Type type = new Type(0, catName.getText().toString(), "", "", "");
                            boolean done = db.createAccountType(type);
                            if(done){
                                intent.putExtra("created_cat", catName.getText().toString());
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }else {
                                View view = findViewById(android.R.id.content);
                                Snackbar.make(view, getResources().getString(R.string._cat_exist), Snackbar.LENGTH_LONG).show();
                            }
                        } else {

                            Type type = new Type(bundle.getInt("edit"), catName.getText().toString(), "", "", "");
                            boolean done = db.updateType(type);
                            if(done){
                                finish();
                            }else {
                                View view = findViewById(android.R.id.content);
                                Snackbar.make(view, getResources().getString(R.string._cat_exist), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(c, getResources().getString(R.string._fill_inputs), Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (!(catName.getText().toString().equals(""))) {
                        Type type = new Type(0, catName.getText().toString(), "", "", "");
                        boolean done = db.createAccountType(type);
                        if(done){
                            finish();
                        }else {
                            View view = findViewById(android.R.id.content);
                            Snackbar.make(view, getResources().getString(R.string._cat_exist), Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(c, getResources().getString(R.string._fill_inputs), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        if(bundle != null){
            if(bundle.containsKey("edit")) {
                edit_id = bundle.getInt("edit");
                Type old_type = db.getType(edit_id);
                catName.setText(old_type.get_type());
            }else{
                is_from_account = bundle.getInt("is_from_account");
            }
        }
    }

}
