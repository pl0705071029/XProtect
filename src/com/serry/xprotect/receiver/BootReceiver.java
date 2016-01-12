package com.serry.xprotect.receiver;

import com.serry.xprotect.R;
import com.serry.xprotect.R.drawable;
import com.serry.xprotect.R.string;
import com.serry.xprotect.service.UpdateService;
import com.serry.xprotect.utils.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	Handler mHandler = new Handler();

	@Override
	public void onReceive(final Context context, Intent bootIntent) {
		// Start boot update
		Intent changeIntent = new Intent();
		changeIntent.setClass(context, UpdateService.class);
		changeIntent.putExtra(UpdateService.cAction, UpdateService.cActionBoot);
		context.startService(changeIntent);

		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		boolean protecting = sp.getBoolean("protecting", false);
		Log.i("serry", "protecting = " + protecting);
		if (protecting) {
			final String safenumber = sp.getString("safenumber", "");
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String nowsim = tm.getSimSerialNumber();
			String savedsim = sp.getString("sim", "");
			Log.i("serry", "safenumber=" + safenumber + " nowsim=" + nowsim + " savedsim=" + savedsim);
			if (!nowsim.equals(savedsim)) {
				Log.i("serry", "sim卡变更，发送通知短信");

				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						SmsManager smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage(safenumber, null, "sim卡变更", null, null);
					}
				}, 60 * 1000);
			}
		}

		// Check if Xposed enabled
		if (Util.isXposedEnabled())
			context.sendBroadcast(new Intent("com.serry.xprotect.action.ACTIVE"));
		else {
			// Create Xposed installer intent
			// @formatter:off
			Intent xInstallerIntent = new Intent("de.robv.android.xposed.installer.OPEN_SECTION")
					.setPackage("de.robv.android.xposed.installer").putExtra("section", "modules")
					.putExtra("module", context.getPackageName()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// @formatter:on

			PendingIntent pi = (xInstallerIntent == null ? null : PendingIntent.getActivity(context, 0,
					xInstallerIntent, PendingIntent.FLAG_UPDATE_CURRENT));

			// Build notification
			NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
			notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
			notificationBuilder.setContentTitle(context.getString(R.string.app_name));
			notificationBuilder.setContentText(context.getString(R.string.app_notenabled));
			notificationBuilder.setWhen(System.currentTimeMillis());
			notificationBuilder.setAutoCancel(true);
			if (pi != null)
				notificationBuilder.setContentIntent(pi);
			Notification notification = notificationBuilder.build();

			// Display notification
			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.notify(Util.NOTIFY_NOTXPOSED, notification);
		}
	}
}
