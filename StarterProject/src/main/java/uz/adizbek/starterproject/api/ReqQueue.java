package uz.adizbek.starterproject.api;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
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

            runRequest(id, req, listener);
        }
    }

    private void runRequest(int id, BaseRequest req, ReqListener listener) {
        if (req.isFinished() || req.isExecuting()) return;

        req.setExecuting(true);
        Log.d("NETWORK", "QUEUE access to returned: " + req.getRequest().request().url().toString());


        req.getRequest().enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                req.finished = true;
                req.setExecuting(false);

                if (response.errorBody() != null) {
                    try {
                        Log.w("Network", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Object obj = response.body();

                // TODO handle null
                listener.onReqSuccess(id, obj, response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                req.setExecuting(false);
                listener.onReqFail(id);
            }
        });
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

    public void restart(int id, ReqListener reqListener) {
        BaseRequest t = requests.get(id);

        t.setExecuting(false);
        t.finished = false;

        runRequest(id, t, reqListener);
    }

    public void replace(int id, Call call) {
        BaseRequest req = requests.get(id);

        if (req != null) {
            req.getRequest();
            ReqQueue.cancel(req.getRequest());

            requests.remove(id);
        }

        requests.put(id, BaseRequest.make(call));
    }
}
