package webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Locale;

import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.R;

/**
 * Created by user on 21/04/2016.
 */
public class LanguagesAdapter extends BaseAdapter {

    public int selected = -1;
    String[] languages;
    Context mContext;

    public LanguagesAdapter(Context context, String[] a) {

        languages = a;
        mContext = context;
        String locale = Locale.getDefault().getDisplayLanguage();
        if (locale.equals(a[0]))
            selected = 0;
        else if (locale.equals(a[2]))
            selected = 2;
        else if (locale.equals(a[3]))
            selected = 3;
        else
            selected = 1;
    }


    @Override
    public int getCount() {
        return languages.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.languge_row, null);
        TextView languageTV = (TextView) convertView.findViewById(R.id.language);
        languageTV.setText(languages[position]);

        if (position == selected) {
            languageTV.setBackgroundColor(mContext.getResources().getColor(R.color.color3));
            languageTV.setTextColor(mContext.getResources().getColor(R.color.color_white));
        } else {
            languageTV.setTextColor(mContext.getResources().getColor(R.color.black));
            languageTV.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = position;
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

}
