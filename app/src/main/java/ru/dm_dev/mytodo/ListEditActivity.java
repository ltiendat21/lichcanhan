package ru.dm_dev.mytodo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.dm_dev.mytodo.lib.DBToDo;

public class ListEditActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String ID_KEY = "LISTID";
    public static final Long ID_NEW_LIST_KEY = -1L;

    private long id = ID_NEW_LIST_KEY;

    private EditText tName;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_edit);

        tName = (EditText) findViewById(R.id.edit_list_name);

        btnSave = (Button) findViewById(R.id.save_button);
        btnSave.setOnClickListener(this);

        if (savedInstanceState != null) {
            id = savedInstanceState.getLong(ID_KEY);
            Log.d("ListEditActivity", "Restore id = " + id);
        } else {
            Intent intent = getIntent();
            if (intent != null) {
                id = intent.getLongExtra(ID_KEY, ID_NEW_LIST_KEY);
                Log.d("ListEditActivity", "Start edit id = " + id);
            }
            if (id != ID_NEW_LIST_KEY) {
                Cursor c = MyToDoApp.getDB().getList(id);
                if (c.moveToFirst()) {
                    tName.setText(c.getString(c.getColumnIndex(DBToDo.TableLists.C_NAME)));
                    Log.d("ListEditActivity", "Loaded list from DB");
                }
                c.close();
            }
        }

    }

    private void SaveList() {
        if (id != ID_NEW_LIST_KEY) {
            MyToDoApp.getDB().updateList(tName.getText().toString(), id);
            Log.d("ListEditActivity", "Updated note id = " + id);
        } else {
            long _id = MyToDoApp.getDB().addList(tName.getText().toString());
            Log.d("ListEditActivity", "Saved note id = " + _id);
        }
        Toast.makeText(this, R.string.list_saved, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save_button) {
            Log.d("ListEditActivity", "Click on Save Button!");
            SaveList();
        } else {
            Log.e("ListEditActivity", "onClick: Unknown view Id!");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("ListEditActivity", "Save instance state with id = " + id);
        savedInstanceState.putLong(ID_KEY, id);
        super.onSaveInstanceState(savedInstanceState);
    }
}
