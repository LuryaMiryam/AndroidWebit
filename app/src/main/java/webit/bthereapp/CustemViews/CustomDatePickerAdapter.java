package webit.bthereapp.CustemViews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import webit.bthereapp.R;

/**
 * Created by User on 07/04/2016.
 */
public class CustomDatePickerAdapter extends ArrayAdapter<String> {
    List<String> strings= new ArrayList<>();
    public static final int HALF_MAX_VALUE = Integer.MAX_VALUE/2;
    public final int MIDDLE;


    public CustomDatePickerAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        strings=objects;
        if(strings.size()>0) {
            MIDDLE = HALF_MAX_VALUE - HALF_MAX_VALUE % strings.size();
        }else {
            MIDDLE=0;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_date_item, parent, false);
            holder = new ViewHolder();
            holder.txt = (TextView) convertView.findViewById(R.id.txt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt.setText(strings.get(position % strings.size()));
        return convertView;
    }

    public class ViewHolder {
        TextView txt;
    }

    public String getSelected(int positionSelected) {
        return strings.get(positionSelected % strings.size());
    }


    @Override
    public int getCount()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getItem(int position)
    {
        return strings.get(position % strings.size());
    }
}
