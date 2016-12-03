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

public class MyParticipatedActivity extends AppCompatActivity {
    private ImageView   myParticipatedHeadImage;       // 头像
    private TextView    myParticipatedPlace;           // 地点
    private TextView    myParticipatedTime;            // 时间
    private TextView    myParticipatedAdd;             // 追加评论
    private TextView    myParticipatedDelete;          // 长按删除
    private TextView    myParticipatedIsFinished;      // 是否完成
    private RatingBar   myParticipatedStar;            // 星级
    private ListView    myParticipatedList;

    private List<Map<String, Object>> myParticipatedData;
    private SimpleAdapter myParticipatedSimpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_participated);
        bind();
        listen();
    }

    public void bind() {
        myParticipatedHeadImage    = (ImageView) findViewById(R.id.myParticipatedHeadImage);
        myParticipatedPlace        = (TextView)  findViewById(R.id.myParticipatedPlace);
        myParticipatedTime         = (TextView)  findViewById(R.id.myParticipatedTime);
        myParticipatedAdd          = (TextView)  findViewById(R.id.myParticipatedAdd);
        myParticipatedDelete       = (TextView)  findViewById(R.id.myParticipatedDelete);
        myParticipatedIsFinished   = (TextView)  findViewById(R.id.myParticipatedIsFinished);
        myParticipatedStar         = (RatingBar) findViewById(R.id.myParticipatedStar);
        myParticipatedList         = (ListView)  findViewById(R.id.myParticipatedList);
    }

    public void listen() {
        myParticipatedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        myParticipatedList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });
    }
}
