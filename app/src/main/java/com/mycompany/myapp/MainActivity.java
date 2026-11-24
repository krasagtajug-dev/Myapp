package com.mycompany.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 40, 30, 30);

        TextView title = new TextView(this);
        title.setText("MyApp - Floating AI");
        title.setTextSize(24);

        TextView info = new TextView(this);
        info.setText("Asisten AI dalam jendela mengambang.");

        floatingButton = new Button(this);
        floatingButton.setText("Aktifkan Floating Window");

        layout.addView(title);
        layout.addView(info);
        layout.addView(floatingButton);

        setContentView(layout);

        floatingButton.setOnClickListener(v -> {
            if (Settings.canDrawOverlays(this)) {
                showFloatingWindow();
            } else {
                Intent intent = new Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName())
                );
                startActivity(intent);
            }
        });
    }

    private void showFloatingWindow() {
        TextView panel = new TextView(this);
        panel.setText("MyApp Floating AI\\nJendela mengambang aktif.");
        panel.setTextSize(18);
        panel.setPadding(25, 25, 25, 25);
        panel.setBackgroundColor(0xFFE0E0E0);

        WindowManager wm =
            (WindowManager) getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams params =
            new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 50;
        params.y = 200;

        try {
            wm.addView(panel, params);
        } catch (Exception e) {
            Toast.makeText(this, "Gagal membuka jendela", Toast.LENGTH_SHORT).show();
        }

        panel.setOnClickListener(v -> {
            try {
                wm.removeView(panel);
            } catch (Exception ignored) {}
        });
    }
}
