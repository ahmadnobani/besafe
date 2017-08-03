package DB;

/**
 * Created by nobani on 08/10/15.
 */
public class Account {
    private int _ID;
    private String _username , _password , _type , _create , _update , _version;
    public Account(int id , String username , String password , String type , String createDate , String updateDate , String Passwordverson){
        _ID = id;
        _username = username;
        _password = password;
        _type = type;
        _create = createDate;
        _update = updateDate;
        _version = Passwordverson;
    }
    public int get_ID(){return _ID;}
    public String get_username(){return _username;}
    public String get_password(){return _password;}
    public String get_type(){return _type;}
    public String get_create(){return _create;}
    public String get_update(){return _update;}
    public String get_version(){return _version;}
}
