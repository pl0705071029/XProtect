package com.serry.xprotect.transaction;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class AppInfo {
	//包名
	private String app_package;
	public String getApp_package() {
		return app_package;
	}
	public void setApp_package(String app_package) {
		this.app_package = app_package;
	}

	//图标
	private  Drawable app_icon;
	//名字
		private String app_name="正在运行的程序";
//		//软件大小
//		private String app_capacity;
//		//软件版本
//		private String app_version;
//		//软件占用的内存
//		private String app_memory;
//		//软件cpu占用
//		private String app_cpu;
		//软件gprs上传总量
		private String app_sent="0K";
		//软件gprs下载总量
		private String app_rev="0K";
		//软件流量总量
		private String app_traffic="0K";
		//标示
		private boolean ta=true;
//		//wifi
//		private String wifi_sent;
//		private String wifi_rev;
//		private String wifi_traffic;
		
//	public String getWifi_sent() {
//			return wifi_sent;
//		}
//		public void setWifi_sent(long wifi_sent) {
//			this.wifi_sent = refreshTraffic(wifi_sent);
//		}
//		public String getWifi_rev() {
//			return wifi_rev;
//		}
//		public void setWifi_rev(long wifi_rev) {
//			this.wifi_rev = refreshTraffic(wifi_rev);
//		}
//		public String getWifi_traffic() {
//			return wifi_traffic;
//		}
//		public void setWifi_traffic(long wifi_traffic) {
//			this.wifi_traffic = refreshTraffic(wifi_traffic);
//		}
	public boolean isTa() {
			return ta;
		}
		public void setTa(boolean ta) {
			this.ta = ta;
		}
	public Drawable getApp_icon() {
		return app_icon;
	}
	public void setApp_icon(Drawable app_icon) {
		this.app_icon = app_icon;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
//	public String getApp_capacity() {
//		return app_capacity;
//	}
//	public void setApp_capacity(String app_capacity) {
//		this.app_capacity = app_capacity;
//	}
//	public String getApp_version() {
//		return app_version;
//	}
//	public void setApp_version(String app_version) {
//		this.app_version = app_version;
//	}
//	public String getApp_memory() {
//		return app_memory;
//	}
//	public void setApp_memory(String app_memory) {
//		this.app_memory = app_memory;
//	}
//	public String getApp_cpu() {
//		return app_cpu;
//	}
//	public void setApp_cpu(String app_cpu) {
//		this.app_cpu = app_cpu;
//	}
	public String getApp_sent() {
		return app_sent;
	}
	public void setApp_sent(long app_sent) {
		this.app_sent = refreshTraffic(app_sent);
	}
	public String getApp_rev() {
		return app_rev;
	}
	public void setApp_rev(long app_rev) {
		this.app_rev =refreshTraffic(app_rev);
	}
	public String getApp_traffic() {
		return app_traffic;
	}
	public void setApp_traffic(long app_traffic) {
		this.app_traffic = refreshTraffic(app_traffic);
	}
	
	
	private String refreshTraffic(long lg){
		String str="0K";
		int a=0,b=1024,c=1048576;;
		
		Log.v("hells",lg +"");
		if(lg<1024){
			str="0K";
		}
		else if(lg>=b&&lg<c){
			int d=(int) (lg/b);
			str=d+"K";
		}
		else {
			int e=(int) (lg/c);
			int f=(int) ((lg-e*c)/1024);
		
			str=e+"M"+" "+f+"K";
		}
		return str;
		}

}