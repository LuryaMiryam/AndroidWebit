package webit.bthereapp.CustemViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import webit.bthereapp.R;

/**
 * Created by User on 07/03/2016.
 */
public class CustomTimePicker extends RelativeLayout implements AbsListView.OnScrollListener, View.OnTouchListener {
    private int positionSelectedFrom = 0;
    private int positionSelectedTo = 0;
    private boolean scrollPerformed = false;
    public ListView listViewFrom;
    public ListView listViewTo;
    LinearLayout mLinearLayoutColor;
    private CustomTimePickerAdapter adapterF,adapterT,adapterFrom,adapterTo;
    View mView1;
    View mView2;
    View mView3;
    Context mContext;
    LinearLayout mLinearLayoutMiddle;
    public HashMap<Integer, String> mHoursList = new HashMap<>();


    public CustomTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if(isInEditMode())
        init(mContext, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.custem_time_picker_layout, this);
        listViewFrom = (ListView) view.findViewById(R.id.from);
        listViewTo = (ListView) view.findViewById(R.id.to);
        mView1 = (View) view.findViewById(R.id.View1);
        mView2 = (View) view.findViewById(R.id.View2);
        mView3 = (View) view.findViewById(R.id.View3);
        mLinearLayoutColor = (LinearLayout) view.findViewById(R.id.linearLayoutColor);
        mLinearLayoutMiddle = (LinearLayout) view.findViewById(R.id.linearDarkGreenTran);
        mHoursList.put(0, "01:00");
        mHoursList.put(1, "01:30");
        mHoursList.put(2, "02:00");
        mHoursList.put(3, "02:30");
        mHoursList.put(4, "03:00");
        mHoursList.put(5, "03:30");
        mHoursList.put(6, "04:00");
        mHoursList.put(7, "04:30");
        mHoursList.put(8, "05:00");
        mHoursList.put(9, "05:30");
        mHoursList.put(10, "06:00");
        mHoursList.put(11, "06:30");
        mHoursList.put(12, "07:00");
        mHoursList.put(13, "07:30");
        mHoursList.put(14, "08:00");
        mHoursList.put(15, "08:30");
        mHoursList.put(16, "09:00");
        mHoursList.put(17, "09:30");
        mHoursList.put(18, "10:00");
        mHoursList.put(19, "10:30");
        mHoursList.put(20, "11:00");
        mHoursList.put(21, "11:30");
        mHoursList.put(22, "12:00");
        mHoursList.put(23, "12:30");
        mHoursList.put(24, "13:00");
        mHoursList.put(25, "13:30");
        mHoursList.put(26, "14:00");
        mHoursList.put(27, "14:30");
        mHoursList.put(28, "15:00");
        mHoursList.put(29, "15:30");
        mHoursList.put(30, "16:00");
        mHoursList.put(31, "16:30");
        mHoursList.put(32, "17:00");
        mHoursList.put(33, "17:30");
        mHoursList.put(34, "18:00");
        mHoursList.put(35, "18:30");
        mHoursList.put(36, "19:00");
        mHoursList.put(37, "19:30");
        mHoursList.put(38, "20:00");
        mHoursList.put(39, "20:30");
        mHoursList.put(40, "21:00");
        mHoursList.put(41, "21:30");
        mHoursList.put(42, "22:00");
        mHoursList.put(43, "22:30");
        mHoursList.put(44, "23:00");
        mHoursList.put(45, "23:30");
        mHoursList.put(46, "24:00");
        mHoursList.put(47, "24:30");

