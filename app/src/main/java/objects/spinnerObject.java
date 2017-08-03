package objects;

/**
 * Created by nob on 09/10/2015.
 */
public class spinnerObject {

    private  int databaseId;
    private String databaseValue;

    public spinnerObject ( int databaseId , String databaseValue ) {
        this.databaseId = databaseId;
        this.databaseValue = databaseValue;
    }

    public int getId () {
        return databaseId;
    }

    public String getValue () {
        return databaseValue;
    }

    @Override
    public String toString () {
        return databaseValue;
    }

}