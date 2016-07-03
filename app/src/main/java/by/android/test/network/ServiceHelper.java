package by.android.test.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Павел on 29.06.2016.
 */
public class ServiceHelper {

    private static ServiceHelper instance = new ServiceHelper();
    private Retrofit mRetrofit;

    private ServiceHelper() {
    }

    public static ServiceHelper getInstance() {
        if (instance == null) {
            instance = new ServiceHelper();
        }
        return instance;
    }


    public Retrofit getRetrofit() {

        if (mRetrofit != null) return mRetrofit;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(ConstantsNetwork.URLServer)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return mRetrofit;

    }

}
