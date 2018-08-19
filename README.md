# Header View

[![Join the chat at https://gitter.im/rebus007/HeaderView](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/rebus007/HeaderView?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Frebus007%2FHeaderView.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Frebus007%2FHeaderView?ref=badge_shield)
[ ![Download](https://api.bintray.com/packages/raphaelbussa/maven/header-view/images/download.svg) ](https://bintray.com/raphaelbussa/maven/header-view/_latestVersion) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Header--View-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2123)

![Logo](https://raw.githubusercontent.com/rebus007/HeaderView/master/img/ic_launcher-web.png)

This is a view for NavigationView in android.support.design library

### Import
At the moment the library is in my personal maven repo
```Gradle
repositories {
    maven {
        url 'http://dl.bintray.com/raphaelbussa/maven'
    }
}
```
```Gradle
dependencies {
    compile 'rebus:header-view:2.0.7'
}
```
### How to use
#### Via XML
Create a layout named like this header_drawer.xml
```XML
<rebus.header.view.HeaderView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/header_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:hv_add_icon="@drawable/ic_action_settings"
    app:hv_add_status_bar_height="true"
    app:hv_background_color="@color/colorPrimaryDark"
    app:hv_dialog_title="@string/account"
    app:hv_highlight_color="@color/colorAccent"
    app:hv_profile_avatar="@drawable/ic_placeholder"
    app:hv_profile_background="@drawable/ic_placeholder_bg"
    app:hv_profile_email="batman@gotham.city"
    app:hv_profile_username="Bruce Wayne"
    app:hv_show_add_button="true"
    app:hv_show_arrow="true"
    app:hv_show_gradient="true"
    app:hv_style="normal"
    app:hv_theme="light" />
```
And in your NavigationView

```XML
<android.support.design.widget.NavigationView
    android:id="@+id/nav_view"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    android:layout_gravity="start"
    app:headerLayout="@layout/header_drawer"
    app:menu="@menu/drawer" />
```
#### Manage Profiles
The new HeaderView manage different profile and a new profile chooser inspired from YouTube android app
- Create a profile
```Java
Profile profile = new Profile.Builder()
        .setId(2)
        .setUsername("Raphaël Bussa")
        .setEmail("raphaelbussa@gmail.com")
        .setAvatar("https://github.com/rebus007.png?size=512")
        .setBackground("https://images.unsplash.com/photo-1473220464492-452fb02e6221?dpr=2&auto=format&fit=crop&w=767&h=512&q=80&cs=tinysrgb&crop=")
        .build();
```
- Add a profile
```Java
headerView.addProfile(profile);
```
- Set a profile active
```Java
headerView.setProfileActive(2);
```
- Remove a profile
```Java
headerView.removeProfile(2);
```
- Get actual active profile
```Java
int activeProfileId = headerView.getProfileActive();
```

#### Customize Profile Chooser
You can also customize the profile chooser
- Add bottom items
```Java
Item item = new Item.Builder()
        .setId(1)
        .setTitle("Remove all profile")
        .build();

headerView.addDialogItem(item);
```
- HighlightColor in dialog
```
headerView.setHighlightColor(ContextCompat.getColor(this, R.color.colorAccent));
app:hv_highlight_color="@color/colorAccent"
```
- Change dialog title
```
headerView.setDialogTitle("Choose account");
app:hv_dialog_title="Dialog title"
```
- Change dialog top icon
```
headerView.setAddIconDrawable(R.drawable.ic_action_settings);
app:hv_add_icon="@drawable/ic_action_settings"
```
- Or hide dialog top icon
```
headerView.setShowAddButton(true);
app:hv_show_add_button="true"
```
- Dismiss profile chooser dialog
```Java
headerView.dismissProfileChooser();
```
#### Callback
```Java
headerView.setCallback(new HeaderCallback() {

    @Override
    public boolean onSelect(int id, boolean isActive) {
        //return profile id selected and if is the active profile
        return true; //true for close the dialog, false for do nothing
    }

    @Override
    public boolean onItem(int id) {
        //return witch buttom item is selected
        return true; //true for close the dialog, false for do nothing
    }

    @Override
    public boolean onAdd() {
        return true; //true for close the dialog, false for do nothing
    }
});
```
#### Loading image from network
Just add this in your class Application (of course you can use your preferred libs for load images)
```Java
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
```
#### Use custom font with Calligraphy
You can set a custom font with [Calligraphy](https://github.com/chrisjenx/Calligraphy) just add a CustomViewTypeface with HeaderView.class in CalligraphyConfig
```Java
CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("Oswald-Stencbab.ttf")
        .setFontAttrId(R.attr.fontPath)
        .addCustomViewWithSetTypeface(HeaderView.class)
        .build()
);
```
### Screen
![Screen](https://raw.githubusercontent.com/rebus007/HeaderView/master/img/screen.png)

### Sample
Browse the sample code [here](https://github.com/rebus007/HeaderView/tree/master/sample) or download sample app from the [Play Store](https://play.google.com/store/apps/details?id=rebus.header.view.sample) 

### Javadoc
Browse Javadoc [here](https://rebus007.github.io/HeaderView/javadoc/)

### App using Header View
If you use this lib [contact me](mailto:raphaelbussa@gmail.com) and I will add it to the list below:
- [Mister Gadget](https://play.google.com/store/apps/details?id=rebus.mister.gadget)
- [Git Chat](https://github.com/rebus007/Git-Chat)
- [The Coding Love](https://play.google.com/store/apps/details?id=rebus.thecodinglove)
- [Romanews.eu](https://play.google.com/store/apps/details?id=it.daigan.romanews)
- [Mob@rt](https://play.google.com/store/apps/details?id=it.artigiancassa.mobile.android.mobart)

### Developed By
Raphaël Bussa - [raphaelbussa@gmail.com](mailto:raphaelbussa@gmail.com)

[ ![Twitter](https://raw.githubusercontent.com/rebus007/Header-View/master/img/social/twitter-icon.png) ](https://twitter.com/rebus_007)[ ![Google Plus](https://raw.githubusercontent.com/rebus007/Header-View/master/img/social/google-plus-icon.png) ](https://plus.google.com/+RaphaelBussa/posts)[ ![Linkedin](https://raw.githubusercontent.com/rebus007/Header-View/master/img/social/linkedin-icon.png) ](https://www.linkedin.com/in/rebus007)

### License
```
The MIT License (MIT)

Copyright (c) 2017 Raphaël Bussa <raphaelbussa@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```


[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Frebus007%2FHeaderView.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Frebus007%2FHeaderView?ref=badge_large)