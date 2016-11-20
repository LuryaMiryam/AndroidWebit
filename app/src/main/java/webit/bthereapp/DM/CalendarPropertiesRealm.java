package webit.bthereapp.DM;

import io.realm.RealmObject;
import webit.bthereapp.Entities.CalendarProperties;

/**
 * Created by User on 28/03/2016.
 */
public class CalendarPropertiesRealm extends RealmObject {

    private int iFirstCalendarViewType;
    private String dtCalendarOpenDate;
    private boolean bLimitSeries;
    private int iMaxServiceForCustomer;
    private int iPeriodInWeeksForMaxServices;

    public CalendarPropertiesRealm() {
    }

    public CalendarPropertiesRealm(int iFirstCalendarViewType, String dtCalendarOpenDate, boolean bLimitSeries, int iMaxServiceForCustomer, int iPeriodInWeeksForMaxServices) {
        this.iFirstCalendarViewType = iFirstCalendarViewType;
        this.dtCalendarOpenDate = dtCalendarOpenDate;
        this.bLimitSeries = bLimitSeries;
        this.iMaxServiceForCustomer = iMaxServiceForCustomer;
        this.iPeriodInWeeksForMaxServices = iPeriodInWeeksForMaxServices;
    }
    public CalendarPropertiesRealm(CalendarProperties calendarProperties) {
        this.iFirstCalendarViewType = calendarProperties.getiFirstCalendarViewType();
        this.dtCalendarOpenDate = calendarProperties.getDtCalendarOpenDate();
        this.bLimitSeries = calendarProperties.isbLimitSeries();
        this.iMaxServiceForCustomer = calendarProperties.getiMaxServiceForCustomer();
        this.iPeriodInWeeksForMaxServices = calendarProperties.getiPeriodInWeeksForMaxServices();
    }

    public int getiFirstCalendarViewType() {
        return iFirstCalendarViewType;
    }

    public void setiFirstCalendarViewType(int iFirstCalendarViewType) {
        this.iFirstCalendarViewType = iFirstCalendarViewType;
    }

    public String getDtCalendarOpenDate() {
        return dtCalendarOpenDate;
    }

    public void setDtCalendarOpenDate(String dtCalendarOpenDate) {
        this.dtCalendarOpenDate = dtCalendarOpenDate;
    }

    public boolean isbLimitSeries() {
        return bLimitSeries;
    }

    public void setbLimitSeries(boolean bLimitSeries) {
        this.bLimitSeries = bLimitSeries;
    }

    public int getiMaxServiceForCustomer() {
        return iMaxServiceForCustomer;
    }

    public void setiMaxServiceForCustomer(int iMaxServiceForCustomer) {
        this.iMaxServiceForCustomer = iMaxServiceForCustomer;
    }

    public int getiPeriodInWeeksForMaxServices() {
        return iPeriodInWeeksForMaxServices;
    }

    public void setiPeriodInWeeksForMaxServices(int iPeriodInWeeksForMaxServices) {
        this.iPeriodInWeeksForMaxServices = iPeriodInWeeksForMaxServices;
    }
}
