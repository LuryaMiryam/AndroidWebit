package webit.bthereapp.Screens.Customer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.CustomSpinnerLargeBlack;
import webit.bthereapp.CustemViews.CustomViewTimePicker;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.Fonts.CustomLightEditText;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.orderObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Utils.Utils;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NewAppointmentFromCustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewAppointmentFromCustomer extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CustomLightEditText comments;
    private CustomLightButton ok;
    private static CustomLightTextView date_txt, hour_txt;
    private RelativeLayout date, hour;
    private ImageButton close;
    private Calendar mcurrentTime = Calendar.getInstance();
    private orderObj myorderObj = new orderObj();
    private CustomSpinnerLargeBlack provider, give_service, type_service;
    private Dialog vDialod = null;
    private CustomViewTimePicker timePicker;
    private webit.bthereapp.General.DateTimePicker.DatePicker datePicker;

    public NewAppointmentFromCustomer() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NewAppointmentFromCustomer newInstance(String param1, String param2) {
        NewAppointmentFromCustomer fragment = new NewAppointmentFromCustomer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static NewAppointmentFromCustomer instance;

    public static NewAppointmentFromCustomer getInstance() {
        if (instance == null)
            instance = new NewAppointmentFromCustomer();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
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
        View view = inflater.inflate(R.layout.fragment_new_appointment_from_customer, container, false);


        CustomSpinnerLargeBlack.provider_id = 0;
        CustomSpinnerLargeBlack.give_service_id = 0;
        CustomSpinnerLargeBlack.userObjs = new ArrayList<>();
        CustomSpinnerLargeBlack.providerServices = new ArrayList<>();


        ok = (CustomLightButton) view.findViewById(R.id.ok);
        close = (ImageButton) view.findViewById(R.id.close);

        date_txt = (CustomLightTextView) view.findViewById(R.id.date_tv);
        date = (RelativeLayout) view.findViewById(R.id.date);
        date.setOnClickListener(this);
        timePicker = (CustomViewTimePicker) view.findViewById(R.id.cvTimePicker);

        ok.setOnClickListener(this);
        close.setOnClickListener(this);

        provider = (CustomSpinnerLargeBlack) view.findViewById(R.id.black_provider);
        give_service = (CustomSpinnerLargeBlack) view.findViewById(R.id.new_app_give_service);
        type_service = (CustomSpinnerLargeBlack) view.findViewById(R.id.black_type_service);
        hour = (RelativeLayout) view.findViewById(R.id.hour);
        hour_txt = (CustomLightTextView) view.findViewById(R.id.hour_tv);
        comments = (CustomLightEditText) view.findViewById(R.id.comments);
        //open dialog to select hour
        hour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour_txt.setText(selectedHour + ":" + selectedMinute);
                        mcurrentTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        mcurrentTime.set(Calendar.MINUTE, selectedMinute);
                        mcurrentTime.set(Calendar.SECOND, 0);


                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.date: {
                openDialogDate();
                break;
            }
            case R.id.close: {
                dismiss();
                break;
            }
            case R.id.ok: {
                //if no field empty, send to check if the order available
                if (check_if_no_empty())
                    CheckIfOrderIsAvailable();
                else
                    Toast.makeText(getContext(), getResources().getString(R.string.no_all), Toast.LENGTH_LONG).show();
                break;
            }

        }
    }
