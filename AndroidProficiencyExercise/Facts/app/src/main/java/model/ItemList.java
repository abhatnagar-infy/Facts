package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Model class that holds item list
 */
public class ItemList {
    @SerializedName("title")
    private final String title;
    @SerializedName("rows")
    private final ArrayList<Item> itemList;

    public ItemList(String title, ArrayList<Item> itemList) {
        this.title = title;
        this.itemList = itemList;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }
}