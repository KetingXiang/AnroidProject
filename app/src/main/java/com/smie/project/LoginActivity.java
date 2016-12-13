package com.smie.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by LeviLee on 16-11-25.
 */
public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button LoginLoginLoginButton = (Button) findViewById(R.id.LoginLoginButton);
        final Button Loginreturn = (Button) findViewById(R.id.Loginreturn);
        final TextInputLayout NewPasswordLayout = (TextInputLayout) findViewById(R.id.NewPasswordLayout);
        final EditText NewPassword = NewPasswordLayout.getEditText();
        final TextInputLayout ConfirmPasswordLayout = (TextInputLayout) findViewById(R.id.ConfirmPasswordLayout);
        final EditText ConfirmPassword = ConfirmPasswordLayout.getEditText();
        final TextInputLayout NewUsernameLayout = (TextInputLayout) findViewById(R.id.NewUsernameLayout);
        final EditText NewUsername = NewUsernameLayout.getEditText();
        final TextInputLayout PhoneLayout = (TextInputLayout) findViewById(R.id.PhoneLayout);
        final EditText Phone = PhoneLayout.getEditText();
        final RadioButton MaleButton = (RadioButton) findViewById(R.id.MaleButton);
        final RadioButton FemaleButton = (RadioButton) findViewById(R.id.FelmaleButton);

        final String[] sex = new String[1];

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
                                Toast.makeText(LoginActivity.this,sex[0],Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }
}
