package webit.bthereapp.Screens.Register.Dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import webit.bthereapp.R;


public class CreditCardFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button mNextB;
    private EditText mThreeNumbersET;
    private TextView mTypeOfCardTv;
    public static CreditCardFragment instance;

    public static CreditCardFragment getInstance() {
        if (instance == null)
            instance = new CreditCardFragment();
        return instance;
    }

    public CreditCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreditCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreditCardFragment newInstance(String param1, String param2) {
        CreditCardFragment fragment = new CreditCardFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_credit_card, container, false);
        mNextB= (Button) view.findViewById(R.id.next_pay_);
        mNextB.setOnClickListener(this);
        mThreeNumbersET= (EditText) view.findViewById(R.id.three_numbers);
        mThreeNumbersET.setFilters(setInputFilter(3,getResources().getString(R.string.too_long_time)));

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_pay_:
                //closes the credit card fragment
                CreditCardFragment.this.dismiss();

        }
    }

    private InputFilter[] setInputFilter(int max, final String message)
    {
        InputFilter[] inputFilter=new InputFilter[] {
                new InputFilter.LengthFilter(max) {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        CharSequence res = super.filter(source, start, end, dest, dstart, dend);
                        if (res != null) { // Overflow
                            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                        }
                        return res;
                    }
                }
        };
        return  inputFilter;
    }



}





