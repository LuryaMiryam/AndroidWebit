package webit.bthereapp.CustemViews;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;

/**
 * Created by User on 04/07/2016.
 */
public class CustomViewTimePicker extends LinearLayout implements View.OnTouchListener {
    public PickerView from_pv;
    public PickerView to_pv;
    private Context mContext;
    public String from, to;
    List<String> froms, tos;
    private MyListener myListener;
//    Handler handler;

    public CustomViewTimePicker(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CustomViewTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        final View view = View.inflate(mContext, R.layout.custom_view_time_picker, this);
        from_pv = (PickerView) view.findViewById(R.id.from_pv);
        to_pv = (PickerView) view.findViewById(R.id.to_pv);
        froms = new ArrayList<>();
        tos = new ArrayList<>();
        froms.add("23:00");
        froms.add("23:30");
        tos.add("23:00");
        tos.add("23:30");
        for (int i = 0; i < 23; i++) {
            froms.add(i < 10 ? "0" + i + ":00" : i + ":00");
            froms.add(i < 10 ? "0" + i + ":30" : i + ":30");
            tos.add(i < 10 ? "0" + i + ":00" : i + ":00");
            tos.add(i < 10 ? "0" + i + ":30" : i + ":30");
        }
        from_pv.setData(froms);
        from_pv.setSelected("00:00");
        from = "00:00";
        from_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                from = text;
                if (myListener != null)
                    myListener.ScrollHour();
            }
        });
        to_pv.setData(tos);
        to_pv.setSelected("00:00");
        to = "00:00";
        to_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                to = text;
                if (myListener != null) {
                    myListener.ScrollHour();
                }
//                handler.sendMessage(handler.obtainMessage());
            }
        });
        from_pv.setOnTouchListener(this);
        to_pv.setOnTouchListener(this);
    }


    public void setSelected(String selectedFrom, String selectedto) {
        from = selectedFrom;
        to = selectedto;
    }

    public void setSelectedTop(String selectedFrom, String selectedto) {
        if (froms.indexOf(selectedFrom) != froms.size() - 1) {
            from = froms.get((froms.indexOf(selectedFrom)) + 1);
        }
        if (tos.indexOf(selectedto) != 0) {
            to = tos.get((tos.indexOf(selectedto)) - 1);
        }
    }

    @Override
    public String toString() {
        return from + " - " + to;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.requestFocus();
        v.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }


    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    public interface MyListener {
        void ScrollHour();
    }
}
