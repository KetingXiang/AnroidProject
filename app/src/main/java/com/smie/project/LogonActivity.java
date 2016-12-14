package com.smie.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
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

public class LogonActivity extends AppCompatActivity {

    private static final int SHOW_RESPONSE = 0;

    private final Button Logon = (Button) findViewById(R.id.LogonButton);
    private final Button Login = (Button) findViewById(R.id.LogonLoginButton);
    private final TextInputLayout UesrnameLayout = (TextInputLayout) findViewById(R.id.UsernameLayout);
    private final EditText LogonUsername = UesrnameLayout.getEditText();
    private final TextInputLayout PasswordLayout = (TextInputLayout) findViewById(R.id.PasswordLayout);
    private final EditText LogonPassword = PasswordLayout.getEditText();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        Logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LogonActivity.this,"登录被点击",Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(LogonUsername.getText().toString())) {
                    UesrnameLayout.setErrorEnabled(true);
                    UesrnameLayout.setError("登录名不能为空");
                    PasswordLayout.setErrorEnabled(false);

                } else if (LogonUsername.getText().toString().length() > 10) {
                    UesrnameLayout.setErrorEnabled(true);
                    UesrnameLayout.setError("登录名不合法");
                    PasswordLayout.setErrorEnabled(false);
                } else {
                    UesrnameLayout.setErrorEnabled(false);
                    if (TextUtils.isEmpty(LogonPassword.getText().toString())) {
                        PasswordLayout.setErrorEnabled(true);
                        PasswordLayout.setError("密码不能为空");
                    } else if (LogonPassword.getText().toString().length() > 10 || LogonPassword.getText().toString().length() < 6) {
                        PasswordLayout.setErrorEnabled(true);
                        PasswordLayout.setError("密码应为6~10位");
                    } else {
//                        Intent intent = new Intent(LogonActivity.this, LaunchActivity.class);//方便试验
//                        startActivity(intent);
                        PasswordLayout.setErrorEnabled(false);
                        sendRequestWithHttpURLConnection("http://172.18.57.116:8000/findusers/"+LogonUsername.getText().toString());
                    }
                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LogonActivity.this,"注册被点击",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogonActivity.this, LoginActivity.class);
                startActivity(intent);
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
                    if(response.get(0).equals(LogonPassword.getText().toString()))//de dao mima
                    {
                        //dengluchenggong liliwei
                        Toast.makeText(LogonActivity.this,"ID: "+LogonUsername.getText().toString()+"\nPW: "
                                +LogonPassword.getText().toString()+"\nRP: "+response.get(0),Toast.LENGTH_SHORT).show();
                        //strange zengjunlin
                        //跳转到主界面
                        Intent intent = new Intent(LogonActivity.this,MenuActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id",LogonUsername.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else
                    {
                        //denglushibai liliwei
                        PasswordLayout.setErrorEnabled(true);
                        PasswordLayout.setError("登录密码有误,请检查登录名及密码");
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
