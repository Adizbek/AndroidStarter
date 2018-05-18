package uz.adizbek.starterproject.api;

import retrofit2.Response;

/**
 * Created by adizbek on 2/23/18.
 */

public interface ReqListener {
    void onReqSuccess(Integer id, Object data, Response response);

    void onReqFail(Integer id);
}
