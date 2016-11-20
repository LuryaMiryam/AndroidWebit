package webit.bthereapp.Connection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

import webit.bthereapp.Entities.objResult;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Utils.MyApp;

/**
 * Created by User on 14/03/2016.
 */
public class VolleyConnection<T> {
    private String url = ConnectionUtils.getServerUrl();
    public T instance;
    private Context mContext;
    objResult result;
    private ProgressDialog progressDialog;
    boolean isProgressDialog;
    int MY_SOCKET_TIMEOUT_MS = 8000;


    public VolleyConnection(Context mContext, boolean isProgressDialog, String textProgress) {
        this.mContext = mContext;
        if (isProgressDialog) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage(textProgress.equals("") ? mContext.getResources().getString(R.string.please_wait) : textProgress);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        this.isProgressDialog = isProgressDialog;
    }

    public VolleyConnection(Context mContext) {
        this.mContext = mContext;
    }

    public void getRequest(String funName, final IExecutable<T> iExecutable) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        this.url = this.url + funName;
        if (!ConnectionUtils.isLive) Log.d("bthere", funName + ": " + this.url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Gson gson = new Gson();
                        Type type = new TypeToken<objResult<T>>() {
                        }.getType();
                        iExecutable.onExecute((T) gson.fromJson(response.toString(), type));
                        closeProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorStat(error);
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }


    public void postRequest(final String funName, JSONObject json, final IExecutable<T> iExecutable, final Class<? extends objResult> clazz) {

        try {
            result = clazz.newInstance();
            Class c = result.getClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
        url = url + funName;
        if (!ConnectionUtils.isLive) Log.d("bthere", funName + ": " + url);
        if (!ConnectionUtils.isLive) Log.d("bthere", funName + ": " + json.toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (!ConnectionUtils.isLive)
                            Log.d("bthere", funName + ": " + response.toString());
                        Type type = new TypeToken<objResult<T>>() {
                        }.getType();
                        Gson gson = new Gson();
                        objResult<T> tobjResult = gson.fromJson(response.toString(), clazz);
                        iExecutable.onExecute(tobjResult.getResult());
                        closeProgressDialog();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErrorStat(error);
                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(mContext).addToRequestQueue(jsObjRequest);
    }

    public void postRequest(final String funName, JSONObject json, final IExecutable<T> iExecutable, final Type type) {
        try {
            Class c = result.getClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
        url = url + funName;
        if (!ConnectionUtils.isLive) Log.d("bthere", funName + ": " + url);
        if (json != null)
            if (!ConnectionUtils.isLive) Log.d("bthere", funName + ": " + json.toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (!ConnectionUtils.isLive)
                            Log.d("bthere", funName + ": " + response.toString());
                        Gson gson = new Gson();
                        objResult<T> tobjResult = gson.fromJson(response.toString(), type);
                        Log.d("result from server: ", tobjResult.getResult() + "");
                        iExecutable.onExecute(tobjResult.getResult());
                        closeProgressDialog();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErrorStat(error);
                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(mContext).addToRequestQueue(jsObjRequest);
    }



    public void postRequest(final String funName, JSONObject json, final IExecutable<T> iExecutable, final IExecutable<Integer> iError, final Type type) {
        url = url + funName;
        if (!ConnectionUtils.isLive) Log.d("bthere", funName + ": " + url);
        if (!ConnectionUtils.isLive) Log.d("bthere", funName + ": " + json.toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (!ConnectionUtils.isLive) Log.d("bthere", funName + ": " + response.toString());
                        Gson gson = new Gson();
                        objResult<T> tobjResult = gson.fromJson(response.toString(), type);
                        if (tobjResult.getError() != null && tobjResult.getError().getErrorCode() != 1) {
                            iError.onExecute(tobjResult.getError().getErrorCode());
                        } else
                            iExecutable.onExecute(tobjResult.getResult());
                        closeProgressDialog();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErrorStat(error);
                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(mContext).addToRequestQueue(jsObjRequest);
    }

    public void postRequestError(final String funName, JSONObject json, final IExecutable<T> iExecutable, final IExecutable<Integer> iError, final Type type) {
        url = url + funName;
        if (!ConnectionUtils.isLive) Log.d("bthere", funName + ": " + url);
        if (!ConnectionUtils.isLive) Log.d("bthere", funName + ": " + json.toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (!ConnectionUtils.isLive)
                            Log.d("bthere", funName + ": " + response.toString());
                        Gson gson = new Gson();
                        objResult<T> tobjResult = gson.fromJson(response.toString(), type);
                        iError.onExecute(tobjResult.getError().getErrorCode());
                        iExecutable.onExecute(tobjResult.getResult());
                        closeProgressDialog();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErrorStat(error);
                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(mContext).addToRequestQueue(jsObjRequest);
    }

    private void ErrorStat(VolleyError error) {
        if (!ConnectionUtils.isNetworkAvailable(mContext)) {
            displayNoInternetAlert(mContext);
        }else{
            if (mContext != null&&!((Activity) mContext).isFinishing()) {
                Toast.makeText(mContext, R.string.there_is_error,Toast.LENGTH_LONG).show();

            }
        }
        if (!ConnectionUtils.isLive) {
            Log.d("bthere", "error.getMessage() :" + error.getMessage());
            Log.d("bthere", "error :" + error);
        }
        closeProgressDialog();
    }

    public static void displayNoInternetAlert(Context context) {
        if (context != null) {
            final AlertDialog.Builder netNotAvailable = new AlertDialog.Builder(context);
            netNotAvailable.setMessage(context.getResources().getString(R.string.no_internet_connection));
            netNotAvailable.setNeutralButton(context.getResources().getString(R.string.ok), null);
            if(!((Activity) context).isFinishing()) {
                netNotAvailable.show();
            }
        }
    }

    private void closeProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

}