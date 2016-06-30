package by.android.test.network;

import by.android.test.network.response.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Павел on 29.06.2016.
 */
public interface IApiMethods {

        @GET("v1/gifs/trending")
        Call<Result> getResults(@Query("api_key") String key);


}
