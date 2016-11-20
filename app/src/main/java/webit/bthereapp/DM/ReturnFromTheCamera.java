package webit.bthereapp.DM;

import io.realm.RealmList;
import io.realm.RealmObject;
import webit.bthereapp.Entities.objProviderAlertsSettings;

/**
 * Created by User on 28/03/2016.
 */
public class ReturnFromTheCamera extends RealmObject {

    private int fragment_id = 0;
    private boolean return_from_camera = false;

    public ReturnFromTheCamera(int fragment_id, boolean return_from_camera) {
        this.fragment_id = fragment_id;
        this.return_from_camera = return_from_camera;
    }

    public ReturnFromTheCamera() {
    }

    public int getFragment_id() {
        return fragment_id;
    }

    public void setFragment_id(int fragment_id) {
        this.fragment_id = fragment_id;
    }

    public boolean isReturn_from_camera() {
        return return_from_camera;
    }

    public void setReturn_from_camera(boolean return_from_camera) {
        this.return_from_camera = return_from_camera;
    }
}
