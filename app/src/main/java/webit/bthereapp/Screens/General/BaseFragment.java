package webit.bthereapp.Screens.General;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Screens.Supplier.ExistsSuplierDefinitionsActivity;


public abstract class BaseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static BaseFragment instance;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BaseFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event

    public static BaseFragment getInstance() {
        return null;
    }

    public void initFragmentMain(Fragment fragment, boolean isTop, boolean isAddToBackStack) {
        ((MainActivity) getActivity()).initFragmentMainYesInOrder(fragment, isTop, isAddToBackStack);
    }
    public void initFragmentMain(Fragment fragment, boolean isTop, boolean isAddToBackStack,int num) {
        ((MainActivity) getActivity()).initFragmentMainNotInOrder(fragment, isTop, isAddToBackStack,num);
    }

    public void initFragmentTop(int position, String title) {

            ((MainActivity) getActivity()).initFragmentTop(position, title);

    }
    public void initFragmentTop(int position, String title,int place,boolean back_) {

        ((MainActivity) getActivity()).initFragmentTop(position, title,place,back_);

    }
    public void initFragmentTop3(int position, String title,boolean is_back) {

        ((ExistsSuplierDefinitionsActivity) getActivity()).initFragmentTop(position, title,is_back);

    }


    public void HideFragmentTop() {
        ((MainActivity) getActivity()).hideFragmentTop();
    }
    public void visibleFragmentBottom() {
        ((MainActivity) getActivity()).visibleFragmentBottom();
    }
    public void HideFragmentBottom() {
        ((MainActivity) getActivity()).hideFragmentBottom();
    }
    public void HideFragmentBottom_n() {
        ((NavigetionLayout) getActivity()).hideFragmentBottom();
    }

    public void HideFragmentTop2() {
        ((ExistsSuplierDefinitionsActivity) getActivity()).hideFragmentTop();
    }

    public abstract boolean OnBackPress();


}
