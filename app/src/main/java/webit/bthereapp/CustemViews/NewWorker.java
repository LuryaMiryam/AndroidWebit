package webit.bthereapp.CustemViews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Entities.ProviderGeneralDetailsObj;
import webit.bthereapp.Entities.ServiceProviders;
import webit.bthereapp.Entities.WorkingHours;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Supplier.ExistsSuplierDefinitionsActivity;

/**
 * Created by user on 09/03/2016.
 */
public class NewWorker extends LinearLayout implements View.OnClickListener {
    Context mContext;
    String mName, name;
    private View view, mSepeartView;
    TextView mNameTv;
    DetailsWorkers dw;
    DetailsWorkers detailsWorkers, savedDW;
    boolean clicked = true;
    LinearLayout mDetailsWorkersR;
    private DialogInterface.OnClickListener dialogClickListener;
    List<ServiceProviders> getWorkersList;
    ServiceProviders mFoundWorker;
    private RelativeLayout pencil, delete;
    boolean flagSav = true, firstTime = true;
    LinearLayout scroll;

    public NewWorker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, null, null);
    }

    public NewWorker(Context context, AttributeSet attrs, String name, DetailsWorkers dw) {
        super(context, attrs);
        init(context, attrs, name, dw);
    }

    public void init(final Context context, AttributeSet attrs, String name, DetailsWorkers dw) {
        view = View.inflate(context, R.layout.add_worker, this);
        this.mContext = context;
        this.mName = name;
        this.dw = dw;
        detailsWorkers = new DetailsWorkers(mContext, null);
        if(dw!=null&&! dw.sameHours){
            dw.workHours.setVisibility(VISIBLE);
            dw.mBreaksL.setVisibility(GONE);
            dw.mBreaks = 0;
        }
        detailsWorkers = dw;
        mDetailsWorkersR = (LinearLayout) view.findViewById(R.id.con_deatils_worker);
        mSepeartView = view.findViewById(R.id.view_second);
        if (this.mContext instanceof MainActivity)
            scroll = ((BusinessGeneralData) (((MainActivity) mContext).getSupportFragmentManager().findFragmentByTag(BusinessGeneralData.class.toString()))).getScroll();
        else
            scroll = ((BusinessGeneralData) (((ExistsSuplierDefinitionsActivity) mContext).getSupportFragmentManager().findFragmentByTag(BusinessGeneralData.class.toString()))).getScroll();
        delete = (RelativeLayout) view.findViewById(R.id.delete_w);
        delete.setOnClickListener(this);
        pencil = (RelativeLayout) view.findViewById(R.id.pencil_w);
        pencil.setOnClickListener(this);

        mNameTv = (TextView) view.findViewById(R.id.worker_name_w);
        if (name != null)
            mNameTv.setText(name);

        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        delete_this();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };
    }

    //delete selected worker
    private void delete_this() {
        String phone = detailsWorkers.get_phone();
        getWorkersList = ProviderGeneralDetailsObj.getInstance().getServiceProviders();
        //finding the worker that is being edit by the unique phone
        for (ServiceProviders serviceProviders : getWorkersList) {
            if (serviceProviders.getObjUsers().getNvPhone().equals(phone)) {
                mFoundWorker = serviceProviders;
            }
        }
        if (mContext instanceof MainActivity)
            ((BusinessGeneralData) (((MainActivity) mContext).getSupportFragmentManager().findFragmentByTag(BusinessGeneralData.class.toString()))).delete_worker(this);
        else
            ((BusinessGeneralData) (((ExistsSuplierDefinitionsActivity) mContext).getSupportFragmentManager().findFragmentByTag(BusinessGeneralData.class.toString()))).delete_worker(this);

        ProviderGeneralDetailsObj.getInstance().getServiceProviders().remove(detailsWorkers.getNewServiceProvider());
        getWorkersList.remove(mFoundWorker);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_w: {
                delete.setClickable(false);
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(getResources().getString(R.string.delete_q) + " " + detailsWorkers.getName() + "?").setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                delete.setClickable(true);
                break;
            }
            case R.id.pencil_w: {
                pencil.setClickable(false);
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                //open the worker for editing
                if (clicked) {
                    //show hours
                    detailsWorkers.workHours.setVisibility(VISIBLE);
                    detailsWorkers.mBreaksL.setVisibility(GONE);
                    detailsWorkers.mBreaks = 0;
                    //save the  phone number before editing
                    String phone = detailsWorkers.get_phone();
                    detailsWorkers.oldPhoneNumber=phone;
                    detailsWorkers.oldMail=detailsWorkers.getMail();
                    getWorkersList = ProviderGeneralDetailsObj.getInstance().getServiceProviders();
                    //finding the worker that is being edit by the unique phone
                    for (ServiceProviders serviceProviders : getWorkersList) {
                        if (serviceProviders.getObjUsers().getNvPhone().equals(phone)) {
                            mFoundWorker = serviceProviders;
                        }
                    }
                    //look's like can make shorter to this
                   // mFoundWorker=detailsWorkers.getNewServiceProvider();

                    boolean r = false;
                    if (mContext instanceof MainActivity) {
                        if (((BusinessGeneralData) (((MainActivity) mContext).getSupportFragmentManager().findFragmentByTag
                                (BusinessGeneralData.class.toString()))).SaveAndNext(5))
                            r = true;
                    } else if (((BusinessGeneralData) (((ExistsSuplierDefinitionsActivity) mContext).getSupportFragmentManager().findFragmentByTag
                            (BusinessGeneralData.class.toString()))).SaveAndNext(5))
                        r = true;
                    //open first time or not edit now
                    if (r) {
//                        //save the phone number and the mail that is inserted now,to enable to input it again
//                        detailsWorkers.oldPhoneNumber=mFoundWorker.getObjUsers().getNvPhone();
//                        detailsWorkers.oldMail=mFoundWorker.getObjUsers().getNvMail();
                        if (flagSav|| firstTime) {
                            detailsWorkers.workerPhone.setError(null);
                            detailsWorkers.workerMail.setError(null);
                            Log.d("ggg","111");
                            if(detailsWorkers.getParent() == null) {
                                Log.d("ggg","222");
                                mDetailsWorkersR.addView(detailsWorkers);
                            }
                        }
                        else {
                            savedDW.workerPhone.setError(null);
                            savedDW.workerMail.setError(null);
                            mDetailsWorkersR.addView(savedDW);
                        }
                        clicked = false;
                        if (mContext instanceof MainActivity)
                            scroll = ((BusinessGeneralData) (((MainActivity) mContext).getSupportFragmentManager().findFragmentByTag(BusinessGeneralData.class.toString()))).getScroll();
                        else
                            scroll = ((BusinessGeneralData) (((ExistsSuplierDefinitionsActivity) mContext).getSupportFragmentManager().findFragmentByTag(BusinessGeneralData.class.toString()))).getScroll();
                        focusOnView(mDetailsWorkersR);
                        mSepeartView.setVisibility(VISIBLE);
                    }
                }
                //close the edit worker
                else {
                    if (mContext instanceof MainActivity)
                        ((BusinessGeneralData) (((MainActivity) mContext).getSupportFragmentManager().
                                findFragmentByTag(BusinessGeneralData.class.toString()))).getField_arr()[5] = false;
                    else
                        ((BusinessGeneralData) (((ExistsSuplierDefinitionsActivity) mContext).getSupportFragmentManager().
                                findFragmentByTag(BusinessGeneralData.class.toString()))).getField_arr()[5] = false;
                    clicked = true;
                    //check the new edit  details for saving
                    saveEditWorkerNew(dw.service_provider_id);
                    if(detailsWorkers!=null&&! detailsWorkers.sameHours) {
                        detailsWorkers.workHours.setVisibility(VISIBLE);
                        detailsWorkers.mBreaksL.setVisibility(GONE);
                    }
                    mSepeartView.setVisibility(GONE);
                    mSepeartView.setVisibility(GONE);
                }
                pencil.setClickable(true);

                break;
            }
        }
    }

