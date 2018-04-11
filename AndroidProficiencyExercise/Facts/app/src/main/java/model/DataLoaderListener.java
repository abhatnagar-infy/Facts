package model;

/**
 * Created by Anubha on 11/04/18.
 */
public interface DataLoaderListener {
    void onInitialItemsLoaded(ItemList itemList);
    void onNextItemsLoaded(ItemList itemList);
}