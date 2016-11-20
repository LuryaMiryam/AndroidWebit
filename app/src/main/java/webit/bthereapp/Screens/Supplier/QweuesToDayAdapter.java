package  webit.bthereapp.Screens.Supplier;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.R;

/**
 * Created by User on 20/04/2016.
 */
public class QweuesToDayAdapter extends ArrayAdapter<QweueDay> implements View.OnClickListener {


    private Context mcontext;


    List<QweueDay> qweues_day = new ArrayList<>();
    //    private ImageButton button;
    private int  width, height;
    private EditText mNCet;
    private CustomRegularTextView mDayInWeekTv, mDayInMonthTv, mMonthTv, mYearTv;
    private View convertView;
    private int state = 0;
    private RelativeLayout mDetailsR, qweue_row;

    public QweuesToDayAdapter(Context context, int resource, List<QweueDay> ls) {
        super(context, resource, ls);
        qweues_day = ls;
        mcontext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        CustomBoldTextView hour;
        CustomRegularTextView type;
        RelativeLayout  cancel_qweue;

        // If holder not exist then locate all view from UI file.
        if (convertView == null)
            convertView = inflater.inflate(R.layout.qweues_to_day_item, parent, false);
        mDetailsR = (RelativeLayout) convertView.findViewById(R.id.details_qweue);
        mDetailsR.setOnClickListener(this);

        type = (CustomRegularTextView) convertView.findViewById(R.id.type);
        hour = (CustomBoldTextView) convertView.findViewById(R.id.hour);


        cancel_qweue = (RelativeLayout) convertView.findViewById(R.id.cancel_qweue);
        cancel_qweue.setOnClickListener(this);

        View view__ = (View) convertView.findViewById(R.id.view);
        if ((position + 1)==qweues_day.size())
            view__.setVisibility(View.GONE);
        type.setText(qweues_day.get(position).getType());
        hour.setText(qweues_day.get(position).getHour());

        WindowManager wm = (WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        qweue_row = (RelativeLayout) convertView.findViewById(R.id.qweue_row);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm;
        switch (v.getId()) {
            case R.id.details_qweue:
                fm = ((ExistsSuplierActivity) mcontext).getSupportFragmentManager();
                DetailsAppointmentDialog newFragment = new DetailsAppointmentDialog();
                newFragment.show(fm, "dialog");
                break;
            case R.id.cancel_qweue:
                fm = ((ExistsSuplierActivity) mcontext).getSupportFragmentManager();
                CancelQweueDialogFragment newFragment1 = new CancelQweueDialogFragment();
                newFragment1.show(fm, "dialog");
                break;
        }
    }
}
