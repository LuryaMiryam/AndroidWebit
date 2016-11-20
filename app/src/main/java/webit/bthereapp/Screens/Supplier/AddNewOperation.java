package  webit.bthereapp.Screens.Supplier;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.BL.SplashBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.Fonts.CustomLightEditText;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.SysAlertsListDM;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.AddProviderDetails;
import webit.bthereapp.Entities.CalendarProperties;
import webit.bthereapp.Entities.CouponObj;
import webit.bthereapp.Entities.CouponTypeObj;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.SysAlertsList;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.SplashActivity;
import webit.bthereapp.Utils.Utils;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AddNewOperation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewOperation extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static CustomLightTextView date_txt, from_hour_txt, to_hour_txt;
    private RelativeLayout date, from_hour, to_hour;
    private ImageButton yn, close;
    private CouponObj objCoupon = new CouponObj();
    private CustomLightButton ok;
    private CustomLightEditText txt;
    private static String date_str;
    private CustomSpinnerLarge type;
    private ArrayList<CouponTypeObj> couponTypeObjs=new ArrayList<>();

    public AddNewOperation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewOperation.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewOperation newInstance(String param1, String param2) {
        AddNewOperation fragment = new AddNewOperation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_operation, container, false);
        date_txt = (CustomLightTextView) view.findViewById(R.id.date_tv);
        date = (RelativeLayout) view.findViewById(R.id.date);
        date.setOnClickListener(this);
        close = (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        ok = (CustomLightButton) view.findViewById(R.id.ok);
        ok.setOnClickListener(this);
        from_hour_txt = (CustomLightTextView) view.findViewById(R.id.from_hour_id_txt);
        txt = (CustomLightEditText) view.findViewById(R.id.txt_);
        from_hour_txt.setText("00:00");
        type = (CustomSpinnerLarge) view.findViewById(R.id.type_);
        from_hour = (RelativeLayout) view.findViewById(R.id.from_hour_id);
        from_hour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        from_hour_txt.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        to_hour_txt = (CustomLightTextView) view.findViewById(R.id.to_hour_id_txt);

        to_hour_txt.setText("00:00");
        to_hour = (RelativeLayout) view.findViewById(R.id.to_hour_id);
        to_hour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        to_hour_txt.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        getCouponTypeOptions();
        return view;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            date_txt.setText(day1 + "/" + month1 + "/" + year1);

            Calendar calendar = Calendar.getInstance();
            calendar.set(selectedYear, selectedMonth, selectedDay, 0, 0);

            SimpleDateFormat mFormatter_2 = new SimpleDateFormat("yyyyMMdd");
            objCoupon.setdDate(mFormatter_2.format(calendar));
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Open dialog to select date
            case R.id.date: {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show((getActivity().getSupportFragmentManager()), "DatePicker");
                break;
            }
            case R.id.ok: {
                objCoupon.setiSupplierServiceId(type.get_choose());
                objCoupon.setNvCouponName(txt.getText().toString());
                objCoupon.settFromHour(from_hour_txt.getText().toString());
                objCoupon.settToHour(to_hour_txt.getText().toString());
                objCoupon.setiCouponType(couponTypeObjs.get(0).getiCouponTypeRowId());
                objCoupon.setdDate(date_str);
                addNewCoupon();
                break;
            }
            case R.id.close: {
                dismiss();
                break;
            }

        }

    }

    private void getCouponTypeOptions() {
        CouponObj objCoupon = new CouponObj();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(objCoupon.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getCouponTypeOptions(getContext(), jsonObject, new IExecutable<ArrayList<CouponTypeObj>>() {
                    @Override
                    public void onExecute(ArrayList<CouponTypeObj> couponTypeObj) {

                        if (couponTypeObj != null) {
                            couponTypeObjs=new ArrayList<CouponTypeObj>(couponTypeObj);
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.no_get_Coupon_Type_Options), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {
                    }
                }
        );
    }

    private void addNewCoupon() {

        String jsonString = "{\"objCoupon\":" + objCoupon.toString() + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.addNewCoupon(getContext(), jsonObject, new IExecutable<Double>() {
                    @Override
                    public void onExecute(Double d) {
                        if (d > 0) {
                            dismiss();
                        }
                        else
                            Toast.makeText(getContext(),getResources().getString(R.string.no_enter_correct), Toast.LENGTH_LONG).show();
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {
                        if (integer == -1 || integer == -2)  //didnt finish registion
                            Toast.makeText(getContext(),getResources().getString(R.string.there_is_error), Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, yy, mm, dd);
            datePicker.getDatePicker().setMaxDate(new Date().getTime());
            return datePicker;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            String mm = String.valueOf(month);
            String dd = String.valueOf(day);
            date_str = String.valueOf(year) + (month < 10 ? "0" + mm : mm) + (day < 10 ? "0" + dd : dd);
            date_txt.setText(month + "/" + day + "/" + year);

        }
    }

}
