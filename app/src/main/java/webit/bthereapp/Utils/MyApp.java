package webit.bthereapp.Utils;


import android.app.Application;
import android.content.Context;

/**
 * Created by User on 05/04/2016.
 */
public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
