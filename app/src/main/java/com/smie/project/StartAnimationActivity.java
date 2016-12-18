package com.smie.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class StartAnimationActivity extends AppCompatActivity {
    private ImageView mImageView; //图片
    private ImageView mImageView2; //文字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_animation);

        mImageView = (ImageView) findViewById(R.id.logo);
        mImageView2 = (ImageView) findViewById(R.id.name);

        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(3000);

        RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);

        mImageView.startAnimation(animation);
        mImageView2.startAnimation(animation);
        mImageView2.startAnimation(rotateAnimation);

        animation.setAnimationListener(new AnimationImp());
    }

    private class AnimationImp implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }
        @Override
        public void onAnimationEnd(Animation animation) {
            skip();
        }
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
    private void skip() {
        startActivity(new Intent(this, LogonActivity.class));
        finish();
    }
}