package webit.bthereapp.DM;

import io.realm.RealmObject;

/**
 * Created by User on 21/03/2016.
 */
public class FieldAndCategoryDM extends RealmObject  {
    //each field has several categories
    private int iFieldRowId;
    private String nvFieldName;
    private int iCategoryRowId;
    private String nvCategoryName;

    public FieldAndCategoryDM(){}

    public FieldAndCategoryDM(int iFieldRowId, String nvFieldName, int iCategoryRowId, String nvCategoryName) {
        this.iFieldRowId = iFieldRowId;
        this.nvFieldName = nvFieldName;
        this.iCategoryRowId = iCategoryRowId;
        this.nvCategoryName = nvCategoryName;
    }

    public int getiFieldRowId() {return iFieldRowId;
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

    public int getiCategoryRowId() {
        return iCategoryRowId;
    }

    public void setiCategoryRowId(int iCategoryRowId) {
        this.iCategoryRowId = iCategoryRowId;
    }

    public String getNvCategoryName() {
        return nvCategoryName;
    }

    public void setNvCategoryName(String nvCategoryName) {
        this.nvCategoryName = nvCategoryName;
    }
}
