package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.R;

/**
 * Created by User on 01/05/2016.
 */
public class MyWaitingListToDayAdapter extends ArrayAdapter<WaitingListItemToDay> {


    private Context mcontext;
    List<WaitingListItemToDay> waiting_day = new ArrayList<>();
    private LinearLayout ll;

    public static int getA() {
        return a;
    }

    public static void setA(int a) {
        MyWaitingListToDayAdapter.a = a;
    }

    private static int a = 0;

    public MyWaitingListToDayAdapter(Context context, int resource, List<WaitingListItemToDay> ls) {
        super(context, resource, ls);
        waiting_day = ls;
        mcontext = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        CustomRegularTextView name;
        CustomLightTextView hour;
        CustomLightTextView type;

        // If holder not exist then locate all view from UI file.
        if (convertView == null)
            convertView = inflater.inflate(R.layout.row_in_day_waiting_list, parent, false);
        ll = (LinearLayout) convertView.findViewById(R.id.ll);

        ViewGroup.LayoutParams params = ll.getLayoutParams();
        a = params.height;

        name = (CustomRegularTextView) convertView.findViewById(R.id.name);
        type = (CustomLightTextView) convertView.findViewById(R.id.type);
        hour = (CustomLightTextView) convertView.findViewById(R.id.hour);

        name.setText(waiting_day.get(position).getFname() + " " + waiting_day.get(position).getLname());
        type.setText(waiting_day.get(position).getType());
        hour.setText(waiting_day.get(position).getHour());


        return convertView;
    }
}
