package presenter;


import model.DataLoaderListener;
import model.ItemInteractor;
import model.ItemList;
import view.ItemView;

/**
 * Class that defines presenter details
 */

public class ItemPresenter implements Presenter<ItemView>, DataLoaderListener {

    private ItemView itemView;
    private final ItemInteractor itemInteractor;

    public ItemPresenter(ItemInteractor itemInteractor) {
        this.itemInteractor = itemInteractor;
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
        itemView.showProgress();
        itemInteractor.loadItems(currentPage, this);
    }

    @Override
    public void onItemSelected(int position) {
        itemView.showDetails(position);
    }

    @Override
    public void loadNext(int currentPage) {
        itemInteractor.loadItems(currentPage, this);
    }

    @Override
    public void onInitialItemsLoaded(ItemList itemList) {
        itemView.setTitle(itemList.getTitle());
        itemView.setItems(itemList.getItemList());
        itemView.hideProgress();
        itemView.resetRefreshingLayout();
    }

    @Override
    public void onNextItemsLoaded(ItemList itemList) {
        itemView.addItems(itemList.getItemList());
        itemView.resetRefreshingLayout();
    }
}