package webit.bthereapp.CustemViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import webit.bthereapp.Intreface.AddOnClickListener;
import webit.bthereapp.R;

public class YesOrNo_Black extends LinearLayout implements View.OnClickListener {
    private int yes_no_state = -1;
    private ImageButton x_, v_;
    private AddOnClickListener addOnClickListenerX = null;
    private AddOnClickListener addOnClickListenerV = null;

    public int getYes_no_state() {
        return yes_no_state;
    }


    public YesOrNo_Black(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(final Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.activity_yes_or_black, this);
        x_ = (ImageButton) view.findViewById(R.id.x_);
        v_ = (ImageButton) view.findViewById(R.id.v_);
        x_.setOnClickListener(this);
        v_.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //change to no
            case R.id.x_: {
                yes_no_state = 0;
                x_.setImageResource(R.drawable.cancel_select);
                v_.setImageResource(R.drawable.ok);
                if (addOnClickListenerX != null)
                    addOnClickListenerX.addOnClick();
                break;
            }
            //change to yes
            case R.id.v_: {
                yes_no_state = 1;
                x_.setImageResource(R.drawable.cancel);
                v_.setImageResource(R.drawable.ok_select);
                if (addOnClickListenerV != null)
                    addOnClickListenerV.addOnClick();
                break;
            }

        }
    }

    public void change_status(boolean s) {
        if ((yes_no_state == 0 || yes_no_state == -1) && s == true) {
            x_.setImageResource(R.drawable.cancel);
            v_.setImageResource(R.drawable.ok_select);
            yes_no_state = 1;
        } else if ((yes_no_state == 1 || yes_no_state == -1) && s == false) {
            x_.setImageResource(R.drawable.cancel_select);
            v_.setImageResource(R.drawable.ok);
            yes_no_state = 0;
        }


    }

    public void change_status_no_selected() {
        x_.setImageResource(R.drawable.cancel);
        v_.setImageResource(R.drawable.ok);
        yes_no_state = -1;
    }

    public void setAddOnClickListenerX(AddOnClickListener addOnClickListenerX) {
        this.addOnClickListenerX = addOnClickListenerX;
    }

    public void setAddOnClickListenerV(AddOnClickListener addOnClickListenerV) {
        this.addOnClickListenerV = addOnClickListenerV;
    }
}
