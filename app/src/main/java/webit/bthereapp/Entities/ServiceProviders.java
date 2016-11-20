package webit.bthereapp.Entities;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import webit.bthereapp.DM.ServiceProviderRealm;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.DM.WorkingHoursRealm;

/**
 * Created by User on 30/03/2016.
 */
//workers class
public class ServiceProviders {
    private UserObj objUsers;
    private boolean bSameWH;
    private List<WorkingHours> objWorkingHours=new ArrayList<>();

    public ServiceProviders(UserObj objUsers, boolean bSameWH, List<WorkingHours> objWorkingHours) {
        this.objUsers = objUsers;
        this.bSameWH = bSameWH;
        this.objWorkingHours = objWorkingHours;
    }


    public ServiceProviders(ServiceProviderRealm serviceProviders) {
        this.objUsers = serviceProviders.getObjUsers_();
        this.bSameWH = serviceProviders.isbSameWH();
        this.objWorkingHours = serviceProviders.getObjWorkingHours_();
    }

    public ServiceProviders() {}

    public UserObj getObjUsers() {
        return objUsers;
    }
    public UserRealm getObjUsersRealm() {
        UserRealm objUser_=new UserRealm(objUsers);
        return objUser_;
    }


    public void setObjUsers(UserObj objUsers) {
        this.objUsers = objUsers;
    }

    public boolean isbSameWH() {
        return bSameWH;
    }

    public void setbSameWH(boolean bSameWH) {
        this.bSameWH = bSameWH;
    }

    public List<WorkingHours> getObjWorkingHours() {

        return objWorkingHours;
    }

    public RealmList<WorkingHoursRealm> getObjWorkingHoursRealm() {
        RealmList<WorkingHoursRealm> workingHours = new RealmList<>();
        if(objWorkingHours!=null)
        for (WorkingHours results : objWorkingHours) {
            workingHours.add(new WorkingHoursRealm(results));
        }
        return workingHours;
    }

    public void setObjWorkingHours(List<WorkingHours> objWorkingHours) {
        this.objWorkingHours = objWorkingHours;
    }
}
