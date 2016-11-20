package  webit.bthereapp.Screens.Supplier;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import webit.bthereapp.R;


public class BlockHoursFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView mHourTV;
    private ImageButton mCloseIB;
    private static TextView mDateTV;
    private Button mBlockB;
    private RelativeLayout choose_date, choose_hour;


    public BlockHoursFragment() {
        // Required empty public constructor
    }


    public static BlockHoursFragment newInstance(String param1, String param2) {
        BlockHoursFragment fragment = new BlockHoursFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_block_hours, container, false);
        mDateTV = (TextView) view.findViewById(R.id.date);
        mHourTV = (TextView) view.findViewById(R.id.hour);
        choose_date= (RelativeLayout) view.findViewById(R.id.choose_date);
        choose_hour= (RelativeLayout) view.findViewById(R.id.choose_hour);
        choose_date.setOnClickListener(this);
        choose_hour.setOnClickListener(this);
        mHourTV.setOnClickListener(this);
        mDateTV.setOnClickListener(this);
        mCloseIB= (ImageButton) view.findViewById(R.id.close);
        mCloseIB.setOnClickListener(this);
        mBlockB= (Button) view.findViewById(R.id.block_ok);
        mBlockB.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date:
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show((getActivity().getSupportFragmentManager()), "DatePicker");
                break;
            case R.id.choose_date:
                DialogFragment newFragment_ = new SelectDateFragment();
                newFragment_.show((getActivity().getSupportFragmentManager()), "DatePicker");
                break;
            case R.id.hour:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mHourTV.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            case R.id.choose_hour:
                Calendar mcurrentTime_ = Calendar.getInstance();
                int hour_ = mcurrentTime_.get(Calendar.HOUR_OF_DAY);
                int minute_ = mcurrentTime_.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker_;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mHourTV.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour_, minute_, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            case R.id.close:
                dismiss();
                break;
            case R.id.block_ok:
                dismiss();
                break;
        }


    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, yy, mm, dd);
            datePicker.getDatePicker().setMaxDate(new Date().getTime());
            return datePicker;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {

            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {

            mDateTV.setText(day + "/" + month + "/" + year);



        }
    }





}
