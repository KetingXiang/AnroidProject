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

public class MyCollectedActivity extends AppCompatActivity {
    private ImageView   myCollectedHeadImage;       // 头像
    private TextView    myCollectedPlace;           // 地点
    private TextView    myCollectedTime;            // 时间
    private TextView    myCollectedAdd;             // 追加评论
    private TextView    myCollectedDelete;          // 长按删除
    private TextView    myCollectedIsFinished;      // 是否完成
    private RatingBar   myCollectedStar;            // 星级
    private ListView    myCollectedList;

    private List<Map<String, Object>> myCollectedData;
    private SimpleAdapter             myCollectedSimpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collected);
        bind();
        listen();
    }

    public void bind() {
        myCollectedHeadImage    = (ImageView) findViewById(R.id.myCollectedHeadImage);
        myCollectedPlace        = (TextView)  findViewById(R.id.myCollectedPlace);
        myCollectedTime         = (TextView)  findViewById(R.id.myCollectedTime);
        myCollectedAdd          = (TextView)  findViewById(R.id.myCollectedAdd);
        myCollectedDelete       = (TextView)  findViewById(R.id.myCollectedDelete);
        myCollectedIsFinished   = (TextView)  findViewById(R.id.myCollectedIsFinished);
        myCollectedStar         = (RatingBar) findViewById(R.id.myCollectedStar);
        myCollectedList         = (ListView)  findViewById(R.id.myCollectedList);
    }

    public void listen() {
        myCollectedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        myCollectedList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });
    }

}
