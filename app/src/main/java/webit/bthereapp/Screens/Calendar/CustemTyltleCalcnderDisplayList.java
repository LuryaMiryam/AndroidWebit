package webit.bthereapp.Screens.Calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.R;

/**
 * Created by User on 21/03/2016.
 */
public class CustemTyltleCalcnderDisplayList extends RelativeLayout {
    private String titleTextDateLIst = "";
    private CustomBoldTextView titleTextViewDeTeList;
    private String titleTextDayLIst;
    private CustomBoldTextView titleTextViewDayList;
    private RelativeLayout mainRelativeLayout;

    public CustemTyltleCalcnderDisplayList(Context context) {
        super(context);
        init(context, null);
    }

    public CustemTyltleCalcnderDisplayList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustemCalanderDisplayList, 0, 0);
        try {
            titleTextDayLIst = a.getString(R.styleable.CustemCalanderDisplayList_DayWeek);
            titleTextDateLIst = a.getString(R.styleable.CustemCalanderDisplayList_nameDate);
        } finally {
            a.recycle();
        }
        LayoutInflater.from(context).inflate(R.layout.custom_layout_tytle_calcnder_display_list, this);
        titleTextViewDeTeList = (CustomBoldTextView) this.findViewById(R.id.textViewDateList);
        titleTextViewDeTeList.setText(titleTextDateLIst);
        titleTextViewDayList = (CustomBoldTextView) this.findViewById(R.id.textViewDayListTitle);
        titleTextViewDayList.setText(titleTextDayLIst);
        mainRelativeLayout=(RelativeLayout)this.findViewById(R.id.mainRelativelayout);
    }

    public void setBackGroundOrange()
    {
        mainRelativeLayout.setBackgroundColor(getResources().getColor(R.color.color3));
    }

    public void SeBackGroundBlue()
    {
        mainRelativeLayout.setBackgroundColor(getResources().getColor(R.color.color4));
    }

    public void setBackGroundMain(boolean flag)
    {
        if(flag)
            SeBackGroundBlue();
        else setBackGroundOrange();
    }


    public void setTitleTextDate(String titleText) {
        if (titleText != null)
            this.titleTextViewDeTeList.setText(titleText);
        invalidate();
        requestLayout();
    }

    public void setTitleTextDay(String titleText) {
        if (titleText != null)
            this.titleTextViewDayList.setText(titleText);
        invalidate();
        requestLayout();
    }

    public void setBockGroundCircleblack(boolean bool) {
        if (bool)
            titleTextViewDayList.setBackground((getResources().getDrawable(R.drawable.black_circle)));
        else
            titleTextViewDayList.setBackgroundColor((getResources().getColor(R.color.transparent)));
    }
}
