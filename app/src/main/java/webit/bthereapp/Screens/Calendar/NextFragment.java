package webit.bthereapp.Screens.Calendar;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import webit.bthereapp.Utils.CalendarUtil;
import webit.bthereapp.Utils.Utils;


public class NextFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view = null;
    public Calendar dayForWeek;
    CustomLightButton mMonthB, mDayB, mWeekB, mListB, mPickedB;


    public void setDayForWeek(Calendar dayForWeek) {
        this.dayForWeek = dayForWeek;
    }

    public NextFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NextFragment newInstance(String param1, String param2) {
        NextFragment fragment = new NextFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static void removeInstance() {
        instance = null;
    }

    public static NextFragment instance;

    public static NextFragment getInstance() {
        if (instance == null)
            instance = new NextFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
                view = inflater.inflate(R.layout.fragment_next, container, false);
                mMonthB = (CustomLightButton) view.findViewById(R.id.month);
                mDayB = (CustomLightButton) view.findViewById(R.id.day);
                mWeekB = (CustomLightButton) view.findViewById(R.id.week);
                mListB = (CustomLightButton) view.findViewById(R.id.list);
                ShowDateFragment.removeInstance();
                ShowWeekFragment.removeInstance();
                ShowDayFragment.removeInstance();

                Realm realm = Utils.getRealmInstance(getContext());
                UserRealm userRealm = realm.where(UserRealm.class).findFirst();
                if (userRealm != null) {
                    if (userRealm.isbIsGoogleCalendarSync()) {
                        CalendarUtil.mEyeShow = true;
                    } else {
                        CalendarUtil.mEyeShow = false;
                    }
                    if (userRealm.getiCalendarViewType() != 0) {
                        if (userRealm.getiCalendarViewType() == 46) {
                            ShowDateFragment.getInstance().put_fr(this);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDateFragment.getInstance()).commit();
                            changePressed(mMonthB);
                            if (mPickedB != null) {
                                changeBack(mPickedB);
                            }
                            mPickedB = mMonthB;
                        } else {
                            if (userRealm.getiCalendarViewType() == 45) {
                                ShowWeekFragment.getInstance().put_cal_and_fr(Calendar.getInstance(), this);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowWeekFragment.getInstance()).commit();
                                changePressed(mWeekB);
                                if (mPickedB != null)
                                    changeBack(mPickedB);
                                mPickedB = mWeekB;
                            } else if (userRealm.getiCalendarViewType() == 44) {
                                ShowDayFragment.getInstance().put_fr(this);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDayFragment.getInstance()).commit();
                                changePressed(mDayB);
                                if (mPickedB != null)
                                    changeBack(mPickedB);
                                mPickedB = mDayB;
                            }
                        }

                    } else {
                        changePressed(mMonthB);
                        if (mPickedB != null) {
                            changeBack(mPickedB);
                        }
                        mPickedB = mMonthB;

                        ShowDateFragment.getInstance().put_fr(this);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDateFragment.getInstance()).commit();
                    }
                }

                mDayB.setOnClickListener(this);
                mWeekB.setOnClickListener(this);
                mListB.setOnClickListener(this);
                mMonthB.setOnClickListener(this);
        }

        if (getActivity() instanceof NavigetionLayout)
            ((NavigetionLayout) getActivity()).from = 0;
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.day:
                if (mPickedB != mDayB) {
                    changePressed(mDayB);
                    if (mPickedB != null) {
                        changeBack(mPickedB);
                    }
                    mPickedB = mDayB;
                    ShowDayFragment.removeInstance();
                    ShowDayFragment.getInstance().put_fr(this);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDayFragment.getInstance()).commit();
                }
                break;
            case R.id.week:
                boolean b = false;
                if (mPickedB != mWeekB) {
                    if (mPickedB == mDayB) {
                        // if the daywas pressed before, so get the date and display it in the displayed week
                        if (dayForWeek != null) {
                            b = true;
                        }
                    }
                    changePressed(mWeekB);
                    if (mPickedB != null) {
                        changeBack(mPickedB);
                    }
                    mPickedB = mWeekB;
                    if (!b)
                        dayForWeek = Calendar.getInstance();
                    ShowWeekFragment.removeInstance();
                    ShowWeekFragment.getInstance().put_cal_and_fr(dayForWeek, this);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowWeekFragment.getInstance()).commit();
                }
                break;
            case R.id.month:
                if (mPickedB != mMonthB) {
                    changePressed(mMonthB);
                    if (mPickedB != null) {
                        changeBack(mPickedB);
                    }
                    mPickedB = mMonthB;
                    ShowDateFragment.removeInstance();
                    ShowDateFragment.getInstance().put_fr(this);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDateFragment.getInstance()).commit();
                }
                break;
            case R.id.list:
                if (mPickedB != mListB) {
                    changePressed(mListB);
                    if (mPickedB != null) {
                        changeBack(mPickedB);
                    }
                    mPickedB = mListB;
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, new ShowListFragment()).commit();
                }
                break;
        }
    }


    public void setDisplayDay(Calendar calendar) {
        changePressed(mDayB);
        if (mPickedB != null) {
            changeBack(mPickedB);
        }
        mPickedB = mDayB;
        ShowDayFragment.removeInstance();
        ShowDayFragment.getInstance().put_cal_and_fr(calendar, this);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDayFragment.getInstance()).commit();
    }

    public void changePressed(CustomLightButton b) {
        b.setBackgroundResource(R.color.transparent);
        b.ChangeFont(1);
        b.setPaintFlags(b.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void changeBack(CustomLightButton b) {
        int color = 0;
        if (b != null) {
            switch (b.getId()) {
                case R.id.day:
                    color = getResources().getColor(R.color.light_gray_9);
                    break;
                case R.id.week:
                    color = getResources().getColor(R.color.light_gray_8);
                    break;
                case R.id.month:
                    color = getResources().getColor(R.color.dark_gray_7);
                    break;
                case R.id.list:
                    color = getResources().getColor(R.color.dark_gray_6);
                    break;
            }
            b.setBackgroundColor(color);
            b.ChangeFont(0);
            b.setPaintFlags(b.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        }
    }

}





