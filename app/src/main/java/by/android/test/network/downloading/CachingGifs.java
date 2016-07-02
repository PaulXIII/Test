package by.android.test.network.downloading;

import android.graphics.Movie;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Павел on 02.07.2016.
 */
public class CachingGifs {

    private static CachingGifs instanceCache = new CachingGifs();

    private Map<String, Movie> mMemoryCache= Collections.synchronizedMap(new WeakHashMap<String, Movie>());

    public Map<String, Movie> getmMemoryCache() {
        return mMemoryCache;
    }

    private CachingGifs() {

    }

    public static CachingGifs getInstance() {
        if (instanceCache == null) {
            instanceCache = new CachingGifs();
        }
        return instanceCache;
    }

    public void addMovieToMemoryCache(String key, Movie movie) {
        if (getMovieFromMemCache(key) == null) {
            mMemoryCache.put(key, movie);
        }
    }

    public Movie getMovieFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public void clearCache()
    {
        mMemoryCache.clear();
    }

}
