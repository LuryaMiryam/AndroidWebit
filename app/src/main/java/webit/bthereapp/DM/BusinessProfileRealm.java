package webit.bthereapp.DM;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import webit.bthereapp.Entities.ProviderProfileObj;
import webit.bthereapp.Entities.WorkingHours;

/**
 * Created by User on 28/03/2016.
 */
public class BusinessProfileRealm extends RealmObject {
    private double iProviderUserId;
    private String nvILogoImage;
    private String nvHeaderImage;
    private String nvFooterImage;
    private String nvCampaignImage;
    private String nvAboutComment;
    private String nvSlogen;


    public BusinessProfileRealm() {
    }

    public BusinessProfileRealm(double iProviderUserId, String nvILogoImage, String nvHeaderImage, String nvFooterImage, String nvCampaignImage, String nvAboutComment, String nvSlogen, List<WorkingHours> objWorkingHours) {
        this.iProviderUserId = iProviderUserId;
        this.nvILogoImage = nvILogoImage;
        this.nvHeaderImage = nvHeaderImage;
        this.nvFooterImage = nvFooterImage;
        this.nvCampaignImage = nvCampaignImage;
        this.nvAboutComment = nvAboutComment;
        this.nvSlogen = nvSlogen;
    }

    public BusinessProfileRealm(ProviderProfileObj providerProfile) {
        this.iProviderUserId = providerProfile.getiProviderUserId();
        this.nvILogoImage = providerProfile.getNvILogoImage();
        this.nvHeaderImage = providerProfile.getNvHeaderImage();
        this.nvFooterImage = providerProfile.getNvFooterImage();
        this.nvCampaignImage = providerProfile.getNvCampaignImage();
        this.nvAboutComment = providerProfile.getNvAboutComment();
        this.nvSlogen = providerProfile.getNvSlogen();
    }

    public double getiProviderUserId() {
        return iProviderUserId;
    }

    public void setiProviderUserId(double iProviderUserId) {
        this.iProviderUserId = iProviderUserId;
    }

    public String getNvSlogen() {
        return nvSlogen;
    }

    public void setNvSlogen(String nvSlogen) {
        this.nvSlogen = nvSlogen;
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
}
