package ru.dm_dev.mytodo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import ru.dm_dev.mytodo.lib.DBToDo;

public class MainActivity extends AppCompatActivity {

    static final int EDIT_LIST_REQUEST = 1;

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MainActivity", "onCreate");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editList(ListEditActivity.ID_NEW_LIST_KEY);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        ListView lvList = (ListView) findViewById(R.id.lvList);
        cursor = MyToDoApp.getDB().getReadableCursor(DBToDo.TableLists.T_NAME);

        String[] from = {DBToDo.TableLists.C_NAME, DBToDo.TableLists.C_TIME};
        int[] to = {R.id.tvListTitle, R.id.tvTime};

        SimpleCursorAdapter lvAdapter = new SimpleCursorAdapter(this,
                R.layout.list_item, cursor, from, to, 1);

        lvList.setAdapter(lvAdapter);
        //lvList.setOnItemClickListener(this);

        registerForContextMenu(lvList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Log.d("MainActivity", "Start AboutActivity");
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editList(long id) {
        Log.d("MainActivity", "Start EditNoteActivity with note id = " + id);
        Intent intent = new Intent(this, ListEditActivity.class);
        intent.putExtra(ListEditActivity.ID_KEY, id);
        startActivityForResult(intent,EDIT_LIST_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_LIST_REQUEST) {
            if (resultCode == RESULT_OK) {
                cursor.requery();
            }
        }
    }
}
