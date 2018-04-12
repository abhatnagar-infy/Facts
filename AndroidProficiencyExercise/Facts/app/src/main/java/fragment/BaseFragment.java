package fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import adapter.AdapterExample;
import androidproficiency.com.facts.R;
import androidproficiency.com.facts.activity.MainActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import model.Item;
import model.ItemInteractor;
import presenter.ItemPresenter;
import presenter.RecyclerItemClickListener;
import utils.PaginationScrollListener;
import view.ItemView;
import widget.ItemOffsetDecoration;

/**
 * Created by Anubha on 11/04/18.
 */
public abstract class BaseFragment extends Fragment implements ItemView, RecyclerItemClickListener,
        SwipeRefreshLayout.OnRefreshListener{

    private static final int PAGE_START = 1;
    private static final int TOTAL_PAGES = 2;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView.Adapter adapter;
    private ItemPresenter itemPresenter;
    private LinearLayoutManager layoutManager;
    private boolean isLoading;
    private int currentPage = PAGE_START;
    private boolean isLastPage;
    private ItemInteractionCallback itemInteractionCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, rootView);

        itemPresenter = new ItemPresenter(new ItemInteractor(getActivity()));
        itemPresenter.attachedView(this);
        setupRecyclerView();

        setupSwipeRefreshLayout();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemPresenter.onResume(currentPage);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            itemInteractionCallback = (ItemInteractionCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ItemInteractionCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        itemInteractionCallback = null;
    }

    @Override
    public void onRefresh() {
        refreshRecyclerViewData();
    }

    private void refreshRecyclerViewData() {
        currentPage = PAGE_START;
        isLastPage = false;
        ((AdapterExample) adapter).clear();
        itemPresenter.onResume(currentPage);
    }

    @Override
    public void setItems(List<Item> itemList) {
        adapter = getAdapter(itemList);
        recyclerView.setAdapter(adapter);

        if (adapter instanceof AdapterExample) {
            ((AdapterExample) adapter).setRecyclerItemClickListener(this);

            if (currentPage <= TOTAL_PAGES) ((AdapterExample) adapter).addLoadingFooter();
            else isLastPage = true;
        }
    }

    @Override
    public void addItems(List<Item> itemList) {
        if (adapter instanceof AdapterExample) {
            ((AdapterExample) adapter).removeLoadingFooter();
            isLoading = false;

            if (adapter instanceof AdapterExample) {
                ((AdapterExample) adapter).setRecyclerItemClickListener(this);
                ((AdapterExample) adapter).addAll(itemList);

                if (currentPage != TOTAL_PAGES) ((AdapterExample) adapter).addLoadingFooter();
                else isLastPage = true;
            }
        }
    }
    @Override
    public void setTitle(String title) {
        ((MainActivity) getActivity()).setToolBarTitle(title);
    }

    @Override
    public void showProgress() {
        if(!swipeRefreshLayout.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDetails(int position) {
        Item item = ((AdapterExample) recyclerView.getAdapter()).getItem(position);
        itemInteractionCallback.onItemInteraction(item);
    }

    @Override
    public void resetRefreshingLayout() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        itemPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onItemClickListener(int position) {
        itemPresenter.onItemSelected(position);
    }

    private void setupRecyclerView() {
        if (getLayoutManager() != null) {
            layoutManager = (LinearLayoutManager) getLayoutManager();
            recyclerView.setLayoutManager(layoutManager);
        }

        recyclerView.addItemDecoration(new ItemOffsetDecoration(recyclerView.getContext(), R.dimen.item_decoration));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        itemPresenter.loadNext(currentPage);
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }

    protected abstract int getLayout();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract RecyclerView.Adapter getAdapter(List<Item> items);
}