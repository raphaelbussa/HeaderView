/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Raphaël Bussa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rebus.header.view.sample;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import rebus.header.view.HeaderCallback;
import rebus.header.view.HeaderView;
import rebus.header.view.Item;
import rebus.header.view.Profile;
//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private static final String TAG = MainActivity.class.getName();

    private HeaderView headerView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(view -> {
            if (drawerLayout != null && !drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0).findViewById(R.id.header_view);

        Profile profile = new Profile.Builder()
                .setId(2)
                .setUsername("Raphaël Bussa")
                .setEmail("raphaelbussa@gmail.com")
                .setAvatar("https://github.com/rebus007.png?size=512")
                .setBackground("https://images.unsplash.com/photo-1473220464492-452fb02e6221?dpr=2&auto=format&fit=crop&w=767&h=512&q=80&cs=tinysrgb&crop=")
                .build();

        Profile profile2 = new Profile.Builder()
                .setId(4)
                .setUsername("Federico Gentile")
                .setEmail("fgentile95dev@icloud.com")
                .setAvatar("https://github.com/FedeGens.png?size=512")
                .setBackground("https://images.unsplash.com/photo-1469173479606-ada03df615ac?dpr=2&auto=format&fit=crop&w=767&h=511&q=80&cs=tinysrgb&crop=")
                .build();

        Profile profile3 = new Profile.Builder()
                .setId(6)
                .setUsername("Luca Rurio")
                .setEmail("rurio.luca@gmail.com")
                .setAvatar("https://github.com/RurioLuca.png?size=512")
                .setBackground("https://images.unsplash.com/photo-1473789810014-375ed569d0ed?dpr=2&auto=format&fit=crop&w=767&h=511&q=80&cs=tinysrgb&crop=")
                .build();

        Profile profile4 = new Profile.Builder()
                .setId(8)
                .setUsername("Krzysztof Klimkiewicz")
                .setEmail("krzkz94@gmail.com")
                .setAvatar("https://github.com/krzykz.png?size=512")
                .setBackground("https://images.unsplash.com/photo-1452509133926-2b180c6d6245?dpr=2&auto=format&fit=crop&w=767&h=431&q=80&cs=tinysrgb&crop=")
                .build();

        Item item = new Item.Builder()
                .setId(1)
                .setTitle("Remove all profile")
                .build();

        Item item2 = new Item.Builder()
                .setId(2)
                .setTitle("Remove active profile")
                .build();


        headerView.setStyle(HeaderView.STYLE_COMPACT);
        headerView.setTheme(HeaderView.THEME_LIGHT);
        headerView.setShowGradient(true);
        headerView.setHighlightColor(ContextCompat.getColor(this, R.color.colorAccent));
        headerView.addProfile(profile, profile2, profile3, profile4);
        headerView.addDialogItem(item, item2);
        headerView.setShowAddButton(true);
        //headerView.setAddIconDrawable(R.drawable.ic_action_settings);
        headerView.setDialogTitle("Choose account");
        headerView.setShowArrow(true);
        headerView.setOnHeaderClickListener(v -> drawerLayout.closeDrawer(GravityCompat.START, true));
        headerView.setFragmentManager(getFragmentManager());
        headerView.setCallback(new HeaderCallback() {

            @Override
            public boolean onSelect(int id, boolean isActive) {
                Log.d(TAG, "profile selected [" + id + "] isActive [" + isActive + "]");
                Toast.makeText(MainActivity.this, "profile selected [" + id + "] isActive [" + isActive + "]", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START, true);
                return true;
            }

            @Override
            public boolean onItem(int id) {
                switch (id) {
                    case 1:
                        headerView.clearProfile();
                        break;
                    case 2:
                        int profileId = headerView.getProfileActive();
                        headerView.removeProfile(profileId);
                        break;
                }
                return true;
            }

            @Override
            public boolean onAdd() {
                Profile newProfile = new Profile.Builder()
                        .setId(100)
                        .setUsername("Mattia Novelli")
                        .setEmail("nove.mattia@gmail.com")
                        .setAvatar("https://github.com/mattinove.png?size=512")
                        .setBackground("https://images.unsplash.com/photo-1478194409487-fa5c1eb18622?dpr=2&auto=format&fit=crop&w=767&h=496&q=80&cs=tinysrgb&crop=")
                        .build();
                headerView.addProfile(newProfile);
                headerView.setProfileActive(100);
                headerView.dismissProfileChooser();
                return false;
            }

        });

        //headerView.addProfile(profile, profile2, profile3);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

/*
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
*/

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
