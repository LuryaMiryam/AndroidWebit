package  webit.bthereapp.Screens.Supplier;

/**
 * Created by User on 18/04/2016.
 */
public class Qwestion {
    private int id_qwestion;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQwestion() {
        return qwestion;
    }

    public void setQwestion(String qwestion) {
        this.qwestion = qwestion;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Qwestion(String date, String qwestion, String answer) {
        this.date = date;
        this.qwestion = qwestion;
        this.answer = answer;
    }

    private String date,qwestion, answer;


}
