package DB;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.TelephonyManager;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import objects.Encryption;
import objects.spinnerObject;

/**
 * Created by nobani on 08/10/15.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private SQLiteDatabase mDefaultWritableDatabase = null;
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "BeSafe" ,
    /* ACCOUNT TABLE */
    TABLE_ACCOUNTS = "accounts" ,
    ACCOUNT_ID = "id",
    ACCOUNT_USERNAME = "username" ,
    ACCOUNT_PASSWORD = "password" ,
    ACCOUNT_TYPE = "account_type" ,
    ACCOUNT_CREATE_DATE = "create_date" ,
    ACCOUTN_EDITE_DATE = "edite_date" ,
    ACCOUNT_PASSWORD_METHOD = "pass_version" ,
    /*ACCOUNT TYPE */
    TABLE_TYPE = "account_type" ,
    TYPE_ID = "id" ,
    TYPE_NAME = "account_name" ,
    TYPE_URL = "account_url" ,
    TYPE_IMAGE = "account_image" ,
    TYPE_DATE = "add_date" ,
    /*ACCOUNT ALGORITHM */
    TABLE_ALGO = "pass_version" ,
    ALGO_ID = "id" ,
    ALGO_METHOD = "version_algo" ,
    ALGO_DATE = "add_date" ,
    ALGO_NAME = "version_name",
    /* System Setting Table */
    TABLE_SYSTEM = "system",
    SYSTEM_ID = "dsiw1",
    SYSTEM_LANGUAGE = "kkweidj",
    SYSTEM_IS_FIRST_TIME = "ddkk1",
    SYSTEM_KEY = "dss2",
    SYSTEM_LOCK = "dd1",
    SYSTEM_START_DATE = "sdds2",
    SYSETM_TUTORIAL = "siw12",
    SYSTEM_EMAIL = "em1i";
     Context _context;
    private Encryption encryption;
    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
        encryption = Encryption.getDefault(GetPI(), "Salt", new byte[16]);
        if(getAlgoCount() == 0){
            createAccountAlgo(new Alqo(0 , "v1" , "First Alpha in company with capetal letter then (nob) then (*) after that the number of aphas in comapny name" , ""));
        }
        if(getTypeCount() == 0){
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SYSTEM_LANGUAGE , "");
            values.put(SYSTEM_IS_FIRST_TIME , "0");
            values.put(SYSTEM_KEY , GetPI());
            values.put(SYSTEM_LOCK, "none");
            values.put(SYSTEM_START_DATE, getDateTime(null));
            values.put(SYSETM_TUTORIAL, "");
            values.put(SYSTEM_EMAIL, "");
            db.insert(TABLE_SYSTEM, null, values);

            createAccountType(new Type(0, "Default", "", "" , ""));
            createAccountType(new Type(0, "Google", "", "" , ""));
            createAccountType(new Type(0, "FaceBook", "", "" , ""));
            createAccountType(new Type(0, "Twitter", "", "" , ""));
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.mDefaultWritableDatabase = db;
         String ACCOUNT_SQL = "CREATE TABLE `"+TABLE_ACCOUNTS+"` (\n" +
                "  `"+ACCOUNT_ID+"` INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                "  `"+ACCOUNT_USERNAME+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+ACCOUNT_PASSWORD+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+ACCOUNT_TYPE+"` INTEGER NULL DEFAULT NULL,\n" +
                "  `"+ACCOUNT_CREATE_DATE+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+ACCOUTN_EDITE_DATE+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+ACCOUNT_PASSWORD_METHOD+"` INTEGER NULL DEFAULT NULL\n" +
                "   \n" +
                ");";
        db.execSQL(ACCOUNT_SQL);
        String TYPE_SQL = "CREATE TABLE `"+TABLE_TYPE+"` (\n" +
                "  `"+TYPE_ID+"` INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                "  `"+TYPE_NAME+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+TYPE_URL+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+TYPE_IMAGE+"` INTEGER NULL DEFAULT NULL,\n" +
                "  `"+TYPE_DATE+"` MEDIUMTEXT NULL DEFAULT NULL\n" +
                "  \n" +
                ");\n";
        db.execSQL(TYPE_SQL);
        String ALGO_SQL = "CREATE TABLE `"+TABLE_ALGO+"` (\n" +
                "  `"+ALGO_ID+"` INTEGER PRIMARY KEY AUTOINCREMENT  ,\n" +
                "  `"+ALGO_NAME+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+ALGO_METHOD+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+ALGO_DATE+"` MEDIUMTEXT NULL DEFAULT NULL\n" +
                "  \n" +
                ");";
        db.execSQL(ALGO_SQL);
        String SYSTEM_SQL = "CREATE TABLE `"+TABLE_SYSTEM+"` (\n" +
                "  `"+SYSTEM_ID+"` INTEGER PRIMARY KEY AUTOINCREMENT  ,\n" +
                "  `"+SYSTEM_LANGUAGE+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+SYSTEM_IS_FIRST_TIME+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+SYSTEM_KEY+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+SYSTEM_LOCK+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+SYSTEM_EMAIL+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+SYSTEM_START_DATE+"` MEDIUMTEXT NULL DEFAULT NULL,\n" +
                "  `"+SYSETM_TUTORIAL+"` MEDIUMTEXT NULL DEFAULT NULL\n" +
                "  \n" +
                ");";
        db.execSQL(SYSTEM_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.mDefaultWritableDatabase = db;
        db.execSQL("DROP TABLE IF EXISTS `" + TABLE_ACCOUNTS + "`");
        db.execSQL("DROP TABLE IF EXISTS `" + TABLE_TYPE + "`");
        db.execSQL("DROP TABLE IF EXISTS `" + TABLE_ALGO + "`");
        db.execSQL("DROP TABLE IF EXISTS `" + TABLE_SYSTEM + "`");
        onCreate(db);
    }
    public String getDateTime(String number) {
        DateFormat dateF = DateFormat.getDateTimeInstance();
        if(number == null){
            return  java.lang.System.currentTimeMillis()+"";
        }else {
            return (new SimpleDateFormat("MMM dd,yyyy HH:mm").format(new Date(Long.parseLong(number))));
        }
    }
    //////////////////////////// ACCOUNT methods ///////////////////////////////////////
    public void createAccount(Account account){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_USERNAME , account.get_username());
        values.put(ACCOUNT_PASSWORD , account.get_password());
        values.put(ACCOUNT_TYPE, account.get_type());
        values.put(ACCOUNT_CREATE_DATE, getDateTime(null));
        values.put(ACCOUTN_EDITE_DATE, account.get_update());
        values.put(ACCOUNT_PASSWORD_METHOD, account.get_version());
        db.insert(TABLE_ACCOUNTS, null, values);
        //db.close();
    }
    public Account getAccount(int id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE_ACCOUNTS ,
                new String[]{ACCOUNT_ID,
                        ACCOUNT_USERNAME ,
                        ACCOUNT_PASSWORD ,
                        ACCOUNT_TYPE ,
                        ACCOUNT_CREATE_DATE ,
                        ACCOUTN_EDITE_DATE ,
                        ACCOUNT_PASSWORD_METHOD} ,
                ACCOUNT_ID + "=?" ,
                new String[]{String.valueOf(id)} ,
                null , null ,null ,null);
        if(c != null)
            c.moveToFirst();
        Account account = new Account(Integer.parseInt(c.getString(0)) , c.getString(1) , c.getString(2) , c.getString(3) , c.getString(4) , c.getString(5) , c.getString(6));
        return account;
    }
    public void deleteAccount(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_ACCOUNTS, ACCOUNT_ID + "=?", new String[]{String.valueOf(id)});
        //db.close();
    }
    public int getAccountsCount(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS, null);
        return c.getCount();
    }
    public int updateAccount(Account account){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_USERNAME , account.get_username());
        values.put(ACCOUNT_PASSWORD , account.get_password());
        values.put(ACCOUNT_TYPE , account.get_type());
        values.put(ACCOUNT_CREATE_DATE , account.get_create());
        values.put(ACCOUTN_EDITE_DATE, getDateTime(null));
        values.put(ACCOUNT_PASSWORD_METHOD, account.get_version());
        int returns = db.update(TABLE_ACCOUNTS, values, ACCOUNT_ID + "=?", new String[]{String.valueOf(account.get_ID())});
        //db.close();
        return returns;
    }
    public List<Account> getAllAccounts(String where){
        SQLiteDatabase db = getWritableDatabase();
        List<Account> Accounts = new ArrayList<Account>();
        Cursor c;
        if(where != null){
            c = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS +" "+ where +" ORDER BY "+ACCOUNT_ID +" DESC", null);
        }else {
            c = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS +" ORDER BY "+ACCOUNT_ID+" DESC", null);
        }
        if(c != null){
            if(c.moveToFirst()){
                do{
                    Account account = new Account(Integer.parseInt(c.getString(0)) , c.getString(1) , c.getString(2) , c.getString(3) , c.getString(4) , c.getString(5) , c.getString(6));
                    Accounts.add(account);
                }while (c.moveToNext());
            }
            return Accounts;
        }else{
            return null;
        }
    }
     //////////////////////////// TYPE methods ///////////////////////////////////////

    public boolean createAccountType(Type type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TYPE_NAME , type.get_type());
        values.put(TYPE_URL , type.get_url());
        values.put(TYPE_IMAGE , type.get_image());
        values.put(TYPE_DATE, getDateTime(null));
        List<Type> serachre = SearchTypes(type.get_type());
        if(serachre.size() == 0){
            db.insert(TABLE_TYPE, null, values);
            return true;
        }else{
            return false;
        }
    }
    public Type getType(int id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE_TYPE ,
                new String[]{TYPE_ID,
                        TYPE_NAME ,
                        TYPE_URL ,
                        TYPE_IMAGE ,
                        TYPE_DATE } ,
                TYPE_ID + "=?" ,
                new String[]{String.valueOf(id)} ,
                null , null ,null ,null);
        if(c != null)
            c.moveToFirst();
        Type type = new Type(Integer.parseInt(c.getString(0)) , c.getString(1) , c.getString(2) , c.getString(3) , c.getString(4));
        return type;
    }
    public void deleteType(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TYPE, TYPE_ID + "=?", new String[]{String.valueOf(id)});
        List<Account> account_list = getAllAccounts("where " + ACCOUNT_TYPE + " = " + id);
        for(int i = 0 ; i < account_list.size() ; i++){
            Account a = account_list.get(i);
            Account newa = new Account(a.get_ID() , a.get_username() , a.get_password() , "1" ,a.get_create() , a.get_update(), a.get_version());
            updateAccount(newa);
        }
        //db.close();
    }
    public List<Type> GetAllTypes(){
        List<Type> data = new ArrayList<Type>();
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE;
        Cursor c = db.rawQuery(selectQuery, null);
        if ( c.moveToFirst () ) {
            do {
                data.add ( new Type (Integer.parseInt(c.getString(0)) , c.getString(1) , c.getString(2) , c.getString(3) , c.getString(4) ) );
            } while (c.moveToNext());
        }
        c.close();
        return data;
    }
    public List<Type> SearchTypes(String Search_key){
        List<Type> data = new ArrayList<Type>();
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE +" where "+TYPE_NAME+" LIKE '%"+Search_key +"%'";
        Cursor c = db.rawQuery(selectQuery, null);
        if ( c.moveToFirst () ) {
            do {
                data.add ( new Type (Integer.parseInt(c.getString(0)) , c.getString(1) , c.getString(2) , c.getString(3) , c.getString(4) ) );
            } while (c.moveToNext());
        }
        c.close();
        return data;
    }
    public List<spinnerObject> SpinnerGetAllTypes(){
        List<spinnerObject> data = new ArrayList<spinnerObject>();
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE;
        Cursor c = db.rawQuery(selectQuery, null);
        if ( c.moveToFirst () ) {
            do {
                data.add ( new spinnerObject (Integer.parseInt(c.getString(0)), c.getString(1) ) );
            } while (c.moveToNext());
        }
        c.close();
        return data;
    }
    public int getTypeCount(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TYPE, null);
        return c.getCount();
    }
    public boolean updateType(Type type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TYPE_NAME , type.get_type());
        values.put(TYPE_URL , type.get_url());
        values.put(TYPE_IMAGE , type.get_image());
        values.put(TYPE_DATE, type.get_createDate());
        List<Type> serachre = SearchTypes(type.get_type());
        if(serachre.size() == 0){
            db.update(TABLE_TYPE, values, TYPE_ID + "=?", new String[]{String.valueOf(type.get_ID())});
            return true;
        }else{
            return false;
        }
        //db.close();
    }
    //////////////////////////// PASSWORD ALGORITHM TYPE methods ///////////////////////////////////////
    public void createAccountAlgo(Alqo algo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ALGO_NAME , algo.get_name());
        values.put(ALGO_METHOD , algo.get_algorithm());
        values.put(ALGO_DATE, algo.get_creatDate());
        db.insert(TABLE_ALGO, null, values);
        //db.close();
    }
    public Alqo getAlgo(int id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE_ALGO,
                new String[]{ALGO_ID,
                        ALGO_NAME,
                        ALGO_METHOD,
                        ALGO_DATE},
                ALGO_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        if(c != null)
            c.moveToFirst();
        Alqo algo = new Alqo(Integer.parseInt(c.getString(0)) , c.getString(1) , c.getString(2) , c.getString(3));
        return algo;
    }
    public int getAlgoCount(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ALGO, null);
        return c.getCount();
    }
    public List<spinnerObject> SpinnerGetAllAlgo(){
        List<spinnerObject> data = new ArrayList<spinnerObject>();
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_ALGO;
        Cursor c = db.rawQuery(selectQuery, null);
        if ( c.moveToFirst () ) {
            do {
                data.add ( new spinnerObject (Integer.parseInt(c.getString(0)), c.getString(1) ) );
            } while (c.moveToNext());
        }
        c.close();
        return data;
    }
    ///////////////////////////// System /////////////////////////
    public void updateSystemLock(System system){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SYSTEM_KEY , system.get_key());
        if(system.get_lock() == ""){
            values.put(SYSTEM_LOCK , system.get_lock());
        }else{
            values.put(SYSTEM_LOCK , system.get_lock());
            //values.put(SYSTEM_LOCK ,encryption.encryptOrNull(system.get_lock()));
        }
        db.update(TABLE_SYSTEM, values, SYSTEM_ID + "=?", new String[]{String.valueOf(system.get_ID())});
    }
    public System getSystem(){
        int id = 1;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE_SYSTEM ,
                new String[]{SYSTEM_ID,
                        SYSTEM_LANGUAGE ,
                        SYSTEM_IS_FIRST_TIME ,
                        SYSTEM_KEY ,
                        SYSTEM_LOCK ,
                        SYSTEM_EMAIL ,
                        SYSTEM_START_DATE , SYSETM_TUTORIAL} ,
                SYSTEM_ID + "=?" ,
                new String[]{String.valueOf(id)} ,
                null , null ,null ,null);
        if(c != null)
            c.moveToFirst();
        System system = new System(Integer.parseInt(c.getString(0)) , c.getString(1) , c.getString(2) , c.getString(3) , c.getString(4) , c.getString(5) , c.getString(6) , c.getString(7));
        return system;
    }
    ///////////////////////////// Other /////////////////////////
    @Override
    public SQLiteDatabase getWritableDatabase() {
        final SQLiteDatabase default_db;
        if(mDefaultWritableDatabase != null){
            default_db = mDefaultWritableDatabase;
        } else {
            default_db = super.getWritableDatabase();
        }
        return default_db;
    }
    private String GetPI(){
        if(checkWriteExternalPermission()) {
            final TelephonyManager tm = (TelephonyManager) _context.getSystemService(Context.TELEPHONY_SERVICE);
            final String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(_context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            return deviceUuid.toString();
        }else{
            return "kkkd1324cw";
        }
    }
    private boolean checkWriteExternalPermission()
    {

        String permission = "android.permission.READ_PHONE_STATE";
        int res = _context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}
