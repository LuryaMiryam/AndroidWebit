package webit.bthereapp.Entities;

import java.util.ArrayList;

import webit.bthereapp.DM.AlertSettingsRealm;
import webit.bthereapp.DM.RealmInt;

/**
 * Created by 1 on 4/7/2016.
 */
public class objProviderAlertsSettings {

    private double iProviderId = 0;
    private ArrayList<Integer> iIncomingAlertsId = new ArrayList<>();
    private boolean b10minutesBeforeService = false;
    private int iCustomerResvFreqId = 0, iCustomerEventsFreqId = 0;
    private ArrayList<Integer> iCustomerEventsId = new ArrayList<>();
    private ArrayList<Integer> iCustomerResvId = new ArrayList<>();

    public double getiProviderId() {
        return iProviderId;
    }

    public void setiProviderId(double iProviderId) {
        this.iProviderId = iProviderId;
    }

    public ArrayList<Integer> getiIncomingAlertsId() {
        return iIncomingAlertsId;
    }

    public void setiIncomingAlertsId(ArrayList<Integer> iIncomingAlertsId) {
        this.iIncomingAlertsId = iIncomingAlertsId;
    }

    public boolean isB10minutesBeforeService() {
        return b10minutesBeforeService;
    }

    public void setB10minutesBeforeService(boolean b10minutesBeforeService) {
        this.b10minutesBeforeService = b10minutesBeforeService;
    }

    public ArrayList<Integer> getiCustomerResvId() {
        return iCustomerResvId;
    }

    public void setiCustomerResvId(ArrayList<Integer> iCustomerResvId) {
        this.iCustomerResvId = iCustomerResvId;
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

    public ArrayList<Integer> getiCustomerEventsId() {
        return iCustomerEventsId;
    }

    public void setiCustomerEventsId(ArrayList<Integer> iCustomerEventsId) {
        this.iCustomerEventsId = iCustomerEventsId;
    }

    public static void setInstance(objProviderAlertsSettings instance) {
//        if (instance != null)
            objProviderAlertsSettings.instance = instance;
    }


    public static objProviderAlertsSettings instance;

    public static objProviderAlertsSettings getInstance() {
        if (instance == null)
            instance = new objProviderAlertsSettings();
        return instance;
    }

    public objProviderAlertsSettings() {
    }

    public objProviderAlertsSettings(double iProviderId, ArrayList<Integer> iIncomingAlertsId, boolean b10minutesBeforeService, ArrayList<Integer> iCustomerResvId, int iCustomerResvFreqId, int iCustomerEventsFreqId, ArrayList<Integer> iCustomerEventsId) {
        this.iProviderId = iProviderId;
        this.iIncomingAlertsId = iIncomingAlertsId;
        this.b10minutesBeforeService = b10minutesBeforeService;
        this.iCustomerResvId = iCustomerResvId;
        this.iCustomerResvFreqId = iCustomerResvFreqId;
        this.iCustomerEventsFreqId = iCustomerEventsFreqId;
        this.iCustomerEventsId = iCustomerEventsId;
    }

    public objProviderAlertsSettings(AlertSettingsRealm providerAlertsSettings) {

        this.iProviderId = providerAlertsSettings.getiProviderId();
        this.b10minutesBeforeService = providerAlertsSettings.isB10minutesBeforeService();
        this.iCustomerResvFreqId = providerAlertsSettings.getiCustomerResvFreqId();
        this.iCustomerEventsFreqId = providerAlertsSettings.getiCustomerEventsFreqId();


        if (providerAlertsSettings.getiCustomerResvId() != null)
            for (RealmInt results : providerAlertsSettings.getiCustomerResvId()) {
                iCustomerResvId.add(Integer.valueOf(results.getVal()));
            }
        if (providerAlertsSettings.getiIncomingAlertsId() != null)
            for (RealmInt results : providerAlertsSettings.getiIncomingAlertsId()) {
                iIncomingAlertsId.add(Integer.valueOf(results.getVal()));
            }
        if (providerAlertsSettings.getiCustomerEventsId() != null)
            for (RealmInt results : providerAlertsSettings.getiCustomerEventsId()) {
                iCustomerEventsId.add(Integer.valueOf(results.getVal()));
            }
    }
}
