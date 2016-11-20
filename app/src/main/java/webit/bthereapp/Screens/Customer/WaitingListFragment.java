package webit.bthereapp.Screens.Customer;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.WindowCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import webit.bthereapp.BL.MainBL;
import webit.bthereapp.CustemViews.CustomSpinnerLargeBlack;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Entities.WaitingListObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;


public class WaitingListFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView mWaitingListLv;
    private ArrayList<WaitingListObj> waitingListObjs;
    AdapterWaitingList adapterWaitingList;
    ImageButton mCloseIB;
    private LinearLayout waiting_list_;
    private String mParam1;
    private String mParam2;
    int width;

    public static WaitingListFragment instance;

    public static WaitingListFragment getInstance() {
        if (instance == null)
            instance = new WaitingListFragment();
        return instance;
    }

    public WaitingListFragment() {
        // Required empty public constructor
    }

    public static void removeInstance() {
        instance = null;
    }

    public static WaitingListFragment newInstance(String param1, String param2) {
        WaitingListFragment fragment = new WaitingListFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting_list, container, false);
        mCloseIB = (ImageButton) view.findViewById(R.id.close_waiting_list);
        mCloseIB.setOnClickListener(this);
        mWaitingListLv = (ListView) view.findViewById(R.id.waiting_list);
        adapterWaitingList = new AdapterWaitingList(getActivity(), waitingListObjs, width);
        mWaitingListLv.setAdapter(adapterWaitingList);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_waiting_list:
                this.dismiss();
                break;
        }
    }


    public void set_list(ArrayList<WaitingListObj> waitingListObjs_) {
        waitingListObjs = waitingListObjs_;
    }

}
