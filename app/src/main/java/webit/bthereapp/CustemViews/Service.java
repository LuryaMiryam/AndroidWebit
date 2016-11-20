package webit.bthereapp.CustemViews;

import android.content.Context;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import webit.bthereapp.CustemViews.Fonts.CustomLightEditText;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.ProviderServiceRealm;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Intreface.AddOnClickListener;
import webit.bthereapp.R;

public class Service extends LinearLayout implements View.OnFocusChangeListener {

    Context mContext;
    private YesOrNo_Black if_to_see;
    private boolean p_or_s_id = false;
    CustomSpinnerWhite p_or_s;
    private LinearLayout s_time_spinner_ll, s_num, s_space_time_spinner_ll;
    private boolean validate = true, flag = true;
    CustomLightEditText mPriceET, mDiscountEt, mServiceNameET, mDurationTimeET, mMaxCustomersET, mTimeIntervalET;
    private CustomLightTextView less, mName;
    public boolean edit = false;
    public NewService ns;
    private String mDurationTime_1,mPrice1, mPrice2 = "";
    int discount = 0;

    public Service(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    //put the previous service
    public void pus_services(ProviderServiceRealm providerServices) {
        mServiceNameET.setText(providerServices.getNvServiceName());
        if (providerServices.getnUntilPrice() != 0)
            mPriceET.setText(String.valueOf(providerServices.getiPrice()) + "-" + String.valueOf(providerServices.getnUntilPrice()));
        else
            mPriceET.setText(String.valueOf(providerServices.getiPrice()));
        if (providerServices.getiUntilSeviceTime() != 0)
            mDurationTimeET.setText(String.valueOf(providerServices.getiTimeOfService()) + "-" + String.valueOf(providerServices.getiUntilSeviceTime()));
        else
            mDurationTimeET.setText(String.valueOf(providerServices.getiTimeOfService()));
        mMaxCustomersET.setText(String.valueOf(providerServices.getiMaxConcurrentCustomers()));
        mTimeIntervalET.setText(String.valueOf(providerServices.getiTimeInterval()));
        mDiscountEt.setText(String.valueOf(providerServices.getiDiscount()));

        if (providerServices.isbDisplayPerCustomer())
            if_to_see.change_status(true);
        else
            if_to_see.change_status(false);

        if (providerServices.getiTimeOfService() == 0 && providerServices.getiTimeInterval() == 0 && providerServices.getiMaxConcurrentCustomers() == 0) {
            p_or_s.put_position(1);
            less.setText(getResources().getString(R.string.less_2));
            s_time_spinner_ll.setVisibility(View.GONE);
            s_space_time_spinner_ll.setVisibility(GONE);
            s_num.setVisibility(GONE);
            mName.setText(getResources().getString(R.string.name_product));
            p_or_s_id = true;
        }

    }

    public Service(Context context, AttributeSet attrs, NewService newService) {
        super(context, attrs);
        initS(context, attrs, newService);
    }

    public void initS(final Context context, AttributeSet attrs, NewService newService) {
        ns = newService;
        init(context, attrs);
    }

    public void init(final Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.custem_service, this);
        this.mContext = context;


        mPriceET = (CustomLightEditText) view.findViewById(R.id.s_service_price);
        mName = (CustomLightTextView) view.findViewById(R.id.s_service_spinner_txt);
        mDiscountEt = (CustomLightEditText) view.findViewById(R.id.s_service_less);
        mServiceNameET = (CustomLightEditText) view.findViewById(R.id.s_service_desc);
        mDurationTimeET = (CustomLightEditText) view.findViewById(R.id.s_service_time_);
        mMaxCustomersET = (CustomLightEditText) view.findViewById(R.id.s_service_num);
        mTimeIntervalET = (CustomLightEditText) view.findViewById(R.id.s_service_space_time_);

        less = (CustomLightTextView) view.findViewById(R.id.less);
        s_time_spinner_ll = (LinearLayout) view.findViewById(R.id.s_time_spinner_ll);
        s_num = (LinearLayout) view.findViewById(R.id.s_num);
        s_space_time_spinner_ll = (LinearLayout) view.findViewById(R.id.s_space_time_spinner_ll);

        mServiceNameET.setFilters(setInputFilter(30, getResources().getString(R.string.too_long_service_name)));
        mServiceNameET.setOnFocusChangeListener(this);
        mPriceET.setFilters(setInputFilter(13, getResources().getString(R.string.too_long_price)));
        mPriceET.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkPrice(mPriceET);
            }
        });
        mDurationTimeET.setFilters(setInputFilter(3, getResources().getString(R.string.too_long_time)));
        mDurationTimeET.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setDuarationTime(mDurationTimeET);
            }
        });
        mMaxCustomersET.setFilters(setInputFilter(3, getResources().getString(R.string.too_long_time)));
        mMaxCustomersET.setOnFocusChangeListener(this);
        mTimeIntervalET.setFilters(setInputFilter(2, getResources().getString(R.string.too_long_space)));
        mDiscountEt.setFilters(setInputFilter(2, getResources().getString(R.string.too_long_space)));

        if_to_see = (YesOrNo_Black) view.findViewById(R.id.if_to_see);
        if_to_see.change_status(true);


        p_or_s = (CustomSpinnerWhite) view.findViewById(R.id.product_or_service);
        p_or_s.setAddOnClickListener(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                if (p_or_s.get_position() == 0) {
                    less.setText(getResources().getString(R.string.less_1));
                    s_time_spinner_ll.setVisibility(View.VISIBLE);
                    s_space_time_spinner_ll.setVisibility(VISIBLE);
                    s_num.setVisibility(VISIBLE);
                    mName.setText(getResources().getString(R.string.name_service));
                    p_or_s_id = false;
                } else if (p_or_s.get_position() == 1) {
                    less.setText(getResources().getString(R.string.less_2));
                    s_time_spinner_ll.setVisibility(View.GONE);
                    s_space_time_spinner_ll.setVisibility(GONE);
                    s_num.setVisibility(GONE);
                    mName.setText(getResources().getString(R.string.name_product));
                    p_or_s_id = true;
                }


            }
        });


    }

    private InputFilter[] setInputFilter(int max, final String message) {
        InputFilter[] inputFilter = new InputFilter[]{
                new InputFilter.LengthFilter(max) {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        CharSequence res = super.filter(source, start, end, dest, dstart, dend);
                        if (res != null) { // Overflow
                            if ((message.equals(getResources().getString(R.string.too_long_price)) && !mPriceET.getText().toString().contains("-")) || (!message.equals(getResources().getString(R.string.too_long_price)))) {
                                final Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
                                toast.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        toast.cancel();
                                    }
                                }, 600);
                            }
                        }
                        return res;
                    }
                }
        };
        return inputFilter;
    }

    public void save_previous(String NvServiceName, float iPrice, float iUntilPrice, int iTimeOfService, int iUntilTimeOfService, int iMaxConcurrentCustomers,
                              int iTimeInterval, int iDiscount) {
        mServiceNameET.setText(NvServiceName);
        if (iUntilPrice != 0)
            mPriceET.setText(String.valueOf(iPrice) + "-" + String.valueOf(iUntilPrice));
        else
            mPriceET.setText(String.valueOf(iPrice));
        if (iUntilTimeOfService != 0)
            mDurationTimeET.setText(String.valueOf(iUntilTimeOfService) + "-" + String.valueOf(iUntilTimeOfService));
        else
            mDurationTimeET.setText(String.valueOf(iTimeOfService));
        mMaxCustomersET.setText(String.valueOf(iMaxConcurrentCustomers));
        mTimeIntervalET.setText(String.valueOf(iTimeInterval));
        mDiscountEt.setText(String.valueOf(iDiscount));


    }

    private boolean checkRequiredField(EditText editText) {
        if (editText.getText().toString().length() == 0) {
            editText.setError(getResources().getString(R.string.error_empty));
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }


    private void setDuarationTime(EditText editText) {
        mDurationTime_1 = editText.getText().toString();
    }

//check the price if validity
    private boolean checkPrice(EditText editText) {
        int a = 0, b = 0, c = 0;
        String s = editText.getText().toString();
        if (s.length() > 0) {
            editText.setError(null);
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '-')
                    c++;
                else if ((s.charAt(i) >= '0' && s.charAt(i) <= '9') || s.charAt(i) == '.') {
                    if (c != 0)
                        b++;
                    else a++;
                } else {
                    editText.setError(getResources().getString(R.string.error_number));
                    return false;

                }

            }
            if ((c == 1 && (a == 0 || b == 0)) || c > 1) {
                editText.setError(getResources().getString(R.string.error_number));
                return false;
            }
            if (a != 0) {

                int endIndex = s.lastIndexOf("-");
                if (endIndex != -1)
                    mPrice1 = s.substring(0, endIndex);
                else
                    mPrice1 = s;
                if ((b == 0 && c == 0) || (b != 0 && c != 0)) {
                    if (b != 0)
                        mPrice2 = s.substring(s.lastIndexOf("-") + 1);
                }

                int z = mPrice1.length();
                if (mPrice1.charAt(z - 1) == '.') {
                    editText.setError(getResources().getString(R.string.error_number));
                    return false;
                }
                z = mPrice2.length();
                if (z != 0)
                    if (mPrice2.charAt(z - 1) == '.') {
                        editText.setError(getResources().getString(R.string.error_number));
                        return false;
                    }

                int counter = 0;
                for (int i = 0; i < mPrice1.length(); i++) {
                    if (mPrice1.charAt(i) == '.') {
                        counter++;
                    }
                }
                if (counter > 1) {
                    editText.setError(getResources().getString(R.string.error_number));
                    return false;
                }
                int counter2 = 0;
                for (int i = 0; i < mPrice2.length(); i++) {
                    if (mPrice2.charAt(i) == '.') {
                        counter2++;
                    }
                }
                if (counter2 > 1) {
                    editText.setError(getResources().getString(R.string.error_number));
                    return false;
                }
                if ((a - counter) > 6 || (b - counter2) > 6) {
                    editText.setError(getResources().getString(R.string.error_number6));
                    return false;
                }
                if (mPrice2 != "")
                    if (Double.parseDouble(mPrice1.toString()) > Double.parseDouble(mPrice2.toString())) {
                        editText.setError(getResources().getString(R.string.error_number));
                        return false;
                    }

                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }

    }
