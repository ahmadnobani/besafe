package DB;

/**
 * Created by nobani on 08/10/15.
 */
public class System {

    private int _ID;
    private String _language , _ftime , _key , _lock , _dates , _tutorial , _email;
    public System(int id, String language, String ftime, String key, String lock, String dates, String tutorial, String email){
        _ID = id;
        _language = language;
        _ftime = ftime;
        _key = key;
        _lock = lock;
        _dates = dates;
        _tutorial = tutorial;
        _email = email;
    }
    public int get_ID(){return _ID;}
    public String get_language(){return _language;}
    public String get_firstTime(){return _ftime;}
    public String get_key(){return _key;}
    public String get_lock(){return _lock;}
    public String get_date(){return _dates;}
    public String get_tutorial(){return _tutorial;}
    public String get_email(){return _email;}
}
