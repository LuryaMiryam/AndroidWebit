package webit.bthereapp.Entities;

/**
 * Created by User on 28/03/2016.
 */

public class Provider {
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
    private static Provider provider;

    public String getNvCity() {
        return nvCity;
    }

    public void setNvCity(String nvCity) {
        this.nvCity = nvCity;
    }

    public Provider() {
    }

    public static Provider getInstance() {
        if (provider == null)
            provider = new Provider();
        return provider;
    }
    public static void setInstance(Provider provider_) {
//        if (provider_ != null)
            provider = provider_;
    }


    public double getiBuisnessDetailsId() {
        return iBusinessId;
    }

    public void setiBuisnessDetailsId(double iBuisnessDetailsId) {
        this.iBusinessId = iBuisnessDetailsId;
    }

    public static Provider getProvider() {
        return provider;
    }

    public static void setProvider(Provider provider) {
        Provider.provider = provider;
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
}
