package webit.bthereapp.Screens.Customer;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.GetFreeDaysForServiceProvider;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Entities.orderObj;
import webit.bthereapp.Entities.ProviderFreeDaysObj;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Utils.CalendarUtil;
import webit.bthereapp.Utils.Utils;

/**
 * Created by user on 23/03/2016.
 */
public class AdapterService extends BaseAdapter {
    Context mContext;
    int widthScroll, widthButtons;
    int width, height, conwidth;
    List<View> myList = new ArrayList<>();
    boolean isClicked = false, orderChangedByOrderMany = false;
    int positionOfOrderMany;

    public void setListOfServices(ArrayList<ProviderServices> listOfServices) {
        arr = new int[listOfServices.size()];
        myList.clear();
        this.listOfServices = listOfServices;
    }
    ArrayList<ProviderServices> listOfServices;
    private boolean isService = true, isFloat = true;
    private int arr[];
    private long providerId_;
    private String strings[];
    private SearchResulstsObj searchResulstsObj;
    private ArrayList<UserObj> userObjs_ = new ArrayList<>();


    public AdapterService(Context context, ArrayList<ProviderServices> listOfServices, SearchResulstsObj searchResulstsObj,long id) {
        this.listOfServices = listOfServices;
        for (int i = 0; i < this.listOfServices.size(); i++)
            arr = new int[listOfServices.size()];
        providerId_ = searchResulstsObj.iProviderId;
        if(providerId_==0)
            providerId_=id;
        this.searchResulstsObj = searchResulstsObj;
        mContext = context;
        if (context != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        }

        if(mContext!=null) {
            conwidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, mContext.getResources().getDisplayMetrics());
        }
        getServicesProviderForSupplier(providerId_);
    }

    @Override
    public int getCount() {
        return listOfServices.size();
    }

    @Override
    public ProviderServices getItem(int position) {
        return listOfServices.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int position_ = position;
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.row_service, null);
            holder = new ViewHolder();
            holder.all = (RelativeLayout) convertView.findViewById(R.id.row_r);
            holder.mOrderManyImageIV = (ImageView) convertView.findViewById(R.id.order_many_image);
            holder.mOrderTextTV = (TextView) convertView.findViewById(R.id.order_text);
            holder.mOrderR = (RelativeLayout) convertView.findViewById(R.id.order);
            holder.mOrderManyR = (RelativeLayout) convertView.findViewById(R.id.choose_many);
            holder.mOrderManyTextTV = (TextView) convertView.findViewById(R.id.text_choose_many);
            holder.scroll = (HorizontalScrollView) convertView.findViewById(R.id.scroll);
            holder.mInformationR = (RelativeLayout) convertView.findViewById(R.id.more_information);
            holder.mTypeTv = (TextView) convertView.findViewById(R.id.service_type);
            holder.mTimeTv = (TextView) convertView.findViewById(R.id.time);
            holder.mPriceTv = (TextView) convertView.findViewById(R.id.price);
            holder.shachTV = (TextView) convertView.findViewById(R.id.shach);
            holder.seperateV = convertView.findViewById(R.id.seperate);
            holder.minTv = (TextView) convertView.findViewById(R.id.min);
            holder.mChooseButtons = (LinearLayout) convertView.findViewById(R.id.buttons);
            RelativeLayout.LayoutParams paramsB = (RelativeLayout.LayoutParams) holder.mChooseButtons.getLayoutParams();
            widthButtons = paramsB.width;
            holder.mChooseButtonsR = (RelativeLayout) convertView.findViewById(R.id.r_buttons);
            holder.mRowRL = (LinearLayout) convertView.findViewById(R.id.service_row);
            holder.service_type_ll = (RelativeLayout) convertView.findViewById(R.id.service_type_ll);
            holder.mServiceR = (RelativeLayout) convertView.findViewById(R.id.row_r);
            widthScroll = widthButtons + width;
            //play animation of scroll to the first service
            if (position == 0) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator animator = ObjectAnimator.ofInt(holder.scroll, "scrollX", holder.mOrderR.getRight());
                        animator.setDuration(1000);
                        animator.start();

                        ObjectAnimator animator1 = ObjectAnimator.ofInt(holder.scroll, "scrollX", holder.mInformationR.getLeft());
                        animator1.setDuration(1000);
                        animator1.setStartDelay(1000);
                        animator1.start();
                    }
                });
            } else {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        holder.scroll.scrollTo(holder.mInformationR.getLeft(), 0);
                    }
                });
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // when comes with a new list needs to initialize the view...
        ////////////////check////////////////////
        isClicked = false;
        orderChangedByOrderMany = false;
        holder.choosenClick = false;
        holder.mOrderManyImageIV.setImageResource(R.drawable.circle_empty);
        holder.mOrderManyTextTV.setVisibility(View.VISIBLE);
        holder.mOrderManyTextTV.setText(R.string.choose_many);
        holder.mOrderR.setVisibility(View.VISIBLE);
        holder.mOrderTextTV.setText(R.string.order);
        ProviderServices item = getItem(position);
        RelativeLayout.LayoutParams params;
        holder.all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int arr2[] = new int[1];
                arr2[0] = listOfServices.get(position_).getIProviderServiceId();
                GetFreeDaysForServiceProvider.getInstance().setlProviderServiceId(arr2);
                boolean g = false;
                if (GetFreeDaysForServiceProvider.getInstance().getlProviderServiceId().length > 1) {
                    for (int i : GetFreeDaysForServiceProvider.getInstance().getlProviderServiceId())
                        if (i > 0)
                            g = true;
                } else
                    g = true;
                if (g)
                    GetCustomerOrders(position_);
                else
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.choose_service), Toast.LENGTH_SHORT).show();
            }
        });
        // set the width of the whole horizantal scroll to the width of the screen plus the width of the three buttons
        if (holder.mChooseButtonsR != null) {
            params = (RelativeLayout.LayoutParams) holder.mChooseButtonsR.getLayoutParams();
            params.width = widthScroll;
            holder.mChooseButtonsR.setLayoutParams(params);
        }
       //set the width of the services detailes to the width of the screen
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) holder.mRowRL.getLayoutParams();
        params1.width = width;
        holder.mRowRL.setLayoutParams(params1);

     // set the width of the whole horizantal scroll to the width of the screen plus the width of the three buttons
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) holder.mServiceR.getLayoutParams();
        params2.width = widthScroll;
        holder.mServiceR.setLayoutParams(params2);
        holder.service_type_ll.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        holder.mTypeTv.setText(item.getNvServiceName() + "");
        if (isService) {
            //if its service show the time

//            if (item.getiUntilSeviceTime() != 0)
//                //if the time is sepatre to two( there is no possibility any more to separate...)
//                holder.mTimeTv.setText(item.getiTimeOfService() + "-" + item.getiUntilSeviceTime());
//            else
            holder.mTimeTv.setVisibility(View.VISIBLE);
            holder.minTv.setVisibility(View.VISIBLE);
            holder.seperateV.setVisibility(View.VISIBLE);
            holder.mTimeTv.setText(item.getiTimeOfService() + "");
        } else {
            //dont show the time
            holder.mTimeTv.setVisibility(View.GONE);
            holder.minTv.setVisibility(View.GONE);
            holder.seperateV.setVisibility(View.GONE);
        }
        //check if the price is suppose to be shown
        if (item.getiPrice() != 0) {
            //make the price shown
            holder.mPriceTv.setVisibility(View.VISIBLE);
            holder.shachTV.setVisibility(View.VISIBLE);
            //check if the price is float
            if (item.getiPrice() == (int) item.getiPrice())
                isFloat = false;
            else
                isFloat = true;
            //check if the price is seperte to two
            if (item.getnUntilPrice() != 0) {
                if (isFloat)
                    //show as float
                    holder.mPriceTv.setText(item.getiPrice() + "-" + item.getnUntilPrice());
                else
                    //show as int
                    holder.mPriceTv.setText((int) item.getiPrice() + "-" + (int) item.getnUntilPrice());
            } else {
                if (isFloat)
                    holder.mPriceTv.setText(item.getiPrice() + "");
                else
                    holder.mPriceTv.setText((int) item.getiPrice() + "");
            }
        } else {
            //make the price not shown
            holder.mPriceTv.setVisibility(View.GONE);
            holder.shachTV.setVisibility(View.GONE);
            holder.seperateV.setVisibility(View.GONE);
        }
        holder.mOrderManyR.setClickable(true);
        holder.mOrderManyR.setEnabled(true);
        //if the user check to order many queues
        holder.mOrderManyR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    isClicked = true;
                    orderChangedByOrderMany = true;
                    holder.mOrderTextTV.setText(R.string.choose_checked);
                    holder.mOrderManyImageIV.setImageResource(R.drawable.client_galaxy_icons1_29);
                    arr[position] = listOfServices.get(position).getIProviderServiceId();
                    holder.choosenClick = true;
                    changeList(position_);
                    //save the position of the place that there's the text "order many"
                    positionOfOrderMany = position_;

                } else {
                    if (!holder.choosenClick) {
                        holder.choosenClick = true;
                        arr[position] = listOfServices.get(position).getIProviderServiceId();
                        holder.mOrderManyImageIV.setImageResource(R.drawable.client_galaxy_icons1_29);
                        // if the place that was pressed is before the text "order many"
                        if (position_ < positionOfOrderMany) {
                            //so set the text  here
                            holder.mOrderR.setVisibility(View.VISIBLE);
                            holder.mOrderTextTV.setText(R.string.choose_checked);
                            //and  remove the text from the old place
                            View vv = myList.get(positionOfOrderMany);
                            RelativeLayout mOrderR = (RelativeLayout) vv.findViewById(R.id.order);
                            mOrderR.setVisibility(View.INVISIBLE);
                            positionOfOrderMany = position_;
                        }
                    } else {
                        arr[position_] = 0;
                        holder.mOrderManyImageIV.setImageResource(R.drawable.circle_empty);
                        holder.choosenClick = false;
                        //if erase the place of the "order many" text
                        if (positionOfOrderMany == position_) {
                            holder.mOrderR.setVisibility(View.INVISIBLE);
                            boolean found = false;
                            int firstChosenPosition = -1;
                            for (int i = 0; i < arr.length && !found; i++) {
                                if (arr[i] != 0) {
                                    found = true;
                                    firstChosenPosition = i;
                                }
                            }
                            if (firstChosenPosition != -1) {
                                positionOfOrderMany = firstChosenPosition;
                                View v1 = myList.get(firstChosenPosition);
                                RelativeLayout mOrderR = (RelativeLayout) v1.findViewById(R.id.order);
                                mOrderR.setVisibility(View.VISIBLE);
                                TextView mOrderTextTV = (TextView) v1.findViewById(R.id.order_text);
                                mOrderTextTV.setText(R.string.choose_checked);
                            } else {
                                //if there is nothing chosen so bring it back to regular
                                changeBackList();
                                isClicked = false;
                                orderChangedByOrderMany = false;
                            }


                        }
                    }
                }
            }
        });
        //if the user check to order queue
        holder.mOrderR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderChangedByOrderMany) {
                    int s = 0;
                    for (int i = 0; i < arr.length; i++)
                        if (arr[i] != 0)
                            s++;
                    int s_arr[] = new int[s];
                    s = 0;
                    for (int i = 0; i < arr.length; i++)
                        if (arr[i] != 0)
                            s_arr[s++] = arr[i];
                    GetFreeDaysForServiceProvider.getInstance().setlProviderServiceId(s_arr);
                    boolean g = false;
                    if (GetFreeDaysForServiceProvider.getInstance().getlProviderServiceId().length > 1) {
                        for (int i : GetFreeDaysForServiceProvider.getInstance().getlProviderServiceId())
                            if (i > 0)
                                g = true;
                    } else
                        g = true;
                    if (g)
                        GetCustomerOrders(position_);
                    else
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.choose_service), Toast.LENGTH_SHORT).show();
                } else {
                    int arr2[] = new int[1];
                    arr2[0] = listOfServices.get(position_).getIProviderServiceId();
                    GetFreeDaysForServiceProvider.getInstance().setlProviderServiceId(arr2);
                    boolean g = false;
                    if (GetFreeDaysForServiceProvider.getInstance().getlProviderServiceId().length > 1) {
                        for (int i : GetFreeDaysForServiceProvider.getInstance().getlProviderServiceId())
                            if (i > 0)
                                g = true;
                    } else
                        g = true;
                    if (g)
                        GetCustomerOrders(position_);
                    else
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.choose_service), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.mInformationR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hightTop = ChosenServiceFragment.mTopFL.getHeight();
                InformationServiceFragment informationServiceFragment=new InformationServiceFragment();
                informationServiceFragment.set_ProviderService(listOfServices.get(position));
                ((NavigetionLayout) mContext).initFragmentAll(informationServiceFragment, true, hightTop);
            }
        });
        myList.add(convertView);
        return convertView;
    }

    private void GetFreeDaysForServiceProvider(final int position__) {
        int[] arr_a;
        if (strings != null && strings.length > 0) {
            arr_a = new int[strings.length - 1];
            for (int i = 0; i < userObjs_.size(); i++) {
                arr_a[i] = (int) userObjs_.get(i).getiUserId();
            }
            GetFreeDaysForServiceProvider.getInstance().setlServiseProviderId(arr_a);
        }
        orderObj.getInstance().setiProviderServiceId(GetFreeDaysForServiceProvider.getInstance().getlProviderServiceId());
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(GetFreeDaysForServiceProvider.getInstance().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetFreeDaysForServiceProvider(mContext, jsonObject, new IExecutable<ArrayList<ProviderFreeDaysObj>>() {
            @Override
            public void onExecute(ArrayList<ProviderFreeDaysObj> providerFreeDaysObj_) {
                if (providerFreeDaysObj_ != null) {
                    if (providerFreeDaysObj_.size() > 0) {
                        CalendarUtil.taskCalanderListFree = providerFreeDaysObj_;
                        OrderServiceFragment.removeInstance();
                        OrderServiceFragment.getInstance().setProviderId(providerId_);
                        Bundle bundle = new Bundle();
                        bundle.putString("SearchResulstsObj", new Gson().toJson(searchResulstsObj));
                        String s = "";
                        for (ProviderServices providerServices : listOfServices)
                            for (int id : GetFreeDaysForServiceProvider.getInstance().getlProviderServiceId())
                                if (providerServices.getIProviderServiceId() == id)
                                    if (s.equals(""))
                                        s += (providerServices.getNvServiceName());
                                    else
                                        s += (", " + providerServices.getNvServiceName());

                        OrderDetailsFragment.getInstance().set_service_name(s);
                        s = searchResulstsObj.nvProviderSlogan;
                        bundle.putString("srvType", s);
                        OrderServiceFragment.getInstance().setArguments(bundle);
                        ((NavigetionLayout) mContext).initFragmentMain(OrderServiceFragment.getInstance()/*OrderServiceFragment.newInstance("", providerId_)*/, true);
                    } else {
                        Toast.makeText(mContext, R.string.no_found_qweues, Toast.LENGTH_LONG).show();
                        CalendarUtil.taskCalanderListFree = new ArrayList<ProviderFreeDaysObj>();
                    }
                } else {
                }
                OrderDetailsFragment.getInstance().setService_id(listOfServices.get(position__).getIProviderServiceId());
            }
        });
    }

    private void GetCustomerOrders(int position_) {
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
            jsonObject = new JSONObject(jsonString.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetCustomerOrders(mContext, jsonObject, new IExecutable<ArrayList<OrderDetailsObj>>() {
            @Override
            public void onExecute(ArrayList<OrderDetailsObj> OrderDetailsObj_) {
                if (OrderDetailsObj_ != null) {
                    if (OrderDetailsObj_.size() > 0) {
                        CalendarUtil.OrderDetailsObj_ = OrderDetailsObj_;
                    }
                } else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.no_found_orders_to_this_customer), Toast.LENGTH_LONG).show();
                    CalendarUtil.OrderDetailsObj_ = new ArrayList<>();
                }
            }
        });
        GetFreeDaysForServiceProvider(position_);
    }

    private void getServicesProviderForSupplier(double d) {
        String jsonString = "{\"iProviderId\":" + d + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getServicesProviderForSupplier(mContext, jsonObject, new IExecutable<ArrayList<UserObj>>() {
            @Override
            public void onExecute(ArrayList<UserObj> userObjs) {
                if (userObjs != null) {
                    if (userObjs.size() > 0) {
                        userObjs_ = new ArrayList<>(userObjs);
                        strings = new String[userObjs_.size() + 1];
                        int j = 0;
                        strings[j++] = mContext.getResources().getStringArray(R.array.staff_member_list)[0];
                        for (int i = 0; i < userObjs_.size(); i++) {
                            strings[j++] = (userObjs_.get(i).getNvFirstName() + " " + userObjs_.get(i).getNvLastName());
                        }
                    } else {
                        //no found giving service to this provider"
                        userObjs_ = new ArrayList<>(userObjs);
                        strings = new String[0];
                        Toast.makeText(mContext, R.string.no_found_giving_service, Toast.LENGTH_LONG).show();
                    }
                    CalendarUtil.serviceProviders = userObjs;
                } else
                    CalendarUtil.serviceProviders = new ArrayList<UserObj>();
            }
        });
    }


    private void changeList(int position) {
        // int childCount = mServiceListLV.getChildCount();
        for (int i = 0; i < listOfServices.size(); i++) {
            View v = myList.get(i);
            TextView mOrderManyTextTV = (TextView) v.findViewById(R.id.text_choose_many);
            mOrderManyTextTV.setVisibility(View.INVISIBLE);
            ImageView mOrderManyImageIV = (ImageView) v.findViewById(R.id.order_many_image);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mOrderManyImageIV.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            mOrderManyImageIV.setLayoutParams(params);
            if (i != position) {
                RelativeLayout mOrderR = (RelativeLayout) v.findViewById(R.id.order);
                mOrderR.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void changeBackList() {
        for (int i = 0; i < myList.size(); i++) {
            View v = myList.get(i);
            TextView mOrderManyTextTV = (TextView) v.findViewById(R.id.text_choose_many);
            mOrderManyTextTV.setVisibility(View.VISIBLE);
            ImageView mOrderManyImageIV = (ImageView) v.findViewById(R.id.order_many_image);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mOrderManyImageIV.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            mOrderManyImageIV.setLayoutParams(params);
            RelativeLayout mOrderR = (RelativeLayout) v.findViewById(R.id.order);
            mOrderR.setVisibility(View.VISIBLE);
            TextView mOrderTextTV = (TextView) v.findViewById(R.id.order_text);
            mOrderTextTV.setText(R.string.order);
        }
    }

    public void setIsService(boolean b) {
        isService = b;
    }

    class ViewHolder {
        RelativeLayout mOrderR, mInformationR, all, mOrderManyR, mChooseButtonsR, mServiceR,service_type_ll;
        ImageView mOrderManyImageIV;
        TextView mOrderTextTV, mTypeTv, mTimeTv, mPriceTv, shachTV, minTv;
        HorizontalScrollView scroll;
        LinearLayout mChooseButtons, mRowRL;
        View seperateV;
        boolean choosenClick = false;
        TextView mOrderManyTextTV;
    }
}
