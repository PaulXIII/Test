package by.android.test;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Павел on 29.06.2016.
 */
public class SearchableActivity extends AppCompatActivity {

    public static final String EXTRA_KEY_SEARCHABLEACTIVITY_IN = "by.android.test.SearchableActivity.In";
    public static final String SEARCHABLEACTIVITY_IN = "by.android.test.SearchableActivity.Value";
    public static final String SEARCHABLEACTIVITY_QUERY="by.android.test.SearchableActivity.Search";

    public static final String EXTRA_KEY_SEARCHABLEACTIVITY_OUT = "by.android.test.SearchableActivity.Out";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        String title = getIntent().getStringExtra(MainActivity.SEARCHABLE_QUERY);
        setTitle(title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        TextView txt = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            txt.setText("Searching by: " + query);

        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String uri = intent.getDataString();
            txt.setText("Suggestion: " + uri);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
