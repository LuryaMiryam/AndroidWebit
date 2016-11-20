package webit.bthereapp.Entities;

import java.util.Date;

import webit.bthereapp.Connection.ObjectConnection;

/**
 * Created by User on 28/03/2016.
 */

public class WaitingListObj extends ObjectConnection {

    private int iWaitingForServiceId,iProviderUserId,iUserId;
    private String nvLogo,nvComment;
    private ProviderServicesObj[] providerServiceObj;
    private String dtDateOrder;


    private static WaitingListObj waitingListObj;

    public static WaitingListObj getInstance(){
        if (waitingListObj==null)
            waitingListObj=new WaitingListObj();
        return waitingListObj;
    }
    public static void setInstance(WaitingListObj waitingListObj_) {
        waitingListObj = waitingListObj_;
    }

    public WaitingListObj(){}

    public WaitingListObj(int iWaitingForServiceId, int iProviderUserId, int iUserId, String nvLogo, String nvComment, ProviderServicesObj[] providerServiceObj, String dtDateOrder) {
        this.iWaitingForServiceId = iWaitingForServiceId;
        this.iProviderUserId = iProviderUserId;
        this.iUserId = iUserId;
        this.nvLogo = nvLogo;
        this.nvComment = nvComment;
        this.providerServiceObj = providerServiceObj;
        this.dtDateOrder = dtDateOrder;
    }

    public int getiWaitingForServiceId() {
        return iWaitingForServiceId;
    }

    public void setiWaitingForServiceId(int iWaitingForServiceId) {
        this.iWaitingForServiceId = iWaitingForServiceId;
    }

    public int getiProviderUserId() {
        return iProviderUserId;
    }

    public void setiProviderUserId(int iProviderUserId) {
        this.iProviderUserId = iProviderUserId;
    }

    public int getiUserId() {
        return iUserId;
    }

    public void setiUserId(int iUserId) {
        this.iUserId = iUserId;
    }

    public String getNvLogo() {
        return nvLogo;
    }

    public void setNvLogo(String nvLogo) {
        this.nvLogo = nvLogo;
    }

    public String getNvComment() {
        return nvComment;
    }

    public void setNvComment(String nvComment) {
        this.nvComment = nvComment;
    }

    public ProviderServicesObj[] getProviderServiceObj() {
        return providerServiceObj;
    }

    public void setProviderServiceObj(ProviderServicesObj[] providerServiceObj) {
        this.providerServiceObj = providerServiceObj;
    }

    public String getDtDateOrder() {
        return dtDateOrder;
    }

    public void setDtDateOrder(String dtDateOrder) {
        this.dtDateOrder = dtDateOrder;
    }
}