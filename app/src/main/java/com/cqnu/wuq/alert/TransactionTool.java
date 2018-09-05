package com.cqnu.wuq.alert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class TransactionTool {

    private Context mContext;
    private AlarmManager mAlarmManager;

    public TransactionTool(Context context) {
        this.mContext = context;
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);//获取AlarmManager实例,闹钟管理器
    }

    public void setAlarm(int id, String name, String phone, String transaction, long time) {
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        intent.putExtra("transaction", transaction);
        intent.setClass(mContext, AlertReceiver.class);
        /**
         * flags有四个取值：
         int FLAG_CANCEL_CURRENT：如果该PendingIntent已经存在，则在生成新的之前取消当前的。
         int FLAG_NO_CREATE：如果该PendingIntent不存在，直接返回null而不是创建一个PendingIntent.
         int FLAG_ONE_SHOT:该PendingIntent只能用一次，在send()方法执行后，自动取消。
         int FLAG_UPDATE_CURRENT：如果该PendingIntent已经存在，则用新传入的Intent更新当前的数据。
         其中PendingIntent.FLAG_UPDATE_CURRENT,这样在启动的Activity里就可以用接收Intent传送数据的方法正常接收。
         */
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);//建立Intent和PendingIntent来调用管理器,未来意图
               mAlarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);//设置alarm
    }
}
