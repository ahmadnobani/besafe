package com.besafe.noubax.besafe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.provider.Settings.Secure;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import DB.*;
import objects.Encryption;
import objects.accountListViewObject;
public class Main extends AppCompatActivity {
    ListView accounts;
    DataBaseHandler db;
    Context c = this;
    accountListViewObject account_adabter;
    List<Account> account_list;
    private static final int REQ_CREATE_PATTERN = 1;
    private String android_id;
    public Encryption encryption;
    SharedPreferences pref;
    boolean isCodeSecurityOpen = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String android_id = Secure.getString(c.getContentResolver(),
                Secure.ANDROID_ID);
        db = new DataBaseHandler(this);
        encryption = Encryption.getDefault(GetPI(), "Salt", new byte[16]);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

        if(!pref.getBoolean("isi1" , false)) {
            showLockScreen(1);
        }
        // String encrypted = encryption.encryptOrNull("top secret string");

        //String decrypted = encryption.decryptOrNull(encrypted);
        //Toast.makeText(c ,encrypted+" \n\n"+decrypted , Toast.LENGTH_LONG).show();
       /* Intent intent = new Intent(this, pattren_lock.class);
       LockPatternActivity.IntentBuilder.newPatternCreator(c)
                .startForResult(, REQ_CREATE_PATTERN);*/

        account_list = db.getAllAccounts(null);
        account_adabter  = new accountListViewObject(this , account_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        accounts = (ListView) findViewById(R.id.accLists);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        accounts.setAdapter(account_adabter);
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(
                R.layout.account_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        accounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView username1 = (TextView) deleteDialogView.findViewById(R.id.textView2);
                TextView password1 = (TextView) deleteDialogView.findViewById(R.id.textView4);
                username1.setText(account_list.get(position).get_username());
                password1.setText(account_list.get(position).get_password());
                deleteDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
            }
        });
        final View deleteDialogView2 = factory.inflate(
                R.layout.account_chose_dialog , null);
        final AlertDialog deleteDialog23 = new AlertDialog.Builder(c).create();
        deleteDialog23.setView(deleteDialogView2);
        final AlertDialog deleteDialog1 = new AlertDialog.Builder(c).create();
        accounts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                TextView TextViewUsername = (TextView) deleteDialogView2.findViewById(R.id.textView5);
                Button EditButton = (Button) deleteDialogView2.findViewById(R.id.editeacc);
                Button DeleteButton = (Button) deleteDialogView2.findViewById(R.id.Deleteacc);
                TextViewUsername.setText((account_list.get(position).get_username()));
                DeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteDialog1.setTitle("Delete Account ?");
                        deleteDialog1.setMessage("Are you sure ,you what to delete (" + account_list.get(position).get_username() + ") account ?");
                        deleteDialog1.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.deleteAccount(account_list.get(position).get_ID());
                                account_list.remove(account_list.get(position));
                                account_adabter.notifyDataSetChanged();
                                accounts.setAdapter(account_adabter);
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
                        Intent intent = new Intent(c, CreateAccount.class);
                        intent.putExtra("edit", account_list.get(position).get_ID());
                        startActivity(intent);
                        deleteDialog23.dismiss();
                    }
                });
                deleteDialog23.show();
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), CreateAccount.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(!pref.getBoolean("isi1" , false)) {
            showLockScreen(1);
        }
        account_list.clear();
        account_list.addAll(db.getAllAccounts(null));
        account_adabter.notifyDataSetChanged();
        accounts.setAdapter(account_adabter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cat) {
            Intent i = new Intent(getBaseContext(), categoryBeSafe.class);
            startActivity(i);
            return true;
        }else if (id == R.id.action_settings) {
            Intent i = new Intent(getBaseContext(), Setting.class);
            startActivity(i);
            return true;
        }else if (id == R.id.action_about) {
            Intent i = new Intent(getBaseContext(), About.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, "Please click BACK again to exit", Snackbar.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    private String GetPI(){
        if(checkWriteExternalPermission()) {
            final TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
            final String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            return deviceUuid.toString();
        }else{
            return "kkkd1324cw";
        }
    }
    @Override
    public void onDestroy (){
        super.onDestroy();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isi1", false); // Storing string
        isCodeSecurityOpen = false;
        editor.commit();
    }
    public void onPause(){
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isi1", true); // Storing string
        //isCodeSecurityOpen = false;
        editor.commit();
    }
    private boolean checkWriteExternalPermission()
    {

        String permission = "android.permission.READ_PHONE_STATE";
        int res = c.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    private void showLockScreen(int type){
        if(type == 1){
            DB.System _system= db.getSystem();
            String pp12 = _system.get_lock().toString();
            if(!pp12.equals("none") && isCodeSecurityOpen) {
                Intent i = new Intent(getBaseContext(), Pin.class);
                startActivity(i);
            }
        }
    }
}
