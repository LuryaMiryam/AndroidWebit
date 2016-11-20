package webit.bthereapp.DM;

import io.realm.RealmList;
import io.realm.RealmObject;
import webit.bthereapp.Entities.ProviderBuisnessDetailsObj;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.ServiceProviders;
import webit.bthereapp.Entities.WorkingHours;
/**
 * Created by User on 28/03/2016.
 */
public class ProviderRealm extends RealmObject {
    private double iUserId;
    private double iBusinessId;
    private String nvSupplierName;
    private String nvBusinessIdentity;
    private String nvAddress;
    private int iCityType;
    private String nvPhone;
    private String nvFax;
    private String nvCity;
    private String nvEmail;
    private String nvSiteAddress;

    public ProviderRealm(double iUserId, double iBusinessId, String nvSupplierName, String nvBusinessIdentity, String nvAddress, int iCityType, String nvPhone, String nvFax, String nvCity, String nvEmail, String nvSiteAddress) {
        this.iUserId = iUserId;
        this.iBusinessId = iBusinessId;
        this.nvSupplierName = nvSupplierName;
        this.nvBusinessIdentity = nvBusinessIdentity;
        this.nvAddress = nvAddress;
        this.iCityType = iCityType;
        this.nvPhone = nvPhone;
        this.nvFax = nvFax;
        this.nvCity = nvCity;
        this.nvEmail = nvEmail;
        this.nvSiteAddress = nvSiteAddress;
    }

    public ProviderRealm(ProviderBuisnessDetailsObj providerBuisnessDetails) {
        this.iUserId = providerBuisnessDetails.getiUserId();
        this.iBusinessId = providerBuisnessDetails.getiBuisnessDetailsId();
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

    public ProviderRealm() {
    }

    public String getNvCity() {
        return nvCity;
    }

    public void setNvCity(String nvCity) {
        this.nvCity = nvCity;
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

    public double getiBuisnessId() {
        return iBusinessId;
    }

    public void setiBuisnessId(double iBuisnessId) {
        this.iBusinessId = iBuisnessId;
    }
}
