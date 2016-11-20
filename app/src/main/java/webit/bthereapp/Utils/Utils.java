package webit.bthereapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.R;

/**
 * Created by User on 01/03/2016.
 */
public class Utils<T> {

    public static Realm realm;

    public static Realm getRealmInstance(Context context) {

        Context mContext;
        if (MyApp.getContext() == null)
            mContext = context;
        else mContext = MyApp.getContext();
        if (realm == null) {
            RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).deleteRealmIfMigrationNeeded().build();
            Utils.realm = Realm.getInstance(realmConfig);
        }
        if (realm != null && realm.isInTransaction())
            realm.commitTransaction();
        return realm;
    }

    public static void exitAppAlert(final Context context/* ,final Fragment fragment*/) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(R.string.exit);
        alertDialogBuilder
                .setMessage(R.string.exit_question)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

