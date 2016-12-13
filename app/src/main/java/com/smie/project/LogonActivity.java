package com.smie.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        final Button Logon = (Button) findViewById(R.id.LogonButton);
        final Button Login = (Button) findViewById(R.id.LogonLoginButton);
        final TextInputLayout UesrnameLayout = (TextInputLayout) findViewById(R.id.UsernameLayout);
        final EditText LogonUsername = UesrnameLayout.getEditText();
        final TextInputLayout PasswordLayout = (TextInputLayout) findViewById(R.id.PasswordLayout);
        final EditText LogonPassword = PasswordLayout.getEditText();

        Logon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(LogonActivity.this,"登录被点击",Toast.LENGTH_SHORT).show();
                if(TextUtils.isEmpty(LogonUsername.getText().toString()))
                {
                    UesrnameLayout.setErrorEnabled(true);
                    UesrnameLayout.setError("登录名不能为空");
                    PasswordLayout.setErrorEnabled(false);

                }
                else if(LogonUsername.getText().toString().length() > 10)
                {
                    UesrnameLayout.setErrorEnabled(true);
                    UesrnameLayout.setError("登录名不合法");
                    PasswordLayout.setErrorEnabled(false);
                }
                else
                {
                    UesrnameLayout.setErrorEnabled(false);
                    if (TextUtils.isEmpty(LogonPassword.getText().toString()))
                    {
                        PasswordLayout.setErrorEnabled(true);
                        PasswordLayout.setError("密码不能为空");
                    }
                    else if(LogonPassword.getText().toString().length() > 10||LogonPassword.getText().toString().length() < 6)
                    {
                        PasswordLayout.setErrorEnabled(true);
                        PasswordLayout.setError("密码应为6~10位");
                    }
                    else
                    {
                        Intent intent = new Intent(LogonActivity.this,LaunchActivity.class);//方便试验
                        startActivity(intent);
                        PasswordLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(LogonActivity.this,"注册被点击",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogonActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
