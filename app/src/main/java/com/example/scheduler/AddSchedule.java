package com.example.scheduler;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AddSchedule extends AppCompatActivity {
    EditText subject, teacher, room;
    Button button;
    Spinner type;
    EditText from;
    EditText to;
    int pos, checker = 0, day;
    String teach, sub, kamra, from1, to1, typ;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedule);

        Bundle b = getIntent().getExtras();
        assert b != null;
        pos = b.getInt("ID");
        day = b.getInt("day");
        checker = b.getInt("update");
        teach = b.getString("teacher");
        sub = b.getString("subject");
        kamra = b.getString("room");
        from1 = b.getString("from");
        to1 = b.getString("to");
        typ = b.getString("type");

        button = findViewById(R.id.button20);
        from = findViewById(R.id.editText2);
        to = findViewById(R.id.editText3);
        type = findViewById(R.id.spinner);
        room = findViewById(R.id.editText30);
        subject = findViewById(R.id.editText);
        teacher = findViewById(R.id.editText90);
        type = findViewById(R.id.spinner);
        Button save = findViewById(R.id.button10);
        save.setOnClickListener(this::save);
        button.setOnClickListener(view -> finish());

        if (teach != null && sub != null && kamra != null && from1 != null && to1 != null) {
            fillData(teach, sub, kamra, from1, to1, typ);
        }
        from.setOnClickListener(view -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(AddSchedule.this, (timePicker, selectedHour, selectedMinute) -> {
                String hour1 = selectedHour + "", minute1 = selectedMinute + "";
                if (selectedHour / 10 == 0) {
                    hour1 = "0" + hour1;
                }
                if (selectedMinute / 10 == 0) {
                    minute1 = "0" + minute1;
                }
                String txt = hour1 + ":" + minute1;
                from.setText(txt);
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });
        to.setOnClickListener(view -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(AddSchedule.this, (timePicker, selectedHour, selectedMinute) -> {

                String hour12 = selectedHour + "", minute12 = selectedMinute + "";
                if (selectedHour / 10 == 0) {
                    hour12 = "0" + hour12;
                }
                if (selectedMinute / 10 == 0) {
                    minute12 = "0" + minute12;
                }
                String txt = hour12 + ":" + minute12;
                to.setText(txt);

            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });
    }
    public void fillData(String teacher1, String subject1, String room1, String from1, String to1, String type) {
        teacher.setText(teacher1);
        subject.setText(subject1);
        from.setText(from1);
        to.setText(to1);
        room.setText(room1);
    }
    public void save(View v) {
        String strTeacher = teacher.getText().toString();
        String strSubject = subject.getText().toString();
        String strFrom = from.getText().toString();
        String strTo = to.getText().toString();
        String strRoom = room.getText().toString();
        if (TextUtils.isEmpty(strSubject)) {
            subject.setError("Enter subject here");
            return;
        }
        if (TextUtils.isEmpty(strFrom)) {
            from.setError("Enter starting time");
            return;
        }
        if (TextUtils.isEmpty(strTo)) {
            to.setError("Enter ending time");
            return;
        }
        if (TextUtils.isEmpty(strRoom)) {
            room.setError("Enter Room No.");
            return;
        }
        if (TextUtils.isEmpty(strTeacher)) {
            teacher.setError("Enter Teacher Name");
            return;
        }

        ContentValues values = new ContentValues();
        values.put(Data.SUBJECT, strSubject);
        values.put(Data.TEACHER, strTeacher);
        values.put(Data.FROM, strFrom);
        values.put(Data.ROOM, strRoom);
        values.put(Data.TO, strTo);
        values.put(Data.TYPE, type.getSelectedItem().toString());
        values.put(Data.DAY, pos);

        if (checker == 0) {
            getContentResolver().insert(Data.CONTENT_URI, values);
            Toast.makeText(this, "Schedule added successfully", Toast.LENGTH_SHORT).show();
        } else {
            String where = Data._ID + " = ?";
            String[] whereArgs = {String.valueOf(checker)};
            getContentResolver().update(Data.CONTENT_URI, values, where, whereArgs);
            Toast.makeText(this, "Schedule updated successfully", Toast.LENGTH_SHORT).show();
        }
        finish();
    }


}