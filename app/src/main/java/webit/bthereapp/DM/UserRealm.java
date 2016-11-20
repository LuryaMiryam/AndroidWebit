package webit.bthereapp.DM;

/**
 * Created by User on 14/03/2016.
 */

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import webit.bthereapp.Entities.UserObj;

public class UserRealm extends RealmObject {

    private double iUserId;
    private String nvUserName;
    private String nvFirstName;
    private String nvLastName;
    private String dBirthdate;
    private String nvMail;
    private String nvAdress;
    private String nvPhone;
    private String nvPassword;
    private String nvVerification;
    private boolean bAutomaticUpdateApproval;
    private boolean bDataDownloadApproval;
    private boolean bTermOfUseApproval;
    private boolean bAdvertisingApproval;
    private int iUserStatusType;
    private boolean bIsGoogleCalendarSync;

    private String nvImageFilePath;
    private int iCreatedByUserId;
    private int iLastModifyUserId;
    private int iSysRowStatus;
    private String dMarriageDate;
    public int iCalendarViewType;


    public UserRealm() {
    }

    public UserRealm(double iUserId, String nvUserName, String nvFirstName, String nvLastName, String dBirthdate, String nvMail, String nvAdress, String nvPhone, String nvPassword, String nvVerification, boolean bAutomaticUpdateApproval, boolean bDataDownloadApproval, boolean bTermOfUseApproval, boolean bAdvertisingApproval, int iUserStatusType, boolean bIsGoogleCalendarSync, String nvImageFilePath, int iCreatedByUserId, int iLastModifyUserId, int iSysRowStatus, String dMarriageDate, int iCalendarViewType) {
        this.iUserId = iUserId;
        this.nvUserName = nvUserName;
        this.nvFirstName = nvFirstName;
        this.nvLastName = nvLastName;
        this.dBirthdate = dBirthdate;
        this.nvMail = nvMail;
        this.nvAdress = nvAdress;
        this.nvPhone = nvPhone;
        this.nvPassword = nvPassword;
        this.nvVerification = nvVerification;
        this.bAutomaticUpdateApproval = bAutomaticUpdateApproval;
        this.bDataDownloadApproval = bDataDownloadApproval;
        this.bTermOfUseApproval = bTermOfUseApproval;
        this.bAdvertisingApproval = bAdvertisingApproval;
        this.iUserStatusType = iUserStatusType;
        this.bIsGoogleCalendarSync = bIsGoogleCalendarSync;
        this.nvImageFilePath = nvImageFilePath;
        this.iCreatedByUserId = iCreatedByUserId;
        this.iLastModifyUserId = iLastModifyUserId;
        this.iSysRowStatus = iSysRowStatus;
        this.dMarriageDate = dMarriageDate;
        this.iCalendarViewType = iCalendarViewType;
    }

    public UserRealm(UserObj user) {
        this.iUserId = user.getiUserId();
        this.nvUserName = user.getNvUserName();
        this.nvFirstName = user.getNvFirstName();
        this.nvLastName = user.getNvLastName();
        this.dBirthdate = user.getdBirthdate();
        this.nvMail = user.getNvMail();
        this.nvAdress = user.getNvAdress();
        this.nvPhone = user.getNvPhone();
        this.nvPassword = user.getNvPassword();
        this.nvVerification = user.getNvVerification();
        this.bAutomaticUpdateApproval = user.isbAutomaticUpdateApproval();
        this.bDataDownloadApproval = user.isbDataDownloadApproval();
        this.bTermOfUseApproval = user.isbTermOfUseApproval();
        this.bAdvertisingApproval = user.isbAdvertisingApproval();
        this.iUserStatusType = user.getiUserStatusType();
        this.bIsGoogleCalendarSync = user.isbIsGoogleCalendarSync();
        this.nvImageFilePath = user.getNvImage();
        this.iCreatedByUserId = user.getiCreatedByUserId();
        this.iLastModifyUserId = user.getiLastModifyUserId();
        this.iSysRowStatus = user.getiSysRowStatus();
        this.dMarriageDate = user.getdMarriageDate();
        this.iCalendarViewType = user.getiCalendarViewType();
    }

    public String getNvUserName() {
        return nvUserName;
    }

