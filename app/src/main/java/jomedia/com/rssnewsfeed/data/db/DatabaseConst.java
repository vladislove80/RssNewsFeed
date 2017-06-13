package jomedia.com.rssnewsfeed.data.db;

public class DatabaseConst {
    public static final class DATABASE {
        public static final String NAME = "news_items_db.db";
        public static final int VERSION = 1;
    }

    public static final class TABLE {
        public static final String ITEMS = "Items";
    }

    public static final class ITEM_FIELDS {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String LINK = "imagelink";
        public static final String GUID = "guid";
        public static final String PUB_DATE = "pubDate";
        public static final String AUTHOR = "author";
        public static final String CATEGORY = "category";
        public static final String DESCRIPTION = "description";
    }

    public static final class QUERY {

        static final String CREATE_TABLE_ITEMS = "CREATE TABLE "
                + TABLE.ITEMS
                + " ("
                + ITEM_FIELDS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEM_FIELDS.TITLE + " TEXT, "
                + ITEM_FIELDS.LINK + " TEXT, "
                + ITEM_FIELDS.GUID + " TEXT, "
                + ITEM_FIELDS.PUB_DATE + " TEXT, "
                + ITEM_FIELDS.AUTHOR + " TEXT, "
                + ITEM_FIELDS.CATEGORY + " TEXT, "
                + ITEM_FIELDS.DESCRIPTION + " TEXT "
                + ");";

        static final String DROP_TABLE_ITEMS = "DROP TABLE IF EXISTS " + TABLE.ITEMS + ";";
        static final String CLEAR_TABLE_ITEMS = "DELETE FROM " + TABLE.ITEMS + ";";
    }
}
