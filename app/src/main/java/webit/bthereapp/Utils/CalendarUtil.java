package webit.bthereapp.Utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.ProviderServiceDetailsObj;
import webit.bthereapp.Entities.TasksCalander;
import webit.bthereapp.Entities.orderObj;
import webit.bthereapp.Entities.ProviderFreeDaysObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.OrderDetailsFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;

/**
 * Created by User on 15/03/2016.
 */
public class CalendarUtil {
    public static int type_of_service = 0;
    public static Cursor cursor;
    public static boolean is_calendar_of_provider = false;
    public static List<ProviderFreeDaysObj> tasksCalandersfree_;
    public static List<ProviderFreeDaysObj> taskCalanderListFree = new ArrayList<>();
    public static List<OrderDetailsObj> OrderDetailsObj_ = new ArrayList<>();
    public static List<UserObj> serviceProviders = new ArrayList<>();
    public static boolean t = false;
    public static boolean mEyeShow = true;

    public CalendarUtil(Context context) {
    }


    public static List<TasksCalander> GetReadCalendarEvent(Context context) {
        cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{"calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation"}, null,
                        null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        List<TasksCalander> taskCalanderList = new ArrayList<TasksCalander>();
        List<String> longs = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {

            TasksCalander tasksCalanders = new TasksCalander();
            tasksCalanders.setId_calender(cursor.getInt(0));
            tasksCalanders.setmTitle(cursor.getString(1));
            tasksCalanders.setmDesription(cursor.getString(2));
            tasksCalanders.setmStartTime(cursor.getLong(3));
            tasksCalanders.setmEndTime(cursor.getLong(4));
            tasksCalanders.setmLocation(cursor.getString(5));
            taskCalanderList.add(tasksCalanders);
            cursor.moveToNext();
        }
        return taskCalanderList;
    }


