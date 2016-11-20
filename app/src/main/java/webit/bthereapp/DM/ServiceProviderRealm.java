package webit.bthereapp.DM;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import webit.bthereapp.Entities.ServiceProviders;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Entities.WorkingHours;

/**
 * Created by User on 28/03/2016.
 */
public class ServiceProviderRealm extends RealmObject {

    private UserRealm objUsers;
    private boolean bSameWH;
    private RealmList<WorkingHoursRealm> objWorkingHours;

    public ServiceProviderRealm() {
    }

    public ServiceProviderRealm(UserRealm objUsers, boolean bSameWH, RealmList<WorkingHoursRealm> objWorkingHours) {
        this.objUsers = objUsers;
        this.bSameWH = bSameWH;
        this.objWorkingHours = objWorkingHours;
    }

    public ServiceProviderRealm(ServiceProviders serviceProviders) {
        this.objUsers = serviceProviders.getObjUsersRealm();
        this.bSameWH = serviceProviders.isbSameWH();
        this.objWorkingHours = serviceProviders.getObjWorkingHoursRealm();
    }

    public UserRealm getObjUsers() {
        return objUsers;

    }

    public UserObj getObjUsers_() {
        if(this.getObjUsers()!=null) {
            UserObj objUser_ = new UserObj(this.getObjUsers());
            return objUser_;
        }
        return null;
    }

    public void setObjUsers(UserRealm objUsers) {
        this.objUsers = objUsers;
    }

    public boolean isbSameWH() {
        return bSameWH;
    }

    public void setbSameWH(boolean bSameWH) {
        this.bSameWH = bSameWH;
    }

    public RealmList<WorkingHoursRealm> getObjWorkingHours() {
        return objWorkingHours;
    }

    public ArrayList<WorkingHours> getObjWorkingHours_() {
        ArrayList<WorkingHours> workingHours = new ArrayList<>();
        if (objWorkingHours != null)
            for (WorkingHoursRealm results : objWorkingHours) {
                workingHours.add(new WorkingHours(results));
            }

        return workingHours;
    }

    public void setObjWorkingHours(RealmList<WorkingHoursRealm> objWorkingHours) {
        this.objWorkingHours = objWorkingHours;
    }
}
