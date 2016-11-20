package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.RippleDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.List;

import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.R;

/**
 * Created by User on 19/04/2016.
 */
public class MessageAdapter extends ArrayAdapter<Message> {

    private Context mcontext;
    private List<Message> list;
    private ImageButton button;
    private int mState = 0;
    private EditText mNCet;
    private CustomLightTextView  mText1Tv, mText2Tv;
    private CustomBoldTextView mDateTv;


    private View convertView;

    public MessageAdapter(Context context, int resource, List<Message> ls) {
        super(context, resource, ls);
        list = ls;
        mcontext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        LinearLayout mClickLl;
        LinearLayout delete;

        // If holder not exist then locate all view from UI file.
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_in_list_messages_, parent, false);

        mDateTv = (CustomBoldTextView) convertView.findViewById(R.id.mess_date);
        mText1Tv = (CustomLightTextView) convertView.findViewById(R.id.mess_txt1);
        mText2Tv = (CustomLightTextView) convertView.findViewById(R.id.mess_txt2);

        mClickLl = (LinearLayout) convertView.findViewById(R.id.click);
        final View finalConvertView = convertView;
        mClickLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout mOpenLl = (LinearLayout) finalConvertView.findViewById(R.id.open);


                if (mState == 0) {
                    mOpenLl.setVisibility(View.VISIBLE);
                    mState = 1;
                } else {
                    mOpenLl.setVisibility(View.GONE);
                    mState = 0;
                }
            }
        });


        delete = (LinearLayout) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout message_row = (LinearLayout) finalConvertView.findViewById(R.id.message_row);
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        mDateTv.setText(list.get(position).getDate());
        mText1Tv.setText(list.get(position).getMessage_h());
        mText2Tv.setText(list.get(position).getMessage());



        return convertView;
    }


}
