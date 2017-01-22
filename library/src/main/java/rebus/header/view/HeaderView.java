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

import android.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.RestrictTo;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * Created by raphaelbussa on 11/01/17.
 */

public class HeaderView extends ViewGroup implements ProfileChooserCallback {

    public static final int STYLE_NORMAL = 1;
    public static final int STYLE_COMPACT = 2;
    public static final int THEME_LIGHT = 1;
    public static final int THEME_DARK = 2;
    private static final String TAG = HeaderView.class.getName();
    private int statusBarHeight;

    private CircleImageView avatar;
    private CircleImageView avatar2;
    private CircleImageView avatar3;
    private ImageView background;
    private View gradient;
    private View selector;
    private TextView username;
    private TextView email;
    private ImageView arrow;

    private int hvAvatarDimen;
    private int hvMarginDimen;
    private int hvTextDimen;
    private int hvArrowDimen;
    private int hvAvatarMiniDimen;
    private int hvTextColor;
    private int hvStyle;
    private int hvTheme;
    @ColorInt
    private int hvBackgroundColor;
    @ColorInt
    private int hvHighlightColor;
    private boolean hvBelowToolbar;
    private boolean hvShowGradient;
    private boolean hvShowAddButton;
    private boolean hvShowArrow;
    @DrawableRes
    private int hvAddIconDrawable;
    private String hvDialogTitle;

    private SparseArray<Profile> profileSparseArray = new SparseArray<>();
    private ArrayList<Item> itemArrayList = new ArrayList<>();

    private HeaderCallback headerCallback;
    private FragmentManager hvFragmentManager;

    public HeaderView(Context context) {
        super(context);
        init(null, 0);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        Log.d(TAG, "init");
        setupAttributeSet(attrs, defStyle);
        setupResources();
        addBackground();
        addGradient();
        addSelector();
        addAvatar();
        addUsername();
        addEmail();
        addArrow();
        addAvatar2();
        addAvatar3();
        setDefaultValues();
    }

    public void setStyle(@Style int style) {
        hvStyle = style;
    }

    public void setTheme(@Theme int theme) {
        hvTheme = theme;
        if (hvTheme == THEME_LIGHT) {
            hvTextColor = Color.WHITE;
        }
        if (hvTheme == THEME_DARK) {
            hvTextColor = Color.BLACK;
        }
        username.setTextColor(hvTextColor);
        email.setTextColor(hvTextColor);
        arrow.setColorFilter(hvTextColor);
    }

    public void setCallback(HeaderCallback headerCallback) {
        this.headerCallback = headerCallback;
    }

    public void clearProfile() {
        profileSparseArray.clear();
        populateAvatar();
    }

    public void addProfile(List<Profile> profiles) {
        for (Profile profile : profiles) {
            profileSparseArray.put(profile.getId(), profile);
        }
        populateAvatar();
        Log.d(TAG, profileSparseArray.toString());
    }

    public void addProfile(Profile... profiles) {
        for (Profile profile : profiles) {
            profileSparseArray.put(profile.getId(), profile);
        }
        populateAvatar();
        Log.d(TAG, profileSparseArray.toString());
    }

    public void addDialogItem(Item... item) {
        itemArrayList.clear();
        Collections.addAll(itemArrayList, item);
    }

    public void addDialogItem(List<Item> items) {
        itemArrayList.clear();
        itemArrayList.addAll(items);
    }

    public void removeProfile(int id) {
        for (int i = 0; i < profileSparseArray.size(); i++) {
            Profile profile = profileSparseArray.valueAt(i);
            if (profile.getId() == id) {
                profileSparseArray.removeAt(i);
                populateAvatar();
                return;
            }
        }
    }

    public int getProfileActive() {
        return profileSparseArray.valueAt(0).getId();
    }

    public void setProfileActive(int id) {
        for (int i = 0; i < profileSparseArray.size(); i++) {
            Profile profile = profileSparseArray.valueAt(i);
            if (profile.getId() == id) {
                swapProfiles(i);
                return;
            }
        }
    }

    public void setShowArrow(boolean showArrow) {
        hvShowArrow = showArrow;
        addArrow();
    }

    public void setShowGradient(boolean showGradient) {
        hvShowGradient = showGradient;
        addGradient();
    }

