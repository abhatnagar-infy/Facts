package model;

/**
 * Interface that loads data
 */
interface DataInteractor {
    void loadItems(int currentPage, DataLoaderListener loaderListener);
}