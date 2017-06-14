package com.renj.floatingmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.renj.floatingmenu.weight.FloatingMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingMenu floating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        floating = (FloatingMenu) findViewById(R.id.floating);

        initFloatingMenu();
        setlistView();
    }

    /**
     * 初始化浮动菜单控件
     */
    private void initFloatingMenu() {
        floating.setOnItemMenuClickListener(new FloatingMenu.OnItemMenuClickListener() {
            @Override
            public void onItemMenuClick(View view, int position) {
                Toast.makeText(MainActivity.this, "子菜单 - " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 设置ListView中的数据
     */
    private void setlistView() {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add("ListView Item Data - " + i);
        }
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 关闭菜单和响应条目事件不同时进行
                // 如果需要同时进行就去掉判断语句就行了
                // floating.closeMenu();
                // Toast.makeText(MainActivity.this, list.get(position) + "", Toast.LENGTH_SHORT).show();
                if (floating.isOpen()) {
                    floating.closeMenu();
                } else {
                    Toast.makeText(MainActivity.this, list.get(position) + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
