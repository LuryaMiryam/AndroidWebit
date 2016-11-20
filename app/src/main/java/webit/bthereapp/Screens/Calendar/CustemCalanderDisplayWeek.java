package webit.bthereapp.Screens.Calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.R;

/**
 * Created by User on 17/03/2016.
 */
public class CustemCalanderDisplayWeek extends LinearLayout {
    private String titleTextNum = "";
    private CustomBoldTextView titleTextViewNum;
    private String titleTextName;
    private CustomBoldTextView titleTextViewName;
    private LinearLayout linearLayout;

    public CustemCalanderDisplayWeek(Context context) {
        super(context);
        init(context, null);
    }

    public CustemCalanderDisplayWeek(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustemCalanderDisplayWeek, 0, 0);
        try {
            titleTextNum = a.getString(R.styleable.CustemCalanderDisplayWeek_numDayWeek);
            titleTextName = a.getString(R.styleable.CustemCalanderDisplayWeek_nameDay);
        } finally {
            a.recycle();
        }
        LayoutInflater.from(context).inflate(R.layout.custom_calander_display_week, this);
        titleTextViewNum = (CustomBoldTextView) this.findViewById(R.id.textViewNumDay);
        titleTextViewNum.setText(titleTextNum);
        titleTextViewName = (CustomBoldTextView) this.findViewById(R.id.textViewNameDay);
        titleTextViewName.setText(titleTextName);
        linearLayout = (LinearLayout) this.findViewById(R.id.linearLayoutMainCalanderWeek1);
    }

    public void setBackGrounColorOrange() {
        linearLayout.setBackgroundColor(getResources().getColor(R.color.color3));
    }

    public void setBackGrounColorBlue() {
        linearLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
    }

    public void setBackGrounColor(boolean flag) {
        if (flag)
            setBackGrounColorBlue();
        else setBackGrounColorOrange();
    }

    public void setTitleTextNum(String titleText) {
        if (titleText != null)
            this.titleTextViewNum.setText(titleText);
        invalidate();
        requestLayout();
    }

    public void setTitleTextName(String titleText) {
        if (titleText != null)
            this.titleTextViewName.setText(titleText);
        invalidate();
        requestLayout();
    }

    public String getTitleTextName() {
        return this.titleTextViewName.getText().toString();
    }

    public void setBockGroundCircleblack(boolean bool) {
        if (bool)
            linearLayout.setBackground((getResources().getDrawable(R.drawable.black_circle)));
        else linearLayout.setBackgroundColor((getResources().getColor(R.color.transparent)));
    }

}



