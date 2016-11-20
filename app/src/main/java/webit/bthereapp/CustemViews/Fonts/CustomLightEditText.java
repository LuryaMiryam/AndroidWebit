package webit.bthereapp.CustemViews.Fonts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import webit.bthereapp.CustemViews.DetailsWorkers;
import webit.bthereapp.R;

/**
 * Created by user on 07/03/2016.
 */
public class CustomLightEditText extends EditText {
    int value;
    String fontType;
    public CustomLightEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomLightEditText, 0, 0);
            try {
                value = a.getInt(R.styleable.CustomLightEditText_change_font_LET,0);
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


}