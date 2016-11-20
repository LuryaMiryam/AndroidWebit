package webit.bthereapp.CustemViews;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.util.List;

import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.ProviderGeneralDetailsObj;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.ServiceProviders;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Supplier.ExistsSuplierDefinitionsActivity;

/**
 * Created by user on 09/03/2016.
 */
public class NewService extends LinearLayout implements View.OnClickListener {
    Context mContext;
    String mName;
    TextView mNameTv;
    private View view, mSepeartView;
    public boolean clicked = true;
    public LinearLayout mDetailsServiceR;
    public Service service, saveService;
    List<ProviderServices> listServices;
    public ProviderServices mFoundService;
    private RelativeLayout pencil, delete;
    private DialogInterface.OnClickListener dialogClickListener;
    LinearLayout scroll;

    public NewService(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, null, null);
    }

    public NewService(Context context, AttributeSet attrs, String name, Service service) {
        super(context, attrs);
        this.mContext = context;
        init(context, attrs, name, service);
    }

    public void init(final Context context, AttributeSet attrs, String name, Service service) {
        view = View.inflate(context, R.layout.add_service, this);
        this.mContext = context;
        this.mName = name;
        this.service = new Service(context, null, this);
        this.service = service;
        service.edit = true;

        mDetailsServiceR = (LinearLayout) view.findViewById(R.id.deatils_service);

        if (context instanceof MainActivity)
            scroll = ((BusinessGeneralData) (((MainActivity) mContext).getSupportFragmentManager().findFragmentByTag(BusinessGeneralData.class.toString()))).getScroll();
        else
            scroll = ((BusinessGeneralData) (((ExistsSuplierDefinitionsActivity) mContext).getSupportFragmentManager().findFragmentByTag(BusinessGeneralData.class.toString()))).getScroll();
        mSepeartView = view.findViewById(R.id.second_view);
        pencil = (RelativeLayout) view.findViewById(R.id.pencil);
        delete = (RelativeLayout) view.findViewById(R.id.delete);
        delete.setOnClickListener(this);
        pencil.setOnClickListener(this);
        mNameTv = (TextView) view.findViewById(R.id.service_name);
        if (name != null)
            mNameTv.setText(name);
        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        delete_this();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete: {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(getResources().getString(R.string.delete_q) + " " + service.getName() + "?").setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                break;
            }
            case R.id.pencil:
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                int i = 0;
                String serviceName = mNameTv.getText().toString();
                listServices = ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices();

                for (ProviderServices providerServices : listServices) {
                    if (serviceName.equals(providerServices.getNvServiceName())) {
                        mFoundService = providerServices;
                    }
                    i++;
                }
                int a = 0;
                if (clicked) {
                    if (mContext instanceof MainActivity) {
                        if (((BusinessGeneralData) (((MainActivity) mContext).getSupportFragmentManager().findFragmentByTag
                                (BusinessGeneralData.class.toString()))).SaveAndNext(6))
                            a = 1;
                    } else if (((BusinessGeneralData) (((ExistsSuplierDefinitionsActivity) mContext).getSupportFragmentManager().findFragmentByTag
                            (BusinessGeneralData.class.toString()))).SaveAndNext(6))
                        a = 1;
                    if (a == 1) {
                        saveService = service;
                        saveService.edit = true;
                        service.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        mDetailsServiceR.addView(service);
                        focusOnView(mDetailsServiceR);
                        clicked = false;
                    }
                    mSepeartView.setVisibility(VISIBLE);
                } else {
                    if (mContext instanceof MainActivity)
                        ((BusinessGeneralData) (((MainActivity) mContext).getSupportFragmentManager().
                                findFragmentByTag(BusinessGeneralData.class.toString()))).getField_arr()[6] = false;
                    else
                        ((BusinessGeneralData) (((ExistsSuplierDefinitionsActivity) mContext).getSupportFragmentManager().
                                findFragmentByTag(BusinessGeneralData.class.toString()))).getField_arr()[6] = false;
                    clicked = true;
                    mSepeartView.setVisibility(GONE);
                    save();
                }
                break;
        }
    }

    //delete selected service
    private void delete_this() {
        listServices = ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices();
        String mail = service.getName();
        for (ProviderServices providerServices : listServices) {
            if (mail.equals(providerServices.getNvServiceName())) {
                mFoundService = providerServices;
            }
        }
        if (this.mContext instanceof MainActivity)
            ((BusinessGeneralData) (((MainActivity) mContext).getSupportFragmentManager().findFragmentByTag(BusinessGeneralData.class.toString()))).delete_service(this);
        else
            ((BusinessGeneralData) (((ExistsSuplierDefinitionsActivity) mContext).getSupportFragmentManager().findFragmentByTag(BusinessGeneralData.class.toString()))).delete_service(this);
        ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices().remove(service.getProviderService());
        listServices.remove(mFoundService);
    }

    //save the sevise after check validity
    public boolean save() {
        clicked = true;
        if (service.checkAllFields()) {
            int j = 0;
            listServices.remove(mFoundService);
            ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices().add(service.getProviderService());
            mDetailsServiceR.removeView(service);
            String name = service.getName();
            mNameTv.setText(name);
            return true;
        } else {
            service.save_previous(mFoundService.getNvServiceName(), mFoundService.getiPrice(),mFoundService.getnUntilPrice(), mFoundService.getiTimeOfService(), mFoundService.getiUntilSeviceTime(), mFoundService.getiMaxConcurrentCustomers(), mFoundService.getiTimeInterval(), mFoundService.getiDiscount());
            service = saveService;
            mDetailsServiceR.removeView(service);
            return true;
        }

    }

    public void focusOnView(final View view) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if((mContext instanceof MainActivity)&&scroll!=null)
                ((ScrollView) scroll.getParent().getParent().getParent().getParent().getParent()).scrollTo(0, view.getBottom());

            }
        });
    }

    public NewService getInstance() {
        return this;
    }

    public boolean getClicked() {
        return clicked;
    }
}


