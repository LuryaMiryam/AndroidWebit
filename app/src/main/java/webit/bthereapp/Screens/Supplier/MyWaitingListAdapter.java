package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.R;

/**
 * Created by User on 01/05/2016.
 */
public class MyWaitingListAdapter extends ArrayAdapter<WaitingListItem> {
    private Context mcontext;


    List<WaitingListItem> waiting_cus = new ArrayList<>();
    private EditText mNCet;
    private RelativeLayout rr;
    private CustomBoldTextView mDayInWeekTv, mDayInMonthTv, mMonthTv, mYearTv;
    private static int a=0;

    public MyWaitingListAdapter(Context context, int resource, List<WaitingListItem> ls) {
        super(context, resource, ls);
        waiting_cus = ls;
        mcontext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        List<WaitingListItemToDay> waiting_list;
        ListView listView;


        if (convertView == null)
            convertView = inflater.inflate(R.layout.waiting_list_item, parent, false);
        mDayInWeekTv = (CustomBoldTextView) convertView.findViewById(R.id.day_in_week);
        mDayInMonthTv = (CustomBoldTextView) convertView.findViewById(R.id.day_in_month);
        mMonthTv = (CustomBoldTextView) convertView.findViewById(R.id.month);
        mYearTv = (CustomBoldTextView) convertView.findViewById(R.id.year);

        rr= (RelativeLayout) convertView.findViewById(R.id.rr);

        mDayInWeekTv.setText(waiting_cus.get(position).getmDayInWeek());
        mDayInMonthTv.setText(waiting_cus.get(position).getmDayInMonth());
        mMonthTv.setText(waiting_cus.get(position).getmMonth());
        mYearTv.setText(waiting_cus.get(position).getmYear());
        waiting_list = waiting_cus.get(position).getList();
        int i = waiting_list.size();
        listView = (ListView) convertView.findViewById(R.id.waiting_list);
        listView.setDivider(null);

        MyWaitingListToDayAdapter adapter = new MyWaitingListToDayAdapter(convertView.getContext(), R.layout.waiting_list_item, waiting_list);

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        View listItem = adapter.getView(0, null, listView);
        listItem.measure(0, 0);
        params.height = (listItem.getMeasuredHeight())*i;
        listView.setLayoutParams(params);
        listView.requestLayout();
        listView.setAdapter(adapter);
        return convertView;
    }

    public static int getA() {
        return a;
    }

    public static void setA(int a) {
        MyWaitingListAdapter.a = a;
    }
}
