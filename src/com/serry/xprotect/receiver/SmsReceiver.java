package com.serry.xprotect.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.serry.xprotect.R;

public class SmsReceiver extends BroadcastReceiver {

	public static final String TAG = "SmsReceiver";

	public static final int STOP_SMS = 1;
	public static final int STOP_CALL = 2;
	public static final int STOP_SMSCALL = 4;

	private SharedPreferences sp;

	public LocationClient mLocationClient;
	public LocationListener mLocationListener;
	private String lastLocation = "";
	private String safeNumber = "";
	private DevicePolicyManager deviceManager;
	private ComponentName componentName;

	// private BlackNumberDao dao;

	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "收到短信");
		// dao = new BlackNumberDao(context);
		deviceManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName componentName = new ComponentName(context, MyAdmin.class);
		
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		safeNumber = sp.getString("safenumber", "");

		if (intent.getAction().equals("android.serry.xprotect.test_location")) {
			Log.i("serry", "test_location");
			requestLocation(context);
			return;
		} else if (intent.getAction().equals("android.serry.xprotect.test_alarm")) {
			Log.i("serry", "test_alarm");
			MediaPlayer player = MediaPlayer.create(context, R.raw.alarm);
			player.setVolume(1.0f, 1.0f);
			player.start();
			return;
		} else if (intent.getAction().equals("android.serry.xprotect.test_lockscreen")) {
			Log.i("serry", "test_lockscreen");
			if (deviceManager.isAdminActive(componentName)) {
				deviceManager.resetPassword("123", 0);
				deviceManager.lockNow();
			}
			return;
		} else if (intent.getAction().equals("android.serry.xprotect.test_wipedata")) {
			Log.i("serry", "test_wipedata");
			if (deviceManager.isAdminActive(componentName)) {
				deviceManager.wipeData(0);
			}
			return;
		}

		Object[] objs = (Object[]) intent.getExtras().get("pdus");

		for (Object obj : objs) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);

			String sender = smsMessage.getOriginatingAddress();

			// 检查是否是黑名单号码
			// int mode = dao.findNumberMode(sender);
			// if ((mode & STOP_SMS) != 0) {
			// Log.i(TAG, "拦截黑名单短信");
			// abortBroadcast();
			// }
			// //////////////////////////////

			// 获取短信内容
			String body = smsMessage.getMessageBody();

			if (body.equals("#*location*#")) {
				Log.i(TAG, "发送当前位置");
				requestLocation(context);
				abortBroadcast();
			} else if (body.equals("#*alarm*#")) {
				Log.i(TAG, "播放报警音乐");

				MediaPlayer player = MediaPlayer.create(context, R.raw.alarm);
				player.setVolume(1.0f, 1.0f);
				player.start();

				abortBroadcast();
			} else if (body.equals("#*wipedata*#")) {
				Log.i(TAG, "清除数据");

				if (deviceManager.isAdminActive(componentName)) {
					deviceManager.wipeData(0);
				}
				abortBroadcast();
			} else if (body.equals("#*lockscreen*#")) {
				Log.i(TAG, "远程锁屏");

				if (deviceManager.isAdminActive(componentName)) {
					deviceManager.resetPassword("123", 0);
					deviceManager.lockNow();
				}
				abortBroadcast();
			}
		}// endfor
	}

	private void requestLocation(Context context) {
		mLocationClient = new LocationClient(context.getApplicationContext());
		if (mLocationClient == null) {
			Log.e("serry", "onEnabled mLocationClient == null");
		} else {
			Log.e("serry", "onEnabled mLocationClient != null");
		}
		mLocationListener = new LocationListener();
		mLocationClient.registerLocationListener(mLocationListener);
		initLocation();
		mLocationClient.start();
		mLocationClient.requestLocation();
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("gcj02");// 可选，默认gcj02，设置返回的定位结果坐标系，
		int span = 1000;
		option.setScanSpan(0);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		mLocationClient.setLocOption(option);
	}

	public class LocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// 单位：公里每小时
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// 单位：米
				sb.append("\ndirection : ");
				sb.append(location.getDirection());
				sb.append("\naddr : ");
				lastLocation = location.getAddrStr();
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps定位成功");

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
				sb.append("\naddr : ");
				lastLocation = location.getAddrStr();
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("网络定位成功");
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
				sb.append("\ndescribe : ");
				sb.append("离线定位成功，离线定位结果也是有效的");
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("网络不同导致定位失败，请检查网络是否通畅");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
			}

			Log.i("serry", sb.toString());
			if (!lastLocation.isEmpty() && !safeNumber.isEmpty()) {
				Log.e("serry", "safeNumber=" + safeNumber);
				mLocationClient.stop();
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(safeNumber, null, lastLocation, null, null);
			}
			// mLocationClient.setEnableGpsRealTimeTransfer(true);
		}

	}
}
