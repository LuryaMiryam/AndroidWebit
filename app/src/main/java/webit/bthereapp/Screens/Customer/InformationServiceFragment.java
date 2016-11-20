package webit.bthereapp.Screens.Customer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.Entities.Provider;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.R;


public class InformationServiceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CustomLightTextView price, time;
    private ImageView mCloseIV;
    private ProviderServices providerService;

    public static InformationServiceFragment instance;

    public static InformationServiceFragment getInstance() {
        if (instance == null)
            instance = new InformationServiceFragment();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }


    public InformationServiceFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static InformationServiceFragment newInstance(String param1, String param2) {
        InformationServiceFragment fragment = new InformationServiceFragment();
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
        Log.d("onCreate_f","InformationServiceFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        mCloseIV = (ImageView) view.findViewById(R.id.image_close);
        price = (CustomLightTextView) view.findViewById(R.id.price);
        time = (CustomLightTextView) view.findViewById(R.id.time);
        price.setText(providerService.getiPrice() + "");
        time.setText(providerService.getiTimeOfService() + "");
        mCloseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        Log.d("onCreateview_f","InformationServiceFragment");
        return view;
    }

    public void set_ProviderService(ProviderServices providerService_) {
        providerService = providerService_;
    }

}
