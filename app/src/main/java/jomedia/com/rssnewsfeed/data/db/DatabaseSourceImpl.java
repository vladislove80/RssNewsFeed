package jomedia.com.rssnewsfeed.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.List;

import jomedia.com.rssnewsfeed.data.models.Item;

public class DatabaseSourceImpl implements DatabaseSource {

    private static final long CLOSE_DB_DELAY_MILLIS = 60 * 1000L;

    private SQLiteDatabase database;

    private boolean isClosed;

    @NonNull
    private final Handler handler;
    @NonNull
    private final Runnable closeDbRunnable;
    @NonNull
    private final DatabaseHelper databaseHelper;

    public DatabaseSourceImpl(@NonNull Context context) {
        databaseHelper = new DatabaseHelper(context);
        isClosed = true;

        handler = new Handler();

        closeDbRunnable = new Runnable() {
            @Override
            public void run() {
                closeDB();
            }
        };
        openDB();
    }

    private void openDB() {
        open(0);
    }

    public synchronized void open(int attempt) {
        close();
        try {
            database = databaseHelper.getWritableDatabase();
            isClosed = false;
            setTime();
        } catch (Exception e) {
            e.printStackTrace();
            if (attempt < 3) {
                int attemptNext = attempt + 1;
                open(attemptNext);
            }
        }
    }

    private synchronized void setTime() {
        handler.removeCallbacks(closeDbRunnable);
        handler.postDelayed(closeDbRunnable, CLOSE_DB_DELAY_MILLIS);
    }

    private synchronized void close() {
        databaseHelper.close();
    }

    private void closeDB() {
        database.close();
        database = null;
        databaseHelper.close();
        isClosed = true;
    }

    @WorkerThread
    @Override
    public void saveNews(@NonNull List<Item> items, @NonNull String category) {
       deleteNewsOfCategory(category);
        if (!isClosed) {
            insertNews(items);
        } else {
            openDB();
            insertNews(items);
        }
        setTime();
    }

    private void insertNews(@NonNull List<Item> items) {
        SQLiteStatement stmt = database.compileStatement(
                String.format("INSERT INTO %s ('%s', '%s', '%s', '%s', '%s', '%s', '%s') VALUES(?1, ?2, ?3, ?4, ?5, ?6, ?7)",
                        DatabaseConst.TABLE.ITEMS,
                        DatabaseConst.ITEM_FIELDS.TITLE,
                        DatabaseConst.ITEM_FIELDS.LINK,
                        DatabaseConst.ITEM_FIELDS.GUID,
                        DatabaseConst.ITEM_FIELDS.PUB_DATE,
                        DatabaseConst.ITEM_FIELDS.AUTHOR,
                        DatabaseConst.ITEM_FIELDS.CATEGORY,
                        DatabaseConst.ITEM_FIELDS.DESCRIPTION
                        )
        );
        database.beginTransaction();
        for (Item item: items) {
            stmt.clearBindings();
            stmt.bindString(1, (item.getTitle() == null)? "":item.getTitle());
            stmt.bindString(2, (item.getLink() == null)? "":item.getLink());
            stmt.bindString(3, (item.getGuid() == null)? "":item.getGuid());
            stmt.bindString(4, (item.getPubDate() == null)? "":item.getPubDate());
            stmt.bindString(5, (item.getAuthor() == null)? "":item.getAuthor());
            stmt.bindString(6, (item.getCategory() == null)? "":item.getCategory());
            stmt.bindString(7, (item.getDescription() == null)? "":item.getDescription());
            stmt.executeInsert();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    @WorkerThread
    @Override
    public List<Item> getCategoryItems(String category) {
        List<Item> items;
        if (!isClosed) {
            items = itemsQuery(category);
        } else {
            openDB();
            items = itemsQuery(category);
        }
        setTime();
        return items;
    }

    private List<Item> itemsQuery(String category) {
        List<Item> items = null;
        Cursor cursor = database.query(DatabaseConst.TABLE.ITEMS, null, DatabaseConst.ITEM_FIELDS.CATEGORY + " =?", new String[]{category}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            items = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                Item item = parseCursorToItem(cursor);
                items.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return items;
    }

    @WorkerThread
    @Override
    public List<Item> getAllItems() {
        List<Item> items;
        if (!isClosed) {
            items = allItemsQuery();
        } else {
            openDB();
            items = allItemsQuery();
        }
        setTime();
        return items;
    }
    private List<Item> allItemsQuery() {
        List<Item> items = null;
        Cursor cursor = database.query(DatabaseConst.TABLE.ITEMS, null,null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            items = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                Item item = parseCursorToItem(cursor);
                items.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return items;
    }

    private void deleteNewsOfCategory(String category) {
        database.delete(DatabaseConst.TABLE.ITEMS, DatabaseConst.ITEM_FIELDS.CATEGORY + " =?", new String[]{category});
    }

    private Item parseCursorToItem(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.ID));
        String title = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.TITLE));
        String link = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.LINK));
        String guid = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.GUID));
        String pubDate = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.PUB_DATE));
        String author = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.AUTHOR));
        String category = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.CATEGORY));
        String description = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.DESCRIPTION));
        new Item(title, link, guid, pubDate, author, category, description);
        return new Item(title, link, guid, pubDate, author, category, description);
    }


}
