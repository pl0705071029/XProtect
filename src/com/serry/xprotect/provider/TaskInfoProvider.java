package com.serry.xprotect.provider;

import java.util.ArrayList;
import java.util.List;

import com.serry.xprotect.R;
import com.serry.xprotect.R.drawable;
import com.serry.xprotect.data.TaskInfo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;

public class TaskInfoProvider {
	private Context context = null;
	private PackageManager pm = null;
	private ActivityManager am = null;

	public TaskInfoProvider(Context context) {

		this.context = context;
		pm = context.getPackageManager();
		am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	}

	public List<TaskInfo> getAllTasks(List<RunningAppProcessInfo> runingappinfos) {
		List<TaskInfo> taskInfos = new ArrayList<TaskInfo>();

		for (RunningAppProcessInfo info : runingappinfos) {
			TaskInfo taskInfo = null;
			try {
				taskInfo = new TaskInfo();
				int pid = info.pid;
				taskInfo.setPid(pid);
				String packname = info.processName;
				taskInfo.setPackname(packname);
				ApplicationInfo appinfo = pm.getPackageInfo(packname, 0).applicationInfo;
				Drawable appicon = appinfo.loadIcon(pm);
				taskInfo.setAppicon(appicon);

				if (filterApp(appinfo)) {
					taskInfo.setSystemapp(false);
				} else {
					taskInfo.setSystemapp(true);
				}

				String appname = appinfo.loadLabel(pm).toString();
				taskInfo.setAppname(appname);
				// 根据当前的pid，获取当前的内存占用信息
				MemoryInfo[] memoryinfos = am.getProcessMemoryInfo(new int[] { pid });
				int memorysize = memoryinfos[0].getTotalPrivateDirty();
				taskInfo.setMemorysize(memorysize);
				taskInfos.add(taskInfo);
				taskInfo = null;
			} catch (Exception e) {
				e.printStackTrace();
				taskInfo = new TaskInfo();
				String packname = info.processName;
				taskInfo.setPackname(packname);
				taskInfo.setAppname(packname);
				Drawable appicon = context.getResources().getDrawable(R.drawable.ic_launcher);
				taskInfo.setAppicon(appicon);
				int pid = info.pid;
				taskInfo.setPid(pid);
				taskInfo.setSystemapp(true);
				MemoryInfo[] memoryinfos = am.getProcessMemoryInfo(new int[] { pid });
				int memorysize = memoryinfos[0].getTotalPrivateDirty();
				taskInfo.setMemorysize(memorysize);
				taskInfos.add(taskInfo);
				taskInfo = null;
			}
		}
		return taskInfos;
	}

	/**
	 * 判断某个应用程序是 不是三方的应用程序
	 * 
	 * @param info
	 * @return
	 */
	public boolean filterApp(ApplicationInfo info) {
		if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
			return true;
		} else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			return true;
		}
		return false;
	}
}
