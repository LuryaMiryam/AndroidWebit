package webit.bthereapp.Camera;

import android.widget.Toast;

/**
 * Created by user on 27/07/2015.
 */
public class CameraLauncherArguments {

    public enum ESourceType{
        CAMERA, GALLERY
    }


    public ESourceType eSourceType;
    public int targetWidth;                // desired width of the image
    public int targetHeight;               // desired height of the image
    private boolean saveToPhotoAlbum = false;       // Should the picture be saved to the device'su photo album
    public boolean allowEdit = false;              // Should we allow the user to crop the image.

    public CameraLauncherArguments(ESourceType sourceType){
        this.eSourceType = sourceType;
    }

    public CameraLauncherArguments(int width, int height, boolean allowEdit, ESourceType sourceType) {
        this.targetWidth = width;
        this.targetHeight = height;
        this.allowEdit = allowEdit;
        this.eSourceType = sourceType;
        this.eSourceType = sourceType;

    }

    public CameraLauncherArguments() {

    }
}
