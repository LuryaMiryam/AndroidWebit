package webit.bthereapp.Screens.Customer;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.Entities.orderObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NoEmptyQweueDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoEmptyQweueDialog extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CustomLightButton option1, option2, option3, option4;
    private ImageButton close;
    private orderObj obj_;
    private Dialog vDialod = null;

    private webit.bthereapp.General.DateTimePicker.DatePicker datePicker;

    public NoEmptyQweueDialog() {
        // Required empty public constructor
    }

    public NoEmptyQweueDialog(orderObj orderObj_) {
        // Required empty public constructor
        obj_ = orderObj_;
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
    public static NoEmptyQweueDialog newInstance(String param1, String param2) {
        NoEmptyQweueDialog fragment = new NoEmptyQweueDialog();
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
        View view = inflater.inflate(R.layout.fragment_no_empty_qweue_dialog, container, false);

        option1 = (CustomLightButton) view.findViewById(R.id.option_1);
        option2 = (CustomLightButton) view.findViewById(R.id.option_2);
        option3 = (CustomLightButton) view.findViewById(R.id.option_3);
        option4 = (CustomLightButton) view.findViewById(R.id.option_4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        close = (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.option_1: {
                addUserToWaitingList();
                break;
            }
            case R.id.option_2: {

                break;
            }
            case R.id.option_3: {
                openDialogDate();
                break;
            }
            case R.id.option_4: {

                break;
            }
            case R.id.close: {
                dismiss();
                break;
            }

        }
    }

    private void addUserToWaitingList() {
        String jsonString = "{\"orderObj\":" + obj_.toString() + "}";

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.addUserToWaitingList(getContext(), jsonObject, new IExecutable<Double>() {
                    @Override
                    public void onExecute(Double d) {
                        if (d != 0) {
                            //if the order added to the waiting list, the dialogs closed

                            Toast.makeText(getContext(), getResources().getString(R.string.enter_waiting_list), Toast.LENGTH_LONG).show();
                            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                            if (prev != null) {
                                DialogFragment df = (DialogFragment) prev;
                                df.dismiss();
                            }
                            Fragment prev2 = getActivity().getSupportFragmentManager().findFragmentByTag("NewAppointmentFromCustomer");
                            if (prev2 != null) {
                                DialogFragment df = (DialogFragment) prev2;
                                df.dismiss();
                            }
                            Fragment prev3 = getActivity().getSupportFragmentManager().findFragmentByTag("dialogMyQweues");
                            if (prev3 != null) {
                                DialogFragment df = (DialogFragment) prev3;
                                df.dismiss();
                            }
                            dismiss();
                        } else {

                        }
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {
                        if (integer == 1) {
                        } else if (integer == -1 || integer == -2) {
                            Toast.makeText(getContext(), getResources().getString(R.string.cant_enter_waiting_list), Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
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
                    SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");
                    obj_.setDtDateOrder(ConnectionUtils.convertStringToDate(format3.format(c.getTime())));
                    newOrder();
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

    private void newOrder() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(obj_.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.newOrder(getContext(), jsonObject, new IExecutable<Double>() {
                    @Override
                    public void onExecute(Double d) {
                        if (d != 0) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(ConnectionUtils.JsonDateToDate(obj_.getDtDateOrder()));
                            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
                            String formatted = format1.format(calendar.getTime());
                            OrderDetailsFragment.getInstance().set_date(formatted);
                            OrderDetailsFragment.getInstance().set_day(calendar.get(Calendar.DAY_OF_WEEK));
                            ((NavigetionLayout) getContext()).initFragmentMain(OrderDetailsFragment.getInstance(), true);
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.no_in_this_day), Toast.LENGTH_LONG).show();

                        }
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {
                        if (integer == 1) {
                        } else if (integer == -1) {
                            Toast.makeText(getContext(), getResources().getString(R.string.no_in_this_day), Toast.LENGTH_LONG).show();
                        } else if (integer == -2) {
                            Toast.makeText(getContext(), getResources().getString(R.string.no_in_this_day), Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

}
