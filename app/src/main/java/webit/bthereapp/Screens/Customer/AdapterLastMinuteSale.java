package webit.bthereapp.Screens.Customer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Entities.CouponObj;
import webit.bthereapp.R;

/**
 * Created by user on 22/03/2016.
 */
public class AdapterLastMinuteSale extends BaseAdapter {
    Context mContext;
    private ArrayList<CouponObj> couponList;

    public AdapterLastMinuteSale(Context context, ArrayList<CouponObj> couponList_) {
        mContext = context;
        couponList = couponList_;
    }

    @Override
    public int getCount() {
        return couponList.size();
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
        convertView = View.inflate(mContext, R.layout.row_sales, null);
        TextView mDateTv = (TextView) convertView.findViewById(R.id.date);
        TextView mTypeTv = (TextView) convertView.findViewById(R.id.type);
        Date date = ConnectionUtils.JsonDateToDate(couponList.get(position).getdDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
        mDateTv.setText(format1.format(calendar.getTime()));
        mTypeTv.setText(couponList.get(position).getiSupplierServiceId() + "");
        return convertView;
    }
}
