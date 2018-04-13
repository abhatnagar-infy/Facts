package fragment;


import model.Item;

/**
 * Interface that listens item click
 */

public interface ItemInteractionCallback {
    void onItemInteraction(Item item);
}