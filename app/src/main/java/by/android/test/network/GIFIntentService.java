package by.android.test.network;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.android.test.MainActivity;
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


    private Retrofit mRetrofit = ServiceHelper.getInstance().getRetrofit();

    public GIFIntentService() {
        super(GIFIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent.getStringExtra(MainActivity.EXTRA_KEY_MAINACTIVITY_IN).equals(MainActivity.MAINACTIVITY_IN)) {
            downloadDeafualtGifs();
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
            for (int i = 0; i < list.size(); i++) {
                listUrls.add(list.get(i).getImages().getFixedHeight().getUrl());
            }


            Intent intentResponse = new Intent();
            intentResponse.setAction(ACTION_GIFIntentService);
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            intentResponse.putStringArrayListExtra(MainActivity.EXTRA_KEY_MAINACTIVITY_OUT, listUrls);

            sendBroadcast(intentResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
