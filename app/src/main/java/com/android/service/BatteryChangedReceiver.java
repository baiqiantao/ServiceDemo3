package com.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 电量改变的广播接收者
 */
public class BatteryChangedReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Action：" + intent.getAction(), Toast.LENGTH_SHORT).show();
		switch (intent.getAction()) {
			case Intent.ACTION_BATTERY_CHANGED://"android.intent.action.BATTERY_CHANGED"
				Log.i("battery", "==============电池电量改变：BATTERY_CHANGED_ACTION");
				Log.i("battery", "当前电压=" + intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1));
				Log.i("battery", "健康状态=" + intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1));//如BATTERY_HEALTH_COLD
				Log.i("battery", "电量最大值=" + intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1));
				Log.i("battery", "当前电量=" + intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1));
				Log.i("battery", "充电电源类型=" + intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1));
				Log.i("battery", "充电状态=" + intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1));//如BATTERY_STATUS_CHARGING 正在充电
				Log.i("battery", "电池类型=" + intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY));//比如，对于锂电池是Li-ion
				Log.i("battery", "电池温度=" + intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1));
				break;
			case Intent.ACTION_BATTERY_LOW:// "android.intent.action.BATTERY_LOW"
				Log.i("battery", "电池电量低：ACTION_BATTERY_LOW");
				break;
			case Intent.ACTION_BATTERY_OKAY:// "android.intent.action.BATTERY_OKAY"
				Log.i("battery", "电池已经从电量低恢复为正常：ACTION_BATTERY_OKAY");
				break;
			default:
				break;
		}
	}
}