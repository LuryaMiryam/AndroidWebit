package webit.bthereapp.Entities;

public class FieldAndCategory {


    //each field has several categories

    private int iFieldRowId;
    private int iCategoryRowId;
    private String nvFieldName;
    private String nvCategoryName;

    public FieldAndCategory() {
    }

    public FieldAndCategory(int iFieldRowId, int iCategoryRowId, String nvFieldName, String nvCategoryName) {
        this.iFieldRowId = iFieldRowId;
        this.iCategoryRowId = iCategoryRowId;
        this.nvFieldName = nvFieldName;
        this.nvCategoryName = nvCategoryName;
    }

    public int getiFieldRowId() {
        return iFieldRowId;
    }

    public void setiFieldRowId(int iFieldRowId) {
        this.iFieldRowId = iFieldRowId;
    }

    public int getiCategoryRowId() {
        return iCategoryRowId;
    }

    public void setiCategoryRowId(int iCategoryRowId) {
        this.iCategoryRowId = iCategoryRowId;
    }

    public String getNvFieldName() {
        return nvFieldName;
    }

    public void setNvFieldName(String nvFieldName) {
        this.nvFieldName = nvFieldName;
    }

    public String getNvCategoryName() {
        return nvCategoryName;
    }

    public void setNvCategoryName(String nvCategoryName) {
        this.nvCategoryName = nvCategoryName;
    }
}
