package com.serry.xprotect.transaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.text.format.Time;
import android.util.Log;

public class Traffic {
	private Context context;
	private long[] gprs = { 0, 0, 0 };
	private long[] wifi = { 0, 0, 0 };
	private long[] one_gprs = { 0, 0, 0 };
	private long[] one_wifi = { 0, 0, 0 };
	private ConnectivityManager manager;

	public Traffic(Context context) {
		// TODO Auto-generated constructor stub

		this.context = context;
	}

	// 获取全部程序的gprs上传流量下载流量与总流量并写入配置文件
	public long[] getAllGprs() {
		SharedPreferences preferences = context.getSharedPreferences("total_traffic", Context.MODE_PRIVATE);
		manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo a_mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		SharedPreferences.Editor editor = preferences.edit();
		long a = preferences.getLong("be_send", 0);
		long b = preferences.getLong("be_rev", 0);
		long c = preferences.getLong("gprs_send", 0);
		long d = preferences.getLong("gprs_rev", 0);
		if (TrafficStats.getMobileTxBytes() > a || TrafficStats.getMobileTxBytes() == a) {
			editor.putLong("gprs_send", TrafficStats.getMobileTxBytes() - a + c);
			editor.putLong("gprs_rev", TrafficStats.getMobileRxBytes() - b + d);
		} else {
			editor.putLong("gprs_send", TrafficStats.getMobileTxBytes() + c);

			editor.putLong("gprs_rev", TrafficStats.getMobileRxBytes() + d);
		}
		a = TrafficStats.getMobileTxBytes();
		b = TrafficStats.getMobileRxBytes();
		editor.putLong("be_send", a);
		editor.putLong("be_rev", b);
		editor.commit();
		gprs[0] = preferences.getLong("gprs_send", 0);
		gprs[1] = preferences.getLong("gprs_rev", 0);
		gprs[2] = gprs[0] + gprs[1];

		// 比较日期
		SharedPreferences preferences2 = context.getSharedPreferences("small_setting", Context.MODE_PRIVATE);
		String now = getCurrentTime()[0] + "" + getCurrentTime()[1] + "" + getCurrentTime()[2];
		String yet = preferences2.getInt("year", 2012) + "" + preferences2.getInt("mouth", 2) + ""
				+ preferences2.getInt("day", 28);
		SharedPreferences.Editor editor2 = preferences2.edit();
		if (!now.equals(yet)) {
			if (getCurrentTime()[1] > preferences2.getInt("mouth", 2)
					&& getCurrentTime()[2] > preferences2.getInt("overday", 1)) {
				editor2.putLong("mouth_gprs_base", 0);
				editor2.putLong("mouth_gprs_all", 0);
			} else {
				editor2.putLong("mouth_gprs_base", gprs[2] + preferences2.getLong("mouth_gprs_base", 0));
			}
			editor2.putInt("year", getCurrentTime()[0]);
			editor2.putInt("mouth", getCurrentTime()[1]);
			editor2.putInt("day", getCurrentTime()[2]);
			editor2.commit();
			editor.putLong("gprs_send", 0);
			editor.putLong("gprs_rev", 0);
			editor.commit();
			gprs[2] = 0;
		} else {

			editor2.putLong("mouth_gprs_all", preferences2.getLong("mouth_gprs_base", 0) + gprs[2]);
			editor2.commit();

		}
		Log.v("fuck", getCurrentTime()[1] + "ss" + preferences2.getInt("mouth", 2) + "aa" + getCurrentTime()[2] + "PP"
				+ preferences2.getInt("overday", 1));
		return gprs;
	}

	// 获取全部程序的wifi上传量与下载量和总量
	public long[] getAllWifi() {
		SharedPreferences preferences = context.getSharedPreferences("total_traffic", Context.MODE_PRIVATE);
		manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo a_wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		SharedPreferences.Editor editor = preferences.edit();
		long e = preferences.getLong("bewi_send", 0);
		long f = preferences.getLong("bewi_rev", 0);
		long g = preferences.getLong("wifi_send", 0);
		long h = preferences.getLong("wifi_rev", 0);
		if (TrafficStats.getTotalTxBytes() - TrafficStats.getMobileTxBytes() > e
				|| TrafficStats.getTotalTxBytes() - TrafficStats.getMobileTxBytes() == e) {
			editor.putLong("wifi_send", TrafficStats.getTotalTxBytes() - TrafficStats.getMobileTxBytes() - e + g);
			editor.putLong("wifi_rev", TrafficStats.getTotalRxBytes() - TrafficStats.getMobileRxBytes() - f + h);
		} else {
			editor.putLong("wifi_send", TrafficStats.getTotalTxBytes() - TrafficStats.getMobileTxBytes() + g);

			editor.putLong("wifi_rev", TrafficStats.getTotalRxBytes() - TrafficStats.getMobileRxBytes() + h);
		}
		e = TrafficStats.getTotalTxBytes() - TrafficStats.getMobileTxBytes();
		f = TrafficStats.getTotalRxBytes() - TrafficStats.getMobileRxBytes();
		editor.putLong("bewi_send", e);
		editor.putLong("bewi_rev", f);
		editor.commit();
		wifi[0] = preferences.getLong("wifi_send", 0);
		wifi[1] = preferences.getLong("wifi_rev", 0);
		wifi[2] = wifi[0] + wifi[1];
		// 比较日期
		SharedPreferences preferences2 = context.getSharedPreferences("small_setting", Context.MODE_PRIVATE);
		String now = getCurrentTime()[0] + "" + getCurrentTime()[1] + "" + getCurrentTime()[2];
		String yet = preferences2.getInt("year", 2012) + "" + preferences2.getInt("mouth", 2) + ""
				+ preferences2.getInt("day", 28);
		if (!now.equals(yet)) {
			editor.putLong("wifi_send", 0);
			editor.putLong("wifi_rev", 0);
			editor.commit();
			wifi[2] = 0;
		}
		// Log.v("fuck", now+"qq"+yet);

		return wifi;
	}

	// 获取系统时间。返回数组
	private int[] getCurrentTime() {
		int[] is = { 0, 0, 0, 0, 0, 0 };
		Time time = new Time();
		time.setToNow();
		is[0] = time.year;
		is[1] = time.month + 1;
		is[2] = time.monthDay;
		is[3] = time.hour;
		is[4] = time.minute;
		is[5] = time.second;
		return is;

	}

}