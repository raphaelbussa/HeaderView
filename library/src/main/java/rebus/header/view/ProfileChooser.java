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

package rebus.header.view;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by raphaelbussa on 17/01/17.
 */

class ProfileChooser extends Dialog {

    private ProfileChooserCallback callback;

    @SuppressLint("RtlHardcoded")
    ProfileChooser(Context context, SparseArray<Profile> profileSparseArray, ArrayList<Item> items, int accent, boolean showAdd, String titleValue, int icon) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.hw_account_chooser);
        boolean hvIsRTL = context.getResources().getBoolean(R.bool.is_right_to_left);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hw_profile_container);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.hw_action_container);
        TextView title = (TextView) findViewById(R.id.hw_dialog_title);
        ImageView add = (ImageView) findViewById(R.id.hv_add_profile);
        add.setImageResource(icon);
        title.setTextColor(Utils.getTextColorPrimary(context));
        title.setText(titleValue);
        title.setGravity(Gravity.CENTER_VERTICAL | (hvIsRTL ? Gravity.RIGHT : Gravity.LEFT));
        add.setVisibility(showAdd ? View.VISIBLE : View.INVISIBLE);
        add.setColorFilter(Utils.getTextColorPrimary(context));
        add.setBackgroundResource(Utils.selectableItemBackgroundBorderless(context));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    if (callback.onAdd()) {
                        dismiss();
                    }
                }
            }
        });
        for (int i = 0; i < profileSparseArray.size(); i++) {
            RowProfileView profileView = new RowProfileView(context);
            profileView.setProfile(profileSparseArray.valueAt(i), i == 0);
            profileView.setAccent(accent);
            profileView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RowProfileView rowProfileView = (RowProfileView) v;
                    if (callback != null) {
                        if (callback.onSelect(rowProfileView.getProfile().getId(), rowProfileView.isActive())) {
                            dismiss();
                        }
                    }
                }
            });
            linearLayout.addView(profileView);
        }
        int padding = context.getResources().getDimensionPixelSize(R.dimen.hv_item_padding);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (Item item : items) {
            TextView textView = new TextView(context);
            textView.setText(item.getTitle());
            textView.setTag(item.getId());
            textView.setBackgroundResource(Utils.selectableItemBackground(context));
            textView.setPadding(padding, padding / 2, padding, padding / 2);
            textView.setTextColor(Utils.getTextColorSecondary(context));
            textView.setGravity(Gravity.CENTER_VERTICAL | (hvIsRTL ? Gravity.RIGHT : Gravity.LEFT));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) v.getTag();
                    if (callback != null) {
                        if (callback.onItem(id)) {
                            dismiss();
                        }
                    }
                }
            });
            linearLayout1.addView(textView, layoutParams);
        }
    }

    void setCallback(ProfileChooserCallback callback) {
        this.callback = callback;
    }

}
