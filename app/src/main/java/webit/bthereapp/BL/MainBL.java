package webit.bthereapp.BL;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Connection.VolleyConnection;
import webit.bthereapp.Entities.CouponObj;
import webit.bthereapp.Entities.CouponTypeObj;
import webit.bthereapp.Entities.CustomerAlertsSettingsObj;
import webit.bthereapp.Entities.CustomerObj;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.ProviderBuisnessDetailsObj;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.ProviderFreeDaysObj;
import webit.bthereapp.Entities.ProviderGeneralDetailsObj;
import webit.bthereapp.Entities.ProviderProfileObj;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Entities.WaitingListObj;
import webit.bthereapp.Entities.objProviderAlertsSettings;
import webit.bthereapp.Entities.SearchResulstsObj;

import webit.bthereapp.Entities.objResult;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;


/**
 * Created by User on 14/03/2016.
 */
public class MainBL {

    public static void getDetails(final Context context, String funcName, JSONObject json, final IExecutable<Double> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(funcName, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double id) {
                onSuccess.onExecute(id);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, (new objResult<Double>()).getClass());
    }

    public static void getDetails(final Context context, String funcName, JSONObject json, final IExecutable<Double> onSuccess,boolean isProgress) {
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context,isProgress,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(funcName, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double id) {
                onSuccess.onExecute(id);
            }
        }, (new objResult<Double>()).getClass());
    }
