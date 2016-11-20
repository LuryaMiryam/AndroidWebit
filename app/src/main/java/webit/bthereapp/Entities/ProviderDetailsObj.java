package webit.bthereapp.Entities;

import java.util.ArrayList;

import webit.bthereapp.Connection.ObjectConnection;

/**
 * Created by User on 23/06/2016.
 */
public class ProviderDetailsObj extends ObjectConnection {

    ArrayList<CustomerObj> objCustomer=new ArrayList<>();
    objProviderAlertsSettings objProviderAlertsSettings;
    ProviderBuisnessDetailsObj objProviderBuisnessDetails;
    ProviderGeneralDetailsObj objProviderGeneralDetails;
    ProviderProfileObj objProviderProfile;
    UserObj objUser;
    private static ProviderDetailsObj providerDetailsObj;


    public static ProviderDetailsObj getInstance() {
        if(providerDetailsObj==null)
            providerDetailsObj=new ProviderDetailsObj();
        return providerDetailsObj;
    }
    public static void setInstance(ProviderDetailsObj addProviderDetails_){
//        if (addProviderDetails_!=null)
            providerDetailsObj=addProviderDetails_;
    }
    public static void removeInstance() {
        providerDetailsObj = null;
    }

    public ProviderDetailsObj() {

    }

    public ProviderDetailsObj(ArrayList<CustomerObj> objCustomer, objProviderAlertsSettings objProviderAlertsSettings, ProviderBuisnessDetailsObj objProviderBuisnessDetails, ProviderGeneralDetailsObj objProviderGeneralDetails, ProviderProfileObj ObjProviderProfile, UserObj objUser) {
        this.objCustomer = objCustomer;
        this.objProviderAlertsSettings = objProviderAlertsSettings;
        this.objProviderBuisnessDetails = objProviderBuisnessDetails;
        this.objProviderGeneralDetails = objProviderGeneralDetails;
        this.objProviderProfile = ObjProviderProfile;
        this.objUser = objUser;
    }

    public ArrayList<CustomerObj> getObjCustomer() {
        return objCustomer;
    }

    public void setObjCustomer(ArrayList<CustomerObj> objCustomer) {
        this.objCustomer = objCustomer;
    }

    public objProviderAlertsSettings getObjProviderAlertsSettings() {
        return objProviderAlertsSettings;
    }

    public void setObjProviderAlertsSettings(objProviderAlertsSettings objProviderAlertsSettings) {
        this.objProviderAlertsSettings = objProviderAlertsSettings;
    }

    public ProviderBuisnessDetailsObj getObjProviderBuisnessDetails() {
        return objProviderBuisnessDetails;
    }

    public void setObjProviderBuisnessDetails(ProviderBuisnessDetailsObj objProviderBuisnessDetails) {
        this.objProviderBuisnessDetails = objProviderBuisnessDetails;
    }

    public ProviderGeneralDetailsObj getObjProviderGeneralDetails() {
        return objProviderGeneralDetails;
    }

    public void setObjProviderGeneralDetails(ProviderGeneralDetailsObj objProviderGeneralDetails) {
        this.objProviderGeneralDetails = objProviderGeneralDetails;
    }

    public ProviderProfileObj getObjProviderProfile() {
        return objProviderProfile;
    }

    public void setObjProviderProfile(ProviderProfileObj objProviderProfile) {
        this.objProviderProfile = objProviderProfile;
    }

    public UserObj getObjUser() {
        return objUser;
    }

    public void setObjUser(UserObj objUser) {
        this.objUser = objUser;
    }
}
