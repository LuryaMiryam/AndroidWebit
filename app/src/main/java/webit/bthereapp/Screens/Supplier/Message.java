package  webit.bthereapp.Screens.Supplier;

/**
 * Created by User on 19/04/2016.
 */
public class Message {

    private int id_message;

    private String date,message_h,message;


    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage_h() {
        return message_h;
    }

    public void setMessage_h(String message_h) {
        this.message_h = message_h;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(String date, String message_h,String message) {
        this.message = message;
        this.date = date;
        this.message_h = message_h;
    }

    public Message(){}




}