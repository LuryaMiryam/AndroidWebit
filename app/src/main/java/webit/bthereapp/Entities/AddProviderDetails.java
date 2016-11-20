package webit.bthereapp.Entities;

import java.util.ArrayList;

import webit.bthereapp.Connection.ObjectConnection;


/**
 * Created by User on 28/03/2016.
 */

public class AddProviderDetails extends ObjectConnection{
    int bAutoApproval;
    double iUserId;
    ProviderBuisnessDetailsObj objProviderBuisnessDetails;
    ProviderGeneralDetailsObj objProviderGeneralDetails;
    objProviderAlertsSettings objProviderAlertsSettings;
    ProviderProfileObj objProviderProfile;
    ArrayList<String> nvPhoneList=new ArrayList<>();

//    SyncContacts contacts;
    private static AddProviderDetails addProviderDetails;

    public AddProviderDetails() {
        nvPhoneList=new ArrayList<>();
    }

    public static AddProviderDetails getInstance() {
        if(addProviderDetails==null)
            addProviderDetails=new AddProviderDetails();
        return addProviderDetails;
    }
    public static void setInstance(AddProviderDetails addProviderDetails_){
//        if (addProviderDetails_!=null)
            addProviderDetails=addProviderDetails_;
    }

    public AddProviderDetails(int bAutoApproval, double iUserId, ProviderBuisnessDetailsObj objProviderBuisnessDetails, ProviderGeneralDetailsObj objProviderGeneralDetails, objProviderAlertsSettings objProviderAlertsSettings, ProviderProfileObj objProviderProfile, ArrayList<String> nvPhoneList) {
        this.nvPhoneList=new ArrayList<>();
        this.bAutoApproval = bAutoApproval;
        this.iUserId = iUserId;
        this.objProviderBuisnessDetails = objProviderBuisnessDetails;
        this.objProviderGeneralDetails = objProviderGeneralDetails;
        this.objProviderAlertsSettings = objProviderAlertsSettings;
        this.objProviderProfile = objProviderProfile;
        this.nvPhoneList = nvPhoneList;
    }

    public double getiUserId() {
        return iUserId;
    }

    public void setiUserId(double iUserId) {
        this.iUserId = iUserId;
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

    public objProviderAlertsSettings getObjProviderAlertsSettings() {
        return objProviderAlertsSettings;
    }

    public void setObjProviderAlertsSettings(objProviderAlertsSettings objProviderAlertsSettings) {
        this.objProviderAlertsSettings = objProviderAlertsSettings;
    }


    public void setObjProviderProfile(ProviderProfileObj objProviderProfile) {
        this.objProviderProfile = objProviderProfile;
    }


    public void setNvPhoneList(ArrayList<String> nvPhoneList) {
        this.nvPhoneList = new ArrayList<>();
        this.nvPhoneList = nvPhoneList;
    }

}
