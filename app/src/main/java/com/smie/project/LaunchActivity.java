package com.smie.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by LeviLee on 16-12-12.
 */
public class LaunchActivity extends AppCompatActivity {

    private static final int SHOW_RESPONSE = 0;
    private  Button Confirm ;
    private  EditText LaunchName ;
    private  EditText LaunchPrice ;
    private  EditText LaunchPlace ;
    private  EditText LaunchTime ;
    private  EditText LaunchBrifing ;
    private String personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        personId = bundle.getString("id");

        Confirm = (Button) findViewById(R.id.LaunchConfirmButton);
        LaunchName = (EditText) findViewById(R.id.LaunchName);
        LaunchPrice = (EditText) findViewById(R.id.LaunchPrice);
        LaunchPlace = (EditText) findViewById(R.id.LaunchPlace);
        LaunchTime = (EditText) findViewById(R.id.LaunchTime);
        LaunchBrifing = (EditText) findViewById(R.id.LaunchBrifing);

        Confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                if(TextUtils.isEmpty(LaunchName.getText().toString()))
                    Toast.makeText(LaunchActivity.this, "请输入发起项目的名称", Toast.LENGTH_LONG).show();
                else if(LaunchName.getText().toString().length()>10)
                    Toast.makeText(LaunchActivity.this, "项目名称最多10个字", Toast.LENGTH_LONG).show();
                else
                if(TextUtils.isEmpty(LaunchPlace.getText().toString()))
                    Toast.makeText(LaunchActivity.this, "请输入发起项目的地点", Toast.LENGTH_LONG).show();
                else if(LaunchPlace.getText().toString().length()>10)
                    Toast.makeText(LaunchActivity.this, "项目地点最多10个字", Toast.LENGTH_LONG).show();
                else
                if(TextUtils.isEmpty(LaunchPrice.getText().toString()))
                    Toast.makeText(LaunchActivity.this, "请输入发起项目的价格", Toast.LENGTH_LONG).show();
                else if(LaunchPrice.getText().toString().length()>6)
                    Toast.makeText(LaunchActivity.this, "价格需要为6位整数", Toast.LENGTH_LONG).show();
                else
                if(TextUtils.isEmpty(LaunchTime.getText().toString()))
                    Toast.makeText(LaunchActivity.this, "请输入发起项目的用时状况", Toast.LENGTH_LONG).show();
                else if(LaunchTime.getText().toString().length()>20)
                    Toast.makeText(LaunchActivity.this, "时间描述最多20个字", Toast.LENGTH_LONG).show();
                else
                if(TextUtils.isEmpty(LaunchBrifing.getText().toString()))
                    Toast.makeText(LaunchActivity.this, "请输入发起项目的简介", Toast.LENGTH_LONG).show();
                else if(LaunchBrifing.getText().toString().length()>200)
                    Toast.makeText(LaunchActivity.this, "简介最多200字", Toast.LENGTH_LONG).show();
                else
                {
//                                    Toast.makeText(LaunchActivity.this, (LaunchName.getText().toString()), Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(LaunchActivity.this, (LaunchPlace.getText().toString()), Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(LaunchActivity.this, (LaunchPrice.getText().toString()), Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(LaunchActivity.this, (LaunchTime.getText().toString()), Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(LaunchActivity.this, (LaunchBrifing.getText().toString()), Toast.LENGTH_SHORT).show();
                    sendRequestWithHttpURLConnection("http://172.18.57.116:8000/addprograms/"+LaunchName.getText().toString()
                            +"&"+LaunchPlace.getText().toString()+"&0.5&"+LaunchPrice.getText().toString()+"&"
                            +LaunchBrifing.getText().toString()+"&"+LaunchTime.getText().toString()+"&"+personId+"&15626275067");
                }
            }
        });
    }


    private void sendRequestWithHttpURLConnection(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                findprograms(url);
            }
        }).start();
    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_RESPONSE:
                    ArrayList<String> response = (ArrayList<String>) msg.obj;
                    for(String item:response){
                        Log.i("response1",""+item);
                    }
                    if(response.get(0).equals("success"))//chakan shifou chenggong
                    {
                        //fabuchenggong liliwei
                        Toast.makeText(LaunchActivity.this,"您已成功发起项目!"//;
                                +"\n您的项目名是: "+LaunchName.getText().toString()//测试用
                                +"\nfanhuixinxishi: "+response.get(0),Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //fabushibai
                        Toast.makeText(LaunchActivity.this, "该项目名已存在", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    private void findprograms(String url){
        Log.i("tag",url);
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection)((new URL(url.toString())).openConnection());
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            connection.setRequestProperty("Charset","UTF-8");
//            connection.setDoOutput(true);


            Log.i("tag","connect successfuly "+connection.getResponseCode());
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
