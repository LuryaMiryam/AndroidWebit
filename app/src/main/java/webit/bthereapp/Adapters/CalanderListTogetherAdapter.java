package webit.bthereapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.ProviderServiceDetailsObj;
import webit.bthereapp.Entities.TasksCalander;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.OrderDetailsFragment;


/**
 * Created by User on 22/03/2016.
 */
public class CalanderListTogetherAdapter extends BaseAdapter {
    private Context mContext;
    private int count = 0;
    private int minHeight;
    private Object mTasksCalanderList[];
    private RelativeLayout relativeLayout_list;
    private View convertView;

    public CalanderListTogetherAdapter(Context context, Object object[], int location, int height) {
        mContext = context;
        //get the items -meeting to this day
        mTasksCalanderList = getList(object, location);
        count = mTasksCalanderList.length;
        minHeight = height;
    }

    @Override
    public int getCount() {
        return mTasksCalanderList.length;
    }

    @Override
    public Object getItem(int position) {
        return mTasksCalanderList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView_, ViewGroup parent) {
        boolean b = false;//if is queue or task
        TasksCalander tt = new TasksCalander();
        OrderDetailsObj oo = new OrderDetailsObj();
        if (mTasksCalanderList[position] instanceof TasksCalander) {
            tt = (TasksCalander) mTasksCalanderList[position];
            b = true;
        } else {
            oo = (OrderDetailsObj) mTasksCalanderList[position];
            b = false;
        }

        final CustomLightTextView textViewDate;
        final CustomLightTextView textViewDay;
        final int mPosition = position;
        final ImageButton mImageButtonWase;
        convertView = View.inflate(mContext, R.layout.row_in_list_calcnder, null);
        View view__ = (View) convertView.findViewById(R.id.view_a);
        textViewDate = (CustomLightTextView) convertView.findViewById(R.id.textViewDate);
        textViewDay = (CustomLightTextView) convertView.findViewById(R.id.textViewTitle1);
        if (mTasksCalanderList[position] instanceof OrderDetailsObj) {
            view__.setVisibility(View.GONE);
            textViewDate.ChangeFont(1);
            textViewDay.ChangeFont(1);
            convertView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {

                                                   //open order details to make queue

                                                   OrderDetailsObj oo = (OrderDetailsObj) mTasksCalanderList[position];
                                                   Calendar calendar = Calendar.getInstance();
                                                   OrderDetailsFragment.setInstance(null);
                                                   calendar.setTime(ConnectionUtils.convertJsonDate_2(oo.getDtDateOrder()));
                                                   SimpleDateFormat mFormatter_2 = new SimpleDateFormat("HH:mm");
                                                   OrderDetailsFragment.getInstance().set_hour(mFormatter_2.format(calendar.getTime()));

                                                   SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
                                                   String formatted = format1.format(calendar.getTime());
                                                   OrderDetailsFragment.getInstance().set_date(formatted);
                                                   String s = "";
                                                   for (ProviderServiceDetailsObj providerServiceDetailsObj : oo.getObjProviderServiceDetails())
                                                       if (s.equals(""))
                                                           s += providerServiceDetailsObj.getNvServiceName();
                                                       else
                                                           s += (", " + providerServiceDetailsObj.getNvServiceName());
                                                   OrderDetailsFragment.getInstance().set_service_name(s);
                                                   OrderDetailsFragment.getInstance().set_b_name(oo.getNvSupplierName());
                                                   OrderDetailsFragment.getInstance().set_adress(oo.getNvAddress());
                                                   OrderDetailsFragment.getInstance().set_day(calendar.get(Calendar.DAY_OF_WEEK));
                                                   calendar.setTime(ConnectionUtils.JsonDateToDate(oo.getNvToHour()));
                                                   OrderDetailsFragment.getInstance().setHourEnd(mFormatter_2.format(calendar.getTime()));

                                                   ((NavigetionLayout) mContext).initFragmentMain(OrderDetailsFragment.getInstance(), true);

                                               }
                                           }

            );
        } else {
            view__.setVisibility(View.VISIBLE);
            textViewDate.ChangeFont(0);
            textViewDay.ChangeFont(0);
        }

        mImageButtonWase = (ImageButton) convertView.findViewById(R.id.waseIB);
        mImageButtonWase.setOnClickListener(new View.OnClickListener()

                                            {
                                                @Override
                                                public void onClick(View v) {

                                                    //to see the place

                                                    String location;
                                                    TasksCalander t;
                                                    OrderDetailsObj o;
                                                    if (mTasksCalanderList[mPosition] instanceof TasksCalander) {
                                                        t = (TasksCalander) mTasksCalanderList[mPosition];
                                                        location = t.getmLocation();
                                                    } else {
                                                        o = (OrderDetailsObj) mTasksCalanderList[mPosition];
                                                        location = o.getNvAddress();
                                                    }
                                                    String url = "waze://?q=" + location;
                                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                                    mContext.startActivity(intent);
                                                }
                                            }

        );
        relativeLayout_list = (RelativeLayout) convertView.findViewById(R.id.line);
        int HOUR;
        int MINUTE;
        int HOUREND;
        int MINUTEEND;
        if (b)

        {
            HOUR = tt.getmCalendarStart().getTime().getHours();
            MINUTE = tt.getmCalendarStart().getTime().getMinutes();
            HOUREND = tt.getmCalendarEnd().getTime().getHours();
            MINUTEEND = tt.getmCalendarEnd().getTime().getMinutes();
        } else

        {
            java.util.Date date;
            date = ConnectionUtils.JsonDateToDate(oo.getDtDateOrder());
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            HOUR = c.getTime().getHours();
            MINUTE = c.getTime().getMinutes();
            date = ConnectionUtils.JsonDateToDate(oo.getNvToHour());
            c.setTime(date);
            HOUREND = c.getTime().getHours();
            MINUTEEND = c.getTime().getMinutes();
        }

        String minuteTextStart = MINUTE < 10 ? "0" + String.valueOf(MINUTE) : String.valueOf(MINUTE);
        String hourTextStart = HOUR < 10 ? "0" + String.valueOf(HOUR) + ":" + minuteTextStart : String.valueOf(HOUR) + ":" + minuteTextStart;
        String minuteTextEnd = MINUTEEND < 10 ? "0" + String.valueOf(MINUTEEND) : String.valueOf(MINUTEEND);
        String hourTextEnd = HOUREND < 10 ? "0" + String.valueOf(HOUREND) + ":" + minuteTextEnd : String.valueOf(HOUREND) + ":" + minuteTextEnd + "   ";
        textViewDate.setText(hourTextStart + "-" + hourTextEnd);
        if (b) {
            textViewDay.setText(tt.getmTitle());
            if (tt.getmLocation() != null) {
            }
        } else {
            String s = "";
            for (ProviderServiceDetailsObj providerServiceDetailsObj : oo.getObjProviderServiceDetails())
                if (s.equals(""))
                    s += providerServiceDetailsObj.getNvServiceName();
                else
                    s += (", " + providerServiceDetailsObj.getNvServiceName());
            s = s + ", " + oo.getNvSupplierName();
            textViewDay.setText(s);
        }
        return convertView;
    }


    public Object[] getList(Object tasksCalanderList[], int location) {
        List<Object> objects = new ArrayList<>();
        TasksCalander task;
        OrderDetailsObj order;
        if (tasksCalanderList[location] instanceof TasksCalander) {
            TasksCalander t = (TasksCalander) tasksCalanderList[location];
            for (Object tasksCalander : tasksCalanderList) {
                if (tasksCalander instanceof TasksCalander) {
                    task = (TasksCalander) tasksCalander;
                    //to year
                    if (task.getmCalendarStart().get(Calendar.DAY_OF_MONTH) == t.getmCalendarStart().get(Calendar.DAY_OF_MONTH) &&
                            task.getmCalendarStart().get(Calendar.MONTH) == t.getmCalendarStart().get(Calendar.MONTH) &&
                            task.getmCalendarStart().get(Calendar.YEAR) == t.getmCalendarStart().get(Calendar.YEAR)) {
                        //to month
                        objects.add(new TasksCalander(task));
                    }
                } else {
                    order = (OrderDetailsObj) tasksCalander;
                    java.util.Date date_;
                    date_ = ConnectionUtils.convertJsonDate_(order.getDtDateOrder());
                    Calendar c = Calendar.getInstance();
                    c.setTime(date_);
                    if (c.get(Calendar.DAY_OF_MONTH) == t.getmCalendarStart().get(Calendar.DAY_OF_MONTH) &&
                            c.get(Calendar.MONTH) == t.getmCalendarStart().get(Calendar.MONTH) &&
                            c.get(Calendar.YEAR) == t.getmCalendarStart().get(Calendar.YEAR)) {
                        objects.add(new OrderDetailsObj(order));
                    }
                }
            }
        } else {
            OrderDetailsObj t = (OrderDetailsObj) tasksCalanderList[location];
            java.util.Date date;
            date = ConnectionUtils.convertJsonDate_(t.getDtDateOrder());
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            for (Object tasksCalander : tasksCalanderList) {
                if (tasksCalander instanceof TasksCalander) {
                    task = (TasksCalander) tasksCalander;
                    //to year
                    if (task.getmCalendarStart().get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH) &&
                            task.getmCalendarStart().get(Calendar.MONTH) == c.get(Calendar.MONTH) &&
                            task.getmCalendarStart().get(Calendar.YEAR) == c.get(Calendar.YEAR)) {

                        //to month
                        objects.add(new TasksCalander(task));
                    }
                } else {
                    order = (OrderDetailsObj) tasksCalander;
                    date = ConnectionUtils.convertJsonDate_(order.getDtDateOrder());
                    Calendar ca = Calendar.getInstance();
                    ca.setTime(date);
                    if (c.get(Calendar.DAY_OF_MONTH) == ca.get(Calendar.DAY_OF_MONTH) &&
                            c.get(Calendar.MONTH) == ca.get(Calendar.MONTH) &&
                            c.get(Calendar.YEAR) == ca.get(Calendar.YEAR)) {
                        objects.add(new OrderDetailsObj(order));
                    }
                }
            }
        }

        if (objects.size() == 0)
            objects.add(tasksCalanderList[location]);
        Object[] objects_list = new Object[objects.size()];
        int index = 0;
        for (Object value : objects) {
            if (value instanceof TasksCalander) {
                TasksCalander tt = new TasksCalander((TasksCalander) value);
                objects_list[index] = tt;
            } else {
                OrderDetailsObj oo = new OrderDetailsObj((OrderDetailsObj) value);
                objects_list[index] = oo;
            }
            index++;
        }

        return objects_list;
    }

    public int getcount() {
        return count;
    }

}
