package  webit.bthereapp.Screens.Supplier;

/**
 * Created by User on 01/05/2016.
 */
public class WaitingListItemToDay {
    private int id;
    private String fname,lname;
    private String type;
    private String hour;

    public WaitingListItemToDay(String fname, String lname, String type, String hour) {
        this.fname = fname;
        this.lname = lname;
        this.type = type;
        this.hour = hour;
    }
    public WaitingListItemToDay(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
