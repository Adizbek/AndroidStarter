package uz.adizbek.starterproject.api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adizbek on 2/23/18.
 */

public class ReqQueue {

    HashMap<Integer, BaseRequest> requests;

    public void run(ReqListener listener) {

        for (Integer id : requests.keySet()) {
            BaseRequest req = requests.get(id);

            if (req.isFinished()) continue;

            req.request.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    req.finished = true;

                    listener.onReqSuccess(id, response);
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    listener.onReqFail(id);
                }
            });
        }
    }

    public void setRequests(HashMap<Integer, BaseRequest> req) {
        requests = req;
    }

    public void hasFinished(int id) {
        requests.get(id).setFinished();
    }

}
