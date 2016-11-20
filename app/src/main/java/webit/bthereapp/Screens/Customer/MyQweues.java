package webit.bthereapp.Screens.Customer;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.R;


public class MyQweues extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView mWaitingListLv;
    AdapterMyQweues adapterMyQweues;
    ImageButton mCloseIB;
    private String mParam1;
    int b = 0;
    private String mParam2;

    public static MyQweues instance;
    ArrayList<OrderDetailsObj> OrderDetailsObj_ = new ArrayList<>();


    public static MyQweues getInstance() {
        if (instance == null)
            instance = new MyQweues();
        return instance;
    }

    public MyQweues() {
        // Required empty public constructor
    }

    public MyQweues(int a) {
        b = a;
    }

    public static void removeInstance() {
        instance = null;
    }

    public static MyQweues newInstance(String param1, String param2, int a) {
        MyQweues fragment = new MyQweues(a);
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
        View view = inflater.inflate(R.layout.fragment_my_qweues, container, false);
        mCloseIB = (ImageButton) view.findViewById(R.id.close_waiting_list);
        mCloseIB.setOnClickListener(this);
        mWaitingListLv = (ListView) view.findViewById(R.id.waiting_list);
        sortListOrders();
        adapterMyQweues = new AdapterMyQweues(getActivity(), OrderDetailsObj_, b);
        mWaitingListLv.setAdapter(adapterMyQweues);

        return view;
    }

    public void sortListOrders() {
        if (OrderDetailsObj_ != null) {
            Collections.sort(OrderDetailsObj_, new Comparator<OrderDetailsObj>() {
                @Override
                public int compare(OrderDetailsObj lhs, OrderDetailsObj rhs) {
                    return lhs.getDtDateOrder().compareTo(rhs.getDtDateOrder());
                }
            });
        }
    }


    public void set_list_and_a(int a, ArrayList<OrderDetailsObj> OrderDetailsObj) {
        OrderDetailsObj_ = OrderDetailsObj;
        b = a;
        Date date = ConnectionUtils.JsonDateToDate(OrderDetailsObj_.get(0).getDtDateOrder());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_waiting_list:
                dismiss();
                break;
        }
    }

}
