package com.example.sqliteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks, editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnviewUpdate;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.editTextText_Name);
        editSurname = (EditText) findViewById(R.id.editText_Surname);
        editTextId = (EditText) findViewById(R.id.EditTextId);
        editMarks = (EditText) findViewById(R.id.editText_Marks);
        btnAddData = (Button) findViewById(R.id.button);
        btnviewAll = (Button) findViewById(R.id.button2);
        btnviewUpdate = (Button) findViewById(R.id.button3);
        btnDelete = (Button) findViewById(R.id.button4);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }
    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0){
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                    }
                        else{
                        Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                    }
                    }
                }
        );

    }
    public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = myDb.updateData(editTextId.getText().toString(), editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());
                        if(isUpdate == true) {
                            Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
    public void AddData(){

               btnAddData.setOnClickListener(
                       new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                              boolean isInserted = myDb.insertData(
                                      editName.getText().toString(),
                                      editSurname.getText().toString(),
                                      editMarks.getText().toString());
                              if (isInserted == true) {
                                   Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                               }
                               else{
                                   Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                               }
                           }
                       }
               );
    }
    public void viewAll(){
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      Cursor res =  myDb.getAllData();
                      if(res.getCount() == 0){
                          showMessage("Error","Nothing found");
                          return;
                      }
                 StringBuffer buffer = new StringBuffer();
                      while(res.moveToNext()){
                          buffer.append("Id :"+ res.getString(0) + "\n");
                          buffer.append("Name :"+ res.getString(1) + "\n");
                          buffer.append("Surname :"+ res.getString(2) + "\n");
                          buffer.append("Marks :"+ res.getString(3) + "\n\n");

                      }
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return true;
    }
}