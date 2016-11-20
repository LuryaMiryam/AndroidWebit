package webit.bthereapp.CustemViews.Fonts;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import webit.bthereapp.CustemViews.CustomTimePickerAdapter;
import webit.bthereapp.R;

/**
 * Created by User on 13/03/2016.
 */
public class CustemCalanderDisplayMonth extends RelativeLayout {
    private String titleText = "";

    public String getTitleTextView() {
        return String.valueOf(titleTextView.getText());
    }

    private TextView titleTextView;
    private SpannableString content;
    private ImageView mCircleBlack;
    private ImageView mCircleOrange;
    private View view_;
    RelativeLayout mRelativeLayout;
    LinearLayout.LayoutParams layoutParams;

    public CustemCalanderDisplayMonth(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustemCalanderDisplayMonth(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomCalnderDisplayMonth, 0, 0);
        try {
            titleText = a.getString(R.styleable.CustomCalnderDisplayMonth_numDay);
        } finally {
            a.recycle();
        }
        mRelativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.custom_calander_display_month, this);
        titleTextView = (TextView) this.findViewById(R.id.textViewDays);
        titleTextView.setText(titleText);
        mCircleBlack = (ImageView) this.findViewById(R.id.circleBlackIV);
        mCircleOrange = (ImageView) this.findViewById(R.id.circleOrangeIV);
        view_=this.findViewById(R.id.view_);
        layoutParams = new LinearLayout.LayoutParams(0, AbsListView.LayoutParams.MATCH_PARENT, 1f);

    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        if (titleText != null)
            this.titleTextView.setText(titleText);
        invalidate();
        requestLayout();
    }

    public void setmCircleBlack(boolean aBoolean) {
        if (aBoolean)
            mCircleBlack.setVisibility(VISIBLE);
        else mCircleBlack.setVisibility(INVISIBLE);
    }

    public void setmCircleOrange(boolean aBoolean) {
        if (aBoolean)
            mCircleOrange.setVisibility(VISIBLE);
        else mCircleOrange.setVisibility(INVISIBLE);
    }

    public void setBlueBackGround(boolean aBoolean) {
        if (aBoolean) {
            mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
            layoutParams.setMargins(2, 4, 2, 7);
            mRelativeLayout.setLayoutParams(layoutParams);
            titleTextView.setGravity(CENTER_IN_PARENT);
        } else mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    public void setUnderline(boolean bool) {
        content = new SpannableString(titleTextView.getText());
        UnderlineSpan underlineSpan = new UnderlineSpan();
        content.setSpan(underlineSpan, 0, content.length(), 0);
        if (!bool)
            content.removeSpan(underlineSpan);
        titleTextView.setText(content);
    }


    public void setColorTextView(int color) {
        titleTextView.setTextColor(color);
        view_.setBackgroundColor(color);
    }

    public void setColorOrange(){
        titleTextView.setTextColor(getResources().getColor(R.color.color3));
    }

    public void setColorBlue(){
        titleTextView.setTextColor(getResources().getColor(R.color.light_blue));
    }

    public void setColor(boolean color) {
        if(color)
            setColorBlue();
        else setColorOrange();
    }

    public void setBockGroundCircleblack(boolean bool) {
        if (bool)
            titleTextView.setBackground((getResources().getDrawable(R.drawable.black_circle)));
        else titleTextView.setBackgroundColor((getResources().getColor(R.color.transparent)));
    }


}