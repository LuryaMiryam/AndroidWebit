package webit.bthereapp.CustemViews;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import webit.bthereapp.DM.FieldAndCategoryDM;
import webit.bthereapp.Entities.SysAlertsList;
import webit.bthereapp.R;

public class BussinesAreaAdapterActivity extends BaseAdapter {

    private boolean f = false;
    private boolean flag = false;
    private Context context;
    private int changePosition = -1;
    private List<FieldAndCategoryDM> fieldAndCategoryDMList, fieldAndCategoryDMList2;


    public BussinesAreaAdapterActivity(Context context, List<FieldAndCategoryDM> fieldAndCategoryDMList) {
        this.context = context;
        this.fieldAndCategoryDMList = fieldAndCategoryDMList;

        filter();
    }

    //filter data from the server
    private void filter() {
        int i = 0;
        fieldAndCategoryDMList2 = new ArrayList<>();
        if (fieldAndCategoryDMList2.size() > 0 && fieldAndCategoryDMList.size() > 0)
            fieldAndCategoryDMList2.add(fieldAndCategoryDMList.get(0));
        for (FieldAndCategoryDM fieldAndCategoryDM : fieldAndCategoryDMList) {
            f = true;
            if (f == true) {
                fieldAndCategoryDMList2.add(fieldAndCategoryDM);
            }
        }
        this.fieldAndCategoryDMList = fieldAndCategoryDMList2;
    }



    public void change_position(int position) {
        changePosition = position;
        notifyDataSetChanged();
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_bussines_area_adapter, parent, false);
            // set value into textview
            mViewHolder = new ViewHolder();
            mViewHolder.textView = (TextView) convertView.findViewById(R.id.grid_item_label);
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.grid_item_image);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.textView.setText(fieldAndCategoryDMList.get(position).getNvCategoryName());
        if (position == changePosition) {
            mViewHolder.textView.setTextColor(ContextCompat.getColor(context, R.color.color4));
            mViewHolder.imageView.setImageResource(R.drawable.full);
        }
        else {
            mViewHolder.textView.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            mViewHolder.imageView.setImageResource(R.drawable.empty);

        }

        return convertView;
    }

    @Override
    public int getCount() {
        return fieldAndCategoryDMList.size();
    }

    @Override
    public Object getItem(int position) {
        return fieldAndCategoryDMList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}