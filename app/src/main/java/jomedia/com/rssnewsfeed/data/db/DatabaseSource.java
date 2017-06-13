package jomedia.com.rssnewsfeed.data.db;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.util.List;

import jomedia.com.rssnewsfeed.data.models.Item;

public interface DatabaseSource {
    @WorkerThread
    void saveNews(@NonNull List<Item> news);

    @WorkerThread
    List<Item> getAllItems();
}
