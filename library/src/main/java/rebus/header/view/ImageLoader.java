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
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.RestrictTo;
import android.widget.ImageView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * Created by raphaelbussa on 13/01/17.
 */
public class ImageLoader {

    public static final int AVATAR = 1;
    public static final int HEADER = 2;

    private static ImageLoader instance;
    private ImageLoaderInterface imageLoaderInterface;

    public static ImageLoader init(ImageLoaderInterface imageLoaderInterface) {
        if (instance == null) {
            instance = new ImageLoader();
        }
        instance.setImageLoaderInterface(imageLoaderInterface);
        return instance;
    }

    static void loadImage(Uri url, ImageView imageView, @Type int type) {
        if (instance == null) {
            throw new RuntimeException("ImageLoader must be implemented for use setAvatarUrl and setBackgroundUrl methods");
        }
        instance.imageLoaderInterface.loadImage(url, imageView, type);
    }

    private void setImageLoaderInterface(ImageLoaderInterface imageLoaderInterface) {
        this.imageLoaderInterface = imageLoaderInterface;
    }

    public interface ImageLoaderInterface {
        void loadImage(Uri url, ImageView imageView, @Type int type);
    }

    @RestrictTo(LIBRARY_GROUP)
    @IntDef({AVATAR, HEADER})
    @IntRange(from = 1, to = 2)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

}
