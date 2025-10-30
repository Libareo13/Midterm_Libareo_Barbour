package com.example.midterm_libareo_barbour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private Button btnGenerate, btnHistory;
    private ListView lvTable;
    private ArrayAdapter<String> adapter;
    private List<String> currentTable = DataStore.currentTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnHistory = findViewById(R.id.btnHistory);
        lvTable = findViewById(R.id.lvTable);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentTable);
        lvTable.setAdapter(adapter);

        btnGenerate.setOnClickListener(v -> generateTable());
        btnHistory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HistoryActivity.class)));

        lvTable.setOnItemClickListener((parent, view, position, id) -> {
            String item = currentTable.get(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete row?")
                    .setMessage("Do you want to delete:\n" + item)
                    .setPositiveButton("Delete", (dialog, which) -> {
                        currentTable.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Deleted: " + item, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void generateTable() {
        String text = etNumber.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "Please enter a number.", Toast.LENGTH_SHORT).show();
            return;
        }

        int num;
        try {
            num = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input.", Toast.LENGTH_SHORT).show();
            return;
        }

        currentTable.clear();
        for (int i = 1; i <= 10; i++) {
            currentTable.add(num + " × " + i + " = " + (num * i));
        }
        DataStore.addNumberToHistory(num);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear_all) {
            if (currentTable.isEmpty()) {
                Toast.makeText(this, "List is already empty.", Toast.LENGTH_SHORT).show();
                return true;
            }
            new AlertDialog.Builder(this)
                    .setTitle("Clear all rows?")
                    .setMessage("This will remove all rows.")
                    .setPositiveButton("Clear", (dialog, which) -> {
                        currentTable.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "All rows cleared.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
