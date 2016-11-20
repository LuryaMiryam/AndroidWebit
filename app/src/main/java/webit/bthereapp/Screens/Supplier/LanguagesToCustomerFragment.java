package webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.UserRegister.RegisterUserFragment;


public class LanguagesToCustomerFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    SharedPreferences sharedPreferences;
    public static LanguagesToCustomerFragment instance;

    public static LanguagesToCustomerFragment getInstance() {
        if (instance == null)
            instance = new LanguagesToCustomerFragment();
        return instance;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }


    private String mParam1;
    private String mParam2;

    ListView mLanguagesLV;
    LanguagesAdapter mLanguagesAdapter;
    public String[] LanguagesList;
    private CustomLightButton save;

    public LanguagesToCustomerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LanguagesToCustomerFragment newInstance(String param1, String param2) {
        LanguagesToCustomerFragment fragment = new LanguagesToCustomerFragment();
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
        View view = inflater.inflate(R.layout.fragment_languages_to_customer, container, false);
        LanguagesList = getResources().getStringArray(R.array.languages);

        mLanguagesLV = (ListView) view.findViewById(R.id.list_of_Languages);
        mLanguagesAdapter = new LanguagesAdapter(getActivity(), LanguagesList);
        mLanguagesLV.setAdapter(mLanguagesAdapter);
        save = (CustomLightButton) view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mLanguagesAdapter.selected;
                String l = "";
                switch (position) {
                    case 0:
                        l = "iw";
                        break;
                    case 1:
                        l = "en";
                        break;
                }
                if (getActivity() != null) {
                    saveInSharedPreference(l);
                    ConnectionUtils.setLanguage(getActivity(), l);
                    RegisterUserFragment.removeInstance();
                    new Handler().post(new Runnable() {

                        @Override
                        public void run() {
                            Intent intent = getActivity().getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            getActivity().overridePendingTransition(0, 0);
                            getActivity().finish();

                            getActivity().overridePendingTransition(0, 0);
                            getActivity().startActivity(intent);
                        }
                    });
                }
//                getActivity().onBackPressed();
            }
        });


        return view;
    }


    private void saveInSharedPreference(String lan) {
        sharedPreferences = getActivity().getSharedPreferences("DETAILS", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Languages", lan);
        editor.commit();
    }

}
