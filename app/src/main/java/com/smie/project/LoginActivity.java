package com.smie.project;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by LeviLee on 16-11-25.
 */
public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button Login = (Button) findViewById(R.id.LoginLoginButton);
        final TextInputLayout Newpassword = (TextInputLayout) findViewById(R.id.NewPasswordLayout);
        final String LoginNewPassword = Newpassword.getEditText().getText().toString();
        //final String LoginNewPassword = ((EditText)findViewById(R.id.LoginNewPassword)).getText().toString();
        //Newpassword.setErrorEnabled(true);
        //Newpassword.setError(emptyUsername);
        final TextInputLayout ConfirmPassword = (TextInputLayout) findViewById(R.id.ConfirmPasswordLayout);
        final String LoginConfirmPassword = ConfirmPassword.getEditText().getText().toString();
        //final String LoginConfirmPassword = ((EditText)findViewById(R.id.LoginConfirmPassword)).getText().toString();
        //ConfirmPassword.setErrorEnabled(true);
        //ConfirmPassword.setError(emptyUsername);
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            //这儿判断操作，如果输入错误可以给用户提示
//            if (s.length() < 5) {
//                Newpassword.setErrorEnabled(true);
//                Newpassword.setError("用户名不能小于6位");
//            } else {
//                Newpassword.setErrorEnabled(false);
//            }
//        }

        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                if (!Objects.equals(LoginNewPassword, LoginConfirmPassword))
                {
                    ConfirmPassword.setErrorEnabled(true);
                    ConfirmPassword.setError("此密码与新密码不同");
                }
                else
                {
                    ConfirmPassword.setErrorEnabled(false);
                }
                //Toast.makeText(LoginActivity.this, "注册被点击!", Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, LoginNewPassword, Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, LoginConfirmPassword, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