         adapterFrom=new CustomTimePickerAdapter(mHoursList,context );
        listViewFrom.setAdapter(adapterFrom);
        listViewFrom.post(new Runnable() {
            @Override
            public void run() {
                listViewFrom.setSelectionFromTop(adapterFrom.MIDDLE, 0);
            }
        });;
         adapterTo=new CustomTimePickerAdapter(mHoursList, context);
        listViewTo.setAdapter(adapterTo);
        listViewTo.post(new Runnable() {
            @Override
            public void run() {
                listViewTo.setSelectionFromTop(adapterTo.MIDDLE, 0);
            }
        });
        listViewFrom.setOnScrollListener(this);
        listViewTo.setOnScrollListener(this);
        listViewFrom.setOnTouchListener(this);
        listViewTo.setOnTouchListener(this);
        int fromPosition = getHourPosition("08:00");
        listViewFrom.setSelection(fromPosition - 2);
        listViewTo.setSelection(fromPosition - 2);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        visibleItemCount = visibleItemCount - 1;
        int half = visibleItemCount / 2;
        if (visibleItemCount % 2 != 0) {
            half = half + 1;
        }
        switch (view.getId()) {
            case R.id.from: {
                scrollPerformed = true;
                positionSelectedFrom = firstVisibleItem + half;
                break;
            }
            case R.id.to: {
                scrollPerformed = true;
                positionSelectedTo = firstVisibleItem + half;
                break;
            }
        }
    }

    public void setColorTransparen() {
        mLinearLayoutColor.setBackgroundColor(getResources().getColor(R.color.transparent));
        mView1.setBackgroundColor(getResources().getColor(R.color.black));
        mView2.setBackgroundColor(getResources().getColor(R.color.black));
        mView3.setBackgroundColor(getResources().getColor(R.color.black));
        mLinearLayoutMiddle.setBackgroundColor(getResources().getColor(R.color.color6trans));
        int height = 125;
        RelativeLayout.LayoutParams layoutSecond = (RelativeLayout.LayoutParams) mLinearLayoutMiddle.getLayoutParams();
        layoutSecond.height = height;
        mLinearLayoutMiddle.setLayoutParams(layoutSecond);
        boolean flag = true;
        adapterF =new CustomTimePickerAdapter(mHoursList, mContext, flag);
        listViewFrom.setAdapter(adapterF);
        listViewFrom.post(new Runnable() {
            @Override
            public void run() {
                listViewFrom.setSelectionFromTop(adapterF.MIDDLE, 0);
            }
        });
        adapterT =new CustomTimePickerAdapter(mHoursList, mContext, flag);
        listViewTo.setAdapter(adapterT);
        listViewTo.post(new Runnable() {
            @Override
            public void run() {
        listViewTo.setSelectionFromTop(adapterT.MIDDLE, 0);
            }
        });
        listViewFrom.setOnScrollListener(this);
        listViewTo.setOnScrollListener(this);
        listViewFrom.setOnTouchListener(this);
        listViewTo.setOnTouchListener(this);
    }

    public int getHourPosition(String hour) {
        for (Map.Entry<Integer, String> entry : mHoursList.entrySet()) {
            if (entry.getValue().equals(hour))
                return entry.getKey();
        }
        return 0;
    }

    public String getFromHour() {
        return ((CustomTimePickerAdapter) listViewFrom.getAdapter()).getSelected(positionSelectedFrom);
    }

    public String getToHour() {
        return ((CustomTimePickerAdapter) listViewTo.getAdapter()).getSelected(positionSelectedTo);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.requestFocus();
        v.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }

    public int getPositionSelectedTo() {
        return positionSelectedTo;
    }

    public void setPositionSelectedTo(int positionSelectedTo) {
        this.positionSelectedTo = positionSelectedTo;
    }

    public int getPositionSelectedFrom() {
        return positionSelectedFrom;
    }

    public void setPositionSelectedFrom(int positionSelectedFrom) {
        this.positionSelectedFrom = positionSelectedFrom;
    }

    public ListView getListViewFrom() {
        return listViewFrom;
    }

    public HashMap<Integer, String> getmHoursList() {
        return mHoursList;
    }


    public ListView getListViewTo() {
        return listViewTo;
    }

    public boolean isScrollPerformed() {
        return scrollPerformed;
    }

    public void setScrollPerformed(boolean scrollPerformed) {
        this.scrollPerformed = scrollPerformed;
    }
    public void sortHours(HashMap<Integer, String> mHoursList){
        adapterF.Clear();
        adapterF.addAll(mHoursList);
        adapterF.notifyDataSetChanged();

        adapterT.Clear();
        adapterT.addAll(mHoursList);
        adapterT.notifyDataSetChanged();

        adapterFrom.Clear();
        adapterFrom.addAll(mHoursList);
        adapterFrom.notifyDataSetChanged();

        adapterTo.Clear();
        adapterTo.addAll(mHoursList);
        adapterTo.notifyDataSetChanged();
    }

}