    public void setNvUserName(String nvUserName) {
        this.nvUserName = nvUserName;
    }

    public String getNvFirstName() {
        return nvFirstName;
    }

    public void setNvFirstName(String nvFirstName) {
        this.nvFirstName = nvFirstName;
    }

    public String getNvLastName() {
        return nvLastName;
    }

    public void setNvLastName(String nvLastName) {
        this.nvLastName = nvLastName;
    }

    public String getNvMail() {
        return nvMail;
    }

    public void setNvMail(String nvMail) {
        this.nvMail = nvMail;
    }

    public String getdMarriageDate() {
        return dMarriageDate;
    }

    public void setdMarriageDate(String dMarriageDate) {
        this.dMarriageDate = dMarriageDate;
    }

    public int getiCalendarViewType() {
        return iCalendarViewType;
    }

    public void setiCalendarViewType(int iCalendarViewType) {
        this.iCalendarViewType = iCalendarViewType;
    }

    public String getNvAdress() {
        return nvAdress;
    }

    public void setNvAdress(String nvAdress) {
        this.nvAdress = nvAdress;
    }

    public String getNvPhone() {
        return nvPhone;
    }

    public void setNvPhone(String nvPhone) {
        this.nvPhone = nvPhone;
    }

    public String getNvPassword() {
        return nvPassword;
    }

    public void setNvPassword(String nvPassword) {
        this.nvPassword = nvPassword;
    }

    public String getNvVerification() {
        return nvVerification;
    }

    public void setNvVerification(String nvVerification) {
        this.nvVerification = nvVerification;
    }

    public boolean isbAutomaticUpdateApproval() {
        return bAutomaticUpdateApproval;
    }

    public void setbAutomaticUpdateApproval(boolean bAutomaticUpdateApproval) {
        this.bAutomaticUpdateApproval = bAutomaticUpdateApproval;
    }

    public boolean isbDataDownloadApproval() {
        return bDataDownloadApproval;
    }

    public void setbDataDownloadApproval(boolean bDataDownloadApproval) {
        this.bDataDownloadApproval = bDataDownloadApproval;
    }

    public boolean isbTermOfUseApproval() {
        return bTermOfUseApproval;
    }

    public void setbTermOfUseApproval(boolean bTermOfUseApproval) {
        this.bTermOfUseApproval = bTermOfUseApproval;
    }

    public int getiUserStatusType() {
        return iUserStatusType;
    }

    public void setiUserStatusType(int iUserStatusType) {
        this.iUserStatusType = iUserStatusType;
    }


    public String getNvImageFilePath() {
        return nvImageFilePath;
    }

    public void setNvImageFilePath(String nvImageFilePath) {
        this.nvImageFilePath = nvImageFilePath;
    }


    public int getiCreatedByUserId() {
        return iCreatedByUserId;
    }

    public void setiCreatedByUserId(int iCreatedByUserId) {
        this.iCreatedByUserId = iCreatedByUserId;
    }

    public int getiLastModifyUserId() {
        return iLastModifyUserId;
    }

    public void setiLastModifyUserId(int iLastModifyUserId) {
        this.iLastModifyUserId = iLastModifyUserId;
    }

    public int getiSysRowStatus() {
        return iSysRowStatus;
    }

    public void setiSysRowStatus(int iSysRowStatus) {
        this.iSysRowStatus = iSysRowStatus;
    }

    public double getUserID() {
        return iUserId;
    }

    public void setUserID(double userID) {
        iUserId = userID;
    }

    public String getdBirthdate() {
        return dBirthdate;
    }

    public void setdBirthdate(String dBirthdate) {
        this.dBirthdate = dBirthdate;
    }

    public boolean isbAdvertisingApproval() {
        return bAdvertisingApproval;
    }

    public void setbAdvertisingApproval(boolean bAdvertisingApproval) {
        this.bAdvertisingApproval = bAdvertisingApproval;
    }

    public boolean isbIsGoogleCalendarSync() {
        return bIsGoogleCalendarSync;
    }

    public void setbIsGoogleCalendarSync(boolean bIsGoogleCalendarSync) {
        this.bIsGoogleCalendarSync = bIsGoogleCalendarSync;
    }
}
