package  webit.bthereapp.Screens.Supplier;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;


public class AboutUsFragment extends BaseFragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CustomLightButton movie, regulation;

    public static AboutUsFragment instance;

    public static AboutUsFragment getInstance() {
        if (instance == null)
            instance = new AboutUsFragment();
        return instance;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }

    // TODO: Rename and change types and number of parameters
    public static AboutUsFragment newInstance(String param1, String param2) {
        AboutUsFragment fragment = new AboutUsFragment();
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
        View view= inflater.inflate(R.layout.fragment_about_us, container, false);

        movie = (CustomLightButton) view.findViewById(R.id.movie);
        regulation = (CustomLightButton) view.findViewById(R.id.regulations);
        movie.setOnClickListener(this);
        regulation.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.movie:{

                break;
            }
            case R.id.regulations:{
                FragmentManager fm = getFragmentManager();
                RegulationToUserDialogFragment newFragment = new RegulationToUserDialogFragment();
                newFragment.show(fm, "dialog");
                break;
            }
        }
    }
}
