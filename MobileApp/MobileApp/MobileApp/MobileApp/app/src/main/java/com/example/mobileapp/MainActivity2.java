package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText editTitle, editDesc;

    Button button;

    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
        button = findViewById(R.id.addButton);
        helper = new Helper(MainActivity2.this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTitle.length()>0&&editDesc.length()>0){
                    helper.insertData(editTitle.getText().toString(), editDesc.getText().toString());
                    Toast.makeText(MainActivity2.this, "The data successfully added", Toast.LENGTH_SHORT).show();
                    editDesc.setText("");
                    editTitle.setText("");
                    setResult(RESULT_OK);
                    finish();

                } else {
                    Toast.makeText(MainActivity2.this, "The edit box is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}