package model;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interactor class for item data
 */

public class ItemInteractor implements DataInteractor {

    private final Context context;

    public ItemInteractor(Context context) {
        this.context = context;
    }

    /***
     * Using current page number to simulate lazy loading of list in case of large data
     * @param currentPage Page number to load
     * @param loaderListener callback
     */
    @Override
    public void loadItems(final int currentPage, final DataLoaderListener loaderListener) {
        if(currentPage == 1) {
            loaderListener.onInitialItemsLoaded(createDataSet("android_facts_1.json"));
        } else {
            loaderListener.onNextItemsLoaded(createDataSet("android_facts_2.json"));
        }
    }

    private String loadJsonFromAssets(String fileName) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private ItemList createDataSet(String fileName) {
        return new Gson().fromJson(loadJsonFromAssets(fileName), ItemList.class);
    }
}