package com.serry.xprotect.receiver;

//www.javaapk.com 提供测试
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.serry.xprotect.service.FlowService;

public class FlowReceiver extends android.content.BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// 开机启动监控服务
		SharedPreferences preferences = context.getSharedPreferences("shadow_traffic", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("first", true);
		editor.commit();
		Intent intent2 = new Intent(context, FlowService.class);
		context.startService(intent2);
		Log.v("helsha", "1");

	}

}