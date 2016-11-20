package webit.bthereapp.Screens.Register.BusinessRegister;


import webit.bthereapp.BL.MainBL;
import webit.bthereapp.BL.SplashBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.CustomViewTimePicker;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.UserRealm;

import webit.bthereapp.Entities.FieldAndCategory;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.ProviderGeneralDetailsObj;

import webit.bthereapp.Entities.ServiceProviders;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmResults;
import webit.bthereapp.CustemViews.Bus_G_CalendarSet;
import webit.bthereapp.CustemViews.BussinesAreaAdapterActivity;
import webit.bthereapp.CustemViews.DetailsWorkers;
import webit.bthereapp.CustemViews.Fonts.CustomLightEditText;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.CustemViews.NewService;
import webit.bthereapp.CustemViews.NewWorker;
import webit.bthereapp.CustemViews.Service;
import webit.bthereapp.CustemViews.TimeText;
import webit.bthereapp.DM.FieldAndCategoryDM;
import webit.bthereapp.Entities.HelpWorkingHours;
import webit.bthereapp.Entities.WorkingHours;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.Utils;


public class BusinessGeneralData extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CustomLightTextView hours_txt, hoursTV, hours_break_txt;
    private LinearLayout day1, day2, day3, day4, day5, day6;
    private LinearLayout day1break, day2break, day3break, day4break, day5break, day6break;
    private Bus_G_CalendarSet bus_g_calendarSet;
    private DetailsWorkers dw, newDw;
    private Service newService, ser, ser2;
    private Button mContinueBtn, mBreaksBtn;
    private TextView mWorkersTV, mServiceTV, mCalenderTv, mSubjectTV;
    private RelativeLayout mWorkersRL, mServiceRL, mCalenderR, mB_areaR;
    private ImageView mArrorWorkersIV, mArrowAddWorkerIV, mArrowServiceIV, mArrowAddServiceIV, mArrowCalenderIV, mArrowSubjectIV, mArrowHoursIV;
    private int choose_p = -1, mArea = 0, mCalendarSet = 0, mServices = 0, mHours = 0, mBreaks = 0;
    private boolean mWorkerDetailsPressed = false;
    private ImageView imageV, imageVbreak;
    private RealmResults<FieldAndCategoryDM> fieldAndCategoryDMs;
    private GridView gridView;
    private int position;
    public static boolean is_back = false, isBreak = false;
    public static int i = 0, j = 0;
    private LinearLayout mServiceListL;
    private LinearLayout mAllL, mAllServices, mAllWorkersService, ll_scroll, workHours;
    private int parentPositionDay = -1, parentPositionDayBreak = -1;
    private String[] strings;
    private List<String> myHoursList = new ArrayList<>();
    private LinearLayout mHoursL, mBreaksL;
    private RelativeLayout mAddWorkerR, mAddServiceR;
    private boolean isAllHours = false, isAllHoursBreak = false;
    private View mSeperateW, mSeperateS;
    private int flag_area = 0, flags_calendar = 1, flags_service = 0;
    private ScrollView scrollOfActivity;
    private boolean[] Days = new boolean[6], DaysBreaks = new boolean[6];
    private CustomViewTimePicker viewTimePicker, cvTimePickerbreak;
    private boolean required, validate;
    private CustomLightButton ok;
    private BussinesAreaAdapterActivity bussinesAreaAdapterActivity;
    private View view;
    private List<WorkingHours> workingHoursList = new ArrayList<>(), workingHoursListHelp = new ArrayList<>(), workingHoursHH = new ArrayList<>();
    private ArrayList<WorkingHours> workingHourses, breaksHourses;

    final boolean[] arrDays = new boolean[7];
    final boolean[] field_arr = new boolean[7];
    private LinearLayout mHoursBtn;
    private boolean flagHours;
    private boolean openWorkers = true, openService = true;

    public BusinessGeneralData() {
    }

    public static BusinessGeneralData instance;


    public static BusinessGeneralData getInstance() {
        if (instance == null)
            instance = new BusinessGeneralData();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    @Override
    public boolean OnBackPress() {
        if (getActivity() instanceof ExistsSuplierActivity) {
            ((ExistsSuplierActivity) getActivity()).visibleFragmentMain();
            ((ExistsSuplierActivity) getActivity()).hideFragmentTop();
            ((ExistsSuplierActivity) getActivity()).hideContainerMain();
        }
        return true;
    }

    public static BusinessGeneralData newInstance(String param1, String param2) {
        BusinessGeneralData fragment = new BusinessGeneralData();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() instanceof MainActivity) {
            HideFragmentBottom();
        }
        flagHours = true;
        myHoursList = new ArrayList();

        myHoursList.add("sunday");
        myHoursList.add("monday");
        myHoursList.add("tuesday");
        Arrays.fill(Days, false);
        Arrays.fill(DaysBreaks, false);
        workingHourses = new ArrayList<>();
        breaksHourses = new ArrayList<>();
        strings = getResources().getStringArray(R.array.days);
        List<String> stringList = new ArrayList<String>(Arrays.asList(strings));

        myHoursList = stringList;
        // Inflate the layout for this fragment
        Arrays.fill(field_arr, false);

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_business_general_data, container, false);

//            Definitions
            mAllL = (LinearLayout) view.findViewById(R.id.list_of_workers);
            viewTimePicker = (CustomViewTimePicker) view.findViewById(R.id.cvTimePicker);
            cvTimePickerbreak = (CustomViewTimePicker) view.findViewById(R.id.cvTimePickerbreak);
            mAllServices = (LinearLayout) view.findViewById(R.id.list_services);
            ll_scroll = (LinearLayout) view.findViewById(R.id.ll_scroll);
            imageV = (ImageView) view.findViewById(R.id.alldays);
            imageVbreak = (ImageView) view.findViewById(R.id.alldaysbreak);
            mBreaksBtn = (Button) view.findViewById(R.id.addBreaks);
            ok = (CustomLightButton) view.findViewById(R.id.ok);
            workHours = (LinearLayout) view.findViewById(R.id.workHours);
            mHoursBtn = (LinearLayout) view.findViewById(R.id.hours);
            mArrowHoursIV = (ImageView) view.findViewById(R.id.arrow_hours);
            mHoursBtn.setOnClickListener(this);
            hoursTV = (CustomLightTextView) view.findViewById(R.id.hours_);
            hours_break_txt = (CustomLightTextView) view.findViewById(R.id.hours_break_txt);
            hours_txt = (CustomLightTextView) view.findViewById(R.id.hours_txt);
            mHoursL = (LinearLayout) view.findViewById(R.id.all_hours);
            mBreaksL = (LinearLayout) view.findViewById(R.id.all_hours_break);
            mContinueBtn = (Button) view.findViewById(R.id.b_g_general_continue1);
            gridView = (GridView) view.findViewById(R.id.g_v_bussines_area);
            bus_g_calendarSet = (Bus_G_CalendarSet) view.findViewById(R.id.b_g_calendar_set_view);
            mB_areaR = (RelativeLayout) view.findViewById(R.id.b_g_area);
            mArrowSubjectIV = (ImageView) view.findViewById(R.id.arrow_subject);
            mSubjectTV = (TextView) view.findViewById(R.id.subject);
            mServiceRL = (RelativeLayout) view.findViewById(R.id.b_g_services_btn);
            mServiceTV = (TextView) view.findViewById(R.id.services);
            mArrowAddServiceIV = (ImageView) view.findViewById(R.id.arror_add_service);
            mArrowServiceIV = (ImageView) view.findViewById(R.id.arrow_service);
            mWorkersRL = (RelativeLayout) view.findViewById(R.id.b_g_employee_btn);
            mWorkersTV = (TextView) view.findViewById(R.id.employee);
            mArrorWorkersIV = (ImageView) view.findViewById(R.id.arror_workers);
            mArrowAddWorkerIV = (ImageView) view.findViewById(R.id.arror_add_workers);
            mAllWorkersService = (LinearLayout) view.findViewById(R.id.list_workers);
            mCalenderR = (RelativeLayout) view.findViewById(R.id.b_g_calendar_set_btn);
            mCalenderTv = (TextView) view.findViewById(R.id.calendar);
            mArrowCalenderIV = (ImageView) view.findViewById(R.id.arrow_calender);
            mServiceListL = (LinearLayout) view.findViewById(R.id.list_of_services);
            mAddWorkerR = (RelativeLayout) view.findViewById(R.id.add_worker);
            mAddServiceR = (RelativeLayout) view.findViewById(R.id.add_service);
            mSeperateW = view.findViewById(R.id.seprate_w);
            mSeperateS = view.findViewById(R.id.seprate_s);

//            Listeners
            mAddWorkerR.setOnClickListener(this);
            mAddServiceR.setOnClickListener(this);
            mServiceRL.setOnClickListener(this);
            mCalenderR.setOnClickListener(this);
            mWorkersRL.setOnClickListener(this);
            mContinueBtn.setOnClickListener(this);
            ok.setOnClickListener(this);
            mBreaksBtn.setOnClickListener(this);
            imageVbreak.setOnClickListener(this);
            imageV.setOnClickListener(this);
            mB_areaR.setOnClickListener(this);
            gridView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.requestFocus();
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

