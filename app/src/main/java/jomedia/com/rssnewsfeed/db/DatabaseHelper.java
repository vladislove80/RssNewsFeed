package jomedia.com.rssnewsfeed.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import jomedia.com.rssnewsfeed.utils.Utils;

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context) {
        super(context, DatabaseConst.DATABASE.NAME, null, DatabaseConst.DATABASE.VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        createNewTables(db);
    }

    private void createNewTables(SQLiteDatabase db) {
        db.execSQL(DatabaseConst.QUERY.CREATE_TABLE_ITEMS);
        Log.d(Utils.LOG, "Create table: Items");
    }

    private void deleteOldTables(SQLiteDatabase db) {
        db.execSQL(DatabaseConst.QUERY.DROP_TABLE_ITEMS);

    }

    public void clearTable(SQLiteDatabase db){
        db.execSQL(DatabaseConst.QUERY.CLEAR_TABLE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteOldTables(db);
        createNewTables(db);
    }
}
