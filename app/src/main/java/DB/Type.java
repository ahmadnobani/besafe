package DB;

/**
 * Created by nobani on 08/10/15.
 */
public class Type {
    private int _ID;
    private String _account_name , _account_url , _account_image , _add_date;
    public Type(int id, String account_name, String account_url, String account_image, String add_date){
        _ID = id;
        _account_name = account_name;
        _account_url = account_url;
        _account_image = account_image;
        _add_date = add_date;
    }
    public int get_ID(){return _ID;}
    public String get_type(){return _account_name;}
    public String get_url(){return _account_url;}
    public String get_image(){return _account_image;}
    public String get_createDate(){return _add_date;}
}
