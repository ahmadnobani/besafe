package objects;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import DB.Account;
import DB.DataBaseHandler;
import DB.Type;
import com.besafe.noubax.besafe.R;


public class TypeListViewObject extends ArrayAdapter<Type> {

    private final Activity context;
    private final List<Type> Types;
    private final DataBaseHandler db;
    public TypeListViewObject(Activity context, List<Type> Types) {
        super(context, R.layout.accounts_view_style, Types);
        this.context=context;
        this.Types = Types;
        db = new DataBaseHandler(context);
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.type_view_style1, null, true);
        Type Types = getItem(position);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(Types.get_type());
        imageView.setImageResource(R.drawable.ic_launcher);
        return rowView;

    };
}