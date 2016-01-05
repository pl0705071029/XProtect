package com.serry.xprotect;

import com.serry.xprotect.data.TaskInfo;
import com.serry.xprotect.utils.Util;

import android.app.Application;
import android.util.Log;

public class ApplicationEx extends Application {
	private Thread.UncaughtExceptionHandler mPrevHandler;
	public TaskInfo taskinfo;

	@Override
	public void onCreate() {
		Util.log(null, Log.WARN, "UI started");
		mPrevHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				Util.bug(null, ex);
				if (mPrevHandler != null)
					mPrevHandler.uncaughtException(thread, ex);
			}
		});
	}

	public void onDestroy() {
		Util.log(null, Log.WARN, "UI stopped");
	}
}
