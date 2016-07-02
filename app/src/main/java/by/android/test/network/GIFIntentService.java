package by.android.test.network;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import by.android.test.MainActivity;
import by.android.test.SearchableActivity;
import by.android.test.network.downloading.CachingGifs;
import by.android.test.network.response.Datum;
import by.android.test.network.response.Meta;
import by.android.test.network.response.Result;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Павел on 29.06.2016.
 */
public class GIFIntentService extends IntentService {

    public static final String ACTION_GIFIntentService = "by.android.test.GIFIntentService.RESPONSE";
    private static final boolean DECODE_STREAM = true;

    private Retrofit mRetrofit = ServiceHelper.getInstance().getRetrofit();

    public GIFIntentService() {
        super(GIFIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent.getStringExtra(MainActivity.EXTRA_KEY_MAINACTIVITY_IN).equals(MainActivity.MAINACTIVITY_IN)) {
            downloadDeafualtGifs();
        }

        if (intent.getStringExtra(SearchableActivity.EXTRA_KEY_SEARCHABLEACTIVITY_IN).
                equals(SearchableActivity.SEARCHABLEACTIVITY_IN))
        {


            downloadSearchGifs();
        }


    }

    private void downloadDeafualtGifs() {
        IApiMethods myApi = mRetrofit.create(IApiMethods.class);
        Log.d("TAG", "GIFIntentService");
        myApi.getResults(ServiceHelper.PUBCLIC_KEY);

        Call<Result> call = myApi.getResults(ServiceHelper.PUBCLIC_KEY);
        try {
            Response<Result> resultResponse = call.execute();

            ArrayList<String> listUrls = new ArrayList<>();
            List<Datum> list = resultResponse.body().getData();
            String url = "";
            for (int i = 0; i < list.size(); i++) {
                url = list.get(i).getImages().getFixedHeight().getUrl();
                listUrls.add(url);
                CachingGifs.getInstance().addMovieToMemoryCache(url, getMovie(url));

            }

            Intent intentResponse = new Intent();
            intentResponse.setAction(ACTION_GIFIntentService);
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            intentResponse.putStringArrayListExtra(MainActivity.EXTRA_KEY_MAINACTIVITY_OUT, listUrls);
            Log.d("TAG", "sendBroadcast");
            sendBroadcast(intentResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadSearchGifs()
    {

    }

    private Movie getMovie(String url) {
        Movie gifMovie = null;
        int movieWidth = 0;
        int movieHeight = 0;
        long movieDuration = 0;

        try {
            URL gifURL = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) gifURL.openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            InputStream gifInputStream = connection.getInputStream();

            if (DECODE_STREAM) {
                gifMovie = Movie.decodeStream(gifInputStream);
            } else {
                byte[] array = streamToBytes(gifInputStream);
                gifMovie = Movie.decodeByteArray(array, 0, array.length);
            }

            movieWidth = gifMovie.width();
            movieHeight = gifMovie.height();
            movieDuration = gifMovie.duration();
            Log.d("TAG", "=================");
            Log.d("TAG", "Movie movieWidth is " + movieWidth);
            Log.d("TAG", "Movie movieHeight is " + movieHeight);
            Log.d("TAG", "Movie movieDuration is " + movieDuration);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return gifMovie;

    }

    private static byte[] streamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (java.io.IOException e) {
        }
        return os.toByteArray();
    }


}
