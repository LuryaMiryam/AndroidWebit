package  webit.bthereapp.Screens.Supplier;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.R;

/**
 * Created by User on 22/05/2016.
 */
public class ReportsListAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private ArrayList<String> stringList;
    private ReportsDialogFragment dialogFragment;

    public ReportsListAdapter(ArrayList<String> stringList, Context context, ReportsDialogFragment dialog) {
        this.stringList = stringList;
        this.context = context;
        dialogFragment = dialog;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View rowView;

        rowView = View.inflate(context, R.layout.reports_list_item, null);
        RelativeLayout report_rl = (RelativeLayout) rowView.findViewById(R.id.report_rl);
        final CustomLightTextView txt = (CustomLightTextView) rowView.findViewById(R.id.txt);
        txt.setText(stringList.get(position).toString());

        report_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.dismiss();
                ReportOperationalUtilizationFragment fragment = ReportOperationalUtilizationFragment.newInstance(txt.getText().toString(), "");
                ((ExistsSuplierActivity) context).initFragmentMain(fragment, true);

            }
        });

        return rowView;

    }
}
