package androidproficiency.com.facts.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import androidproficiency.com.facts.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import fragment.ItemDetailFragment;
import fragment.ItemInteractionCallback;
import fragment.LinearVerticalFragment;
import interfaces.ISetTitle;
import model.Item;

/**
 * Main activity that hosts the fragment with the view
 */
public class MainActivity extends AppCompatActivity implements ItemInteractionCallback, ISetTitle {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, LinearVerticalFragment.newInstance()).commit();
    }

    @Override
    public void onItemInteraction(Item item) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        ItemDetailFragment itemDetailFragment = ItemDetailFragment.newInstance(item);
        itemDetailFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme);
        itemDetailFragment.show(mFragmentManager, "");
    }

    /***
     * Set title of the toolbar
     * @param title Title name
     */
    @Override
    public void setTitle(String title) {
        if(null != getSupportActionBar())
            getSupportActionBar().setTitle(title);
    }
}