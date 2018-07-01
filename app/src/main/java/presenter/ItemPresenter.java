package presenter;


import android.util.Log;

import event.FactsSuccessEvent;
import event.RequestFailureEvent;
import fetcher.ConnectivityChecker;
import fetcher.FactsFetcher;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import model.DataLoaderListener;
import model.ItemInteractor;
import model.ItemList;
import network.MyRxBus;
import view.ItemView;

/**
 * Class that defines presenter details
 */

public class ItemPresenter implements Presenter<ItemView>, DataLoaderListener {

    private ItemView itemView;
    private final ItemInteractor itemInteractor;
    private final FactsFetcher factsFetcher;
    private final ConnectivityChecker connectivityChecker;
    private Disposable disposable;
    private int currentPage;

    public ItemPresenter(ItemInteractor itemInteractor, FactsFetcher factsFetcher,
                         ConnectivityChecker connectivityChecker) {
        this.itemInteractor = itemInteractor;
        this.factsFetcher = factsFetcher;
        this.connectivityChecker = connectivityChecker;
        initSearch();
    }

    private void initSearch() {
        disposable = MyRxBus.instanceOf().toObservable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                if (o instanceof FactsSuccessEvent) {
                    if (currentPage == 1) {
                        onInitialItemsLoaded(((FactsSuccessEvent) o).getItemListResponse());
                    } else {
                        onNextItemsLoaded(((FactsSuccessEvent) o).getItemListResponse());
                    }
                } else if (o instanceof RequestFailureEvent) {
                    Log.d("Error:", ((RequestFailureEvent) o).getFailureReason());
                    showNetworkError();
                }
            }
        });
    }

    @Override
    public void attachedView(ItemView view) {
        if (view == null)
        throw new IllegalArgumentException("You can't set a null view");

        itemView = view;
    }

    @Override
    public void detachView() {
        itemView = null;
    }

    @Override
    public void onResume(int currentPage) {
        this.currentPage = currentPage;
        itemView.showProgress();
        if (connectivityChecker.isConnected())
            disposable = factsFetcher.getFacts();
        else
            showNetworkError();

        //itemInteractor.loadItems(currentPage, this);
    }

    @Override
    public void onItemSelected(int position) {
        itemView.showDetails(position);
    }

    @Override
    public void loadNext(int currentPage) {
        this.currentPage = currentPage;
        //itemInteractor.loadItems(currentPage, this);
    }

    @Override
    public void onInitialItemsLoaded(ItemList itemList) {
        if (itemView != null ) {
            itemView.setTitle(itemList.getTitle());
            itemView.setItems(itemList.getItemList());
            itemView.hideProgress();
            itemView.resetRefreshingLayout();
        }
    }

    @Override
    public void onNextItemsLoaded(ItemList itemList) {
        itemView.addItems(itemList.getItemList());
        itemView.resetRefreshingLayout();
    }

    private void showNetworkError() {
        itemView.hideProgress();
        itemView.resetRefreshingLayout();
        itemView.showNetworkError();
    }

    @Override
    public void activityPaused() {
        itemView.hideProgress();
        itemView.resetRefreshingLayout();
        disposable.dispose();
    }
}