package com.example.akshay.sqlite_example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.akshay.sqlite_example.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences; //you can view shared preferences xml file by clicking right bottom icon
    FloatingActionButton fab;

    List<String> notes;
    List<String> ids;
    ArrayAdapter<String> adapter;  //adapter is bridge between database and your list
    ListView listView;
    int j,c;
    //method created by us
    private void listNotes(){
        notes = new ArrayList<>();
        ids = new ArrayList<>();

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getNotes();
        if(cursor.moveToFirst()){  //will move to first element and return true if first element exists
            do{
                ids.add(cursor.getString(0));  //0 is column no. in database
                notes.add(cursor.getString(1));
            }
            while(cursor.moveToNext());
        }

        adapter  = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        //preferences p;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                c = dbHelper.getCount();
                String s = "10";
                SharedPreferences p =getSharedPreferences(Prefs.NOTES_SETTINGS,MODE_PRIVATE);
                j = p.getInt(Prefs.MAX_NOTES,0);
                //Intent i = new Intent();
                //if(i.getStringExtra("max")!=null)
                   // s =i.getStringExtra("max").toString();
                //j = parseInt(s);
                if(j<=c)
                    Toast.makeText(MainActivity.this,"Maximum Notes Count Reached",Toast.LENGTH_SHORT).show();
                else
                    showNoteDialog(-1);
            }
        });
    }

    //overrided by us
    @Override
    protected void onResume() {
        super.onResume();
        preferences = getSharedPreferences(Prefs.NOTES_SETTINGS,MODE_PRIVATE);
        Boolean readOnly = preferences.getBoolean(Prefs.READ_ONLY,false);
        fab.setVisibility(readOnly ? View.GONE : View.VISIBLE);


        j = preferences.getInt(Prefs.MAX_NOTES,0);
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        c = dbHelper.getCount();

        if(c>j) {
            while (c != j) {
                dbHelper.deleteNote(ids.get(c - 1));
                c--;
            }
        }

        listView = findViewById(R.id.IvNotes);
        listNotes();

        if(!readOnly){
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    showNoteDialog(i);
                    return false;
                }
            });
        }
        else
            listView.setOnItemLongClickListener(null);

    }


    //created by us
    private void showNoteDialog(final int position){
        if(position == -1)  //if new record
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        else{
            Intent i = new Intent(MainActivity.this,Main2Activity.class);
            i.putExtra("NOTE",notes.get(position));
            i.putExtra("ID",ids.get(position));
            startActivity(i);
        }
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
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,Settings_Activity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