//            days for Working hours
            day1 = (LinearLayout) view.findViewById(R.id.day1);
            day2 = (LinearLayout) view.findViewById(R.id.day2);
            day3 = (LinearLayout) view.findViewById(R.id.day3);
            day4 = (LinearLayout) view.findViewById(R.id.day4);
            day5 = (LinearLayout) view.findViewById(R.id.day5);
            day6 = (LinearLayout) view.findViewById(R.id.day6);
            day1.setOnClickListener(this);
            day2.setOnClickListener(this);
            day3.setOnClickListener(this);
            day4.setOnClickListener(this);
            day5.setOnClickListener(this);
            day6.setOnClickListener(this);
            day1break = (LinearLayout) view.findViewById(R.id.day1break);
            day2break = (LinearLayout) view.findViewById(R.id.day2break);
            day3break = (LinearLayout) view.findViewById(R.id.day3break);
            day4break = (LinearLayout) view.findViewById(R.id.day4break);
            day5break = (LinearLayout) view.findViewById(R.id.day5break);
            day6break = (LinearLayout) view.findViewById(R.id.day6break);
            day1break.setOnClickListener(this);
            day2break.setOnClickListener(this);
            day3break.setOnClickListener(this);
            day4break.setOnClickListener(this);
            day5break.setOnClickListener(this);
            day6break.setOnClickListener(this);


            viewTimePicker.setMyListener(new CustomViewTimePicker.MyListener() {
                @Override
                public void ScrollHour() {
                    saveHours(parentPositionDay, null, true);
                }
            });

            cvTimePickerbreak.setMyListener(new CustomViewTimePicker.MyListener() {
                @Override
                public void ScrollHour() {
                    saveHoursBreak(parentPositionDayBreak, null, true);
                }
            });

            bus_g_calendarSet.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            mAllL.setVisibility(View.GONE);

            //select Categories
            Realm realm = Utils.getRealmInstance(getContext());
            fieldAndCategoryDMs = realm.where(FieldAndCategoryDM.class).findAll();
            if (fieldAndCategoryDMs.toString().equals("[]")) {
                getInformation();
            } else {
                bussinesAreaAdapterActivity = new BussinesAreaAdapterActivity(getContext(), fieldAndCategoryDMs);
                gridView.setAdapter(bussinesAreaAdapterActivity);
                bussinesAreaAdapterActivity.isFlag();
            }
