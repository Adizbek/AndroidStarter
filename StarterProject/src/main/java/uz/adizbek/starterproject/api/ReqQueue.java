package uz.adizbek.starterproject.api;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adizbek on 2/23/18.
 */

public class ReqQueue {

    HashMap<Integer, BaseRequest> requests = new HashMap<>();

    public void run(ReqListener listener) {
        if (requests.size() <= 0) return;

        for (Integer id : requests.keySet()) {
            BaseRequest req = requests.get(id);

            if (req.isFinished() || req.isExecuting()) continue;

            req.setExecuting(true);
            Log.d("NETWORK", "QUEUE access to returned: " + req.getRequest().request().url().toString());


            req.getRequest().enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    req.finished = true;
                    req.setExecuting(false);

                    listener.onReqSuccess(id, response.body(), response);
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    req.setExecuting(false);
                    listener.onReqFail(id);
                }
            });
        }
    }

    public void addRequest(int id, Call req) {
        requests.put(id, BaseRequest.make(req));
    }

    public void setRequests(HashMap<Integer, BaseRequest> req) {
        requests = req;
    }

    public void hasFinished(int id) {
        requests.get(id).setFinished();
    }

    public void cancelAll() {
        for (BaseRequest baseRequest : requests.values()) {
            if (baseRequest.isExecuting() &&
                    !baseRequest.getRequest().isExecuted() &&
                    !baseRequest.getRequest().isCanceled()) {
                baseRequest.getRequest().cancel();
            }
        }
    }


    /**
     * Cancel this call, if it not executed
     */
    public static void cancel(Call call) {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }
}
