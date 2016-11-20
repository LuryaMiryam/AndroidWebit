package webit.bthereapp.Entities;

import webit.bthereapp.DM.ProviderRealm;

/**
 * Created by User on 28/03/2016.
 */

public class ProviderBuisnessDetailsObj {

    private double iUserId;
    private double iBusinessId;
    private String nvSupplierName;
    private String nvBusinessIdentity;
    private String nvAddress;
    private int iCityType;
    private String nvCity;
    private String nvPhone;
    private String nvFax;
    private String nvEmail;
    private String nvSiteAddress;
    private static ProviderBuisnessDetailsObj objProviderBuisnessDetails;

    public String getNvCity() {
        return nvCity;
    }

    public void setNvCity(String nvCity) {
        this.nvCity = nvCity;
    }

    public ProviderBuisnessDetailsObj() {
    }

    public static ProviderBuisnessDetailsObj getInstance() {
        if (objProviderBuisnessDetails == null)
            objProviderBuisnessDetails = new ProviderBuisnessDetailsObj();
        return objProviderBuisnessDetails;
    }
    public static void setInstance(ProviderBuisnessDetailsObj objProvider_BuisnessDetails_) {
//        if (objProvider_BuisnessDetails_ != null)
            objProviderBuisnessDetails = objProvider_BuisnessDetails_;
    }


    public double getiBuisnessDetailsId() {
        return iBusinessId;
    }

    public void setiBuisnessDetailsId(double iBuisnessDetailsId) {
        this.iBusinessId = iBuisnessDetailsId;
    }

    public static ProviderBuisnessDetailsObj getObjProviderBuisnessDetails() {
        return objProviderBuisnessDetails;
    }

    public static void setObjProviderBuisnessDetails(ProviderBuisnessDetailsObj objProviderBuisnessDetails) {
        objProviderBuisnessDetails.objProviderBuisnessDetails = objProviderBuisnessDetails;
    }

    public double getiUserId() {
        return iUserId;
    }

    public void setiUserId(double iUserId) {
        this.iUserId = iUserId;
    }

    public String getNvSupplierName() {
        return nvSupplierName;
    }

    public void setNvSupplierName(String nvSupplierName) {
        this.nvSupplierName = nvSupplierName;
    }

    public String getNvBusinessIdentity() {
        return nvBusinessIdentity;
    }

    public void setNvBusinessIdentity(String nvBusinessIdentity) {
        this.nvBusinessIdentity = nvBusinessIdentity;
    }

    public String getNvAddress() {
        return nvAddress;
    }

    public void setNvAddress(String nvAddress) {
        this.nvAddress = nvAddress;
    }

    public int getiCityType() {
        return iCityType;
    }

    public void setiCityType(int iCityType) {
        this.iCityType = iCityType;
    }

    public String getNvPhone() {
        return nvPhone;
    }

    public void setNvPhone(String nvPhone) {
        this.nvPhone = nvPhone;
    }

    public String getNvFax() {
        return nvFax;
    }

    public void setNvFax(String nvFax) {
        this.nvFax = nvFax;
    }

    public String getNvEmail() {
        return nvEmail;
    }

    public void setNvEmail(String nvEmail) {
        this.nvEmail = nvEmail;
    }

    public String getNvSiteAddress() {
        return nvSiteAddress;
    }

    public void setNvSiteAddress(String nvSiteAddress) {
        this.nvSiteAddress = nvSiteAddress;
    }

    public ProviderBuisnessDetailsObj(ProviderRealm providerBuisnessDetails) {
        this.iUserId = providerBuisnessDetails.getiUserId();
        this.iBusinessId = providerBuisnessDetails.getiBuisnessId();
        this.nvSupplierName = providerBuisnessDetails.getNvSupplierName();
        this.nvBusinessIdentity = providerBuisnessDetails.getNvBusinessIdentity();
        this.nvAddress = providerBuisnessDetails.getNvAddress();
        this.iCityType = providerBuisnessDetails.getiCityType();
        this.nvPhone = providerBuisnessDetails.getNvPhone();
        this.nvFax = providerBuisnessDetails.getNvFax();
        this.nvCity = providerBuisnessDetails.getNvCity();
        this.nvEmail = providerBuisnessDetails.getNvEmail();
        this.nvSiteAddress = providerBuisnessDetails.getNvSiteAddress();
    }
}
