package com.example.scheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private String f;
    private SearchView search;
    private View root;
    private ScrollView scrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.bg);
        search = findViewById(R.id.searchBar);
        search.clearFocus();
        search.clearChildFocus(search);
        int searchId = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = search.findViewById(searchId);
        int searchTextColor = ContextCompat.getColor(this, R.color.darkGrey);
        searchText.setTextColor(searchTextColor);
        search.setOnClickListener(v -> search.findFocus());
        FloatingActionButton fab = findViewById(R.id.addBtn);
        fab.setOnClickListener(view -> {
            fileName();
            search.clearFocus();
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        if (search != null) {
            search.setQuery("", false);
            search.clearFocus();
            root.requestFocus();
        }
    }
    private void fileName() {
        EditText ed = new EditText(MainActivity.this);
        AlertDialog.Builder nameBox = new AlertDialog.Builder(MainActivity.this);
        nameBox.setMessage("Enter the Schedule Name");
        nameBox.setView(ed);

        nameBox.setPositiveButton("Create", null);
        AlertDialog alertDialog = nameBox.create();
        alertDialog.setOnShowListener(dialogInterface -> {
            AlertDialog d = (AlertDialog) dialogInterface;
            d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            ed.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = s.toString();
                    d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(text.length() > 3);
                    f = text;
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        });
        alertDialog.setCancelable(false);

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.cancel());

        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            Intent inte = new Intent(MainActivity.this, ScheduleTime.class);
            startActivity(inte);
            new Handler(getMainLooper()).postDelayed(() -> {
                CardView cardView = new CardView(MainActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dpToPx(50)
                );
                params.setMargins(20, 20, 20, 20);
                cardView.setLayoutParams(params);
                cardView.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.lightGrey));

                TextView textView = new TextView(MainActivity.this);
                f = Character.toUpperCase(f.charAt(0)) + f.substring(1);
                textView.setText(f);
                textView.setTextSize(20);
                textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                cardView.addView(textView);
                LinearLayout layout = findViewById(R.id.cardContainer);
                layout.addView(cardView);
            },1000);
            alertDialog.dismiss();
        });
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
