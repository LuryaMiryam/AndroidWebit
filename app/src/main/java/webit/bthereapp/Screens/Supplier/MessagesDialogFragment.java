package  webit.bthereapp.Screens.Supplier;


import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MessagesDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagesDialogFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView listView;
    private List<Message> messages = new ArrayList<>();
    private Message message;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton close;

    public MessagesDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessagesDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessagesDialogFragment newInstance(String param1, String param2) {
        MessagesDialogFragment fragment = new MessagesDialogFragment();
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
        View view = inflater.inflate(R.layout.fragment_messages_dialog, container, false);

        close = (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        message = new Message("10.04.2012", getResources().getString(R.string.messege1),"");

        messages.add(message);
        message = new Message("22.04.2014", getResources().getString(R.string.messege2), "");
        messages.add(message);
        message = new Message("18.04.2016", getResources().getString(R.string.messege3), "");
        messages.add(message);


        listView = (ListView) view.findViewById(R.id.messages_list);
        listView.setDivider(null);
        MessageAdapter adapter = new MessageAdapter(getActivity(), R.layout.item_in_list_messages_, messages);
        listView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close: {
                this.dismiss();
                break;
            }
        }
    }
}
