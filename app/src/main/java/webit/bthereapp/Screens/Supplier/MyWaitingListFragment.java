package  webit.bthereapp.Screens.Supplier;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MyWaitingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyWaitingListFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RelativeLayout rr, all;
    private ListView listView;
    private List<WaitingListItem> waitingListItems = new ArrayList<>();
    private WaitingListItem waitingListItem;

    private ImageButton close;

    public MyWaitingListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyWaitingListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyWaitingListFragment newInstance(String param1, String param2) {
        MyWaitingListFragment fragment = new MyWaitingListFragment();
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

        View view = inflater.inflate(R.layout.fragment_my_waiting_list, container, false);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        close = (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        rr = (RelativeLayout) view.findViewById(R.id.rr);
        all = (RelativeLayout) view.findViewById(R.id.all_rr);

        List<WaitingListItemToDay> waitings_day = new ArrayList<>();

        //1
        WaitingListItemToDay waitingListItemToDay = new WaitingListItemToDay("יסמין גנזי", "בן לולו", "תספורת אישה שיער ארוך", "08:00-09:00");
        waitings_day.add(waitingListItemToDay);

        waitingListItemToDay = new WaitingListItemToDay("קארין ", "אופיר", "צבע + פן", "09:00-11:00");
        waitings_day.add(waitingListItemToDay);


        waitingListItemToDay = new WaitingListItemToDay("משה", "סבח", "תספורת גבר", "10:00-11:00");
        waitings_day.add(waitingListItemToDay);

        waitingListItemToDay = new WaitingListItemToDay("לירון", "חזיז", "גוונים + תספורת", "11:00-13:30");
        waitings_day.add(waitingListItemToDay);

        waitingListItemToDay = new WaitingListItemToDay("דודו", "טסה", "מילוי שיער", "12:00-14:00");
        waitings_day.add(waitingListItemToDay);

        waitingListItemToDay = new WaitingListItemToDay("חיה", "בן ששון", "אומברה אפור", "13:00-15:00");
        waitings_day.add(waitingListItemToDay);

        //2
        waitingListItems = new ArrayList<>();

        waitingListItem = new WaitingListItem("ה", "22", "אוק'", "2015", waitings_day);
        waitingListItems.add(waitingListItem);


        //3
        waitings_day = new ArrayList<>();

        waitingListItemToDay = new WaitingListItemToDay("אבירן", "כהן", "תספורת גבר", "08:00-09:00");
        waitings_day.add(waitingListItemToDay);

        waitingListItem = new WaitingListItem("ה", "23", "אוק'", "2015", waitings_day);
        waitingListItems.add(waitingListItem);

        listView = (ListView) view.findViewById(R.id.waiting_list);
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        MyWaitingListAdapter adapter = new MyWaitingListAdapter(getActivity(), R.layout.waiting_list_item, waitingListItems);
        int a = 0, i = 0, j = 0;

        View listItem = adapter.getView(1, null, listView);
        ViewGroup.LayoutParams params_rr = rr.getLayoutParams();
        ViewGroup.LayoutParams params_view = all.getLayoutParams();
        int m = 0;
        for (WaitingListItem waitingListItem : waitingListItems) {

            listItem = adapter.getView(m, null, listView);
            listItem.measure(0, 0);
            j= listItem.getMeasuredHeight();


            a += j;

            m++;
        }
        params.height = a;
        listView.setLayoutParams(params);
        listView.requestLayout();
        all.setLayoutParams(params_view);
        all.requestLayout();


        listView.setDivider(null);

        listView.setAdapter(adapter);
        return view;
    }


}
