package webit.bthereapp.DM;

import android.printservice.PrintService;

import io.realm.RealmObject;
import webit.bthereapp.Entities.ProviderServices;

/**
 * Created by User on 28/03/2016.
 */
public class ProviderServiceRealm extends RealmObject {

    private boolean bDisplayPerCustomer;
    private String nvServiceName;
    private float iPrice;
    private int iTimeOfService;
    private int iMaxConcurrentCustomers;
    private int iMinConcurrentCustomers;
    private float nUntilPrice;
    private int iUntilSeviceTime;
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

    public int getiUntilSeviceTime() {
        return iUntilSeviceTime;
    }

    public void setiUntilSeviceTime(int iUntilSeviceTime) {
        this.iUntilSeviceTime = iUntilSeviceTime;
    }

    public float getnUntilPrice() {
        return nUntilPrice;
    }

    public void setnUntilPrice(float nUntilPrice) {
        this.nUntilPrice = nUntilPrice;
    }



    public boolean isbDisplayPerCustomer() {
        return bDisplayPerCustomer;
    }

    public void setbDisplayPerCustomer(boolean bDisplayPerCustomer) {
        this.bDisplayPerCustomer = bDisplayPerCustomer;
    }

    public int getiServiceType() {
        return iServiceType;
    }

    public void setiServiceType(int iServiceType) {
        this.iServiceType = iServiceType;
    }




    public ProviderServiceRealm() {
    }

    public ProviderServiceRealm(boolean bDisplayPerCustomer, String nvServiceName, float iPrice, int iTimeOfService, int iMaxConcurrentCustomers, int iMinConcurrentCustomers, float nUntilPrice, int iUntilSeviceTime, int iTimeInterval, int iDiscount, int iServiceType, int intiProviderServiceId) {
        this.bDisplayPerCustomer = bDisplayPerCustomer;
        this.nvServiceName = nvServiceName;
        this.iPrice = iPrice;
        this.iTimeOfService = iTimeOfService;
        this.iMaxConcurrentCustomers = iMaxConcurrentCustomers;
        this.iMinConcurrentCustomers = iMinConcurrentCustomers;
        this.nUntilPrice = nUntilPrice;
        this.iUntilSeviceTime = iUntilSeviceTime;
        this.iTimeInterval = iTimeInterval;
        this.iDiscount = iDiscount;
        this.iServiceType = iServiceType;
        this.iProviderServiceId = intiProviderServiceId;
    }

    public ProviderServiceRealm(ProviderServices providerServices) {
        this.nvServiceName = providerServices.getNvServiceName();
        this.iPrice = providerServices.getiPrice();
        this.iTimeOfService = providerServices.getiTimeOfService();
        this.iMaxConcurrentCustomers = providerServices.getiMaxConcurrentCustomers();
        this.iMinConcurrentCustomers = providerServices.getiMinConcurrentCustomers();
        this.iTimeInterval = providerServices.getiTimeInterval();
        this.iDiscount = providerServices.getiDiscount();
        this.iServiceType=providerServices.getiServiceType();
        this.bDisplayPerCustomer=providerServices.isbDisplayPerCustomer();
        this.nUntilPrice=providerServices.getnUntilPrice();
        this.iUntilSeviceTime=providerServices.getiUntilSeviceTime();
        this.iProviderServiceId=providerServices.getIProviderServiceId();
    }

    public String getNvServiceName() {
        return nvServiceName;
    }

    public void setNvServiceName(String nvServiceName) {
        this.nvServiceName = nvServiceName;
    }

    public float getiPrice() {
        return iPrice;
    }

    public void setiPrice(float iPrice) {
        this.iPrice = iPrice;
    }

    public int getiTimeOfService() {
        return iTimeOfService;
    }

    public void setiTimeOfService(int iTimeOfService) {
        this.iTimeOfService = iTimeOfService;
    }

    public int getiMaxConcurrentCustomers() {
        return iMaxConcurrentCustomers;
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

    public int getiTimeInterval() {
        return iTimeInterval;
    }

    public void setiTimeInterval(int iTimeInterval) {
        this.iTimeInterval = iTimeInterval;
    }

    public int getiDiscount() {
        return iDiscount;
    }

    public void setiDiscount(int iDiscount) {
        this.iDiscount = iDiscount;
    }
}
