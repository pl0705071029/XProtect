package com.serry.xprotect.transaction;

import android.content.pm.PackageManager;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Xprotect implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		// TODO Auto-generated method stub
		Log.e("sai", "Xprotect handleLoadPackage :" + lpparam.packageName);
		if (lpparam.packageName.equals("com.serry.xprotect") || lpparam.packageName.equals("android")) {
			XposedHelpers.findAndHookMethod("com.android.server.am.ActivityManagerService", lpparam.classLoader,
					"checkCallingPermission", String.class, new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {
							Log.e("sai", "Xprotect afterHookedMethod :" + lpparam.packageName);
							Object ret = param.getResult();
							if (ret != null) {
								ret = PackageManager.PERMISSION_GRANTED;
								Log.e("sai", "Xprotect afterHookedMethod ret = PERMISSION_GRANTED");
								param.setResult(ret);
							}
						}
					});
		}

	}

}
