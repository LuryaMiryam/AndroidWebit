package webit.bthereapp.Entities;

/**
 * Created by 1 on 4/4/2016.
 */
public class SysAlertsList {

    private int iSysTableRowId;
    private int iTableId;
    private String nvAletName;
    private String nvSysTableNameEng;

    public SysAlertsList(){}

    public SysAlertsList(int iSysTableRowId, int iTableId, String nvAletName, String nvSysTableNameEng) {
        this.iSysTableRowId = iSysTableRowId;
        this.iTableId = iTableId;
        this.nvAletName = nvAletName;
        this.nvSysTableNameEng = nvSysTableNameEng;
    }

    public String getNvAletName() {
        return nvAletName;
    }

    public void setNvAletName(String nvAletName) {
        this.nvAletName = nvAletName;
    }

    public int getiSysTableRowId() {
        return iSysTableRowId;
    }

    public void setiSysTableRowId(int iSysTableRowId) {
        this.iSysTableRowId = iSysTableRowId;
    }

    public int getiTableId() {
        return iTableId;
    }

    public void setiTableId(int iTableId) {
        this.iTableId = iTableId;
    }

    public String getNvSysTableNameEng() {
        return nvSysTableNameEng;
    }

    public void setNvSysTableNameEng(String nvSysTableNameEng) {
        this.nvSysTableNameEng = nvSysTableNameEng;
    }




}
