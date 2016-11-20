package webit.bthereapp.Entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 20/03/2016.
 */
public class MainClass {
    private List<FieldAndCategory> FieldsAndCategories = new ArrayList<>();
    private static MainClass mainClass;
    private static double bussinessId;
    public MainClass(List<FieldAndCategory> fieldsAndCategories) {
        FieldsAndCategories = fieldsAndCategories;
    }

    public MainClass() {}

    public static MainClass getInstance(){
        if (mainClass==null)
            mainClass=new MainClass();
        return  mainClass;
    }
    public List<FieldAndCategory> getFieldsAndCategories() {
        return FieldsAndCategories;
    }

    public void setFieldsAndCategories(List<FieldAndCategory> fieldsAndCategories) {
        FieldsAndCategories = fieldsAndCategories;
    }

    public MainClass getMainClass() {
        return mainClass;
    }

    public void setMainClass(MainClass mainClass) {
        this.mainClass = mainClass;
    }

    public static double getBussinessId() {
        return bussinessId;
    }

    public static void setBussinessId(double bussinessId) {
        MainClass.bussinessId = bussinessId;
    }
}
