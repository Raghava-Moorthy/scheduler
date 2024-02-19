package com.example.schedulaer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FileNameActivity extends AppCompatActivity {
    EditText className, days, hours;
    TextView noOfDays, noOfHours;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_name);


        toggleElements();
        save = findViewById(R.id.saveBtn);
        save.setOnClickListener(view -> validateDayHour());
        Button cancelButton = findViewById(R.id.button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to MainActivity
                Intent intent = new Intent(FileNameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void toggleElements() {
        className = findViewById(R.id.className);
        days = findViewById(R.id.days);
        hours = findViewById(R.id.hours);
        noOfDays = findViewById(R.id.no_of_days);
        noOfHours = findViewById(R.id.no_of_hours);
        className.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = String.valueOf(className.getText());
                if (text.length() > 1) {
                    days.setVisibility(View.VISIBLE);
                    hours.setVisibility(View.VISIBLE);
                    noOfDays.setVisibility(View.VISIBLE);
                    noOfHours.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    protected void validateDayHour() {
        days = findViewById(R.id.days);
        hours = findViewById(R.id.hours);
        int day = Integer.parseInt(String.valueOf(days.getText()));
        int hour = Integer.parseInt(String.valueOf(hours.getText()));
        int flag = 0;
        if (day == 0 || day > 5) {
            Toast.makeText(FileNameActivity.this, "Enter a Valid Number of Working Days", Toast.LENGTH_SHORT).show();
            days.setText("");
            flag++;
        }
        if (hour > 8 || hour < 3) {
            Toast.makeText(FileNameActivity.this, "Enter a Valid Number of Working Hours", Toast.LENGTH_SHORT).show();
            hours.setText("");
            flag++;
        }
        if (flag == 0) {
            Intent tabIntent = new Intent(this, MainActivity.class);
            tabIntent.putExtra("className", className.getText().toString());
            sendBroadcast(tabIntent);
            proceeedToProcess();
        }
    }
    protected void proceeedToProcess(){
        Intent processIntent = new Intent(this, process.class);
        startActivity(processIntent);

    }
}