//check oll the fields if validity before save
    public boolean checkAllFields() {
        validate = true;
        flag = true;
        if (!checkRequiredField(mServiceNameET)) {
            validate = true;
            flag = false;
        } else if ((!checkLengthWithoutSpaces(mServiceNameET))) {
            flag = true;
            validate = false;
            mServiceNameET.setError(getResources().getString(R.string.service_name_error));
        } else
            validate = true;
        if (!checkRequiredField(mPriceET) || !checkPrice(mPriceET))
            flag = false;

        if ((!checkRequiredField(mDurationTimeET)) && (!p_or_s_id))
            flag = false;
        if ((!checkRequiredField(mMaxCustomersET)) && (!p_or_s_id))
            flag = false;
        if (!p_or_s_id)
            if (mMaxCustomersET.getText().toString().equals("0")) {
                mMaxCustomersET.setError(getResources().getString(R.string.error_number));
                flag = false;
            } else {
                mMaxCustomersET.setError(null);
            }
        if (!validate || !flag)
            return false;
        else
            return true;

    }

    public ProviderServices getProviderService() {
        //// TODO: 04/04/2016 required validations
        ProviderServices providerService = new ProviderServices();
        providerService.setNvServiceName(mServiceNameET.getText().toString());
        if (if_to_see.getYes_no_state() == 1)
            providerService.setbDisplayPerCustomer(true);
        else
            providerService.setbDisplayPerCustomer(false);
        checkPrice(mPriceET);


        float price1, price2;
        price1 = Float.parseFloat(mPrice1);
        providerService.setiPrice(price1);
        if (mPrice2 != "") {
            price2 = Float.parseFloat(mPrice2);
            providerService.setnUntilPrice(price2);
        } else
            providerService.setnUntilPrice(0);

        int durationTime, durationTime1, durationTime2;
        if (!p_or_s_id) {
            setDuarationTime(mDurationTimeET);
            if (!(mDurationTime_1.equals(""))) {
                durationTime1 = Integer.parseInt(mDurationTime_1);
                providerService.setiTimeOfService(durationTime1);
                providerService.setiUntilSeviceTime(0);
            }
        } else {
            providerService.setiTimeOfService(0);
            providerService.setiUntilSeviceTime(0);
        }
        if (!p_or_s_id)
            providerService.setiMaxConcurrentCustomers(Integer.valueOf(mMaxCustomersET.getText().toString()));
        providerService.setiMinConcurrentCustomers(1);
        if ((!p_or_s_id) && (!mTimeIntervalET.getText().toString().equals(""))) {
            //ser
            durationTime = Integer.parseInt(mTimeIntervalET.getText().toString().replaceAll("[\\D]", ""));
            providerService.setiServiceType(90);
        } else {
            //pro
            durationTime = 0;
            providerService.setiServiceType(89);
        }
        providerService.setiTimeInterval(durationTime);
        if (!(mDiscountEt.getText().toString().equals(""))) {
            discount = Integer.parseInt(mDiscountEt.getText().toString().replaceAll("[\\D]", ""));
            providerService.setiDiscount(discount);
        } else
            providerService.setiDiscount(0);

        return providerService;
    }

    public String getName() {
        return mServiceNameET.getText().toString();
    }

    public CustomLightEditText getmDiscountEt() {
        return mDiscountEt;
    }
    private Boolean checkLengthWithoutSpaces(CustomLightEditText s) {
        int count = 0;
        for (int i = 0; i < s.getText().toString().length(); i++) {
            if (String.valueOf(s.getText().toString().charAt(i)).equals(" ")) {
                count++;
            }
        }
        if ((s.getText().toString().length() - count) < 2)
            return false;
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        TextView textView = (TextView) v;
        if (!hasFocus) {
            switch (v.getId()) {
                case R.id.s_service_desc:
                    if (textView.getText().length() > 0) {
                        if ((!checkLengthWithoutSpaces(mServiceNameET))) {
                            validate = false;
                            mServiceNameET.setError(getResources().getString(R.string.service_name_error));
                        } else {
                            validate = true;
                            mServiceNameET.setError(null);
                        }
                    }
                    break;
                case R.id.s_service_num:
                    if (!p_or_s_id)
                        if (mMaxCustomersET.getText().toString().equals("0")) {
                            mMaxCustomersET.setError(getResources().getString(R.string.error_number));
                            validate = false;
                        } else {
                            mMaxCustomersET.setError(null);
                            validate = true;
                        }

                    break;
            }
        }
    }

    public boolean isEmpty() {
        return flag;
    }

}
