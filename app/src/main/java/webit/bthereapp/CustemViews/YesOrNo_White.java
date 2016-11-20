package webit.bthereapp.CustemViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import webit.bthereapp.Intreface.AddOnClickListener;
import webit.bthereapp.R;

public class YesOrNo_White extends LinearLayout implements View.OnClickListener {

    private int m =2;
    private ImageButton x_, v_;
    private AddOnClickListener addOnClickListenerX = null;
    private AddOnClickListener addOnClickListenerV = null;


    public int getYes_no_state() {
        return m;
    }
    public boolean getYes_no_state_b() {
        if(m==1)
            return true;
        return false;
    }


    public YesOrNo_White(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(final Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.activity_yes_or_no_white, this);
        x_ = (ImageButton) view.findViewById(R.id.x_w);
        v_ = (ImageButton) view.findViewById(R.id.v_w);
        x_.setOnClickListener(this);
        v_.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //change to no
            case R.id.x_w: {
                m = 0;
                x_.setImageResource(R.drawable.cancel_select_strock_black);
                v_.setImageResource(R.drawable.ok_strock_black);
                if (addOnClickListenerX != null)
                    addOnClickListenerX.addOnClick();
                break;
            }


            //change to yes

            case R.id.v_w: {
                m = 1;
                x_.setImageResource(R.drawable.cancel_strock_black);

                v_.setImageResource(R.drawable.ok_select_strock_black);
                if (addOnClickListenerV != null)
                    addOnClickListenerV.addOnClick();

                break;
            }
        }
    }

    public void change_status(boolean s) {
        if ((m == 0||m==2 ) && s == true) {
            x_.setImageResource(R.drawable.cancel_strock_black);

            v_.setImageResource(R.drawable.ok_select_strock_black);
            m = 1;
        } else if ((m == 1||m==2 ) && s == false) {
            x_.setImageResource(R.drawable.cancel_select_strock_black);
            v_.setImageResource(R.drawable.ok_strock_black);
            m = 0;
        }

    }

    public void setAddOnClickListenerX(AddOnClickListener addOnClickListenerX) {
        this.addOnClickListenerX = addOnClickListenerX;
    }

    public void setAddOnClickListenerV(AddOnClickListener addOnClickListenerV) {
        this.addOnClickListenerV = addOnClickListenerV;
    }
}
