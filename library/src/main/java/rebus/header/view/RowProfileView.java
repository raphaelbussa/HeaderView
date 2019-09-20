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
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by raphaelbussa on 16/01/17.
 */

class RowProfileView extends ViewGroup {

    private CircleImageView avatar;
    private TextView username;
    private TextView email;
    private ImageView check;

    private int hvAvatarRowDimen;
    private int hvRowHeightDimen;
    private int hvRowMarginDimen;
    private int hvRowCheckDimen;
    private int hvRowMarginTextDimen;
    private int hvTextDimen;
    private int hvRowAvatarBorderDimen;

    private Profile hvProfile;
    private boolean hvActive;
    private boolean hvIsRTL;

    private int accent = Color.BLACK;

    private Typeface typeface;

    public RowProfileView(Context context) {
        super(context);
        init();
    }

    public RowProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RowProfileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(Utils.selectableItemBackground(getContext()));
        setupResources();
        addViews();
    }

    public void setAccent(@ColorInt int accentColor) {
        accent = accentColor;
        avatar.setBorderColor(accent);
        check.setColorFilter(accent);
    }

    public boolean isActive() {
        return hvActive;
    }

    public Profile getProfile() {
        return hvProfile;
    }

    public void setProfile(Profile profile, boolean active) {
        hvActive = active;
        hvProfile = profile;
        avatar.setBorderWidth(active ? hvRowAvatarBorderDimen : 0);
        if (hvActive) {
            avatar.setPadding(0, 0, 0, 0);
        } else {
            avatar.setPadding(hvRowAvatarBorderDimen, hvRowAvatarBorderDimen, hvRowAvatarBorderDimen, hvRowAvatarBorderDimen);
        }
        check.setVisibility(hvActive ? VISIBLE : INVISIBLE);
        if (typeface != null) {
            username.setTypeface(typeface);
            email.setTypeface(typeface);
        } else {
            username.setTypeface(active ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            email.setTypeface(active ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        }
        username.setText(hvProfile.getUsername());
        email.setText(hvProfile.getEmail());
        if (hvProfile.getAvatarRes() != 0)
            avatar.setImageResource(hvProfile.getAvatarRes());
        if (hvProfile.getAvatarUri() != null)
            ImageLoader.loadImage(hvProfile.getAvatarUri(), avatar, ImageLoader.AVATAR);
    }

    @SuppressLint("RtlHardcoded")
    private void addViews() {
        LayoutParams avatarLayoutParams = new LayoutParams(hvAvatarRowDimen, hvAvatarRowDimen);
        LayoutParams checkLayoutParams = new LayoutParams(hvRowCheckDimen, hvRowCheckDimen);
        avatar = new CircleImageView(getContext());
        avatar.setBorderColor(accent);
        avatar.setBorderWidth(hvRowAvatarBorderDimen);
        check = new ImageView(getContext());
        check.setImageResource(R.drawable.hv_profile_check);
        check.setColorFilter(accent);
        username = new TextView(getContext());
        username.setTextColor(Utils.getTextColorPrimary(getContext()));
        username.setGravity(Gravity.CENTER_VERTICAL | (hvIsRTL ? Gravity.RIGHT : Gravity.LEFT));
        username.setMaxLines(1);
        username.setEllipsize(TextUtils.TruncateAt.END);
        email = new TextView(getContext());
        email.setTextColor(Utils.getTextColorSecondary(getContext()));
        email.setGravity(Gravity.CENTER_VERTICAL | (hvIsRTL ? Gravity.RIGHT : Gravity.LEFT));
        email.setMaxLines(1);
        email.setEllipsize(TextUtils.TruncateAt.END);
        addView(avatar, avatarLayoutParams);
        addView(check, checkLayoutParams);
        addView(username);
        addView(email);
    }

    private void setupResources() {
        accent = Color.BLACK;
        hvIsRTL = getResources().getBoolean(R.bool.is_right_to_left);
        hvAvatarRowDimen = getResources().getDimensionPixelSize(R.dimen.hv_row_avatar);
        hvRowHeightDimen = getResources().getDimensionPixelSize(R.dimen.hv_row_height);
        hvRowMarginDimen = getResources().getDimensionPixelSize(R.dimen.hv_row_margin);
        hvRowCheckDimen = getResources().getDimensionPixelSize(R.dimen.hv_row_check);
        hvRowMarginTextDimen = getResources().getDimensionPixelSize(R.dimen.hv_row_margin_text);
        hvTextDimen = getResources().getDimensionPixelSize(R.dimen.hv_text);
        hvRowAvatarBorderDimen = getResources().getDimensionPixelSize(R.dimen.hv_row_avatar_border);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), hvRowHeightDimen);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!changed) return;
        if (hvIsRTL) {
            avatar.layout(getMeasuredWidth() - hvRowMarginDimen - hvAvatarRowDimen,
                    getMeasuredHeight() / 2 - hvAvatarRowDimen / 2,
                    getMeasuredWidth() - hvRowMarginDimen,
                    getMeasuredHeight() / 2 + hvAvatarRowDimen / 2);
            check.layout(hvRowMarginDimen,
                    getMeasuredHeight() / 2 - hvRowCheckDimen / 2,
                    hvRowCheckDimen + hvRowMarginDimen,
                    getMeasuredHeight() / 2 + hvRowCheckDimen / 2);
            username.layout(check.getRight() + hvRowMarginTextDimen,
                    getMeasuredHeight() / 2 - hvTextDimen,
                    avatar.getLeft() - hvRowMarginTextDimen,
                    getMeasuredHeight() / 2);
            email.layout(check.getRight() + hvRowMarginTextDimen,
                    getMeasuredHeight() / 2,
                    avatar.getLeft() - hvRowMarginTextDimen,
                    getMeasuredHeight() / 2 + hvTextDimen);
        } else {
            avatar.layout(hvRowMarginDimen,
                    getMeasuredHeight() / 2 - hvAvatarRowDimen / 2,
                    hvRowMarginDimen + hvAvatarRowDimen,
                    getMeasuredHeight() / 2 + hvAvatarRowDimen / 2);
            check.layout(getMeasuredWidth() - hvRowMarginDimen - hvRowCheckDimen,
                    getMeasuredHeight() / 2 - hvRowCheckDimen / 2,
                    getMeasuredWidth() - hvRowMarginDimen,
                    getMeasuredHeight() / 2 + hvRowCheckDimen / 2);
            username.layout(avatar.getRight() + hvRowMarginTextDimen,
                    getMeasuredHeight() / 2 - hvTextDimen,
                    check.getLeft() - hvRowMarginTextDimen,
                    getMeasuredHeight() / 2);
            email.layout(avatar.getRight() + hvRowMarginTextDimen,
                    getMeasuredHeight() / 2,
                    check.getLeft() - hvRowMarginTextDimen,
                    getMeasuredHeight() / 2 + hvTextDimen);
        }
    }

    public void setTypeface(Typeface tf) {
        if (tf == null) return;
        typeface = tf;
        username.setTypeface(tf);
        email.setTypeface(tf);
    }

}
