package webit.bthereapp.Screens.Customer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Utils.Utils;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CalendarPrintDialogClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarPrintDialogClientFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static int flag = 0;
    private RelativeLayout choose_from, choose_to;
    private static CustomLightTextView choose_from_txt, choose_to_txt;

    private ImageView close;
    Dialog vDialod = null;
    private webit.bthereapp.General.DateTimePicker.DatePicker datePicker;
    private CustomLightButton ok;
    static Date dtStartDate;
    static Date dtEndDate;
    private static ImageView mDayIv, mWeekIv, mListIv;
    private int choose = -1;

    //    x black
    public CalendarPrintDialogClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalendarPrintDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarPrintDialogClientFragment newInstance() {
        CalendarPrintDialogClientFragment fragment = new CalendarPrintDialogClientFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_calendar_print_dialog_client, container, false);
        ok = (CustomLightButton) view.findViewById(R.id.ok);
        ok.setOnClickListener(this);
        dtStartDate = new Date();
        dtEndDate = new Date();
        choose_from_txt = (CustomLightTextView) view.findViewById(R.id.choose_from_txt);
        choose_to_txt = (CustomLightTextView) view.findViewById(R.id.choose_to_txt);
        close = (ImageView) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        choose_from = (RelativeLayout) view.findViewById(R.id.choose_from);
        choose_from.setOnClickListener(this);
        choose_to = (RelativeLayout) view.findViewById(R.id.choose_to);
        choose_to.setOnClickListener(this);
        mDayIv = (ImageView) view.findViewById(R.id.day);
        mDayIv.setOnClickListener(this);
        mWeekIv = (ImageView) view.findViewById(R.id.week);
        mWeekIv.setOnClickListener(this);
        mListIv = (ImageView) view.findViewById(R.id.list);
        mListIv.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_from:
                flag = 1;
                openDialogDate();
                break;
            case R.id.choose_to:
                flag = 2;
                openDialogDate();
                break;
            //day view
            case R.id.day: {
                if (choose == 1) {
                    mDayIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_29);
                    choose = -1;
                } else {
                    if (choose == 2)
                        mWeekIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_29);

                    if (choose == 3)
                        mListIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_29);
                    choose = 1;
                    mDayIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                }
                break;
            }
            //week view
            case R.id.week: {
                if (choose == 2) {
                    mWeekIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_29);
                    choose = -1;
                } else {
                    if (choose == 1)
                        mDayIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_29);
                    if (choose == 3)
                        mListIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_29);
                    choose = 2;
                    mWeekIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                }
                break;

            }
            //list view
            case R.id.list: {
                if (choose == 3) {
                    mListIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_29);
                    choose = -1;
                } else {
                    if (choose == 1)
                        mDayIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_29);
                    if (choose == 2)
                        mWeekIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_29);
                    choose = 3;
                    mListIv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                }
                break;
            }
            case R.id.close: {
                dismiss();
                break;
            }
            case R.id.ok: {
                if (valid()) {
                    print();
//                    dismiss();
                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), getString(R.string.not_fill_all), Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
        }
    }

    private void openDialogDate() {
        if (getActivity() != null) {
            vDialod = new Dialog(getActivity());
            vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
            vDialod.setCanceledOnTouchOutside(false);
            vDialod.setContentView(R.layout.date_picker_dialog);
            datePicker = (webit.bthereapp.General.DateTimePicker.DatePicker) vDialod.findViewById(R.id.datePicker);
            final Calendar calendar = Calendar.getInstance();
            if (flag == 1)
                calendar.setTime(dtStartDate);
            else
                calendar.setTime(dtEndDate);
            datePicker.setCalendar(calendar);
            Button btnOk = (Button) vDialod.findViewById(R.id.ok);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (flag == 1) {
                        choose_from_txt.setText(datePicker.getDay() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                        mDayIv.requestFocus();
                        dtStartDate.setYear(datePicker.getYear());
                        dtStartDate.setMonth(datePicker.getMonth());
                        dtStartDate.setDate(datePicker.getDay());
                    } else {
                        choose_to_txt.setText(datePicker.getDay() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                        mDayIv.requestFocus();
                        dtEndDate.setYear(datePicker.getYear());
                        dtEndDate.setMonth(datePicker.getMonth());
                        dtEndDate.setDate(datePicker.getDay());
                    }
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

    private boolean valid() {
        if (choose_from_txt.getText() == null || choose_from_txt.getText().length() == 0) {
            return false;
        }
        if (choose_to_txt.getText() == null || choose_from_txt.getText().length() == 0) {
            return false;
        }
        if (choose == -1) {
            return false;
        }
        return true;
    }

    private void print() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("iUserId", userRealm.getUserID());
            jsonObject.put("dtStartDate", ConnectionUtils.convertStringToDate(choose_from_txt.getText().toString()));
            jsonObject.put("dtEndDate", ConnectionUtils.convertStringToDate(choose_to_txt.getText().toString()));
            choose = 0;
            jsonObject.put("iDisplayType ", choose);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getError(getActivity(), ConnectionUtils.PrintCalendar, jsonObject, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer isValid) {
//                if (isValid==1)
//                Toast.makeText(getActivity(), getString(R.string.successEmail), Toast.LENGTH_LONG).show();
                dismiss();
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer error) {
//                if (error == 1) {
//                        Toast.makeText(getActivity(), getString(R.string.successEmail), Toast.LENGTH_LONG).show();
//                } else
//                        Toast.makeText(getActivity(), getString(R.string.there_are_problem), Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, yy, mm, dd);
            return datePicker;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {

            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {


            if (flag == 1) {
                choose_from_txt.setText(day + "/" + month + "/" + year);
                mDayIv.requestFocus();
                dtStartDate.setYear(year);
                dtStartDate.setMonth(month);
                dtStartDate.setDate(day);
            } else {
                choose_to_txt.setText(day + "/" + month + "/" + year);
                mDayIv.requestFocus();
                dtEndDate.setYear(year);
                dtEndDate.setMonth(month);
                dtEndDate.setDate(day);
            }
        }
    }


}
