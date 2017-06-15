package jomedia.com.rssnewsfeed.data.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import jomedia.com.rssnewsfeed.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RestManager {
    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 10;
    private final OkHttpClient.Builder httpClientBuilder;

    public RestManager() {
        httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        if (BuildConfig.DEBUG)
            httpClientBuilder.addInterceptor(logging);
    }

    public RestApi provideRestApi(String link) {
        return new Retrofit.Builder()
                .baseUrl(link)
                .client(httpClientBuilder.build())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(RestApi.class);
    }
}
