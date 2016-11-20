package webit.bthereapp.CustemViews.Fonts;

/**
 * Created by user on 07/03/2016.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import webit.bthereapp.R;


public class CustomBoldTextView extends TextView {
    String fontType;
    Context mContext;
    int value;

    public CustomBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (!isInEditMode()) {
            TypedArray a = mContext.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.CustomBoldTextView, 0, 0);
            try {
                value = a.getInt(R.styleable.CustomBoldTextView_change_font_BTV, 0);
                if (value == 0)
                    fontType = "OpenSansHebrew-Bold.ttf";
                if (value == 1)
                    fontType = "OpenSansHebrew-Light.ttf";
                if (value == 2)
                    fontType = "OpenSansHebrew-Regular.ttf";
            } finally {
                a.recycle();
            }
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSansHebrew/" + fontType));
        }
    }

    public void setStyle(int a) {
        switch (a) {
            case 0: {
                fontType = "OpenSansHebrew-Bold.ttf";
//                a.recycle();
                this.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSansHebrew/" + fontType));
                break;
            }
            case 1: {
                fontType = "OpenSansHebrew-Light.ttf";
//                a.recycle();
                this.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSansHebrew/" + fontType));
                break;
            }
            case 2: {
                fontType = "OpenSansHebrew-Regular.ttf";//                a.recycle();
                this.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSansHebrew/" + fontType));
                break;
            }
        }
    }


}