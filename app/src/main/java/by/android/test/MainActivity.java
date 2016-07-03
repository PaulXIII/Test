package by.android.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import by.android.test.UI.recyclerview.ImageAdapter;
import by.android.test.network.ConstantsNetwork;
import by.android.test.network.GIFIntentService;
import by.android.test.network.InternetConnection;
import by.android.test.network.downloading.CachingGifs;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private ImageAdapter mAdapter;
    private MyBroadcastReceiver myBroadcastReceiver;

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
        if (InternetConnection.isAvailable(this)) {
            Log.d("TAG", "Service start MainActivity");
            Intent intentService = new Intent(this, GIFIntentService.class);
            intentService.putExtra(ConstantsNetwork.EXTRA_KEY_MAINACTIVITY_IN, ConstantsNetwork.MAINACTIVITY_IN);
            startService(intentService);
            Log.d("TAG", "Service end MainActivity");

            myBroadcastReceiver = new MyBroadcastReceiver();

            //register BroadcastReceiver
            IntentFilter intentFilter = new IntentFilter(ConstantsNetwork.ACTION_GIFIntentService);
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            intentFilter.addCategory(ConstantsNetwork.CATEGORY_GIFIntentService);
            registerReceiver(myBroadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CachingGifs.getInstance().clearCache();
        unregisterReceiver(myBroadcastReceiver);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);


        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Toast.makeText(this, "Hello " + query, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, SearchableActivity.class);
        intent.putExtra(ConstantsNetwork.SEARCHABLE_QUERY, query);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            List<String> list = intent.getStringArrayListExtra(ConstantsNetwork.EXTRA_KEY_MAINACTIVITY_OUT);
            mAdapter.updateList(list);

        }
    }

}
