package com.example.schedulaer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.addBtn);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FileNameActivity.class);
            startActivity(intent);
        });
        SearchView search = findViewById(R.id.searchBar);
        int searchId = search.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
        TextView searchText = search.findViewById(searchId);
        int searchTextColor = ContextCompat.getColor(this,R.color.orange);
        searchText.setTextColor(searchTextColor);
    }
}