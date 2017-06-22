package jomedia.com.rssnewsfeed.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;

import jomedia.com.rssnewsfeed.data.api.RestApi;
import jomedia.com.rssnewsfeed.data.api.RestManager;
import jomedia.com.rssnewsfeed.data.callback.NewsCallback;
import jomedia.com.rssnewsfeed.data.executor.TaskExecutor;
import jomedia.com.rssnewsfeed.data.models.DataResponse;
import jomedia.com.rssnewsfeed.data.models.NewsFeedItemModel;
import jomedia.com.rssnewsfeed.data.db.DatabaseSource;
import jomedia.com.rssnewsfeed.data.db.DatabaseSourceImpl;
import jomedia.com.rssnewsfeed.data.tasks.GetNewsTask;
import jomedia.com.rssnewsfeed.utils.Utils;

public class NewsRepositoryImpl implements NewsRepository {

    private final RestApi restApi;
    private final DatabaseSource diskDataSource;
    private final ExecutorService executorService;
    private final Handler mainUiHandler;

    public NewsRepositoryImpl(@NonNull Context context) {
        restApi = new RestManager().provideRestApi(Utils.BASE_URL);
        diskDataSource = new DatabaseSourceImpl(context);
        TaskExecutor taskExecutor = new TaskExecutor();
        executorService = taskExecutor.getThreadPoolExecutor();
        mainUiHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void getNewsItems(@NonNull NewsCallback<DataResponse> callback, @NonNull String link) {
        executorService.execute(new GetNewsTask(diskDataSource, restApi, mainUiHandler, callback, link));
    }
}
