package webit.bthereapp.Connection;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import java.io.ByteArrayOutputStream;
import java.nio.channels.GatheringByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.NewAppointmentFromCustomer;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Utils.MyApp;
import webit.bthereapp.Utils.Utils;


/**
 * Created by New_Android_Team on 17/12/2014.
 */
public class ConnectionUtils {

    public static int return_from_camera = 0;
    public static boolean which_calendar = true;
    public static boolean if_server = true;
    public static LatLng CurrentLatLng = null;
    public static ArrayList<SearchResulstsObj> resulstsObjs = new ArrayList<>();
    public static boolean isLive = false;

    public static String getProviderServicesForSupplier = "getProviderServicesForSupplier";
    public static String getWaitingListForCustomer = "getWaitingListForCustomer";
    public static String getServicesProviderForSupplier = "getServicesProviderForSupplier";
    public static String RemoveProviderFromCustomer = "RemoveProviderFromCustomer";
    public static String DeleteFromWaitingList = "DeleteFromWaitingList";
    public  static String GetProviderListForCustomer = "GetProviderListForCustomer";
    public static String SearchByKeyWord = "SearchByKeyWord";
    public static String GetCouponsForProvider = "GetCouponsForProvider";
    public static String GetProviderBuisnessDetails = "GetProviderBuisnessDetails";
    public static String GetProviderContact = "GetProviderContact";
    public static String GetProviderProfile = "GetProviderProfile";
    public static String GetProviderAlertSettings = "GetProviderAlertSettings";
    public static String getProviderAllDetails = "getProviderAllDetails";
    public static String GetCustomerDetails = "GetCustomerDetails";
    public static String UpdateUser  = "UpdateUser";
    public static String GetProviderDetails = "GetProviderDetails";
    public static String AddAlertSettingsForCustomer = "AddAlertSettingsForCustomer";
    public static String CheckIfOrderIsAvailable = "CheckIfOrderIsAvailable";
    public static String CancelOrder = "CancelOrder";
    public static String CheckMailValidity = "CheckMailValidity";
    public static String CheckPhoneValidity = "CheckPhoneValidity";
    public static String RegisterUser = "RegisterUser";
    public static String GetFieldsAndCatg = "GetFieldsAndCatg";
    public static String GetSysAlertsList = "GetSysAlertsList";
    public static String AddProviderUser = "AddProviderUser";
    public static String GetAndSmsValidationCode = "GetAndSmsValidationCode";
    public static String AddProviderAllDetails = "AddProviderAllDetails";
    public static String SendAgainSms = "SendAgainSms";
    public static String addNewCoupon = "addNewCoupon";
    public static String UpdateOrder = "UpdateOrder";
    public static String newOrder = "newOrder";
    public static String GetAlertSettingsForCustomer = "GetAlertSettingsForCustomer";
    public static String GetCouponListForCustomer  = "GetCouponListForCustomer ";
    public static String addUserToWaitingList = "addUserToWaitingList";
    public static String getCouponTypeOptions = "getCouponTypeOptions";
    public static String PrintCalendar = "PrintCalendar";
    public static String LoginUser = "LoginUser";
    public static String RestoreVerCode = "RestoreVerCode";
    public static String SearchProviders = "SearchProviders";
    public static String OrderDetailsObj = "OrderDetailsObj";
    public static String GetCustomerOrders = "GetCustomerOrders";
    public static String GetFreeDaysForServiceProvider = "GetFreeDaysForServiceProvider";
    public static String SearchWordCompletion = "SearchWordCompletion";
    public static String CheckProviderExistByPhone  = "CheckProviderExistByPhone";
    public static String CheckProviderExistByMail  = "CheckProviderExistByMail";


