package network;

import event.FactsSuccessEvent;
import event.RequestFailureEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import model.ItemList;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManagerImpl implements NetworkManager {

    private final String baseUrl;
    private final OkHttpClient okHttpClient;

    NetworkManagerImpl(String baseUrl, OkHttpClient okHttpClient) {
        this.baseUrl = baseUrl;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public Disposable getFacts() {
        return getFactsAdapter()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .getFacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) {
                        if (o instanceof ItemList) {
                            MyRxBus.instanceOf().publish(new FactsSuccessEvent((ItemList) o));
                        } else if (o instanceof Error) {
                            MyRxBus.instanceOf().publish(new RequestFailureEvent(((Error) o).getMessage()));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        MyRxBus.instanceOf().publish(new RequestFailureEvent(throwable.getLocalizedMessage()));
                    }
                });
    }

    private Retrofit.Builder getFactsAdapter() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient);
    }
}
