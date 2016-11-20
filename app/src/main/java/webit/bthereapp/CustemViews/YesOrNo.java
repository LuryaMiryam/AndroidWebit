package webit.bthereapp.CustemViews;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.ContactListFragment;

public class YesOrNo extends LinearLayout implements View.OnClickListener{

    private int m=0;
    private ImageButton x_, v_;


    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public YesOrNo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public void init(final Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.activity_yes_or_no, this);
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
                m = 0;
                x_.setImageResource(R.drawable.client_galaxy_icons1_38);
                v_.setImageResource(R.drawable.client_galaxy_icons1_37);
                break;
            }
            //change to yes
            case R.id.v_: {
                m = 1;
                x_.setImageResource(R.drawable.client_galaxy_icons1_36);
                v_.setImageResource(R.drawable.client_galaxy_icons1_39);
                break;
            }

        }
    }

}
