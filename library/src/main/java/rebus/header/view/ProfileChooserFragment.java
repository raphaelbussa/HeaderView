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
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by raphaelbussa on 21/01/17.
 */

public class ProfileChooserFragment extends DialogFragment {

    public static final String FRAGMENT_TAG = "HV_PROFILE_CHOOSER_FRAGMENT";

    private LinearLayout linearLayout;
    private LinearLayout linearLayout1;
    private TextView title;
    private ImageView add;

    private ProfileChooserCallback callback;
    private Typeface typeface;
    private boolean hvIsRTL;

    public static ProfileChooserFragment newInstance(SparseArray<Profile> profileSparseArray, ArrayList<Item> items, int accent, boolean showAdd, String titleValue, int icon) {
        Bundle bundle = new Bundle();
        bundle.putSparseParcelableArray("profileSparseArray", profileSparseArray);
        bundle.putParcelableArrayList("items", items);
        bundle.putInt("accent", accent);
        bundle.putBoolean("showAdd", showAdd);
        bundle.putString("titleValue", titleValue);
        bundle.putInt("icon", icon);
        ProfileChooserFragment fragment = new ProfileChooserFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hw_account_chooser, container, false);
        linearLayout = rootView.findViewById(R.id.hw_profile_container);
        linearLayout1 = rootView.findViewById(R.id.hw_action_container);
        title = rootView.findViewById(R.id.hw_dialog_title);
        add = rootView.findViewById(R.id.hv_add_profile);
        hvIsRTL = getResources().getBoolean(R.bool.is_right_to_left);
        return rootView;
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        SparseArray<Profile> profileSparseArray = bundle.getSparseParcelableArray("profileSparseArray");
        List<Item> items = bundle.getParcelableArrayList("items");
        int accent = bundle.getInt("accent");
        boolean showAdd = bundle.getBoolean("showAdd");
        String titleValue = bundle.getString("titleValue");
        int icon = bundle.getInt("icon");

        add.setImageResource(icon);
        title.setTextColor(Utils.getTextColorPrimary(getActivity()));
        title.setText(titleValue);
        title.setGravity(Gravity.CENTER_VERTICAL | (hvIsRTL ? Gravity.RIGHT : Gravity.LEFT));
        if (typeface != null) title.setTypeface(typeface);
        add.setVisibility(showAdd ? View.VISIBLE : View.INVISIBLE);
        add.setColorFilter(Utils.getTextColorPrimary(getActivity()));
        add.setBackgroundResource(Utils.selectableItemBackgroundBorderless(getActivity()));
        add.setOnClickListener(v -> {
            if (callback != null) {
                if (callback.onAdd()) {
                    dismiss();
                }
            }
        });
        if (profileSparseArray != null) {
            for (int i = 0; i < profileSparseArray.size(); i++) {
                Profile profile = profileSparseArray.valueAt(i);
                if (profile.getId() != 1) {
                    RowProfileView profileView = new RowProfileView(getActivity());
                    profileView.setTypeface(typeface);
                    profileView.setProfile(profile, i == 0);
                    profileView.setAccent(accent);
                    profileView.setOnClickListener(v -> {
                        RowProfileView rowProfileView = (RowProfileView) v;
                        if (callback != null) {
                            if (callback.onSelect(rowProfileView.getProfile().getId(), rowProfileView.isActive())) {
                                dismiss();
                            }
                        }
                    });
                    linearLayout.addView(profileView);
                }
            }
        }
        if (items != null) {
            int padding = getActivity().getResources().getDimensionPixelSize(R.dimen.hv_item_padding);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            for (Item item : items) {
                TextView textView = new TextView(getActivity());
                textView.setText(item.getTitle());
                if (typeface != null) textView.setTypeface(typeface);
                textView.setTag(item.getId());
                textView.setBackgroundResource(Utils.selectableItemBackground(getActivity()));
                textView.setPadding(padding, padding / 2, padding, padding / 2);
                textView.setTextColor(Utils.getTextColorSecondary(getActivity()));
                textView.setGravity(Gravity.CENTER_VERTICAL | (hvIsRTL ? Gravity.RIGHT : Gravity.LEFT));
                textView.setOnClickListener(v -> {
                    int id = (int) v.getTag();
                    if (callback != null) {
                        if (callback.onItem(id)) {
                            dismiss();
                        }
                    }
                });
                linearLayout1.addView(textView, layoutParams);
            }
        }
    }

    public void setCallback(ProfileChooserCallback callback) {
        this.callback = callback;
    }

    public void updateTypeface(Typeface tf) {
        if (tf == null) return;
        setTypeface(tf);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof RowProfileView) {
                ((RowProfileView) view).setTypeface(typeface);
            }
        }
        for (int i = 0; i < linearLayout1.getChildCount(); i++) {
            View view = linearLayout1.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            }
        }
        title.setTypeface(typeface);
    }

    public void setTypeface(Typeface tf) {
        if (tf == null) return;
        typeface = tf;
    }

}
