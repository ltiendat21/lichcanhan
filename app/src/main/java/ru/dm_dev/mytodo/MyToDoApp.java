package ru.dm_dev.mytodo;

import android.app.Application;
import android.util.Log;
import ru.dm_dev.mytodo.lib.DBToDo;

public class MyToDoApp extends Application {


    /* Static file for store a link to the data base object */
    private static DBToDo mDBToDo = null;

    @Override
    public void onCreate() {

		/* Invoke a parent method */
        super.onCreate();

        Log.d("MyApp", "Create DBToDo");

		/* Create a data base object */
        mDBToDo = new DBToDo(getApplicationContext());

    }

    /**
     * Get a link to the data base object
     */
    public static DBToDo getDB() {
        return mDBToDo;
    }

}
