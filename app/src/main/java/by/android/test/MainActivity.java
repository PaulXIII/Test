package by.android.test;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.android.test.UI.GIFView;
import by.android.test.UI.recyclerview.ImageAdapter;
import by.android.test.network.GIFIntentService;
import by.android.test.network.InternetConnection;
import by.android.test.network.UrlsGifs;
import by.android.test.network.downloading.CachingGifs;
import by.android.test.network.downloading.ImageProcessing;
import by.android.test.network.response.Result;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    public static final String EXTRA_KEY_MAINACTIVITY_IN = "by.android.test.MainActivity.In";
    public static final String MAINACTIVITY_IN = "by.android.test.MainActivity.Value";
    public static final String SEARCHABLE_QUERY="by.android.test.MainActivity.Search";

    public static final String EXTRA_KEY_MAINACTIVITY_OUT = "by.android.test.MainActivity.Out";

    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ImageAdapter();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        if (mRecyclerView != null) {
            mRecyclerView.swapAdapter(mAdapter, false);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }



    }


    @Override
    protected void onResume() {
        super.onResume();
        if (CachingGifs.getInstance().getmMemoryCache().isEmpty())
        {
            Log.d("TAG", "Service start");
            Intent intentService = new Intent(this, GIFIntentService.class);
            intentService.putExtra(EXTRA_KEY_MAINACTIVITY_IN, MAINACTIVITY_IN);
            startService(intentService);
            Log.d("TAG", "Service end");
        }

        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();

        //register BroadcastReceiver
        IntentFilter intentFilter = new IntentFilter(GIFIntentService.ACTION_GIFIntentService);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(myBroadcastReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CachingGifs.getInstance().clearCache();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(
//                new ComponentName(this, SearchableActivity.class)));
        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Toast.makeText(this, "Hello "+query, Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this,SearchableActivity.class);
        intent.putExtra(SEARCHABLE_QUERY,query);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            Toast.makeText(this, "Searching by: " + query, Toast.LENGTH_SHORT).show();
//
//        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
//            String uri = intent.getDataString();
//            Toast.makeText(this, "Suggestion: " + uri, Toast.LENGTH_SHORT).show();
//        }
//    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            List<String> list= intent.getStringArrayListExtra(EXTRA_KEY_MAINACTIVITY_OUT);
            mAdapter.updateList(list);

        }
    }

}
