package webit.bthereapp.DM;

/**
 * Created by User on 14/03/2016.
 */

import android.printservice.PrintService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import webit.bthereapp.Entities.CalendarProperties;
import webit.bthereapp.Entities.ProviderGeneralDetailsObj;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.ServiceProviders;
import webit.bthereapp.Entities.WorkingHours;

public class GeneralDetailsRealm extends RealmObject {

    private int iProviderUserId;
    private int iFieldId;
    private String nvSlogen;
    private RealmList<WorkingHoursRealm> objWorkingHours;
    private RealmList<ServiceProviderRealm> objServiceProviders=new RealmList<>();
    private RealmList<ProviderServiceRealm> objProviderServices;
    private CalendarPropertiesRealm objCalendarProperties;


    public RealmList<ServiceProviderRealm> getObjServiceProviders() {
        return objServiceProviders;
    }

    public void setObjServiceProviders(RealmList<ServiceProviderRealm> objServiceProviders) {
        this.objServiceProviders = objServiceProviders;
    }

    public void setObjServiceProvidersRealm(List<ServiceProviders> objServiceProviders) {
        RealmList<ServiceProviderRealm> serviceProviderRealms = new RealmList<>();
        if(objServiceProviders!=null)
            for (ServiceProviders results : objServiceProviders) {
                serviceProviderRealms.add(new ServiceProviderRealm(results));
            }
        this.objServiceProviders = serviceProviderRealms;
    }

    public int getiProviderUserId() {
        return iProviderUserId;
    }

    public void setiProviderUserId(int iProviderUserId) {
        this.iProviderUserId = iProviderUserId;
    }

    public int getiFieldId() {
        return iFieldId;
    }

    public void setiFieldId(int iFieldId) {
        this.iFieldId = iFieldId;
    }

    public String getNvSlogen() {
        return nvSlogen;
    }

    public void setNvSlogen(String nvSlogen) {
        this.nvSlogen = nvSlogen;
    }

    public RealmList<WorkingHoursRealm> getObjWorkingHours() {
        return objWorkingHours;
    }

    public void setObjWorkingHours(RealmList<WorkingHoursRealm> objWorkingHours) {
        this.objWorkingHours = objWorkingHours;
    }

    public RealmList<ProviderServiceRealm> getObjProviderServices() {
        return objProviderServices;
    }

    public void setObjProviderServices(RealmList<ProviderServiceRealm> objProviderServices) {
        this.objProviderServices = objProviderServices;
    }

    public CalendarPropertiesRealm getObjCalendarProperties() {
        return objCalendarProperties;
    }

    public void setObjCalendarProperties(CalendarPropertiesRealm objCalendarProperties) {
        this.objCalendarProperties = objCalendarProperties;
    }

    public GeneralDetailsRealm() {
    }

    public GeneralDetailsRealm(int iProviderUserId, int iFieldId, String nvSlogen, RealmList<WorkingHoursRealm> objWorkingHours, RealmList<ServiceProviderRealm> objServiceProviders, RealmList<ProviderServiceRealm> objProviderServices, CalendarPropertiesRealm objCalendarProperties) {
        this.iProviderUserId = iProviderUserId;
        this.iFieldId = iFieldId;
        this.nvSlogen = nvSlogen;
        this.objWorkingHours = objWorkingHours;
        this.objServiceProviders = objServiceProviders;
        this.objProviderServices = objProviderServices;
        this.objCalendarProperties = objCalendarProperties;
    }

    public GeneralDetailsRealm(ProviderGeneralDetailsObj objGeneralDetails_) {
        this.iProviderUserId = objGeneralDetails_.getiProviderUserId();
        this.iFieldId = objGeneralDetails_.getiFieldId();
//        this.nvSlogen = objGeneralDetails_.getNvSlogen();

        RealmList<WorkingHoursRealm> workingHoursRealms = new RealmList<>();
        if(objGeneralDetails_.getWorkingHours()!=null)
        for (WorkingHours results : objGeneralDetails_.getWorkingHours()) {
            workingHoursRealms.add(new WorkingHoursRealm(results));
        }

        this.objWorkingHours = workingHoursRealms;

        RealmList<ServiceProviderRealm> serviceProviderRealms = new RealmList<>();
        if(objGeneralDetails_.getServiceProviders()!=null)
        for (ServiceProviders results : objGeneralDetails_.getServiceProviders()) {
            serviceProviderRealms.add(new ServiceProviderRealm(results));
        }

        this.objServiceProviders = serviceProviderRealms;

        RealmList<ProviderServiceRealm> providerServiceRealms = new RealmList<>();
        if(objGeneralDetails_.getServices()!=null)
        for (ProviderServices results : objGeneralDetails_.getServices()) {
            providerServiceRealms.add(new ProviderServiceRealm(results));
        }

        this.objProviderServices = providerServiceRealms;
        this.objCalendarProperties = new CalendarPropertiesRealm(objGeneralDetails_.getCalendarProperties());
    }


}
