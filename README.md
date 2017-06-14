# FloatingMenu
Android 浮动菜单效果

## 效果图
![浮动菜单效果图](http://img.blog.csdn.net/20170614141749822?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvSVRSZW5q/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
## 使用：
### 布局文件中添加代码：
  <pre><code><com.renj.floatingmenu.weight.FloatingMenu
        android:id="@+id/floating"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_alignParentRight="true"
        android:layout_marginBottom="10dp"
        android:layout_marginRight="10dp">

        <!--第一个子控件表示母菜单-->
        <ImageView
            android:id="@+id/imageView3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentLeft="true"
            android:layout_alignParentStart="true"
            android:layout_alignParentTop="true"
            android:src="@mipmap/ic_launcher" />

        <!--其余的控件表示子菜单-->
        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@mipmap/ic_launcher_round" />

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@mipmap/ic_launcher_round" />

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@mipmap/ic_launcher_round" />

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@mipmap/ic_launcher_round" />

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@mipmap/ic_launcher_round" />

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@mipmap/ic_launcher_round" />
    </com.renj.floatingmenu.weight.FloatingMenu></code></pre>

### 设置子菜单点击监听：
  <pre><code>floating.setOnItemMenuClickListener(new FloatingMenu.OnItemMenuClickListener() {
            @Override
            public void onItemMenuClick(View view, int position) {
                Toast.makeText(MainActivity.this, "子菜单 - " + position, Toast.LENGTH_SHORT).show();
            }
        });</code></pre>
