package webit.bthereapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Application.ObjectDrawerItem;
import webit.bthereapp.R;

/**
 * Created by User on 09/03/2016.
 */
public class DrawerItemCustomAdapter extends ArrayAdapter<ObjectDrawerItem> {
    Context mContext;
    int layoutResourceId;
    private  View listItem;
    ObjectDrawerItem data[] = null;
    private int mSelectedItem;
    int mRowHeight = 0;
    AbsListView.LayoutParams params;
    int color;
    ListView listView;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, ObjectDrawerItem[] data,int color,ListView listView) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
        this.color = color;
        this.listView = listView;
    }

    public void setSelectedItem(int selectedItem) {
        mSelectedItem = selectedItem;
    }

    @Override
    public int getCount() {
        if (data != null) {
            mRowHeight = listView.getHeight() / data.length;
            return data.length;
        } else
            mRowHeight=50;
            return 0;

    }
    public void no_choice() {
        mSelectedItem=-1;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        listItem = convertView;
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);
        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);
        ObjectDrawerItem folder = data[position];
        imageViewIcon.setImageResource(folder.icon);
        textViewName.setText(folder.name);
        if (mSelectedItem == position) {
            listItem.setBackgroundColor(getContext().getResources().getColor(color));
            textViewName.setBackgroundColor(getContext().getResources().getColor(color));
        }
        else {
            textViewName.setBackgroundColor(getContext().getResources().getColor(R.color.dark_blue));
            listItem.setBackgroundColor(getContext().getResources().getColor(R.color.dark_blue));
        }
//        set height to each item
        params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,mRowHeight );
        listItem.setLayoutParams(params);


        return listItem;
    }
}
