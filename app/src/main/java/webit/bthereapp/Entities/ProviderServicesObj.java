package webit.bthereapp.Entities;

/**
 * Created by User on 30/03/2016.
 */
public class ProviderServicesObj {

    private boolean bDisplayPerCustomer;
    private String nvServiceName;
    private int iPrice;
    private int iTimeOfService;
    private int iMaxConcurrentCustomers;
    private int iMinConcurrentCustomers;
    private int iTimeInterval;
    private int iDiscount;
    private int iServiceType;
    private int iProviderServiceId;

    public int getIProviderServiceId() {
        return iProviderServiceId;
    }

    public void setIProviderServiceId(int intiProviderServiceId) {
        this.iProviderServiceId = intiProviderServiceId;
    }



    public int getiServiceType() {
        return iServiceType;
    }

    public void setiServiceType(int iServiceType) {
        this.iServiceType = iServiceType;
    }

    public ProviderServicesObj() {}

    public ProviderServicesObj(boolean bDisplayPerCustomer, String nvServiceName, int iPrice, int iTimeOfService, int iMaxConcurrentCustomers, int iMinConcurrentCustomers, int iTimeInterval, int iDiscount, int iServiceType, int iProviderServiceId) {
        this.bDisplayPerCustomer = bDisplayPerCustomer;
        this.nvServiceName = nvServiceName;
        this.iPrice = iPrice;
        this.iTimeOfService = iTimeOfService;
        this.iMaxConcurrentCustomers = iMaxConcurrentCustomers;
        this.iMinConcurrentCustomers = iMinConcurrentCustomers;
        this.iTimeInterval = iTimeInterval;
        this.iDiscount = iDiscount;
        this.iServiceType = iServiceType;
        this.iProviderServiceId = iProviderServiceId;
    }

    public String getNvServiceName() {
        return nvServiceName;
    }

    public void setNvServiceName(String nvServiceName) {
        this.nvServiceName = nvServiceName;
    }

    public int getiPrice() {
        return iPrice;
    }

    public void setiPrice(int iPrice) {
        this.iPrice = iPrice;
    }

    public int getiTimeOfService() {
        return iTimeOfService;
    }

    public void setiTimeOfService(int iTimeOfService) {
        this.iTimeOfService = iTimeOfService;
    }



    public int getiTimeInterval() {
        return iTimeInterval;
    }

    public void setiTimeInterval(int iTimeInterval) {
        this.iTimeInterval = iTimeInterval;
    }

    public int getiMaxConcurrentCustomers() {
        return iMaxConcurrentCustomers;
    }

    public boolean isbDisplayPerCustomer() {
        return bDisplayPerCustomer;
    }

    public void setbDisplayPerCustomer(boolean bDisplayPerCustomer) {
        this.bDisplayPerCustomer = bDisplayPerCustomer;
    }

    public void setiMaxConcurrentCustomers(int iMaxConcurrentCustomers) {
        this.iMaxConcurrentCustomers = iMaxConcurrentCustomers;
    }

    public int getiMinConcurrentCustomers() {
        return iMinConcurrentCustomers;
    }

    public void setiMinConcurrentCustomers(int iMinConcurrentCustomers) {
        this.iMinConcurrentCustomers = iMinConcurrentCustomers;
    }

    public int getiDiscount() {
        return iDiscount;
    }

    public void setiDiscount(int iDiscount) {
        this.iDiscount = iDiscount;
    }
}
