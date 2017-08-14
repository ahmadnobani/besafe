package objects;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;
import java.util.Locale;

import DB.DataBaseHandler;
import DB.System;

/**
 * Created by amnoubax on 8/8/2017.
 */

public class AppGeneral {
    Context c;
    public DataBaseHandler db;
    private boolean isDebugging = true; // if true all logs will appears in logs
    public String email = "dev.ahmad.b@gmail.com";
    String Lang;
    public AppGeneral(Context C){
        c = C;
        InitMethod();
    }
    public void InitMethod(){
        db = DBConnect();
        SetLang();
    }
    public DataBaseHandler DBConnect(){
        return  new DataBaseHandler(c);
    }

    // ar - en
    public void SetLang(){
        System sys = db.getSystem();
        if(sys.get_language() == "" && !sys.get_language().equals(Locale.getDefault().getLanguage())){
            Lang = Locale.getDefault().getLanguage();
            db.updateSystemLanguage(Lang);
        }else{
            Lang = sys.get_language();
            db.updateSystemLanguage(Lang);
        }
        PrintLogs("Lang" , Lang , 4);
        PrintLogs("Lang" , "----"+Locale.getDefault().getLanguage() , 4);
            SharedPreferences shp = c.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
            String language = shp.getString("Language", Lang);
            Locale myLocale = new Locale(language);
            Configuration config = new Configuration();
            config.setLocale(myLocale);
            c.getResources().getConfiguration().locale.getDisplayName();
            c.getResources().updateConfiguration(config, c.getResources().getDisplayMetrics());
    }
    // to Print in consol logs ,
    // TAG : Log key <Method Name>
    // context : logs content
    // type : 1.  Warning , 2. Error , 3. Debugging , 4.Info
    public void PrintLogs(String TAG , String lang , int type){
        if(isDebugging){
            switch (type){
                case 1:
                    Log.w(TAG , lang);
                    break;
                case 2:
                    Log.e(TAG , lang);
                    break;
                case 3:
                    Log.d(TAG , lang);
                    break;
                case 4:
                    Log.i(TAG , lang);
                    break;
            }
        }
    }
}
