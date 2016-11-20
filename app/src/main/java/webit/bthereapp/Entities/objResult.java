package webit.bthereapp.Entities;


/**
 * Created by User on 08/03/2016.
 */
public class objResult <T>{
    private Error Error;
    private T Result;

    public objResult(Error error, T t) {
        this.Error = error;
        this.Result = t;
    }
    public objResult() {

    }

    public objResult(T t) {
        this.Result = t;
    }

    public Error getError() {
        return Error;
    }

    public void setError(Error error) {
        this.Error = error;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T t) {
        this.Result = t;
    }


}
