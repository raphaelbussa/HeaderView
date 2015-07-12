# Header-View
[ ![Download](https://api.bintray.com/packages/raphaelbussa/maven/header-view/images/download.svg) ](https://bintray.com/raphaelbussa/maven/header-view/_latestVersion)

This is a view for NavigationView in android.support.design library

![Screen](https://raw.githubusercontent.com/rebus007/Header-View/master/img/screen/screen_1.png)![Screen](https://raw.githubusercontent.com/rebus007/Header-View/master/img/screen/screen_2.png)![Screen](https://raw.githubusercontent.com/rebus007/Header-View/master/img/screen/screen_3.png)

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
    compile 'rebus:header-view:1.0.0'
}
```
### How to use
You MUST declare the view in your class, you can use two different layout, normal or compact

Normal Header View
```java
HeaderView headerView = new HeaderView(this);
        headerView.background().setBackgroundColor(getResources().getColor(R.color.primary_dark));
        Picasso.with(HeaderActivity.this)
                .load("http://www.nexus-lab.com/wp-content/uploads/2014/08/image_new-material.jpeg")
                .into(headerView.background());
        Picasso.with(HeaderActivity.this)
                .load("https://avatars1.githubusercontent.com/u/3964819?v=3&s=460")
                .into(headerView.avatar());
        headerView.username("Raphael Bussa");
        headerView.email("rapahelbussa@gmail.com");
        headerView.setOnHeaderClickListener(new HeaderInterface.OnHeaderClickListener() {
            @Override
            public void onClick() {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
navigationView.addHeaderView(headerView);
```

Compact Header View
```java
HeaderCompactView headerCompactView = new HeaderCompactView(this, false); //true if you want to use this view below toolbar
        headerCompactView.background().setBackgroundColor(getResources().getColor(R.color.primary_dark));
        Picasso.with(HeaderActivity.this)
                .load("http://www.nexus-lab.com/wp-content/uploads/2014/08/image_new-material.jpeg")
                .into(headerCompactView.background());
        Picasso.with(HeaderActivity.this)
                .load("https://avatars1.githubusercontent.com/u/3964819?v=3&s=460")
                .into(headerCompactView.avatar());
        headerCompactView.username("Raphael Bussa");
        headerCompactView.email("rapahelbussa@gmail.com");
        headerCompactView.setOnHeaderClickListener(new HeaderInterface.OnHeaderClickListener() {
            @Override
            public void onClick() {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
navigationView.addHeaderView(headerCompactView);
```

### App using Header View
If you use this lib [contact me](mailto:raphaelbussa@gmail.com?subject=WordPress Helper) and I will add it to the list below:
- [Mister Gadget Beta](https://play.google.com/store/apps/details?id=rebus.mister.gadget)

###Developed By
Raphaël Bussa - [raphaelbussa@gmail.com](mailto:raphaelbussa@gmail.com)

[ ![Twitter](https://raw.githubusercontent.com/rebus007/Header-View/master/img/social/twitter-icon.png) ](https://twitter.com/rebus_007)[ ![Google Plus](https://raw.githubusercontent.com/rebus007/Header-View/master/img/social/google-plus-icon.png) ](https://plus.google.com/+RaphaelBussa/posts)[ ![Linkedin](https://raw.githubusercontent.com/rebus007/Header-View/master/img/social/linkedin-icon.png) ](https://www.linkedin.com/in/rebus007)

### License
```
The MIT License (MIT)

Copyright (c) 2015 Raphael Bussa <raphaelbussa@gmail.com>

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
