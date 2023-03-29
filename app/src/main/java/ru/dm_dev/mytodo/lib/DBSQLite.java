package ru.dm_dev.mytodo.lib;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public abstract class DBSQLite extends SQLiteOpenHelper {

    /* Private fields for store a link to the Context value */
    private Context mContext = null;

    /* Constructor from super class */
    public DBSQLite(Context context, String name, CursorFactory factory,
                    int version, DatabaseErrorHandler errorHandler) {

		/* Invoke a parent method */
        super(context, name, factory, version, errorHandler);

		/* Setting up Context value  */
        mContext = context;
    }

    /* Constructor from super class */
    public DBSQLite(Context context, String name, CursorFactory factory,
                    int version) {

		/* Invoke a parent method */
        super(context, name, factory, version);

		/* Setting up Context value  */
        mContext = context;
    }

    /**
     * Get a link to the current Context object.
     *
     * @return Context object that using for this object.
     */
    protected Context getContext() {
        return mContext;
    }

    /**
     * Get readable cursor for a table.
     *
     * @param table the	name of table
     * @return Readable Cursor for this table.
     */
    public Cursor getReadableCursor(String table) {
        return this.getReadableDatabase().query(table, null, null, null, null,
                null, null);
    }

    public Cursor getReadableCursorWithSelectedData(String table, String[] columnNames) {
        return this.getReadableDatabase().query(table, columnNames, null, null, null,
                null, null);
    }

    /**
     * Get writable cursor for a table.
     *
     * @param table the	name of table
     * @return Writable Cursor for this table.
     */
    public Cursor getWritableCursor(String table) {
        return this.getWritableDatabase().query(table, null, null, null, null,
                null, null);
    }

    /**
     * Execute a single SQL statement that is NOT a SELECT or any other SQL
     * statement that returns data.
     *
     * @param sql the SQL statement to be executed. Multiple statements
     *            separated by semicolons are not supported.
     */
    public static boolean execSQL(SQLiteDatabase db, String sql) {

		/* Checking a DB object */
        if (db == null) return false;

		/* Try to execute SQL request */
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            return false;
        }

		/* Return true value */
        return true;
    }

    /**
     * Execute SQL query for drop table from data base.
     *
     * @param db    the data base
     * @param table name that will be deleted
     */
    public static boolean dropTable(SQLiteDatabase db, String table) {
        return DBSQLite.execSQL(db, "DROP TABLE IF EXISTS " + table);
    }

}