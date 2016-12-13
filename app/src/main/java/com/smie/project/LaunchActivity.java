package com.smie.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by LeviLee on 16-12-12.
 */
public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        final Button Confirm = (Button) findViewById(R.id.LaunchConfirmButton);
        final EditText LaunchName = (EditText) findViewById(R.id.LaunchName);
        final EditText LaunchPrice = (EditText) findViewById(R.id.LaunchPrice);
        final EditText LaunchPlace = (EditText) findViewById(R.id.LaunchPlace);
        final EditText LaunchTime = (EditText) findViewById(R.id.LaunchTime);
        final EditText LaunchBrifing = (EditText) findViewById(R.id.LaunchBrifing);

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

                }
            }
        });
    }
}
