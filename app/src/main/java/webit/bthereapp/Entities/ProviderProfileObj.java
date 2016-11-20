package webit.bthereapp.Entities;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.DM.BusinessProfileRealm;

/**
 * Created by User on 11/04/2016.
 */
public class ProviderProfileObj {

    private String nvSlogen;
    private double iProviderUserId;
    private String nvILogoImage;
    private String nvHeaderImage;
    private String nvFooterImage;
    private String nvCampaignImage;
    private String nvAboutComment;

    public List<WorkingHours> getObjWorkingHours() {
        return objWorkingHours;
    }

    public void setObjWorkingHours(List<WorkingHours> objWorkingHours) {
        this.objWorkingHours = objWorkingHours;
    }

    private List<WorkingHours> objWorkingHours=new ArrayList<>();
    private static ProviderProfileObj ProviderProfileObj;

    public ProviderProfileObj() {}

    public static ProviderProfileObj getInstance() {
        if(ProviderProfileObj ==null)
            ProviderProfileObj =new ProviderProfileObj();
        return ProviderProfileObj;
    }
    public static void setInstance(ProviderProfileObj providerProfile_Obj_){
//        if (providerProfile_Obj_ !=null)
            ProviderProfileObj = providerProfile_Obj_;
    }
    public static void removeInstance(){
            ProviderProfileObj = null;
    }


    public String getNvSlogen() {
        return nvSlogen;
    }

    public void setNvSlogen(String nvSlogen) {
        this.nvSlogen = nvSlogen;
    }

    public double getiProviderUserId() {
        return iProviderUserId;
    }

    public void setiProviderUserId(double iProviderUserId) {
        this.iProviderUserId = iProviderUserId;
    }

    public String getNvILogoImage() {
        return nvILogoImage;
    }

    public void setNvILogoImage(String nvILogoImage) {
        this.nvILogoImage = nvILogoImage;
    }

    public String getNvHeaderImage() {
        return nvHeaderImage;
    }

    public void setNvHeaderImage(String nvHeaderImage) {
        this.nvHeaderImage = nvHeaderImage;
    }

    public String getNvFooterImage() {
        return nvFooterImage;
    }

    public void setNvFooterImage(String nvFooterImage) {
        this.nvFooterImage = nvFooterImage;
    }

    public String getNvCampaignImage() {
        return nvCampaignImage;
    }

    public void setNvCampaignImage(String nvCampaignImage) {
        this.nvCampaignImage = nvCampaignImage;
    }

    public String getNvAboutComment() {
        return nvAboutComment;
    }

    public void setNvAboutComment(String nvAboutComment) {
        this.nvAboutComment = nvAboutComment;
    }

    public static ProviderProfileObj getProviderProfileObj() {
        return ProviderProfileObj;
    }

    public static void setProviderProfileObj(ProviderProfileObj providerProfileObj) {
        providerProfileObj.ProviderProfileObj = providerProfileObj;
    }
    public ProviderProfileObj(BusinessProfileRealm providerProfile) {
        this.iProviderUserId = providerProfile.getiProviderUserId();
        this.nvILogoImage = providerProfile.getNvILogoImage();
        this.nvHeaderImage = providerProfile.getNvHeaderImage();
        this.nvFooterImage = providerProfile.getNvFooterImage();
        this.nvCampaignImage = providerProfile.getNvCampaignImage();
        this.nvAboutComment = providerProfile.getNvAboutComment();
        this.nvSlogen = providerProfile.getNvSlogen();
    }
}
