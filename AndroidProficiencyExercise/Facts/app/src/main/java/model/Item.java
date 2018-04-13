package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class that holds item data
 */
public class Item implements Serializable {
    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("imageHref")
    private String imagePath;

    public Item() {

    }
    public Item(String title, String description, String imagePath) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isItemNull() {
        return null == title
                && null == description
                && null == imagePath;
    }
}