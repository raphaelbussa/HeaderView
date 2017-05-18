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

import android.app.Application;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import rebus.header.view.HeaderView;
import rebus.header.view.ImageLoader;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by raphaelbussa on 13/01/17.
 */

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(new ImageLoader.ImageLoaderInterface() {
            @Override
            public void loadImage(Uri url, ImageView imageView, @ImageLoader.Type int type) {
                switch (type) {
                    case ImageLoader.AVATAR:
                        Glide.with(imageView.getContext())
                                .load(url)
                                .asBitmap()
                                .placeholder(R.drawable.ic_placeholder)
                                .error(R.drawable.ic_placeholder)
                                .into(imageView);
                        break;
                    case ImageLoader.HEADER:
                        Glide.with(imageView.getContext())
                                .load(url)
                                .asBitmap()
                                .placeholder(R.drawable.ic_placeholder_bg)
                                .error(R.drawable.ic_placeholder_bg)
                                .into(imageView);
                        break;
                }
            }

        });
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Oswald-Stencbab.ttf")
                .setFontAttrId(R.attr.fontPath)
                .addCustomViewWithSetTypeface(HeaderView.class)
                .build()
        );
    }
}
