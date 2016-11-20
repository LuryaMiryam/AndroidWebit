package webit.bthereapp.Screens.Customer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.Connection.ObjectConnection;
import webit.bthereapp.CustemViews.Fonts.CustomRegularButton;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Supplier.Qwestion;
import webit.bthereapp.Screens.Supplier.QwestionsClientAdapter;

/**
 * Created by 1 on 4/12/2016.
 */
public class SyncContacts extends ObjectConnection{

    private ArrayList<String> nvPhoneList;
    private double iProviderId;
    private int bAutoApproval;
    public SyncContacts() {
    }

    public double getiProviderId() {
        return iProviderId;
    }

    public void setiProviderId(double iProviderId) {
        this.iProviderId = iProviderId;
    }

    public ArrayList<String> getPhone_list() {
        return nvPhoneList;
    }

    public void setPhone_list(ArrayList<String> phone_list) {
        this.nvPhoneList = phone_list;
    }

    public int getbAutoApproval() {
        return bAutoApproval;
    }

    public void setbAutoApproval(int bAutoApproval) {
        this.bAutoApproval = bAutoApproval;

    }

    public static void setInstance(SyncContacts instance) {
        SyncContacts.instance = instance;
    }

    public static SyncContacts instance;

    public static SyncContacts getInstance() {
        if (instance == null)
            instance = new SyncContacts();
        return instance;
    }

    /**
     * Activities that contain this fragment must implement the
     * to handle interaction events.
     * Use the {@link HelpDialogClientFragment#newInstance} factory method to
     * create an instance of this fragment.
     */
    public static class HelpDialogClientFragment extends DialogFragment implements View.OnClickListener {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        private int new_qwes = 0;
        private ListView listView;
        private ImageButton phone, envelope;
        private List<Qwestion> qwestions = new ArrayList<>();
        private Qwestion qwestion;
        private CustomRegularButton mSend_qwesBtn;
        private LinearLayout new_qwestion, add;
        private RelativeLayout add_qwestion;
        private CustomRegularButton btn;
        private ImageButton close_out;

        public HelpDialogClientFragment() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HelpDialogFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static HelpDialogClientFragment newInstance() {
            HelpDialogClientFragment fragment = new HelpDialogClientFragment();
            Bundle args = new Bundle();
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

            View view = inflater.inflate(R.layout.fragment_help_dialog_client, container, false);

            ImageButton close_in = (ImageButton) view.findViewById(R.id.close_in);
            close_out = (ImageButton) view.findViewById(R.id.close);
            close_out.setOnClickListener(this);

            qwestion = new Qwestion("22.04.2012", getString(R.string.q4),"...........");
            qwestions.add(qwestion);
            qwestion = new Qwestion("18.10.2002", getString(R.string.q2), "...........");
            qwestions.add(qwestion);
            qwestion = new Qwestion("18.10.2010", getString(R.string.q3), "...........");
            qwestions.add(qwestion);
            qwestion = new Qwestion("18.10.2010",getString(R.string.q4), "...........");
            qwestions.add(qwestion);

            listView = (ListView) view.findViewById(R.id.qwestions_list);
            listView.setDivider(null);
            QwestionsClientAdapter adapter = new QwestionsClientAdapter(getActivity(), R.layout.item_in_list_qwestions_client, qwestions);
            listView.setAdapter(adapter);
            envelope = (ImageButton) view.findViewById(R.id.envelope);
            phone = (ImageButton) view.findViewById(R.id.phone);
            envelope.setOnClickListener(this);
            phone.setOnClickListener(this);
            new_qwestion = (LinearLayout) view.findViewById(R.id.new_qwestion);
            add_qwestion = (RelativeLayout) view.findViewById(R.id.add_qwestion);
            mSend_qwesBtn = (CustomRegularButton) view.findViewById(R.id.send_qwes);
            mSend_qwesBtn.setOnClickListener(this);

            close_in = (ImageButton) view.findViewById(R.id.close_in);
            close_in.setOnClickListener(this);
            return view;

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.envelope: {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "Bthere@gmail.com" });
                    startActivity(Intent.createChooser(intent, ""));
                    break;
                }
                case R.id.phone: {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:0506872077"));
                    startActivity(callIntent);
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
}
