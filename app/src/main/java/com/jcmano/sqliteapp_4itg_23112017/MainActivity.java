package com.jcmano.sqliteapp_4itg_23112017;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHelper helper;
    EditText etFirstName, etLastName, etGrade, etID;
    Button btnSaveNewRecord, btnUpdateRecord;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        etFirstName = (EditText) findViewById(R.id.etFName);
        etLastName = (EditText) findViewById(R.id.etLName);
        etGrade = (EditText) findViewById(R.id.etFGrade);
        etID = (EditText) findViewById(R.id.etID);
        btnSaveNewRecord = (Button) findViewById(R.id.btSaveNewRecord);
        btnUpdateRecord = (Button) findViewById(R.id.btUpdateRecord);
        cursor = helper.getAllData();

    }

    public void saveNewRecord(View view){
        String fname, lname, grade;

        fname = etFirstName.getText().toString();
        lname = etLastName.getText().toString();
        grade = etGrade.getText().toString();
        boolean isInserted = helper.insertData(fname, lname, grade);

        if(isInserted == true){
            toastMessage("Data Successfully Inserted");

        }else{
            toastMessage("Data Not Inserted");

        }

        cursor.close();
        cursor = helper.getAllData();
        cursor.move(cursor.getCount() - 1);

    }

    public void saveUpdate(View view){
        String id, fname, lname, grade;

        id = etID.getText().toString();
        fname = etFirstName.getText().toString();
        lname = etLastName.getText().toString();
        grade = etGrade.getText().toString();

        boolean isUpdated = helper.updateData(id, fname, lname, grade);


        if(isUpdated == true){
            toastMessage("Data Successfully Updated");

        }else{
            toastMessage("Data Not Updated");

        }

        cursor.close();
        cursor = helper.getAllData();
        cursor.move(Integer.parseInt(id));
    }

    public void showFirstRecord(View view){

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            displayRecord();
            toastMessage("First Record Displayed");
        }else{
            toastMessage("No records found");
        }
    }

    public void showLastRecord(View view){

        if(cursor.getCount() > 0){
            cursor.moveToLast();
            displayRecord();
            toastMessage("Last Record Displayed");
        }else{
            toastMessage("No records found");
        }
    }

    public void showNext(View view){

        if(cursor.getCount() > 0){
            cursor.moveToNext();
            displayRecord();
            toastMessage("Next Record Displayed");
        }else{
            toastMessage("No records found");
        }
    }

    public void showPrevious(View view){

        if(cursor.getCount() > 0){
            cursor.moveToPrevious();
            displayRecord();
            toastMessage("Previous Record Displayed");
        }else{
            toastMessage("No Record Found");
        }
    }

    public void deleteRecord(View view){
        String id = etID.getText().toString();
        Integer deleted = helper.deleteData(id);

        if(deleted > 0){
            toastMessage("ID no:" + id + " was deleted");
        }else{
            toastMessage("Deletion failed");
        }

        cursor.close();
        cursor = helper.getAllData();
        cursor.move(Integer.parseInt(id));

    }

    private void displayRecord(){
        etID.setText(cursor.getString(0));
        etFirstName.setText(cursor.getString(1));
        etLastName.setText(cursor.getString(2));
        etGrade.setText(cursor.getString(3));

    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
