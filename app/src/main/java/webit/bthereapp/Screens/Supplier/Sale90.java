package  webit.bthereapp.Screens.Supplier;

/**
 * Created by User on 03/05/2016.
 */
public class Sale90 {
    private int id;
    private String  date, description;

    public Sale90(String date, String description) {
        this.date = date;
        this.description = description;
    }


    public Sale90(){}



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
