package webit.bthereapp.Entities;

/**
 * Created by 1 on 4/12/2016.
 */
public class objInContactList {

    private String name,phone;

    public objInContactList() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public objInContactList(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }



}
