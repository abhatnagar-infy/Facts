package event;

import model.ItemList;

public class FactsSuccessEvent {
    private final ItemList itemListResponse;

    public ItemList getItemListResponse() {
        return itemListResponse;
    }

    public FactsSuccessEvent(ItemList itemListResponse) {
        this.itemListResponse = itemListResponse;
    }
}
