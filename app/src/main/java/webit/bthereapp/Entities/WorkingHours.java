package webit.bthereapp.Entities;

import webit.bthereapp.DM.WorkingHoursRealm;

/**
 * Created by User on 30/03/2016.
 */
public class WorkingHours {
    private int iDayInWeekType = -1;
    private String nvFromHour = "08:00";
    private String nvToHour = "08:00";
    private transient int num;

    public WorkingHours() {
    }

    public WorkingHours(WorkingHours workingHours) {
        this.iDayInWeekType = workingHours.iDayInWeekType;
        this.nvFromHour = workingHours.nvFromHour;
        this.nvToHour = workingHours.nvToHour;
        this.num = workingHours.num;
    }
    public WorkingHours(WorkingHoursRealm workingHours) {
        this.iDayInWeekType = workingHours.getiDayInWeekType();
        this.nvFromHour = workingHours.getNvFromHour();
        this.nvToHour = workingHours.getNvToHour();
        this.num = workingHours.getNum();

    }

    public WorkingHours(int iDayInWeekType, String nvFromHour, String nvToHour) {
        this.iDayInWeekType = iDayInWeekType;
        this.nvFromHour = nvFromHour;
        this.nvToHour = nvToHour;
    }

    public WorkingHours(int iDayInWeekType, String nvFromHour, String nvToHour, int num) {
        this.nvFromHour = nvFromHour.equals("") ? "08:00" : nvFromHour;
        this.nvToHour = nvToHour.equals("") ? "08:00" : nvToHour;
        this.iDayInWeekType = iDayInWeekType;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getiDayInWeekType() {
        return iDayInWeekType;
    }

    public void setiDayInWeekType(int iDayInWeekType) {
        this.iDayInWeekType = iDayInWeekType;
    }

    public String getNvFromHour() {
        return nvFromHour;
    }

    public void setNvFromHour(String nvFromHour) {
        this.nvFromHour = nvFromHour;
    }

    public String getNvToHour() {
        return nvToHour;
    }

    public void setNvToHour(String nvToHour) {
        this.nvToHour = nvToHour;
    }

    @Override
    public String toString() {
        return "day: " + this.getiDayInWeekType() + " from hour: " + this.getNvFromHour() + " to hour: " + this.getNvToHour() + " number:" + this.getNum();
    }
}
