package webit.bthereapp.Screens.Register.Dialogs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;


public class PaymentDialogeFragment extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton mCloseIB;
    private Button mSendB;
    private EditText mNumCardEt, mThreeNumbersEt, mTzEt;
    private TextView mNumCardTv, mValidityTvS, mValidityTvP, mThreeNumbersTv, mTzTv;
    String empty;
    private OnFragmentInteractionListener mListener;


    public static PaymentDialogeFragment newInstance(String param1, String param2) {
        PaymentDialogeFragment fragment = new PaymentDialogeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PaymentDialogeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_dialoge, container, false);
        mNumCardEt = (EditText) view.findViewById(R.id.numCard);
        mValidityTvP = (TextView) view.findViewById(R.id.validity);
        mThreeNumbersEt = (EditText) view.findViewById(R.id.threeNumbers);
        mTzEt = (EditText) view.findViewById(R.id.tz);
        mNumCardTv = (TextView) view.findViewById(R.id.numCardTv);
        mValidityTvS = (TextView) view.findViewById(R.id.validityTv);
        mThreeNumbersTv = (TextView) view.findViewById(R.id.threeNumbersTv);
        mTzTv = (TextView) view.findViewById(R.id.tzTv);
        mSendB = (Button) view.findViewById(R.id.send);
        mCloseIB = (ImageButton) view.findViewById(R.id.close);
        mThreeNumbersTv.setOnClickListener(this);
        mThreeNumbersEt.setOnFocusChangeListener(this);
        mNumCardEt.setOnFocusChangeListener(this);
        mNumCardTv.setOnClickListener(this);
        mTzEt.setOnFocusChangeListener(this);
        mTzTv.setOnClickListener(this);
        // mValidityTvS.setOnClickListener(this);
        mSendB.setOnClickListener(this);
        mCloseIB.setOnClickListener(this);
        mValidityTvP.setOnClickListener(this);
        mValidityTvS.setOnClickListener(this);

        empty = getString(R.string.eEmpty);
        mValidityTvP.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mValidityTvP.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mValidityTvP.setError(null);
            }
        });
//opens a date dialog and displays on the tv the selected day
        mValidityTvP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                c.set(year, monthOfYear, dayOfMonth);
                                mValidityTvP.setText(new StringBuilder()/*.append(dayOfMonth).append("/")*/.append(monthOfYear + 1).append("/").append(year));
                                // checkDate();
                            }
                        }, mYear, mMonth, mDay);
                DatePicker dp = dpd.getDatePicker();
                dp.setMinDate(c.getTimeInMillis());
                dpd.show();
            }
        });
        return view;
    }

    @Override
    public boolean OnBackPress() {
        if (!(getActivity() instanceof NavigetionLayout))
            mListener.onClickInPayment(0);
        return false;
    }

    //Checks if there were inserted three numbers
    private boolean checkThreeNumbers() {
        String numbers = mThreeNumbersEt.getText().toString();
        if (numbers.length() == 3) {
            mThreeNumbersEt.setError(null);
            return true;
        } else {
            mThreeNumbersEt.setError(getString(R.string.eNumbers));
            return false;
        }

    }

    //checks if the num card is correct
    private boolean checkNumCard() {
        String numCard = mNumCardEt.getText().toString();
        return true;
    }


    private boolean ValidateID() {
        String tz = mTzEt.getText().toString();
        // The number is too short - add leading 0000
        if (tz.length() < 9) {
            while (tz.length() < 9) {
                tz = '0' + tz;
            }
        }
        // CHECK THE ID NUMBER
        int mone = 0;
        int incNum;
        int tmpTZ = Integer.valueOf(tz);
        int[] digits = new int[9];
        // Start filling array from the end
        int i = 8;
        while (tmpTZ > 0) {
            digits[i] = tmpTZ % 10;
            tmpTZ /= 10;
            i--;
        }
        for (i = 0; i < 9; i++) {
            incNum = digits[i];
            incNum *= (i % 2) + 1;
            if (incNum > 9)
                incNum -= 9;
            mone += incNum;
        }
        if (mone % 10 == 0) {
            mTzEt.setError(null);
            return true;
        } else {
            mTzEt.setError(getString(R.string.eTz));
            return false;
        }
    }

    private void checkSend() {
        if (mNumCardEt.getText().toString().length() == 0)
            mNumCardEt.setError(empty);
        if (mThreeNumbersEt.getText().toString().length() == 0)
            mThreeNumbersEt.setError(empty);
        if (mValidityTvP.getText().toString().length() == 0)
            mValidityTvP.setError(empty);
        if (mTzEt.getText().toString().length() == 0)
            mTzEt.setError(empty);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.numCardTv:
                mNumCardEt.requestFocus();
                break;
            case R.id.threeNumbersTv:
                mThreeNumbersEt.requestFocus();
                break;
            case R.id.tzTv:
                mTzEt.requestFocus();
                break;
            case R.id.send:
                if(!(getActivity() instanceof NavigetionLayout))
                    mListener.onClickInPayment(1);
                checkSend();
                getActivity().onBackPressed();
                break;
            case R.id.close:
                if (!(getActivity() instanceof NavigetionLayout))
                    mListener.onClickInPayment(2);
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.numCard:
                checkNumCard();
                break;
            case R.id.validity:
                mValidityTvP.setError(null);
                break;
            case R.id.threeNumbers:
                checkThreeNumbers();
                break;
            case R.id.tz:
                ValidateID();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof NavigetionLayout)) {
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onClickInPayment(int b);
    }

}









