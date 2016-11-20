package webit.bthereapp.Entities;

/**
 * Created by User on 30/03/2016.
 */
public class CustomerObj {
    
    UserObj userObj;
    int iProviderId;
    boolean bIsVip;
    private static CustomerObj customer;

    public static CustomerObj getInstance(){
        if (customer==null)
            customer=new CustomerObj();
        return customer;
    }
    public static void setInstance(CustomerObj customerObj_){
//        if (customerObj_!=null)
            customer=customerObj_;
    }

    public CustomerObj() {}

    public CustomerObj(UserObj userObj, int iProviderId, boolean bIsVip) {
        this.userObj = userObj;
        this.iProviderId = iProviderId;
        this.bIsVip = bIsVip;
    }

    public UserObj getUserObj() {
        return userObj;
    }

    public void setUserObj(UserObj userObj) {
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
