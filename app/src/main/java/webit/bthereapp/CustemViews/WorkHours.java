package webit.bthereapp.CustemViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import webit.bthereapp.Entities.ProviderGeneralDetailsObj;
import webit.bthereapp.Entities.WorkingHours;
import webit.bthereapp.Intreface.AddOnClickListener;
import webit.bthereapp.R;

/**
 * Created by user on 17/05/2016.
 */
public class WorkHours extends LinearLayout implements View.OnClickListener, AdapterView.OnItemClickListener {

    List myHoursList;
    String[] strings;
    Button mSaveBreaksB;
    LinearLayout mAllL;
    private ListView  mWorkingList, mRaceList;
    private HashMap<Integer, String> mDayList = new HashMap<>();
    Context mContext;
    private TimeText r1, r2, r3, r4, r5, r6, r11, r12, r13, r14, r15, r16, w1, w2, w3, w4, w5, w6, w11, w12, w13, w14, w15, w16;

    public WorkHours(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.custem_working_hours, this);
       mContext = context;
        myHoursList = new ArrayList();
        strings = getResources().getStringArray(R.array.days);
        List<String> stringList = new ArrayList<String>(Arrays.asList(strings));
        myHoursList = stringList;


        mSaveBreaksB = (Button) view.findViewById(R.id.save_breaks);
        mAllL= (LinearLayout) view.findViewById(R.id.all_hours);
        mSaveBreaksB.setOnClickListener(this);


        mWorkingList = (ListView) view.findViewById(R.id.working_hours_list);
        mRaceList = (ListView) view.findViewById(R.id.race_hours_list);

        mWorkingList.setOnItemClickListener(this);
        mRaceList.setOnItemClickListener(this);


        strings = getResources().getStringArray(R.array.days);
        for (int i = 0; i < strings.length; i++)
            mDayList.put(i, strings[i]);

          /* r- breacking
        * w-  worker
        * 1- day
        * 0- to
        * 1- from*/
        r1 = (TimeText) view.findViewById(R.id.r1);
        r1.setTag("r10");
        r2 = (TimeText) view.findViewById(R.id.r2);
        r2.setTag("r20");
        r3 = (TimeText) view.findViewById(R.id.r3);
        r3.setTag("r30");
        r4 = (TimeText) view.findViewById(R.id.r4);
        r4.setTag("r40");
        r5 = (TimeText) view.findViewById(R.id.r5);
        r5.setTag("r50");
        r6 = (TimeText) view.findViewById(R.id.r6);
        r6.setTag("r60");
        r11 = (TimeText) view.findViewById(R.id.r11);
        r11.setTag("r11");
        r12 = (TimeText) view.findViewById(R.id.r12);
        r12.setTag("r21");
        r13 = (TimeText) view.findViewById(R.id.r13);
        r13.setTag("r31");
        r14 = (TimeText) view.findViewById(R.id.r14);
        r14.setTag("r41");
        r15 = (TimeText) view.findViewById(R.id.r15);
        r15.setTag("r51");
        r16 = (TimeText) view.findViewById(R.id.r16);
        r16.setTag("r61");


        w1 = (TimeText) view.findViewById(R.id.w1);
        w1.setTag("w10");
        w2 = (TimeText) view.findViewById(R.id.w2);
        w2.setTag("w20");
        w3 = (TimeText) view.findViewById(R.id.w3);
        w3.setTag("w30");
        w4 = (TimeText) view.findViewById(R.id.w4);
        w4.setTag("w40");
        w5 = (TimeText) view.findViewById(R.id.w5);
        w5.setTag("w50");
        w6 = (TimeText) view.findViewById(R.id.w6);
        w6.setTag("w60");
        w11 = (TimeText) view.findViewById(R.id.w11);
        w11.setTag("w11");
        w12 = (TimeText) view.findViewById(R.id.w12);
        w12.setTag("w21");
        w13 = (TimeText) view.findViewById(R.id.w13);
        w13.setTag("w31");
        w14 = (TimeText) view.findViewById(R.id.w14);
        w14.setTag("w41");
        w15 = (TimeText) view.findViewById(R.id.w15);
        w15.setTag("w51");
        w16 = (TimeText) view.findViewById(R.id.w16);
        w16.setTag("w61");


