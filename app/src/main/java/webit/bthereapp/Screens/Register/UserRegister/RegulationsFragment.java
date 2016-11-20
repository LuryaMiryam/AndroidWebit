package webit.bthereapp.Screens.Register.UserRegister;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p/>
 * to handle interaction events.
 * Use the {@link RegulationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegulationsFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static RegulationsFragment instance;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button mOkBtn, mCancelBtn;
    private OnFragmentInteractionListener mListener;

    public RegulationsFragment() {
    }

    public static RegulationsFragment getInstance() {
        if (instance == null)
            instance = new RegulationsFragment();
        return instance;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegulationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegulationsFragment newInstance(String param1, String param2) {
        RegulationsFragment fragment = new RegulationsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getActivity() instanceof MainActivity)
        initFragmentTop(1, getString(R.string.business_the_datails_title));
        View view = inflater.inflate(R.layout.fragment_regulations, container, false);
        mOkBtn = (Button) view.findViewById(R.id.ok_regulation);
        mCancelBtn = (Button) view.findViewById(R.id.cancel_regulation);
        mOkBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //When the user presses the ok button
            case R.id.ok_regulation: {
                if(getActivity() instanceof MainActivity)
                mListener.onFragmentInteraction(1);
                getActivity().onBackPressed();

                break;
            }
            //  When the user presses the cancel button
            case R.id.cancel_regulation: {
                if(getActivity() instanceof MainActivity)
                mListener.onFragmentInteraction(0);
                getActivity().onBackPressed();

                break;
            }
            default:
                break;
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int b);
    }
}