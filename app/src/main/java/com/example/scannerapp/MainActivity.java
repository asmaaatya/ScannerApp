package com.example.scannerapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scannerapp.data.ScannedItemInfo;
import com.example.scannerapp.data.ScannerDatabase;
import com.example.scannerapp.ui.ScannedItemsAdapter;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScannedItemsAdapter adapter;
    private List<ScannedItemInfo> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(v -> startScan());

        loadScannedItems();
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    saveScannedData(result.getContents());
                }
            });

    private void startScan() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a QR Code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        } else {
            barcodeLauncher.launch(options);
        }
    }

    private void saveScannedData(String scannedContent) {
        new Thread(() -> {
            ScannedItemInfo item = new ScannedItemInfo(scannedContent, false);
            ScannerDatabase.getInstance(this).scannedItemDao().insert(item);
            runOnUiThread(this::loadScannedItems);
        }).start();
    }

    private void loadScannedItems() {
        new Thread(() -> {
            items = ScannerDatabase.getInstance(this).scannedItemDao().getAllScannedItems();
            runOnUiThread(() -> {
                adapter = new ScannedItemsAdapter(items);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }
}