//

    public static void SearchProviders(final Context context, String funcName, JSONObject json, final IExecutable<ArrayList<SearchResulstsObj>> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<ArrayList<SearchResulstsObj>> volleyConnection = null;
        Type type = new TypeToken<objResult<ArrayList<SearchResulstsObj>>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection(context,true,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(funcName, json, new IExecutable<ArrayList<SearchResulstsObj>>() {
            @Override
            public void onExecute(ArrayList<SearchResulstsObj> searchResulsts) {
                onSuccess.onExecute(searchResulsts);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, type /*(new objResult<ArrayList<SearchResulstsObj>>()).getClass()*/);
    }


    public static void GetFreeDaysForServiceProvider(final Context context, JSONObject json, final IExecutable<ArrayList<ProviderFreeDaysObj>> onSuccess) {
        VolleyConnection<ArrayList<ProviderFreeDaysObj>> volleyConnection = null;
        Type type = new TypeToken<objResult<ArrayList<ProviderFreeDaysObj>>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection<>(context, true, context.getResources().getString(R.string.Wait_for_loading_the_selected_service_provider_calendar));
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.GetFreeDaysForServiceProvider, json, new IExecutable<ArrayList<ProviderFreeDaysObj>>() {
            @Override
            public void onExecute(ArrayList<ProviderFreeDaysObj> isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void GetCustomerOrders(final Context context, JSONObject json, final IExecutable<ArrayList<OrderDetailsObj>> onSuccess) {
        VolleyConnection<ArrayList<OrderDetailsObj>> volleyConnection = null;
        Type type = new TypeToken<objResult<ArrayList<OrderDetailsObj>>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection<>(context, true, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.GetCustomerOrders, json, new IExecutable<ArrayList<OrderDetailsObj>>() {
            @Override
            public void onExecute(ArrayList<OrderDetailsObj> isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void AddAlertSettingsForCustomer(final Context context, JSONObject json, final IExecutable<Double> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<Double> volleyConnection = null;
        Type type = new TypeToken<objResult<Double>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.AddAlertSettingsForCustomer, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, type);
    }

    public static void CheckIfOrderIsAvailable(final Context context, JSONObject json, final IExecutable<String> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<String> volleyConnection = null;
        Type type = new TypeToken<objResult<String>>() {}.getType();
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.CheckIfOrderIsAvailable, json, new IExecutable<String>() {
            @Override
            public void onExecute(String isValid) {
                onSuccess.onExecute(isValid);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        },type);
    }

    public static void getCouponTypeOptions(final Context context, JSONObject json, final IExecutable<ArrayList<CouponTypeObj>> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<ArrayList<CouponTypeObj>> volleyConnection = null;
        Type type = new TypeToken<objResult<ArrayList<CouponTypeObj>>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.getCouponTypeOptions, json, new IExecutable<ArrayList<CouponTypeObj>>() {
            @Override
            public void onExecute(ArrayList<CouponTypeObj> isValid) {
                onSuccess.onExecute(isValid);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, type);
    }

    public static void addNewCoupon(final Context context, JSONObject json, final IExecutable<Double> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<Double> volleyConnection = null;
        Type type = new TypeToken<objResult<Double>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.addNewCoupon, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, type);
    }

    public static void addUserToWaitingList(final Context context, JSONObject json, final IExecutable<Double> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<Double> volleyConnection = null;
        Type type = new TypeToken<objResult<Double>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.addUserToWaitingList, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, type);
    }

    public static void newOrder(final Context context, JSONObject json, final IExecutable<Double> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<Double> volleyConnection = null;
        Type type = new TypeToken<objResult<Double>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.newOrder, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, type);
    }

    public static void GetAlertSettingsForCustomer(final Context context, JSONObject json, final IExecutable<CustomerAlertsSettingsObj> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<CustomerAlertsSettingsObj> volleyConnection = null;
        Type type = new TypeToken<objResult<CustomerAlertsSettingsObj>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.GetAlertSettingsForCustomer, json, new IExecutable<CustomerAlertsSettingsObj>() {
            @Override
            public void onExecute(CustomerAlertsSettingsObj isValid) {
                onSuccess.onExecute(isValid);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, type);
    }

    public static void GetCouponListForCustomer (final Context context, JSONObject json, final IExecutable<ArrayList<CouponObj>> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<ArrayList<CouponObj>> volleyConnection = null;
        Type type = new TypeToken<objResult<ArrayList<CouponObj>>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.GetCouponListForCustomer , json, new IExecutable<ArrayList<CouponObj>>() {
            @Override
            public void onExecute(ArrayList<CouponObj> isValid) {
                onSuccess.onExecute(isValid);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, type);
    }

    public static void UpdateOrder(final Context context, JSONObject json, final IExecutable<Double> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<Double> volleyConnection = null;
        Type type = new TypeToken<objResult<Double>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.UpdateOrder, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, type);
    }

    public static void GetCustomerDetails(final Context context, JSONObject json, final IExecutable<UserObj> onSuccess) {
        Type type = new TypeToken<objResult<UserObj>>() {
        }.getType();
        VolleyConnection<UserObj> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.GetCustomerDetails, json, new IExecutable<UserObj>() {
            @Override
            public void onExecute(UserObj isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void UpdateUser(final Context context, JSONObject json, final IExecutable<Double> onSuccess) {
        Type type = new TypeToken<objResult<Double>>() {
        }.getType();
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.UpdateUser, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void GetProviderProfile(final Context context, JSONObject json, final IExecutable<ProviderProfileObj> onSuccess) {
        Type type = new TypeToken<objResult<ProviderProfileObj>>() {
        }.getType();
        VolleyConnection<ProviderProfileObj> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.GetProviderProfile, json, new IExecutable<ProviderProfileObj>() {
            @Override
            public void onExecute(ProviderProfileObj isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void GetCouponsForProvider(final Context context, JSONObject json, final IExecutable<ArrayList<CouponObj>> onSuccess) {
        Type type = new TypeToken<objResult<ArrayList<CouponObj>>>() {
        }.getType();
        VolleyConnection<ArrayList<CouponObj>> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.GetCouponsForProvider, json, new IExecutable<ArrayList<CouponObj>>() {
            @Override
            public void onExecute(ArrayList<CouponObj> isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void AddProviderAllDetails(final Context context, JSONObject json, final IExecutable<Double> onSuccess) {
        Type type = new TypeToken<objResult<Double>>() {
        }.getType();
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context, true, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.AddProviderAllDetails, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void SearchByKeyWord(final Context context, JSONObject json, final IExecutable<ArrayList<SearchResulstsObj>> onSuccess) {
        Type type = new TypeToken<objResult<ArrayList<SearchResulstsObj>>>() {
        }.getType();
        VolleyConnection<ArrayList<SearchResulstsObj>> volleyConnection = null;

        try {
//            volleyConnection = new VolleyConnection<>(context);
            volleyConnection = new VolleyConnection<>(context, true, context.getResources().getString(R.string.Wait_for_loading_the_results));

        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.SearchByKeyWord, json, new IExecutable<ArrayList<SearchResulstsObj>>() {
            @Override
            public void onExecute(ArrayList<SearchResulstsObj> isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void getServicesProviderForSupplier(final Context context, JSONObject json, final IExecutable<ArrayList<UserObj>> onSuccess) {
        Type type = new TypeToken<objResult<ArrayList<UserObj>>>() {
        }.getType();
        VolleyConnection<ArrayList<UserObj>> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.getServicesProviderForSupplier, json, new IExecutable<ArrayList<UserObj>>() {
            @Override
            public void onExecute(ArrayList<UserObj> isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void getWaitingListForCustomer(final Context context, JSONObject json, final IExecutable<ArrayList<WaitingListObj>> onSuccess) {
        Type type = new TypeToken<objResult<ArrayList<WaitingListObj>>>() {
        }.getType();
        VolleyConnection<ArrayList<WaitingListObj>> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.getWaitingListForCustomer, json, new IExecutable<ArrayList<WaitingListObj>>() {
            @Override
            public void onExecute(ArrayList<WaitingListObj> isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void getProviderServicesForSupplier(final Context context, JSONObject json, final IExecutable<ArrayList<ProviderServices>> onSuccess) {
        Type type = new TypeToken<objResult<ArrayList<ProviderServices>>>() {
        }.getType();
        VolleyConnection<ArrayList<ProviderServices>> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.getProviderServicesForSupplier, json, new IExecutable<ArrayList<ProviderServices>>() {
            @Override
            public void onExecute(ArrayList<ProviderServices> isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void GetProviderListForCustomer(final Context context, JSONObject json, final IExecutable<ArrayList<SearchResulstsObj>> onSuccess) {
        Type type = new TypeToken<objResult<ArrayList<SearchResulstsObj>>>() {
        }.getType();
        VolleyConnection<ArrayList<SearchResulstsObj>> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.GetProviderListForCustomer, json, new IExecutable<ArrayList<SearchResulstsObj>>() {
            @Override
            public void onExecute(ArrayList<SearchResulstsObj> isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void RemoveProviderFromCustomer(final Context context, JSONObject json, final IExecutable<Double> onSuccess) {
        Type type = new TypeToken<objResult<Double>>() {
        }.getType();
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.RemoveProviderFromCustomer, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void CancelOrder(final Context context, JSONObject json, final IExecutable<Double> onSuccess, final IExecutable<Integer> onError) {
        Type type = new TypeToken<objResult<Double>>() {
        }.getType();
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.CancelOrder, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, type);
    }

    public static void DeleteFromWaitingList(final Context context, JSONObject json, final IExecutable<Double> onSuccess) {
        Type type = new TypeToken<objResult<Double>>() {
        }.getType();
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.DeleteFromWaitingList, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void getProviderAllDetails(final Context context, JSONObject json, final IExecutable<ProviderDetailsObj> onSuccess) {
        Type type = new TypeToken<objResult<ProviderDetailsObj>>() {
        }.getType();
        VolleyConnection<ProviderDetailsObj> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context, true, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.getProviderAllDetails, json, new IExecutable<ProviderDetailsObj>() {
            @Override
            public void onExecute(ProviderDetailsObj isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void SearchWordCompletion(final Context context, JSONObject json, final IExecutable<ArrayList<String>> onSuccess) {
        Type type = new TypeToken<objResult<ArrayList<String>>>() {
        }.getType();
        VolleyConnection<ArrayList<String>> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context, false, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.SearchWordCompletion, json, new IExecutable<ArrayList<String>>() {
            @Override
            public void onExecute(ArrayList<String> isValid) {
                onSuccess.onExecute(isValid);
            }
        }, type);
    }

    public static void CheckProviderExistByPhone(final Context context, JSONObject json, final IExecutable<Double> onSuccess) {
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.CheckProviderExistByPhone, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, (new objResult<Double>()).getClass());
    }

    public static void CheckProviderExistByMail(final Context context, JSONObject json, final IExecutable<Double> onSuccess) {
        VolleyConnection<Double> volleyConnection = null;
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequest(ConnectionUtils.CheckProviderExistByMail, json, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                onSuccess.onExecute(isValid);
            }
        }, (new objResult<Double>()).getClass());
    }


    public static void getError(final Context context, String funcName, JSONObject json, final IExecutable<Integer> onSuccess, final IExecutable<Integer> onError) {
        VolleyConnection<Integer> volleyConnection = null;
        Type type = new TypeToken<objResult<Integer>>() {
        }.getType();
        try {
            volleyConnection = new VolleyConnection<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyConnection.postRequestError(funcName, json, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer isValid) {
                onSuccess.onExecute(isValid);
            }
        }, new IExecutable<Integer>() {
            @Override
            public void onExecute(Integer integer) {
                onError.onExecute(integer);
            }
        }, type);
    }
}