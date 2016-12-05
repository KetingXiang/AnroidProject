package com.smie.project;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailActivity extends AppCompatActivity {
    private Button DetailJoinProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findViews();

        /*
        listen DetailJoinProgram
        */
        myOnClickListenterForDetailJoinProgram myOnClickListenterForDetailJoinProgram = new myOnClickListenterForDetailJoinProgram();
        DetailJoinProgram.setOnClickListener(myOnClickListenterForDetailJoinProgram);
    }
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

            builder.setTitle("导游联系方式")
                    .setMessage("13609755624");
            builder.setPositiveButton("拨打电话",new DialogInterface.OnClickListener(){
//                点击拨打电话之后取消对话框，进入电话拨打界面，之后弹出另一个询问是否参加项目的对话框
//                字符串提取到string（先不处理）
                @Override
                public void onClick(DialogInterface dialogInterface, int which){
                        dialog.dismiss();
                        AlertDialog.Builder builder_join_program = new AlertDialog.Builder(DetailActivity.this);
                        builder_join_program.setTitle("温馨提示")
                                .setMessage("你愿意继续参与这个项目吗？");
                        builder_join_program.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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
