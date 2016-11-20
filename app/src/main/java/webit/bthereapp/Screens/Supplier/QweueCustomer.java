package  webit.bthereapp.Screens.Supplier;

import java.util.List;

/**
 * Created by User on 21/04/2016.
 */
public class QweueCustomer {
    private int id;
    private String  mDayInWeek, mDayInMonth, mMonth,mYear;
    private List<QweueDay> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmDayInWeek() {
        return mDayInWeek;
    }

    public void setmDayInWeek(String mDayInWeek) {
        this.mDayInWeek = mDayInWeek;
    }

    public String getmDayInMonth() {
        return mDayInMonth;
    }

    public void setmDayInMonth(String mDayInMonth) {
        this.mDayInMonth = mDayInMonth;
    }

    public String getmMonth() {
        return mMonth;
    }

    public void setmMonth(String mMonth) {
        this.mMonth = mMonth;
    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public List<QweueDay> getList() {
        return list;
    }

    public void setList(List<QweueDay> list) {
        this.list = list;
    }

    public QweueCustomer(String mDayInWeek, String mDayInMonth, String mMonth, String mYear, List<QweueDay> list) {
        this.mDayInWeek = mDayInWeek;
        this.mDayInMonth = mDayInMonth;
        this.mMonth = mMonth;
        this.mYear = mYear;

        this.list = list;
    }
    public QweueCustomer(){}

}
