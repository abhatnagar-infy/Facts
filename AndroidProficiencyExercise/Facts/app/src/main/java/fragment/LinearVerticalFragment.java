package fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import adapter.AdapterExample;
import androidproficiency.com.facts.R;
import model.Item;

/**
 * Fragment that displays the list
 */
public class LinearVerticalFragment extends BaseFragment {

    public static LinearVerticalFragment newInstance() {
        return new LinearVerticalFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return getLinearLayoutManager();
    }

    @Override
    protected RecyclerView.Adapter getAdapter(List<Item> itemList) {
        return new AdapterExample(getActivity(), itemList, R.layout.item_type_main);
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
    }
}