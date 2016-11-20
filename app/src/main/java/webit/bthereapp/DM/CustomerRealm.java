package webit.bthereapp.DM;

/**
 * Created by User on 14/03/2016.
 */

import io.realm.RealmObject;
import webit.bthereapp.Entities.CustomerObj;

public class CustomerRealm extends RealmObject {

    private UserRealm userObj;
    private int iProviderId;
    private boolean bIsVip;


    public CustomerRealm() {
    }

    public CustomerRealm(UserRealm userObj, int iProviderId, boolean bIsVip) {
        this.userObj = userObj;
        this.iProviderId = iProviderId;
        this.bIsVip = bIsVip;
    }
    public CustomerRealm(CustomerObj CustomerObj) {
        this.bIsVip = CustomerObj.isbIsVip();
        this.iProviderId = CustomerObj.getiProviderId();
        this.userObj = new UserRealm(CustomerObj.getUserObj());
    }

    public UserRealm getUserObj() {
        return userObj;
    }

    public void setUserObj(UserRealm userObj) {
        this.userObj = userObj;
    }

    public int getiProviderId() {
        return iProviderId;
    }

    public void setiProviderId(int iProviderId) {
        this.iProviderId = iProviderId;
    }

    public boolean isbIsVip() {
        return bIsVip;
    }

    public void setbIsVip(boolean bIsVip) {
        this.bIsVip = bIsVip;
    }
}
