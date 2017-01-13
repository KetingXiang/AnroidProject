package com.smie.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

public class DetailActivity extends AppCompatActivity {
    private Button DetailJoinProgram;
    private String personId;
    private String programId;
    private TextView DetailName ;
    private RatingBar DetailRateBar;
    private TextView DetailIntroduction;
    private TextView DetailPrice;
    private TextView DetailLocation;
    private TextView DetailTime;
    private ImageView DetailCollected;
    private String DetailPhone;

    private static final int SHOW_RESPONSE_INFO = 0;
    private static final int SHOW_RESPONSE_COLLECTED_INFO = 1;
    private static final int SHOW_RESPONSE_COLLECTED_UPDATE = 2;
    private static final int SHOW_RESPONSE_COLLECTED_DELETE = 3;
    private static final int BEGIN = 0 ;
    private static final int UPDATE = 1 ;
    private static final int DELETE = 2 ;
    private boolean collected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findViews();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        personId = bundle.getString("id");
        programId = bundle.getString("programId");

        DetailName = (TextView)findViewById(R.id.DetailName);
        DetailRateBar = (RatingBar)findViewById(R.id.DetailRateBar);
        DetailIntroduction = (TextView)findViewById(R.id.DetailIntroduction);
        DetailPrice = (TextView)findViewById(R.id.DetailPrice);
        DetailLocation = (TextView)findViewById(R.id.DetailLocation);
        DetailTime = (TextView)findViewById(R.id.DetailTime);
        DetailCollected = (ImageView)findViewById(R.id.DetailCollected);

        sendRequestWithHttpURLConnection(BEGIN);

        DetailCollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!collected){
                    sendRequestWithHttpURLConnection(UPDATE);
                }
                else{
                    sendRequestWithHttpURLConnection(DELETE);
                }
            }
        });
        /*
        listen DetailJoinProgram
        */
        myOnClickListenterForDetailJoinProgram myOnClickListenterForDetailJoinProgram = new myOnClickListenterForDetailJoinProgram();
        DetailJoinProgram.setOnClickListener(myOnClickListenterForDetailJoinProgram);
    }
    /*
    2016 12 14
    曾钧麟
    链接网络获取详细信息
    */
    private void sendRequestWithHttpURLConnection(final int code){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (code == BEGIN){
                    findprograms();
                    findcollected();
                }
                else if (code == UPDATE){
                    updatecollected();
                }
                else if (code == DELETE){
                    deletecollected();
                }

            }
        }).start();
    }
    private void findprograms(){
        String url = "";
        url = getString(R.string.host_ip)+"findprograms/"+programId;
        Log.i("tag",url);
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection)((new URL(url.toString())).openConnection());
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            connection.setRequestProperty("Charset","UTF-8");

            Log.i("tag","connect successfuly "+connection.getResponseCode());
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder response = new StringBuilder();
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            Message message = new Message();
            message.what = SHOW_RESPONSE_INFO;
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
    private void updatecollected(){
        String url = "";
        url = getString(R.string.host_ip)+"addcollected/"+personId+'&'+programId+'/';
        Log.i("tag",url);
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection)((new URL(url.toString())).openConnection());
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            connection.setRequestProperty("Charset","UTF-8");

            Log.i("tag","connect successfuly "+connection.getResponseCode());
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder response = new StringBuilder();
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            Message message = new Message();
            message.what = SHOW_RESPONSE_COLLECTED_INFO;
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
    private void findcollected() {
        String url = "";
        url = getString(R.string.host_ip) + "findcollected/" + personId + "&" + programId + "/";
        Log.i("tag", url);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) ((new URL(url.toString())).openConnection());
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            connection.setRequestProperty("Charset", "UTF-8");

            Log.i("tag", "connect successfuly " + connection.getResponseCode());
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            Message message = new Message();
            message.what = SHOW_RESPONSE_COLLECTED_UPDATE;
            Log.i("tag", "" + (response.toString()));
            message.obj = parseXMLWithPull(response.toString());

            handler.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    private void deletecollected(){
        String url = "";
        url = getString(R.string.host_ip)+"deletecollected/"+personId+"&"+programId+"/";
        Log.i("tag",url);
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection)((new URL(url.toString())).openConnection());
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            connection.setRequestProperty("Charset","UTF-8");

            Log.i("tag","connect successfuly "+connection.getResponseCode());
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder response = new StringBuilder();
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            Message message = new Message();
            message.what = SHOW_RESPONSE_COLLECTED_DELETE;
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

    /*
    曾钧麟
    2016 12 14
    解析XML
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
    /*
    解析Handler接受解析到的结果
    12 14
    曾钧麟
    */
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            ArrayList<String> response;
            switch (msg.what){
                case SHOW_RESPONSE_INFO:
                    //对界面上的内容赋值
                    response = (ArrayList<String>)msg.obj;
                    DetailName.setText(response.get(0));
                    DetailIntroduction.setText(response.get(4));
                    DetailRateBar.setRating(Float.parseFloat(response.get(2)));
                    DetailPrice.setText(response.get(3));
                    DetailPhone = response.get(7);
                    break;
                case SHOW_RESPONSE_COLLECTED_INFO:
                    //对界面上的内容赋值
                    response = (ArrayList<String>)msg.obj;
                    if(response.get(0).equals("success")){
                        Picasso.with(DetailActivity.this).load(R.mipmap.detail_ic_favorite_on).into(DetailCollected);
                        collected = true;
                    }else{
                        Picasso.with(DetailActivity.this).load(R.mipmap.detail_ic_collected_off).into(DetailCollected);
                        collected = false;
                    }
                    break;
                case SHOW_RESPONSE_COLLECTED_UPDATE:
                    //对界面上的内容赋值
                    response = (ArrayList<String>)msg.obj;
                    if(response.get(0).equals("success")){
                        Picasso.with(DetailActivity.this).load(R.mipmap.detail_ic_favorite_on).into(DetailCollected);
                        collected = true;
                    }
                    break;
                case SHOW_RESPONSE_COLLECTED_DELETE:
                    //对界面上的内容赋值
                    response = (ArrayList<String>)msg.obj;
                    if(response.get(0).equals("success")){
                        Picasso.with(DetailActivity.this).load(R.mipmap.detail_ic_collected_off).into(DetailCollected);
                        collected = false;
                    }
                    break;
            }
        }
    };

    /**
    2016 / 12 / 1
    by zackzhao
    new a AlertDialog
     */
    private class myOnClickListenterForDetailJoinProgram implements View.OnClickListener{
        private AlertDialog dialog;
        @Override

        public void onClick(View view){

            final AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            DetailPhone = "13609755495";
            builder.setTitle("导游联系方式")
                    .setMessage(DetailPhone);
            builder.setPositiveButton("拨打电话",new DialogInterface.OnClickListener(){
//                点击拨打电话之后取消对话框，进入电话拨打界面，之后弹出另一个询问是否参加项目的对话框
//                字符串提取到string（先不处理）
                @Override
                public void onClick(DialogInterface dialogInterface, int which){
                        dialog.dismiss();
                        final AlertDialog.Builder builder_join_program = new AlertDialog.Builder(DetailActivity.this);
                        builder_join_program.setTitle("温馨提示")
                                .setMessage("你愿意继续参与这个项目吗？");
                        builder_join_program.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    Intent phoneintent = new Intent();
                                    phoneintent.setAction(Intent.ACTION_CALL);
                                    phoneintent.setData(Uri.parse("tel:"+DetailPhone));
                                    startActivity(phoneintent);
                            }
                        });
                        builder_join_program.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder_join_program.show();

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog = builder.show();
        }
    }
    private void findViews(){
        DetailJoinProgram = (Button) findViewById(R.id.DetailJoinProgram);
    }

        /*
    2016 / 12 / 1
    by zackzhao
    new a AlertDialog end
     */
}
