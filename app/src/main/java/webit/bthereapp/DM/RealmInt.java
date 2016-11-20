package webit.bthereapp.DM;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by User on 28/06/2016.
 */
public class RealmInt extends RealmObject {

    private int val;

    public RealmInt() {
    }

    public RealmInt(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
