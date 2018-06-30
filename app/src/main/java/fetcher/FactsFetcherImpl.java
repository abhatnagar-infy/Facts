package fetcher;

import io.reactivex.disposables.Disposable;
import network.NetworkManager;

public class FactsFetcherImpl implements FactsFetcher {

    private final NetworkManager networkManager;

    public FactsFetcherImpl(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public Disposable getFacts() {
        return networkManager.getFacts();
    }
}
