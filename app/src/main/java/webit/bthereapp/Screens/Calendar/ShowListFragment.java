package webit.bthereapp.Screens.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Calendar.CalanderDisplayList;
import webit.bthereapp.Utils.CalendarUtil;


public class ShowListFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    ImageButton mEyeIB,mEyeIB2;
    Boolean isEyeClicked=false;
    ViewPager mViewPager;
    int currentPosition;
    FrameLayout mListContainerFL;
    LinearLayout.LayoutParams params;



    public ShowListFragment() {
        // Required empty public constructor
    }

    public static ShowListFragment newInstance(String param1, String param2) {
        ShowListFragment fragment = new ShowListFragment();
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
       View view=inflater.inflate(R.layout.fragment_show_list, container, false);
        mEyeIB= (ImageButton) view.findViewById(R.id.eye);
        mEyeIB.setOnClickListener(this);
        mEyeIB2= (ImageButton) view.findViewById(R.id.eye2);
        mEyeIB2.setOnClickListener(this);
        mListContainerFL= (FrameLayout) view.findViewById(R.id.list_container);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.list_container, new CalanderDisplayList()).commit();
        if (!CalendarUtil.mEyeShow) {
            mEyeIB2.setVisibility(View.VISIBLE);
            mEyeIB.setVisibility(View.GONE);
        }
        else {
            mEyeIB.setVisibility(View.VISIBLE);
            mEyeIB2.setVisibility(View.GONE);
        }
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eye: {
                //refresh with/without synchronization and change the eye image
                if (!CalendarUtil.mEyeShow) {
                    mEyeIB.setVisibility(View.VISIBLE);
                    mEyeIB2.setVisibility(View.GONE);
                }
                else {
                    mEyeIB.setVisibility(View.GONE);
                    mEyeIB2.setVisibility(View.VISIBLE);
                }
                CalendarUtil.mEyeShow=!CalendarUtil.mEyeShow;
                if (getActivity() instanceof NavigetionLayout)
                    //refresh the list
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander,new ShowListFragment()).commit();
            }
            break;


            case R.id.eye2: {
                //refresh with/without synchronization and change the eye image
                if (!CalendarUtil.mEyeShow) {
                    mEyeIB.setVisibility(View.VISIBLE);
                    mEyeIB2.setVisibility(View.GONE);
                }
                else {
                    mEyeIB.setVisibility(View.GONE);
                    mEyeIB2.setVisibility(View.VISIBLE);
                }
                CalendarUtil.mEyeShow=!CalendarUtil.mEyeShow;
                if (getActivity() instanceof NavigetionLayout)
                    //refresh the list
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander,new ShowListFragment()).commit();

            }
            break;
        }
    }
}