//check the new edit details worker
    public boolean saveEditWorkerNew(int i) {
        //remove the worker that was edited with his old details
        if(getWorkersList.remove(mFoundWorker))
        clicked = true;
        //check the new details of the worker
        if (detailsWorkers.checkAllFieldsNew(i)) {
            //if all the fields are right so check the validate from the server
           phone_validity_server(detailsWorkers.get_phone(),detailsWorkers.getMail());
        } else {
            //if the details are not correct so save the previous details of the worker
            detailsWorkers.save_previous(mFoundWorker);
            ProviderGeneralDetailsObj.getInstance().getServiceProviders().add(mFoundWorker);
            flagSav = false;
            //close the details worker
            mDetailsWorkersR.removeAllViews();
        }
        return true;
    }

    //save the new edit worker's details after checked
  private void saveCheckedEditWorker() {
    flagSav = true;
    firstTime = false;
    //add a new worker with the new details
    ProviderGeneralDetailsObj.getInstance().getServiceProviders().add(detailsWorkers.getNewServiceProvider());
    savedDW = detailsWorkers;
    String s = detailsWorkers.getName();
    mNameTv.setText(s);
    mDetailsWorkersR.removeAllViews();
}

    public void phone_validity_server(final String phone, final String workerMail) {
        //checking  that the worker's phone is not exists as a giving service or supplier
        String jsonString = "{\"nvPhone\":\"" + phone + "\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.CheckProviderExistByPhone(mContext, jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                if (isValid == 0) {
                    //check that the phone number is not existed for one of the workers that were inserted now
                    boolean notExisted = true;
                    ServiceProviders sp;
                    if (ProviderGeneralDetailsObj.getInstance().getServiceProviders().size() > 0)
                        for (int i = 0; i < ProviderGeneralDetailsObj.getInstance().getServiceProviders().size(); i++) {
                            sp = ProviderGeneralDetailsObj.getInstance().getServiceProviders().get(i);
                            if (sp.getObjUsers().getNvPhone().equals(phone)/* && i == service_provider_id*/)
                                notExisted = false;
//                            //when editing a worker enable inserting the phone that is written there now
                            //not needed because the worker that is edited now was removed
//                            if (!detailsWorkers.oldPhoneNumber.equals(""))
//                                if (phone.equals(detailsWorkers.oldPhoneNumber))
//                                    notExisted = true;
                        }
                    if (notExisted) {
                        //if the validation server of phone is correct, so check the validation server of mail
                        mail_validity_server(workerMail);
                    } else {
                        Toast.makeText(mContext,getResources().getString(R.string.existed_phone_for_worker),Toast.LENGTH_SHORT).show();
                        detailsWorkers.save_previous(mFoundWorker);
                        ProviderGeneralDetailsObj.getInstance().getServiceProviders().add(mFoundWorker);
                        flagSav = false;
                        mDetailsWorkersR.removeAllViews();
                    }
                } else {
                    Toast.makeText(mContext,getResources().getString(R.string.exists_phone_error),Toast.LENGTH_SHORT).show();
                    detailsWorkers.save_previous(mFoundWorker);
                    ProviderGeneralDetailsObj.getInstance().getServiceProviders().add(mFoundWorker);
                    flagSav = false;
                    mDetailsWorkersR.removeAllViews();
                }
            }
        });
    }

    public void mail_validity_server(final String mail) {
        //checking  that the worker's mail is not exists as a giving service or supplier
        String jsonString = "{\"nvMail\":\"" + mail + "\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.CheckProviderExistByMail(mContext, jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                if (isValid == 0) {
                    //check that the mail is not existed for one of the workers that were inserted now
                    boolean notExisted = true;
                    ServiceProviders sp;
                    if (ProviderGeneralDetailsObj.getInstance().getServiceProviders().size() > 0)
                        for (int i = 0; i < ProviderGeneralDetailsObj.getInstance().getServiceProviders().size(); i++) {
                            sp = ProviderGeneralDetailsObj.getInstance().getServiceProviders().get(i);
                            if (sp.getObjUsers().getNvMail().equals(mail) /*&& i == service_provider_id*/)
                                notExisted = false;
                            //when editing a worker enable inserting the mail that is written there now
                            //not needed because it was removed
//                            if (!detailsWorkers.oldMail.equals(""))
//                                if (mail.equals(detailsWorkers.oldMail))
//                                    notExisted = true;
                        }
                    if (notExisted) {
                        // all the validation are ok, so save the edit worker
                        saveCheckedEditWorker();
                    } else {
                        //the mail is not correct, so put back the previous worker
                        Toast.makeText(mContext,getResources().getString(R.string.existed_mail_for_worker),Toast.LENGTH_SHORT).show();
                        detailsWorkers.save_previous(mFoundWorker);
                        ProviderGeneralDetailsObj.getInstance().getServiceProviders().add(mFoundWorker);
                        flagSav = false;
                        mDetailsWorkersR.removeAllViews();
                    }
                } else {
                    //the mail is not correct, so put back the previous worker
                    Toast.makeText(mContext,getResources().getString(R.string.exists_mail_error),Toast.LENGTH_SHORT).show();
                    detailsWorkers.save_previous(mFoundWorker);
                    ProviderGeneralDetailsObj.getInstance().getServiceProviders().add(mFoundWorker);
                    flagSav = false;
                    mDetailsWorkersR.removeAllViews();
                }
            }
        });
    }

    //show the view in the middle of the screen
    public void focusOnView(final View view) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if ((mContext instanceof MainActivity)&&scroll!=null)
                    ((ScrollView) scroll.getParent().getParent().getParent().getParent().getParent()).scrollTo(0, view.getBottom());
            }
        });
    }

    public boolean getClicked() {
        return clicked;
    }

    public boolean saveEditWorker(int i) {
        //remove the worker that was edited with his old details
        getWorkersList.remove(mFoundWorker);
        clicked = true;
        //check the new details of the worker
        if (detailsWorkers.checkAllFields(i)) {
            flagSav = true;
            firstTime = false;
            //add a new worker with the new details
            ProviderGeneralDetailsObj.getInstance().getServiceProviders().add(detailsWorkers.getNewServiceProvider());
            savedDW = detailsWorkers;
            String s = detailsWorkers.getName();
            mNameTv.setText(s);
            mDetailsWorkersR.removeAllViews();
            return true;
        } else {
            //if the details are not correct so save the previous details of the worker
            detailsWorkers.save_previous(mFoundWorker);
            flagSav = false;
            mDetailsWorkersR.removeAllViews();
            return true;
        }
    }
}


