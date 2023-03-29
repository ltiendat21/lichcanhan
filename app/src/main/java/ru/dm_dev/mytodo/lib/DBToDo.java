package ru.dm_dev.mytodo.lib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DBToDo extends DBSQLite {

    /* Private field for store SQL WHERE for one element (by id) */
    private static final String SQL_WHERE_BY_ID = BaseColumns._ID + "=?";

    /* Public constant that store a name of data base */
    public static final String DB_NAME = "DBToDo.db";

    /* Public constant that store a version of data base */
    public static final int DB_VERSION = 1;

    /**
     * Constructor with one parameter that describe a link
     * to the Context object.
     * 	@param context	The context object.
     * */
    public DBToDo(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is
     * where the creation of tables and the initial population of the
     * tables should happen.
     * 	@param db	The database.
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {

		/* Create tables */
        DBSQLite.execSQL(db, TableLists.SQL_CREATE);
        DBSQLite.execSQL(db, TableTasks.SQL_CREATE);

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything
     * else it needs to upgrade to the new schema version.
     * @param db	The database.
     * @param oldVersion	The old database version.
     * @param newVersion	The new database version.
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		/* Drop tables */
        DBSQLite.dropTable(db, TableLists.T_NAME);
        DBSQLite.dropTable(db, TableTasks.T_NAME);

		/* Invoke onCreate method */
        this.onCreate(db);

    }

    /**
     * Add information about list to a data base
     * @param name	list name
     * @return id of new element
     * */
    public long addList(String name) {

		/* Create a new map of values, where column names are the keys */
        ContentValues v = new ContentValues();

		/* Fill values */
        v.put(TableLists.C_NAME, name);

		/* Add item to a data base */
        return this.getWritableDatabase().insert(TableLists.T_NAME, null, v);

    }

    /**
     * Update information about list into a data base.
     * @param name	new list name
     * @return True, if was been updated only one element
     * */
    public boolean updateList(String name, long id) {

		/* Create a new map of values, where column names are the keys */
        ContentValues v = new ContentValues();

		/* Fill values */
        v.put(TableLists.C_NAME, name);

		/* Update information */
        return 1 == this.getWritableDatabase().update(TableLists.T_NAME, v,
                SQL_WHERE_BY_ID, new String[] {String.valueOf(id)});
    }


    /**
     * Delete list from a data base.
     * @param id	of element that will be deleted
     * @return True, if was been deleted only one element
     * */
    public boolean deleteList(long id) {
        return 1 == this.getWritableDatabase().delete(
                TableLists.T_NAME, SQL_WHERE_BY_ID,
                new String[] {String.valueOf(id)});
    }

    public Cursor getList(long id) {
        return this.getReadableDatabase().query(TableLists.T_NAME,
                new String[] {TableLists.C_NAME, TableLists.C_TIME},
                SQL_WHERE_BY_ID,
                new String[] {String.valueOf(id)},
                null, null, null);
    }

    /**
     * Add information about list to a data base
     * @param name	task name
     * @return id of new element
     * */
    public long addTask(Long list_id, String name, int checked) {

		/* Create a new map of values, where column names are the keys */
        ContentValues v = new ContentValues();

		/* Fill values */
        v.put(TableTasks.C_NAME, name);
        v.put(TableTasks.C_LISTID, list_id);
        v.put(TableTasks.C_CHEC, checked);

		/* Add item to a data base */
        return this.getWritableDatabase().insert(TableTasks.T_NAME, null, v);

    }

    /**
     * Update information about list into a data base.
     * @param name	new task name
     * @return True, if was been updated only one element
     * */
    public boolean updateTask(Long list_id, String name, int checked, long id) {

		/* Create a new map of values, where column names are the keys */
        ContentValues v = new ContentValues();

		/* Fill values */
        v.put(TableTasks.C_NAME, name);
        v.put(TableTasks.C_LISTID, list_id);
        v.put(TableTasks.C_CHEC, checked);

		/* Update information */
        return 1 == this.getWritableDatabase().update(TableTasks.T_NAME, v,
                SQL_WHERE_BY_ID, new String[] {String.valueOf(id)});
    }


    /**
     * Delete list from a data base.
     * @param id	of element that will be deleted
     * @return True, if was been deleted only one element
     * */
    public boolean deleteTask(long id) {
        return 1 == this.getWritableDatabase().delete(
                TableTasks.T_NAME, SQL_WHERE_BY_ID,
                new String[] {String.valueOf(id)});
    }

    public Cursor getTask(long id) {
        return this.getReadableDatabase().query(TableLists.T_NAME,
                new String[] {TableLists.C_NAME, TableLists.C_TIME},
                SQL_WHERE_BY_ID,
                new String[] {String.valueOf(id)},
                null, null, null);
    }

    /**
     * Public static class that contains information about table tList
     * */
    public static class TableLists implements BaseColumns {

        /** Name of this table. */
        public static final String T_NAME = "tLists";

        /**
         * The name of List.
         * <P>Type: TEXT</P>
         */
        public static final String C_NAME = "NAME";

        /**
         * Time of creation.
         * <P>Type: TEXT</P>
         */
        public static final String C_TIME = "CREATE_TIME";

        /** SQL query for a create this table. */
        public static final String SQL_CREATE = "CREATE TABLE " + T_NAME +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                C_NAME + " TEXT," +
                C_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
    }

    /**
     * Public static class that contains information about table tToDo
     * */
    public static class TableTasks implements BaseColumns {

        /** Name of this table. */
        public static final String T_NAME = "tToDo";

        /**
         * The name of doing.
         * <P>Type: TEXT</P>
         */
        public static final String C_NAME = "NAME";

        /**
         * The name of Checked.
         * <P>Type: BOOLEAN</P>
         */
        public static final String C_CHEC = "CHECKED";

        /**
         * The name of List.
         * <P>Type: LONG</P>
         */
        public static final String C_LISTID = "LIST_ID";

        /**
         * Time of creation.
         * <P>Type: TEXT</P>
         */
        public static final String C_TIME = "CREATE_TIME";

        /** SQL query for a create this table. */
        public static final String SQL_CREATE = "CREATE TABLE " + T_NAME +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                C_NAME + " TEXT," +
                C_CHEC + " INTEGER(1)," +
                C_LISTID + " INTEGER NOT NULL," +
                C_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
    }
}
