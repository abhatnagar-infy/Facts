package androidproficiency.com.facts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import model.DataLoaderListener;
import model.Item;
import model.ItemInteractor;
import model.ItemList;
import presenter.ItemPresenter;
import view.ItemView;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Anubha on 12/04/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemPresenterTest {

    @Mock
    private ItemView itemView;
    @Mock
    private ItemInteractor itemInteractor;

    @Captor
    private ArgumentCaptor<DataLoaderListener> callbackArgumentCaptor;

    private ItemPresenter itemPresenter;

    private int currentPage = 1;
    private ItemList itemList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        itemPresenter = new ItemPresenter(itemInteractor);
        itemPresenter.attachedView(itemView);

        prepareData();
    }

    @After
    public void tearDown() throws Exception {
        itemPresenter.detachView();
    }

    private void prepareData() {
        Item item1 = new Item("TestTile1", "Test Description 1", "");
        Item item2 = new Item("TestTile2", "Test Description 2", "");
        Item item3 = new Item("TestTile3", "Test Description 3", "");
        Item item4 = new Item("TestTile4", "Test Description 4", "");
        ArrayList<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        itemList = new ItemList("Title", items);
    }

    @Test
    public void onResume() throws Exception {
        itemPresenter.onResume(currentPage);
        verify(itemView, times(1)).showProgress();
        verify(itemInteractor, times(1)).loadItems(eq(currentPage), callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onInitialItemsLoaded(itemList);
    }

    @Test
    public void loadNext() throws Exception {
        currentPage++;
        itemPresenter.loadNext(currentPage);
        verify(itemInteractor, times(1)).loadItems(eq(currentPage), callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onNextItemsLoaded(itemList);
    }

    @Test
    public void onItemSelected() throws Exception {
        itemPresenter.onItemSelected(2);
        verify(itemView).showDetails(2);
    }

    @Test
    public void onInitialItemsLoaded() throws Exception {
        itemPresenter.onInitialItemsLoaded(itemList);
        verify(itemView).setTitle(itemList.getTitle());
        verify(itemView).setItems(itemList.getItemList());
        verify(itemView).hideProgress();
        verify(itemView).resetRefreshingLayout();
    }

    @Test
    public void onNextItemsLoaded() throws Exception {
        itemPresenter.onNextItemsLoaded(itemList);
        itemView.addItems(itemList.getItemList());
        itemView.resetRefreshingLayout();
    }
}