package com.cqnu.wuq.alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cqnu.wuq.Activity.AddressBookActivity;

public class AlertReceiver extends BroadcastReceiver {//广播监听器
	@Override
	public void onReceive(Context context, Intent intent) {
		intent.setClass(context, AddressBookActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //通过Component Name来打开的方式,一种默认打开方式，显示启动
		context.startActivity(intent);
	}
}
