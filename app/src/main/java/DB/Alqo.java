package DB;

/**
 * Created by nobani on 08/10/15.
 */
public class Alqo {
    private int _ID;
    private String _version_name , _version_algo , _add_date;
    public Alqo(int id, String version_name, String version_algo, String add_date){
        _ID = id;
        _version_name = version_name;
        _version_algo = version_algo;
        _add_date = add_date;
    }
    public int get_ID(){return _ID;}
    public String get_name(){return _version_name;}
    public String get_algorithm(){return _version_algo;}
    public String get_creatDate(){return _add_date;}
}
