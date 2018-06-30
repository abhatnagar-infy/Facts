package fetcher;

import io.reactivex.disposables.Disposable;

public interface FactsFetcher {
    Disposable getFacts();
}
