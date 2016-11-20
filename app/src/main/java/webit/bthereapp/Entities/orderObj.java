package webit.bthereapp.Entities;

import webit.bthereapp.Connection.ObjectConnection;

/**
 * Created by User on 28/03/2016.
 */

public class orderObj extends ObjectConnection {

    public double getiUserId() {
        return iUserId;
    }

    public void setiUserId(double iUserId) {
        this.iUserId = iUserId;
    }

    private double iUserId;
    private int iSupplierId;
    private int[] iSupplierUserId;
    private int[] iProviderServiceId;
    private String dtDateOrder;
    private String nvFromHour;
    private String nvComment="";
    private static orderObj orderObj_;


    public orderObj() {
    }

    public orderObj(double iUserId, int iSupplierId, int[] iSupplierUserId, int[] iProviderServiceId, String dtDateOrder, String nvFromHour, String nvComment) {
        this.iUserId = iUserId;
        this.iSupplierId = iSupplierId;
        this.iSupplierUserId = iSupplierUserId;
        this.iProviderServiceId = iProviderServiceId;
        this.dtDateOrder = dtDateOrder;
        this.nvFromHour = nvFromHour;
        this.nvComment = nvComment="";
    }

    public static orderObj getInstance() {
        if (orderObj_ == null)
            orderObj_ = new orderObj();
        return orderObj_;
    }

    public static void setInstance(orderObj provider_) {
//        if (provider_ != null)
            orderObj_ = provider_;
    }

    public int getiSupplierId() {
        return iSupplierId;
    }

    public void setiSupplierId(int iSupplierId) {
        this.iSupplierId = iSupplierId;
    }

    public int[] getiSupplierUserId() {
        return iSupplierUserId;
    }

    public void setiSupplierUserId(int[] iSupplierUserId) {
        this.iSupplierUserId = iSupplierUserId;
    }

    public int[] getiProviderServiceId() {
        return iProviderServiceId;
    }

    public void setiProviderServiceId(int[] iProviderServiceId) {
        this.iProviderServiceId = iProviderServiceId;
    }

    public String getDtDateOrder() {
        return dtDateOrder;
    }

    public void setDtDateOrder(String dtDateOrder) {
        this.dtDateOrder = dtDateOrder;
    }

    public String getNvFromHour() {
        return nvFromHour;
    }

    public void setNvFromHour(String nvFromHour) {
        this.nvFromHour = nvFromHour;
    }

    public String getNvComment() {
        return nvComment;
    }

    public void setNvComment(String nvComment) {
        this.nvComment = nvComment;
    }

    public static orderObj getOrderObj() {
        return orderObj_;
    }

    public static void setOrderObj(orderObj orderObj) {
        orderObj_ = orderObj;
    }
}
