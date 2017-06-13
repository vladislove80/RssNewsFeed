package jomedia.com.rssnewsfeed.data.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import jomedia.com.rssnewsfeed.BuildConfig;
import jomedia.com.rssnewsfeed.utils.Utils;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RestManager {
    private static final int CONNECT_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 10;
    private static final int MAX_SIZE = 4 * 1024 * 1024;
    private static final String HTTP_CACHE = "http-cache";
    private final OkHttpClient.Builder httpClientBuilder;

    public RestManager(Context context) {
        httpClientBuilder = new OkHttpClient.Builder()
                /*.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)*/
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        if (BuildConfig.DEBUG)
            httpClientBuilder.addInterceptor(logging);
        Cache cache = new Cache(new File(context.getCacheDir(), HTTP_CACHE), MAX_SIZE);
        httpClientBuilder.cache(cache);
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
