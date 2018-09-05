package com.cqnu.wuq.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cqnu.wuq.R;

import java.util.Timer;
import java.util.TimerTask;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Intent it = new Intent(this, MainActivity.class); //你要转向的Activity
        Timer timer = new Timer();//构造一个Timer定时器，执行定时任务
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
            }
        };
        timer.schedule(task, 1000 *5); //5秒后
    }

}
