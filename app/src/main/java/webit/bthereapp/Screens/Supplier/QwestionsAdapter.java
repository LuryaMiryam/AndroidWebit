package webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.R;

/**
 * Created by User on 18/04/2016.
 */

public class QwestionsAdapter extends ArrayAdapter<Qwestion> {

    private Context mcontext;
    private List<Qwestion> list;
    private ImageButton button;
    private EditText mNCet;
    private CustomRegularTextView mDateTv, mQwestionTv, mAnswerTv;
    private View convertView;
    private int state = 0;

    public QwestionsAdapter(Context context, int resource, List<Qwestion> ls) {
        super(context, resource, ls);
        list = ls;
        mcontext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout click;


        // If holder not exist then locate all view from UI file.
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_in_list_qwestions, parent, false);
        final View finalConvertView = convertView;

        click = (LinearLayout) convertView.findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout open = (LinearLayout) finalConvertView.findViewById(R.id.open);
                if (state == 0) {
                    open.setVisibility(View.VISIBLE);
                    state = 1;
                } else {
                    open.setVisibility(View.GONE);
                    state = 0;
                }
            }
        });

        mDateTv = (CustomRegularTextView) convertView.findViewById(R.id.qwes_date);
        mQwestionTv = (CustomRegularTextView) convertView.findViewById(R.id.qwes_txt);
        mAnswerTv = (CustomRegularTextView) convertView.findViewById(R.id.answer_txt);

        mDateTv.setText(list.get(position).getDate());
        mQwestionTv.setText(list.get(position).getQwestion());
        if (list.get(position).getAnswer() != null)
            mAnswerTv.setText(list.get(position).getAnswer());

        convertView.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                           }
                                       }

        );
        return convertView;
    }
}