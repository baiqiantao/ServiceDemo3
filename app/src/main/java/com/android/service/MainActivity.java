package com.android.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends ListActivity {
	private BatteryChangedReceiver receiver;
	static final String SERVICE_NAME = "com.android.service.SystemService";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] mData = {"开启服务",
				"停止服务",
				"判断服务是否正在运行",
				"动态注册电量变化的广播接收者",
				"取消注册"};
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Arrays.asList(mData)));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
			case 0:
				startService(new Intent(this, SystemService.class));
				break;
			case 1:
				stopService(new Intent(this, SystemService.class));
				break;
			case 2:
				Toast.makeText(this, "服务是否在运行:" + isServiceWorked(this, SERVICE_NAME), Toast.LENGTH_SHORT).show();
				break;
			case 3:
				if (receiver == null) receiver = new BatteryChangedReceiver();
				registerReceiver(receiver, getIntentFilter());//电池的状态改变广播只能通过动态方式注册
				break;
			case 4:
				if (receiver != null) {
					unregisterReceiver(receiver);
					receiver = null;
				} else Toast.makeText(this, "你还没有注册", Toast.LENGTH_SHORT).show();
				
				break;
		}
	}
	
	private IntentFilter getIntentFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);//This is a sticky broadcast containing the charging state, level, and other information about the battery.
		filter.addAction(Intent.ACTION_BATTERY_LOW);//Indicates low battery condition on the device. This broadcast corresponds to the "Low battery warning" system dialog.
		filter.addAction(Intent.ACTION_BATTERY_OKAY);//This will be sent after ACTION_BATTERY_LOW once the battery has gone back up to an okay state.
		return filter;
	}
	
	public static boolean isServiceWorked(Context context, String serviceName) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> services = (ArrayList<RunningServiceInfo>) manager.getRunningServices(Integer.MAX_VALUE);
		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).service.getClassName().equals(serviceName)) return true;
		}
		return false;
	}
}