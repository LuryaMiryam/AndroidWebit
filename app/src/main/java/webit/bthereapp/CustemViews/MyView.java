package webit.bthereapp.CustemViews;

/**
 * Created by User on 07/03/2016.
 */
import android.widget.TextView;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.content.res.Resources;

import webit.bthereapp.R;

public class MyView extends TextView {

    private static final int MAX_INDENT = 300;

    public MyView(Context context) {
        super(context);
        this.setTextColor(getResources().getColor(R.color.color_white));
    }

    public void onDraw(Canvas canvas){
        canvas.save();
        float indent = getIndent(getY());
        //Part of the magic happens here too
        canvas.translate(indent, 0);
        super.onDraw(canvas);
        canvas.restore();
    }

    public float getIndent(float distance){
        float x_vertex = MAX_INDENT;
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float y_vertex = displayMetrics.heightPixels / 2 / displayMetrics.density;
        double a = ( 0 - x_vertex ) / ( Math.pow(( 0 - y_vertex), 2) ) ;
        float indent = (float) (a * Math.pow((distance - y_vertex), 2) + x_vertex);
        return indent;
    }
}