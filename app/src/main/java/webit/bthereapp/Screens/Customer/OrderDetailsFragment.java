package webit.bthereapp.Screens.Customer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.Entities.GetFreeDaysForServiceProvider;
import webit.bthereapp.Entities.ProviderFreeDaysObj;
import webit.bthereapp.Entities.orderObj;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Calendar.ShowDayFragment;
import webit.bthereapp.Screens.Calendar.ShowWeekFragment;
import webit.bthereapp.Utils.CalendarUtil;
import webit.bthereapp.Utils.Utils;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OrderDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailsFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private int num_time = 0;
    private int b_id;
    private String b_name = "";
    private String adress = "";
    private int day;
    private Calendar calendar;
    private String date = "";
    private String hour = "", hourEnd = "";
    private String service_name = "";
    private CustomBoldTextView number_txt;
    private RelativeLayout finish;
    private RelativeLayout number;
    private CustomBoldTextView title;
    private boolean state = false;
    private CustomBoldTextView b_name_;
    private CustomLightTextView adress_;
    private CustomLightTextView day_;
    private CustomLightTextView date_;
    private CustomLightTextView hour_;
    private CustomLightTextView service_name_;
    private LinearLayout mBackL;
    private View view;
    private boolean b = false;
    private int service_id;
    private boolean ok_by_provider = false;
    private CustomLightButton ok, cancel, to_the_next;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OrderDetailsFragment newInstance(String param1, String param2) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static OrderDetailsFragment instance;

    public static OrderDetailsFragment getInstance() {
        if (instance == null)
            instance = new OrderDetailsFragment();
        return instance;
    }

    public static void setInstance(OrderDetailsFragment orderDetailsFragment) {
        instance = orderDetailsFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            String x = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order_details, container, false);


        num_time++;
        b_name_ = (CustomBoldTextView) view.findViewById(R.id.b_name);
        adress_ = (CustomLightTextView) view.findViewById(R.id.adress_txt);
        day_ = (CustomLightTextView) view.findViewById(R.id.day_txt);
        date_ = (CustomLightTextView) view.findViewById(R.id.date_txt);
        hour_ = (CustomLightTextView) view.findViewById(R.id.hour_txt);
        service_name_ = (CustomLightTextView) view.findViewById(R.id.service_name);
        title = (CustomBoldTextView) view.findViewById(R.id.title);
        b_name_.setText(b_name);
        adress_.setText(adress);
        mBackL = (LinearLayout) view.findViewById(R.id.back);
        mBackL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ok = (CustomLightButton) view.findViewById(R.id.ok);
