package jomedia.com.rssnewsfeed.data.api;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import jomedia.com.rssnewsfeed.utils.Utils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RestFactory {
    private static final int CONNECT_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;

    @NonNull
    private static Retrofit getRetrofit(@NonNull String baseUrl) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .build();
    }

    @NonNull
    static RestApi getRestApi() {
        return getRetrofit(Utils.BASE_URL)
                .create(RestApi.class);
    }
}
