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
import android.widget.RadioButton;
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
 * Created by LeviLee on 16-11-25.
 */
public class LoginActivity extends AppCompatActivity{

    private static final int SHOW_RESPONSE = 0;
    private  Button LoginLoginLoginButton;
    private  Button Loginreturn;
    private  TextInputLayout NewPasswordLayout;
    private  EditText NewPassword;
    private  TextInputLayout ConfirmPasswordLayout;
    private  EditText ConfirmPassword;
    private  TextInputLayout NewUsernameLayout;
    private  EditText NewUsername;
    private  TextInputLayout PhoneLayout;
    private  EditText Phone;
    private  RadioButton MaleButton;
    private  RadioButton FemaleButton;
    private final String[] sex = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginLoginLoginButton = (Button) findViewById(R.id.LoginLoginButton);
        Loginreturn = (Button) findViewById(R.id.Loginreturn);
        NewPasswordLayout = (TextInputLayout) findViewById(R.id.NewPasswordLayout);
        NewPassword = NewPasswordLayout.getEditText();
        ConfirmPasswordLayout = (TextInputLayout) findViewById(R.id.ConfirmPasswordLayout);
        ConfirmPassword = ConfirmPasswordLayout.getEditText();
        NewUsernameLayout = (TextInputLayout) findViewById(R.id.NewUsernameLayout);
        NewUsername = NewUsernameLayout.getEditText();
        PhoneLayout = (TextInputLayout) findViewById(R.id.PhoneLayout);
        Phone = PhoneLayout.getEditText();
        MaleButton = (RadioButton) findViewById(R.id.MaleButton);
        FemaleButton = (RadioButton) findViewById(R.id.FelmaleButton);

        Loginreturn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this,LogonActivity.class);
                startActivity(intent);
            }
        });


        LoginLoginLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(TextUtils.isEmpty(NewUsername.getText().toString()))
                {
                    NewUsernameLayout.setErrorEnabled(true);
                    NewUsernameLayout.setError("登录名不能为空");
                    ConfirmPasswordLayout.setErrorEnabled(false);
                    NewPasswordLayout.setErrorEnabled(false);
                    PhoneLayout.setErrorEnabled(false);
                }
                else if(NewUsername.getText().toString().length() > 10)
                {
                    NewUsernameLayout.setErrorEnabled(true);
                    NewUsernameLayout.setError("登录名不能不超过10字节");
                    NewPasswordLayout.setErrorEnabled(false);
                    ConfirmPasswordLayout.setErrorEnabled(false);
                    PhoneLayout.setErrorEnabled(false);
                }
                else
                {
                    NewUsernameLayout.setErrorEnabled(false);
                    if (TextUtils.isEmpty(NewPassword.getText().toString()))
                    {
                        NewPasswordLayout.setErrorEnabled(true);
                        NewPasswordLayout.setError("密码不能为空");
                        ConfirmPasswordLayout.setErrorEnabled(false);
                        PhoneLayout.setErrorEnabled(false);
                    }
                    else if(NewPassword.getText().toString().length() > 10||NewPassword.getText().toString().length() < 6)
                    {
                        NewPasswordLayout.setErrorEnabled(true);
                        NewPasswordLayout.setError("密码应为6~10位");
                        ConfirmPasswordLayout.setErrorEnabled(false);
                        PhoneLayout.setErrorEnabled(false);
                    }
                    else
                    {
                        NewPasswordLayout.setErrorEnabled(false);
                        if (TextUtils.isEmpty(ConfirmPassword.getText().toString()))
                        {
                            ConfirmPasswordLayout.setErrorEnabled(true);
                            ConfirmPasswordLayout.setError("请确认密码");
                            PhoneLayout.setErrorEnabled(false);
                        }
                        else if(ConfirmPassword.getText().toString().length() > 10||ConfirmPassword.getText().toString().length() < 6)
                        {
                            ConfirmPasswordLayout.setErrorEnabled(true);
                            ConfirmPasswordLayout.setError("密码应为6~10位");
                            PhoneLayout.setErrorEnabled(false);
                        }
                        else if(!(ConfirmPassword.getText().toString().equals(NewPassword.getText().toString())))
                        {
                            ConfirmPasswordLayout.setErrorEnabled(true);
                            ConfirmPasswordLayout.setError("确认密码与新密码不一致");
                            Toast.makeText(LoginActivity.this,NewPassword.getText().toString(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this,ConfirmPassword.getText().toString(),Toast.LENGTH_SHORT).show();
                            PhoneLayout.setErrorEnabled(false);
                        }
                        else
                        {
                            ConfirmPasswordLayout.setErrorEnabled(false);
                            if (TextUtils.isEmpty(Phone.getText().toString()))
                            {
                                PhoneLayout.setErrorEnabled(true);
                                PhoneLayout.setError("手机号码不能为空");
                            }
                            else if(Phone.getText().toString().length() != 11)
                            {
                                PhoneLayout.setErrorEnabled(true);
                                PhoneLayout.setError("请输入11位手机号码");
                            }
                            else
                            {
                                PhoneLayout.setErrorEnabled(false);
                                if (MaleButton.isChecked())
                                {
                                    sex[0] = "Male";
                                }
                                else if (FemaleButton.isChecked())
                                {
                                    sex[0] = "Female";
                                }
                                //xinjianyonghu liliwei
                                sendRequestWithHttpURLConnection(getString(R.string.host_ip)+"addusers/"+NewUsername.getText().toString()
                                +"&"+NewPassword.getText().toString()+"&"+Phone.getText().toString()+"&"+sex[0]
                                +"&http://i1.piimg.com/567571/bcad4a3672028efa.jpg&http://i1.piimg.com/567571/4a263c9ec7a5e4be.jpg&no");
                            }
                        }
                    }
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
                        //zhucechenggong liliwei
                        Toast.makeText(LoginActivity.this,"您的用户名是: "+NewUsername.getText().toString()+"\n欢迎登录!"//;
                                +"\nfanhuixinxishi: "+response.get(0),Toast.LENGTH_SHORT).show();//测试用
                        Intent intent = new Intent(LoginActivity.this, LogonActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        //zhuceshibai liliwei
                        NewUsernameLayout.setErrorEnabled(true);
                        NewUsernameLayout.setError("此用户名已存在");
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
