package webit.bthereapp.Screens.Supplier;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link QweuesToCustomerDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QweuesToCustomerDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private ListView listView;
    private List<QweueCustomer> qweues = new ArrayList<>();
    private QweueCustomer qweue;

    private ImageButton close;

    public QweuesToCustomerDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QweuesToCustomerDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QweuesToCustomerDialogFragment newInstance(String param1, String param2) {
        QweuesToCustomerDialogFragment fragment = new QweuesToCustomerDialogFragment();
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
        View view = inflater.inflate(R.layout.quewes_to_customer_2, container, false);
            close = (ImageButton) view.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            List<QweueDay> qweues_day = new ArrayList<>();
            QweueDay qweue_day;

            //1

            qweue_day = new QweueDay("08:00-09:30", "צבע וגוונים");
            qweues_day.add(qweue_day);

            qweue_day = new QweueDay("17:00-17:30", "תספורת");
            qweues_day.add(qweue_day);


            qweue = new QweueCustomer("ה", "22", "אוק'", "2015", qweues_day);
            qweues.add(qweue);

            //2
            qweues_day = new ArrayList<>();
//        qweues_day.add(qweue_day);

            qweue = new QweueCustomer("ו", "23", "אוק'", "2015", qweues_day);
            qweues.add(qweue);

            //3
            qweue = new QweueCustomer("ו", "23", "אוק'", "2015", qweues_day);
            qweues.add(qweue);

            //4
            qweue = new QweueCustomer("ו", "23", "אוק'", "2015", qweues_day);
            qweues.add(qweue);

            int a = 0;
            for (QweueCustomer qweue : qweues) {
                a += 38;
                for (QweueDay qweueDay : qweue.getList()) {
                    a += 121;
                }
            }
            listView = (ListView) view.findViewById(R.id.qweues_list);
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = a;
            listView.setLayoutParams(params);
            listView.requestLayout();

            listView.setDivider(null);
            QweuesToCusAdapter adapter = new QweuesToCusAdapter(getActivity(), R.layout.qweues_to_cus_item, qweues);
            listView.setAdapter(adapter);
        return view;
    }
}
