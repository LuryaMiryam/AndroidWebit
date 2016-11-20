package  webit.bthereapp.Screens.Supplier;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ReportsDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportsDialogFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String title;
    private String mParam2;

    private ListView lv;
    private ImageButton close;
    private String strings[];


    private RelativeLayout mOPReportL,r2,r3,r4,r5,r6,r7,r8,r9,r10;

    public ReportsDialogFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ReportsDialogFragment newInstance(String title, String param2) {
        ReportsDialogFragment fragment = new ReportsDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_reports_dialog, container, false);
        close= (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        lv = (ListView) view.findViewById(R.id.reports_listView);
        strings = getResources().getStringArray(R.array.reports_list);
        ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(strings));
        ReportsListAdapter adapter = new ReportsListAdapter(stringList,getContext(),this);
        lv.setAdapter(adapter);



        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close:
                this.dismiss();
                break;
            default:
                 this.dismiss();
                ((ExistsSuplierActivity)getActivity()).initFragmentMain(ReportOperationalUtilizationFragment.getInstance(), true);
        }
    }
}
