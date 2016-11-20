package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AboutTheSystemDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutTheSystemDialogFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public final int CATEGORY_ID = 0;
    private Context mContext;
    Dialog dialog;
    private RelativeLayout mItemRl;
    ViewHolder_ holder;
    private Integer[] images_arr = {
            R.drawable.black_icons_galaxy_x1_08, R.drawable.black_icons_galaxy_x1_27,
            R.drawable.black_icons_galaxy_x1_05, R.drawable.black_icons_galaxy_x1_01,

            R.drawable.black_icons_galaxy_x1_10, R.drawable.supplier_galaxy_icons_x1_23,
            R.drawable.black_icons_galaxy_x1_18, R.drawable.black_icons_galaxy_x1_06,

            R.drawable.black_icons_galaxy_x1_13, R.drawable.black_icons_galaxy_x1_12,
            R.drawable.black_icons_galaxy_x1_24, R.drawable.black_icons_galaxy_x1_09,

            R.drawable.black_icons_galaxy_x1_08, R.drawable.black_icons_galaxy_x1_27,
            R.drawable.black_icons_galaxy_x1_05, R.drawable.black_icons_galaxy_x1_01,
    };
    private String[] h_arr = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l","m","n","o","p"};
    private String[] txt_arr = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l","m","n","o","p"};
    //    private Integer[] images_arr = {};
    private ImageView imageView;

    public AboutTheSystemDialogFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AboutTheSystemDialogFragment newInstance(String param1, String param2) {
        AboutTheSystemDialogFragment fragment = new AboutTheSystemDialogFragment();
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
        View view = inflater.inflate(R.layout.fragment_about_the_system_dialog, container, false);


        AlertDialog.Builder builder;
        inflater = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final GridView gridview = (GridView) view.findViewById(R.id.icons_gv);
        gridview.setVerticalScrollBarEnabled(false);
        gridview.setAdapter(new TextAdapter_(getActivity()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                imageView.setImageResource(images_arr[position]);
                holder.iv = images_arr[position];
                holder.h = h_arr[position];
                holder.txt = txt_arr[position];

                FragmentManager fm = getFragmentManager();
                ItemInIconsDialog itemInIconsDialog = ItemInIconsDialog.newInstance(holder.h, holder.txt, holder.iv);
                getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                itemInIconsDialog.show(fm, "dialog");
            }
        });

        ImageButton close_out = (ImageButton) view.findViewById(R.id.close_out);
        close_out.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.close_out: {
                dismiss();
                break;
            }
        }
    }

    public class TextAdapter_ extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater_;

        public TextAdapter_(Context c) {
            mInflater_ = LayoutInflater.from(c);
            mContext = c;
        }

        public int getCount() {
            return h_arr.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {  // if it's not recycled,
                convertView = mInflater_.inflate(R.layout.icons_item, null);
                holder = new ViewHolder_();
                imageView = (ImageView) convertView.findViewById(R.id.icon_iv);
                holder.mItemRl = (RelativeLayout) convertView.findViewById(R.id.icon_r);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder_) convertView.getTag();
            }

            imageView.setImageResource(images_arr[position]);
            holder.h = h_arr[position];
            holder.txt = txt_arr[position];

            return convertView;
        }

    }

    class ViewHolder_ {
        String h, txt;
        int iv;
        RelativeLayout mItemRl;
    }
}