    public static List<TasksCalander> getGoogleCalccndeer(Context context) {
        List<TasksCalander> taskCalanderList = new ArrayList<TasksCalander>();
        List<Integer> idCalanders = new ArrayList<Integer>();

        String[] EVENT_PROJECTION = new String[]{
                CalendarContract.Calendars._ID,                           // 0
                CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
                CalendarContract.Calendars.OWNER_ACCOUNT
        };
        // Run query
        Cursor cur = null;
        ContentResolver cr = context.getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;

        String selection = "((" + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?))";
        String[] selectionArgs = new String[]{"com.google"};
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return taskCalanderList;
        }
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
        // Use the cursor to step through the returned records
        while (cur.moveToNext()) {
            // Get the field values
            idCalanders.add(cur.getInt(0));
        }
        taskCalanderList = GetReadCalendarEvent(context);
        List<TasksCalander> tasksCalanderGoogle = new ArrayList<TasksCalander>();
        for (TasksCalander tasksCalander : taskCalanderList) {
            boolean contain = true;
            for (Integer id : idCalanders) {
                if (id == tasksCalander.getId_calender()) {
                    break;
                } else {
                    if (contain) {
                        tasksCalanderGoogle.add(tasksCalander);
                    }
                    contain = false;
                }
            }
        }
        return tasksCalanderGoogle;
    }

    public static List<TasksCalander> GetListTask(Context context, Calendar calanderDate, int numOfFif) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calanderDate.get(Calendar.YEAR), calanderDate.get(Calendar.MONTH), calanderDate.get(Calendar.DATE), 1, 0, 0);
        Calendar overcalander = Calendar.getInstance();
        overcalander.set(calanderDate.get(Calendar.YEAR), calanderDate.get(Calendar.MONTH), calanderDate.get(Calendar.DATE), 24, 59, 59);
        if (numOfFif == 1)
            overcalander.add(Calendar.MONTH, 6);
        else
            overcalander.add(Calendar.DATE, numOfFif);


        List<TasksCalander> taskCalanderList = GetReadCalendarEvent(context);
        List<TasksCalander> taskCalanderListFiltter = new ArrayList<>();
        for (TasksCalander tasksCalander : taskCalanderList) {
            if (calendar.before(tasksCalander.getmCalendarStart()) || (calendar.get(Calendar.DAY_OF_MONTH) == tasksCalander.getmCalendarStart().get(Calendar.DAY_OF_MONTH) &&
                    calendar.get(Calendar.MONTH) == tasksCalander.getmCalendarStart().get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == tasksCalander.getmCalendarStart().get(Calendar.YEAR)))
                if (overcalander.after(tasksCalander.getmCalendarStart()))
                    if (tasksCalander.getmTitle() == null || !tasksCalander.getmTitle().equals("BThere")) {
                        boolean r = false;
                        for (TasksCalander tasksCalander2 : taskCalanderListFiltter) {
                            if (tasksCalander.getmTitle() != null && tasksCalander2.getmTitle() != null)
                                if (tasksCalander.getmTitle().equals(tasksCalander2.getmTitle()) && tasksCalander.getmDesription().equals(tasksCalander2.getmDesription()) && tasksCalander.getmCalendarStart().equals(tasksCalander2.getmCalendarStart()) && tasksCalander.getmCalendarEnd().equals(tasksCalander2.getmCalendarEnd()))
                                    r = true;
                        }
                        if (!r) {
                            taskCalanderListFiltter.add(tasksCalander);
                        }
                    }

        }
        return taskCalanderListFiltter;
    }


    public static List<OrderDetailsObj> GetListOrders(Context context, Calendar calanderDate, int numOfFif) {
        Date date_;
        Calendar calendar_ = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calanderDate.get(Calendar.YEAR), calanderDate.get(Calendar.MONTH), calanderDate.get(Calendar.DATE), 1, 0, 0);
        Calendar overcalander = Calendar.getInstance();
        overcalander.set(calanderDate.get(Calendar.YEAR), calanderDate.get(Calendar.MONTH), calanderDate.get(Calendar.DATE), 24, 59, 59);
        if (numOfFif == 1)
            overcalander.add(Calendar.MONTH, 6);
        else
            overcalander.add(Calendar.DATE, numOfFif);
        List<OrderDetailsObj> taskCalanderList = OrderDetailsObj_;
        List<OrderDetailsObj> taskCalanderListFiltter = new ArrayList<>();

        for (OrderDetailsObj OrderDetailsObj_ : taskCalanderList) {

            date_ = ConnectionUtils.JsonDateToDate(OrderDetailsObj_.getDtDateOrder());
            calendar_.setTime(date_);
            if (calendar.before(calendar_) || (calendar.get(Calendar.YEAR) == calendar_.get(Calendar.YEAR) &&
                    calendar.get(Calendar.MONTH) == calendar_.get(Calendar.MONTH) &&
                    calendar.get(Calendar.DAY_OF_MONTH) == calendar_.get(Calendar.DAY_OF_MONTH)))
                if (overcalander.after(calendar_)) {
                    taskCalanderListFiltter.add(OrderDetailsObj_);
                }
        }
        return taskCalanderListFiltter;
    }

    public static List<ProviderFreeDaysObj> GetListTaskFreeDayFromWeek(Calendar calanderDate, List<ProviderFreeDaysObj> providerFreeDaysObjs) {
        Date date_;
        Calendar calendar_ = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calanderDate.get(Calendar.YEAR), calanderDate.get(Calendar.MONTH), calanderDate.get(Calendar.DATE), 1, 0, 0);
        Calendar overcalander = Calendar.getInstance();
        overcalander.set(calanderDate.get(Calendar.YEAR), calanderDate.get(Calendar.MONTH), calanderDate.get(Calendar.DATE), 24, 59, 59);
        overcalander.add(Calendar.DATE, 0);
        List<ProviderFreeDaysObj> taskCalanderListFiltter = new ArrayList<>();
        for (ProviderFreeDaysObj tasksCalander : providerFreeDaysObjs) {
            date_ = ConnectionUtils.convertJsonDate_(tasksCalander.getDtDate());
            calendar_.setTime(date_);
            if (calendar.before(calendar_) || (calendar.get(Calendar.YEAR) == calendar_.get(Calendar.YEAR) &&
                    calendar.get(Calendar.MONTH) == calendar_.get(Calendar.MONTH) &&
                    calendar.get(Calendar.DAY_OF_MONTH) == calendar_.get(Calendar.DAY_OF_MONTH)))
                if (overcalander.after(calendar_)) {
                    taskCalanderListFiltter.add(tasksCalander);
                }
        }
        return taskCalanderListFiltter;
    }

    public static List<ProviderFreeDaysObj> GetListTaskFree(Context context, Calendar calanderDate, int numOfFif) {
        Date date_;
        Calendar calendar_ = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calanderDate.get(Calendar.YEAR), calanderDate.get(Calendar.MONTH), calanderDate.get(Calendar.DATE), 1, 0, 0);
        Calendar overcalander = Calendar.getInstance();
        overcalander.set(calanderDate.get(Calendar.YEAR), calanderDate.get(Calendar.MONTH), calanderDate.get(Calendar.DATE), 24, 59, 59);
        overcalander.add(Calendar.DATE, numOfFif);
        List<ProviderFreeDaysObj> taskCalanderList = CalendarUtil.GetFreeDaysForServiceProvider();
        List<ProviderFreeDaysObj> taskCalanderListFiltter = new ArrayList<>();
        for (ProviderFreeDaysObj tasksCalander : taskCalanderList) {
            date_ = ConnectionUtils.convertJsonDate_(tasksCalander.getDtDate());
            calendar_.setTime(date_);

            if (calendar.before(calendar_) || (calendar.get(Calendar.YEAR) == calendar_.get(Calendar.YEAR) &&
                    calendar.get(Calendar.MONTH) == calendar_.get(Calendar.MONTH) &&
                    calendar.get(Calendar.DAY_OF_MONTH) == calendar_.get(Calendar.DAY_OF_MONTH)))
                if (overcalander.after(calendar_)) {
                    taskCalanderListFiltter.add(tasksCalander);

                }
        }
        return taskCalanderListFiltter;
    }

    public static List<TasksCalander> GetListTaskList(Context context, Calendar date) {
        return GetListTask(context, date, 1);
    }

    public static List<TasksCalander> GetListTaskMonth(Context context, Calendar date) {
        return GetListTask(context, date, date.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
    }

    public static List<ProviderFreeDaysObj> GetListTaskMonthFree(Context context, Calendar date) {
        return GetListTaskFree(context, date, date.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
    }

    public static List<TasksCalander> GetListTaskDay(Context context, Calendar date) {
        return GetListTask(context, date, 0);
    }

    public static List<OrderDetailsObj> GetOrdersMonth(Context context, Calendar date) {
        return GetListOrders(context, date, date.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
    }

    public static List<OrderDetailsObj> GetOrdersList(Context context, Calendar date) {
        List<OrderDetailsObj> orders = GetListOrders(context, date, 1);
        return orders;
    }

    public static List<OrderDetailsObj> GetOrdersDay(Context context, Calendar date) {
        return GetListOrders(context, date, 0);
    }


    public static List<OrderDetailsObj> GetOrdersWeek(Context context, Calendar date) {
        return GetListOrders(context, date, 6);
    }

    public static List<TasksCalander> GetListTaskWeek(Context context, Calendar date) {
        return GetListTask(context, date, 6);
    }

    public static List<ProviderFreeDaysObj> GetListTaskDayFree(Context context, Calendar date) {
        return GetListTaskFree(context, date, 0);
    }

    public static List<ProviderFreeDaysObj> GetListTaskWeekFree(Context context, Calendar date) {
        return GetListTaskFree(context, date, 6);
    }

    public static void setLIneTextView(final Context context, int index, RelativeLayout relativeLayout, List<TasksCalander> taskCalanderList, int half, boolean flag, View view) {
        List<TasksCalander> tasksCalanders = new ArrayList<>();
        String hourTextStart;
        String minuteTextStart;
        String minuteTextEnd;
        String hourTextEnd;
        tasksCalanders = taskCalanderList;
        float halff = (float) (half / 60.0);
        for (TasksCalander tasksCalander : tasksCalanders) {
            // if the view is of week
            if (((!flag && tasksCalander.getmCalendarStart().get(Calendar.DAY_OF_WEEK) == index) || flag) && !tasksCalander.getmTitle().equals("BThere")) {
                int HOUR = tasksCalander.getmCalendarStart().getTime().getHours();
                int MINUTE = tasksCalander.getmCalendarStart().getTime().getMinutes();
                HOUR = (HOUR * 60) + MINUTE;
                int startMargin = HOUR;
                int HOUREND = tasksCalander.getmCalendarEnd().getTime().getHours();
                int MINUTEEND = tasksCalander.getmCalendarEnd().getTime().getMinutes();
                HOUREND = (HOUREND * 60) + MINUTEEND;
                HOUREND = HOUR == HOUREND ? HOUREND + 1 : HOUREND;
                int endMargin = HOUREND;
                int start = (int) (startMargin * halff);
                int end = (int) (endMargin * halff);
                int height = (int) (halff * (endMargin - startMargin));

                RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                params2.setMargins(0, start, 0, end);
                if (tasksCalander.isAvailability()) {
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setId(index * 10);
                    linearLayout.setBackgroundColor(context.getResources().getColor(R.color.light_blue_transprent2));
                    relativeLayout.addView(linearLayout, params2);
                } else {
                    TextView textView = new TextView(context);
                    textView.setId(index * 10 + 2);

                    textView.setGravity(Gravity.CENTER);
                    String sourceString = tasksCalander.getmTitle();
                    textView.setText(Html.fromHtml(sourceString));
                    if (flag)
                        //in day view
                        textView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                    else
                        //in week view
                        textView.setBackground(context.getResources().getDrawable(R.drawable.rect_to_meet));
                    if (flag) {
                        //in day view
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, start, 0, 0);
                        HOUR = tasksCalander.getmCalendarStart().getTime().getHours();
                        MINUTE = tasksCalander.getmCalendarStart().getTime().getMinutes();
                        HOUREND = tasksCalander.getmCalendarEnd().getTime().getHours();
                        MINUTEEND = tasksCalander.getmCalendarEnd().getTime().getMinutes();
                        minuteTextStart = MINUTE < 10 ? "0" + MINUTE : MINUTE + "";
                        hourTextStart = HOUR < 10 ? "0" + HOUR + ":" + minuteTextStart : HOUR + ":" + minuteTextStart;
                        minuteTextEnd = MINUTEEND < 10 ? "0" + MINUTEEND : MINUTEEND + "";
                        hourTextEnd = HOUREND < 10 ? "0" + HOUREND + ":" + minuteTextEnd : HOUREND + ":" + minuteTextEnd + "   ";
                        textView.setGravity(Gravity.CENTER_VERTICAL | View.FOCUS_RIGHT);
                        textView.setTextSize(15);

                        sourceString = hourTextStart + "-" + hourTextEnd + " " + textView.getText();

                        if (tasksCalander.getmDesription() != null && !tasksCalander.getmDesription().equals(""))
                            sourceString = sourceString + ", " + tasksCalander.getmDesription();
                        //set the text of the hours
                        textView.setText(Html.fromHtml(sourceString));

                        textView.setPadding(0, 0, 40, 0);

                        RelativeLayout.LayoutParams paramsVew = new RelativeLayout.LayoutParams(view.getLayoutParams());
                        paramsVew.addRule(RelativeLayout.ALIGN_PARENT_START);
                        relativeLayout.addView(textView, params);
                        paramsVew.addRule(RelativeLayout.LEFT_OF, textView.getId());
                        View view2 = new View(context);

                        relativeLayout.setGravity(RelativeLayout.RIGHT_OF);
                        relativeLayout.addView(view2, paramsVew);
                    } else {
                        //in week view
                        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                        params1.setMargins(0, start, 0, end);
                        textView.setTextSize(10);
                        relativeLayout.addView(textView, params1);
                    }
                }
            }
        }
    }

    //put the order in the calendar
    public static void setLIneTextViewOrder(final Context context, int index, RelativeLayout relativeLayout, final List<OrderDetailsObj> taskCalanderList, int half, boolean flag, View view) {
        List<OrderDetailsObj> tasksCalanders = new ArrayList<>();
        String hourTextStart;
        String minuteTextStart;
        String minuteTextEnd;
        String hourTextEnd;
        float halff = (float) (half / 60.0);
        final Calendar calendar = Calendar.getInstance();
        final Calendar calendar1 = Calendar.getInstance();
        tasksCalanders = taskCalanderList;
        if (tasksCalanders == null) {
            tasksCalanders = new ArrayList<>();
        }
        for (final OrderDetailsObj tasksCalander : tasksCalanders) {
            calendar.setTime(ConnectionUtils.JsonDateToDate(tasksCalander.getDtDateOrder()));

            if ((calendar.get(Calendar.DAY_OF_WEEK) == index && !flag) || flag) {
                int HOUR = calendar.getTime().getHours();
                int MINUTE = calendar.getTime().getMinutes();
                HOUR = (HOUR * 60) + MINUTE;
                int startMargin = HOUR;
                calendar1.setTime(ConnectionUtils.JsonDateToDate(tasksCalander.getNvToHour()));

                int HOUREND = calendar1.getTime().getHours();
                int MINUTEEND = calendar1.getTime().getMinutes();
                HOUREND = (HOUREND * 60) + MINUTEEND;
                HOUREND = HOUR == HOUREND ? HOUREND + 1 : HOUREND;
                int endMargin = HOUREND;
                int start = (int) (startMargin * halff);
                int height = (int) (halff * (endMargin - startMargin));

                TextView textView = new TextView(context);
                textView.setId(index * 10 + 2);

                if (flag) {
                    textView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                    textView.setTextSize(15);
                    textView.setGravity(Gravity.CENTER_VERTICAL | View.FOCUS_RIGHT);
                } else {
                    textView.setBackground(context.getResources().getDrawable(R.drawable.rect_to_meet));
                    textView.setTextSize(10);
                    textView.setGravity(Gravity.CENTER);
                }

                HOUR = calendar.getTime().getHours();
                MINUTE = calendar.getTime().getMinutes();
                HOUREND = calendar1.getTime().getHours();
                MINUTEEND = calendar1.getTime().getMinutes();
                minuteTextStart = MINUTE < 10 ? "0" + MINUTE : MINUTE + "";
                hourTextStart = HOUR < 10 ? "0" + HOUR + ":" + minuteTextStart : HOUR + ":" + minuteTextStart;
                minuteTextEnd = MINUTEEND < 10 ? "0" + MINUTEEND : MINUTEEND + "";
                hourTextEnd = HOUREND < 10 ? "0" + HOUREND + ":" + minuteTextEnd : HOUREND + ":" + minuteTextEnd + "   ";
                String sourceString = "";
                for (int i = 0; i < tasksCalander.getObjProviderServiceDetails().length; i++)
                    if (i == 0)
                        sourceString = tasksCalander.getObjProviderServiceDetails()[i].getNvServiceName();
                    else
                        sourceString = sourceString + ", " + tasksCalander.getObjProviderServiceDetails()[i].getNvServiceName();
                //set the text hours of the qweue
                textView.setText(Html.fromHtml(sourceString));
                textView.setTextColor(Color.BLACK);
                if (!is_calendar_of_provider)
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OrderDetailsFragment.setInstance(null);
                            calendar.setTime(ConnectionUtils.JsonDateToDate(tasksCalander.getDtDateOrder()));
                            SimpleDateFormat mFormatter_2 = new SimpleDateFormat("HH:mm");
                            OrderDetailsFragment.getInstance().set_hour(mFormatter_2.format(calendar.getTime()));

                            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
                            String formatted = format1.format(calendar.getTime());
                            OrderDetailsFragment.getInstance().set_date(formatted);
                            String s = "";
                            for (ProviderServiceDetailsObj providerServiceDetailsObj : tasksCalander.getObjProviderServiceDetails())
                                if (s.equals(""))
                                    s += providerServiceDetailsObj.getNvServiceName();
                                else
                                    s += (", " + providerServiceDetailsObj.getNvServiceName());
                            OrderDetailsFragment.getInstance().set_service_name(s);
                            OrderDetailsFragment.getInstance().set_b_name(tasksCalander.getNvSupplierName());
                            OrderDetailsFragment.getInstance().set_adress(tasksCalander.getNvAddress());
                            OrderDetailsFragment.getInstance().set_day(calendar.get(Calendar.DAY_OF_WEEK));
                            calendar.setTime(ConnectionUtils.JsonDateToDate(tasksCalander.getNvToHour()));
                            OrderDetailsFragment.getInstance().setHourEnd(mFormatter_2.format(calendar.getTime()));
                            try {
                                ((NavigetionLayout) context).initFragmentMain(OrderDetailsFragment.getInstance(), true);
                            } catch (ClassCastException e) {

                            }
                        }
                    });
                if (flag) {
                    //in day view
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, start/*Margin * half*/, 0, 0/*Margin * half*/);
                    sourceString = hourTextStart + "-" + hourTextEnd + " " + textView.getText() + ", " + tasksCalander.getNvSupplierName();
                    textView.setText(Html.fromHtml(sourceString));
                    textView.setPadding(0, 0, 40, 0);
                    RelativeLayout.LayoutParams paramsVew = new RelativeLayout.LayoutParams(view.getLayoutParams());

                    paramsVew.addRule(RelativeLayout.ALIGN_PARENT_START);
                    relativeLayout.addView(textView, params);
                    View view2 = new View(context);
                    relativeLayout.setGravity(RelativeLayout.ALIGN_PARENT_TOP);
                    relativeLayout.addView(view2, paramsVew);
                } else {
                    //in week view
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height /*half * (endMargin - startMargin)*/);
                    params.setMargins(0, start/*Margin * half*/, 0, 0/*Margin * half*/);
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    relativeLayout.addView(textView, params);

                }
            }
        }

    }

    //put the order free in the calendar
    public static void setLIneTextViewFree(final Context context, int index, RelativeLayout relativeLayout, List<ProviderFreeDaysObj> taskCalanderListFree, int half, boolean flag, final View view) throws ParseException {
        final Calendar calendar = Calendar.getInstance();
//        =============================
        float halff = (float) (half / 60.0);
//        =============================

        tasksCalandersfree_ = new ArrayList<>();
        tasksCalandersfree_ = taskCalanderListFree;
        int i = 0;

        for (final ProviderFreeDaysObj tasksCalander : tasksCalandersfree_) {

            final ProviderFreeDaysObj mytasksCalander = tasksCalander;
            calendar.setTime(ConnectionUtils.JsonDateToDate(tasksCalander.getObjProviderHour().getNvFromHour()));
            int HOUR = calendar.getTime().getHours();
            int MINUTE = calendar.getTime().getMinutes();
            String s = HOUR + ":" + (MINUTE > 9 ? MINUTE : "0" + MINUTE);
//        =============================
            HOUR = (HOUR * 60) + MINUTE;
//        =============================
            int startMargin = HOUR;
            calendar.setTime(ConnectionUtils.JsonDateToDate(tasksCalander.getObjProviderHour().getNvToHour()));
            int HOUREND = calendar.getTime().getHours();
            int MINUTEEND = calendar.getTime().getMinutes();
            s += " - " + HOUREND + ":" + (MINUTEEND > 9 ? MINUTEEND : "0" + MINUTEEND);
//        =============================
            HOUREND = (HOUREND * 60) + MINUTEEND;
//        =============================
            HOUREND = HOUR == HOUREND ? HOUREND + 1 : HOUREND;
            int endMargin = HOUREND;

            int start = (int) (startMargin * halff);
            int end = (int) (endMargin * halff);
            int height = (int) (halff * (endMargin - startMargin));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            params.setMargins(0, start, 0, 0);
//            params.setMargins(0, start, 0, end);
            final TextView textView = new TextView(context);
            textView.setId(index * 10 + 2);
            textView.setTextSize(10);
            textView.setText(s);
            textView.setGravity(Gravity.CENTER);
            textView.setBackground(context.getResources().getDrawable(R.drawable.rect_hour_free));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when the user click on free order, we show him the order detail, and he will choose if to order qweue
                    Date date;
                    calendar.setTime(ConnectionUtils.JsonDateToDate(mytasksCalander.getObjProviderHour().getNvFromHour()));
                    SimpleDateFormat mFormatter_2 = new SimpleDateFormat("HH:mm");
                    orderObj.getInstance().setNvFromHour(mFormatter_2.format(calendar.getTime()));
                    OrderDetailsFragment.getInstance().set_hour(mFormatter_2.format(calendar.getTime()));
                    calendar.setTime(ConnectionUtils.JsonDateToDate(mytasksCalander.getObjProviderHour().getNvToHour()));
                    OrderDetailsFragment.getInstance().setHourEnd(mFormatter_2.format(calendar.getTime()));
                    date = ConnectionUtils.JsonDateToDate(tasksCalander.getDtDate());
                    calendar.setTime(date);
                    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
                    String formatted = format1.format(calendar.getTime());
                    OrderDetailsFragment.getInstance().set_date(formatted);
                    OrderDetailsFragment.getInstance().setCalendar(calendar);
                    orderObj.getInstance().setiSupplierUserId(mytasksCalander.getiProviderUserId());
                    orderObj.getInstance().setDtDateOrder(tasksCalander.getDtDate());
                    OrderDetailsFragment.getInstance().set_day(calendar.get(Calendar.DAY_OF_WEEK));
                    Log.d("new_order_free", mytasksCalander.toString());

//                    String s = "";
//                    for (ProviderServiceDetailsObj providerServiceDetailsObj : tasksCalander.getObjProviderServiceDetails())
//                        if (s.equals(""))
//                            s += providerServiceDetailsObj.getNvServiceName();
//                        else
//                            s += (", " + providerServiceDetailsObj.getNvServiceName());
//                    OrderDetailsFragment.getInstance().set_service_name(s);
//                    OrderDetailsFragment.getInstance().set_b_name(tasksCalander.getNvSupplierName());
//                    OrderDetailsFragment.getInstance().set_adress(tasksCalander.getNvAddress());
//                    OrderDetailsFragment.getInstance().set_day(calendar.get(Calendar.DAY_OF_WEEK));


                    Fragment fragment = null;
                    Realm realm = Utils.getRealmInstance(context);
                    UserRealm userRealm = realm.where(UserRealm.class).findFirst();
                    if (userRealm == null || (userRealm.getNvFirstName().equals(""))) {
                        exitToRegisterAlert(context);
                    } else {
                        OrderDetailsFragment.getInstance().set_b_false();

                        ((NavigetionLayout) context).otherFrom = 5;

                        fragment = OrderDetailsFragment.getInstance();
                        ((NavigetionLayout) context).initFragmentMain(fragment, true);
                    }

                }


            });

            if (flag) {
                //in day view
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setTextSize(15);
                RelativeLayout.LayoutParams paramsVew = new RelativeLayout.LayoutParams(view.getLayoutParams());
                paramsVew.addRule(RelativeLayout.ALIGN_PARENT_START);
                relativeLayout.addView(textView, params);
                paramsVew.addRule(RelativeLayout.LEFT_OF, textView.getId());
                View view2 = new View(context);
                relativeLayout.addView(view2, paramsVew);
            } else {
                //in week view
                relativeLayout.addView(textView, params);
            }
        }
        i++;
    }

    //when user that didnt register to the bthere, want to order qweue,
    //we open him dialog that asks him if he want to enter to the registion
    public static void exitToRegisterAlert(final Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(R.string.exit_r);
        alertDialogBuilder
                .setMessage(R.string.exit_question_r)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        OrderDetailsFragment.getInstance().set_b_true();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("fragmentNum", 7);
                        intent.putExtra("sentFrom", 1);
                        context.startActivity(intent);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void setMlinearLayoutDays(final Context context, float dpwidth, final float heightBottom, final View mView, int half, LinearLayout mlinearLayoutTime, final List<TasksCalander> taskCalanderList, List<ProviderFreeDaysObj> taskCalanderListFree, List<OrderDetailsObj> orderList, final int index, final View view) {
        final LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setId(Integer.valueOf(222));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) dpwidth / 9 * 7, (int) heightBottom * 3);
        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mlinearLayoutTime.addView(linearLayout, params);
        half = ((int) ((heightBottom * 3) / 24) * half);
        final int widthLIne = (int) ((dpwidth / 9 * 7) / index);
        for (int i = 1; i < index + 1; i++) {
            RelativeLayout relativeLayout = new RelativeLayout(context);
            relativeLayout.setId(Integer.valueOf(i));
            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(widthLIne, (int) heightBottom * 3);
            relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            linearLayout.addView(relativeLayout, params1);
            setLineView(context, relativeLayout, mView, half);
            boolean flag;
            flag = (index == 1);
            //if day flag=true ; if week flag= false;
            setLIneTextView(context, i, relativeLayout, taskCalanderList, half, flag, view);

            List<ProviderFreeDaysObj> pfd = new ArrayList<>();
            if (!flag && taskCalanderListFree != null && taskCalanderListFree.size() > 0) {
                Calendar calander = Calendar.getInstance(), calendar1 = Calendar.getInstance();
                calander.setTime(ConnectionUtils.JsonDateToDate((taskCalanderListFree.get(0).getDtDate())));
                calendar1.clear();
                calendar1.set(Calendar.YEAR, calander.get(Calendar.YEAR));
                calendar1.set(Calendar.WEEK_OF_YEAR, calander.get(Calendar.WEEK_OF_YEAR));
                calendar1.add(Calendar.DATE, i - 1);
                pfd = GetListTaskFreeDayFromWeek(calendar1, taskCalanderListFree);
            } else {
                pfd = taskCalanderListFree;
            }
            try {
                //view the free orders
                setLIneTextViewFree(context, i, relativeLayout, pfd, half, flag, view);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //view the orders
            setLIneTextViewOrder(context, i, relativeLayout, orderList, half, flag, view);
        }
    }

    public static List<ProviderFreeDaysObj> GetFreeDaysForServiceProvider() {
        List<ProviderFreeDaysObj> ListFree = new ArrayList<>();
        if (CalendarUtil.taskCalanderListFree != null)
            ListFree = CalendarUtil.taskCalanderListFree;
        return ListFree;
    }

    public static List<UserObj> GetServiceProviders() {
        return CalendarUtil.serviceProviders;
    }

    public static void setLineView(Context context, RelativeLayout relativeLayout, View mView, int half) {
        int start = 1;
        for (int i = 0; i < 24; i++) {
            View view = new View(context);
            view.setId(i * 100);
            RelativeLayout.LayoutParams paramsView = new RelativeLayout.LayoutParams(mView.getLayoutParams());
            paramsView.setMargins(1, start * half, 1, 0);
            start++;
            view.setBackgroundColor(context.getResources().getColor(R.color.color6));
            relativeLayout.addView(view, paramsView);
        }
    }


    public static void addNameHours(Context context, float heightBottom, float dpwidth, LinearLayout mlinearLayoutTime) {
        int deviceLInear = (int) (heightBottom / 16);
        float width = dpwidth / 9 * 2;
        LinearLayout LinearLayoutView = new LinearLayout(context);
        LinearLayoutView.setId(Integer.valueOf(111));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) width, (int) (heightBottom * 3));
        LinearLayoutView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        LinearLayoutView.setOrientation(LinearLayout.VERTICAL);
        mlinearLayoutTime.addView(LinearLayoutView, params);
        LinearLayout linearLayout1 = new LinearLayout(context);
        linearLayout1.setId(Integer.valueOf(1111));
        params = new LinearLayout.LayoutParams((int) width, deviceLInear / 4);
        linearLayout1.setLayoutParams(params);
        LinearLayoutView.addView(linearLayout1);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) deviceLInear / 4, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        for (int i = 1; i < 25; i++) {
            CustomBoldTextView mTextView = new CustomBoldTextView(context, null);
            mTextView.setId(Integer.valueOf(i));
            if (i < 10)
                mTextView.setText("0" + i + ":00");
            else mTextView.setText(i + ":00");
            mTextView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            mTextView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            mTextView.setLayoutParams(params);
            LinearLayoutView.addView(mTextView);
        }
    }
}