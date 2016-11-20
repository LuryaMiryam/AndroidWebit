package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.app.AliasActivity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.games.internal.GamesClientImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.CustomerObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.ContactListFragment;

/**
 * Created by User on 01/05/2016.
 */
public class CusInMyCustomersAdapter extends ArrayAdapter<CustomerObj> implements View.OnClickListener {
    private Context mcontext;


    ArrayList<CustomerObj> my_customers_list = new ArrayList<>();
    private TextView mCustomerNameTv;
    private int position;
    private LinearLayout mButtonsL, custom;
    int width, height, buttonsWidth;
    RelativeLayout mClientRow, mAllR, mEaseR, mCallR, mAppointmentsR, mDetailsR;


    public CusInMyCustomersAdapter(Context context, int resource, ArrayList<CustomerObj> ls) {
        super(context, resource, ls);
        my_customers_list = ls;
        mcontext = context;

        WindowManager wm = (WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.customer_in_list, parent, false);

        this.position = position;
        mClientRow = (RelativeLayout) convertView.findViewById(R.id.client_row);

        mAllR = (RelativeLayout) convertView.findViewById(R.id.r_buttons);
        mButtonsL = (LinearLayout) convertView.findViewById(R.id.buttons);
        mCustomerNameTv = (TextView) convertView.findViewById(R.id.name);
        mEaseR = (RelativeLayout) convertView.findViewById(R.id.delete);
        mCallR = (RelativeLayout) convertView.findViewById(R.id.call);
        mAppointmentsR = (RelativeLayout) convertView.findViewById(R.id.appointments);
        mDetailsR = (RelativeLayout) convertView.findViewById(R.id.details);
        mEaseR.setOnClickListener(this);
        mCallR.setOnClickListener(this);
        mAppointmentsR.setOnClickListener(this);
        mDetailsR.setOnClickListener(this);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mClientRow.getLayoutParams();
        params.width = width;
        mClientRow.setLayoutParams(params);
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) mButtonsL.getLayoutParams();
        buttonsWidth = params1.width;
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) mAllR.getLayoutParams();
        params2.width = width + buttonsWidth;
        mAllR.setLayoutParams(params2);

        mCustomerNameTv.setText(my_customers_list.get(position).getUserObj().getNvFirstName().toString() + " " + my_customers_list.get(position).getUserObj().getNvLastName().toString());

        return convertView;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm;
        switch (v.getId()) {
            case R.id.call:

                break;

            case R.id.delete:
                if (mcontext instanceof ExistsSuplierActivity)
                    fm = ((ExistsSuplierActivity) mcontext).getSupportFragmentManager();
                else
                    fm = ((ExistsSuplierDefinitionsActivity) mcontext).getSupportFragmentManager();
                DeleteCustomerDialogFragment newFragment__ = new DeleteCustomerDialogFragment();
                newFragment__.show(fm, "dialog");
                break;

            case R.id.details:
                if (mcontext instanceof ExistsSuplierActivity)
                    fm = ((ExistsSuplierActivity) mcontext).getSupportFragmentManager();
                else
                    fm = ((ExistsSuplierDefinitionsActivity) mcontext).getSupportFragmentManager();

                CustomDetailsDialogFragment newFragment = new CustomDetailsDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("customer", (Serializable) my_customers_list.get(position));
                newFragment.setArguments(bundle);

                newFragment.show(fm, "dialog");
                break;

            case R.id.appointments:
                if (mcontext instanceof ExistsSuplierActivity)
                    fm = ((ExistsSuplierActivity) mcontext).getSupportFragmentManager();
                else
                    fm = ((ExistsSuplierDefinitionsActivity) mcontext).getSupportFragmentManager();
                QweuesToCustomerDialogFragment newFragment_ = new QweuesToCustomerDialogFragment();
                newFragment_.show(fm, "dialog");
                break;

        }
    }
}
