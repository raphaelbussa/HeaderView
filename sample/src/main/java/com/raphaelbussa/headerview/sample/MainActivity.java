package com.raphaelbussa.headerview.sample;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        findViewById(R.id.normal_drawer).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NormalHeaderActivity.class)));
        findViewById(R.id.compact_drawer).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CompactHeaderActivity.class)));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.action_info));
            builder.setMessage(HtmlCompat.fromHtml(getString(R.string.info_message), HtmlCompat.FROM_HTML_MODE_LEGACY));
            builder.setPositiveButton(getString(R.string.close), null);
            AlertDialog dialog = builder.create();
            dialog.show();
            TextView textView = dialog.findViewById(android.R.id.message);
            if (textView != null) {
                textView.setMovementMethod(LinkMovementMethod.getInstance());
            }
            return true;
        }
        return false;
    }

}
