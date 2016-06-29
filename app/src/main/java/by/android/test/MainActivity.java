package by.android.test;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import by.android.test.UI.GIFView;
import by.android.test.network.GIFIntentService;
import by.android.test.network.InternetConnection;
import by.android.test.network.downloading.ImageProcessing;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static volatile boolean isViewReady;
    private static volatile boolean isConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GIFView imageView= (GIFView) findViewById(R.id.image_view);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "Service start");
//        Intent intentService = new Intent(this, GIFIntentService.class);
//        startService(intentService);
        Log.d("TAG", "Service end");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, MainActivity.class)));
        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, "Searching by: " + query, Toast.LENGTH_SHORT).show();

        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String uri = intent.getDataString();
            Toast.makeText(this, "Suggestion: " + uri, Toast.LENGTH_SHORT).show();
        }
    }


    protected void download(final GIFView image, final ProgressBar progress, final String link, final boolean fitHeight) {
        if (image == null || progress == null || link == null) {
            return;
        }
        if (!InternetConnection.isAvailable(this)) {
            progress.setVisibility(View.GONE);
            return;
        }
        if (!isViewReady) {
            /**
             * Global layout listener need for make sure that pre-draw listener will work with proper size
             */
            image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    isViewReady = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
        /**
         * Pre-draw listener used for get real image view dimensions
         */
        image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Log.i("TAG", "w: " + image.getMeasuredWidth() + ", h: " + image.getMeasuredHeight());
                if (isViewReady) {
                    image.getViewTreeObserver().removeOnPreDrawListener(this);
                    progress.setVisibility(View.VISIBLE);
                    ImageProcessing imageProcessing = new ImageProcessing(link, fitHeight, image, progress, MainActivity.this);
                    imageProcessing.execute();
                }
                return true;
            }
        });
    }
}
