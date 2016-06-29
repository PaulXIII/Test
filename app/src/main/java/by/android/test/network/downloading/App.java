package by.android.test.network.downloading;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.SparseArray;

/**
 * Created by Павел on 29.06.2016.
 */
public class App extends Application {

    /**
     * Using memory cache for reuse received bitmaps during app lifecycle
     */
    SparseArray<Bitmap> cache = new SparseArray<>();
}
