package network;

import io.reactivex.disposables.Disposable;

public interface NetworkManager {
    Disposable getFacts();
}
