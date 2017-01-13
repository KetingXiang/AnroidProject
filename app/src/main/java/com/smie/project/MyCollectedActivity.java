package com.smie.project;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCollectedActivity extends AppCompatActivity {
    private ImageView   myParticipatedHeadImage;       // 头像
    private TextView    myParticipatedPlace;           // 地点
    private TextView    myParticipatedTime;            // 时间
    private TextView    myParticipatedAdd;             // 追加评论
    private TextView    myParticipatedDelete;          // 长按删除
    private TextView    myParticipatedIsFinished;      // 是否完成
    private RatingBar   myParticipatedStar;            // 星级
    private ListView    myParticipatedList;
    private List<MenuPersonItem> list = new ArrayList<MenuPersonItem>();
    private List<Map<String, Object>> myParticipatedData;
    private SimpleAdapter myParticipatedSimpleAdapter;
    private String personId;
    private ListView listView;
    private static final int SHOW_RESPONSE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_participated);
        Bundle bundle = this.getIntent().getExtras();
        personId = bundle.getString("id");
        sendRequestWithHttpURLConnection();
        bind();
//        listen();
    }

    public void bind() {
        myParticipatedHeadImage    = (ImageView) findViewById(R.id.myParticipatedHeadImage);
        myParticipatedPlace        = (TextView)  findViewById(R.id.myParticipatedPlace);
        myParticipatedTime         = (TextView)  findViewById(R.id.myParticipatedTime);
        myParticipatedAdd          = (TextView)  findViewById(R.id.myParticipatedAdd);
        myParticipatedDelete       = (TextView)  findViewById(R.id.myParticipatedDelete);
        myParticipatedIsFinished   = (TextView)  findViewById(R.id.myParticipatedIsFinished);
        myParticipatedStar         = (RatingBar) findViewById(R.id.myParticipatedStar);
//        myParticipatedList         = (ListView)  findViewById(R.id.myParticipatedList);
        listView                    =(ListView) findViewById(R.id.myParticipatedList);
    }

    public void listen() {
        myParticipatedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ;
            }
        });

        myParticipatedList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });
    }
    private void initPersonItem(List<Map<String,String>> myItem){
        Log.i("tag","init");
        list.clear();
        for (int i = 0;i < myItem.size();i++){
            int menu_personItemId = R.mipmap.ic_launcher;
            int menu_location_icon = R.mipmap.menu_main_item_location2;
            String menu_programId = myItem.get(i).get("programId");
            String menu_personName = myItem.get(i).get("name");
            String menu_personAddress = myItem.get(i).get("address");
            String menu_evaluate = "评价";
            float menu_evaluta_bar = Float.parseFloat(myItem.get(i).get("evaluate"));
            String menu_personDescription = myItem.get(i).get("description");
            String menu_go_to_connect = "￥："+myItem.get(i).get("price");

            MenuPersonItem person1 = new MenuPersonItem(
                    menu_programId,
                    menu_personItemId,
                    menu_location_icon,
                    menu_personName,
                    menu_personAddress,
                    menu_evaluate,
                    menu_evaluta_bar,
                    menu_personDescription,
                    menu_go_to_connect);
            list.add(person1);
        }

        MyParticipatedActivityAdapter adapter = new MyParticipatedActivityAdapter(MyCollectedActivity.this,list);
        listView.setAdapter(adapter);
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_RESPONSE:
                    ArrayList<String> response = (ArrayList<String>) msg.obj;
                    for(String item:response){
                        Log.i("response1",""+item);
                    }
                    List<Map<String,String> > myItem = get_person_item(response);
                    initPersonItem(myItem);
                    break;
            }
        }
    };
    private List<Map<String,String> > get_person_item(ArrayList<String> response){

        List<Map<String,String> > myItems = new ArrayList<Map<String,String> >();

        int program_num = Integer.parseInt(response.get(0));
        for(int i = 0;i < program_num;i++){
            int bisa = i*9;
            Map<String,String> item = new HashMap<String,String>();
            item.put("programId",response.get(1+bisa));
            item.put("name",response.get(2+bisa));
            item.put("address",response.get(3+bisa));
            item.put("evaluate",response.get(4+bisa));
            item.put("price",response.get(5+bisa));
            item.put("description",response.get(6+bisa));

            myItems.add(item);
        }

        return myItems;
    }

    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                findallparticipated();
            }
        }).start();
    }

    private void findallparticipated(){
        String url = "";
        Log.i("tag",url+"        " +personId+"fffffffffffffffff");
        url = getString(R.string.host_ip)+"findallparticipated/"+personId;
        Log.i("tag",url+"        " +personId);
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection)((new URL(url.toString())).openConnection());
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            connection.setRequestProperty("Charset","UTF-8");
//            connection.setDoOutput(true);

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder response = new StringBuilder();
            while((line = reader.readLine()) != null){
                response.append(line);
            }

            Message message = new Message();
            message.what = SHOW_RESPONSE;
            Log.i("tag",""+(response.toString()));
            message.obj = parseXMLWithPull(response.toString());
            handler.sendMessage(message);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    /**解析xml
     *
     * @param xmlData :完整的xml字符串文本
     * @return 储存需要信息的数组
     */
    private ArrayList<String> parseXMLWithPull(String xmlData){
        Log.i("tag2",xmlData);
        ArrayList<String> info = new ArrayList<String>();
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));

            int eventType = xmlPullParser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if ("string".equals(nodeName)){
                            info.add(xmlPullParser.nextText());
                            Log.i("infofff",""+info.isEmpty());
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        break;
                    default:
                        break;

                }
                eventType = xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        for(String item:info){
            Log.i("info222",""+item);
        }
        return info;
    }
}
