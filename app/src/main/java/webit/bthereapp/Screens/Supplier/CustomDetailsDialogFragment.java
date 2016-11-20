package  webit.bthereapp.Screens.Supplier;


import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.Entities.CustomerObj;
import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CustomDetailsDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomDetailsDialogFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button mUpdateB;
    private ImageButton close;
    LinearLayout mDeleteL;
    private CustomBoldTextView name;
    private CustomLightTextView adress, mail, phone, date, massege;
    private ImageView image;
    private CustomerObj customerObj;

    public CustomDetailsDialogFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomDetailsDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomDetailsDialogFragment newInstance(String param1, String param2) {
        CustomDetailsDialogFragment fragment = new CustomDetailsDialogFragment();
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
            customerObj = (CustomerObj) getArguments().getSerializable("customer");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setStyle(STYLE_NO_TITLE, 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_details_dialog, container, false);
        close = (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        mDeleteL = (LinearLayout) view.findViewById(R.id.delete);
        mDeleteL.setOnClickListener(this);
        mUpdateB = (Button) view.findViewById(R.id.update);
        mUpdateB.setOnClickListener(this);

        name= (CustomBoldTextView) view.findViewById(R.id.name_txt);
        image= (ImageView) view.findViewById(R.id.image);
        adress= (CustomLightTextView) view.findViewById(R.id.adress_txt);
        mail= (CustomLightTextView) view.findViewById(R.id.mail_txt);
        phone= (CustomLightTextView) view.findViewById(R.id.phone_txt);
        date= (CustomLightTextView) view.findViewById(R.id.date_txt);
        massege= (CustomLightTextView) view.findViewById(R.id.massege_txt);

        if(customerObj!=null&&customerObj.getUserObj()!=null) {
            name.setText(customerObj.getUserObj().getNvFirstName() + " " + customerObj.getUserObj().getNvLastName());
            adress.setText(customerObj.getUserObj().getNvAdress());
            mail.setText(customerObj.getUserObj().getNvMail());
            phone.setText(customerObj.getUserObj().getNvPhone());
            date.setText(customerObj.getUserObj().getdBirthdate().toString());
        }
        return view;
    }


    @Override
    public void onClick(View v) {
        FragmentManager fm;
        switch (v.getId()) {
            case R.id.close:
                this.dismiss();
                break;
            case R.id.delete:
                fm = getActivity().getSupportFragmentManager();
                DeleteCustomerDialogFragment newFragment__ = new DeleteCustomerDialogFragment();
                newFragment__.show(fm, "dialog");
                break;
            case R.id.update:
                fm = getActivity().getSupportFragmentManager();
                UpdateCustemerFragment updateCustemerFragment = new UpdateCustemerFragment();
                updateCustemerFragment.show(fm, "dialog");
                break;

        }
    }
}
