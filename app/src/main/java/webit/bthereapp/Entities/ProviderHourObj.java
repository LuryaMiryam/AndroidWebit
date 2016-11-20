package webit.bthereapp.Entities;

import webit.bthereapp.Connection.ObjectConnection;

/**
 * Created by User on 28/03/2016.
 */

public class ProviderHourObj extends ObjectConnection {

    private String nvFromHour;
    private String nvToHour;

    public ProviderHourObj(String nvFromHour, String nvToHour) {
        this.nvFromHour = nvFromHour;
        this.nvToHour = nvToHour;
    }

    public ProviderHourObj() {
    }

    public String getNvFromHour() {
        return nvFromHour;
    }

    public void setNvFromHour(String nvFromHour) {
        this.nvFromHour = nvFromHour;
    }

    public String getNvToHour() {
        return nvToHour;
    }

    public void setNvToHour(String nvToHour) {
        this.nvToHour = nvToHour;
    }

}
