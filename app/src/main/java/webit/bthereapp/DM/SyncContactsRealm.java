package webit.bthereapp.DM;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import webit.bthereapp.Screens.Customer.SyncContacts;

/**
 * Created by User on 28/03/2016.
 */
public class SyncContactsRealm extends RealmObject {

    private RealmList<RealmString> nvPhoneList=new RealmList<>();
    private double iProviderId;
    private int bAutoApproval;

    public SyncContactsRealm() {
    }

    public SyncContactsRealm(RealmList<RealmString> nvPhoneList, double iProviderId, int bAutoApproval) {
        this.nvPhoneList = nvPhoneList;
        this.iProviderId = iProviderId;
        this.bAutoApproval = bAutoApproval;
    }

    public SyncContactsRealm(SyncContacts syncContacts) {

        if (syncContacts.getPhone_list() != null)
            for (String results : syncContacts.getPhone_list()) {
                nvPhoneList.add(new RealmString(results));
            }
        this.iProviderId = syncContacts.getiProviderId();
        this.bAutoApproval = syncContacts.getbAutoApproval();
    }


    public double getiProviderId() {
        return iProviderId;
    }

    public void setiProviderId(double iProviderId) {
        this.iProviderId = iProviderId;
    }

    public RealmList<RealmString> getNvPhoneList() {
        return nvPhoneList;
    }
    public ArrayList<String> getNvPhoneList_() {
        ArrayList<String> nvPhoneList_=new ArrayList<>();
        if (nvPhoneList != null)
            for (RealmString results : nvPhoneList) {
                nvPhoneList_.add(results.getVal());
            }
        return nvPhoneList_;
    }

    public void setNvPhoneList(RealmList<RealmString> nvPhoneList) {
        this.nvPhoneList = nvPhoneList;
    }

    public int getbAutoApproval() {
        return bAutoApproval;
    }

    public void setbAutoApproval(int bAutoApproval) {
        this.bAutoApproval = bAutoApproval;
    }


}
