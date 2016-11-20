package webit.bthereapp.CustemViews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.CouponObj;
import webit.bthereapp.Entities.CustomerAlertsSettingsObj;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.WaitingListObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;

import webit.bthereapp.Screens.Customer.LastMinuteSaleFragment;
import webit.bthereapp.Screens.Customer.MyQweues;
import webit.bthereapp.Screens.Customer.SyncContacts;
import webit.bthereapp.Screens.Customer.WaitingListFragment;
import webit.bthereapp.Screens.Customer.NewAppointmentFromCustomer;
import webit.bthereapp.Screens.Customer.NewEventDialogFragmen;
import webit.bthereapp.Utils.CalendarUtil;
import webit.bthereapp.Utils.Utils;

/**
 * Created by User on 14/02/2016.
 */
public class TriangleButton extends Button {
    private Context mContext;
    Dialog dialog;
    private FragmentManager fm;
    private Integer[] menuTriangleItems_color = {0, 1, 0, 1, 0, 1, 0, 1, 0};
    private String[] menuTriangleItems_text = getResources().getStringArray(R.array.trialgle_strings_menu);
    final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.supplier_galaxy_icons_x1_new);

    public TriangleButton(Context context, Context mContext) {
        super(context);
        this.mContext = mContext;
    }

    public TriangleButton(Context context, AttributeSet attrs, int defStyleAttr, Context mContext) {
        super(context, attrs, defStyleAttr);
        this.mContext = mContext;
    }

    public TriangleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int eventPadTouch = event.getAction();
        switch (eventPadTouch) {
            case MotionEvent.ACTION_DOWN:
                AlertDialog.Builder builder;
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.menu_fill, (ViewGroup) findViewById(R.id.layout_root));
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                final GridView gridview = (GridView) layout.findViewById(R.id.tringle_btn_menu_gv);
                final LinearLayout mContainerAll = (LinearLayout) layout.findViewById(R.id.layout_root);
                gridview.setAdapter(new TextAdapter(mContext));
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                        switch (position) {
                            case 0:
                                //dialog of new qweue
                                fm = ((NavigetionLayout) mContext).getSupportFragmentManager();
                                NewAppointmentFromCustomer.removeInstance();
                                NewAppointmentFromCustomer.getInstance().show(fm, "NewAppointmentFromCustomer");
                                dialog.dismiss();
                                break;
                            case 1: {
                                //dialog of new event
                                fm = ((NavigetionLayout) mContext).getSupportFragmentManager();
                                NewEventDialogFragmen fagmentt = new NewEventDialogFragmen();
                                fagmentt.show(fm, "dialog");
                                dialog.dismiss();
                                break;
                            }
                            case 2: {
                                //dialog of update qweue
                                GetCustomerOrders(1);
                                break;
                            }
                            case 3:
                                //dialog of get waiting list
                                Realm realm = Utils.getRealmInstance(mContext);
                                UserRealm userRealm = realm.where(UserRealm.class).findFirst();
                                getWaitingListForCustomer((int) userRealm.getUserID());
                                break;
                            case 4:
                                //dialog of dalay to the provider
                                GetCustomerOrders(2);
                                break;
                            case 5:
                                //dialog of cancel qweue-not coming
                                GetCustomerOrders(3);
                                break;
                            case 6:
                                //dialog of minute 90
                                fm = ((NavigetionLayout) mContext).getSupportFragmentManager();
                                LastMinuteSaleFragment.removeInstance();
                                GetCouponListForCustomer();
                                break;
                            case 7:
                                //dialog of get help
                                fm = ((NavigetionLayout) mContext).getSupportFragmentManager();
                                SyncContacts.HelpDialogClientFragment newFragment_ = new SyncContacts.HelpDialogClientFragment();
                                newFragment_.show(fm, "dialog");
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                builder = new AlertDialog.Builder(mContext, R.style.NewDialog);
                builder.setView(layout);
                dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.BOTTOM);

                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((NavigetionLayout) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//                int height = displaymetrics.heightPixels;
//                lp.height = (int) (height * 0.6f);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = getNavigationBarSizeInt(mContext) + displaymetrics.widthPixels;

                //open and close the dialog with animation
                lp.windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setAttributes(lp);
                mContainerAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
        }
        return false;
    }

    private ArrayList<OrderDetailsObj> GetCustomerOrders(final int i) {
        ArrayList<OrderDetailsObj> OrderDetailsObj_ = new ArrayList<>();
        Realm realm = Utils.getRealmInstance(getContext());
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
        MainBL.GetCustomerOrders(getContext(), jsonObject, new IExecutable<ArrayList<OrderDetailsObj>>() {
            @Override
            public void onExecute(ArrayList<OrderDetailsObj> OrderDetailsObj_) {

                if (OrderDetailsObj_ != null) {
                    if (OrderDetailsObj_.size() > 0) {
                        fm = ((NavigetionLayout) mContext).getSupportFragmentManager();
                        MyQweues.removeInstance();
                        MyQweues.getInstance().set_list_and_a(i, OrderDetailsObj_);
                        MyQweues.getInstance().show(fm, "dialogMyQweues");
                        dialog.dismiss();
                        CalendarUtil.OrderDetailsObj_ = OrderDetailsObj_;
                    } else {
                        Toast.makeText(mContext, getResources().getString(R.string.no_order), Toast.LENGTH_LONG).show();
                        CalendarUtil.OrderDetailsObj_ = new ArrayList<OrderDetailsObj>();
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.no_order), Toast.LENGTH_LONG).show();
                    CalendarUtil.OrderDetailsObj_ = new ArrayList<OrderDetailsObj>();
                    dialog.dismiss();
                }
            }
        });
        return OrderDetailsObj_;
    }

    private void GetCouponListForCustomer() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        String jsonString = "{\"iUserId\":" + userRealm.getUserID() + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetCouponListForCustomer(getContext(), jsonObject, new IExecutable<ArrayList<CouponObj>>() {
                    @Override
                    public void onExecute(ArrayList<CouponObj> couponObj) {
                        if (couponObj != null) {
                            LastMinuteSaleFragment.getInstance().setCouponList(couponObj);
                            LastMinuteSaleFragment.getInstance().show(fm, "dialogLastMinuteSaleFragment");
                            dialog.dismiss();
                        } else {
                            CustomerAlertsSettingsObj.setInstance(null);
                            Toast.makeText(getContext(), getResources().getString(R.string.no_90_sales), Toast.LENGTH_LONG).show();

                        }
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {

                    }
                }
        );
    }

    public class TextAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;

        public TextAdapter(Context c) {
            mInflater = LayoutInflater.from(c);
            mContext = c;
        }

        public int getCount() {
            return menuTriangleItems_text.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                // if it's not recycled,
                convertView = mInflater.inflate(R.layout.menu_fill_item, null);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.text_triangle_menu_item);
                holder.r_back_color = (RelativeLayout) convertView.findViewById(R.id.rl_triangle_menu_item);
                holder.mItemRl = (RelativeLayout) convertView.findViewById(R.id.r_item_txt);

                Resources r = Resources.getSystem();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
                convertView.setLayoutParams(new GridView.LayoutParams((int) px, (int) px));

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(menuTriangleItems_text[position]);
            if (position == 8) {
                holder.title.setBackgroundResource(R.drawable.exclamation_mark);
                holder.mItemRl.setPadding(48, 50, 48, 50);

            }
            if (menuTriangleItems_color[position] == 1)
                holder.r_back_color.setBackgroundColor(getResources().getColor(R.color.color4));
            else
                holder.r_back_color.setBackgroundColor(getResources().getColor(R.color.color3));
            return convertView;
        }

    }


    class ViewHolder {
        TextView title;
        RelativeLayout r_back_color;
        RelativeLayout mItemRl;
    }

    private void getWaitingListForCustomer(final int user_id) {

        String jsonString = "{\"iUserId\":" + user_id + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getWaitingListForCustomer(getContext(), jsonObject, new IExecutable<ArrayList<WaitingListObj>>() {
            @Override
            public void onExecute(ArrayList<WaitingListObj> waitingListObjs) {
                if (waitingListObjs != null && waitingListObjs.size() > 0) {
                    fm = ((NavigetionLayout) mContext).getSupportFragmentManager();
                    WaitingListFragment.removeInstance();
                    WaitingListFragment.getInstance().set_list(waitingListObjs);
                    if (waitingListObjs.size() > 0)
                        WaitingListFragment.getInstance().show(fm, "dialogWaitingList");
                    else
                        Toast.makeText(mContext, getResources().getString(R.string.no_waiting_list), Toast.LENGTH_LONG).show();

                    dialog.dismiss();
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.no_waiting_list), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
    }

    public int getNavigationBarSizeInt(Context context) {
        int b = 0;
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);

        // navigation bar at the bottom
        if (appUsableSize.y < realScreenSize.y) {
            b = realScreenSize.y - appUsableSize.y;
        }
        // navigation bar is not present
        return b;
    }

    public static Point getAppUsableScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else if (Build.VERSION.SDK_INT >= 14) {

            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }

        return size;
    }
}
