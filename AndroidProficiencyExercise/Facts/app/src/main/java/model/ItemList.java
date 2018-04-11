package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemList {
    @SerializedName("title")
    private String title;
    @SerializedName("rows")
    private ArrayList<Item> itemList;

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