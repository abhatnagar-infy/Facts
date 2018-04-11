package fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import androidproficiency.com.facts.R;
import model.Item;

/**
 * Created by Anubha on 11/04/18.
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
    protected RecyclerView.Adapter getAdapter(List<Item> items) {
        return null;
    }


    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
    }
}