    /**
     * @param addIconDrawable replace default add icon, param 14dp icon and 16dp padding
     */
    public void setAddIconDrawable(@DrawableRes int addIconDrawable) {
        hvAddIconDrawable = addIconDrawable;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        hvFragmentManager = fragmentManager;
    }

    public void setShowAddButton(boolean showAddButton) {
        hvShowAddButton = showAddButton;
    }

    public void setBackgroundColor(@ColorInt int color) {
        background.setBackgroundColor(color);
    }

    public void setBackgroundColorRes(@ColorRes int color) {
        background.setBackgroundColor(Utils.getColor(getContext(), color));
    }

    public void setOnAvatarClickListener(View.OnClickListener onClickListener) {
        avatar.setOnClickListener(onClickListener);
    }

    public void setOnLongAvatarClickListener(View.OnLongClickListener onLongClickListener) {
        avatar.setOnLongClickListener(onLongClickListener);
    }

    public void setOnHeaderClickListener(OnClickListener onClickListener) {
        selector.setOnClickListener(onClickListener);
    }

    public void setOnLongHeaderClickListener(View.OnLongClickListener onLongClickListener) {
        selector.setOnLongClickListener(onLongClickListener);
    }

    public void setHighlightColor(int highlightColor) {
        hvHighlightColor = highlightColor;
    }

    public void setDialogTitle(String dialogTitle) {
        hvDialogTitle = dialogTitle;
    }

    private void populateAvatar() {
        int size = profileSparseArray.size();
        Log.d(TAG, "profileSparseArray.size() [" + profileSparseArray.size() + "]");
        if (size >= 3) {
            size = 3;
        }
        avatar.setVisibility(INVISIBLE);
        username.setVisibility(INVISIBLE);
        email.setVisibility(INVISIBLE);
        background.setImageDrawable(null);

        avatar2.setVisibility(INVISIBLE);
        avatar3.setVisibility(INVISIBLE);
        for (int i = 0; i < size; i++) {
            Profile profile = profileSparseArray.valueAt(i);
            Log.d(TAG, "pos [" + i + "] " + profile.getUsername());
            switch (i) {
                case 0:
                    setFirstProfile(profile);
                    break;
                case 1:
                    setSecondProfile(profile);
                    break;
                case 2:
                    setThirdProfile(profile);
                    break;
            }
        }
    }

    private void setDefaultValues() {
        headerCallback = new HeaderCallback();
        populateAvatar();
        setBackgroundColor(hvBackgroundColor);
    }

