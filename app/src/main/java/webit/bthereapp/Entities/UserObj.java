package webit.bthereapp.Entities;

//import io.realm.UserRealmRealmProxy;


import webit.bthereapp.DM.UserRealm;

/**
 * Created by User on 06/03/2016.
 */
public class UserObj {

    private double iUserId;
    private String nvUserName;
    private String nvFirstName;
    private String nvLastName;
    private String dBirthdate;
    private String nvMail;
    private String nvAdress;
    //    private int iCityType;
    private String nvPhone;
    private String nvPassword;
    private String nvVerification;
    private boolean bAutomaticUpdateApproval;
    private boolean bDataDownloadApproval;
    private boolean bTermOfUseApproval;
    private boolean bAdvertisingApproval;
    private int iUserStatusType;
    private String dMarriageDate;
    public int iCalendarViewType;
    //// TODO: 29/03/2016 delete the next property
    private int iUserType;

    private boolean bIsGoogleCalendarSync;

    private String nvImage;
    private int iCreatedByUserId;
    private int iLastModifyUserId;
    private int iSysRowStatus;

    private static UserObj objUser;

    public UserObj(){}

    public static UserObj getInstance(){
        if (objUser==null)
            objUser=new UserObj();
        return objUser;
    }
    public static void setInstance(UserObj objUser_){
//        if (objUser_!=null)
            objUser=objUser_;
    }
    public static void removeInstance() {
        objUser = null;
    }

    public UserObj(UserRealm user){
        this.iUserId = user.getUserID();
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
        this.bIsGoogleCalendarSync =user.isbIsGoogleCalendarSync();
        this.nvImage = user.getNvImageFilePath();
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

    public String getNvAdress() {
        return nvAdress;
    }

    public void setNvAdress(String nvAdress) {
        this.nvAdress = nvAdress;
    }

    public String getNvPhone() {
        return nvPhone;
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

    public String getNvImage() {
        return nvImage;
    }

    public void setNvImage(String nvImage) {
        this.nvImage = nvImage;
    }

    public static webit.bthereapp.Entities.UserObj getObjUser() {
        return objUser;
    }

    public static void setObjUser(webit.bthereapp.Entities.UserObj objUser) {
        webit.bthereapp.Entities.UserObj.objUser = objUser;
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

    public double getiUserId() {
        return iUserId;
    }

    public void setiUserId(Double iUserId) {
        this.iUserId = iUserId;
    }

    public int getiUserType() {
        return iUserType;
    }

    public void setiUserType(int iUserType) {
        this.iUserType = iUserType;
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
