package  webit.bthereapp.Screens.Supplier;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import webit.bthereapp.CustemViews.CustomSpinnerLargeBlack;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.R;


public class DetailsAppointmentDialog extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout cancel, call_;

    private static CustomLightTextView date_txt, from_hour, to_hour;
    private RelativeLayout date,hour;

    private ImageButton close;

    private Button mUpdateB;


    public static DetailsAppointmentDialog instance;

    public static DetailsAppointmentDialog getInstance() {
        if (instance == null)
            instance = new DetailsAppointmentDialog();
        return instance;
    }


    public DetailsAppointmentDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsAppointmentDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsAppointmentDialog newInstance(String param1, String param2) {
        DetailsAppointmentDialog fragment = new DetailsAppointmentDialog();
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
        getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_details_appointment_dialog, container, false);

        close = (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        cancel = (LinearLayout) view.findViewById(R.id.cancel_appointment);
        cancel.setOnClickListener(this);
        call_ = (LinearLayout) view.findViewById(R.id.call);
        call_.setOnClickListener(this);

        date_txt = (CustomLightTextView) view.findViewById(R.id.date_tv);
        date = (RelativeLayout) view.findViewById(R.id.date);
        date.setOnClickListener(this);
        from_hour = (CustomLightTextView) view.findViewById(R.id.from_hour_id);

        mUpdateB = (Button) view.findViewById(R.id.update);
        mUpdateB.setOnClickListener(this);


        from_hour.setText("00:00");
        from_hour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        from_hour.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        to_hour = (CustomLightTextView) view.findViewById(R.id.to_hour_id);

        to_hour.setText("00:00");
        to_hour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        to_hour.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm;
        switch (v.getId()) {
            case R.id.date: {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show((getActivity().getSupportFragmentManager()), "DatePicker");
                break;
            }
            case R.id.call: {
                break;
            }
            case R.id.cancel_appointment:
                fm = getFragmentManager();
                CancelQweueDialogFragment newFragment = new CancelQweueDialogFragment();
                newFragment.show(fm, "dialog");
                break;

            case R.id.close:
                dismiss();
                break;
            case R.id.update:
                fm = getFragmentManager();
                UpdateAppointment updateAppointment = new UpdateAppointment();
                updateAppointment.show(fm, "dialog");
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
            String mm = String.valueOf(month);
            String dd = String.valueOf(day);
            date_txt.setText(month + "/" + day + "/" + year);
        }
    }

}
