package presenter;

/**
 * Interface that acts as presenter
 */
public interface Presenter <V> {

    void attachedView(V view);

    void detachView();

    void onResume(int currentPage);

    void onItemSelected(int position);

    void loadNext(int currentPage);
}