package com.project.sqlitegui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editAuthor, editEntry, editTimeStamp, editImage;
    Button buttonNewEntry, buttonViewJournal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editAuthor = (EditText)findViewById(R.id.editText_Author);
        editEntry = (EditText)findViewById(R.id.editText_Entry);
        editTimeStamp = (EditText)findViewById(R.id.editText_timeStamp);
        editImage = (EditText)findViewById(R.id.editText_Image);
        buttonNewEntry = (Button)findViewById(R.id.button_newEntry);
        buttonViewJournal = (Button)findViewById(R.id.button_viewJournal);
        AddData();
        viewAll();
    }

    public void AddData()   {
        buttonNewEntry.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editAuthor.getText().toString(),
                                editEntry.getText().toString(),
                                editTimeStamp.getText().toString(),
                                editImage.getText().toString());
                        if (isInserted = true)
                            Toast.makeText(MainActivity.this,"Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Fail",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void viewAll()   {
        buttonViewJournal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error", "No data");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID :" + res.getString(0) + "\n");
                            buffer.append("Author :" + res.getString(1) + "\n");
                            buffer.append("Entry :" + res.getString(2) + "\n");
                            buffer.append("Timestamp :" + res.getString(3) + "\n");
                            buffer.append("Image :" + res.getString(4) + "\n\n");
                        }
                        showMessage("Journal Entries", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String Title, String Message)   {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }

}
