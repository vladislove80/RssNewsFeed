package jomedia.com.rssnewsfeed.db;

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
        public static final String IMAGE_LINK = "imagelink";
        public static final String TITLE = "title";
        public static final String NEWS_DESCRIPTION = "description";
        public static final String DATE = "date";
        public static final String AUTHOR = "author";
        public static final String NEWS_LINK = "newslink";
    }

    public static final class QUERY {

        static final String CREATE_TABLE_ITEMS = "CREATE TABLE "
                + TABLE.ITEMS
                + " ("
                + ITEM_FIELDS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEM_FIELDS.IMAGE_LINK + " TEXT, "
                + ITEM_FIELDS.TITLE + " TEXT, "
                + ITEM_FIELDS.NEWS_DESCRIPTION + " TEXT, "
                + ITEM_FIELDS.DATE + " TEXT, "
                + ITEM_FIELDS.AUTHOR + " TEXT, "
                + ITEM_FIELDS.NEWS_LINK + " TEXT "
                + ");";

        static final String DROP_TABLE_ITEMS = "DROP TABLE IF EXISTS " + TABLE.ITEMS + ";";
        static final String CLEAR_TABLE_ITEMS = "DELETE FROM " + TABLE.ITEMS + ";";
    }
}
