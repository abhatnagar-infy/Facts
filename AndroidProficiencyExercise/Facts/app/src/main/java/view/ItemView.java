package view;

import java.util.List;
import model.Item;

/**
 * Created by Anubha on 11/04/18.
 */
public interface ItemView {

    void setItems(List<Item> itemList);

    void addItems(List<Item> itemList);

    void setTitle(String title);

    void showProgress();

    void hideProgress();

    void showDetails(int position);

    void resetRefreshingLayout();
}