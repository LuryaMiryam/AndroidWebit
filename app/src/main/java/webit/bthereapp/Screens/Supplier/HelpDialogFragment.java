package  webit.bthereapp.Screens.Supplier;


import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.CustemViews.CustomSpinner;
import webit.bthereapp.CustemViews.Fonts.CustomRegularButton;
import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HelpDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpDialogFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int new_qwes = 0;
    private ListView listView;
    private List<Qwestion> qwestions = new ArrayList<>();
    private Qwestion qwestion;
    private CustomRegularButton mSend_qwesBtn;
    private LinearLayout new_qwestion, add;
    private RelativeLayout add_qwestion;
    private CustomRegularButton btn;
    private ImageButton close_out;

    public HelpDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HelpDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HelpDialogFragment newInstance(String param1, String param2) {
        HelpDialogFragment fragment = new HelpDialogFragment();
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
        setStyle(STYLE_NO_TITLE, 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_help_dialog, container, false);

        ImageButton close_in = (ImageButton) view.findViewById(R.id.close_in);
        close_out = (ImageButton) view.findViewById(R.id.close);
        close_out.setOnClickListener(this);

        qwestion = new Qwestion("18.10.2002",getResources().getString(R.string.q1),"..............");
        qwestions.add(qwestion);
        qwestion = new Qwestion("18.10.2002", getResources().getString(R.string.q2), "...........");
        qwestions.add(qwestion);
        qwestion = new Qwestion("18.10.2010",getResources().getString(R.string.q3), "...........");
        qwestions.add(qwestion);
        qwestion = new Qwestion("18.10.2010",getResources().getString(R.string.q4), "...........");
        qwestions.add(qwestion);

        listView = (ListView) view.findViewById(R.id.qwestions_list);
        listView.setDivider(null);
        QwestionsAdapter adapter = new QwestionsAdapter(getActivity(), R.layout.item_in_list_qwestions, qwestions);
        listView.setAdapter(adapter);
        new_qwestion = (LinearLayout) view.findViewById(R.id.new_qwestion);
        add_qwestion = (RelativeLayout) view.findViewById(R.id.add_qwestion);
        mSend_qwesBtn = (CustomRegularButton) view.findViewById(R.id.send_qwes);
        mSend_qwesBtn.setOnClickListener(this);
        add = (LinearLayout) view.findViewById(R.id.add);
        add.setOnClickListener(this);
        close_in = (ImageButton) view.findViewById(R.id.close_in);
        close_in.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add: {
                new_qwestion.setVisibility(View.VISIBLE);
                add_qwestion.setVisibility(View.GONE);
                break;
            }
            case R.id.send_qwes: {
                new_qwestion.setVisibility(View.GONE);
                add_qwestion.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.close_in: {
                new_qwestion.setVisibility(View.GONE);
                add_qwestion.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.close: {
                dismiss();
                break;
            }
        }
    }
}
