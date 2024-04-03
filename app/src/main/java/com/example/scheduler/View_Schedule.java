package com.example.scheduler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class View_Schedule extends AppCompatActivity {
    ListView listView;
    ArrayList<Item> arraylist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule);
        listView = findViewById(R.id.listView);
        CustomAdapter adapter = new CustomAdapter(this, arraylist);
        listView.setAdapter(adapter);
        prepareList(this);
    }


    public void prepareList(Context c) {
        int day = 0;
        Calendar sCalendar = Calendar.getInstance();
        String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        day = switch (dayLongName) {
            case "Monday" -> 0;
            case "Tuesday" -> 1;
            case "Wednesday" -> 2;
            case "Thursday" -> 3;
            case "Friday" -> 4;
            case "Saturday" -> 5;
            case "Sunday" -> 6;
            default -> 0;
        };
        String where = " day= ? ";
        String[] data = {day + ""};
        Cursor cr = null;
        try {
            cr = c.getContentResolver().query(Data.CONTENT_URI, null, where, data, null);
            if (cr != null && cr.moveToFirst()) {
                do {
                    @SuppressLint("Range") String subject = cr.getString(cr.getColumnIndex(Data.SUBJECT));
                    @SuppressLint("Range") String type = cr.getString(cr.getColumnIndex(Data.TYPE));
                    @SuppressLint("Range") String timing = cr.getString(cr.getColumnIndex(Data.FROM)) + "-" + cr.getString(cr.getColumnIndex(Data.TO));
                    Item s = new Item(subject, type, timing);
                    arraylist.add(s);
                } while (cr.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cr != null) {
                cr.close();
            }
        }
    }


}

class CustomAdapter extends BaseAdapter {
    Context context;
    TextView subject, type, timing, srno;
    ArrayList<Item> arrayList;

    public CustomAdapter(Context context, ArrayList<Item> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row, viewGroup, false);
        srno = row.findViewById(R.id.sr);
        subject = row.findViewById(R.id.col1);
        type = row.findViewById(R.id.col3);
        timing = row.findViewById(R.id.col4);
        srno.setText(i + 1 + "");
        Item p = arrayList.get(i);
        subject.setText(p.subject);
        type.setText(p.type);
        timing.setText(p.timing);
        return row;
    }
}

