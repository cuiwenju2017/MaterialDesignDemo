package com.example.materialdesigndemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesigndemo.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    List<String> titles = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    private ViewHolder holder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initData();
    }

    private void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true) //状态栏字体是深色，不写默认为亮色
                .titleBar(binding.ll) //解决状态栏和布局重叠问题，任选其一
                .init();

        ImmersionBar.with(this)
                .statusBarDarkFont(true) //状态栏字体是深色，不写默认为亮色
                .titleBar(binding.includeHeader.llDrawerHeader) //解决状态栏和布局重叠问题，任选其一
                .init();

        //设置锁定模式来禁用侧滑
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void initData() {
        //打开左
        binding.siv.setOnClickListener(v -> binding.drawerLayout.openDrawer(GravityCompat.START));
        //关闭左
        binding.tvCloseRight.setOnClickListener(v -> binding.drawerLayout.closeDrawer(GravityCompat.START));
        //活动
        binding.tvActivity.setOnClickListener(v -> Toast.makeText(MainActivity.this, "活动", Toast.LENGTH_SHORT).show());

        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {///解除锁定模式
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {//设置锁定模式来禁用侧滑
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //添加标题
        titles.add("推荐");
        titles.add("会员精选");
        //添加Fragment进去
        fragments.add(new TestFragment(0));
        fragments.add(new TestFragment(1));
        //实例化适配器
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        //设置适配器
        binding.vp2.setAdapter(myAdapter);
        //设置abLayout点击时的水波效果
        binding.tabLayout.setTabRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
        //TabLayout和Viewpager2进行关联
        new TabLayoutMediator(binding.tabLayout, binding.vp2, (tab, position) -> tab.setText(titles.get(position))).attach();

        /*
          字体变化设置
         */
        holder = null;
        for (int i = 0; i < titles.size(); i++) {
            //获取tab
            TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
            //给tab设置自定义布局
            tab.setCustomView(R.layout.tab_item);
            holder = new ViewHolder(Objects.requireNonNull(tab.getCustomView()));
            //填充数据
            holder.tab_item_title.setText(titles.get(i));

            //默认选择第一项
            if (i == 0) {
                holder.tab_item_title.setSelected(true);
                holder.tab_item_title.setTextSize(20);

                /*常用的字体类型名称有：
                Typeface.DEFAULT //常规字体类型
                Typeface.DEFAULT_BOLD //黑体字体类型
                Typeface.MONOSPACE //等宽字体类型
                Typeface.SANS_SERIF //无衬线字体类型
                Typeface.SERIF //衬线字体字体类型

                常用的字体风格名称还有：
                Typeface.BOLD //粗体
                Typeface.BOLD_ITALIC //粗斜体
                Typeface.ITALIC //斜体
                Typeface.NORMAL //常规*/
                holder.tab_item_title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                //字体颜色
                holder.tab_item_title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        }

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tab_item_title.setSelected(true);
                //设置选中后的字体大小
                holder.tab_item_title.setTextSize(20);
                //字体类型、字体风格
                holder.tab_item_title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                //关联Viewpager
                binding.vp2.setCurrentItem(tab.getPosition());
                //字体颜色
                holder.tab_item_title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tab_item_title.setSelected(false);
                //恢复默认字体大小
                holder.tab_item_title.setTextSize(15);
                //字体类型、字体风格
                holder.tab_item_title.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                //字体颜色
                holder.tab_item_title.setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    static class ViewHolder {
        TextView tab_item_title;

        ViewHolder(View tabView) {
            tab_item_title = tabView.findViewById(R.id.tab_item_title);
        }
    }
}