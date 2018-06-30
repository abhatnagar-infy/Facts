package view;

import java.util.List;
import model.Item;

/**
 * Interface that provides methods for setting up list data
 */
public interface ItemView {

    void setItems(List<Item> itemList);

    void addItems(List<Item> itemList);

    void setTitle(String title);

    void showProgress();

    void hideProgress();

    void showDetails(int position);

    void resetRefreshingLayout();

    void showNetworkError();
}