//we check if all the fields are full
    private boolean check_if_no_empty() {
        if (date_txt.getText().length() == 0) {
//            Toast.makeText(getContext(), getResources().getString(R.string.about_us), Toast.LENGTH_LONG).show();
            return false;
        }
        if (hour_txt.getText().length() == 0) {
//            Toast.makeText(getContext(), getResources().getString(R.string.about_us), Toast.LENGTH_LONG).show();
            return false;
        }
        if (provider.get_choose_txt().length() == 0) {
//            Toast.makeText(getContext(), getResources().getString(R.string.about_us), Toast.LENGTH_LONG).show();
            return false;
        }
        if (give_service.get_choose_txt().length() == 0) {
//            Toast.makeText(getContext(), getResources().getString(R.string.about_us), Toast.LENGTH_LONG).show();
            return false;
        }
        if (type_service.get_choose_txt().length() == 0) {
//            Toast.makeText(getContext(), getResources().getString(R.string.about_us), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    //open dialog to select date
    private void openDialogDate() {
        if (getActivity() != null) {
            vDialod = new Dialog(getActivity());
            vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
            vDialod.setCanceledOnTouchOutside(false);
            vDialod.setContentView(R.layout.date_picker_dialog);
            datePicker = (webit.bthereapp.General.DateTimePicker.DatePicker) vDialod.findViewById(R.id.datePicker);
            Button btnOk = (Button) vDialod.findViewById(R.id.ok);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR, datePicker.getYear());
                    c.set(Calendar.MONTH, datePicker.getMonth());
                    c.set(Calendar.DAY_OF_MONTH, datePicker.getDay());

                    mcurrentTime.set(Calendar.YEAR, datePicker.getYear());
                    mcurrentTime.set(Calendar.MONTH, datePicker.getMonth());
                    mcurrentTime.set(Calendar.DAY_OF_MONTH, datePicker.getDay());
                    date_txt.setText(datePicker.getDay() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear());
                    SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");

                    myorderObj.setDtDateOrder(ConnectionUtils.convertStringToDate(format3.format(c.getTime())));

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


//before order the qweue, we check if the qweue is free
    private void CheckIfOrderIsAvailable() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm user = realm.where(UserRealm.class).findFirst();
        if (user != null) {
            Double userId = user.getUserID();
            myorderObj.setiUserId(userId);
        }
        SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");
        myorderObj.setDtDateOrder(ConnectionUtils.convertStringToDate(format3.format(mcurrentTime.getTime())));

        myorderObj.setiSupplierId((int) provider.get_provider_id());
        myorderObj.setiProviderServiceId(type_service.get_many_choose());
        if (give_service.get_many_choose() != null) {
            myorderObj.setiSupplierUserId(give_service.get_many_choose());
        }

        myorderObj.setNvComment(comments.getText().toString());
        SimpleDateFormat mFormatter_2 = new SimpleDateFormat("HH:mm");

        myorderObj.setNvFromHour(mFormatter_2.format(mcurrentTime.getTime()));
        orderObj.setInstance(myorderObj);
        String jsonString = "{\"orderObj\":" + orderObj.getInstance().toString() + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.CheckIfOrderIsAvailable(getContext(), jsonObject, new IExecutable<String>() {
                    @Override
                    public void onExecute(String d) {
                        Calendar calendar = Calendar.getInstance();
                        OrderDetailsFragment.setInstance(null);
                        calendar.setTime(ConnectionUtils.JsonDateToDate(myorderObj.getDtDateOrder()));
                        SimpleDateFormat mFormatter_2 = new SimpleDateFormat("HH:mm");
                        OrderDetailsFragment.getInstance().set_hour(mFormatter_2.format(calendar.getTime()));
                        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
                        String formatted = format1.format(calendar.getTime());
                        OrderDetailsFragment.getInstance().set_date(formatted);
                        OrderDetailsFragment.getInstance().set_service_name(type_service.get_choose_txt());
                        OrderDetailsFragment.getInstance().set_b_name(provider.get_choose_txt());
                        OrderDetailsFragment.getInstance().set_adress(provider.adress);
                        OrderDetailsFragment.getInstance().set_day(calendar.get(Calendar.DAY_OF_WEEK));
                        calendar.setTime(ConnectionUtils.JsonDateToDate(d));
                        OrderDetailsFragment.getInstance().set_hour_end(mFormatter_2.format(calendar.getTime()));
                        if (d!=null) {
                            ((NavigetionLayout) getContext()).initFragmentMain(OrderDetailsFragment.getInstance(), true);
                            dismiss();
                        } else {
                            FragmentManager fm;
                            fm = ((NavigetionLayout) getContext()).getSupportFragmentManager();
                            NoEmptyQweueDialog newFragmentt = new NoEmptyQweueDialog(myorderObj);
                            newFragmentt.show(fm, "dialog");
                        }
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {
                        if (integer == 1) {
                            dismiss();
                        } else if (integer == -1) {

                            FragmentManager fm;
                            fm = ((NavigetionLayout) getContext()).getSupportFragmentManager();
                            NoEmptyQweueDialog newFragmentt = new NoEmptyQweueDialog(myorderObj);
                            newFragmentt.show(fm, "dialog");
                        } else if (integer == -2) {
                            Toast.makeText(getContext(), getString(R.string.no_enter_correct), Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }


    public void set_zero_in_service() {
        give_service.set_zero();
        type_service.set_zero();
    }

}
