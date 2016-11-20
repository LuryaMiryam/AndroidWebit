package webit.bthereapp.Entities;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 16/03/2016.
 */
public class TasksCalander {
    private String mTitle;
    private  int id_calender;
    private String mLocation;
    private String mDesription;
    private Long mStartTime;
    private long mEndTime;
    private transient Calendar mCalendarStart;
    private transient Calendar mCalendarEnd;
    private boolean Availability; ///To know availability Queuing client

    public boolean isAvailability() {
        return Availability;
    }

    public void setAvailability(boolean availability) {
        Availability = availability;
    }


    public int getId_calender() {
        return id_calender;
    }

    public void setId_calender(int id_calender) {
        this.id_calender = id_calender;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }



    public String getmDesription() {
        return mDesription;
    }

    public void setmDesription(String mDesription) {
        this.mDesription = mDesription;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Long getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
        mCalendarStart=Calendar.getInstance();
        mCalendarStart.setTimeInMillis(mStartTime);
    }

    public Calendar getmCalendarStart() {
        return mCalendarStart;
    }

    public void setmCalendarStart(Calendar mCalendarStart) {
        this.mCalendarStart = mCalendarStart;
    }

    public Calendar getmCalendarEnd() {
        return mCalendarEnd;
    }

    public void setmCalendarEnd(Calendar mCalendarEnd) {
        this.mCalendarEnd = mCalendarEnd;
    }

    public long getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
        mCalendarEnd= Calendar.getInstance();
        mCalendarEnd.setTimeInMillis(mEndTime);
    }




    public TasksCalander(TasksCalander tasksCalander) {
        this.mDesription = tasksCalander.getmDesription();
        this.mTitle = tasksCalander.getmTitle();
        this.mStartTime = tasksCalander.getmStartTime();
        this.mEndTime = tasksCalander.getmEndTime();
        mCalendarStart= Calendar.getInstance();
        mCalendarStart.setTimeInMillis(mStartTime);
        mCalendarEnd= Calendar.getInstance();
        mCalendarEnd.setTimeInMillis(mEndTime);
        this.mLocation=tasksCalander.getmLocation();
        this.id_calender=tasksCalander.getId_calender();
    }

    public TasksCalander(String mDesription, String mTitle, long mStartTime, long mEndTime,String mLocation,int id_calander) {
        this.mDesription = mDesription;
        this.mLocation=mLocation;
        this.mTitle = mTitle;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        mCalendarStart= Calendar.getInstance();
        mCalendarStart.setTimeInMillis(mStartTime);
        mCalendarEnd= Calendar.getInstance();
        mCalendarEnd.setTimeInMillis(mEndTime);
        this.id_calender=id_calander;
    }
    public TasksCalander(){}

}
