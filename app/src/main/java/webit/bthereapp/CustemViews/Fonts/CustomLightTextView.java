package webit.bthereapp.CustemViews.Fonts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.TextView;

import webit.bthereapp.R;

/**
 * Created by user on 07/03/2016.
 */
public class CustomLightTextView extends TextView {
    Context mContext;
    AttributeSet mAttributeSet;



    String fontType;
    int value;

    public CustomLightTextView(Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mAttributeSet = attrs;
        if (!isInEditMode()) {
            TypedArray a = mContext.getTheme().obtainStyledAttributes(
                    mAttributeSet, R.styleable.CustomLightTextView, 0, 0);
            try {
                value = a.getInt(R.styleable.CustomLightTextView_change_font_LTV, 0);
                if (value == 0)
                    fontType = "OpenSansHebrew-Light.ttf";
                if (value == 1)
                    fontType = "OpenSansHebrew-Bold.ttf";
                if (value == 2)
                    fontType = "OpenSansHebrew-Regular.ttf";
            } finally {
                a.recycle();
            }
        }
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSansHebrew/" + fontType));


    }

    public void init(int i) {
        switch (i) {
            case 0:
                fontType = "OpenSansHebrew-Light.ttf";
                break;
            case 1:
                fontType = "OpenSansHebrew-Bold.ttf";
                break;
            case 2:
                fontType = "OpenSansHebrew-Regular.ttf";
                break;
        }
        this.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSansHebrew/" + fontType));

    }

    public void ChangeFont(int i) {
        switch (i) {
            case 0:
                fontType = "OpenSansHebrew-Light.ttf";
                break;
            case 1:
                fontType = "OpenSansHebrew-Bold.ttf";
                break;
            case 2:
                fontType = "OpenSansHebrew-Regular.ttf";
                break;
        }
        this.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSansHebrew/" + fontType));

    }
}
