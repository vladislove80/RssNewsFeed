package jomedia.com.rssnewsfeed.data.api;

import jomedia.com.rssnewsfeed.data.models.RssModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RestApi {
    @GET
    Call<RssModel> getRssData(@Url String link);
}
