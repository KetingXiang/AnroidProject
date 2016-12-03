package com.smie.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MyOrganizedActivity extends AppCompatActivity {
    private ImageView   myOrganizedHeadImage;       // 头像
    private TextView    myOrganizedPlace;           // 地点
    private TextView    myOrganizedTime;            // 时间
    private TextView    myOrganizedAdd;             // 追加评论
    private TextView    myOrganizedDelete;          // 长按删除
    private TextView    myOrganizedIsFinished;      // 是否完成
    private RatingBar   myOrganizedStar;            // 星级
    private ListView    myOrganizedList;

    private List<Map<String, Object>> myOrganizedData;
    private SimpleAdapter myOrganizedSimpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_organized);
        bind();
        listen();
    }

    public void bind() {
        myOrganizedHeadImage    = (ImageView) findViewById(R.id.myOrganizedHeadImage);
        myOrganizedPlace        = (TextView)  findViewById(R.id.myOrganizedPlace);
        myOrganizedTime         = (TextView)  findViewById(R.id.myOrganizedTime);
        myOrganizedAdd          = (TextView)  findViewById(R.id.myOrganizedAdd);
        myOrganizedDelete       = (TextView)  findViewById(R.id.myOrganizedDelete);
        myOrganizedIsFinished   = (TextView)  findViewById(R.id.myOrganizedIsFinished);
        myOrganizedStar         = (RatingBar) findViewById(R.id.myOrganizedStar);
        myOrganizedList         = (ListView)  findViewById(R.id.myOrganizedList);
    }

    public void listen() {
        myOrganizedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        myOrganizedList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });
    }
}
