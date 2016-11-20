package webit.bthereapp.Screens.Customer;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.ProviderServiceDetailsObj;
import webit.bthereapp.Entities.SysAlertsListItem;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Utils.Utils;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link UpdateAppointmentFromCustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateAppointmentFromCustomer extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Dialog vDialod = null;
    private webit.bthereapp.General.DateTimePicker.DatePicker datePicker;
    private CustomLightEditText comments;
    private Calendar mcurrentTime = Calendar.getInstance();
    private CustomSpinnerLargeBlack provider, give_service, type_service;
    private CustomLightButton ok;
    private OrderDetailsObj orderObj = new OrderDetailsObj();
    private static CustomLightTextView date_txt, hour_txt;
    private RelativeLayout date_, hour;
    private ImageButton close;
    private OrderDetailsObj orderDetailsObj;
    private Date date;
    private int provider_id;
    private Calendar calendar = Calendar.getInstance();
    private CustomViewTimePicker timePicker;


    public UpdateAppointmentFromCustomer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewAppointmentFromCustomer.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateAppointmentFromCustomer newInstance(String param1, String param2) {
        UpdateAppointmentFromCustomer fragment = new UpdateAppointmentFromCustomer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static UpdateAppointmentFromCustomer instance;

    public static UpdateAppointmentFromCustomer getInstance() {
        if (instance == null)
            instance = new UpdateAppointmentFromCustomer();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_appointment_from_customer, container, false);
            CustomSpinnerLargeBlack.provider_id = 1;
            CustomSpinnerLargeBlack.give_service_id = 1;


            ok = (CustomLightButton) view.findViewById(R.id.ok);
            close = (ImageButton) view.findViewById(R.id.close);

            date_txt = (CustomLightTextView) view.findViewById(R.id.date_tv);
            date_ = (RelativeLayout) view.findViewById(R.id.date);
            date_.setOnClickListener(this);

            ok.setOnClickListener(this);
            close.setOnClickListener(this);
            timePicker = (CustomViewTimePicker) view.findViewById(R.id.cvTimePicker);

            hour = (RelativeLayout) view.findViewById(R.id.hour);
            hour_txt = (CustomLightTextView) view.findViewById(R.id.hour_tv);
            give_service = (CustomSpinnerLargeBlack) view.findViewById(R.id.new_app_give_service);
            type_service = (CustomSpinnerLargeBlack) view.findViewById(R.id.black_type_service);
            hour = (RelativeLayout) view.findViewById(R.id.hour);
            hour_txt = (CustomLightTextView) view.findViewById(R.id.hour_tv);
            comments = (CustomLightEditText) view.findViewById(R.id.comments);
            hour_txt.setText("00:00");
            hour.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    timePicker.setVisibility(timePicker.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    if (timePicker.getVisibility() == View.GONE) {
                        if (toBigerFrom(timePicker.from, timePicker.to)) {
                            hour_txt.setText(timePicker.from + " - " + timePicker.to);
                            int hour = Integer.parseInt(timePicker.from.substring(0, 2));
                            int minute = Integer.parseInt(timePicker.from.substring(3, 5));
                            mcurrentTime.set(Calendar.HOUR_OF_DAY, hour);
                            mcurrentTime.set(Calendar.MINUTE, minute);
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.hours_not_correct2), Toast.LENGTH_LONG).show();
                            hour_txt.setText("");
                            mcurrentTime.set(Calendar.HOUR_OF_DAY, 0);
                            mcurrentTime.set(Calendar.MINUTE, 0);
                        }
                    }
                }
            });

            date = ConnectionUtils.convertJsonDate_2(orderDetailsObj.getDtDateOrder());
            calendar.setTime(date);
            mcurrentTime.setTime(date);
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
            date_txt.setText(format.format(calendar.getTime()));
            SimpleDateFormat mFormatter_2 = new SimpleDateFormat("HH:mm");

            hour_txt.setText(mFormatter_2.format(calendar.getTime()));

            String s = "";
            for (ProviderServiceDetailsObj providerServiceDetailsObj : orderDetailsObj.getObjProviderServiceDetails())
                if (s.equals(""))
                    s += providerServiceDetailsObj.getNvServiceName();
                else
                    s += (", " + providerServiceDetailsObj.getNvServiceName());
            ArrayList<Integer> a = new ArrayList<>();
            int z = 0;
            give_service.set_old(orderDetailsObj.getiProviderUserId());
            for (ProviderServiceDetailsObj i : orderDetailsObj.getObjProviderServiceDetails())
                a.add(orderDetailsObj.getObjProviderServiceDetails()[z++].getiProviderServiceId());
            type_service.set_old_array(a);
        return view;
    }

    public void setOrderDetailsObj(OrderDetailsObj orderDetailsObj) {
        this.orderDetailsObj = orderDetailsObj;
        this.orderObj = orderDetailsObj;
    }

    private boolean toBigerFrom(String nvFromHour, String nvToHour) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date from = sdf.parse(nvFromHour);
            Date to = sdf.parse(nvToHour);
            return from.compareTo(to) != 1;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.date: {
                openDialogDate();
                break;
            }
            case R.id.close: {
                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev;
                    df.dismiss();
                }
                dismiss();
                break;
            }
            case R.id.ok: {
                UpdateOrder();
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

    private void UpdateOrder() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm user = realm.where(UserRealm.class).findFirst();
        if (user != null) {
            int userId = (int) user.getUserID();
            orderObj.setiUserId(userId);
        }
        ArrayList<SysAlertsListItem> arr_ = type_service.get_many_choose_with_txt();
        ProviderServiceDetailsObj[] arr = new ProviderServiceDetailsObj[arr_.size()];
        for (int i = 0; i < arr_.size(); i++)
            arr[i] = new ProviderServiceDetailsObj(arr_.get(i).getItem_id(), arr_.get(i).getItem_txt());
        orderObj.setObjProviderServiceDetails(arr);

        if (give_service.get_many_choose() != null) {
            orderObj.setiProviderUserId(give_service.get_choose());
        }

        orderObj.setNvComment(comments.getText().toString());

        SimpleDateFormat format4 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        orderObj.setiDayInWeek(mcurrentTime.get(Calendar.DAY_OF_WEEK));
        orderObj.setDtDateOrder(ConnectionUtils.convertStringToDateTime(format4.format(mcurrentTime.getTime())));
        String jsonString = "{\"orderDetailsObj\":" + orderObj.toString() + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.UpdateOrder(getContext(), jsonObject, new IExecutable<Double>() {
                    @Override
                    public void onExecute(Double d) {
                        if (d != 0) {
                            Toast.makeText(getContext(), getResources().getString(R.string.update_ok), Toast.LENGTH_LONG).show();
                            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialogMyQweues");
                            if (prev != null) {
                                DialogFragment df = (DialogFragment) prev;
                                df.dismiss();
                            }
                            FragmentTopUser.removeInstance();
                            ((NavigetionLayout) getActivity()).initFragmentMain(FragmentTopUser.getInstance(), true);

                        } else {
                            FragmentManager fm;
                            fm = ((NavigetionLayout) getContext()).getSupportFragmentManager();
                            NoEmptyQweueAfterUpdateDialog newFragmentt = new NoEmptyQweueAfterUpdateDialog(orderDetailsObj, orderObj);
                            newFragmentt.show(fm, "dialog");
                        }
                        dismiss();
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {
                        if (integer == 1) {
                            dismiss();
                        } else if (integer == -1) {
                            FragmentManager fm;
                            fm = ((NavigetionLayout) getContext()).getSupportFragmentManager();
                            NoEmptyQweueAfterUpdateDialog newFragmentt = new NoEmptyQweueAfterUpdateDialog(orderDetailsObj, orderObj);
                            newFragmentt.show(fm, "dialog");

                        } else if (integer == -2) {
                            Toast.makeText(getContext(), getString(R.string.no_enter_correct), Toast.LENGTH_LONG).show();
                        }
                        dismiss();
                    }

                }
        );
    }


    public void set_provider(int a) {
        provider_id = a;
    }
}
