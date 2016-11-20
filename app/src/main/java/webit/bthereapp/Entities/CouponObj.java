package  webit.bthereapp.Entities;

import webit.bthereapp.Connection.ObjectConnection;

/**
 * Created by User on 26/07/2016.
 */
public class CouponObj extends ObjectConnection {
    private int iCouponId;
    private double iSupplierServiceId;
    private int iCouponType;
    private String nvCouponName;
    private String dDate;
    private String tFromHour;
    private String tToHour;

    public CouponObj(int iCouponId, double iSupplierServiceId, int iCouponType, String nvCouponName, String dDate, String tFromHour, String tToHour) {
        this.iCouponId = iCouponId;
        this.iSupplierServiceId = iSupplierServiceId;
        this.iCouponType = iCouponType;
        this.nvCouponName = nvCouponName;
        this.dDate = dDate;
        this.tFromHour = tFromHour;
        this.tToHour = tToHour;
    }

    public CouponObj(){}

    public int getiCouponId() {
        return iCouponId;
    }

    public void setiCouponId(int iCouponId) {
        this.iCouponId = iCouponId;
    }

    public double getiSupplierServiceId() {
        return iSupplierServiceId;
    }

    public void setiSupplierServiceId(double iSupplierServiceId) {
        this.iSupplierServiceId = iSupplierServiceId;
    }

    public int getiCouponType() {
        return iCouponType;
    }

    public void setiCouponType(int iCouponType) {
        this.iCouponType = iCouponType;
    }

    public String getNvCouponName() {
        return nvCouponName;
    }

    public void setNvCouponName(String nvCouponName) {
        this.nvCouponName = nvCouponName;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public String gettFromHour() {
        return tFromHour;
    }

    public void settFromHour(String tFromHour) {
        this.tFromHour = tFromHour;
    }

    public String gettToHour() {
        return tToHour;
    }

    public void settToHour(String tToHour) {
        this.tToHour = tToHour;
    }
}
