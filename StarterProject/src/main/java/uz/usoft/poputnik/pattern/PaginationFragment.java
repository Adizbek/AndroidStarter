package uz.usoft.poputnik.pattern;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.adizbek.starterproject.BaseFragment;
import uz.adizbek.starterproject.api.ReqQueue;
import uz.usoft.poputnik.entity.response.PageResponse;

/**
 * Created by adizbek on 24.07.2018.
 */

public abstract class PaginationFragment<T> extends BaseFragment {

    private Call call;

    protected abstract RecyclerView getList();

    protected abstract SwipeRefreshLayout getRefreshLayout();

    protected abstract Call<PageResponse<T>> getCall(int page);

    protected abstract ItemAdapter getAdapter();

    @Override
    public void onDestroy() {
        ReqQueue.cancel(call);

        super.onDestroy();

    }

    protected void startPagination() {
        EndlessRecyclerOnScrollListener listener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                loadPage(currentPage);
            }

        };

        getRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hideNoResult();
                listener.resetPageCount(1);
                getAdapter().clear();
            }
        });

        getList().addOnScrollListener(listener);

        listener.resetPageCount(1);
    }

    protected void
    loadPage(int page) {
        getRefreshLayout().setRefreshing(true);

        call = getCall(page);
        call.enqueue(new Callback<PageResponse<T>>() {
            @Override

            public void onResponse(Call<PageResponse<T>> call, Response<PageResponse<T>> response) {
                PageResponse body = response.body();

                if (body != null) {
                    getAdapter().add(body.data);

                    if (page == 1 && body.data.size() == 0) {
                        showNoResult();
                    }
                }


                getRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onFailure(Call<PageResponse<T>> call, Throwable t) {
                getRefreshLayout().setRefreshing(false);
            }
        });
    }
}
