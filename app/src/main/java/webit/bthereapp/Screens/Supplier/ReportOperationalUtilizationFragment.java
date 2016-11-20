package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

//import io.realm.Case;
import webit.bthereapp.CustemViews.CustomDatePicker;
import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.CustemViews.YesOrNo;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;


public class ReportOperationalUtilizationFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String title_txt;
    private String mParam2;
    private CustomDatePicker customDatePicker;
    private static int flag = 0;
    private LinearLayout mBackL;
    private CustomBoldTextView title;

    private GraphView graph;

    private PopupWindow popupWindow;
    private static CustomLightTextView choose_from, choose_to;

    public static ReportOperationalUtilizationFragment instance;

    public static ReportOperationalUtilizationFragment getInstance() {
        if (instance == null)
            instance = new ReportOperationalUtilizationFragment();
        return instance;
    }

    public ReportOperationalUtilizationFragment() {
        // Required empty public constructor
    }

    public static ReportOperationalUtilizationFragment newInstance(String title_txt, String param2) {
        ReportOperationalUtilizationFragment fragment = new ReportOperationalUtilizationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title_txt);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title_txt = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_operational_utilization, container, false);
        choose_from = (CustomLightTextView) view.findViewById(R.id.chooseFrom);
        choose_to = (CustomLightTextView) view.findViewById(R.id.chooseTo);
        customDatePicker = (CustomDatePicker) view.findViewById(R.id.cDate);
        mBackL = (LinearLayout) view.findViewById(R.id.back);
        mBackL.setOnClickListener(this);
        choose_from.setOnClickListener(this);
        choose_to.setOnClickListener(this);
        graph = (GraphView) view.findViewById(R.id.graph);
        buildGraph();
        title= (CustomBoldTextView) view.findViewById(R.id.title);
        title.setText(title_txt.toString());

        return view;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
//            view.setMinDate(System.currentTimeMillis() - 1000);
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            if (flag == 1)
                choose_from.setText(day1 + "/" + month1 + "/" + year1);
            else
                choose_to.setText(day1 + "/" + month1 + "/" + year1);
        }
    };

    private void buildGraph() {


        LineGraphSeries<DataPoint> line_series =
                new LineGraphSeries<DataPoint>(new DataPoint[]{
                        new DataPoint(0, 0),
                        new DataPoint(4, 2.5),
                        new DataPoint(6, 1.5),
                        new DataPoint(13.2, 5.3),
                        new DataPoint(18.14, 2.3),
                        new DataPoint(21.2, 6.3),
                        new DataPoint(22.4, 4),
                        new DataPoint(28, 9.6),
                        new DataPoint(32, 8.3),
                });
        graph.getGraphContentHeight();

        line_series.setColor(getResources().getColor(R.color.light_blue));

        graph.getLegendRenderer().setVisible(false);
        graph.getGridLabelRenderer().setGridColor(getResources().getColor(R.color.color1));

        graph.addSeries(line_series);

        graph.getGridLabelRenderer().setTextSize(32);
        graph.getGridLabelRenderer().reloadStyles();

        graph.getGridLabelRenderer().setHorizontalAxisTitleColor(getResources().getColor(R.color.transparent));

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"X", "X", "X", "X", "X", "X", "X", "X"});
        staticLabelsFormatter.setVerticalLabels(new String[]{"0Y", "50Y", "100Y", "150Y", "200Y", "250Y", "300Y", "350Y", "400Y", "450Y", "500Y"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseFrom:
                flag = 1;
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show((getActivity().getSupportFragmentManager()), "DatePicker");
                break;
            case R.id.chooseTo:
                flag = 2;
                DialogFragment newFragment_ = new SelectDateFragment();
                newFragment_.show((getActivity().getSupportFragmentManager()), "DatePicker");
                break;
            case R.id.back:
                getActivity().onBackPressed();
        }
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, yy, mm, dd);
//            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
            datePicker.getDatePicker().setMaxDate(new Date().getTime());
            //-(1000*60*60*24)
            return datePicker;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {

            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {

            if (flag == 1)
                choose_from.setText(day + "/" + month + "/" + year);
            else
                choose_to.setText(day + "/" + month + "/" + year);

        }
    }
}



