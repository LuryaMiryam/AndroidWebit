package  webit.bthereapp.Entities;

import webit.bthereapp.Entities.Provider;

/**
 * Created by User on 26/07/2016.
 */
public class CouponTypeObj {

    private int iFieldRowId;
    private String nvFieldName;
    private int iCouponTypeRowId;
    private String nvCouponType;


    public CouponTypeObj() {
    }

    public CouponTypeObj(int iFieldRowId, String nvFieldName, int iCouponTypeRowId, String nvCouponType) {
        this.iFieldRowId = iFieldRowId;
        this.nvFieldName = nvFieldName;
        this.iCouponTypeRowId = iCouponTypeRowId;
        this.nvCouponType = nvCouponType;
    }

    public int getiFieldRowId() {
        return iFieldRowId;
    }

    public void setiFieldRowId(int iFieldRowId) {
        this.iFieldRowId = iFieldRowId;
    }

    public String getNvFieldName() {
        return nvFieldName;
    }

    public void setNvFieldName(String nvFieldName) {
        this.nvFieldName = nvFieldName;
    }

    public int getiCouponTypeRowId() {
        return iCouponTypeRowId;
    }

    public void setiCouponTypeRowId(int iCouponTypeRowId) {
        this.iCouponTypeRowId = iCouponTypeRowId;
    }

    public String getNvCouponType() {
        return nvCouponType;
    }

    public void setNvCouponType(String nvCouponType) {
        this.nvCouponType = nvCouponType;
    }
}
