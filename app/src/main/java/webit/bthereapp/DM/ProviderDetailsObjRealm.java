package webit.bthereapp.DM;

/**
 * Created by User on 14/03/2016.
 */

import android.graphics.pdf.PdfRenderer;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import webit.bthereapp.Entities.AddProviderDetails;
import webit.bthereapp.Entities.CustomerObj;
import webit.bthereapp.Entities.ProviderBuisnessDetailsObj;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.ProviderGeneralDetailsObj;
import webit.bthereapp.Entities.ProviderProfileObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Entities.WorkingHours;
import webit.bthereapp.Entities.objProviderAlertsSettings;

public class ProviderDetailsObjRealm extends RealmObject {

    private int bAutoApproval;
    private double iUserId;
    private UserRealm objUser;
    private AlertSettingsRealm objProviderAlertsSettings=new AlertSettingsRealm();
    private ProviderRealm objProviderRealm=new ProviderRealm();
    private GeneralDetailsRealm generalDetailsRealm=new GeneralDetailsRealm();
    private BusinessProfileRealm profileRealm=new BusinessProfileRealm();
    private RealmList<RealmString> nvPhoneList = new RealmList<>();

    public ProviderDetailsObjRealm() {
    }

    public ProviderDetailsObjRealm(int bAutoApproval, double iUserId, AlertSettingsRealm objProviderAlertsSettings, ProviderRealm objProviderRealm, GeneralDetailsRealm generalDetailsRealm, BusinessProfileRealm profileRealm, RealmList<RealmString> nvPhoneList,UserRealm objUser) {
        this.bAutoApproval = bAutoApproval;
        this.iUserId = iUserId;
        this.objProviderAlertsSettings = objProviderAlertsSettings;
        this.objProviderRealm = objProviderRealm;
        this.generalDetailsRealm = generalDetailsRealm;
        this.profileRealm = profileRealm;
        this.nvPhoneList = nvPhoneList;
        this.objUser = objUser;
    }

    public UserRealm getObjUser() {
        return objUser;
    }

    public void setObjUser(UserRealm objUser) {
        this.objUser = objUser;
    }

    public int getbAutoApproval() {
        return bAutoApproval;
    }

    public void setbAutoApproval(int bAutoApproval) {
        this.bAutoApproval = bAutoApproval;
    }

    public double getiUserId() {
        return iUserId;
    }

    public void setiUserId(double iUserId) {
        this.iUserId = iUserId;
    }

    public AlertSettingsRealm getObjProviderAlertsSettings() {
        return objProviderAlertsSettings;
    }

    public void setObjProviderAlertsSettings(AlertSettingsRealm objProviderAlertsSettings) {
        this.objProviderAlertsSettings = objProviderAlertsSettings;
    }

    public ProviderRealm getObjProviderRealm() {
        return objProviderRealm;
    }

    public void setObjProviderRealm(ProviderRealm objProviderRealm) {
        this.objProviderRealm = objProviderRealm;
    }

    public GeneralDetailsRealm getGeneralDetailsRealm() {
        return generalDetailsRealm;
    }

    public void setGeneralDetailsRealm(GeneralDetailsRealm generalDetailsRealm) {
        this.generalDetailsRealm = generalDetailsRealm;
    }

    public BusinessProfileRealm getProfileRealm() {
        return profileRealm;
    }

    public void setProfileRealm(BusinessProfileRealm profileRealm) {
        this.profileRealm = profileRealm;
    }

    public RealmList<RealmString> getNvPhoneList() {
        return nvPhoneList;
    }

    public void setNvPhoneList(RealmList<RealmString> nvPhoneList) {
        this.nvPhoneList = nvPhoneList;
    }

    public void setNvPhoneList(ArrayList<String> nvPhoneList) {
        if (nvPhoneList != null)
            for (String results : nvPhoneList) {
                this.nvPhoneList.add(new RealmString(results));
            }
    }
}
