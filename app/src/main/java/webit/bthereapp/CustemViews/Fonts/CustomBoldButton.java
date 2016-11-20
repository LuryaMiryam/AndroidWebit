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
public class CustomBoldButton extends Button {
    String fontType;
    int value;
    public CustomBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.CustomBoldButton, 0, 0);
            try {
                value = a.getInt(R.styleable.CustomBoldTextView_change_font_BTV,0);
                if (value ==0)
                    fontType="OpenSansHebrew-Bold.ttf";
                if (value ==1)
                    fontType= "OpenSansHebrew-Light.ttf" ;
                if (value==2)
                    fontType="OpenSansHebrew-Regular.ttf";
            } finally {
                a.recycle();
            }

            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSansHebrew/" + fontType));
        }
    }
}
