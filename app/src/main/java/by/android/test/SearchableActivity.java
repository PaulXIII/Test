package by.android.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import by.android.test.UI.recyclerview.SearchableImageAdapter;
import by.android.test.network.ConstantsNetwork;
import by.android.test.network.GIFIntentService;
import by.android.test.network.InternetConnection;
import by.android.test.network.downloading.CachingGifs;

/**
 * Created by Павел on 29.06.2016.
 */
public class SearchableActivity extends AppCompatActivity {


    private String title;
    private SearchableImageAdapter mAdapter;
    private MyBroadcastReceiverForSearch myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        title = getIntent().getStringExtra(ConstantsNetwork.SEARCHABLE_QUERY);
        setTitle(title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        mAdapter = new SearchableImageAdapter();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearch);
        if (mRecyclerView != null) {
            mRecyclerView.swapAdapter(mAdapter, false);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (InternetConnection.isAvailable(this)) {
            Log.d("TAG", "Service start SearchableActivity");
            Intent intentService = new Intent(this, GIFIntentService.class);
            intentService.putExtra(ConstantsNetwork.EXTRA_KEY_MAINACTIVITY_IN, ConstantsNetwork.SEARCHABLEACTIVITY_IN);
            intentService.putExtra(ConstantsNetwork.SEARCHABLEACTIVITY_QUERY, title);
            startService(intentService);
            Log.d("TAG", "Service end SearchableActivity");

            myBroadcastReceiver = new MyBroadcastReceiverForSearch();

    //      register BroadcastReceiver
            IntentFilter intentFilter = new IntentFilter(ConstantsNetwork.ACTION_SERACH_GIFIntentService);
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            intentFilter.addCategory(ConstantsNetwork.CATEGORY_SEARCH_GIFIntentService);
            registerReceiver(myBroadcastReceiver, intentFilter);
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CachingGifs.getInstance().clearSearchableCache();
        unregisterReceiver(myBroadcastReceiver);
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

    public class MyBroadcastReceiverForSearch extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            List<String> list = intent.getStringArrayListExtra(ConstantsNetwork.EXTRA_KEY_SEARCHABLEACTIVITY_OUT);
            mAdapter.updateList(list);

        }
    }

}