//            For additional retrieval times entering the stand
            GeneralDetailsRealm generalDetailsRealm = realm.where(GeneralDetailsRealm.class).findFirst();
            if (generalDetailsRealm != null) {
                ProviderGeneralDetailsObj.setInstance(new ProviderGeneralDetailsObj(generalDetailsRealm));
                ProviderDetailsObj.getInstance().setObjProviderGeneralDetails(new ProviderGeneralDetailsObj(generalDetailsRealm));
                //
                ProviderGeneralDetailsObj.getInstance().setServiceProviders(generalDetailsRealm.getObjServiceProviders());
                putService();
                putWorker();
                bus_g_calendarSet.setCalendarProperties(generalDetailsRealm.getObjCalendarProperties());

                if (ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getWorkingHours() != null) {
                    //put breaks from the realm
                    String s = setTextHour((ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getWorkingHours()), 2, DaysBreaks);
                    if (!(s.equals(""))) {
                        hours_break_txt.setVisibility(View.VISIBLE);
                        hours_break_txt.setText(getString(R.string.break_, s));

                    } else {
                        hours_break_txt.setText("");
                        hours_break_txt.setVisibility(View.GONE);
                    }
                    //put hours from the realm
                    s = setTextHour(ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getWorkingHours(), 3, Days);
                    hours_txt.setText(s);
                    hours_txt.setVisibility(View.VISIBLE);
                    hoursTV.setTextColor(getResources().getColor(R.color.color1));
                }
                if (ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getiFieldId() != -1) {
                    int i = 0;
                    for (FieldAndCategoryDM fieldAndCategoryDM : fieldAndCategoryDMs) {
                        if (fieldAndCategoryDM.getiCategoryRowId() == ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getiFieldId()) {
                            position = i;
                            mSubjectTV.setText(fieldAndCategoryDM.getNvCategoryName());
                            mSubjectTV.setTextColor(getResources().getColor(R.color.color1));
                            changeToChoose();
                        }
                        i++;
                    }

                }
            } else {
                ProviderGeneralDetailsObj.setInstance(null);
                ProviderDetailsObj.getInstance().setObjProviderGeneralDetails(new ProviderGeneralDetailsObj());
            }

            //initialization Categories
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position_, long id) {
                    FieldAndCategoryDM fieldAndCategoryDM = (FieldAndCategoryDM) parent.getAdapter().getItem(position_);
                    ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().setiFieldId(fieldAndCategoryDM.getiCategoryRowId());
                    position = position_;
                    fieldAndCategoryDM = (FieldAndCategoryDM) parent.getAdapter().getItem(position);
                    mSubjectTV.setText(fieldAndCategoryDM.getNvCategoryName());
                    mSubjectTV.setTextColor(getResources().getColor(R.color.color1));
                    changeToChoose();
                }
            });
        }

        //back
        if (getActivity() instanceof MainActivity) {
            if (is_back)
                super.initFragmentTop(MainActivity.Num, getString(R.string.bus_general_data), 2, true);
            else
                super.initFragmentTop(MainActivity.Num, getString(R.string.bus_general_data), 2, false);
            scrollOfActivity = ((MainActivity) getActivity()).getScrollView();
        } else {
            super.initFragmentTop3(2, getString(R.string.bus_general_data), is_back);
            ll_scroll.setVerticalScrollBarEnabled(true);
            ok.setVisibility(View.VISIBLE);
            mContinueBtn.setVisibility(View.GONE);
        }
        is_back = false;


        return view;
    }

    private void getInformation() {
        String jsonString = "{}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SplashBL.getFieldsAndCategories(getActivity(), jsonObject, new IExecutable<List<FieldAndCategory>>() {
            @Override
            public void onExecute(List<FieldAndCategory> fieldAndCategoryList) {
                Realm realm = null;
                if (fieldAndCategoryList != null) {
                    realm = Utils.getRealmInstance(getActivity());
                    RealmResults<FieldAndCategoryDM> results = realm.where(FieldAndCategoryDM.class).findAll();
                    realm.beginTransaction();
                    results.deleteAllFromRealm();
                    realm.commitTransaction();
                    for (FieldAndCategory fieldAndCategory : fieldAndCategoryList) {
                        realm.beginTransaction();
                        FieldAndCategoryDM fieldAndCategoryDM = realm.createObject(FieldAndCategoryDM.class);
                        fieldAndCategoryDM.setiFieldRowId(fieldAndCategory.getiFieldRowId());
                        fieldAndCategoryDM.setNvFieldName(fieldAndCategory.getNvFieldName());
                        fieldAndCategoryDM.setiCategoryRowId(fieldAndCategory.getiCategoryRowId());
                        fieldAndCategoryDM.setNvCategoryName(fieldAndCategory.getNvCategoryName());
                        realm.commitTransaction();
                    }
                    fieldAndCategoryDMs = realm.where(FieldAndCategoryDM.class).findAll();
                    bussinesAreaAdapterActivity = new BussinesAreaAdapterActivity(getContext(), fieldAndCategoryDMs);
                    gridView.setAdapter(bussinesAreaAdapterActivity);
                    bussinesAreaAdapterActivity.isFlag();
                }
            }
        });
    }

    public void delete_service(NewService newService) {
        mAllServices.removeView(newService);
        mServiceListL.removeView(newService);
        mAllWorkersService.removeView(newService);
        if (mServiceListL.getChildCount() == 0) {
            flags_service = 0;
            mAddServiceR.setVisibility(View.GONE);
            mSeperateS.setVisibility(View.GONE);
            openService = true;
            mServiceTV.setTextColor(Color.GRAY);
            mArrowServiceIV.setVisibility(View.VISIBLE);
            mArrowServiceIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_black));
        }
    }

    public void delete_worker(NewWorker newWorker) {
        mAllL.removeView(newWorker);
        if (mAllL.getChildCount() == 0) {
            mAddWorkerR.setVisibility(View.GONE);
            mWorkerDetailsPressed = false;
            openWorkers = true;
            mArrorWorkersIV.setVisibility(View.VISIBLE);
            mArrorWorkersIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_black));
            mWorkersTV.setTextColor(Color.GRAY);
            newDw = null;
        }
    }

    //change Category
    public void changeToChoose() {
        if (choose_p != position) {
            flag_area = 1;
            bussinesAreaAdapterActivity.change_position(position);
            if (choose_p != -1) {
                choose_p = position;
            }
        } else {
            choose_p = -1;
            flag_area = 0;
            position = -1;
            mSubjectTV.setText(getResources().getString(R.string.bus_area));
            mSubjectTV.setTextColor(Color.GRAY);
            bussinesAreaAdapterActivity.change_position(-1);
        }
        choose_p = position;

        if (choose_p != -1)
            mSubjectTV.setError(null);

    }


    private void setData(String name, String hour, int day, int fromOrTO) {
        if (name.contains("w")) {
            if (fromOrTO == 1) {
                insertList(day, 1, hour, 1);
            } else if (fromOrTO == 0) {
                insertList(day, 0, hour, 0);
            }
        } else if (name.contains("r")) {
            if (fromOrTO == 1) {
                insertList(day, 1, hour, 0);
            } else if (fromOrTO == 0) {
                insertList(day, 0, hour, 1);
            }

        }
    }

    private void insertList(int day, int num, String hour, int toOrFrom) {
        for (int i = 0; i < workingHoursListHelp.size(); i++) {
            if (workingHoursListHelp.get(i).getiDayInWeekType() == day && workingHoursListHelp.get(i).getNum() == num) {
                if (toOrFrom == 1) {
                    workingHoursListHelp.get(i).setNvToHour(hour);
                    return;
                } else {
                    workingHoursListHelp.get(i).setNvFromHour(hour);
                    return;
                }
            }
        }
        if (toOrFrom == 1) {
            workingHoursListHelp.add(new WorkingHours(day, "", hour, num));
        } else {
            workingHoursListHelp.add(new WorkingHours(day, hour, "", num));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ok:
                getActivity().onBackPressed();
                break;

            // onclick next
            case R.id.b_g_general_continue1:
                if (SaveAndNext(-1)) {
                    required = true;
                    validate = true;
                    if (flag_area == 0) {
                        mSubjectTV.setError(getResources().getString(R.string.error_empty));
                        required = false;
                    } else if (choose_p != -1)
                        mSubjectTV.setError(null);
                    if (ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getWorkingHours().size() == 0) {
                        hoursTV.setError(getResources().getString(R.string.error_empty));
                        required = false;
                    } else
                        hoursTV.setError(null);
                    if (flags_service == 0) {
                        mServiceTV.setError(getResources().getString(R.string.error_empty));
                        required = false;
                    } else
                        mServiceTV.setError(null);
                    if (mCalendarSet == 1)
                        required = false;
                    if (ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getWorkingHours().size() >
                            0 && flags_calendar == 1 && flags_service == 1 && flag_area == 1 && mCalendarSet == 0) {
                        mContinueBtn.setEnabled(false);
                        ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().setiProviderUserId((int) getProviderId());
                        ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().setCalendarProperties(bus_g_calendarSet.getCalendarProperties());
                        Realm realm = Utils.getRealmInstance(getContext());
                        ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().setServiceProviders(ProviderGeneralDetailsObj.getInstance().getServiceProviders());

                        GeneralDetailsRealm detailsRealm = new GeneralDetailsRealm(ProviderDetailsObj.getInstance().getObjProviderGeneralDetails());
                        realm.beginTransaction();

                        realm.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
                        realm.copyToRealm(detailsRealm);


                        realm.commitTransaction();
                        GeneralDetailsRealm generalDetailsRealm2 = realm.where(GeneralDetailsRealm.class).findFirst();

                        if (!ConnectionUtils.if_server)
                            initFragmentMain(AlertsFragment.getInstance(), true, true, 3);
                        else
                            initFragmentMain(AlertsFragment.getInstance(), true, true, 3);

                    } else
                        Toast.makeText(getContext(), R.string.empty_fields, Toast.LENGTH_LONG).show();
                }
                break;

            //open calendar settings
            case R.id.b_g_calendar_set_btn:

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


                if (mCalendarSet == 0) {
                    if (SaveAndNext(4)) {
                        if (getActivity() instanceof MainActivity)
                            ((MainActivity) getActivity()).getScrollView().fullScroll(View.FOCUS_DOWN);
                        bus_g_calendarSet.setVisibility(View.VISIBLE);
                        mCalendarSet = 1;
                        focusOnView(bus_g_calendarSet);
                        mArrowCalenderIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow));
                    }
                } else {
                    field_arr[4] = false;
                    SaveCalendarSettings();
                }
                break;

            //open bussines area
            case R.id.b_g_area:
                imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (mArea == 0) {
                    if (SaveAndNext(0)) {
                        gridView.setVisibility(View.VISIBLE);
                        mArea = 1;
                        mArrowSubjectIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow));
                    }
                } else {
                    closeArea();
                    field_arr[0] = false;
                }
                break;

            //open bussines services
            case R.id.b_g_services_btn:
                imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (openService) {
                    if (mServices == 0) {
                        if (SaveAndNext(3)) {
                            ser = new Service(getActivity(), null);
                            ser.getmDiscountEt().setOnEditorActionListener(new EditText.OnEditorActionListener() {
                                @Override
                                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                    boolean handled = false;
                                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                                        handled = true;
                                        ser.getmDiscountEt().clearFocus();
                                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                                    }
                                    return handled;
                                }
                            });
                            if (getActivity() instanceof MainActivity)
                                ((MainActivity) getActivity()).getScrollView().fullScroll(View.FOCUS_DOWN);
                            ser.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            mAllServices.addView(ser);
                            mAllServices.setVisibility(View.VISIBLE);

                            focusOnView(mAllServices);
                            mServices = 1;
                            mArrowServiceIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow));
                        }
                    } else {
                        field_arr[3] = false;
                        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        SaveService();
                    }
                } else
                    SaveAndNext(-1);
                break;

            //open employee details
            case R.id.b_g_employee_btn:
                imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (openWorkers) {
                    if (!mWorkerDetailsPressed) {
                        if (SaveAndNext(2)) {
                            dw = new DetailsWorkers(getActivity(), null);
                            mAllWorkersService.addView(dw);
                            if (getActivity() instanceof MainActivity)
                                ((MainActivity) getActivity()).getScrollView().fullScroll(View.FOCUS_DOWN);
                            mWorkerDetailsPressed = true;
                            focusOnView(mAllWorkersService);
                            mAllWorkersService.setVisibility(View.VISIBLE);
                            mArrorWorkersIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow));
                        }
                    } else {
                        //SaveDetailsWorker();
                        SaveDetailsWorkerNew();
                        field_arr[2] = false;
                    }
                } else {
                    SaveAndNext(-1);
                }
                break;

            //add worker
            case R.id.add_worker:
                imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                addWorker();
                break;

            //add service
            case R.id.add_service:
                imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                addService();
                break;

            //click addBreaks
            case R.id.addBreaks:
                if (!checkHour(false)) {
                    saveHours(0, null, false);
//                    saveHoursBreak(0, null);
                }
                if (checkHour(true) || isBreak) {
                    workHours.setVisibility(View.GONE);
                    saveHours(0, null, false);
                    imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    if (mBreaks == 0) {
                        mBreaksL.setVisibility(View.VISIBLE);
                        mBreaks = 1;
                        focusOnView(mBreaksL);
                    } else {
                        mBreaksL.setVisibility(View.GONE);
                        mBreaks = 0;
                    }
                } else {

                }
                break;

            //open working hours
            case R.id.hours:
                imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (mHours == 0) {
                    if (SaveAndNext(1)) {
                        mArrowHoursIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow));
                        mHoursL.setVisibility(View.VISIBLE);
                        mHours = 1;
                        focusOnView(mHoursL);
                    } else {
                        if (checkHour(true)) {
                            saveHours(0, null, false);
                            if (breaksHourses != null && breaksHourses.size() > 0) {
                                saveHoursBreak(0, null, false);
                            }
                        } else {
                            if (workingHoursList.size() > 0) {
                                hours_txt.setError(null);
                            }
                        }
                        mHoursL.setVisibility(View.GONE);
                        mBreaksL.setVisibility(View.GONE);
                        mHours = 0;
                    }
                } else {

                    checkAndSaveHours();
                    field_arr[1] = false;
                }
                break;

            case R.id.save_breaks:
                if (!NotAllFalse(Days)) {
                    hours_txt.setError("");
                    Toast.makeText(getActivity(), getResources().getString(R.string.enter_hour), Toast.LENGTH_LONG).show();
                } else if (checkHour(true)) {
                    String s = setTextHour(workingHourses, 1, Days);
                    hours_txt.setText(s);
                    hours_txt.setVisibility(View.VISIBLE);
                    hoursTV.setTextColor(getResources().getColor(R.color.color1));
                    mHoursL.setVisibility(View.GONE);
                    mHours = 0;
                    flagHours = false;
                }
                break;

            //chose all days
            case R.id.alldays:
                if (!isAllHours) {
                    if (isAllEquals(workingHourses, Days)) {
                        if (toBigerFrom(viewTimePicker.from, viewTimePicker.to) && !viewTimePicker.from.equals(viewTimePicker.to)) {

                            imageV.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                            isAllHours = true;
                            for (int i = 0; i < 6; i++) {
                                view.findViewWithTag("day" + (i + 1)).setBackgroundColor(getResources().getColor(R.color.color4));
                                ((TextView) view.findViewWithTag("t" + (i + 1))).setTextColor(getResources().getColor(R.color.color_white));
                            }
                            hours_txt.setVisibility(View.VISIBLE);
                            Arrays.fill(Days, true);
                            SaveAllDays();
                            if (checkHour(true)) {
                                String s = setTextHour(workingHourses, 1, Days);
                                hours_txt.setText(s);
                                String s1 = setTextHour(breaksHourses, 1, DaysBreaks);
                                if (!s1.equals("")) {
                                    hours_break_txt.setVisibility(View.VISIBLE);
                                    hours_break_txt.setText(getString(R.string.break_, s1));
                                } else {
                                    hours_break_txt.setVisibility(View.GONE);
                                    hours_break_txt.setText("");
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.enter_hour_period), Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    imageV.setImageResource(R.drawable.client_galaxy_icons1_34);
                    isAllHours = false;
                    for (int i = 0; i < 6; i++) {
                        view.findViewWithTag("day" + (i + 1)).setBackgroundColor(getResources().getColor(R.color.light_gray_8));
                        ((TextView) view.findViewWithTag("t" + (i + 1))).setTextColor(getResources().getColor(R.color.black));
                    }
                    workingHourses.clear();
                    Arrays.fill(Days, false);
                    imageVbreak.setImageResource(R.drawable.client_galaxy_icons1_34);
                    isAllHoursBreak = false;
                    for (int i = 0; i < 6; i++) {
                        view.findViewWithTag("day" + (i + 1) + "break").setBackgroundColor(getResources().getColor(R.color.light_gray_8));
                        ((TextView) view.findViewWithTag("t" + (i + 1) + "break")).setTextColor(getResources().getColor(R.color.black));
                    }
                    Arrays.fill(DaysBreaks, false);
                    breaksHourses.clear();
                    hours_txt.setVisibility(View.GONE);
                    if (checkHour(true)) {
                        String s = setTextHour(workingHourses, 1, Days);
                        hours_txt.setText(s);
                        String s1 = setTextHour(breaksHourses, 1, DaysBreaks);
                        if (!s1.equals("")) {
                            hours_break_txt.setVisibility(View.VISIBLE);
                            hours_break_txt.setText(getString(R.string.break_, s1));
                        } else {
                            hours_break_txt.setVisibility(View.GONE);
                            hours_break_txt.setText("");
                        }
                    }
                }
                break;

            //chose all days break
            case R.id.alldaysbreak:
                if (!isAllHoursBreak) {
                    saveHoursBreak(0, null, true);
                    if (isAllEquals(breaksHourses, DaysBreaks)) {
                        if (allTrue(Days)) {
                            if (checkHour(false)) {
                                imageVbreak.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                                isAllHoursBreak = true;
                                for (int i = 0; i < 6; i++) {
                                    view.findViewWithTag("day" + (i + 1) + "break").setBackgroundColor(getResources().getColor(R.color.color4));
                                    ((TextView) view.findViewWithTag("t" + (i + 1) + "break")).setTextColor(getResources().getColor(R.color.color_white));
                                }
                                hours_break_txt.setVisibility(View.VISIBLE);
                                Arrays.fill(DaysBreaks, true);
                                SaveAllDaysbreak();
                                if (checkHour(true)) {
                                    String s = setTextHour(breaksHourses, 1, DaysBreaks);
                                    if (!s.equals("")) {
                                        hours_break_txt.setText(getString(R.string.break_, s));
                                        hours_break_txt.setVisibility(View.VISIBLE);
                                        hoursTV.setTextColor(getResources().getColor(R.color.color1));
                                    } else {
                                        hours_break_txt.setText("");
                                        hours_break_txt.setVisibility(View.GONE);
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.enter_hour_period), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    imageVbreak.setImageResource(R.drawable.client_galaxy_icons1_34);
                    isAllHoursBreak = false;
                    for (int i = 0; i < 6; i++) {
                        view.findViewWithTag("day" + (i + 1) + "break").setBackgroundColor(getResources().getColor(R.color.light_gray_8));
                        ((TextView) view.findViewWithTag("t" + (i + 1) + "break")).setTextColor(getResources().getColor(R.color.black));
                    }
                    Arrays.fill(DaysBreaks, false);
                    breaksHourses.clear();
                    hours_break_txt.setVisibility(View.GONE);
                    if (checkHour(true)) {
                        String s = setTextHour(breaksHourses, 1, DaysBreaks);
                        if (!s.equals("")) {
                            hours_break_txt.setText(getString(R.string.break_, s));
                            hours_break_txt.setVisibility(View.VISIBLE);
                            hoursTV.setTextColor(getResources().getColor(R.color.color1));
                        } else {
                            hours_break_txt.setText("");
                            hours_break_txt.setVisibility(View.GONE);
                        }
                    }
                }
                break;

//            Clicking on a specific day
            case R.id.day1:
                saveHours(0, v, true);
                break;
            case R.id.day2:
                saveHours(1, v, true);
                break;
            case R.id.day3:
                saveHours(2, v, true);
                break;
            case R.id.day4:
                saveHours(3, v, true);
                break;
            case R.id.day5:
                saveHours(4, v, true);
                break;
            case R.id.day6:
                saveHours(5, v, true);
                break;
            case R.id.day1break:
                saveHoursBreak(0, v, true);
                break;
            case R.id.day2break:
                saveHoursBreak(1, v, true);
                break;
            case R.id.day3break:
                saveHoursBreak(2, v, true);
                break;
            case R.id.day4break:
                saveHoursBreak(3, v, true);
                break;
            case R.id.day5break:
                saveHoursBreak(4, v, true);
                break;
            case R.id.day6break:
                saveHoursBreak(5, v, true);
                break;
        }
    }


    private void SaveAllDays() {
        for (int i = 0; i < 6; i++) {
            int p = getWorkingHoursesPositionByDay(workingHourses, i + 1);
            if (p != -1) {
                workingHourses.get(p).setNvToHour(viewTimePicker.to);
                workingHourses.get(p).setNvFromHour(viewTimePicker.from);
            } else {
                workingHourses.add(new WorkingHours(i + 1, viewTimePicker.from, viewTimePicker.to));
            }
            setData("w", viewTimePicker.from, i + 1, 0);
            setData("w", viewTimePicker.to, i + 1, 1);
        }
        parentPositionDay = 0;
    }

    private void SaveAllDaysbreak() {
        for (int i = 0; i < 6; i++) {
            int p = getWorkingHoursesPositionByDay(breaksHourses, i + 1);
            if (p != -1) {
                breaksHourses.get(p).setNvToHour(cvTimePickerbreak.to);
                breaksHourses.get(p).setNvFromHour(cvTimePickerbreak.from);
            } else {
                breaksHourses.add(new WorkingHours(i + 1, cvTimePickerbreak.from, cvTimePickerbreak.to));
            }
            setData("r", cvTimePickerbreak.from, i + 1, 0);
            setData("r", cvTimePickerbreak.to, i + 1, 1);
        }
        parentPositionDayBreak = 0;
    }

    private void saveHours(int position, View v, boolean isShow) {
        if (parentPositionDay != -1) {
            int p = getWorkingHoursesPositionByDay(workingHourses, parentPositionDay + 1);
            if (p != -1) {
                workingHourses.get(p).setNvFromHour(viewTimePicker.from);
                workingHourses.get(p).setNvToHour(viewTimePicker.to);
            } else {
                workingHourses.add(new WorkingHours(parentPositionDay + 1, viewTimePicker.from, viewTimePicker.to));
            }
            setData("w", viewTimePicker.from, parentPositionDay + 1, 0);
            setData("w", viewTimePicker.to, parentPositionDay + 1, 1);
        }
        if (parentPositionDay == -1 || checkHour(isShow)) {
            if (parentPositionDay != -1) {
                String s = setTextHour(workingHourses, 1, Days);
                hours_txt.setText(s);
                hours_txt.setVisibility(View.VISIBLE);
                hoursTV.setError(null);
                hoursTV.setTextColor(getResources().getColor(R.color.color1));
            }
            if (v != null) {
                if (!Days[position]) {
                    v.setBackgroundColor(getResources().getColor(R.color.color4));
                    ((TextView) v.findViewWithTag("t" + (position + 1))).setTextColor(getResources().getColor(R.color.color_white));
                    Days[position] = true;
                    WorkingHours workingHours = getWorkingHoursesByDay(workingHourses, position + 1);
                    if (workingHours != null) {
                        viewTimePicker.from_pv.setSelected(workingHours.getNvFromHour());
                        viewTimePicker.to_pv.setSelected(workingHours.getNvToHour());
                        viewTimePicker.setSelected(workingHours.getNvFromHour(), workingHours.getNvToHour());
                    }
                    parentPositionDay = position;
                } else {
                    RemoveDay(v, position);
                }
            }

            if (allTrue(Days) && isAllEquals(workingHourses, Days)) {
                imageV.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                isAllHours = true;
            }
        } else {
            viewTimePicker.to_pv.setSelected(viewTimePicker.from);
            viewTimePicker.setSelected(viewTimePicker.from, viewTimePicker.from);
            if (v != null) {
                if (position == parentPositionDay) {
                    RemoveDay(v, position);
                }
            }
        }
    }

    public void RemoveDay(View v, int position) {
        if (v != null) {
            v.setBackgroundColor(getResources().getColor(R.color.light_gray_8));
            ((TextView) v.findViewWithTag("t" + (position + 1))).setTextColor(getResources().getColor(R.color.black));
        }
        Days[position] = false;
        boolean allFalse = true;
        for (int i = 0; i <= 5; i++)
            if (Days[i])
                allFalse = false;
        if (DaysBreaks[position]) {
            view.findViewWithTag("day" + (position + 1) + "break").setBackgroundColor(getResources().getColor(R.color.light_gray_8));
            ((TextView) view.findViewWithTag("t" + (position + 1) + "break")).setTextColor(getResources().getColor(R.color.black));
            DaysBreaks[position] = false;
            if (checkHour(true)) {
                String s = setTextHour(breaksHourses, 1, DaysBreaks);
                if (!s.equals("")) {
                    hours_break_txt.setText(getString(R.string.break_, s));
                } else {
                    hours_break_txt.setText("");
                    hours_break_txt.setVisibility(View.GONE);
                }
                hours_txt.setVisibility(View.VISIBLE);
                hoursTV.setTextColor(getResources().getColor(R.color.color1));
            }
        }
        if (isAllHours) {
            imageV.setImageResource(R.drawable.client_galaxy_icons1_34);
            isAllHours = false;
            if (isAllHoursBreak) {
                imageVbreak.setImageResource(R.drawable.client_galaxy_icons1_34);
                isAllHoursBreak = false;
            }
        }
        if (checkHour(true)) {
            String s = setTextHour(workingHourses, 1, Days);
            hours_txt.setText(s);
            if (allFalse)
                hours_txt.setVisibility(View.GONE);
            else
                hours_txt.setVisibility(View.VISIBLE);
            hoursTV.setTextColor(getResources().getColor(R.color.color1));
        }
    }

    private void saveHoursBreak(int position, View v, boolean isShow) {
        if (parentPositionDayBreak != -1) {
            int p = getWorkingHoursesPositionByDay(breaksHourses, parentPositionDayBreak + 1);
            if (p != -1) {
                breaksHourses.get(p).setNvFromHour(cvTimePickerbreak.from);
                breaksHourses.get(p).setNvToHour(cvTimePickerbreak.to);
            } else {
                breaksHourses.add(new WorkingHours(parentPositionDayBreak + 1, cvTimePickerbreak.from, cvTimePickerbreak.to));
            }
            setData("r", cvTimePickerbreak.from, parentPositionDayBreak + 1, 0);
            setData("r", cvTimePickerbreak.to, parentPositionDayBreak + 1, 1);

        }
        if (parentPositionDayBreak == -1 || checkHour(isShow)) {
            if (parentPositionDayBreak != -1) {
                String s = setTextHour(breaksHourses, 1, DaysBreaks);
                if (!s.equals("")) {
                    hours_break_txt.setVisibility(View.VISIBLE);
                    hours_break_txt.setText(getString(R.string.break_, s));
                } else {
                    hours_break_txt.setText("");
                    hours_break_txt.setVisibility(View.GONE);
                }
            }
            if (v != null) {
                if (!DaysBreaks[position] && Days[position]) {
                    v.setBackgroundColor(getResources().getColor(R.color.color4));
                    ((TextView) v.findViewWithTag("t" + (position + 1) + "break")).setTextColor(getResources().getColor(R.color.color_white));
                    DaysBreaks[position] = true;
                    WorkingHours workingHours = getWorkingHoursesByDay(breaksHourses, position + 1);
                    if (workingHours != null) {
                        cvTimePickerbreak.from_pv.setSelected(workingHours.getNvFromHour());
                        cvTimePickerbreak.to_pv.setSelected(workingHours.getNvToHour());
                        cvTimePickerbreak.setSelected(workingHours.getNvFromHour(), workingHours.getNvToHour());
                    } else {
                        WorkingHours workingHours1 = getWorkingHoursesByDay(workingHourses, position + 1);
                        if (workingHours1 != null) {
                            cvTimePickerbreak.from_pv.setSelectedtop(workingHours1.getNvFromHour(), true);
                            cvTimePickerbreak.to_pv.setSelectedtop(workingHours1.getNvToHour(), false);
                            cvTimePickerbreak.setSelectedTop(workingHours1.getNvFromHour(), workingHours1.getNvToHour());
                        }
                    }
                    parentPositionDayBreak = position;
                } else {
                    RemoveDayBreak(v, position);
                }
            }
            if (allTrue(DaysBreaks) && isAllEquals(breaksHourses, DaysBreaks)) {
                imageVbreak.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                isAllHoursBreak = true;
            }
        } else {
            cvTimePickerbreak.to_pv.setSelected(cvTimePickerbreak.from);
            cvTimePickerbreak.setSelected(cvTimePickerbreak.from, cvTimePickerbreak.from);
            if (v != null) {
                if (position == parentPositionDayBreak) {
                    RemoveDayBreak(v, position);
                }
            }
        }
    }

    public void RemoveDayBreak(View v, int position) {
        if (v != null) {
            v.setBackgroundColor(getResources().getColor(R.color.light_gray_8));
            ((TextView) v.findViewWithTag("t" + (position + 1) + "break")).setTextColor(getResources().getColor(R.color.black));
        }
        DaysBreaks[position] = false;
        if (checkHour(true)) {
            boolean AllFalse = true;
            for (int i = 0; i <= 5; i++) {
                if (DaysBreaks[i]) {
                    AllFalse = false;
                }
            }
            if (AllFalse) {
                hours_break_txt.setVisibility(View.GONE);
            } else {
                hours_break_txt.setVisibility(View.VISIBLE);
            }

            String s = setTextHour(breaksHourses, 1, DaysBreaks);
            if (!s.equals("")) {
                hours_break_txt.setVisibility(View.VISIBLE);
                hours_break_txt.setText(getString(R.string.break_, s));
            } else {
                hours_break_txt.setText("");
                hours_break_txt.setVisibility(View.GONE);
            }


            if (isAllHoursBreak) {
                imageVbreak.setImageResource(R.drawable.client_galaxy_icons1_34);
                isAllHoursBreak = false;
            }
        }
    }

    public WorkingHours getWorkingHoursesByDay(ArrayList<WorkingHours> wh, int day) {
        for (int i = 0; i < wh.size(); i++) {
            if (wh.get(i).getiDayInWeekType() == day)
                return wh.get(i);
        }
        return null;
    }

    public int getWorkingHoursesPositionByDay(ArrayList<WorkingHours> wh, int day) {
        for (int i = 0; i < wh.size(); i++) {
            if (wh.get(i).getiDayInWeekType() == day)
                return i;
        }
        return -1;
    }

    public boolean isAllEquals(ArrayList<WorkingHours> wh, boolean[] b) {
        if (wh == null || wh.size() == 0) {
            return true;
        }
        WorkingHours wh1 = wh.get(0);
        for (WorkingHours workingHours : wh) {
            if (b[workingHours.getiDayInWeekType() - 1] && (!wh1.getNvFromHour().equals(workingHours.getNvFromHour()) || !wh1.getNvToHour().equals(workingHours.getNvToHour()))) {
                return false;
            }
        }
        return true;
    }


    public boolean SaveAndNext(int position) {
        int parentPosition = -1;
        for (int i = 0; i < field_arr.length; i++) {
            if (field_arr[i]) {
                parentPosition = i;
                field_arr[parentPosition] = false;
            }
        }
        if (position != -1) {
            field_arr[position] = true;
        }
        if (parentPosition > -1) {
            switch (parentPosition) {
                case 0://subject
                    return closeArea();
                case 1:  //hours
                    return checkAndSaveHours();

                case 2://workers
                    // return SaveDetailsWorker();
                    return SaveDetailsWorkerNew();

                case 3://service
                    return SaveService();

                case 4://calender
                    return SaveCalendarSettings();

                case 5://edit worker
                    return editWorker();

                case 6://edit service
                    return editService();

            }
        } else {
            return true;
        }
        return false;
    }

    //  private void setA
    private boolean closeArea() {
        mArrowSubjectIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_black));
        if (choose_p != -1) {
            mSubjectTV.setError(null);
            gridView.setVisibility(View.GONE);
            mArea = 0;
            return true;
        } else {
            // makeAllFalse();
            field_arr[0] = true;
            mSubjectTV.setError(getResources().getString(R.string.error_empty));
            gridView.setVisibility(View.GONE);
            mArea = 0;
            return true;
        }
    }

    private boolean checkAndSaveHours() {
        if (!checkHour(false)) {
            saveHours(0, null, false);
            saveHoursBreak(0, null, false);
        }
        if (checkHour(true)) {
            saveHours(0, null, false);
            if (breaksHourses != null && breaksHourses.size() > 0) {
                saveHoursBreak(0, null, false);
            }
            String s = setTextHour(breaksHourses, 1, DaysBreaks);
            if (!s.equals("")) {
                hours_break_txt.setText(getString(R.string.break_, s));
                hours_break_txt.setVisibility(View.VISIBLE);
            } else {
                hours_break_txt.setText("");
                hours_break_txt.setVisibility(View.GONE);
            }
        } else {
            if (workingHoursList.size() > 0)
                hours_txt.setError(null);
        }
        parentPositionDay = -1;
        parentPositionDayBreak = -1;
        mArrowHoursIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_black));
        workHours.setVisibility(View.VISIBLE);
        mHoursL.setVisibility(View.GONE);
        mBreaksL.setVisibility(View.GONE);
        if (hours_txt.getText().equals("")) {
            hours_txt.setVisibility(View.GONE);
            hoursTV.setTextColor(Color.GRAY);
        }
        if (hours_break_txt.getText().equals(""))
            hours_break_txt.setVisibility(View.GONE);
        mHours = 0;
        mBreaks = 0;
        return true;
    }

    public boolean SaveDetailsWorkerNew() {
        if (newDw != null) {
            dw = newDw;
        }
        mArrowAddWorkerIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_white));
        mArrorWorkersIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_black));
        if (dw.checkAllFieldsNew(i)) {
            //if all the fields of the new worker are correct,so check the validation from the server
            phone_validity_server(dw.get_phone(), dw.getMail());
        } else {
            closeNotSaveWorker();
        }
        return true;
    }

    private boolean SaveService() {
        mArrowServiceIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_black));
        mArrowAddServiceIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_white));
        if (ser != null && ser.checkAllFields()) {
            mServiceTV.setTextColor(getResources().getColor(R.color.color1));
            flags_service = 1;
            mServiceTV.setError(null);
            ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices().add(ser.getProviderService());
            j++;
            mAllServices.removeView(ser);
            mAllServices.setVisibility(View.GONE);
            mServices = 0;
            String nameService = ser.getName();
            NewService newService = new NewService(getActivity(), null, nameService, ser);
            mServiceListL.addView(newService);
            mAddServiceR.setVisibility(View.VISIBLE);
            mServiceListL.setVisibility(View.VISIBLE);
            openService = false;
            mArrowServiceIV.setVisibility(View.GONE);
            return true;
        } else {
            closeNotSaveService();
            return true;
        }
    }

    private void putService() {
        Realm realm = Utils.getRealmInstance(getContext());
        GeneralDetailsRealm generalDetailsRealm = realm.where(GeneralDetailsRealm.class).findFirst();
        int r = generalDetailsRealm.getObjProviderServices().size();
        if (r > 0)
            flags_service = 1;
        for (i = 0; i < r; i++) {
            mArrowServiceIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_black));
            mArrowAddServiceIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_white));
            mServiceTV.setTextColor(getResources().getColor(R.color.color1));
            ser2 = new Service(getActivity(), null);
            ser2.pus_services(generalDetailsRealm.getObjProviderServices().get(i));
            j++;
            mAllServices.removeView(ser);
            mServices = 0;
            String nameService = ser2.getName();
            NewService newService = new NewService(getActivity(), null, nameService, ser2);
            mServiceListL.addView(newService);
            mAddServiceR.setVisibility(View.VISIBLE);
            mServiceListL.setVisibility(View.VISIBLE);
            openService = false;
            mArrowServiceIV.setVisibility(View.GONE);
        }
    }

    private void putWorker() {
        int r = ProviderGeneralDetailsObj.getInstance().getServiceProviders().size();
        for (int m = 0; m < r; m++) {
            if (ProviderGeneralDetailsObj.getInstance().getServiceProviders().get(m).getObjUsers() != null) {
                mWorkersTV.setTextColor(getResources().getColor(R.color.color1));
                dw = new DetailsWorkers(getActivity(), null);
                dw.put_workers(ProviderGeneralDetailsObj.getInstance().getServiceProviders().get(m));
                i++;
                String workerName = dw.getName();
                NewWorker new_worker = new NewWorker(getActivity(), null, workerName, dw);
                mAllL.addView(new_worker);
                mAddWorkerR.setVisibility(View.VISIBLE);
                mAllL.setVisibility(View.VISIBLE);
                mArrorWorkersIV.setVisibility(View.GONE);
            }
        }
    }

    private void addService() {
        if (mServices == 0) {
            if (SaveAndNext(3)) {
                mArrowAddServiceIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_white_down));
                newService = new Service(getActivity(), null);
                newService.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mAllServices.addView(newService);
                ser = newService;
                mAllServices.setVisibility(View.VISIBLE);
                mSeperateS.setVisibility(View.VISIBLE);
                mServices = 1;
                newService.getmDiscountEt().setOnEditorActionListener(new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean handled = false;
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            handled = true;
                            newService.getmDiscountEt().clearFocus();
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        }
                        return handled;
                    }
                });
                focusOnView(mAllServices);
            }
        } else {
            mServices = 0;
            //check
            field_arr[3] = false;
            SaveService();
        }
    }

    private void addWorker() {
        if (!mWorkerDetailsPressed) {
            if (SaveAndNext(2)) {
                mArrowAddWorkerIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_white_down));
                newDw = new DetailsWorkers(getActivity(), null);
                mAllWorkersService.setVisibility(View.VISIBLE);
                mAllWorkersService.addView(newDw);
                dw = newDw;
                mSeperateW.setVisibility(View.VISIBLE);
                mWorkerDetailsPressed = true;
                focusOnView(mAllWorkersService);
            }
        } else {
            mWorkerDetailsPressed = false;
            field_arr[2] = false;
            //SaveDetailsWorker();
            SaveDetailsWorkerNew();

        }
    }

    private boolean editWorker() {
        int childcount = mAllL.getChildCount();
        for (int i = 0; i < childcount; i++) {
            if (mAllL.getChildAt(i) instanceof NewWorker)
                if (!((NewWorker) mAllL.getChildAt(i)).getClicked()) {
                    if (((NewWorker) mAllL.getChildAt(i)).saveEditWorkerNew(i))
                        return true;
                    else
                        return false;
                }
        }
        return false;
    }

    private boolean editService() {
        int childcount = mServiceListL.getChildCount();
        for (int i = 0; i < childcount; i++) {
            if (mServiceListL.getChildAt(i) instanceof NewService)
                if (!((NewService) mServiceListL.getChildAt(i)).getClicked())
                    if (((NewService) mServiceListL.getChildAt(i)).save())
                        return true;
                    else
                        return false;
        }
        return false;
    }

    private boolean SaveCalendarSettings() {
        flags_calendar = 0;
        mArrowCalenderIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_black));
        if (bus_g_calendarSet.checkAllFields()) {
            mCalenderTv.setTextColor(getResources().getColor(R.color.color1));
            flags_calendar = 1;
            mCalenderTv.setError(null);
            ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().setCalendarProperties(bus_g_calendarSet.getCalendarProperties());
            bus_g_calendarSet.setVisibility(View.GONE);
            mCalendarSet = 0;
            return true;
        } else {
            closeCalender();
            return true;
        }
    }

    public void phone_validity_server(final String phone, final String workerMail) {
        //checking  that the worker's phone is not exists as a giving service or supplier
        String jsonString = "{\"nvPhone\":\"" + phone + "\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.CheckProviderExistByPhone(getActivity(), jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                if (isValid == 0) {
                    //check that the phone number is not existed for one of the workers that were inserted now
                    boolean notExisted = true;
                    ServiceProviders sp;
                    if (ProviderGeneralDetailsObj.getInstance().getServiceProviders().size() > 0)
                        for (int i = 0; i < ProviderGeneralDetailsObj.getInstance().getServiceProviders().size(); i++) {
                            sp = ProviderGeneralDetailsObj.getInstance().getServiceProviders().get(i);
                            if (sp.getObjUsers().getNvPhone().equals(phone))
                                notExisted = false;
                            //when editing a worker enable inserting the phone that is written there now
                            //not needed, for editing
//                                if (!dw.oldPhoneNumber.equals(""))
//                                    if (phone.equals(dw.oldPhoneNumber))
//                                        notExisted = true;
                        }
                    if (notExisted) {
                        //if the validation server of phone is correct, so check the validation server of mail
                        mail_validity_server(workerMail);
                    } else {
                        //if not correct,so do not save
                        Toast.makeText(getActivity(), getResources().getString(R.string.existed_phone_for_worker), Toast.LENGTH_SHORT).show();
                        closeNotSaveWorker();
                    }
                } else {
                    //if not correct,so do not save
                    Toast.makeText(getActivity(), getResources().getString(R.string.exists_phone_error), Toast.LENGTH_SHORT).show();
                    closeNotSaveWorker();
                }
            }
        });
    }

    public void mail_validity_server(final String mail) {
        //checking  that the worker's mail is not exists as a giving service or supplier
        String jsonString = "{\"nvMail\":\"" + mail + "\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.CheckProviderExistByMail(getActivity(), jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                if (isValid == 0) {
                    //check that the mail is not existed for one of the workers that were inserted now
                    boolean notExisted = true;
                    ServiceProviders sp;
                    if (ProviderGeneralDetailsObj.getInstance().getServiceProviders().size() > 0)
                        for (int i = 0; i < ProviderGeneralDetailsObj.getInstance().getServiceProviders().size(); i++) {
                            sp = ProviderGeneralDetailsObj.getInstance().getServiceProviders().get(i);
                            if (sp.getObjUsers().getNvMail().equals(mail) /*&& i == service_provider_id*/)
                                notExisted = false;
                            //when editing a worker enable inserting the mail that is written there now
                            //not needed, just for editing...
//                                if (!dw.oldMail.equals(""))
//                                    if (mail.equals(dw.oldMail))
//                                        notExisted = true;
                        }
                    if (notExisted) {
                        // if everything is correct, so save the new worker
                        saveCheckedWorker();
                    } else {
                        //if not correct,so do not save
                        Toast.makeText(getActivity(), getResources().getString(R.string.existed_mail_for_worker), Toast.LENGTH_SHORT).show();
                        closeNotSaveWorker();
                    }
                } else {
                    //if not correct,so do not save
                    Toast.makeText(getActivity(), getResources().getString(R.string.exists_mail_error), Toast.LENGTH_SHORT).show();
                    closeNotSaveWorker();
                }
            }
        });
    }

    //save the checked worker
    private void saveCheckedWorker() {
        mWorkersTV.setTextColor(getResources().getColor(R.color.color1));
        mWorkersTV.setError(null);
        dw.parentPositionDay = -1;
        dw.parentPositionDayBreak = -1;
        if (dw.getNewServiceProvider() != null) {
            ProviderGeneralDetailsObj.getInstance().getServiceProviders().add(dw.getNewServiceProvider());
        }
        i++;
        mAllWorkersService.removeView(dw);
        mAllWorkersService.setVisibility(View.GONE);
        String workerName = dw.getName();
        NewWorker new_worker = new NewWorker(getActivity(), null, workerName, dw);
        mAllL.addView(new_worker);
        mAddWorkerR.setVisibility(View.VISIBLE);
        mAllL.setVisibility(View.VISIBLE);
        openWorkers = false;
        mArrorWorkersIV.setVisibility(View.GONE);
        mWorkerDetailsPressed = false;
    }


    private void closeNotSaveWorker() {
        mAllWorkersService.removeView(dw);
        mAllWorkersService.setVisibility(View.GONE);
        mWorkerDetailsPressed = false;
    }

    public void closeNotSaveService() {
        mAllServices.removeView(ser);
        mAllServices.setVisibility(View.GONE);
        mServices = 0;
    }

    public void closeCalender() {
        bus_g_calendarSet.setVisibility(View.GONE);
        mCalendarSet = 0;
    }

    private static boolean allTrue(boolean[] values) {
        for (boolean value : values) {
            if (!value)
                return false;
        }
        return true;
    }

    public static boolean NotAllFalse(boolean[] array) {
        for (boolean b : array) if (b) return true;
        return false;
    }

    //check hour whas selected if valid and return true or false
    private boolean checkHour(boolean isShow) {
        isBreak = false;
        ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().setWorkingHours(new ArrayList<WorkingHours>());
        if (!flagHours)
            correctWorkingHoursList();
        workingHoursHH.clear();
        for (WorkingHours workingHours : workingHoursListHelp)
            workingHoursHH.add(new WorkingHours(workingHours));
        printList("workingHoursListHelp", workingHoursHH);
        if (workingHoursHH.size() > 0) {
            {
                boolean isValid = true;

                for (WorkingHours workingHours : workingHoursHH) {
                    if (workingHours.getNum() != 2) {
                        if (/*hoursAdapter.*/Days[workingHours.getiDayInWeekType() - 1] && DaysBreaks[workingHours.getiDayInWeekType() - 1]) {
                            for (WorkingHours workingHours1 : workingHoursHH) {
                                if (workingHours.getiDayInWeekType() == workingHours1.getiDayInWeekType()
                                        && workingHours.getNum() != workingHours1.getNum() && workingHours1.getNum() != 2) {
                                    if (workingHours.getNum() == 0 && workingHours1.getNum() == 1) {
                                        if (!toBigerFrom(workingHours.getNvToHour(), workingHours1.getNvFromHour())) {
                                            isBreak = true;
                                            isValid = false;
                                        }
                                        if (workingHours.getNvToHour().equals(workingHours1.getNvFromHour())) {
                                            isBreak = true;
                                            isValid = false;
                                        }
                                    }
                                    if (workingHours.getNvToHour().equals(workingHours1.getNvFromHour())) {
                                        if (workingHours.getNum() == 0) {
                                            workingHours.setNvToHour(workingHours1.getNvToHour());
                                        } else {
                                            workingHours.setNvFromHour(workingHours1.getNvFromHour());
                                        }
                                        workingHours1.setNum(2);
                                    }
                                }
                            }
                            if (!toBigerFrom(workingHours.getNvFromHour(), workingHours.getNvToHour())) {
                                isValid = false;
                                isBreak = true;
                            } else {
                                if (valid(workingHours)) {
                                    ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getWorkingHours().add(new WorkingHours(workingHours));
                                } else {
                                    isValid = false;
                                    isBreak = true;
                                }
                            }
                        } else if (Days[workingHours.getiDayInWeekType() - 1] && !DaysBreaks[workingHours.getiDayInWeekType() - 1]) {
                            for (int i = 0; i < workingHoursHH.size(); i++) {
                                if (workingHoursHH.get(i).getiDayInWeekType() == workingHours.getiDayInWeekType() && workingHoursHH.get(i).getNum() != workingHours.getNum() && workingHours.getNum() != 2) {
                                    if (workingHours.getNum() == 0) {
                                        workingHours.setNvToHour(workingHoursHH.get(i).getNvToHour());
                                    } else {
                                        workingHours.setNvFromHour(workingHoursHH.get(i).getNvFromHour());
                                    }
                                    workingHoursHH.get(i).setNum(2);
                                }
                            }
                            if (!toBigerFrom(workingHours.getNvFromHour(), workingHours.getNvToHour())) {
                                isValid = false;
                            } else {
                                if (valid(workingHours)) {
                                    ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getWorkingHours().add(new WorkingHours(workingHours));
                                } else {
                                    isValid = false;
                                }
                            }
                        }
                    }
                }
                printList("objGeneralDetails ", ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getWorkingHours());
                if (isValid) {
                    hours_txt.setError(null);
                    return true;
                } else {
                    if (isShow) {
                        hours_txt.setError("");
                        Toast.makeText(getActivity(), getResources().getString(R.string.enter_hour_period), Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            }
        } else {
            if (isShow) {
                hours_txt.setError("");
                Toast.makeText(getActivity(), getResources().getString(R.string.enter_hour), Toast.LENGTH_LONG).show();
            }
            return false;
        }
    }

    private void correctWorkingHoursList() {
        final boolean[] days = new boolean[6];
        Arrays.fill(days, false);
        boolean flag;
        for (WorkingHours workingHours : workingHoursListHelp) {
            flag = true;
            if (!days[workingHours.getiDayInWeekType() - 1])
                for (WorkingHours workingHours1 : workingHoursListHelp) {
                    if (flag)
                        if (workingHours.getiDayInWeekType() == workingHours1.getiDayInWeekType() && workingHours1.getNum() == 2) {
                            flag = false;
                            days[workingHours.getiDayInWeekType() - 1] = true;
                            if (workingHours.getNum() == 0)
                                workingHours1.setNum(1);
                            else if (workingHours.getNum() == 1)
                                workingHours1.setNum(0);
                        }
                }
        }
    }

    private boolean valid(WorkingHours workingHours) {
        if (workingHours.getNvFromHour().equals(workingHours.getNvToHour()))
            return false;
        if (workingHours.getNum() == 2)
            return false;
        return true;
    }

    private void printList(String tag, List<WorkingHours> workingHoursList) {
//        for (WorkingHours workingHours : workingHoursList) {
//            Log.d(tag, workingHours.toString());
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private boolean toBigerFrom(String nvFromHour, String nvToHour) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date from = sdf.parse(nvFromHour);
            Date to = sdf.parse(nvToHour);
            return from.compareTo(to) != 1;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private List<HelpWorkingHours> sortByDays(List<WorkingHours> workingHoursList) {
        List<HelpWorkingHours> helpWorkingHoursList = new ArrayList<>();
        HelpWorkingHours help;
        int i, j;
        int m = -1, m2 = -1;

        WorkingHours work1 = new WorkingHours(0, "", "");
        help = new HelpWorkingHours(work1, work1);
        helpWorkingHoursList.add(0, help);

        for (i = 1; i < 8; i++) {
            m = -1;
            m2 = -1;
            for (j = 0; j < workingHoursList.size(); j++) {
                if (workingHoursList.get(j).getiDayInWeekType() == i && workingHoursList.get(j).getNum() == 0) {
                    m = j;
                }
                if (workingHoursList.get(j).getiDayInWeekType() == i && workingHoursList.get(j).getNum() == 1) {
                    m2 = j;
                }

            }
            if (m != -1) {
                if (m2 != -1) {
                    help = new HelpWorkingHours(workingHoursList.get(m), workingHoursList.get(m2));
                    helpWorkingHoursList.add(i, help);
                } else {
                    work1 = new WorkingHours(i, "", "");
                    help = new HelpWorkingHours(workingHoursList.get(m), work1);
                    helpWorkingHoursList.add(i, help);
                }
            }
            if (m == -1) {
                work1 = new WorkingHours(i, "", "");
                help = new HelpWorkingHours(work1, work1);
                helpWorkingHoursList.add(i, help);
            }
        }
        return helpWorkingHoursList;
    }


    private List<HelpWorkingHours> sortByDays2(List<WorkingHours> workingHoursList) {
        List<HelpWorkingHours> helpWorkingHoursList = new ArrayList<>();
        HelpWorkingHours help;
        int i, j, a = 0;
        int m[] = {-1, -1};

        WorkingHours work1 = new WorkingHours(0, "", "");
        help = new HelpWorkingHours(work1, work1);
        helpWorkingHoursList.add(0, help);

        for (i = 1; i < 8; i++) {
            m[0] = -1;
            m[1] = -1;
            a = 0;
            for (j = 0; j < workingHoursList.size(); j++) {
                if (workingHoursList.get(j).getiDayInWeekType() == i) {
                    m[a++] = j;
                }
            }
            if (m[0] != -1) {
                if (m[1] != -1) {
                    help = new HelpWorkingHours(workingHoursList.get(m[0]), workingHoursList.get(m[1]));
                    helpWorkingHoursList.add(i, help);
                } else {
                    work1 = new WorkingHours(i, "", "");
                    help = new HelpWorkingHours(workingHoursList.get(m[0]), work1);
                    helpWorkingHoursList.add(i, help);
                }
            } else {
                work1 = new WorkingHours(i, "", "");
                help = new HelpWorkingHours(work1, work1);
                helpWorkingHoursList.add(i, help);
            }
        }
        return helpWorkingHoursList;
    }

    //get array of breaks from the large array
    public List<HelpWorkingHours> getBreaks(List<HelpWorkingHours> workingHours) {
        List<HelpWorkingHours> array = new ArrayList<>();
        HelpWorkingHours help = null;
        WorkingHours workingHours1;
        WorkingHours workingHours2;
        breaksHourses.clear();

        for (int i = 0; i < 7; i++) {
            workingHours2 = new WorkingHours(i, "", "");
            if (!workingHours.get(i).getWorker1().getNvToHour().equals("") && !workingHours.get(i).getWorker2().getNvToHour().equals("") && workingHours.get(i).getWorker1().getiDayInWeekType() == i) {
                workingHours1 = new WorkingHours(i, workingHours.get(i).getWorker1().getNvToHour(), workingHours.get(i).getWorker2().getNvFromHour());

                help = new HelpWorkingHours(workingHours1, workingHours2);
                view.findViewWithTag("day" + (i) + "break").setBackgroundColor(getResources().getColor(R.color.color4));
                ((TextView) view.findViewWithTag("t" + (i) + "break")).setTextColor(getResources().getColor(R.color.color_white));
                if (i > 0)
                    DaysBreaks[i - 1] = true;
                breaksHourses.add(workingHours1);
            } else
                help = new HelpWorkingHours(workingHours2, workingHours2);

            array.add(i, help);
        }

        return array;
    }

    //get array of hours from the large array
    public List<HelpWorkingHours> getHours(List<HelpWorkingHours> workingHours) {
        List<HelpWorkingHours> array = new ArrayList<>();
        HelpWorkingHours help = null;
        WorkingHours workingHours1;
        WorkingHours workingHours2;
        workingHourses.clear();
        workingHoursListHelp.clear();

        for (int i = 0; i < 7; i++) {
            workingHours2 = new WorkingHours(i, "", "");

            if (!workingHours.get(i).getWorker1().getNvToHour().equals("") && !workingHours.get(i).getWorker2().getNvToHour().equals("") && workingHours.get(i).getWorker2().getiDayInWeekType() == i) {
                workingHours1 = new WorkingHours(i, workingHours.get(i).getWorker1().getNvFromHour(), workingHours.get(i).getWorker2().getNvToHour());
                workingHourses.add(workingHours1);
                workingHoursListHelp.add(workingHours1);
                help = new HelpWorkingHours(workingHours1, workingHours2);
                if (i > 0) {
                    view.findViewWithTag("day" + (i)).setBackgroundColor(getResources().getColor(R.color.color4));
                    ((TextView) view.findViewWithTag("t" + (i))).setTextColor(getResources().getColor(R.color.color_white));
                    Days[i - 1] = true;
                }
            }

            if (!workingHours.get(i).getWorker1().getNvToHour().equals("") && workingHours.get(i).getWorker2().getNvToHour().equals("") && workingHours.get(i).getWorker2().getiDayInWeekType() == i) {
                help = new HelpWorkingHours(workingHours.get(i).getWorker1(), workingHours2);
                workingHourses.add(workingHours.get(i).getWorker1());
                workingHoursListHelp.add(workingHours.get(i).getWorker1());

                view.findViewWithTag("day" + (i)).setBackgroundColor(getResources().getColor(R.color.color4));
                ((TextView) view.findViewWithTag("t" + (i))).setTextColor(getResources().getColor(R.color.color_white));
                if (i > 0)
                    Days[i - 1] = true;
            }

            if (workingHours.get(i).getWorker1().getNvToHour().equals("") && !workingHours.get(i).getWorker2().getNvToHour().equals("") && workingHours.get(i).getWorker2().getiDayInWeekType() == i) {
                help = new HelpWorkingHours(workingHours2, workingHours.get(i).getWorker2());
                workingHourses.add(workingHours.get(i).getWorker2());
                workingHoursListHelp.add(workingHours.get(i).getWorker2());
                view.findViewWithTag("day" + (i)).setBackgroundColor(getResources().getColor(R.color.color4));
                ((TextView) view.findViewWithTag("t" + (i))).setTextColor(getResources().getColor(R.color.color_white));
                if (i > 0)
                    Days[i - 1] = true;
            }

            if (workingHours.get(i).getWorker1().getNvToHour().equals("") && workingHours.get(i).getWorker2().getNvToHour().equals("") && workingHours.get(i).getWorker2().getiDayInWeekType() == i) {
                help = new HelpWorkingHours(workingHours2, workingHours2);
            }
            if (workingHours.get(i).getWorker2().getiDayInWeekType() != i)
                help = new HelpWorkingHours(workingHours2, workingHours2);
            array.add(i, help);
        }

        return array;
    }


    //put the text after enter hours;
    //get list of hours and days and sebd the string the text
    public String setTextHour(List<WorkingHours> workingHours, int r, boolean[] Days) {
        boolean fl = false;
        boolean b = false;
        final boolean[] workDays = new boolean[7];
        Arrays.fill(workDays, true);
        final boolean[] breakDays = new boolean[7];
        Arrays.fill(breakDays, true);
        boolean m = false;
        String text_day = "";
        String newString = "";
        String hours = "";
        List<HelpWorkingHours> workingHoursList = new ArrayList<>();
        if (r == 1)
            workingHoursList = sortByDays(workingHours);
        if (r == 2)
            //put break from the realm
            workingHoursList = getBreaks(sortByDays2(workingHours));
        if (r == 3)
            //put hours from the realm
            workingHoursList = getHours(sortByDays2(workingHours));


        for (int i = 1; i <= 6; i++) {
            if (Days[i - 1]) {
                m = false;
                hours = "";
                text_day = "";
                if (workingHoursList.get(i) != null && (workDays[i])) {
                    text_day = text_day + getNameDayFromNumber(i);
                    arrDays[i] = false;

                    m = true;
                    if (workingHoursList.get(i).getWorker1().getNvFromHour() == "" && workingHoursList.get(i).getWorker2().getNvFromHour() == "") {
                        hours = "";
                    } else {
                        //only if not empty
                        if (workingHoursList.get(i).getWorker1() != null && workingHoursList.get(i).getWorker1().getNvFromHour() != ""/*&& Days[i-1]*/)
                            hours = hours + workingHoursList.get(i).getWorker1().getNvFromHour() + "-" + workingHoursList.get(i).getWorker1().getNvToHour();
                        if (workingHoursList.get(i).getWorker2() != null && workingHoursList.get(i).getWorker2().getNvFromHour() != "") {
                            hours = hours + ", ";
                            hours = hours + workingHoursList.get(i).getWorker2().getNvFromHour() + "-" + workingHoursList.get(i).getWorker2().getNvToHour();
                        }
                    }
                    for (int j = i + 1; j <= 6; j++) {
                        if (Days[j - 1]) {

                            if (workDays[j]/*&& Days[j-1]*/)
                                if (workingHoursList.get(i).getWorker1() != null && workingHoursList.get(j).getWorker1() != null)

                                    if (workingHoursList.get(j).getWorker1().getNvFromHour().equals(workingHoursList.get(i).getWorker1().getNvFromHour()))

                                        if (workingHoursList.get(j).getWorker1().getNvToHour().equals(workingHoursList.get(i).getWorker1().getNvToHour())) {
                                            if (workingHoursList.get(i).getWorker2() != null && workingHoursList.get(j).getWorker2() != null) {

                                                if (workingHoursList.get(j).getWorker2().getNvFromHour().equals(workingHoursList.get(i).getWorker2().getNvFromHour())) {

                                                    if (workingHoursList.get(j).getWorker2().getNvToHour().equals(workingHoursList.get(i).getWorker2().getNvToHour())) {
                                                        text_day = text_day + ", " + getNameDayFromNumber(j);
                                                        workDays[j] = false;
                                                    }
                                                }
                                            } else if (workingHoursList.get(i).getWorker2() == null && workingHoursList.get(j).getWorker2() == null) {
                                                text_day = text_day + ", " + getNameDayFromNumber(j);
                                                workDays[j] = false;
                                            }
                                        }
                        }
                    }
                }
                if (m && !hours.equals("")) {
                    if (b)
                        newString += "," + text_day + " - " + hours;
                    else {
                        newString += text_day + " - " + hours;
                        b = true;
                    }
                }

            }
        }
        return newString;
    }


    private String getNameDayFromNumber(int i) {

        switch (i) {
            case 1:
                return getString(R.string.sun);
            case 2:
                return getString(R.string.mon);
            case 3:
                return getString(R.string.tues);
            case 4:
                return getString(R.string.wed);
            case 5:
                return getString(R.string.thur);
            case 6:
                return getString(R.string.fir);
        }
        return "";
    }

    private double getProviderId() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm provider = realm.where(UserRealm.class).findFirst();

        return provider.getUserID();

    }


    @Override
    public void onResume() {
        super.onResume();
        mContinueBtn.setEnabled(true);
    }


    public void focusOnView(final View view) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int vLeft = view.getTop();
                int vRight = view.getBottom();
                int sWidth = view.getWidth();
                if (scrollOfActivity != null)
                    scrollOfActivity.scrollTo(0, ((vLeft + vRight - sWidth) / 2));

            }
        });
    }


    public LinearLayout getScroll() {
        return ll_scroll;
    }

    public boolean[] getField_arr() {
        return field_arr;
    }

    public boolean SaveDetailsWorker() {
        if (newDw != null) {
            dw = newDw;
        }
        mArrowAddWorkerIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_white));
        mArrorWorkersIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_black));

        if (dw.checkAllFields(i)) {

            mWorkersTV.setTextColor(getResources().getColor(R.color.color1));
            mWorkersTV.setError(null);
            if (dw.getNewServiceProvider() != null) {
                ProviderGeneralDetailsObj.getInstance().getServiceProviders().add(dw.getNewServiceProvider());
            }
            i++;
            mAllWorkersService.removeView(dw);
            mAllWorkersService.setVisibility(View.GONE);
            String workerName = dw.getName();
            NewWorker new_worker = new NewWorker(getActivity(), null, workerName, dw);
            mAllL.addView(new_worker);
            mAddWorkerR.setVisibility(View.VISIBLE);
            mAllL.setVisibility(View.VISIBLE);
            openWorkers = false;
            mArrorWorkersIV.setVisibility(View.GONE);
            mWorkerDetailsPressed = false;
            return true;
        } else {
            closeNotSaveWorker();
            return true;
        }

    }
}




