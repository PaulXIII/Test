package by.android.test.network;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import by.android.test.network.response.Datum;
import by.android.test.network.response.Result;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Павел on 29.06.2016.
 */
public class GIFIntentService extends IntentService {

    private Retrofit mRetrofit = ServiceHelper.getInstance().getRetrofit();

    public GIFIntentService() {
        super(GIFIntentService.class.getName());
    }



    @Override
    protected void onHandleIntent(Intent intent) {


        IApiMethods myApi = mRetrofit.create(IApiMethods.class);
        Log.d("TAG", "GIFIntentService");
        myApi.getResults(ServiceHelper.PUBCLIC_KEY);

        Call<Result> call = myApi.getResults(ServiceHelper.PUBCLIC_KEY);
        try {
            Response<Result> resultResponse = call.execute();

            resultResponse.body().getData().get(0).getImages().getFixedHeight().getUrl();


            List<Datum> rs=resultResponse.body().getData();
            Log.d("TAG", "count1 is " +rs.get(0).getId());
            Log.d("TAG", "count " +resultResponse.body());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
