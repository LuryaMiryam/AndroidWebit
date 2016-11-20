package webit.bthereapp.Entities;

/**
 * Created by 1 on 4/4/2016.
 */
public class SysAlertsListItem {

    private int item_id;
    private String item_txt;

    public SysAlertsListItem() {
    }

    public SysAlertsListItem(int item_id, String item_txt) {
        this.item_id = item_id;
        this.item_txt = item_txt;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_txt() {
        return item_txt;
    }

    public void setItem_txt(String item_txt) {
        this.item_txt = item_txt;
    }


}
