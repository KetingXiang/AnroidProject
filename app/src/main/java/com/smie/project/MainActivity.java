package com.smie.project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends FragmentActivity {
    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private List<Fragment> data;
    private TextView activity_main_textview_menu;
    private TextView activity_main_textview_person;
    private ImageView activity_main_image_add_programs ;
    private ImageView activity_main_image_menu ;
    private ImageView activity_main_image_person ;
    private RelativeLayout menuLayout;
    private LinearLayout personLayout;
    private int currentPageIndex;
    private MenuTabPage menuTabPage;
    private PersonTabPage personTabPage;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        bundle = intent.getExtras();

        initView(bundle);
    }

    private void initView(Bundle bundle) {
        viewPager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        activity_main_textview_menu = (TextView) findViewById(R.id.activity_main_textview_menu);
        activity_main_textview_person = (TextView) findViewById(R.id.activity_main_textview_person);
        activity_main_image_add_programs = (ImageView)findViewById(R.id.activity_main_image_add_programs);
        activity_main_image_person = (ImageView)findViewById(R.id.activity_main_image_person);
        activity_main_image_menu = (ImageView)findViewById(R.id.activity_main_image_menu);
        menuLayout = (RelativeLayout) findViewById(R.id.menu_main_activity);
        personLayout = (LinearLayout) findViewById(R.id.activity_person);

        activity_main_textview_menu.setOnClickListener(new myOnClickListener());
        activity_main_textview_person.setOnClickListener(new myOnClickListener());
        activity_main_image_add_programs.setOnClickListener(new myOnClickListener());




        data = new ArrayList<Fragment>();

        // ViewPager的三个页面
        menuTabPage = new MenuTabPage();
        personTabPage = new PersonTabPage();
        menuTabPage.setId(bundle);
        personTabPage.setId(bundle);
        data.add(menuTabPage);
        data.add(personTabPage);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Fragment getItem(int position) {
                return data.get(position);
            }
        };

        viewPager.setAdapter(adapter);

        // ViewPager滑动结束后，改变Tabbar中的字体颜色
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position){
                resetTextView();
                switch (position) {
                    case 0:
                        activity_main_textview_menu.setTextColor(Color.parseColor("#39D1DF"));
                        Picasso.with(MainActivity.this).load(R.mipmap.main_ic_menu_button_on).into(activity_main_image_menu);
                        Picasso.with(MainActivity.this).load(R.mipmap.main_ic_person_button_off).into(activity_main_image_menu);
                        break;
                    case 1:
                        activity_main_textview_person.setTextColor(Color.parseColor("#39D1DF"));
                        Picasso.with(MainActivity.this).load(R.mipmap.main_ic_menu_button_off).into(activity_main_image_menu);
                        Picasso.with(MainActivity.this).load(R.mipmap.main_ic_person_button_on).into(activity_main_image_menu);
                        break;
                }
                currentPageIndex = position;
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){}


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // 重置三个TextView的字体颜色
    protected void resetTextView() {
        activity_main_textview_menu.setTextColor(Color.BLACK);
        activity_main_textview_person.setTextColor(Color.BLACK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        personTabPage.onActivityResult(requestCode,resultCode,data);
    }

    class myOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.activity_main_textview_menu:
                    viewPager.setCurrentItem(0,true);
                    break;
                case R.id.activity_main_textview_person:
                    viewPager.setCurrentItem(1,true);
                    break;
                case R.id.activity_main_image_add_programs:
                    Intent intent2 = new Intent(MainActivity.this,LaunchActivity.class);
                    // 跳转到新增项目界面时也需要传递用户id 曾钧麟
                    intent2.putExtras(bundle);
                    startActivity(intent2);
                    break;
            }
        }
    }
}

