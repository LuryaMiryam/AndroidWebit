package webit.bthereapp.Entities;

import java.util.Date;

import webit.bthereapp.Connection.ObjectConnection;

/**
 * Created by User on 28/03/2016.
 */

public class ProviderFreeDaysObj extends ObjectConnection {

    int[] iProviderUserId;
    private String dtDate;
    private ProviderHourObj objProviderHour;

    public int[] getiProviderUserId() {
        return iProviderUserId;
    }

    public String getDtDate() {
        return dtDate;
    }

    public ProviderHourObj getObjProviderHour() {
        return objProviderHour;
    }

}
