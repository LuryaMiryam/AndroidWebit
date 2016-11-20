package  webit.bthereapp.Screens.Supplier;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;


public class LanguagesFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public static LanguagesFragment instance;

    public static LanguagesFragment getInstance() {
        if (instance == null)
            instance = new LanguagesFragment();
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
    public String [] LanguagesList;
    TextView oldTV;
    LinearLayout mBackL;
private CustomLightButton save;

    public LanguagesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LanguagesFragment newInstance(String param1, String param2) {
        LanguagesFragment fragment = new LanguagesFragment();
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
        View view=inflater.inflate(R.layout.fragment_languages, container, false);
        mLanguagesLV= (ListView) view.findViewById(R.id.list_of_Languages);
        LanguagesList = getResources().getStringArray(R.array.languages);
        save= (CustomLightButton) view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getActivity().onBackPressed();
                                    }
                                });
                mBackL = (LinearLayout) view.findViewById(R.id.back);
        mBackL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          getActivity().onBackPressed();
            }
        });
        mLanguagesAdapter=new LanguagesAdapter(getActivity(),LanguagesList);
        mLanguagesLV.setAdapter(mLanguagesAdapter);

        mLanguagesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.language);
                textView.setBackgroundColor(getResources().getColor(R.color.light_blue));
                textView.setTextColor(getResources().getColor(R.color.color_white));

                if (oldTV != null && oldTV != textView) {
                    oldTV.setTextColor(getResources().getColor(R.color.black));
                    oldTV.setBackgroundColor(getResources().getColor(R.color.transparent));
                }
                oldTV = textView;


            }
        });

        return view;
    }




}
