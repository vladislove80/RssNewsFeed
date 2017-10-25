package jomedia.com.rssnewsfeed.data.db;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import jomedia.com.rssnewsfeed.BuildConfig;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DatabaseHelperTest {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Before
    public void setup(){
        dbHelper = new DatabaseHelper(RuntimeEnvironment.application);
        database = dbHelper.getWritableDatabase();
    }

    @Test
    public void testPreConditions() throws Exception {
        Assert.assertNotNull(dbHelper);
    }

    @Test
    public void test_onUpgrade() {
        dbHelper.onUpgrade(database, DatabaseConst.DATABASE.VERSION, 2);
        long numberOfRows = DatabaseUtils.queryNumEntries(database, DatabaseConst.TABLE.ITEMS);
        Assert.assertEquals(0, numberOfRows);
    }

    @After
    public void tearDown() throws Exception {
        dbHelper.close();
    }
}