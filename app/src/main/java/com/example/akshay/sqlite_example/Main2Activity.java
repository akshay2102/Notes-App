package com.example.akshay.sqlite_example;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.akshay.sqlite_example.database.DBHelper;

public class Main2Activity extends Activity {

    String note,id = "0";
    EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etNote = findViewById(R.id.etNote);

        if(getIntent().hasExtra("ID")){
            id = getIntent().getStringExtra("ID");
            Button btnDelete = findViewById(R.id.btnDelete);
            btnDelete.setVisibility(View.VISIBLE);
        }
        if(getIntent().hasExtra("NOTE")){
            note = getIntent().getStringExtra("NOTE");
            etNote.setText(note);
        }
    }

    public void  btnSaveClick(View v){
        DBHelper dbHelper = new DBHelper(Main2Activity.this);
        String msg;
        if(id.equals("0"))
            msg = dbHelper.saveNote(etNote.getText().toString());
        else
            msg = dbHelper.updateNote(id,etNote.getText().toString());
        Toast.makeText(Main2Activity.this,msg,Toast.LENGTH_SHORT).show();
        finish();
    }

    public void btnDeleteClick(View v){
        DBHelper dbHelper = new DBHelper(Main2Activity.this);
        dbHelper.deleteNote(id);
        finish();
    }

    public void btnCloseClick(View v){
        finish();
    }
}

