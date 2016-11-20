package webit.bthereapp.Screens.Customer;

/**
 * Created by user on 22/03/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.CustomSpinnerLargeBlack;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.ProviderServiceDetailsObj;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Utils.Utils;

public class AdapterMyQweues extends BaseAdapter {
    Context mContext;
    private java.util.Date date;
    private Calendar calendar = Calendar.getInstance();

    ArrayList<OrderDetailsObj> orderDetailsObjs;
    int d;

    public AdapterMyQweues(Context context, ArrayList<OrderDetailsObj> orderDetailsObjs, int v) {
        mContext = context;
        this.orderDetailsObjs = orderDetailsObjs;
        this.d = v;
    }

    @Override
    public int getCount() {
        return orderDetailsObjs.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.row_order, null);
        final int position_ = position;
        TextView mDateTv = (TextView) convertView.findViewById(R.id.date);
        TextView mTimeTv = (TextView) convertView.findViewById(R.id.time);
        TextView mTypeTv = (TextView) convertView.findViewById(R.id.type);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        //set image logo of the bussines
        if (orderDetailsObjs.get(position).getNvLogo() != null && !(orderDetailsObjs.get(position).getNvLogo().equals(""))) {
            String logoString = orderDetailsObjs.get(position).getNvLogo().toString();
            byte[] decodedString = Base64.decode(logoString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }
        date = ConnectionUtils.JsonDateToDate(orderDetailsObjs.get(position).getDtDateOrder());
        calendar.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        mDateTv.setText(format.format(calendar.getTime()));
        SimpleDateFormat mFormatter_2 = new SimpleDateFormat("HH:mm");

        mTimeTv.setText(mFormatter_2.format(calendar.getTime()));

        String s = "";
        for (ProviderServiceDetailsObj providerServiceDetailsObj : orderDetailsObjs.get(position).getObjProviderServiceDetails())
            if (s.equals(""))
                s += providerServiceDetailsObj.getNvServiceName();
            else
                s += (", " + providerServiceDetailsObj.getNvServiceName());

        mTypeTv.setText(s);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (d == 1) {
                    //open dialog of Update Appointment
                    GetProviderListForCustomer(position_);
                }
                if (d == 2) {
                    //open dialog of dalay
                    FragmentManager fm;
                    fm = ((NavigetionLayout) mContext).getSupportFragmentManager();
                    DelayDialogFromCustomerFragment newFragment2 = new DelayDialogFromCustomerFragment(orderDetailsObjs.get(position));
                    newFragment2.show(fm, "DelayDialogFromCustomerFragment");
                }
                if (d == 3) {
                    //open dialog of Cancel Qweue
                    FragmentManager fm;
                    fm = ((NavigetionLayout) mContext).getSupportFragmentManager();
                    CancelQweueDialogFromCustomerFragment newFragment2 = new CancelQweueDialogFromCustomerFragment(orderDetailsObjs.get(position));
                    newFragment2.show(fm, "CancelQweueDialogFromCustomerFragment");

                }
            }
        });
        return convertView;
    }

    private void GetProviderListForCustomer(final int p) {
        Realm realm = Utils.getRealmInstance(mContext);
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        double d;
        if (userRealm != null)
            d = userRealm.getUserID();
        else
            d = 0;
        String jsonString = "{\"iUserId\":" + d + "}";

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetProviderListForCustomer(mContext, jsonObject, new IExecutable<ArrayList<SearchResulstsObj>>() {
                    @Override
                    public void onExecute(ArrayList<SearchResulstsObj> list) {

                        if (list != null) {
                            if (list.size() > 0) {
                                UpdateAppointmentFromCustomer.removeInstance();
                                SearchResulstsObj searchResulstsObj_ = new SearchResulstsObj();
                                for (SearchResulstsObj searchResulstsObj : list)
                                    if (orderDetailsObjs.get(p).getNvSupplierName().toString().equals(searchResulstsObj.nvProviderName.toString())) {
                                        searchResulstsObj_ = searchResulstsObj;
                                    }
                                UpdateAppointmentFromCustomer.getInstance().set_provider(searchResulstsObj_.iProviderId);
//                                delete the provider and the give service
                                CustomSpinnerLargeBlack.provider_id = 0;
                                CustomSpinnerLargeBlack.give_service_id = 0;
                                getServicesProviderForSupplier(searchResulstsObj_.iProviderId);
                            }
                        }
                    }

                    private void getServicesProviderForSupplier(final int provider_id) {

                        String jsonString = "{\"iProviderId\":" + provider_id + "}";
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(jsonString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MainBL.getServicesProviderForSupplier(mContext, jsonObject, new IExecutable<ArrayList<UserObj>>() {
                            @Override
                            public void onExecute(ArrayList<UserObj> userObjs_) {
                                CustomSpinnerLargeBlack.give_service_id = 0;
                                if (userObjs_ != null) {
                                    CustomSpinnerLargeBlack.userObjs = userObjs_;
                                    CustomSpinnerLargeBlack.if_userObjs_changed = true;

                                } else {
                                    CustomSpinnerLargeBlack.userObjs = new ArrayList<UserObj>();
                                }
                                getProviderServicesForSupplier(provider_id);

                            }
                        });

                    }


                    private void getProviderServicesForSupplier(int provider_id) {
                        String jsonString = "{\"iProviderId\":" + provider_id + "}";
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(jsonString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MainBL.getProviderServicesForSupplier(mContext, jsonObject, new IExecutable<ArrayList<ProviderServices>>() {
                            @Override
                            public void onExecute(ArrayList<ProviderServices> providerServices_) {

                                if (providerServices_ != null) {
                                    CustomSpinnerLargeBlack.providerServices = providerServices_;
                                    CustomSpinnerLargeBlack.if_providerServices_changed = true;

                                } else
                                    CustomSpinnerLargeBlack.providerServices = new ArrayList<>();
                                FragmentManager fm = ((NavigetionLayout) mContext).getSupportFragmentManager();
                                UpdateAppointmentFromCustomer.getInstance().setOrderDetailsObj(orderDetailsObjs.get(p));
                                UpdateAppointmentFromCustomer.getInstance().show(fm, "UpdateAppointmentFromCustomer");
                            }
                        });
                    }
                }
        );
    }
}

