package webit.bthereapp.CustemViews.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by user on 07/03/2016.
 */
public class CustomRegularTextView extends TextView {
    public CustomRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSansHebrew/" + "OpenSansHebrew-Regular.ttf"));
        }
    }
}
