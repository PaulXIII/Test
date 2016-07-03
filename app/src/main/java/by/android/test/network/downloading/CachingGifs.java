package by.android.test.network.downloading;

import android.graphics.Movie;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Павел on 02.07.2016.
 */
public class CachingGifs {

    private static CachingGifs instanceCache = new CachingGifs();

    private Map<String, Movie> mMemoryCache= Collections.synchronizedMap(new WeakHashMap<String, Movie>());
    private Map<String, Movie> mSearchableMemoryCache= Collections.synchronizedMap(new WeakHashMap<String, Movie>());

    public Map<String, Movie> getmMemoryCache() {
        return mMemoryCache;
    }

    public Map<String, Movie> getmSearchableMemoryCache() {
        return mSearchableMemoryCache;
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
        if (getMovieFromMemoryCache(key) == null) {
            mMemoryCache.put(key, movie);
        }
    }

    public void addMovieToSearchableMemoryCache(String key, Movie movie) {
        if (getMovieFromMemoryCache(key) == null) {
            mMemoryCache.put(key, movie);
        }
    }

    public Movie getMovieFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    public Movie getMovieFromSearchableMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    public void clearCache()
    {
        mMemoryCache.clear();
    }

    public void clearSearchableCache()
    {
        mSearchableMemoryCache.clear();
    }
}
