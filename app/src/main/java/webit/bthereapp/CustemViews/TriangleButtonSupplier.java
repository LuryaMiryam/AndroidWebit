package webit.bthereapp.CustemViews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.style.TtsSpan;
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

import java.lang.reflect.InvocationTargetException;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Supplier.AboutTheSystemDialogFragment;
import webit.bthereapp.Screens.Supplier.BlockHoursFragment;
import webit.bthereapp.Screens.Supplier.CustomersInParallel;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Screens.Supplier.ExistsSuplierDefinitionsActivity;
import webit.bthereapp.Screens.Supplier.HelpDialogFragment;
import webit.bthereapp.Screens.Supplier.MessagesDialogFragment;
import webit.bthereapp.Screens.Supplier.MyWaitingListFragment;
import webit.bthereapp.Screens.Supplier.NewAppointmentDialog;
import webit.bthereapp.Screens.Supplier.The90MinuteDialogFragment;
import webit.bthereapp.Screens.Supplier.UpdateAppointment;


/**
 * Created by User on 14/02/2016.
 */
public class TriangleButtonSupplier extends Button {

    public final int CATEGORY_ID = 0;
    private static Context mContext;
    Dialog dialog;
    private RelativeLayout mItemRl;


    public TriangleButtonSupplier(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

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
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.title.setText(menuTriangleItems_text[position]);
            //of about image
            if (position == 8) {
                holder.title.setBackgroundResource(R.drawable.exclamation_mark);
                holder.mItemRl.setPadding(48, 50, 48, 50);
            }
            if (menuTriangleItems_color[position] == 1)
                holder.r_back_color.setBackgroundColor(getResources().getColor(R.color.color4));
            else
                holder.r_back_color.setBackgroundColor(getResources().getColor(R.color.color3));
            Resources r = Resources.getSystem();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
            convertView.setLayoutParams(new GridView.LayoutParams((int) px, (int) px));

            return convertView;
        }

    }


    class ViewHolder {
        TextView title;
        RelativeLayout r_back_color;
        RelativeLayout mItemRl;
    }

    private Integer[] menuTriangleItems_color = {0, 1, 0, 1, 0, 1, 0, 1, 0};
    private String[] menuTriangleItems_text = getResources().getStringArray(R.array.trialgle_strings_menu_supplier);


    public boolean onTouchEvent(MotionEvent event) {
        int eventPadTouch = event.getAction();
        switch (eventPadTouch) {
            case MotionEvent.ACTION_DOWN:
                //dialog_menu
                AlertDialog.Builder builder;
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.menu_fill, (ViewGroup) findViewById(R.id.layout_root));
                final GridView gridview = (GridView) layout.findViewById(R.id.tringle_btn_menu_gv);
                final LinearLayout mContainerAll = (LinearLayout) layout.findViewById(R.id.layout_root);
                gridview.setAdapter(new TextAdapter(mContext));
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        FragmentManager fm;
                        if (mContext instanceof ExistsSuplierDefinitionsActivity)
                            fm = ((ExistsSuplierDefinitionsActivity) mContext).getSupportFragmentManager();
                        else
                            fm = ((ExistsSuplierActivity) mContext).getSupportFragmentManager();
                        switch (position) {
                            case 0:
                                //dialog of new qweue
                                NewAppointmentDialog newFragment = new NewAppointmentDialog();
                                newFragment.show(fm, "dialog");
                                dialog.dismiss();
                                break;
                            case 1:
                                BlockHoursFragment blockHoursFragment = new BlockHoursFragment();
                                blockHoursFragment.show(fm, "dialog");
                                dialog.dismiss();
                                break;
                            case 2:
                                The90MinuteDialogFragment newFragment_ = new The90MinuteDialogFragment();
                                newFragment_.show(fm, "dialog");
                                dialog.dismiss();
                                break;
                            case 3:
                                CustomersInParallel customersInParallel = new CustomersInParallel();
                                customersInParallel.show(fm, "dialog");
                                dialog.dismiss();
                                break;
                            case 4:
                                MyWaitingListFragment myWaitingListFragment = new MyWaitingListFragment();
                                myWaitingListFragment.show(fm, "dialog");
                                dialog.dismiss();
                                break;
                            case 5:
                                UpdateAppointment updateAppointment = new UpdateAppointment();
                                updateAppointment.show(fm, "dialog");
                                dialog.dismiss();
                                break;
                            case 6:
                                MessagesDialogFragment new_ = new MessagesDialogFragment();
                                new_.show(fm, "dialog");
                                dialog.dismiss();
                                break;
                            case 7:
                                HelpDialogFragment new__ = new HelpDialogFragment();
                                new__.show(fm, "dialog");
                                dialog.dismiss();
                                break;
                            case 8:
                                if (mContext instanceof ExistsSuplierDefinitionsActivity)
                                    fm = ((ExistsSuplierDefinitionsActivity) mContext).getSupportFragmentManager();
                                else
                                    fm = ((ExistsSuplierActivity) mContext).getSupportFragmentManager();
                                AboutTheSystemDialogFragment aboutTheSystemDialogFragment = new AboutTheSystemDialogFragment();
                                aboutTheSystemDialogFragment.show(fm, "dialog");
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
                if (mContext instanceof ExistsSuplierActivity)
                    ((ExistsSuplierActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                else if (mContext instanceof ExistsSuplierDefinitionsActivity)
                    ((ExistsSuplierDefinitionsActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int height = displaymetrics.heightPixels;

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                lp.height = (int) (height * 0.6f);

                lp.height = getNavigationBarSizeInt(mContext) + displaymetrics.widthPixels;
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
