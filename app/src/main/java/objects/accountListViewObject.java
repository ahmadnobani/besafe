package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.besafe.noubax.besafe.R;

import DB.Account;
import DB.DataBaseHandler;
import DB.Type;



public class accountListViewObject extends ArrayAdapter<Account> {

    private final Activity context;
    private final List<Account> accounts;
    private final DataBaseHandler db;
    public accountListViewObject(Activity context, List<Account> accounts) {
        super(context, R.layout.accounts_view_style, accounts);
        this.context=context;
        this.accounts = accounts;
        db = new DataBaseHandler(context);
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.accounts_view_style, null, true);
        Account accounts = getItem(position);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(accounts.get_username());
        imageView.setImageResource(R.drawable.ic_launcher);
        Type type = db.getType(Integer.parseInt(accounts.get_type()));
        extratxt.setText(type.get_type());

        return rowView;

    };
}