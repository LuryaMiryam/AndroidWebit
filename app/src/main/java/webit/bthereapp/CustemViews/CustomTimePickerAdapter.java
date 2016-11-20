package webit.bthereapp.CustemViews;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

/**
 * Created by User on 07/03/2016.
 */
import java.util.HashMap;
import java.util.List;

import android.widget.BaseAdapter;
import android.view.ViewGroup;
import android.view.View;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import webit.bthereapp.R;

public class CustomTimePickerAdapter extends BaseAdapter  {

    public static final int HALF_MAX_VALUE = Integer.MAX_VALUE/2;
    public final int MIDDLE;
    private LinearLayout mLinearLayout;
    private boolean display;
    private boolean flag=false;
    int height;
    private HashMap<Integer, String> scrollViews = new HashMap<>();
    private Context mContext;
    private static final String TAG = CustomTimePickerAdapter.class.getSimpleName();

    public CustomTimePickerAdapter(HashMap<Integer, String> scrollViews, Context context) {
      this(scrollViews, context, false);
    }
    public CustomTimePickerAdapter(HashMap<Integer, String> scrollViews, Context context, boolean display) {
        this.scrollViews = scrollViews;
        this.mContext = context;
        if(scrollViews.size()>0) {
            MIDDLE = HALF_MAX_VALUE - HALF_MAX_VALUE % scrollViews.size();
        }else {
            MIDDLE=0;
        }
        this.display=display;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getItem(int i) {
        return scrollViews.get(i % scrollViews.size());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
        if (view == null) {
            view = View.inflate(mContext, R.layout.custem_time_picker_row, null);
        }
        textView = (TextView) view.findViewById(R.id.time);
        mLinearLayout=(LinearLayout)view.findViewById(R.id.customTimePickerSingle);
        if(display){
            if(!flag) {
                height = mLinearLayout.getMinimumHeight() * 3;
                flag = true;
            }
            mLinearLayout.setMinimumHeight(height);
            textView.setTextColor(Color.BLACK);
        }
        textView.setText(scrollViews.get(i % scrollViews.size()));
        return view;
    }
    public String getSelected(int positionSelected) {
        return scrollViews.get(positionSelected % scrollViews.size());
    }


    public void Clear(){
        scrollViews.clear();
    }
    public void addAll(HashMap<Integer, String> scrollViews){
        this.scrollViews=scrollViews;
    }



}