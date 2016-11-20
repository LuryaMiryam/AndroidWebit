package  webit.bthereapp.Screens.Supplier;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.nio.channels.ClosedByInterruptException;
import java.util.Calendar;
import java.util.Date;

import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AddNewCustomerDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewCustomerDialogFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static CustomLightTextView date_tv;
    private ImageButton close;
    private CustomLightButton ok;
    private EditText first,last,email;
    private CustomSpinnerPhone phone;


    public AddNewCustomerDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewCustomerDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewCustomerDialogFragment newInstance(String param1, String param2) {
        AddNewCustomerDialogFragment fragment = new AddNewCustomerDialogFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_new_customer_dialog, container, false);
        close = (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        ok = (CustomLightButton) view.findViewById(R.id.ok);
        ok.setOnClickListener(this);
        date_tv = (CustomLightTextView) view.findViewById(R.id.date);
        date_tv.setOnClickListener(this);
        first= (EditText) view.findViewById(R.id.ed_first);
        last= (EditText) view.findViewById(R.id.ed_last);
        email= (EditText) view.findViewById(R.id.ed_email);

//        phone= (CustomSpinnerPhone) view.findViewById(R.id.cus_phone);
        return view;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            date_tv.setText(day1 + "/" + month1 + "/" + year1);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Open dialog to select date
            case R.id.date: {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show((getActivity().getSupportFragmentManager()), "DatePicker");
                break;
            }
            case R.id.close: {
                dismiss();
                break;
            }
            case R.id.ok: {
                if (isValid())
                    Toast.makeText(getActivity(),getString(R.string.valid),Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }

    private boolean isValid() {
        if (first!=null&&first.length()==0){
            first.setError(getString(R.string.error_empty));
            return false;
        }
        if (last!=null&&last.length()==0){
            last.setError(getString(R.string.error_empty));
            return false;
        }
        if(!phone.isValid()) {
            return false;
        }
        if(email!=null&&email.length()>0&&!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            email.setError(getString(R.string.adrerror));
            return false;
        }
        return true;
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
            datePicker.getDatePicker().setMaxDate(new Date().getTime());
            return datePicker;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {

            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            String mm = String.valueOf(month);
            String dd = String.valueOf(day);
            date_str = String.valueOf(year) + (month < 10 ? "0" + mm : mm) + (day < 10 ? "0" + dd : dd);
            date_tv.setText(month + "/" + day + "/" + year);
        }
    }
}
