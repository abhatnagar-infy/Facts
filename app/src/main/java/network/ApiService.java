package network;

import io.reactivex.Observable;
import model.ItemList;
import retrofit2.http.GET;

public interface ApiService {
    @GET("s/42hltw3wgi7dncl/android_facts_1.json?raw=1")
    Observable<ItemList> getFacts();
}
