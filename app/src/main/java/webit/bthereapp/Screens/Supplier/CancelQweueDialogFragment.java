package  webit.bthereapp.Screens.Supplier;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CancelQweueDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelQweueDialogFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CustomLightButton cancel_ok;
    private ImageButton close;

    public CancelQweueDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CancelQweueDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancelQweueDialogFragment newInstance(String param1, String param2) {
        CancelQweueDialogFragment fragment = new CancelQweueDialogFragment();
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
        View view = inflater.inflate(R.layout.fragment_cancel_qweue_dialog, container, false);
        cancel_ok = (CustomLightButton) view.findViewById(R.id.cancel_ok);
        cancel_ok.setOnClickListener(this);

        close= (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_ok: {
                dismiss();
                break;
            }
            case R.id.close:{
                dismiss();
                break;
            }
        }
    }
}
