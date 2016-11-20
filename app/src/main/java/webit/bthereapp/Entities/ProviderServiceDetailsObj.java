package webit.bthereapp.Entities;

import java.util.ArrayList;

import webit.bthereapp.Connection.ObjectConnection;

/**
 * Created by User on 23/06/2016.
 */
public class ProviderServiceDetailsObj extends ObjectConnection {


    private int iProviderServiceId;
    private String nvServiceName;

    public ProviderServiceDetailsObj() {

    }

    public ProviderServiceDetailsObj(int iProviderServiceId, String nvServiceName) {
        this.iProviderServiceId = iProviderServiceId;
        this.nvServiceName = nvServiceName;
    }

    public int getiProviderServiceId() {
        return iProviderServiceId;
    }

    public void setiProviderServiceId(int iProviderServiceId) {
        this.iProviderServiceId = iProviderServiceId;
    }

    public String getNvServiceName() {
        return nvServiceName;
    }

    public void setNvServiceName(String nvServiceName) {
        this.nvServiceName = nvServiceName;
    }
}
