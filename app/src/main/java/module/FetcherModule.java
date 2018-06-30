package module;

import fetcher.FactsFetcher;
import fetcher.FactsFetcherImpl;

import static network.NetworkManagerModule.retrofitNetworkManager;

public class FetcherModule {

    public static FactsFetcher factsFetcher() {
        return new FactsFetcherImpl(retrofitNetworkManager());
    }
}
