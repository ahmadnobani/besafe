package com.besafe.noubax.besafe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import DB.Account;
import DB.DataBaseHandler;
import DB.Type;
import objects.TypeListViewObject;
import objects.accountListViewObject;

public class categoryBeSafe extends AppCompatActivity {
    EditText SearchBox;
    ListView type_listView;
    DataBaseHandler db;
    Context c = this;
    List<Type> Type_list;
    TypeListViewObject type_adabter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        db = new DataBaseHandler(this);
        Type_list = db.GetAllTypes();
        type_adabter  = new TypeListViewObject(this , Type_list);
        type_listView = (ListView) findViewById(R.id.type_listView);
        type_listView.setAdapter(type_adabter);
        SearchBox = (EditText) findViewById(R.id.cat_name);
        SearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Type_list.clear();
                Type_list.addAll(db.SearchTypes(SearchBox.getText().toString()));
                type_adabter.notifyDataSetChanged();
                type_listView.setAdapter(type_adabter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(
                R.layout.account_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        type_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* TextView username1 = (TextView) deleteDialogView.findViewById(R.id.textView2);
                TextView password1 = (TextView) deleteDialogView.findViewById(R.id.textView4);
                username1.setText(account_list.get(position).get_username());
                password1.setText(account_list.get(position).get_password());
                deleteDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();*/
            }
        });
        final View deleteDialogView2 = factory.inflate(
                R.layout.account_chose_dialog, null);
        final AlertDialog deleteDialog23 = new AlertDialog.Builder(c).create();
        deleteDialog23.setView(deleteDialogView2);
        final AlertDialog deleteDialog1 = new AlertDialog.Builder(c).create();
        type_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (Type_list.get(position).get_ID() != 1) {

                    TextView TextViewUsername = (TextView) deleteDialogView2.findViewById(R.id.textView5);
                    Button EditButton = (Button) deleteDialogView2.findViewById(R.id.editeacc);
                    Button DeleteButton = (Button) deleteDialogView2.findViewById(R.id.Deleteacc);
                    EditButton.setText("Edite");
                    DeleteButton.setText("Delete");
                    TextViewUsername.setText((Type_list.get(position).get_type()));
                    DeleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            deleteDialog1.setTitle("Delete Category ?");
                            deleteDialog1.setMessage("Are you sure ,you what to delete (" + Type_list.get(position).get_type() + ") category ?");
                            deleteDialog1.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    db.deleteType(Type_list.get(position).get_ID());
                                    Type_list.remove(Type_list.get(position));
                                    type_adabter.notifyDataSetChanged();
                                    type_listView.setAdapter(type_adabter);
                                    deleteDialog1.dismiss();
                                    deleteDialog23.dismiss();
                                }
                            });
                            deleteDialog1.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteDialog1.dismiss();
                                    deleteDialog23.dismiss();
                                }
                            });
                            deleteDialog1.show();
                        }
                    });
                    EditButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(c, Createcate.class);
                            intent.putExtra("edit", Type_list.get(position).get_ID());
                            startActivity(intent);
                            deleteDialog23.dismiss();
                        }
                    });
                    deleteDialog23.show();
                } else {
                    Toast.makeText(c , "Sorry this category cant be edit" , Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent i = new Intent(getBaseContext(), Createcate.class);
                startActivity(i);
            }
        });




}
    @Override
    public void onResume()
    {
        super.onResume();
        Type_list.clear();
        Type_list.addAll(db.GetAllTypes());
        type_adabter.notifyDataSetChanged();
        type_listView.setAdapter(type_adabter);
    }
}
