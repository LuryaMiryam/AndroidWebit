package  webit.bthereapp.Screens.Supplier;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ItemInIconsDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemInIconsDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private int mIcon;
    private String mTxt, mH;

    public ItemInIconsDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ItemInIconsDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemInIconsDialog newInstance(String h, String txt, int icon) {
        ItemInIconsDialog fragment = new ItemInIconsDialog();
        Bundle args = new Bundle();
        args.putString("h", h);
        args.putString("txt", txt);
        args.putInt("icon", icon);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mH = getArguments().getString("h");
            mTxt = getArguments().getString("txt");
            mIcon = getArguments().getInt("icon");
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
        ViewGroup.LayoutParams params = getActivity().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.FILL_PARENT;
        getActivity().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            mH = getArguments().getString("h");
            mTxt = getArguments().getString("txt");
            mIcon = getArguments().getInt("icon");
        }


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_in_icons_dialog, container, false);

        CustomBoldTextView h_ = (CustomBoldTextView) view.findViewById(R.id.h);
        CustomRegularTextView txt = (CustomRegularTextView) view.findViewById(R.id.txt);
        ImageView image = (ImageView) view.findViewById(R.id.image__);
        h_.setText(mH);
        image.setImageResource(mIcon);

        ImageButton close = (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }


}
