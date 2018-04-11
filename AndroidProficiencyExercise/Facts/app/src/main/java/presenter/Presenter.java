package presenter;

/**
 * Created by Anubha on 11/04/18.
 */
public interface Presenter <V> {

    void attachedView(V view);

    void detachView();

    void onResume(int currentPage);

    void onItemSelected(int position);

    void loadNext(int currentPage);
}