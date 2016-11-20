package webit.bthereapp.Entities;

import webit.bthereapp.Connection.ObjectConnection;

/**
 * Created by User on 28/03/2016.
 */

public class GetFreeDaysForServiceProvider extends ObjectConnection {

    private int[] lServiseProviderId;
    private int[] lProviderServiceId;

    private static GetFreeDaysForServiceProvider getFreeDaysForServiceProvider;

    public static GetFreeDaysForServiceProvider getInstance() {
        if (getFreeDaysForServiceProvider == null)
            getFreeDaysForServiceProvider = new GetFreeDaysForServiceProvider();
        return getFreeDaysForServiceProvider;
    }
    public static void setInstance(GetFreeDaysForServiceProvider getFreeDaysForServiceProvider_) {
//        if (getFreeDaysForServiceProvider_ != null)
            getFreeDaysForServiceProvider = getFreeDaysForServiceProvider_;
    }


    public GetFreeDaysForServiceProvider() {
    }

    public GetFreeDaysForServiceProvider(int[] lServiseProviderId, int[] lProviderServiceId) {
        this.lServiseProviderId = lServiseProviderId;
        this.lProviderServiceId = lProviderServiceId;
    }

    public int[] getlServiseProviderId() {
        return lServiseProviderId;
    }

    public void setlServiseProviderId(int[] lServiseProviderId) {
        this.lServiseProviderId = lServiseProviderId;
    }

    public int[] getlProviderServiceId() {
        return lProviderServiceId;
    }

    public void setlProviderServiceId(int[] lProviderServiceId) {
        this.lProviderServiceId = lProviderServiceId;
    }
}
