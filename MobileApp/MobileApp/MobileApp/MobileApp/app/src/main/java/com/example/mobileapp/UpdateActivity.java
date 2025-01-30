package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText updateTitle, updateDesc;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        updateButton = findViewById(R.id.updateButton);
        updateTitle = findViewById(R.id.updateTitle);
        updateDesc = findViewById(R.id.updateDesc);

        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("description");
        int id = getIntent().getIntExtra("id", 0);
        updateTitle.setText(title);
        updateDesc.setText(desc);

        String sId = String.valueOf(id);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Helper helper = new Helper(UpdateActivity.this);
                helper.updateData(updateTitle.getText().toString(), updateDesc.getText().toString(), sId);
                Toast.makeText(UpdateActivity.this, "The data is successfully updated", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();

            }
        });


    }
}