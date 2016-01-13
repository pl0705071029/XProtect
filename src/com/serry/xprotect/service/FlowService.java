package com.serry.xprotect.service;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.serry.xprotect.transaction.AppInfo;
import com.serry.xprotect.transaction.PackagesInfo;
import com.serry.xprotect.transaction.Traffic;

public class FlowService extends Service {
	private PackagesInfo packageInfo;
	private SharedPreferences preferences;
	public List<AppInfo> infos;
	private final IBinder myBinder = new MyBinder();
	public Boolean mGet = false;
	public String[] tra = { "0K", "0K", "0K" };
	public double l;
	// 使用handler不停获取程序信息
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			infos = packageInfo.getRunningProcess();
			setTraffic();
			mGet = true;
			handler.postDelayed(runnable, 3000);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.v("shadow", "2");
		infos = packageInfo.getRunningProcess();
		handler.postDelayed(runnable, 3000);
		return myBinder;

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		packageInfo = new PackagesInfo(getApplicationContext());
		preferences = getSharedPreferences("small_setting", MODE_PRIVATE);// 获取总流量
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		infos = packageInfo.getRunningProcess();
		Log.v("serry", "2");
		return super.onStartCommand(intent, flags, START_STICKY);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		startService(new Intent(getApplicationContext(), FlowService.class));

		super.onDestroy();
	}

	// 自定义binder
	public class MyBinder extends Binder {

		public FlowService getService() {
			return FlowService.this;
		}

	}

	// 单位换算
	// 转换单位
	private String refreshTraffic(long lg, Boolean o) {
		String str = "0K";
		int a = 0, b = 1024, c = 1048576;
		;

		Log.v("serry", lg + "");
		if (lg < 1024) {
			str = "0K";
		} else if (lg >= b && lg < c) {
			int d = (int) (lg / b);
			str = d + "K";
		} else {
			double e = (double) lg / c;
			java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
			str = df.format(e) + "M";
			if (o == true) {
				l = e;
			}
		}
		return str;
	}

	private void setTraffic() {
		Traffic traffic = new Traffic(getApplicationContext());
		// 获取流量
		tra[1] = refreshTraffic(traffic.getAllWifi()[2], false);
		tra[0] = refreshTraffic(traffic.getAllGprs()[2], false);
		tra[2] = refreshTraffic(preferences.getLong("mouth_gprs_all", 0), true);

		// Log.v("fuck", traffic.getAllGprs()[2]+"ss"+traffic.getAllWifi()[2]);
		// Log.v("fuck", tra[0]+"ss"+tra[1]);
	}

}