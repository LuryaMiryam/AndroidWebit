package webit.bthereapp.BL;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Connection.VolleyConnection;
import webit.bthereapp.Entities.FieldAndCategory;
import webit.bthereapp.Entities.SysAlertsList;
import webit.bthereapp.Entities.objResult;
import webit.bthereapp.Intreface.IExecutable;


public class SplashBL {
    public static void GetSysAlertsList(final Context context, JSONObject json, final IExecutable<List<SysAlertsList>> onSuccess) {

        Type type = new TypeToken<objResult<ArrayList<SysAlertsList>>>() {
        }.getType();
        objResult<ArrayList<SysAlertsList>> arrayListobjResult;
        VolleyConnection<List<SysAlertsList>> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.GetSysAlertsList, json, new IExecutable<List<SysAlertsList>>() {
                    public void onExecute(List<SysAlertsList> sysAlertsLists) {
                        onSuccess.onExecute(sysAlertsLists);
                    }
                }
                , type);
    }


    public static void getFieldsAndCategories(final Context context, JSONObject json, final IExecutable<List<FieldAndCategory>> onSuccess) {

        Type type = new TypeToken<objResult<ArrayList<FieldAndCategory>>>() {
        }.getType();
        VolleyConnection<List<FieldAndCategory>> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.GetFieldsAndCatg, json, new IExecutable<List<FieldAndCategory>>() {
                    public void onExecute(List<FieldAndCategory> fieldAndCategory) {
                        onSuccess.onExecute(fieldAndCategory);
                    }
                }
                , type);
    }
}