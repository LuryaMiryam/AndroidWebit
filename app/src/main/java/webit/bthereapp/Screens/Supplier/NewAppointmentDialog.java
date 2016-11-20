package  webit.bthereapp.Screens.Supplier;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.IdentityHashMap;

import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.Fonts.CustomLightEditText;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NewAppointmentDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewAppointmentDialog extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static CustomLightTextView date_txt;
    private CustomLightTextView hour_;
    private RelativeLayout date, hour;
    private ImageButton yn, close;
    private int state=0;
    private CustomLightButton ok;

    public static NewAppointmentDialog instance;

    public static NewAppointmentDialog getInstance() {
        if (instance == null)
            instance = new NewAppointmentDialog();
        return instance;
    }


    public NewAppointmentDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewAppointmentDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static NewAppointmentDialog newInstance(String param1, String param2) {
        NewAppointmentDialog fragment = new NewAppointmentDialog();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_appointment_dialog, container, false);
        date_txt = (CustomLightTextView) view.findViewById(R.id.date_tv);
        date = (RelativeLayout) view.findViewById(R.id.date);
        date.setOnClickListener(this);
        close = (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        ok = (CustomLightButton) view.findViewById(R.id.ok);
        ok.setOnClickListener(this);
        yn = (ImageButton) view.findViewById(R.id.yn);
        yn.setOnClickListener(this);
        hour_ = (CustomLightTextView) view.findViewById(R.id.hour_tv);
        hour = (RelativeLayout) view.findViewById(R.id.hour);
        hour_.setText("00:00");
        hour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour_.setText(selectedHour + ":" + selectedMinute);
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
        switch (v.getId()) {
            //Open dialog to select date
            case R.id.date: {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show((getActivity().getSupportFragmentManager()), "DatePicker");
                break;
            }
            case R.id.yn: {
                if(state==0) {
                    yn.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                    state=1;
                }
                else {
                    yn.setImageResource(R.drawable.supplier_galaxy_icons_x1_29);
                    state=0;
                }

                break;
            }
            case R.id.ok: {
                dismiss();
                break;
            }
            case R.id.close: {
                dismiss();
                break;
            }

        }
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private String date_str;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, yy, mm, dd);
            datePicker.getDatePicker().setMinDate(new Date().getTime());
            return datePicker;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            String mm = String.valueOf(month);
            String dd = String.valueOf(day);
            date_str = String.valueOf(year) + (month < 10 ? "0" + mm : mm) + (day < 10 ? "0" + dd : dd);
            date_txt.setText(month + "/" + day + "/" + year);
        }
    }

}
