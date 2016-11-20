package webit.bthereapp.Screens.Customer;

/**
 * Created by user on 22/03/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.nfc.cardemulation.HostApduService;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.ProviderServicesObj;
import webit.bthereapp.Entities.WaitingListObj;
import webit.bthereapp.Entities.orderObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;

public class AdapterWaitingList extends BaseAdapter {
    private ArrayList<WaitingListObj> waitingListObjs;
    Context mContext;
    private Calendar calendar = Calendar.getInstance();


    public AdapterWaitingList(Context context, ArrayList<WaitingListObj> waitingListObjs_, int width_) {
        waitingListObjs = waitingListObjs_;
        mContext = context;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
    }


    @Override
    public int getCount() {
        if (waitingListObjs != null)
            return waitingListObjs.size();
        return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        convertView = View.inflate(mContext, R.layout.row_waiting_list, null);
        holder = new ViewHolder();
        holder.pos=position;
        holder.mDateTv = (TextView) convertView.findViewById(R.id.date);
        holder.mTimeTv = (TextView) convertView.findViewById(R.id.time);
        holder.mTypeTv = (TextView) convertView.findViewById(R.id.type);
        holder.r_buttons = (LinearLayout) convertView.findViewById(R.id.r_buttons);
        holder.row_all = (LinearLayout) convertView.findViewById(R.id.row_r);
        holder.inform_row = (LinearLayout) convertView.findViewById(R.id.service_row);
        holder.scroll = (HorizontalScrollView) convertView.findViewById(R.id.scroll);
        holder.imageView = (ImageView) convertView.findViewById(R.id.image);

        holder.row_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteFromWaitingList(holder.pos);
            }
        });
        //set the logo image of the bussines
        if (waitingListObjs.get(position).getNvLogo() != null && (!(waitingListObjs.get(position).getNvLogo().equals("")))) {
            byte[] decodedString = Base64.decode(waitingListObjs.get(position).getNvLogo().toString(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imageView.setImageBitmap(decodedByte);
        }
        calendar.setTime(ConnectionUtils.JsonDateToDate(waitingListObjs.get(position).getDtDateOrder()));
        SimpleDateFormat mFormatter_2 = new SimpleDateFormat("HH:mm");
        holder.mTimeTv.setText(mFormatter_2.format(calendar.getTime()));

        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
        holder.mDateTv.setText(format1.format(calendar.getTime()));

        String service = "";
        for (ProviderServicesObj providerServicesObj : waitingListObjs.get(position).getProviderServiceObj())
            if (!(service.equals("")))
                service += (", " + providerServicesObj.getNvServiceName());
            else
                service += providerServicesObj.getNvServiceName();
        holder.mTypeTv.setText(service);
        holder.scroll.smoothScrollBy(500, 0);

        return convertView;
    }

    class ViewHolder {
        int pos;
        TextView mDateTv, mTimeTv, mTypeTv;
        LinearLayout r_buttons;
        LinearLayout row_all, inform_row;
        HorizontalScrollView scroll;
        ImageView imageView;
    }

    private void DeleteFromWaitingList(final int a) {

        String jsonString = "{\"iWaitingForServiceId\":" + waitingListObjs.get(a).getiWaitingForServiceId() + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.DeleteFromWaitingList(mContext, jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double num) {
                if (num == 1) {
                    waitingListObjs.remove(a);
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.erase_from_w), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();

                    //dismiss the dialog
                    if (waitingListObjs.size() == 0)
                        WaitingListFragment.getInstance().dismiss();

                    Fragment prev3 =((NavigetionLayout)mContext).getSupportFragmentManager().findFragmentByTag("dialogMyQweues");
                    if (prev3 != null) {
                        DialogFragment df = (DialogFragment) prev3;
                        df.dismiss();
                    }

                } else if (num == -1 || num == -2)
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.not_erase_from_w), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
