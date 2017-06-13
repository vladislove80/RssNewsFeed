package jomedia.com.rssnewsfeed.data.api;

import jomedia.com.rssnewsfeed.data.models.RssModel;
import jomedia.com.rssnewsfeed.utils.Utils;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApi {
    @GET(Utils.TOP_NEWS)
    Call<RssModel> getRssData();
}
