package webit.bthereapp.BL;

import android.content.Context;

import org.json.JSONObject;

import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Connection.VolleyConnection;
import webit.bthereapp.Entities.objResult;
import webit.bthereapp.Intreface.IExecutable;

/**
 * Created by User on 14/03/2016.
 */
public class RegisterBL {

    public static void RegisterUser(final Context context, JSONObject json, final IExecutable<Double> onSuccess)  {
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context,true,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.RegisterUser, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double id) {
                onSuccess.onExecute(id);
            }
        }, (new objResult<Double>()).getClass());
    }

    public static void verificationCode(final Context context, JSONObject json, final IExecutable<Double> onSuccess)  {
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context,true,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.GetAndSmsValidationCode, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double id) {
                onSuccess.onExecute(id);
            }
        }, (new objResult<Double>()).getClass());
    }


    public static void checkMailValidity(final Context context, JSONObject json, final IExecutable<Double> onSuccess)  {
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.CheckMailValidity, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, (new objResult<Double>()).getClass());
    }

    public static void CheckPhoneValidity(final Context context, JSONObject json, final IExecutable<Double> onSuccess) {
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.CheckPhoneValidity, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, (new objResult<Double>()).getClass());
    }

    public static void loginUser(final Context context, JSONObject json, final IExecutable<Double> onSuccess) {
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.LoginUser, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, (new objResult<Double>()).getClass());
    }


}
