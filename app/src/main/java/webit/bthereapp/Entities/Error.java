package webit.bthereapp.Entities;

/**
 * Created by User on 08/03/2016.
 */
public class Error {
    private int ErrorCode;
    private String ErrorMessage;

    public Error(int errorCode, String errorMessage) {
        ErrorCode = errorCode;
        ErrorMessage = errorMessage;
    }
    public Error(){}

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
