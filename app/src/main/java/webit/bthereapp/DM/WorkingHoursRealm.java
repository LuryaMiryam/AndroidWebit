package webit.bthereapp.DM;

/**
 * Created by User on 14/03/2016.
 */

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import webit.bthereapp.Entities.CalendarProperties;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.ServiceProviders;
import webit.bthereapp.Entities.WorkingHours;

public class WorkingHoursRealm extends RealmObject {

    private int iDayInWeekType = -1;
    private String nvFromHour = "08:00";
    private String nvToHour = "08:00";
    private int num;


    public WorkingHoursRealm(int iDayInWeekType, String nvFromHour, String nvToHour, int num) {
        this.iDayInWeekType = iDayInWeekType;
        this.nvFromHour = nvFromHour;
        this.nvToHour = nvToHour;
        this.num = num;
    }

    public WorkingHoursRealm(WorkingHours workingHours) {
        this.iDayInWeekType = workingHours.getiDayInWeekType();
        this.nvFromHour = workingHours.getNvFromHour();
        this.nvToHour = workingHours.getNvToHour();
        this.num = workingHours.getNum();

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

        public WorkingHoursRealm(){}




    }
