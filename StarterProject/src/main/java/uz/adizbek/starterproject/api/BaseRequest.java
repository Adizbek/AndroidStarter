package uz.adizbek.starterproject.api;

import retrofit2.Call;

/**
 * Created by adizbek on 2/23/18.
 */

public class BaseRequest<T> {

    public boolean finished = false, executing = false;
    private Call request;

    public void setFinished() {
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public static BaseRequest make(Call<?> call) {
        BaseRequest<?> r = new BaseRequest<>();
        r.request = call;

        return r;
    }

    public Call<?> getRequest() {
        return request.clone();
    }

    public boolean isExecuting() {
        return executing;
    }

    public void setExecuting(boolean executing) {
        this.executing = executing;
    }
}
