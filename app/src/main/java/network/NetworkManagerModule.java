package network;

import java.util.concurrent.TimeUnit;

import androidproficiency.com.facts.R;
import module.ApplicationModule;
import okhttp3.OkHttpClient;

public class NetworkManagerModule {
    private static final int TIMEOUT = 30;
    private static OkHttpClient okHttpClient;

    private static OkHttpClient okHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .retryOnConnectionFailure(true)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }

        return okHttpClient;
    }

    public static NetworkManager retrofitNetworkManager() {
        return new NetworkManagerImpl(ApplicationModule.getResources().getString(R.string.base_url), okHttpClient());
    }
}