    public static boolean isOnLine(Context mcontext) {
        Context context = MyApp.getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting())
            return true;
        return false;
    }


    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return !(networkInfo == null || !networkInfo.isConnected());
        }
        return true;
    }

    public static boolean isRunning(Context ctx) {
        try {
            ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
            for (ActivityManager.RunningTaskInfo task : tasks) {
                if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                    return true;
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static void setLanguage(Context context, String languageToLoad) {
//        String languageToLoad = "fr"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
        String loca = Locale.getDefault().getDisplayLanguage();
    }

    public static String convertBitmapToBase64(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream1);
            if (!isLive) Log.d("BITMAP-SIZE:", byteArrayOutputStream.size() + ":" + byteArrayOutputStream1.size());
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        return "";
    }


    public static String convertStringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("iw")), zoneFormat = new SimpleDateFormat("Z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        java.util.Date date1 = new java.util.Date();
        try {
            date1 = dateFormat.parse(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return ("/Date(" + String.valueOf(date1.getTime()) + ")/");//+ zoneFormat.format(date1)
    }
    public static String convertStringToDateTime(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("iw")), zoneFormat = new SimpleDateFormat("Z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        java.util.Date date1 = new java.util.Date();
        try {
            date1 = dateFormat.parse(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return ("/Date(" + String.valueOf(date1.getTime()) + ")/");//+ zoneFormat.format(date1)
    }

    public static Date convertJsonDate_(String timeString) {
        Date time=new Date();
        if (timeString != null) {
            timeString = timeString.substring(timeString.indexOf("(") + 1,
                    timeString.indexOf(")"));
            String[] timeSegments = timeString.split("\\+");
            long timeZoneOffSet=0,millis=0;
            if(timeSegments.length>1)
            timeZoneOffSet = Long.valueOf(timeSegments[1]) * 36000;
            if(timeSegments.length>0)
            millis= Long.valueOf(timeSegments[0]);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:ss");
            time = new Date(millis + timeZoneOffSet);
        }
        return time;
    }



    public static Date convertJsonDate_2(String jsonDate)
    {
        if(jsonDate==null||jsonDate.length()==0){
            return new Date();
        }
        //  "/Date(1321867151710+0100)/"
        int idx1 = jsonDate.indexOf("(");
        int idx2 = jsonDate.indexOf(")") - 5;
        String s = jsonDate.substring(idx1+1, idx2);
        long l = Long.valueOf(s);
        return new Date(l);
    }
    public static Date JsonDateToDate(String jsonDate)
    {
        if(jsonDate==null||jsonDate.length()==0){
            return new Date();
        }
        //  "/Date(1321867151710+0100)/"
        int idx1 = jsonDate.indexOf("(");
        int idx2 = jsonDate.indexOf(")") - 5;
        String s = jsonDate.substring(idx1+1, idx2);
        long l = Long.valueOf(s);
        return new Date(l);
    }

    public static Bitmap convertStringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static void displayGeneralErrorAlert(Context mcontext) {
        Context context = MyApp.getContext();
        boolean b = context instanceof Activity;
        if ((b) && isRunning(context)) {
            final AlertDialog.Builder generalError = new AlertDialog.Builder(context);
            generalError.setMessage(context.getResources().getString(R.string.general_error_msg));
            generalError.setTitle(context.getResources().getString(R.string.attention));
            generalError.setNeutralButton(context.getResources().getString(R.string.ok), null);
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    generalError.show();
                }
            });
        }
    }

    public static void displayNoInternetAlert(Context mcontext) {
        Context context;
        if (MyApp.getContext() == null)
            context = mcontext;
        else
            context = MyApp.getContext();
        if (isRunning(context)) {
            final AlertDialog.Builder netNotAvailable = new AlertDialog.Builder(context);
            netNotAvailable.setMessage(context.getResources().getString(R.string.no_internet_connection));
            netNotAvailable.setTitle(context.getResources().getString(R.string.attention));
            netNotAvailable.setNeutralButton(context.getResources().getString(R.string.ok), null);
            netNotAvailable.show();
//            if (context instanceof Activity) {
//                ((Activity) context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        netNotAvailable.show();
//                    }
//                });
//            }
        }
    }


    public static void displayGeneraAlert(Context mcontext, String msg) {
        Context context = MyApp.getContext();
        boolean b = context instanceof Activity;
        if ((b) && isRunning(context)) {
            final AlertDialog.Builder generalError = new AlertDialog.Builder(context);
            generalError.setMessage(msg);
            generalError.setTitle(context.getResources().getString(R.string.attention));
            generalError.setNeutralButton(context.getResources().getString(R.string.ok), null);
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    generalError.show();
                }
            });
        }
    }

    public static String getServerUrl() {
        if (isLive)
            return "http://ws.webit-track.com/BThereLiveWs/BThereWS.svc/";
        else
//            return "http://10.0.0.225//BThereWS/BThereWS.svc/";
            return "http://ws.webit-track.com/BThereWS/BThereWS.svc/";
//            return "http://10.0.0.88/BThereWS/BThereWS.svc/";

    }
}



