package webit.bthereapp.Connection;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class ObjectConnection {
    @Override
    public String toString() {
        return objectToJson(this);
    }

    public String objectToJson(Object object) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();//new Gson();
        return gson.toJson(object);
    }
}
