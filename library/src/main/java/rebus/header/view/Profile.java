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

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spanned;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * Created by raphaelbussa on 14/01/17.
 */

public class Profile implements Parcelable {

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
    @IdValue
    private int id;
    private Uri avatarUri;
    private int avatarRes;
    private Uri backgroundUri;
    private int backgroundRes;
    private Spanned username;
    private Spanned email;

    private Profile(int id, Uri avatarUri, int avatarRes, Uri backgroundUri, int backgroundRes, Spanned username, Spanned email) {
        this.id = id;
        this.avatarUri = avatarUri;
        this.avatarRes = avatarRes;
        this.backgroundUri = backgroundUri;
        this.backgroundRes = backgroundRes;
        this.username = username;
        this.email = email;
    }

    private Profile(Parcel in) {
        id = in.readInt();
        avatarUri = (Uri) in.readValue(Uri.class.getClassLoader());
        avatarRes = in.readInt();
        backgroundUri = (Uri) in.readValue(Uri.class.getClassLoader());
        backgroundRes = in.readInt();
        username = (Spanned) in.readValue(Spanned.class.getClassLoader());
        email = (Spanned) in.readValue(Spanned.class.getClassLoader());
    }

    int getId() {
        return id;
    }

    Uri getAvatarUri() {
        return avatarUri;
    }

    int getAvatarRes() {
        return avatarRes;
    }

    Uri getBackgroundUri() {
        return backgroundUri;
    }

    int getBackgroundRes() {
        return backgroundRes;
    }

    Spanned getUsername() {
        return username;
    }

    Spanned getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", avatarUri=" + avatarUri +
                ", avatarRes=" + avatarRes +
                ", backgroundUri=" + backgroundUri +
                ", backgroundRes=" + backgroundRes +
                ", username=" + username +
                ", email=" + email +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeValue(avatarUri);
        dest.writeValue(avatarRes);
        dest.writeValue(backgroundUri);
        dest.writeValue(backgroundRes);
        dest.writeValue(username);
        dest.writeValue(email);
    }

    @RestrictTo(LIBRARY_GROUP)
    @IntRange(from = 2)
    @Retention(RetentionPolicy.SOURCE)
    public @interface IdValue {
    }

    public static class Builder {

        @IdValue
        private int id;
        private Uri avatarUri;
        private int avatarRes;
        private Uri backgroundUri;
        private int backgroundRes;
        private Spanned username;
        private Spanned email;

        public Builder() {
        }

        /**
         * @param id set id of profile for match onClick result
         * @return current builder instance
         */
        public Builder setId(@IdValue int id) {
            this.id = id;
            return this;
        }

        /**
         * @param avatar set avatar Uri (for use this function you must implement ImageLoader)
         * @return current builder instance
         */
        public Builder setAvatar(@NonNull Uri avatar) {
            this.avatarUri = avatar;
            this.avatarRes = 0;
            return this;
        }

        /**
         * @param avatar set avatar url as String (for use this function you must implement ImageLoader)
         * @return current builder instance
         */
        public Builder setAvatar(@NonNull String avatar) {
            this.avatarUri = Uri.parse(avatar);
            this.avatarRes = 0;
            return this;
        }

        /**
         * @param avatar set avatar drawable res
         * @return current builder instance
         */
        public Builder setAvatar(@DrawableRes int avatar) {
            this.avatarRes = avatar;
            this.avatarUri = null;
            return this;
        }

        /**
         * @param background set background Uri (for use this function you must implement ImageLoader)
         * @return current builder instance
         */
        public Builder setBackground(@NonNull Uri background) {
            this.backgroundUri = background;
            this.backgroundRes = 0;
            return this;
        }

        /**
         * @param background set background url as String (for use this function you must implement ImageLoader)
         * @return current builder instance
         */
        public Builder setBackground(@NonNull String background) {
            this.backgroundUri = Uri.parse(background);
            this.backgroundRes = 0;
            return this;
        }

        /**
         * @param background set background drawable res
         * @return current builder instance
         */
        public Builder setBackground(@DrawableRes int background) {
            this.backgroundRes = background;
            this.backgroundUri = null;
            return this;
        }

        /**
         * @param username set profile username
         * @return current builder instance
         */
        public Builder setUsername(Spanned username) {
            this.username = username;
            return this;
        }

        /**
         * @param email set profile email
         * @return current builder instance
         */
        public Builder setEmail(Spanned email) {
            this.email = email;
            return this;
        }

        /**
         * @param username set profile username
         * @return current builder instance
         */
        public Builder setUsername(String username) {
            this.username = Utils.fromHtml(username);
            return this;
        }

        /**
         * @param email set profile email
         * @return current builder instance
         */
        public Builder setEmail(String email) {
            this.email = Utils.fromHtml(email);
            return this;
        }

        /**
         * @return build Profile and return it
         */
        public Profile build() {
            return new Profile(id, avatarUri, avatarRes, backgroundUri, backgroundRes, username, email);
        }

    }

}
