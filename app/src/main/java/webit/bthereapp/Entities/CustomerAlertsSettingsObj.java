package webit.bthereapp.Entities;

import webit.bthereapp.Connection.ObjectConnection;

/**
 * Created by User on 30/03/2016.
 */
public class CustomerAlertsSettingsObj extends ObjectConnection {

    private int iCustomerUserId;
    private boolean b90thAlertTime,b20minutesBeforeService,bPermissionsFromBusinesses,bOrderInWaiting,bUpdatesAndNews;

    private static CustomerAlertsSettingsObj customer;

    public static CustomerAlertsSettingsObj getInstance(){
        if (customer==null)
            customer=new CustomerAlertsSettingsObj();
        return customer;
    }
    public static void setInstance(CustomerAlertsSettingsObj addProviderDetails_){
            customer=addProviderDetails_;
    }


    public CustomerAlertsSettingsObj() {}

    public CustomerAlertsSettingsObj(int iCustomerUserId, boolean b90thAlertTime, boolean b20minutesBeforeService, boolean bPermissionsFromBusinesses, boolean bOrderInWaiting, boolean bUpdatesAndNews) {
        this.iCustomerUserId = iCustomerUserId;
        this.b90thAlertTime = b90thAlertTime;
        this.b20minutesBeforeService = b20minutesBeforeService;
        this.bPermissionsFromBusinesses = bPermissionsFromBusinesses;
        this.bOrderInWaiting = bOrderInWaiting;
        this.bUpdatesAndNews = bUpdatesAndNews;
    }

    public int getiCustomerUserId() {
        return iCustomerUserId;
    }

    public void setiCustomerUserId(int iCustomerUserId) {
        this.iCustomerUserId = iCustomerUserId;
    }

    public boolean isB90thAlertTime() {
        return b90thAlertTime;
    }

    public void setB90thAlertTime(boolean b90thAlertTime) {
        this.b90thAlertTime = b90thAlertTime;
    }

    public boolean isB20minutesBeforeService() {
        return b20minutesBeforeService;
    }

    public void setB20minutesBeforeService(boolean b20minutesBeforeService) {
        this.b20minutesBeforeService = b20minutesBeforeService;
    }

    public boolean isbPermissionsFromBusinesses() {
        return bPermissionsFromBusinesses;
    }

    public void setbPermissionsFromBusinesses(boolean bPermissionsFromBusinesses) {
        this.bPermissionsFromBusinesses = bPermissionsFromBusinesses;
    }

    public boolean isbOrderInWaiting() {
        return bOrderInWaiting;
    }

    public void setbOrderInWaiting(boolean bOrderInWaiting) {
        this.bOrderInWaiting = bOrderInWaiting;
    }

    public boolean isbUpdatesAndNews() {
        return bUpdatesAndNews;
    }

    public void setbUpdatesAndNews(boolean bUpdatesAndNews) {
        this.bUpdatesAndNews = bUpdatesAndNews;
    }

    public static CustomerAlertsSettingsObj getCustomer() {
        return customer;
    }

    public static void setCustomer(CustomerAlertsSettingsObj customer) {
        CustomerAlertsSettingsObj.customer = customer;
    }
}
