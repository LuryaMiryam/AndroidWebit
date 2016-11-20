package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.CustemViews.Fonts.CustomBoldButton;
import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.R;

/**
 * Created by User on 20/04/2016.
 */
public class QweuesToCusAdapter extends ArrayAdapter<QweueCustomer> {
    private Context mcontext;


    List<QweueCustomer> qweues_cus = new ArrayList<>();
    private CustomBoldTextView mDayInWeekTv, mDayInMonthTv, mMonthTv, mYearTv;

    public QweuesToCusAdapter(Context context, int resource, List<QweueCustomer> ls) {
        super(context, resource, ls);
        qweues_cus = ls;
        mcontext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        List<QweueDay> qweues_list ;
        ListView listView;

        if (convertView == null)
            convertView = inflater.inflate(R.layout.qweues_to_cus_item, parent, false);

        mDayInWeekTv = (CustomBoldTextView) convertView.findViewById(R.id.day_in_week);
        mDayInMonthTv = (CustomBoldTextView) convertView.findViewById(R.id.day_in_month);
        mMonthTv = (CustomBoldTextView) convertView.findViewById(R.id.month);
        mYearTv = (CustomBoldTextView) convertView.findViewById(R.id.year);


        mDayInWeekTv.setText(qweues_cus.get(position).getmDayInWeek());
        mDayInMonthTv.setText(qweues_cus.get(position).getmDayInMonth());
        mMonthTv.setText(qweues_cus.get(position).getmMonth());
        mYearTv.setText(qweues_cus.get(position).getmYear());
        qweues_list = qweues_cus.get(position).getList();
        int i = qweues_list.size();
        i = i * 121;
        listView = (ListView) convertView.findViewById(R.id.qweues_list);
        listView.setDivider(null);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = i;
        listView.setLayoutParams(params);
        listView.requestLayout();

        QweuesToDayAdapter adapter = new QweuesToDayAdapter(convertView.getContext(), R.layout.qweues_to_cus_item, qweues_list);
        listView.setAdapter(adapter);

        return convertView;
    }

}