//        ok.setEnabled(true);
        cancel = (CustomLightButton) view.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok.setEnabled(false);
                newOrder(1);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        setday();
        date_.setText(date);
        hour_.setText(hour);
        service_name_.setText(service_name);

        to_the_next = (CustomLightButton) view.findViewById(R.id.to_the_next);
        to_the_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newOrder(2);
            }
        });
        number_txt = (CustomBoldTextView) view.findViewById(R.id.number_txt);
        number_txt.setText(num_time + "");
        number = (RelativeLayout) view.findViewById(R.id.number);
        finish = (RelativeLayout) view.findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //if the user choosed a series of appointments
        if (CalendarUtil.type_of_service == 3)
            state = true;
        else
            state = false;
        if (state) {
            number.setVisibility(View.VISIBLE);
            finish.setVisibility(View.VISIBLE);
            to_the_next.setVisibility(View.VISIBLE);
            ok.setVisibility(View.GONE);
        }
        if (!(CalendarUtil.is_calendar_of_provider))
            from_customer();
        return view;
    }


    public void set_adress(String s) {
        adress = s;
    }

    public void set_day(int s) {
        day = s;
    }

    public void setday() {
        switch (day) {
            case 1:
                day_.setText(getResources().getString(R.string.day1));
                break;
            case 2:
                day_.setText(getResources().getString(R.string.day2));
                break;
            case 3:
                day_.setText(getResources().getString(R.string.day3));
                break;
            case 4:
                day_.setText(getResources().getString(R.string.day4));
                break;
            case 5:
                day_.setText(getResources().getString(R.string.day5));
                break;
            case 6:
                day_.setText(getResources().getString(R.string.day6));
                break;


        }

    }

    public void set_b_name(String s) {
        b_name = s;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public void set_b_true() {
        b = true;
    }

    public void set_b_false() {
        b = false;
    }

    public void set_date(String s) {
        date = s;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public void set_hour(String s) {
        hour = s;
    }

    public void set_hour_end(String s) {
        hourEnd = s;
    }

    public void setHourEnd(String hourEnd) {
        this.hourEnd = hourEnd;
    }

    public void set_service_name(String s) {
        service_name = s;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }


    //create order
    private void newOrder(final int a) {
        String the_name="";
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm user = realm.where(UserRealm.class).findFirst();
        if (user != null) {
            the_name = user.getNvFirstName();
        }
        ((NavigetionLayout) getActivity()).otherFrom = 10;
        if (user != null && user.getUserID() != 0) {
            Double userId = user.getUserID();
            orderObj.getInstance().setiUserId(userId);
            String jsonString = "{\"orderObj\":" + orderObj.getInstance().toString() + "}";

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String finalThe_name = the_name;
            MainBL.newOrder(getContext(), jsonObject, new IExecutable<Double>() {
                        @Override
                        public void onExecute(Double d) {
                            if (d != 0) {
                                if (mayRequestCalendar()) {
                                    setAppointmentInCalendar();
                                }
                                if (a == 2) {
                                    //if in a series of appointments- to the next order
                                    GetFreeDaysForServiceProvider();
                                }
                                if (a == 1) {
                                    //if in one order- open dialog

                                    final Dialog dialog = new Dialog(getContext());
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    if (ok_by_provider)
                                        dialog.setContentView(R.layout.fragment_ok_order_by_provider);
                                    else {
                                        dialog.setContentView(R.layout.fragment_ok_order);
                                        CustomRegularTextView sup_name = (CustomRegularTextView) dialog.findViewById(R.id.sup_name);
                                        sup_name.setText(getResources().getString(R.string.your_ask_1)+" "+b_name+" "+getResources().getString(R.string.your_ask_2));
                                    }
                                        CustomRegularTextView name = (CustomRegularTextView) dialog.findViewById(R.id.name);
                                        name.setText(finalThe_name);
                                    dialog.getWindow().setBackgroundDrawableResource(R.color.light_gray_9);
                                    dialog.show();
                                    SearchFragment.removeInstance();

                                    new CountDownTimer(2500, 1) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            dialog.dismiss();
                                            if (getActivity() != null) {
                                                ((NavigetionLayout) getActivity()).initFragmentMain(SearchFragment.getInstance(), true);
                                                ((NavigetionLayout) getActivity()).set_no_choose();
                                            }
                                        }
                                    }.start();
                                }
                                ((NavigetionLayout) getActivity()).otherFrom = 5;
                            }
                            ok.setEnabled(true);
                        }
                    }, new IExecutable<Integer>() {
                        @Override
                        public void onExecute(Integer integer) {
                            ok.setEnabled(true);
                            if (integer == 1) {
                                dismiss();
                            } else if (integer == -1) {
                                if (b) {
                                    //if yhe order not free now
                                    new AlertDialog.Builder(getContext())
                                            .setMessage(getResources().getString(R.string.order_not_free))
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dismiss();
//                                                    getActivity().onBackPressed();
                                                    GetFreeDaysForServiceProvider();
                                                }
                                            })
                                            .setCancelable(false)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();

                                } else {
                                    Toast.makeText(getContext(), getResources().getString(R.string.no_empty_qweue), Toast.LENGTH_LONG).show();
                                    ((NavigetionLayout) getContext()).initFragmentMain(OrderServiceFragment.getInstance()/*OrderServiceFragment.newInstance("", providerId_)*/, true);
                                }
                            } else if (integer == -2) {
                                Toast.makeText(getContext(), getResources().getString(R.string.no_enter_correct), Toast.LENGTH_LONG).show();
                            }

                        }
                    }
            );
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.cannot_in_this_state), Toast.LENGTH_LONG).show();
        }

    }

    public void from_customer() {
        ok.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
        title.setText(getResources().getString(R.string.meet_details));
        ((NavigetionLayout) getActivity()).otherFrom = 0;
    }

    private static final int REQUEST_WRITE_CALENDAR = 0;

    private boolean mayRequestCalendar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR)) {
            try {
                Snackbar.make(view.findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR}, REQUEST_WRITE_CALENDAR);
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR}, REQUEST_WRITE_CALENDAR);
        }
        return false;
    }

    private void GetFreeDaysForServiceProvider() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(GetFreeDaysForServiceProvider.getInstance().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetFreeDaysForServiceProvider(getContext(), jsonObject, new IExecutable<ArrayList<ProviderFreeDaysObj>>() {
            @Override
            public void onExecute(ArrayList<ProviderFreeDaysObj> providerFreeDaysObj_) {

                if (providerFreeDaysObj_ != null) {
                    CalendarUtil.taskCalanderListFree = providerFreeDaysObj_;
//                    OrderServiceFragment.removeInstance();
                    ((NavigetionLayout) getContext()).initFragmentMain(OrderServiceFragment.getInstance()/*OrderServiceFragment.newInstance("", providerId_)*/, true);
                } else {
                    CalendarUtil.taskCalanderListFree = new ArrayList<>();
                    Toast.makeText(getContext(), getResources().getString(R.string.no_found_free_days_to_this_service_provider), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_CALENDAR) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted , Access contacts here or do whatever you need.
                setAppointmentInCalendar();
            }
        }
    }


//create Appointment in phone's calendar
    private long setAppointmentInCalendar() {
        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put("calendar_id", 1); // id, We need to choose from
        eventValues.put("title", "BThere");
        eventValues.put("description", b_name + " - " + service_name);

        try {
            eventValues.put("dtstart", String.valueOf(parseDate(hour + " " + date)));
            eventValues.put("dtend", String.valueOf(parseDate(hourEnd + " " + date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TimeZone timeZone = TimeZone.getDefault();
        eventValues.put("eventTimezone","UTC/GMT +2:00");
        eventValues.put("hasAlarm", 1);
        Uri eventUri = getActivity().getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        long eventID = Long.parseLong(eventUri.getLastPathSegment());
        return eventID;
    }

    private static long parseDate(String text) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd.MM.yyyy", Locale.US);
        return dateFormat.parse(text + "+0200").getTime();
    }
}


