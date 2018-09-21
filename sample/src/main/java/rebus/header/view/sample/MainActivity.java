package rebus.header.view.sample;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        findViewById(R.id.normal_drawer).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NormalHeaderActivity.class)));
        findViewById(R.id.compact_drawer).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CompactHeaderActivity.class)));
    }


    @SuppressWarnings("deprecation")
    private Spanned fromHtml(String value) {
        if (value == null) {
            value = "";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(value);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.action_info));
                builder.setMessage(fromHtml(getString(R.string.info_message)));
                builder.setPositiveButton(getString(R.string.close), null);
                AlertDialog dialog = builder.create();
                dialog.show();
                TextView textView = dialog.findViewById(android.R.id.message);
                if (textView != null) {
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                }
                return true;
            default:
                return false;
        }
    }

}
