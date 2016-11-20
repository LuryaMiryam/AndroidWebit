package webit.bthereapp.Entities;

import webit.bthereapp.Connection.ObjectConnection;

/**
 * Created by User on 28/03/2016.
 */

public class ProviderAlertsSettingsObj extends ObjectConnection {

    public int iProviderId,iCustomerResvFreqId,iCustomerEventsFreqId;
    public boolean b10minutesBeforeService;
    public int[] iCustomerResvId,iCustomerEventsId,iIncomingAlertsId;

    private static ProviderAlertsSettingsObj objProviderBuisnessDetails;

    public static ProviderAlertsSettingsObj getInstance() {
        if (objProviderBuisnessDetails == null)
            objProviderBuisnessDetails = new ProviderAlertsSettingsObj();
        return objProviderBuisnessDetails;
    }
    public static void setInstance(ProviderAlertsSettingsObj objProviderBuisnessDetails_) {
//        if (objProviderBuisnessDetails_ != null)
            objProviderBuisnessDetails = objProviderBuisnessDetails_;
    }

    public ProviderAlertsSettingsObj(){}

    public ProviderAlertsSettingsObj(int iProviderId, int iCustomerResvFreqId, int iCustomerEventsFreqId, boolean b10minutesBeforeService, int[] iCustomerResvId, int[] iCustomerEventsId, int[] iIncomingAlertsId) {
        this.iProviderId = iProviderId;
        this.iCustomerResvFreqId = iCustomerResvFreqId;
        this.iCustomerEventsFreqId = iCustomerEventsFreqId;
        this.b10minutesBeforeService = b10minutesBeforeService;
        this.iCustomerResvId = iCustomerResvId;
        this.iCustomerEventsId = iCustomerEventsId;
        this.iIncomingAlertsId = iIncomingAlertsId;
    }

    public int getiProviderId() {
        return iProviderId;
    }

    public void setiProviderId(int iProviderId) {
        this.iProviderId = iProviderId;
    }

    public int getiCustomerResvFreqId() {
        return iCustomerResvFreqId;
    }

    public void setiCustomerResvFreqId(int iCustomerResvFreqId) {
        this.iCustomerResvFreqId = iCustomerResvFreqId;
    }

    public int getiCustomerEventsFreqId() {
        return iCustomerEventsFreqId;
    }

    public void setiCustomerEventsFreqId(int iCustomerEventsFreqId) {
        this.iCustomerEventsFreqId = iCustomerEventsFreqId;
    }

    public boolean isB10minutesBeforeService() {
        return b10minutesBeforeService;
    }

    public void setB10minutesBeforeService(boolean b10minutesBeforeService) {
        this.b10minutesBeforeService = b10minutesBeforeService;
    }

    public int[] getiCustomerResvId() {
        return iCustomerResvId;
    }

    public void setiCustomerResvId(int[] iCustomerResvId) {
        this.iCustomerResvId = iCustomerResvId;
    }

    public int[] getiCustomerEventsId() {
        return iCustomerEventsId;
    }

    public void setiCustomerEventsId(int[] iCustomerEventsId) {
        this.iCustomerEventsId = iCustomerEventsId;
    }

    public int[] getiIncomingAlertsId() {
        return iIncomingAlertsId;
    }

    public void setiIncomingAlertsId(int[] iIncomingAlertsId) {
        this.iIncomingAlertsId = iIncomingAlertsId;
    }
}
