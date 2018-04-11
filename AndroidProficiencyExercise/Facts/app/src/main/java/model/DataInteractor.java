package model;

/**
 * Created by Anubha on 11/04/18.
 */
public interface DataInteractor {
    void loadItems(int currentPage, DataLoaderListener loaderListener);
}