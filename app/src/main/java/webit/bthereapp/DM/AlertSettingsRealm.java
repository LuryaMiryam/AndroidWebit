package webit.bthereapp.DM;

import android.widget.RelativeLayout;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import webit.bthereapp.Entities.CustomerObj;
import webit.bthereapp.Entities.objProviderAlertsSettings;

/**
 * Created by User on 28/03/2016.
 */
public class AlertSettingsRealm extends RealmObject {

    private double iProviderId = 0;
    private RealmList<RealmInt> iIncomingAlertsId = new RealmList<>();
    private boolean b10minutesBeforeService = false;
    private int  iCustomerResvFreqId = 0, iCustomerEventsFreqId = 0;
    private RealmList<RealmInt> iCustomerEventsId = new RealmList<>();
    private RealmList<RealmInt> iCustomerResvId = new RealmList<>();

    public double getiProviderId() {
        return iProviderId;
    }

    public void setiProviderId(double iProviderId) {
        this.iProviderId = iProviderId;
    }

    public RealmList<RealmInt> getiIncomingAlertsId() {
        return iIncomingAlertsId;
    }

    public boolean isB10minutesBeforeService() {
        return b10minutesBeforeService;
    }

    public RealmList<RealmInt> getiCustomerResvId() {
        return iCustomerResvId;
    }

    public int getiCustomerResvFreqId() {
        return iCustomerResvFreqId;
    }

    public int getiCustomerEventsFreqId() {
        return iCustomerEventsFreqId;
    }

    public RealmList<RealmInt> getiCustomerEventsId() {
        return iCustomerEventsId;
    }

    public AlertSettingsRealm(double iProviderId, RealmList<RealmInt> iIncomingAlertsId, boolean b10minutesBeforeService, RealmList<RealmInt> iCustomerResvId, int iCustomerResvFreqId, int iCustomerEventsFreqId, RealmList<RealmInt> iCustomerEventsId) {
        this.iProviderId = iProviderId;
        this.iIncomingAlertsId = iIncomingAlertsId;
        this.b10minutesBeforeService = b10minutesBeforeService;
        this.iCustomerResvId = iCustomerResvId;
        this.iCustomerResvFreqId = iCustomerResvFreqId;
        this.iCustomerEventsFreqId = iCustomerEventsFreqId;
        this.iCustomerEventsId = iCustomerEventsId;
    }

    public AlertSettingsRealm(objProviderAlertsSettings providerAlertsSettings) {

        this.iProviderId = providerAlertsSettings.getiProviderId();
        this.b10minutesBeforeService = providerAlertsSettings.isB10minutesBeforeService();
        this.iCustomerResvFreqId = providerAlertsSettings.getiCustomerResvFreqId();
        this.iCustomerEventsFreqId = providerAlertsSettings.getiCustomerEventsFreqId();

        if (providerAlertsSettings.getiCustomerResvId() != null)
            for (Integer results : providerAlertsSettings.getiCustomerResvId()) {
                iCustomerResvId.add(new RealmInt(results));
            }
        if (providerAlertsSettings.getiIncomingAlertsId() != null)
            for (Integer results : providerAlertsSettings.getiIncomingAlertsId()) {
                iIncomingAlertsId.add(new RealmInt(results));
            }
        if (providerAlertsSettings.getiCustomerEventsId() != null)
            for (Integer results : providerAlertsSettings.getiCustomerEventsId()) {
                iCustomerEventsId.add(new RealmInt(results));
            }
    }

    public AlertSettingsRealm() {
    }


}