    private void setFirstProfile(Profile profile) {
        username.setText(profile.getUsername());
        email.setText(profile.getEmail());
        if (profile.getAvatarDrawable() != null)
            avatar.setImageDrawable(profile.getAvatarDrawable());
        if (profile.getBackgroundDrawable() != null)
            background.setImageDrawable(profile.getBackgroundDrawable());
        if (profile.getAvatarUri() != null)
            ImageLoader.loadImage(profile.getAvatarUri(), avatar, ImageLoader.AVATAR);
        if (profile.getBackgroundUri() != null)
            ImageLoader.loadImage(profile.getBackgroundUri(), background, ImageLoader.HEADER);
        avatar.setVisibility(VISIBLE);
        username.setVisibility(VISIBLE);
        email.setVisibility(VISIBLE);
        avatar.setTag(R.id.hv_first_profile, profile.getId());
        avatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                headerCallback.onSelect((int) v.getTag(R.id.hv_first_profile), true);
            }
        });
    }

    private void setSecondProfile(Profile profile) {
        if (profile.getAvatarDrawable() != null)
            avatar2.setImageDrawable(profile.getAvatarDrawable());
        if (profile.getAvatarUri() != null)
            ImageLoader.loadImage(profile.getAvatarUri(), avatar2, ImageLoader.AVATAR);
        avatar2.setVisibility(VISIBLE);
        avatar2.setTag(R.id.hv_second_profile, profile.getId());
        avatar2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                headerCallback.onSelect((int) v.getTag(R.id.hv_second_profile), false);
                swapProfiles(1);
            }
        });
    }

    private void setThirdProfile(Profile profile) {
        if (profile.getAvatarDrawable() != null)
            avatar3.setImageDrawable(profile.getAvatarDrawable());
        if (profile.getAvatarUri() != null)
            ImageLoader.loadImage(profile.getAvatarUri(), avatar3, ImageLoader.AVATAR);
        avatar3.setVisibility(VISIBLE);
        avatar3.setTag(R.id.hv_third_profile, profile.getId());
        avatar3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                headerCallback.onSelect((int) v.getTag(R.id.hv_third_profile), false);
                swapProfiles(2);
            }
        });
    }

    private void swapProfiles(int pos) {
        if (pos == 0) return;
        Profile newFirst = profileSparseArray.valueAt(pos);
        Profile oldFirst = profileSparseArray.valueAt(0);
        profileSparseArray.setValueAt(0, newFirst);
        profileSparseArray.setValueAt(pos, oldFirst);
        populateAvatar();
    }

    @SuppressWarnings("Range")
    private void setupAttributeSet(AttributeSet attrs, int defStyle) {

        String hvUsername;
        String hvEmail;
        int hvAvatar;
        int hvBackground;

        if (attrs == null) {

            hvUsername = "";
            hvEmail = "";
            hvAvatar = 0;
            hvBackground = 0;

            hvBackgroundColor = Color.TRANSPARENT;
            hvHighlightColor = Color.BLACK;
            hvBelowToolbar = false;
            hvStyle = STYLE_NORMAL;
            hvTheme = THEME_LIGHT;
            hvShowGradient = true;
            hvShowArrow = true;
            hvShowAddButton = true;
            hvDialogTitle = "Account";
            hvAddIconDrawable = R.drawable.hv_add_profile;
        } else {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HeaderView, defStyle, 0);

            hvUsername = typedArray.getString(R.styleable.HeaderView_hv_profile_username);
            hvEmail = typedArray.getString(R.styleable.HeaderView_hv_profile_email);
            hvAvatar = typedArray.getResourceId(R.styleable.HeaderView_hv_profile_avatar, 0);
            hvBackground = typedArray.getResourceId(R.styleable.HeaderView_hv_profile_background, 0);

            hvBackgroundColor = typedArray.getColor(R.styleable.HeaderView_hv_background_color, Color.TRANSPARENT);
            hvHighlightColor = typedArray.getColor(R.styleable.HeaderView_hv_highlight_color, Color.BLACK);
            hvBelowToolbar = typedArray.getBoolean(R.styleable.HeaderView_hv_below_toolbar, false);
            hvStyle = typedArray.getInt(R.styleable.HeaderView_hv_style, STYLE_NORMAL);
            hvTheme = typedArray.getInt(R.styleable.HeaderView_hv_theme, THEME_LIGHT);
            hvShowGradient = typedArray.getBoolean(R.styleable.HeaderView_hv_show_gradient, true);
            hvShowArrow = typedArray.getBoolean(R.styleable.HeaderView_hv_show_arrow, true);
            hvShowAddButton = typedArray.getBoolean(R.styleable.HeaderView_hv_show_add_button, true);
            hvDialogTitle = typedArray.getString(R.styleable.HeaderView_hv_dialog_title);
            hvAddIconDrawable = typedArray.getResourceId(R.styleable.HeaderView_hv_add_icon, R.drawable.hv_add_profile);
            typedArray.recycle();
        }

        if ((hvUsername != null && !hvUsername.isEmpty()) || (hvEmail != null && !hvEmail.isEmpty()) || hvAvatar != 0 || hvBackground != 0) {
            Log.d(TAG, "profile created from XML");
            Profile profile = new Profile.Builder()
                    .setId(1)
                    .setUsername(hvUsername)
                    .setEmail(hvEmail)
                    .setAvatar(Utils.getDrawable(getContext(), hvAvatar))
                    .setBackground(Utils.getDrawable(getContext(), hvBackground))
                    .build();

            profileSparseArray.put(profile.getId(), profile);
        }

    }

    private void setupResources() {
        statusBarHeight = Utils.getStatusBarHeight(getContext());
        hvAvatarDimen = getResources().getDimensionPixelSize(R.dimen.hv_avatar);
        hvAvatarMiniDimen = getResources().getDimensionPixelSize(R.dimen.hv_avatar_mini);
        hvMarginDimen = getResources().getDimensionPixelSize(R.dimen.hv_margin);
        hvTextDimen = getResources().getDimensionPixelSize(R.dimen.hv_text);
        hvArrowDimen = getResources().getDimensionPixelSize(R.dimen.hv_arrow);
        if (hvTheme == THEME_LIGHT) {
            hvTextColor = Color.WHITE;
        }
        if (hvTheme == THEME_DARK) {
            hvTextColor = Color.BLACK;
        }
    }

    private void addBackground() {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        background = new ImageView(getContext());
        background.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(background, 0, layoutParams);
    }

    private void addSelector() {
        selector = new View(getContext());
        selector.setBackgroundResource(Utils.selectableItemBackground(getContext()));
        addView(selector, 2);
    }

    private void addGradient() {
        gradient = new View(getContext());
        gradient.setBackgroundColor(Color.parseColor("#38000000"));
        addView(gradient, 1);
        gradient.setVisibility(hvShowGradient ? VISIBLE : GONE);
    }

    private void addAvatar() {
        LayoutParams layoutParams = new LayoutParams(hvAvatarDimen, hvAvatarDimen);
        avatar = new CircleImageView(getContext());
        avatar.setBackgroundResource(Utils.selectableItemBackgroundBorderless(getContext()));
        addView(avatar, 3, layoutParams);
    }

    private void addUsername() {
        username = new TextView(getContext());
        username.setTextColor(hvTextColor);
        username.setTypeface(Typeface.DEFAULT_BOLD);
        username.setGravity(Gravity.CENTER_VERTICAL);
        username.setMaxLines(1);
        username.setEllipsize(TextUtils.TruncateAt.END);
        addView(username, 4);
    }

    private void addEmail() {
        email = new TextView(getContext());
        email.setTextColor(hvTextColor);
        email.setGravity(Gravity.CENTER_VERTICAL);
        email.setMaxLines(1);
        email.setEllipsize(TextUtils.TruncateAt.END);
        addView(email, 5);
    }

    private void addArrow() {
        arrow = new ImageView(getContext());
        arrow.setImageResource(R.drawable.hv_arrow);
        arrow.setBackgroundResource(Utils.selectableItemBackground(getContext()));
        arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hvFragmentManager != null) {
                    ProfileChooserFragment profileChooserFragment = ProfileChooserFragment.newInstance(profileSparseArray, itemArrayList, hvHighlightColor, hvShowAddButton, hvDialogTitle, hvAddIconDrawable);
                    profileChooserFragment.setCallback(HeaderView.this);
                    profileChooserFragment.show(hvFragmentManager, "ProfileChooserFragment");
                } else {
                    ProfileChooser profileChooser = new ProfileChooser(getContext(), profileSparseArray, itemArrayList, hvHighlightColor, hvShowAddButton, hvDialogTitle, hvAddIconDrawable);
                    profileChooser.setCallback(HeaderView.this);
                    profileChooser.show();
                }
            }
        });
        arrow.setVisibility(hvShowArrow ? VISIBLE : GONE);
        addView(arrow, 6);
    }

    private void addAvatar2() {
        LayoutParams layoutParams = new LayoutParams(hvAvatarMiniDimen, hvAvatarMiniDimen);
        avatar2 = new CircleImageView(getContext());
        avatar2.setVisibility(INVISIBLE);
        avatar2.setBackgroundResource(Utils.selectableItemBackgroundBorderless(getContext()));
        addView(avatar2, 7, layoutParams);
    }

    private void addAvatar3() {
        LayoutParams layoutParams = new LayoutParams(hvAvatarMiniDimen, hvAvatarMiniDimen);
        avatar3 = new CircleImageView(getContext());
        avatar3.setVisibility(INVISIBLE);
        avatar3.setBackgroundResource(Utils.selectableItemBackgroundBorderless(getContext()));
        addView(avatar3, 8, layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout changed [" + changed + "]");
        if (!changed) return;
        background.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        if (hvShowGradient) {
            gradient.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }
        selector.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        avatar.layout(hvMarginDimen,
                getDimensionFix(hvMarginDimen),
                hvMarginDimen + hvAvatarDimen,
                getDimensionFix(hvAvatarDimen + hvMarginDimen));
        if (hvStyle == STYLE_NORMAL) {
            avatar2.layout(getMeasuredWidth() - hvMarginDimen - hvAvatarMiniDimen,
                    getDimensionFix(hvMarginDimen),
                    getMeasuredWidth() - hvMarginDimen,
                    getDimensionFix(hvAvatarMiniDimen + hvMarginDimen));
            avatar3.layout(avatar2.getLeft() - hvMarginDimen - hvAvatarMiniDimen,
                    getDimensionFix(hvMarginDimen),
                    avatar2.getLeft() - hvMarginDimen,
                    getDimensionFix(hvAvatarMiniDimen + hvMarginDimen));
            arrow.layout(getMeasuredWidth() - hvArrowDimen,
                    getMeasuredHeight() - hvArrowDimen,
                    getMeasuredWidth(),
                    getMeasuredHeight());
            username.layout(hvMarginDimen,
                    avatar.getBottom() + hvMarginDimen,
                    arrow.getLeft(),
                    avatar.getBottom() + hvMarginDimen + hvTextDimen);
            email.layout(hvMarginDimen,
                    username.getBottom(),
                    arrow.getLeft(),
                    username.getBottom() + hvTextDimen);
        }
        if (hvStyle == STYLE_COMPACT) {
            avatar3.layout(0, 0, 0, 0);
            avatar2.layout(0, 0, 0, 0);
            arrow.layout(getMeasuredWidth() - hvArrowDimen,
                    getDimensionFix(getMeasuredHeight()) / 2 - hvArrowDimen / 2,
                    getMeasuredWidth(),
                    getDimensionFix(getMeasuredHeight()) / 2 + hvArrowDimen / 2);
            username.layout(avatar.getRight() + hvMarginDimen,
                    getDimensionFix(getMeasuredHeight()) / 2 - hvTextDimen,
                    arrow.getLeft(),
                    getDimensionFix(getMeasuredHeight()) / 2);
            email.layout(avatar.getRight() + hvMarginDimen,
                    getDimensionFix(getMeasuredHeight()) / 2,
                    arrow.getLeft(),
                    getDimensionFix(getMeasuredHeight()) / 2 + hvTextDimen);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getHeaderHeight(heightMeasureSpec));
    }

    private int getDimensionFix(int dimen) {
        if (hvBelowToolbar) {
            return dimen;
        } else {
            return dimen + statusBarHeight;
        }
    }

    private int getHeaderHeight(int heightMeasureSpec) {
        if (hvStyle == STYLE_NORMAL) {
            if (hvBelowToolbar) {
                return getResources().getDimensionPixelSize(R.dimen.hv_normal);
            } else {
                return getResources().getDimensionPixelSize(R.dimen.hv_normal) + statusBarHeight;
            }
        }
        if (hvStyle == STYLE_COMPACT) {
            if (hvBelowToolbar) {
                return getResources().getDimensionPixelSize(R.dimen.hv_compact);
            } else {
                return getResources().getDimensionPixelSize(R.dimen.hv_compact) + statusBarHeight;
            }
        }
        return getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d(TAG, "onSaveInstanceState");
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        //STORE CUSTOM VALUES
        bundle.putSparseParcelableArray("PROFILE_LIST", profileSparseArray);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.d(TAG, "onRestoreInstanceState");
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            state = bundle.getParcelable("superState");
            //RESTORE CUSTOM VALUES
            profileSparseArray = bundle.getSparseParcelableArray("PROFILE_LIST");
            populateAvatar();
        }
        if (hvFragmentManager != null) {
            ProfileChooserFragment profileChooserFragment = (ProfileChooserFragment) hvFragmentManager.findFragmentByTag("ProfileChooserFragment");
            if (profileChooserFragment != null) {
                profileChooserFragment.setCallback(HeaderView.this);
            }
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    public boolean onSelect(int id, boolean selected) {
        setProfileActive(id);
        return headerCallback.onSelect(id, selected);
    }

    @Override
    public boolean onItem(int id) {
        return headerCallback.onItem(id);
    }

    @Override
    public boolean onAdd() {
        return headerCallback.onAdd();
    }

    @RestrictTo(LIBRARY_GROUP)
    @IntDef({STYLE_NORMAL, STYLE_COMPACT})
    @IntRange(from = 1, to = 2)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {
    }

    @RestrictTo(LIBRARY_GROUP)
    @IntDef({THEME_LIGHT, THEME_DARK})
    @IntRange(from = 1, to = 2)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Theme {
    }

}