        r1.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r1, w1, w11, false);
            }
        });
        r2.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r2, w2, w12, false);
            }
        });
        r3.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r3, w3, w13, false);
            }
        });
        r4.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r4, w4, w14, false);
            }
        });
        r5.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r5, w5, w15, false);
            }
        });
        r6.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r6, w6, w16, false);
            }
        });
        r11.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r11, w1, w11, false);
            }
        });
        r12.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r12, w2, w12, false);
            }
        });
        r13.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r13, w3, w13, false);
            }
        });
        r14.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r14, w4, w14, false);
            }
        });
        r15.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r15, w5, w15, false);
            }
        });
        r16.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(r16, w6, w16, false);
            }
        });


        w1.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w1, null, w11, false);
            }
        });
        w2.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w2, null, w12, false);
            }
        });
        w3.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w3, null, w13, false);
            }
        });
        w4.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w4, null, w14, false);
            }
        });
        w5.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w5, null, w15, false);
            }
        });
        w6.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w6, null, w16, false);
            }
        });
        w11.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w11, w1, null, false);
            }
        });
        w12.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w12, w2, null, false);
            }
        });
        w13.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w13, w3, null, false);
            }
        });
        w14.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w14, w4, null, false);
            }
        });
        w15.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w15, w5, null, false);
            }
        });
        w16.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                checkTime(w16, w6, null, false);
            }
        });
    }

    public void checkTime(TimeText timeTextR, TimeText timeTextWfrom, TimeText timeTextWto, boolean isFrom) {
        boolean isValid = true;
        String pattern = "HH:mm";
        Date date1 = null, date2 = null, date3 = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date1 = sdf.parse(timeTextR.getText());
            if (timeTextWfrom != null)
                date2 = sdf.parse(timeTextWfrom.getText());
            if (timeTextWto != null)
                date3 = sdf.parse(timeTextWto.getText());
            //if  begin hour biger enter
            if (date2 != null)
                if (date1.compareTo(date2) == -1) {
                    isValid = false;
                    timeTextR.setText(sdf.format(date2));
                }
            //if  finish hour smaaler enter
            if (date3 != null)
                if (date3.compareTo(date1) == -1) {
                    isValid = false;
                    timeTextR.setText(sdf.format(date3));
                }

        } catch (ParseException e) {
            // Exception handling goes here
        }
        if (!isValid) {
            Toast.makeText(mContext,getResources().getString(R.string.enter_hour_valid), Toast.LENGTH_LONG).show();
        } else {
            setData((String) timeTextR.getTag(), timeTextR.getText());
        }
    }

    //toOrFrom == 1; to;
    //toOrFrom == 0; from;
    private void setData(String name, String hour) {
        int day = 0, fromOrTO = 0;
        day = Integer.parseInt(String.valueOf(name.charAt(1)));
        fromOrTO = Integer.parseInt(String.valueOf(name.charAt(2)));
        if (name.contains("w")) {
            if (fromOrTO == 1) {
                insertList(day, 1, hour, 1);
            } else if (fromOrTO == 0) {
                insertList(day, 0, hour, 0);
            }
        } else if (name.contains("r")) {
            if (fromOrTO == 1) {
                insertList(day, 1, hour, 0);
            } else if (fromOrTO == 0) {
                insertList(day, 0, hour, 1);
            }

        }
    }
    //toOrFrom == 1; to;
//toOrFrom == 0; from;
    private void insertList(int day, int num, String hour, int toOrFrom) {
        for (int i = 0; i < ProviderGeneralDetailsObj.getInstance().getWorkingHours().size(); i++) {
            if (ProviderGeneralDetailsObj.getInstance().getWorkingHours().get(i).getiDayInWeekType() == day && ProviderGeneralDetailsObj.getInstance().getWorkingHours().get(i).getNum() == num) {
                if (toOrFrom == 1) {
                    ProviderGeneralDetailsObj.getInstance().getWorkingHours().get(i).setNvToHour(hour);
                    return;
                } else {
                    ProviderGeneralDetailsObj.getInstance().getWorkingHours().get(i).setNvFromHour(hour);
                    return;
                }
            }
        }
        if (toOrFrom == 1) {
            ProviderGeneralDetailsObj.getInstance().getWorkingHours().add(new WorkingHours(day, "", hour, num));
        } else {
            ProviderGeneralDetailsObj.getInstance().getWorkingHours().add(new WorkingHours(day, hour, "", num));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_breaks:
                mAllL.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}


