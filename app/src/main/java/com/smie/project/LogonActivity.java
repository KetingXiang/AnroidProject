package com.smie.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LogonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        final Button Logon = (Button) findViewById(R.id.LogonButton);
        final Button Login = (Button) findViewById(R.id.LogonLoginButton);

        Logon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(LogonActivity.this,"登录被点击",Toast.LENGTH_SHORT).show();
                /**test by zackzhao
                 *
                 */
                Intent intent = new Intent(LogonActivity.this,MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id","166654");
                intent.putExtras(bundle);
                startActivity(intent);
                /********************************************************************************/
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
