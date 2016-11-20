package webit.bthereapp.Entities;

import java.io.Serializable;

/**
 * Created by User on 16/06/2016.
 */
public class SearchResulstsObj    {
    public int iProviderId;
    public String nvProviderName;
    public String nvProviderSlogan;
    public String nvAdress;
    public float iDistance;
    public int iCustomerRank;
    public String nvProviderLogo;
    public String nvProviderHeader;
    public boolean bIsApprovedsupplier;

    private static SearchResulstsObj searchResulstsObj;

    public SearchResulstsObj(){}

    public static SearchResulstsObj getInstance(){
        if (searchResulstsObj==null)
            searchResulstsObj=new SearchResulstsObj();
        return searchResulstsObj;
    }
    public static void SearchResulstsObj(SearchResulstsObj searchResulstsObj_){
        if (searchResulstsObj_!=null)
            searchResulstsObj=searchResulstsObj_;
    }
    public static void setInstance(SearchResulstsObj searchResulstsObj_) {
//        if (searchResulstsObj_ != null)
            searchResulstsObj = searchResulstsObj_;
    }
    @Override
    public String toString() {
        return "SearchResulstsObj{" +
                "iProviderId=" + iProviderId +
                ", nvProviderName='" + nvProviderName + '\'' +
                ", nvProviderSlogan='" + nvProviderSlogan + '\'' +
                ", nvAdress='" + nvAdress + '\'' +
                ", iDistancepublic=" + iDistance +
                ", iCustomerRank=" + iCustomerRank +
                ", nvProviderLogo='" + nvProviderLogo + '\'' +
                ", nvProviderHeader='" + nvProviderHeader + '\'' +
                '}';
    }


}
