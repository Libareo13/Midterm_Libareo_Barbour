package com.example.midterm_libareo_barbour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView lvHistory;
    private ArrayAdapter<String> adapter;
    private List<String> historyStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvHistory = findViewById(R.id.lvHistory);

        // Populate with all generated numbers
        historyStrings.clear();
        for (int num : DataStore.historyNumbers) {
            historyStrings.add(String.valueOf(num));
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyStrings);
        lvHistory.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear_history) {
            if (DataStore.historyNumbers.isEmpty()) {
                Toast.makeText(this, "History is already empty.", Toast.LENGTH_SHORT).show();
                return true;
            }
            new AlertDialog.Builder(this)
                    .setTitle("Clear history?")
                    .setMessage("This will remove all numbers from history.")
                    .setPositiveButton("Clear", (dialog, which) -> {
                        DataStore.historyNumbers.clear();
                        historyStrings.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "History cleared.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
