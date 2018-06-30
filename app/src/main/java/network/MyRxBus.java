package network;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class MyRxBus {

    private static MyRxBus instance;

    private PublishSubject<Object> publisher = PublishSubject.create();

    public static MyRxBus instanceOf() {
        if (instance == null) {
            instance = new MyRxBus();
        }
        return instance;
    }

    /**
     * Pass any event down to event listeners.
     */
    public void publish(Object object) {
        publisher.onNext(object);
    }

    /**
     * Subscribe to this Observable. On event, do something
     * e.g. replace a fragment
     */
    public Observable<Object> toObservable() {
        return publisher;
    }
}