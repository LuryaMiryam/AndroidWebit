package webit.bthereapp.Entities;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import webit.bthereapp.DM.CalendarPropertiesRealm;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.ProviderServiceRealm;
import webit.bthereapp.DM.ServiceProviderRealm;
import webit.bthereapp.DM.WorkingHoursRealm;

/**
 * Created by User on 30/03/2016.
 */
public class ProviderGeneralDetailsObj {

    private  int iProviderUserId;
    private int iFieldId;

    private List<WorkingHours> objWorkingHours=new ArrayList<>();

    //workers
    private List<ServiceProviders>objServiceProviders=new ArrayList<>();

    public ProviderGeneralDetailsObj(int iProviderUserId, int iFieldId,  List<WorkingHours> objWorkingHours, List<ServiceProviders> objServiceProviders, List<ProviderServices> objProviderServices, CalendarProperties objCalendarProperties) {
        this.iProviderUserId = iProviderUserId;
        this.iFieldId = iFieldId;
        this.objWorkingHours = objWorkingHours;
        this.objServiceProviders = objServiceProviders;
        this.objProviderServices = objProviderServices;
        this.objCalendarProperties = objCalendarProperties;
    }


    private List<ProviderServices>objProviderServices=new ArrayList<>();
    private CalendarProperties objCalendarProperties;
    private static ProviderGeneralDetailsObj generalDetails;
    public ProviderGeneralDetailsObj() {}

    public static ProviderGeneralDetailsObj getInstance(){
        if (generalDetails==null)
            generalDetails=new ProviderGeneralDetailsObj();
        return generalDetails;
    }
    public static void setInstance(ProviderGeneralDetailsObj generalDetails_) {
//        if (generalDetails_ != null)
            generalDetails = generalDetails_;
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


    public List<WorkingHours> getWorkingHours() {
        return objWorkingHours;
    }

    public void setWorkingHours(List<WorkingHours> workingHours) {
        this.objWorkingHours = workingHours;
    }

    public List<ServiceProviders> getServiceProviders() {
        return objServiceProviders;
    }

    public List<ServiceProviderRealm> getServiceProvidersRealm() {
        RealmList<ServiceProviderRealm> serviceProviderRealms =new RealmList<>();
        for (ServiceProviders results : objServiceProviders) {
            serviceProviderRealms.add(new ServiceProviderRealm(results));
        }
        return serviceProviderRealms;
    }

    public void setServiceProviders(List<ServiceProviders> serviceProviders) {
        this.objServiceProviders = serviceProviders;
    }

    public void setServiceProviders(RealmList<ServiceProviderRealm> serviceProviders) {
        ArrayList<ServiceProviders> serviceProvider = new ArrayList<>();
        if(serviceProviders!=null)
            for (ServiceProviderRealm results : serviceProviders) {
                serviceProvider.add(new ServiceProviders(results));
            }

        this.objServiceProviders = serviceProvider;

    }

    public List<ProviderServices> getServices() {
        return objProviderServices;
    }
    public RealmList<ProviderServiceRealm> getServicesRealm() {
        RealmList<ProviderServiceRealm> providerServiceRealms =new RealmList<>();
        for (ProviderServices results : objProviderServices) {
            providerServiceRealms.add(new ProviderServiceRealm(results));
        }
        return providerServiceRealms;
    }

    public void setServices(List<ProviderServices> services) {
        this.objProviderServices = services;
    }

    public ProviderGeneralDetailsObj getGeneralDetails() {
        return generalDetails;
    }

    public void setGeneralDetails(ProviderGeneralDetailsObj generalDetails) {
        this.generalDetails = generalDetails;
    }

    public CalendarProperties getCalendarProperties() {
        return objCalendarProperties;
    }

    public void setCalendarProperties(CalendarProperties calendarProperties) {
        this.objCalendarProperties = calendarProperties;
    }

    public ProviderGeneralDetailsObj(GeneralDetailsRealm generalDetailsRealm) {
        this.iProviderUserId = generalDetailsRealm.getiProviderUserId();
        this.iFieldId = generalDetailsRealm.getiFieldId();
//        this.nvSlogen = objGeneralDetails_.getNvSlogen();

        ArrayList<WorkingHours> workingHoursRealms = new ArrayList<>();
        if(generalDetailsRealm.getObjWorkingHours()!=null)
            for (WorkingHoursRealm results : generalDetailsRealm.getObjWorkingHours()) {
                workingHoursRealms.add(new WorkingHours(results));
            }

        this.objWorkingHours = workingHoursRealms;

        ArrayList<ServiceProviders> serviceProvider = new ArrayList<>();
        if(generalDetailsRealm.getObjServiceProviders()!=null)
            for (ServiceProviderRealm results : generalDetailsRealm.getObjServiceProviders()) {
                serviceProvider.add(new ServiceProviders(results));
            }

        this.objServiceProviders = serviceProvider;

        ArrayList<ProviderServices> providerService = new ArrayList<>();
        if(generalDetailsRealm.getObjProviderServices()!=null)
            for (ProviderServiceRealm results : generalDetailsRealm.getObjProviderServices()) {
                providerService.add(new ProviderServices(results));
            }

        this.objProviderServices = providerService;
        this.objCalendarProperties = new CalendarProperties(generalDetailsRealm.getObjCalendarProperties());
    }

}
