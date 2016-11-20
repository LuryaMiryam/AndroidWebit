package webit.bthereapp.Screens.Customer;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NoEmptyQweueAfterUpdateDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoEmptyQweueAfterUpdateDialog extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CustomLightButton option1, option2, option3, option4;
    private ImageButton close;
    private OrderDetailsObj obj;
    private OrderDetailsObj obj_;

    public NoEmptyQweueAfterUpdateDialog() {
        // Required empty public constructor
    }

    public NoEmptyQweueAfterUpdateDialog(OrderDetailsObj orderDetailsObj, OrderDetailsObj orderObj_) {
        // Required empty public constructor
        obj = orderDetailsObj;
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
    public static NoEmptyQweueAfterUpdateDialog newInstance(String param1, String param2) {
        NoEmptyQweueAfterUpdateDialog fragment = new NoEmptyQweueAfterUpdateDialog();
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
        View view = inflater.inflate(R.layout.fragment_no_empty_qweue_after_update_dialog, container, false);

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
                //enter to waiting list
                if (obj != null)
                    addUserToWaitingList();

                break;
            }
            case R.id.option_2: {
                //save the exists qweue
                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                //close the opened dialogs
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev;
                    if (df != null)
                        df.dismiss();
                }
                Fragment prev2 = getActivity().getSupportFragmentManager().findFragmentByTag("UpdateAppointmentFromCustomer");
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev2;
                    if (df != null)
                        df.dismiss();
                }
                Fragment prev3 = getActivity().getSupportFragmentManager().findFragmentByTag("dialogMyQweues");
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev3;
                    if (df != null)
                        df.dismiss();
                }
                dismiss();
                break;
            }
            case R.id.option_3: {
                //Nearby vacant queues
                break;
            }
            case R.id.option_4: {
                //cancel the qweue
                CancelOrder();
                break;
            }
            case R.id.close: {
                dismiss();
                break;
            }

        }
    }

    private void CancelOrder() {
        String jsonString = "{\"iCoordinatedServiceId\":" + obj.getiCoordinatedServiceId() + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.CancelOrder(getContext(), jsonObject, new IExecutable<Double>() {
                    //            ProviderBL.AddProviderUser(mContext,jsonObject,new IExecutable<Double>()
                    @Override
                    public void onExecute(Double id) {
                        //close all the opened dialog
                        if (id == 1) {
                            //if the order canceled, the dialogs closed
                            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                            if (prev != null) {
                                DialogFragment df = (DialogFragment) prev;
                                df.dismiss();
                            }
                            Fragment prev2 = getActivity().getSupportFragmentManager().findFragmentByTag("UpdateAppointmentFromCustomer");
                            if (prev2 != null) {
                                DialogFragment df = (DialogFragment) prev2;
                                df.dismiss();
                            }
                            Fragment prev3 = getActivity().getSupportFragmentManager().findFragmentByTag("dialogMyQweues");
                            if (prev3 != null) {
                                DialogFragment df = (DialogFragment) prev3;
                                df.dismiss();
                            }
                            Toast.makeText(getContext(), R.string.qweue_canceled, Toast.LENGTH_LONG).show();
                            FragmentTopUser.removeInstance();
                            ((NavigetionLayout)getActivity()).initFragmentMain(FragmentTopUser.getInstance(), true);
                            dismiss();
                        } else {
                            //if the order didnt canceled, the dialogs closed
                            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                            if (prev != null) {
                                DialogFragment df = (DialogFragment) prev;
                                df.dismiss();
                            }
                            Fragment prev2 = getActivity().getSupportFragmentManager().findFragmentByTag("UpdateAppointmentFromCustomer");
                            if (prev2 != null) {
                                DialogFragment df = (DialogFragment) prev2;
                                df.dismiss();
                            }
                            Fragment prev3 = getActivity().getSupportFragmentManager().findFragmentByTag("dialogMyQweues");
                            if (prev3 != null) {
                                DialogFragment df = (DialogFragment) prev3;
                                df.dismiss();
                            }
                            Toast.makeText(getContext(), R.string.general_error_msg, Toast.LENGTH_LONG).show();
                            dismiss();
                        }
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {

                    }
                }
        );
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
                            //if the order didnt added to waiting list, the dialogs closed
                            Toast.makeText(getContext(), getResources().getString(R.string.enter_waiting_list), Toast.LENGTH_LONG).show();
                            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                            if (prev != null) {
                                DialogFragment df = (DialogFragment) prev;
                                df.dismiss();
                            }
                            Fragment prev2 = getActivity().getSupportFragmentManager().findFragmentByTag("UpdateAppointmentFromCustomer");
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

}
