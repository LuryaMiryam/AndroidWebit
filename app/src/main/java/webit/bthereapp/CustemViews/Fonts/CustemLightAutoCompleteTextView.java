package webit.bthereapp.CustemViews.Fonts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import webit.bthereapp.R;

/**
 * Created by user on 22/06/2016.
 */
public class CustemLightAutoCompleteTextView extends AutoCompleteTextView {
    Context mContext;
    AttributeSet mAttributeSet;
    String fontType;
    int value;

    public CustemLightAutoCompleteTextView(Context context) {
        super(context);
        this.mContext = context;
    }

    public CustemLightAutoCompleteTextView(Context context, AttributeSet attrs) {
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
    //when press back make the keyboard go away first nd not the list
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isPopupShowing()) {
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if(inputManager.hideSoftInputFromWindow(findFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS)){
                return true;
            }
        }

        return super.onKeyPreIme(keyCode, event);
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


}
