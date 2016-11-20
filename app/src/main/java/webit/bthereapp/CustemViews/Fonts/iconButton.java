package webit.bthereapp.CustemViews.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by user on 11/05/2016.
 */
public class iconButton extends Button {
        public iconButton(Context context) {
            super(context);
            this.setClickable(true);
            if (!isInEditMode())
                this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf"));
        }

        public iconButton(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.setClickable(true);
            if (!isInEditMode())
                this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf"));
        }

        public iconButton(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.setClickable(true);
            if (!isInEditMode())
                this.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSansHebrew/fontawesome-webfont.ttf"));
        }
    }

