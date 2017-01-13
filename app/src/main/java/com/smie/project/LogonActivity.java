package com.smie.project;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
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

    private Button Logon;
    private Button Login;
    private TextInputLayout UesrnameLayout;
    private EditText LogonUsername;
    private TextInputLayout PasswordLayout;
    private EditText LogonPassword;
    private TextInputLayout CodeUtilsEditLayout;
    private EditText LogonCodeUtilsEdit;
    private ImageButton LogonCodeUtilsImage;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        Logon = (Button) findViewById(R.id.LogonButton);
        Login = (Button) findViewById(R.id.LogonLoginButton);
        UesrnameLayout = (TextInputLayout) findViewById(R.id.UsernameLayout);
        LogonUsername = UesrnameLayout.getEditText();
        PasswordLayout = (TextInputLayout) findViewById(R.id.PasswordLayout);
        LogonPassword = PasswordLayout.getEditText();
        CodeUtilsEditLayout = (TextInputLayout)findViewById(R.id.LogonCodeUtilsEditLayout);
        LogonCodeUtilsEdit = CodeUtilsEditLayout.getEditText();
        sharedPref = this.getPreferences(MODE_PRIVATE);
        LogonCodeUtilsImage = (ImageButton) findViewById(R.id.LogonCodeUtilsImage);

        if(sharedPref.getBoolean("STATE",false))//如果STATE是true就马上跳转到menu去 记得把用户id传过去
        {
            // 跳转到菜单界面
            // 曾钧麟
            //Intent intent = new Intent(LogonActivity.this,MenuActivity.class);
            Intent intent = new Intent(LogonActivity.this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id",LogonUsername.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        }

        editor = sharedPref.edit();
        editor.putBoolean("STATE",false);
        editor.apply();

        LogonCodeUtilsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //更换新的验证码0 一共有6个0~5
            }
        });

        Logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LogonActivity.this,"登录被点击",Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(LogonUsername.getText().toString())) {
                    UesrnameLayout.setErrorEnabled(true);
                    UesrnameLayout.setError("登录名不能为空");
                    PasswordLayout.setErrorEnabled(false);
                    LogonPassword.setText("");
                    LogonCodeUtilsEdit.setText("");
                    //更换新的验证码1

                } else if (LogonUsername.getText().toString().length() > 10) {
                    UesrnameLayout.setErrorEnabled(true);
                    UesrnameLayout.setError("登录名不合法");
                    LogonPassword.setText("");
                    PasswordLayout.setErrorEnabled(false);
                    LogonUsername.setText("");
                    LogonCodeUtilsEdit.setText("");
                    //更换新的验证码2
                } else {
                    UesrnameLayout.setErrorEnabled(false);
                    if (TextUtils.isEmpty(LogonPassword.getText().toString())) {
                        PasswordLayout.setErrorEnabled(true);
                        PasswordLayout.setError("密码不能为空");
                        LogonCodeUtilsEdit.setText("");
                    } else if (LogonPassword.getText().toString().length() > 10 || LogonPassword.getText().toString().length() < 6) {
                        PasswordLayout.setErrorEnabled(true);
                        PasswordLayout.setError("密码应为6~10位");
                        LogonPassword.setText("");
                        LogonCodeUtilsEdit.setText("");
                        //更换新的验证码3
                    } else
                    {
//                        Intent intent = new Intent(LogonActivity.this, LaunchActivity.class);//方便试验
//                        startActivity(intent);
                        PasswordLayout.setErrorEnabled(false);
                        if (true)//判断验证码是否一致)
                        {
                            sendRequestWithHttpURLConnection("http://172.18.56.118:8000/findusers/"+LogonUsername.getText().toString());
                        }
                        else
                        {
                            CodeUtilsEditLayout.setErrorEnabled(true);
                            CodeUtilsEditLayout.setError("验证码输入错误");
                            LogonPassword.setText("");
                            LogonCodeUtilsEdit.setText("");
                            //更换新的验证码4
                        }
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
                        //曾钧麟
                        //更改状态 不用再进入登录页面了
                        editor.putString("id",LogonUsername.getText().toString());
                        editor.putBoolean("STATE",true);
                        editor.commit();
                        //跳转到主界面
                        //Intent intent = new Intent(LogonActivity.this,MenuActivity.class);
                        Intent intent = new Intent(LogonActivity.this,MainActivity.class);
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
                        LogonPassword.setText("");
                        LogonCodeUtilsEdit.setText("");
                        //更换新的验证码5
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
