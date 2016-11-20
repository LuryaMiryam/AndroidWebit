package webit.bthereapp.CustemViews;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.General.DateTimePicker.DatePicker;
import webit.bthereapp.General.DateTimePicker.TimePicker;
import webit.bthereapp.Intreface.AddOnClickListener;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;

/**
 * Created by User on 18/04/2016.
 */
public class TimeText extends LinearLayout implements View.OnClickListener {
    Context mContext;
    private TextView time;
    View view;
    Dialog vDialod = null;
    TimePicker timePicker;
    int hour = 0, minute = 0;
    AddOnClickListener addOnClickListener = null;

    public TimeText(Context context) {
        super(context);
    }

    public TimeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(final Context context, AttributeSet attrs) {
        view = View.inflate(context, R.layout.time_txt, this);
        this.mContext = context;
        time = (TextView) view.findViewById(R.id.txt);
        time.setOnClickListener(this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TimeText, 0, 0);

        int valueColor = a.getColor(R.styleable.TimeText_hour, 0);
        a.recycle();

        switch (valueColor) {
            case 1:
                time.setText("08:00");
                hour = 8;
                minute = 0;
                break;
            case 2:
                time.setText("20:00");
                hour = 20;
                minute = 0;
                break;
            default:
                hour = 14;
                minute = 0;
        }
    }

    public TextView getTextView() {
        return time;
    }


    @Override
    public void onClick(View v) {
        openDialogDate();
    }


    private void openDialogDate() {
        if (mContext != null) {
            vDialod = new Dialog(mContext);
            vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
            vDialod.setCanceledOnTouchOutside(false);
            vDialod.setContentView(R.layout.time_picker_dialog);
            timePicker = (TimePicker) vDialod.findViewById(R.id.timePicker);
            timePicker.setIs24Hour(true);
            Button btnOk = (Button) vDialod.findViewById(R.id.ok);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time.setText(timePicker.getTime());
                    if (addOnClickListener != null)
                        addOnClickListener.addOnClick();
                        vDialod.dismiss();
                }
            });
            Button btnBack = (Button) vDialod.findViewById(R.id.back);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vDialod.dismiss();
                }
            });
            vDialod.show();
        }
    }

    public String getText() {
        return time.getText().toString();
    }
    public void setText(String txt) {
        time.setText(txt);
    }

    public void setAddOnClickListener(AddOnClickListener addOnClickListener) {
        this.addOnClickListener = addOnClickListener;
    }
    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        private SimpleDateFormat mFormatter = new SimpleDateFormat("HH:mm");
        @Override
        public void onDateTimeSet(Date date)
        {
            time.setText(mFormatter.format(date));
            if (addOnClickListener != null)
                addOnClickListener.addOnClick();
        }
    };
}
