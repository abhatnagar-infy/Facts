package androidproficiency.com.facts.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import androidproficiency.com.facts.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import fragment.ItemInteractionCallback;
import fragment.LinearVerticalFragment;
import model.Item;

public class MainActivity extends AppCompatActivity implements ItemInteractionCallback {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings)
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/erikcaffrey/RecyclerView-Examples")));

        return super.onOptionsItemSelected(item);
    }

    /***
     * Set title of the toolbar
     * @param title Title name
     */
    public void setToolBarTitle(String title) {
        if(null != getSupportActionBar())
            getSupportActionBar().setTitle(title);
    }

    @Override
    public void onItemInteraction(Item item) {

    }
}
