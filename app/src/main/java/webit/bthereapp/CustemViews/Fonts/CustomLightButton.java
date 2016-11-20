package webit.bthereapp.CustemViews.Fonts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import webit.bthereapp.R;

/**
 * Created by user on 07/03/2016.
 */
public class CustomLightButton extends Button {
    String fontType;
    Context mContext;
    int value;
    public CustomLightButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        if (!isInEditMode()) {
            TypedArray a = mContext.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.CustomLightButton, 0, 0);
            try {
                value = a.getInt(R.styleable.CustomLightButton_change_font_LB,0);
                if (value ==0)
                    fontType="OpenSansHebrew-Light.ttf";
                if (value ==1)
                    fontType= "OpenSansHebrew-Bold.ttf";
                if (value==2)
                    fontType="OpenSansHebrew-Regular.ttf";
            } finally {
                a.recycle();
            }

            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSansHebrew/" + fontType));
        }
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

