package webit.bthereapp.Screens.Register.MainActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;

public class BottomFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static BottomFragment instance;
    TextView mAdvertisementsTv;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public FrameLayout mBottomFl;


    public BottomFragment() {
        // Required empty public constructor
    }

    public static BaseFragment getInstance() {
        if (instance == null)
            instance = new BottomFragment();
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
     * @return A new instance of fragment BottomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomFragment newInstance(String param1, String param2) {
        BottomFragment fragment = new BottomFragment();
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

        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        mBottomFl= (FrameLayout) view.findViewById(R.id.bottom);
        //set animation from Advertisement
        mAdvertisementsTv = (TextView) view.findViewById(R.id.advertisement);
        setAnimation();
        return view;
    }

    //set animation from Advertisement
    private void setAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.move);
        mAdvertisementsTv.setAnimation(animation);
    }


}
