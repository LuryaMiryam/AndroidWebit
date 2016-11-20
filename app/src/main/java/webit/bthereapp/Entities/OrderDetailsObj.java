package webit.bthereapp.Entities;

import java.util.Calendar;

import webit.bthereapp.Connection.ObjectConnection;

/**
 * Created by User on 28/03/2016.
 */

public class OrderDetailsObj extends ObjectConnection {

    private int iCoordinatedServiceId;
    private int iProviderUserId;
    private String nvFirstName;
    private String nvSupplierName;
    private ProviderServiceDetailsObj[] objProviderServiceDetails;
    private int iDayInWeek;
    private String dtDateOrder;
    private String nvToHour;

    public int getiUserId() {
        return iUserId;
    }

    public void setiUserId(int iUserId) {
        this.iUserId = iUserId;
    }

    private int iUserId;

    public String getNvLogo() {
        return nvLogo;
    }

    public void setNvLogo(String nvLogo) {
        this.nvLogo = nvLogo;
    }

    private String nvLogo;
    private String nvAddress;

    public String getNvComment() {
        return nvComment;
    }

    public void setNvComment(String nvComment) {
        this.nvComment = nvComment;
    }

    private String nvComment;

    public OrderDetailsObj(int iCoordinatedServiceId, int iProviderUserId, String nvFirstName, String nvSupplierName, ProviderServiceDetailsObj[] objProviderServiceDetails, int iDayInWeek, String dtDateOrder, String nvToHour, int iUserId, String nvLogo, String nvAddress, String nvComment) {
        this.iCoordinatedServiceId = iCoordinatedServiceId;
        this.iProviderUserId = iProviderUserId;
        this.nvFirstName = nvFirstName;
        this.nvSupplierName = nvSupplierName;
        this.objProviderServiceDetails = objProviderServiceDetails;
        this.iDayInWeek = iDayInWeek;
        this.dtDateOrder = dtDateOrder;
        this.nvToHour = nvToHour;
        this.iUserId = iUserId;
        this.nvLogo = nvLogo;
        this.nvAddress = nvAddress;
        this.nvComment = nvComment;

    }

    public OrderDetailsObj(OrderDetailsObj orderDetailsObj_) {
        this.iCoordinatedServiceId = orderDetailsObj_.iCoordinatedServiceId;
        this.iProviderUserId = orderDetailsObj_.iProviderUserId;
        this.nvFirstName = orderDetailsObj_.nvFirstName;
        this.nvSupplierName = orderDetailsObj_.nvSupplierName;
        this.objProviderServiceDetails = orderDetailsObj_.objProviderServiceDetails;
        this.iDayInWeek = orderDetailsObj_.iDayInWeek;
        this.dtDateOrder = orderDetailsObj_.dtDateOrder;
        this.nvToHour = orderDetailsObj_.nvToHour;
        this.nvAddress = orderDetailsObj_.nvAddress;
        this.nvAddress = orderDetailsObj_.nvAddress;
        this.nvLogo = orderDetailsObj_.nvLogo;
        this.iUserId = orderDetailsObj_.iUserId;
    }




    public String getNvToHour() {
        return nvToHour;
    }

    public void setNvToHour(String nvToHour) {
        this.nvToHour = nvToHour;
    }
    public int getiCoordinatedServiceId() {
        return iCoordinatedServiceId;
    }

    public void setiCoordinatedServiceId(int iCoordinatedServiceId) {
        this.iCoordinatedServiceId = iCoordinatedServiceId;
    }

    public int getiProviderUserId() {
        return iProviderUserId;
    }

    public void setiProviderUserId(int iProviderUserId) {
        this.iProviderUserId = iProviderUserId;
    }

    public String getNvFirstName() {
        return nvFirstName;
    }

    public void setNvFirstName(String nvFirstName) {
        this.nvFirstName = nvFirstName;
    }

    public String getNvSupplierName() {
        return nvSupplierName;
    }

    public void setNvSupplierName(String nvSupplierName) {
        this.nvSupplierName = nvSupplierName;
    }

    public ProviderServiceDetailsObj[] getObjProviderServiceDetails() {
        return objProviderServiceDetails;
    }

    public void setObjProviderServiceDetails(ProviderServiceDetailsObj[] objProviderServiceDetails) {
        this.objProviderServiceDetails = objProviderServiceDetails;
    }

    public int getiDayInWeek() {
        return iDayInWeek;
    }

    public void setiDayInWeek(int iDayInWeek) {
        this.iDayInWeek = iDayInWeek;
    }

    public String getDtDateOrder() {
        return dtDateOrder;
    }

    public void setDtDateOrder(String dtDateOrder) {
        this.dtDateOrder = dtDateOrder;
    }

    public String getNvAddress() {
        return nvAddress;
    }

    public void setNvAddress(String nvAddress) {
        this.nvAddress = nvAddress;
    }

    public OrderDetailsObj() {
    }


}
