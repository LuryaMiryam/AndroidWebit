package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.List;

import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.R;

/**
 * Created by User on 18/04/2016.
 */

public class QwestionsClientAdapter extends ArrayAdapter<Qwestion> {

    private Context mcontext;
    private List<Qwestion> list;
    private CustomRegularTextView  mQwestionTv, mAnswerTv;
    private int state = 0;

    public QwestionsClientAdapter(Context context, int resource, List<Qwestion> ls) {
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
            convertView = inflater.inflate(R.layout.item_in_list_qwestions_client, parent, false);
        final View finalConvertView = convertView;

        click= (LinearLayout) convertView.findViewById(R.id.click);
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
        mQwestionTv = (CustomRegularTextView) convertView.findViewById(R.id.qwes_txt);
        mAnswerTv = (CustomRegularTextView) convertView.findViewById(R.id.answer_txt);
        mQwestionTv.setText(list.get(position).getQwestion());
        if (list.get(position).getAnswer() != null)
            mAnswerTv.setText(list.get(position).getAnswer());

        convertView.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View v) {
                                           }
                                       }

        );


        return convertView;
    }
}