package webit.bthereapp.Screens.Customer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.Entities.orderObj;
import webit.bthereapp.Entities.SearchResulstsObj;

import com.google.gson.Gson;

import java.util.Locale;

import webit.bthereapp.R;


public class ChosenPicFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private long id;
    private CustomBoldTextView name;
    private CustomRegularTextView slogan;

    private SearchResulstsObj searchResulstsObj;
    private TextView providerNameTV, providerSloganTV, providerCityTV, distanceTV, providerVotersTV, providerRankTV;
    private ImageView providerIconTV;
    private String string_distance="";


    public static ChosenPicFragment instance;

    public static ChosenPicFragment getInstance() {
        if (instance == null)
            instance = new ChosenPicFragment();
        return instance;
    }

    public static ChosenPicFragment newInstance(long param1, String param2) {
        ChosenPicFragment fragment = new ChosenPicFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getArguments() != null) {
            try {
                searchResulstsObj = new Gson().fromJson(getArguments().getString("bStr", ""), SearchResulstsObj.class);
                OrderDetailsFragment.setInstance(null);
                //enter the details to fragment of the order details
                orderObj.getInstance().setiSupplierId(searchResulstsObj.iProviderId);
                OrderDetailsFragment.getInstance().set_b_name(searchResulstsObj.nvProviderName);
                OrderDetailsFragment.getInstance().setB_id(searchResulstsObj.iProviderId);
                OrderDetailsFragment.getInstance().set_adress(searchResulstsObj.nvAdress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        View view = inflater.inflate(R.layout.top_service_pic, container, false);
        providerNameTV = (TextView) view.findViewById(R.id.item_title_1);
        providerSloganTV = (TextView) view.findViewById(R.id.item_title_2);
        providerIconTV = (ImageView) view.findViewById(R.id.item_icon);
        providerCityTV = (TextView) view.findViewById(R.id.item_city);
        distanceTV = (TextView) view.findViewById(R.id.item_distance_);
        providerVotersTV = (TextView) view.findViewById(R.id.item_voters);
        providerRankTV = (TextView) view.findViewById(R.id.item_ranking);

        providerNameTV.setText(searchResulstsObj.nvProviderName);
        providerSloganTV.setText(searchResulstsObj.nvProviderSlogan);
      //  providerCityTV.setText(searchResulstsObj.nvAdress);
        providerRankTV.setText(searchResulstsObj.iCustomerRank + "");

        if (searchResulstsObj.iDistance == -1) {
            //if the server did not sucseed in finding the distance so do not show it
            string_distance = searchResulstsObj.nvAdress;
        } else {
          //get the distance with 2 digits after the point
             String dis=String.format(Locale.US,"%.02f", searchResulstsObj.iDistance);
            string_distance = getActivity().getResources().getString(R.string.way,searchResulstsObj.nvAdress, dis);
        }
        distanceTV.setText(string_distance);

        if (searchResulstsObj.nvProviderLogo!=null && !searchResulstsObj.nvProviderLogo.toString().equals("")) {
            Bitmap bitmap = ConnectionUtils.convertStringToBitmap(searchResulstsObj.nvProviderLogo);
            providerIconTV.setImageBitmap(bitmap);
        }
        return view;
    }
}
