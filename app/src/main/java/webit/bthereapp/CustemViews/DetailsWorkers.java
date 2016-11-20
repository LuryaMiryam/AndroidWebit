package webit.bthereapp.CustemViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.CustemViews.Fonts.CustomLightEditText;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.HelpWorkingHours;
import webit.bthereapp.Entities.ProviderGeneralDetailsObj;
import webit.bthereapp.Entities.ServiceProviders;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Entities.WorkingHours;
import webit.bthereapp.Intreface.AddOnClickListener;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Utils.Utils;
import webit.bthereapp.Utils.WorkingHoursUtil;

@TargetApi(Build.VERSION_CODES.M)
public class DetailsWorkers extends LinearLayout implements AdapterView.OnItemClickListener, View.OnFocusChangeListener, View.OnClickListener {

    private LinearLayout day1, day2, day3, day4, day5, day6;
    public LinearLayout workHours;
    private LinearLayout day1break, day2break, day3break, day4break, day5break, day6break;
    private ImageView imageV, imageVbreak;
    private boolean isAllHours = false, isAllHoursBreak = false;
    private boolean[] Days = new boolean[6], DaysBreaks = new boolean[6];
    private CustomViewTimePicker viewTimePicker, cvTimePickerbreak;
    private ArrayList<WorkingHours> workingHourses, breaksHourses;
    public int parentPositionDay = -1, parentPositionDayBreak = -1;
    private ServiceProviders serviceProvider;
    private boolean isBreak = false;
    private YesOrNo_Black sameHoursYN;
    private LinearLayout mDairyL2;
    public boolean phone_error_ex = false, mail_error_ex = false;
    private View view;
    public LinearLayout mHoursL, mBreaksL;
    private boolean flagHours = true;
    private List<WorkingHours> workingHoursList = new ArrayList<>(), workingHoursHH = new ArrayList<>(), workingHoursSave = new ArrayList<>();
    public List<WorkingHours> workingHoursListHelp = new ArrayList<>();
    private List<WorkingHours> saveList;
    private List<WorkingHours> mRaceHoursList = new ArrayList<>();
    public int service_provider_id = -1;
    public int mHours = 0, mBreaks = 0;
    public boolean openPersonalDaily = false, sameHours = true;
    private Context mContext;
    private Button mBreaksBtn;
    private CustomTimePicker mWorkingHoursTimePicker, mRaceHoursTimePicker;
    private int selectWorkingHoursDay = 0, selectRaceHoursDay = 0;
    private boolean required = true, hours = true, emptyHours = true;
    public boolean validate = true;
    private boolean f_name = true, f_mail = true;
    public boolean f_phone = true;
    private boolean workingHoursFocus = false, raceHoursFocus = false;
    private RelativeLayout details_worker_hours;
    private CustomLightEditText workerfName, workerlName;
    public CustomLightEditText workerPhone,workerMail;
    public List<HelpWorkingHours> wHp = new ArrayList<>();
    public String oldPhoneNumber = "";
    public String oldMail = "";
    boolean checkingAllWorkerFields=false;

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public DetailsWorkers(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(final Context context, AttributeSet attrs) {
        view = View.inflate(context, R.layout.custem_details_workers, this);
        this.mContext = context;
        details_worker_hours = (RelativeLayout) view.findViewById(R.id.details_worker_hours);
        workerfName = (CustomLightEditText) view.findViewById(R.id.work_details_fname);
        workerlName = (CustomLightEditText) view.findViewById(R.id.work_details_lname);

        mDairyL2 = (LinearLayout) view.findViewById(R.id.all_hours);
        imageV = (ImageView) view.findViewById(R.id.alldays);
        imageV.setOnClickListener(this);
        imageVbreak = (ImageView) view.findViewById(R.id.alldaysbreak);
        imageVbreak.setOnClickListener(this);
        workHours = (LinearLayout) view.findViewById(R.id.workHours);

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
        viewTimePicker = (CustomViewTimePicker) view.findViewById(R.id.cvTimePicker);
        cvTimePickerbreak = (CustomViewTimePicker) view.findViewById(R.id.cvTimePickerbreak);
        mBreaksBtn = (Button) view.findViewById(R.id.addBreaks);
        mBreaksBtn.setOnClickListener(this);
        mBreaksL = (LinearLayout) view.findViewById(R.id.all_hours_break);
        workerPhone = (CustomLightEditText) view.findViewById(R.id.work_details_phone);
        workerPhone.setFilters(setInputFilter(10, getResources().getString(R.string.too_long_phone)));
        workerMail = (CustomLightEditText) view.findViewById(R.id.work_details_mail);

        openPersonalDaily = true;
        details_worker_hours.setVisibility(VISIBLE);
        sameHoursYN = (YesOrNo_Black) view.findViewById(R.id.w_xv_2_w);
        sameHoursYN.change_status(true);
        //if the user doesnt work in the bussines hours
        sameHoursYN.setAddOnClickListenerX(new AddOnClickListener() {
            @Override
            public void addOnClick() {
//                mail_validity();
//                if (!(workerMail.getText().equals("")))
//                    mail_validity_server();
                hideKeyBoard(view);
                mDairyL2.setVisibility(View.VISIBLE);
                mBreaksL.setVisibility(View.GONE);
                mBreaks = 0;
                workHours.setVisibility(View.VISIBLE);
                workingHoursList.clear();
                sameHours = false;
            }
        });
        //if the user work in the bussines hours
        sameHoursYN.setAddOnClickListenerV(new AddOnClickListener() {
            @Override
            public void addOnClick() {
//                mail_validity();
//                if (!(workerMail.getText().equals("")))
//                    mail_validity_server();
                hideKeyBoard(view);
                mDairyL2.setVisibility(View.GONE);
                sameHours = true;
            }
        });

        workerfName.setOnFocusChangeListener(this);
        workerlName.setOnFocusChangeListener(this);
        workerPhone.setOnFocusChangeListener(this);
        workerMail.setOnFocusChangeListener(this);
        Arrays.fill(Days, false);
        Arrays.fill(DaysBreaks, false);
        workingHourses = new ArrayList<>();
        breaksHourses = new ArrayList<>();

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
    }

    //save the hours work from the date picker
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

    //save the hours breaks from the date picker
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


    //save the hours
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
        if (parentPositionDay == -1 || checkHourOfWorker(isShow)) {
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

    //remove hours day work
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
            if (checkHourOfWorker(true)) {
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
    }

    //save the breaks
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
        if (parentPositionDayBreak == -1 || checkHourOfWorker(isShow)) {
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

    //remove break of day selected
    public void RemoveDayBreak(View v, int position) {
        if (v != null) {
            v.setBackgroundColor(getResources().getColor(R.color.light_gray_8));
            ((TextView) v.findViewWithTag("t" + (position + 1) + "break")).setTextColor(getResources().getColor(R.color.black));
        }
        DaysBreaks[position] = false;
        if (checkHourOfWorker(true)) {
            boolean AllFalse = true;
            for (int i = 0; i <= 5; i++) {
                if (DaysBreaks[i]) {
                    AllFalse = false;
                }
            }
            if (AllFalse)
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
            if (b[workingHours.getiDayInWeekType() - 1] && (!wh1.getNvFromHour().equals(workingHours.getNvFromHour()) || !wh1.getNvToHour().equals(workingHours.getNvToHour())))
                return false;
        }
        return true;
    }

    //save the previous state of the worker
    public void save_previous(ServiceProviders mFoundWorker) {
        workerfName.setText(mFoundWorker.getObjUsers().getNvFirstName());
        workerlName.setText(mFoundWorker.getObjUsers().getNvLastName());
        workerPhone.setText(mFoundWorker.getObjUsers().getNvPhone());
        workerMail.setText(mFoundWorker.getObjUsers().getNvMail());
        int isopenPersonalDaily = mFoundWorker.getObjUsers().getiUserStatusType();

        if (isopenPersonalDaily == 26) {
        } else {
//            v_clicked;
            sameHoursYN.change_status_no_selected();
            mDairyL2.setVisibility(View.GONE);
            details_worker_hours.setVisibility(VISIBLE);
            workingHoursList.clear();

            boolean isbSameWH = mFoundWorker.isbSameWH();
            if (isbSameWH == true) {
//            v_clicked;
                mDairyL2.setVisibility(View.GONE);

                sameHoursYN.change_status(true);

//          hours_empty;
            } else {

//            x_clicked;
                mDairyL2.setVisibility(View.VISIBLE);

                sameHoursYN.change_status(false);
                Arrays.fill(Days, false);
                Arrays.fill(DaysBreaks, false);
                boolean noBreaks;
                for (WorkingHours workingHours : mFoundWorker.getObjWorkingHours()) {
                    noBreaks = true;
                    Days[workingHours.getiDayInWeekType() - 1] = true;
                    if (workingHours.getNum() == 1)
                        DaysBreaks[workingHours.getiDayInWeekType() - 1] = true;
                    for (WorkingHours workingHours1 : mFoundWorker.getObjWorkingHours()) {
                        if (workingHours.getNum() == 1) {
                            noBreaks = false;
                        } else if (workingHours.getiDayInWeekType() == workingHours1.getiDayInWeekType() && workingHours.getNum() == 0 && workingHours1.getNum() == 1) {
                            noBreaks = false;
                        }
                    }
                    if (noBreaks) {
                        wHp = getHours(sortByDays2(mFoundWorker.getObjWorkingHours()));

                    } else {
                        wHp = getBreaks(sortByDays2(mFoundWorker.getObjWorkingHours()));
                        wHp = getHours(sortByDays2(mFoundWorker.getObjWorkingHours()));

                    }
                }
            }

        }
    }

    public void setTime(TimeText timeTextR) {
        String day = (String) timeTextR.getTag();
        setData(day, timeTextR.getText(), 0, 1);

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

    private boolean valid(WorkingHours workingHours) {
        if (workingHours.getNvFromHour().equals(workingHours.getNvToHour()))
            return false;
        if (workingHours.getNum() == 2)
            return false;
        return true;
    }

    //check hours if valid
    private boolean checkHourOfWorker(boolean isShow) {
        isBreak = false;
        workingHoursSave = new ArrayList<WorkingHours>();
        if (!flagHours)
            correctWorkingHoursList();
        workingHoursHH.clear();
        if (saveList != null)
            for (WorkingHours workingHours : saveList)
                workingHoursHH.add(new WorkingHours(workingHours));
        else
            for (WorkingHours workingHours : workingHoursListHelp)
                workingHoursHH.add(new WorkingHours(workingHours));
//        printList("workingHoursListHelp", workingHoursHH);
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
                                            isValid = false;
                                            isBreak = true;
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
                                    workingHoursSave.add((new WorkingHours(workingHours)));
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
                                    workingHoursSave.add((new WorkingHours(workingHours)));
                                } else {
                                    isValid = false;
                                }
                            }
                        }
                    }
                }
//                printList("break", workingHoursSave);
//                printList("breakh", workingHoursHH);
                if (isValid) {
                    return true;
                } else {
                    if (isShow)
                        Toast.makeText(mContext, getResources().getString(R.string.enter_hour_period), Toast.LENGTH_LONG).show();
                    return false;
                }
            }

        } else {
//            printList("objGeneralDetails ", ProviderGeneralDetailsObj.getInstance().getWorkingHours());
            if (isShow)
                Toast.makeText(mContext, getResources().getString(R.string.enter_hour), Toast.LENGTH_LONG).show();
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

    //put the previous worker details
    public void put_workers(ServiceProviders serviceProvider) {
        workerfName.setText(serviceProvider.getObjUsers().getNvFirstName());
        workerlName.setText(serviceProvider.getObjUsers().getNvLastName());
        workerPhone.setText(serviceProvider.getObjUsers().getNvPhone());
        workerMail.setText(serviceProvider.getObjUsers().getNvMail());
        int isopenPersonalDaily = serviceProvider.getObjUsers().getiUserStatusType();
        if (isopenPersonalDaily == 26) {

        } else {
            sameHoursYN.change_status_no_selected();
            mDairyL2.setVisibility(View.GONE);
            details_worker_hours.setVisibility(VISIBLE);
            workingHoursList.clear();
            boolean isbSameWH = serviceProvider.isbSameWH();
            if (isbSameWH == true) {
                mDairyL2.setVisibility(View.GONE);
                sameHoursYN.change_status(true);

            } else {
                mDairyL2.setVisibility(View.VISIBLE);

                sameHoursYN.change_status(false);
                Arrays.fill(Days, false);
                Arrays.fill(DaysBreaks, false);
                boolean noBreaks;

                for (WorkingHours workingHours : serviceProvider.getObjWorkingHours()) {
                    noBreaks = true;
                    Days[workingHours.getiDayInWeekType() - 1] = true;
                    if (workingHours.getNum() == 1)
                        DaysBreaks[workingHours.getiDayInWeekType() - 1] = true;
                    for (WorkingHours workingHours1 : serviceProvider.getObjWorkingHours()) {
                        if (workingHours.getNum() == 1) {
                            noBreaks = false;
                        } else if (workingHours.getiDayInWeekType() == workingHours1.getiDayInWeekType() && workingHours.getNum() == 0 && workingHours1.getNum() == 1) {
                            noBreaks = false;
                        }
                    }
                    if (noBreaks) {
                        wHp = getHours(sortByDays2(serviceProvider.getObjWorkingHours()));


                    } else {
                        wHp = getBreaks(sortByDays2(serviceProvider.getObjWorkingHours()));
                        wHp = getHours(sortByDays2(serviceProvider.getObjWorkingHours()));

                    }
                }

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public ServiceProviders getNewServiceProvider() {
        //// TODO: 04/04/2016 add validation

        if (raceHoursFocus)
            if (!mRaceHoursTimePicker.getFromHour().equals(mRaceHoursTimePicker.getToHour()) &&
                    WorkingHoursUtil.hoursValidation(getContext(), selectRaceHoursDay, mRaceHoursTimePicker)) {
                mRaceHoursList = WorkingHoursUtil.saveHours(mRaceHoursList, mRaceHoursTimePicker, selectRaceHoursDay);
                raceHoursFocus = false;
            }

        if (workingHoursFocus)
            if (!mWorkingHoursTimePicker.getFromHour().equals(mWorkingHoursTimePicker.getToHour()) &&
                    WorkingHoursUtil.hoursValidation(getContext(), selectWorkingHoursDay, mWorkingHoursTimePicker)) {
                workingHoursList = WorkingHoursUtil.saveHours(workingHoursList, mWorkingHoursTimePicker, selectWorkingHoursDay);
                workingHoursFocus = false;
            }
        serviceProvider = new ServiceProviders();
        UserObj userServiceProvider = new UserObj();
        userServiceProvider.setNvUserName(workerMail.getText().toString());
        userServiceProvider.setNvFirstName(workerfName.getText().toString());
        userServiceProvider.setNvLastName(workerlName.getText().toString());
        userServiceProvider.setNvMail(workerMail.getText().toString());
        userServiceProvider.setNvPhone(workerPhone.getText().toString());
        userServiceProvider.setNvPassword("");
        userServiceProvider.setNvVerification("");
        userServiceProvider.setbAutomaticUpdateApproval(false);
        userServiceProvider.setbDataDownloadApproval(false);
        userServiceProvider.setbTermOfUseApproval(false);
        userServiceProvider.setbAdvertisingApproval(false);
        userServiceProvider.setbIsGoogleCalendarSync(false);
        userServiceProvider.setNvImage("");
        double providerId = getProviderId();
        userServiceProvider.setiCreatedByUserId((int) providerId);
        userServiceProvider.setiLastModifyUserId((int) providerId);
        userServiceProvider.setiSysRowStatus(1);

        if (openPersonalDaily) {
            userServiceProvider.setiUserStatusType(25);
            if (sameHours) {
                serviceProvider.setbSameWH(true);
                serviceProvider.setObjWorkingHours(null);
            } else {
                serviceProvider.setbSameWH(false);
                //TODO required validation to working hours
                serviceProvider.setObjWorkingHours(WorkingHoursUtil.getMainWorkingHoursList(workingHoursList, mRaceHoursList));
            }
        } else {
            userServiceProvider.setiUserStatusType(26);
            serviceProvider.setbSameWH(false);
            serviceProvider.setObjWorkingHours(null);
        }
        serviceProvider.setObjUsers(userServiceProvider);
        serviceProvider.setObjWorkingHours(workingHoursSave);
        return serviceProvider;
    }

    private double getProviderId() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm provider = realm.where(UserRealm.class).findFirst();
        return provider.getUserID();
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

    private Boolean check(CustomLightEditText s) {

        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s.getText().toString());
        return matcher.find();

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

    public String get_phone() {
        return workerPhone.getText().toString();
    }

    //checks all the worker details (except the server validation), when something not correct it leaves the function, returns false
    public boolean checkAllFieldsNew(int i) {
        checkingAllWorkerFields=true;
        service_provider_id = i;
        emptyHours = true;
        hours = true;
        required = true;
        //check if the first name is valid
        if (!checkRequiredField(workerfName)) {
        Toast.makeText(mContext,getResources().getString(R.string.required_fields),Toast.LENGTH_SHORT).show();
        return false;
        }
            if (workerfName.getText() == null) {
                Toast.makeText(mContext,getResources().getString(R.string.required_fields),Toast.LENGTH_SHORT).show();
                 return false;}
            if ((!checkLengthWithoutSpaces(workerfName)) || (!(check(workerfName)))) {
                Toast.makeText(mContext,getResources().getString(R.string.first_name_not_correct),Toast.LENGTH_SHORT).show();
                return  false;
            }

      //check if the last name is valid
        if (!checkRequiredField(workerlName)){
            Toast.makeText(mContext,getResources().getString(R.string.required_fields),Toast.LENGTH_SHORT).show();
        return false;}
            if (workerlName.getText() == null){
                Toast.makeText(mContext,getResources().getString(R.string.required_fields),Toast.LENGTH_SHORT).show();
            return false;}
            if ((!checkLengthWithoutSpaces(workerlName)) || (!(check(workerlName)))) {
                Toast.makeText(mContext,getResources().getString(R.string.last_name_not_correct),Toast.LENGTH_SHORT).show();
                return false;
            }

      //check if the phone is valid
        if (!checkRequiredField(workerPhone)){
            Toast.makeText(mContext,getResources().getString(R.string.required_fields),Toast.LENGTH_SHORT).show();
            return false;}

        if(!checkPhone()) {
            Toast.makeText(mContext,getResources().getString(R.string.phone_not_correct),Toast.LENGTH_SHORT).show();
            return false;}

        //check if the mail is valid
        if (!checkRequiredField(workerMail)) {
            Toast.makeText(mContext, getResources().getString(R.string.required_fields), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!checkMail()){
            Toast.makeText(mContext,getResources().getString(R.string.mail_not_correct),Toast.LENGTH_SHORT).show();
            return false;}

        if (sameHoursYN.getYes_no_state() == -1)
            return false;
        if (sameHoursYN.getYes_no_state() == 0) {
            if (!BusinessGeneralData.NotAllFalse(Days)) {
                Toast.makeText(mContext, getResources().getString(R.string.enter_hour), Toast.LENGTH_LONG).show();
                return false;
            }
                saveHours(0, null, false);
                if (!BusinessGeneralData.NotAllFalse(Days))
                    saveHoursBreak(0, null, false);
                if (!checkHourOfWorker(false)) {
                    saveHours(0, null, false);
                    saveHoursBreak(0, null, false);
                }
                if (!checkHourOfWorker(false)) {
                    Toast.makeText(mContext, getResources().getString(R.string.hours_not_correct), Toast.LENGTH_LONG).show();
                    return false;
                }

        }
        flagHours = false;
            return true;
    }

     public boolean checkPhone() {
    String regexStr = "^[0-9]{10}";
    final String phone = workerPhone.getText().toString();
    if (phone.trim().matches(regexStr) && phone.length() > 0 && phone.startsWith("05"))
    return true;
    return false;
}

    public boolean checkMail(){
        final String mail = workerMail.getText().toString();
        //mail validate
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(mail);
        if (m.matches())
            return  true;
        return false;
    }

    //check if the user entered valid phone, and its not in the server for leaving the focus
    public void phone_validity() {
        phone_error_ex = false;
        String regexStr = "^[0-9]{10}";
        final String phone = workerPhone.getText().toString();
        if (phone.trim().matches(regexStr) && phone.length() > 0 && phone.startsWith("05")) {
            //checking  that the worker's phone is not exists as a giving service or supplier
            String jsonString = "{\"nvPhone\":\"" + phone + "\"}";
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MainBL.CheckProviderExistByPhone(mContext, jsonObject, new IExecutable<Double>() {
                @Override
                public void onExecute(Double isValid) {
                    if (isValid == 0) {
                        //check that the phone number is not existed for one of the workers that were inserted now
                        boolean notExisted = true;
                        ServiceProviders sp;
                        if (ProviderGeneralDetailsObj.getInstance().getServiceProviders().size() > 0)
                            for (int i = 0; i < ProviderGeneralDetailsObj.getInstance().getServiceProviders().size(); i++) {
                                sp = ProviderGeneralDetailsObj.getInstance().getServiceProviders().get(i);
                                if (sp.getObjUsers().getNvPhone().equals(phone)/* && i == service_provider_id*/)
                                    notExisted = false;
                            }
                        if(!notExisted)
                            //when editing a worker enable inserting the phone that is written there now
                            if (!oldPhoneNumber.equals(""))
                                if (phone.equals(oldPhoneNumber))
                                    notExisted = true;
                        if (notExisted) {
                            f_phone = true;
                            workerPhone.setError(null);
                        } else {
                            phone_error_ex = true;
                            f_phone = false;
                            workerPhone.setError(getResources().getString(R.string.existed_phone_for_worker));
                        }
                    } else {
                        phone_error_ex = true;
                        f_phone = false;
                        workerPhone.setError(getResources().getString(R.string.exists_phone_error));
                    }
                }
            });
        } else {
            f_phone = false;
            workerPhone.setError(getResources().getString(R.string.phoneerror));
        }

    }

    //check if the user entered valid mail, and its not in the server for leaving the focus
    public void mail_validity_server() {
        final String mail = workerMail.getText().toString();
        //mail validate
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(mail);
        if (m.matches()) {
            //checking  that the worker's mail is not exists as a giving service or supplier
            String jsonString = "{\"nvMail\":\"" + mail + "\"}";
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MainBL.CheckProviderExistByMail(mContext, jsonObject, new IExecutable<Double>() {
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
                            }
                        if(!notExisted) {
                            //when editing a worker enable inserting the mail that is written there now
                            if (!oldMail.equals(""))
                                if (mail.equals(oldMail))
                                    notExisted = true;
                        }
                        if (notExisted) {
                            f_mail = true;
                            workerMail.setError(null);
                        } else {
                            mail_error_ex = true;
                            f_mail = false;
                            workerMail.setError(getResources().getString(R.string.existed_mail_for_worker));
                        }
                    } else {
                        mail_error_ex = true;
                        f_mail = false;
                        workerMail.setError(getResources().getString(R.string.exists_mail_error));
                    }
                }
            });
        } else {
            f_mail = false;
            workerMail.setError(getResources().getString(R.string.mail_not_correct));
        }
    }

    // check validation after leaving the focus of the field
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        service_provider_id = -1;
        TextView textView = (TextView) v;
        if (!hasFocus) {
            if (textView.getText().length() > 0) {
                switch (v.getId()) {
                    //first name
                    case R.id.work_details_fname:
                        if ((!checkLengthWithoutSpaces(workerfName)) || (!(check(workerfName)))) {
                            validate = false;
                            workerfName.setError(getResources().getString(R.string.fnameerror));
                        } else {
                            validate = true;
                            workerfName.setError(null);
                        }
                        break;
                    //last name
                    case R.id.work_details_lname:
                        if ((!checkLengthWithoutSpaces(workerlName)) || (!(check(workerlName)))) {
                            validate = false;
                            workerlName.setError(getResources().getString(R.string.lnameerror));
                        } else {
                            validate = true;
                            workerlName.setError(null);
                        }
                        break;
                 //phone
                    case R.id.work_details_phone:
                        phone_validity();
                        break;
                    //mail
                    case R.id.work_details_mail: {
                        if (!(workerMail.getText().equals(""))){
                            //if was not already checked at checking all the fields
                            if(!checkingAllWorkerFields) {
                                mail_validity_server();
                            }
                            else
                                checkingAllWorkerFields=false;
                        }
                    }
                }
            }
        }

    }


    public String getName() {
        return workerfName.getText().toString() + " " + workerlName.getText().toString();

    }

    public String getMail() {
        return workerMail.getText().toString();

    }

    public void error_empty() {
        Toast.makeText(getContext(), R.string.empty_fields, Toast.LENGTH_LONG).show();

    }

    private void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private InputFilter[] setInputFilter(int max, final String message) {
        InputFilter[] inputFilter = new InputFilter[]{
                new InputFilter.LengthFilter(max) {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        CharSequence res = super.filter(source, start, end, dest, dstart, dend);
                        if (res != null) {
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
                        return res;
                    }
                }
        };
        return inputFilter;
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm;
        switch (v.getId()) {
            case R.id.addBreaks:
                if (!checkHourOfWorker(false)) {
                    saveHours(0, null, false);
                }
                if (checkHourOfWorker(true) || isBreak) {
                    workHours.setVisibility(View.GONE);
                    saveHours(0, null, false);
                    if (mBreaks == 0) {
                        mBreaksL.setVisibility(View.VISIBLE);
                        mBreaks = 1;
                    } else {
                        mBreaksL.setVisibility(View.GONE);
                        mBreaks = 0;
                    }
                }
                break;
            case R.id.hours:
                imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (mHours == 0) {
                    mHoursL.setVisibility(View.VISIBLE);
                    mHours = 1;
                } else {
                    checkAndSaveHours();
                }
                break;
            case R.id.alldays:
                if (!isAllHours) {
                    saveHours(0, null, true);
                    if (isAllEquals(workingHourses, Days)) {
                        if (toBigerFrom(viewTimePicker.from, viewTimePicker.to) && !viewTimePicker.from.equals(viewTimePicker.to)) {
                            imageV.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                            isAllHours = true;
                            for (int i = 0; i < 6; i++) {
                                view.findViewWithTag("day" + (i + 1)).setBackgroundColor(getResources().getColor(R.color.color4));
                                ((TextView) view.findViewWithTag("t" + (i + 1))).setTextColor(getResources().getColor(R.color.color_white));
                            }
                            Arrays.fill(Days, true);
                            SaveAllDays();
                        } else {
                            Toast.makeText(mContext, getResources().getString(R.string.enter_hour_period), Toast.LENGTH_LONG).show();
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

                }
                break;
            case R.id.alldaysbreak:
                if (!isAllHoursBreak) {
                    saveHoursBreak(0, null, true);
                    if (isAllEquals(breaksHourses, DaysBreaks)) {
                        if (allTrue(Days)) {
                            if (checkHourOfWorker(false)) {
                                imageVbreak.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                                isAllHoursBreak = true;
                                for (int i = 0; i < 6; i++) {
                                    view.findViewWithTag("day" + (i + 1) + "break").setBackgroundColor(getResources().getColor(R.color.color4));
                                    ((TextView) view.findViewWithTag("t" + (i + 1) + "break")).setTextColor(getResources().getColor(R.color.color_white));
                                }
                                Arrays.fill(DaysBreaks, true);
                                SaveAllDaysbreak();

                            } else {
                                Toast.makeText(mContext, getResources().getString(R.string.enter_hour_period), Toast.LENGTH_LONG).show();
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
                }
                break;
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

    private boolean checkAndSaveHours() {
        if (!checkHourOfWorker(false)) {
            saveHours(0, null, false);
            saveHoursBreak(0, null, false);
        }
        if (checkHourOfWorker(true)) {
            saveHours(0, null, false);
            if (breaksHourses != null && breaksHourses.size() > 0) {
                saveHoursBreak(0, null, false);
            }
        }
        workHours.setVisibility(View.VISIBLE);
        mHoursL.setVisibility(View.GONE);
        mBreaksL.setVisibility(View.GONE);
        mHours = 0;
        mBreaks = 0;
        return true;
    }


    private static boolean allTrue(boolean[] values) {
        for (boolean value : values) {
            if (!value)
                return false;
        }
        return true;
    }

    //check all the fields if valid
    // this function is not used
    public boolean checkAllFields(int i) {
        service_provider_id = i;
        emptyHours = true;
        hours = true;
        required = true;
        validate = true;
        //check if the first name is valid
        if (!checkRequiredField(workerfName)){
            required = false;
            return false;
        }
        else {
            if (workerfName.getText() == null)
                validate = false;
            if ((!checkLengthWithoutSpaces(workerfName)) || (!(check(workerfName)))) {
                workerfName.setError(getResources().getString(R.string.fnameerror));
                validate = false;
            } else {
                workerfName.setError(null);
            }
        }
//check if the last name is valid
        if (!checkRequiredField(workerlName))
            required = false;
        else {
            if (workerlName.getText() == null)
                validate = false;
            if ((!checkLengthWithoutSpaces(workerlName)) || (!(check(workerlName)))) {
                workerlName.setError(getResources().getString(R.string.lnameerror));
                validate = false;
            }
        }
//check if the mail is valid
        if (!checkRequiredField(workerPhone))
            required = false;
        else {
            if (!(workerPhone.getText().equals(""))) {
                phone_validity();
            }
        }
        if (!checkRequiredField(workerMail))
            required = false;
        else {
            if (!(workerMail.getText().equals(""))) {
                checkingAllWorkerFields=true;
                mail_validity_server();
            }
        }
        if (!f_name || !f_phone || !f_mail)
            validate = false;
        if (sameHoursYN.getYes_no_state() == -1)
            required = false;
        if (sameHoursYN.getYes_no_state() == 0) {
            if (!BusinessGeneralData.NotAllFalse(Days)) {
                emptyHours = false;
            } else {
                saveHours(0, null, false);
                if (!BusinessGeneralData.NotAllFalse(Days))
                    saveHoursBreak(0, null, false);
                if (!checkHourOfWorker(false)) {
                    saveHours(0, null, false);
                    saveHoursBreak(0, null, false);
                }
                if (!checkHourOfWorker(false)) {
                    hours = false;
                }
            }
        }
        flagHours = false;
        if (required && hours && emptyHours && phone_error_ex) {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.exists_phone_error) + workerPhone.getText().toString(), Toast.LENGTH_SHORT).show();
        }

        if (validate && required && hours && emptyHours) {
            return true;
        }
        return false;
    }
}