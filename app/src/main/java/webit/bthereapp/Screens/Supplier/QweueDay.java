package  webit.bthereapp.Screens.Supplier;

/**
 * Created by User on 21/04/2016.
 */
public class QweueDay {
    private int id;
    private String hour;
    private String type;

    public QweueDay(){}

    public QweueDay(String hour, String type) {
        this.hour = hour;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
