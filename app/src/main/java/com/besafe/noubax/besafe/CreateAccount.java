package com.besafe.noubax.besafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DB.Type;
import objects.spinnerObject;
import DB.Account;
import DB.DataBaseHandler;

public class CreateAccount extends AppCompatActivity {
    EditText username , password;
    Spinner type , algo;
    Button save,AddCategory;
    DataBaseHandler db;
    Context c = this;
    int edit_id;
    List<spinnerObject> list1;
    ArrayAdapter<spinnerObject> typeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        db = new DataBaseHandler(c);
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
        AddCategory = (Button) findViewById(R.id.AddCategory);
        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Createcate.class);
                i.putExtra("is_from_account", 1);
                startActivityForResult(i,1);
            }
        });
        type = (Spinner) findViewById(R.id.spinnerType);
        algo = (Spinner) findViewById(R.id.spinnerAlgo);
        save = (Button) findViewById(R.id.buttonSave);
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        list1 = db.SpinnerGetAllTypes();
        if(list1.size() == 0){

        }
        typeAdapter = new ArrayAdapter<spinnerObject>(this,
                        android.R.layout.simple_spinner_item,list1);
        type.setAdapter(typeAdapter);
        final List<spinnerObject> list2 = db.SpinnerGetAllAlgo();
        ArrayAdapter<spinnerObject> typeAdapter2 = new ArrayAdapter<spinnerObject>(this,
                android.R.layout.simple_spinner_item,list2);
        algo.setAdapter(typeAdapter2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type_s = String.valueOf(( (spinnerObject) type.getSelectedItem () ).getId());
                String algo_s = String.valueOf(((spinnerObject) algo.getSelectedItem()).getId());
                Intent i = new Intent(getBaseContext(), Main.class);
                if(bundle != null){
                    if(!(username.getText().toString().equals("")) && !(password.getText().toString().equals(""))) {
                        Account account = new Account(bundle.getInt("edit"), username.getText().toString(), password.getText().toString(), type_s, "", "", algo_s);
                        db.updateAccount(account);
                        startActivity(i);
                    }else{
                        Toast.makeText(c , "You have to fill all inputs" , Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(!(username.getText().toString().equals("")) && !(password.getText().toString().equals(""))) {
                        Account account = new Account(0, username.getText().toString(), password.getText().toString(), type_s, "", "", algo_s);
                        db.createAccount(account);
                        startActivity(i);
                    }else{
                            Toast.makeText(c , "You have to fill all inputs" , Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        if(bundle != null){
            edit_id = bundle.getInt("edit");
            Account old_account = db.getAccount(edit_id);
            username.setText(old_account.get_username());
            password.setText(old_account.get_password());
            Type getting_type = db.getType(Integer.parseInt(old_account.get_type()));
           //type.setSelection(selectSpinnerItemByValue(list1 , getting_type.get_type()));

        }


    }
    String returnValue = null;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == Activity.RESULT_OK) {
                    returnValue = data.getStringExtra("created_cat");

                    Log.d("LogArranger" , "onActivityResult");
                }
    }
    public int selectSpinnerItemByValue( List<spinnerObject> list , String Item)
    {
        int returnid = 0;
        int searchListLength = list.size();
        for (int i = 0; i < searchListLength; i++) {
            if (list.get(i).getValue().contains(Item)) {
                returnid = i;
            }
        }
        return returnid;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        list1.clear();
        list1.addAll(db.SpinnerGetAllTypes());
        typeAdapter.notifyDataSetChanged();
        type.setAdapter(typeAdapter);
        Log.d("LogArranger" , "onResume");
        if (returnValue != null){
            type.setSelection(selectSpinnerItemByValue(list1 , returnValue));
        }

    }
}
