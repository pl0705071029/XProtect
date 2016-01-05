package com.serry.xprotect.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.serry.xprotect.ApplicationEx;
import com.serry.xprotect.R;
import com.serry.xprotect.R.drawable;
import com.serry.xprotect.R.id;
import com.serry.xprotect.R.layout;
import com.serry.xprotect.data.TaskInfo;
import com.serry.xprotect.provider.TaskInfoProvider;
import com.serry.xprotect.utils.TextFormater;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 进程管理
 * 
 * @author Administrator
 * 
 */
public class AppTaskProgressActivity extends Activity {
	protected static final int APPPROGRESSFINISH = 24;
	private TextView tv_progress_size = null;
	private TextView tv_progress_count = null;
	private ActivityManager am = null;
	private LinearLayout ll_app_manager_loading = null;
	private List<RunningAppProcessInfo> runingappinfos;
	private TaskInfoProvider infos = null;
	private ListView lv_app_manager = null;
	private List<TaskInfo> taskInfos = null;
	private MyAppTaskProgressDapter adapter = null;
	private List<TaskInfo> usertaskinfos;
	private List<TaskInfo> systemtaskinfos;
	private SharedPreferences sp = null;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case APPPROGRESSFINISH:
				ll_app_manager_loading.setVisibility(View.INVISIBLE);
				adapter = new MyAppTaskProgressDapter();
				lv_app_manager.setAdapter(adapter);

				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_task_progress);
		tv_progress_count = (TextView) findViewById(R.id.tv_progress_count);
		tv_progress_size = (TextView) findViewById(R.id.tv_progress_size);
		ll_app_manager_loading = (LinearLayout) findViewById(R.id.ll_app_manager_loading);
		lv_app_manager = (ListView) findViewById(R.id.lv_app_manager);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		infos = new TaskInfoProvider(this);
		getProcessCount();
		setTitleData();
		fillData();
		lv_app_manager.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Object obj = lv_app_manager.getItemAtPosition(position);

				if (obj instanceof TaskInfo) {
					TaskInfo taskInfo = (TaskInfo) obj;
					String packname = taskInfo.getPackname();
					CheckBox cb = (CheckBox) view.findViewById(R.id.cb_task_checked);
					if ("cn.itcast.mobilesafe".equals(packname) || "system".equals(packname)
							|| "android.process.media".equals(packname)) {
						cb.setVisibility(View.INVISIBLE);
						return;
					}
					if (taskInfo.isIschecked()) {
						taskInfo.setIschecked(false);
						cb.setChecked(false);
					} else {
						taskInfo.setIschecked(true);
						cb.setChecked(true);
					}
				}
			}

		});
		lv_app_manager.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(AppTaskProgressActivity.this, AppDetailActivity.class);
				ApplicationEx app = (ApplicationEx) getApplication();
				Object obj = lv_app_manager.getItemAtPosition(position);
				if (obj instanceof TaskInfo) {
					TaskInfo taskInfo = (TaskInfo) obj;
					// taskInfo.getPackname();

					app.taskinfo = taskInfo;
					startActivity(intent);
				}
				return false;
			}

		});
	}

	/**
	 * 获得所有进程应用程序
	 */
	private void fillData() {
		ll_app_manager_loading.setVisibility(View.VISIBLE);
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();

				taskInfos = infos.getAllTasks(runingappinfos);
				Message msg = new Message();
				msg.what = APPPROGRESSFINISH;
				handler.sendMessage(msg);

			}

		}.start();

	}

	/**
	 * 设置title的数据
	 */
	private void setTitleData() {
		tv_progress_count.setText("进程数目: " + getProcessCount());
		tv_progress_size.setText("剩余内存" + TextFormater.getDataSize(getAvailMemoryInfo()));
	}

	/**
	 * 获取当前正在运行的进程数目
	 */
	public int getProcessCount() {
		runingappinfos = am.getRunningAppProcesses();
		return runingappinfos.size();
	}

	/**
	 * 获取当前系统的剩余的可用内存信息
	 */
	private long getAvailMemoryInfo() {
		MemoryInfo outInfo = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(outInfo);
		return outInfo.availMem;
	}

	/**
	 * 进程设置
	 */
	public void appSetting(View view) {
		Intent intent = new Intent(this, AppProgressManager.class);
		startActivityForResult(intent, 0);
	}

	/**
	 * 调用下个activity返回调用的方法
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 200) {
			fillData();
		}
	}

	/**
	 * 杀死所有的选中的进程
	 * 
	 * @author Administrator
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * 
	 */
	public void killTask(View view) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		int total = 0;
		int memorysize = 0;
		for (TaskInfo taskinfo : usertaskinfos) {
			if (taskinfo.isIschecked()) {
				memorysize += taskinfo.getMemorysize();
				// sai.pan begin
				// am.killBackgroundProcesses(taskinfo.getPackname());
				Method method = Class.forName("android.app.ActivityManager")
						.getMethod("forceStopPackage", String.class);
				method.invoke(am, taskinfo.getPackname());
				// sai.pan end
				taskInfos.remove(taskinfo);
				total++;
			}
		}
		for (TaskInfo taskinfo : systemtaskinfos) {
			if (taskinfo.isIschecked()) {
				memorysize += taskinfo.getMemorysize();
				// sai.pan begin
				// am.killBackgroundProcesses(taskinfo.getPackname());
				Method method = Class.forName("android.app.ActivityManager")
						.getMethod("forceStopPackage", String.class);
				method.invoke(am, taskinfo.getPackname());
				// sai.pan end
				taskInfos.remove(taskinfo);
				total++;
			}
		}

		// 通知用户杀死了多少个进程
		String size = TextFormater.getKBDataSize(memorysize);
		// Toast.makeText(this, "杀死了"+total+"个进程,释放了"+size+"空间", 0).show();
		MyToast.showToast(this, R.drawable.main_icon_36, "杀死了" + total + "个进程,释放了" + size + "空间");
		// 通知ui更新
		adapter = new MyAppTaskProgressDapter();
		lv_app_manager.setAdapter(adapter);
		// fillData();

	}

	class MyAppTaskProgressDapter extends BaseAdapter {

		MyAppTaskProgressDapter() {
			usertaskinfos = new ArrayList<TaskInfo>();

			systemtaskinfos = new ArrayList<TaskInfo>();
			for (TaskInfo taskinfo : taskInfos) {
				if (taskinfo.isSystemapp()) {
					systemtaskinfos.add(taskinfo);
				} else {
					usertaskinfos.add(taskinfo);
				}
			}

		}

		public int getCount() {
			// TODO Auto-generated method stub
			boolean showsystem = sp.getBoolean("showsystem", false);
			if (showsystem) {
				return taskInfos.size() + 2;
			} else {
				return usertaskinfos.size() + 1;
			}

		}

		public Object getItem(int position) {
			if (position == 0) {
				return 0;
			} else if (position <= usertaskinfos.size()) {
				return usertaskinfos.get(position - 1);
			} else if (position == usertaskinfos.size() + 1) {
				return position;
			} else if (position <= taskInfos.size() + 2) {
				return systemtaskinfos.get(position - usertaskinfos.size() - 2);
			} else {
				return position;
			}
		}

		public long getItemId(int position) {
			if (position == 0) {
				return -1;
			} else if (position <= usertaskinfos.size()) {
				return (position - 1);
			} else if (position == usertaskinfos.size() + 1) {
				return -1;
			} else if (position <= taskInfos.size() + 2) {
				return (position - usertaskinfos.size() - 2);
			} else {
				return -1;
			}

		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (position == 0) {
				TextView tv_userapp = new TextView(AppTaskProgressActivity.this);
				tv_userapp.setText("用户进程 " + usertaskinfos.size() + "个");
				return tv_userapp;
			} else if (position <= usertaskinfos.size()) {
				int currentpositon = (position - 1);
				TaskInfo taskinfo = usertaskinfos.get(currentpositon);
				View view = View.inflate(AppTaskProgressActivity.this, R.layout.task_manager_item, null);
				// ViewHolder holder = new ViewHolder();
				ImageView iv_app_icon = (ImageView) view.findViewById(R.id.iv_app_icon);
				TextView tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
				TextView tv_app_memory_size = (TextView) view.findViewById(R.id.tv_app_memory_size);
				CheckBox cb_task_checked = (CheckBox) view.findViewById(R.id.cb_task_checked);
				String packname = taskinfo.getPackname();
				if ("cn.itcast.mobilesafe".equals(packname) || "system".equals(packname)
						|| "android.process.media".equals(packname)) {
					cb_task_checked.setVisibility(View.INVISIBLE);

				} else {
					cb_task_checked.setVisibility(View.VISIBLE);
				}
				iv_app_icon.setImageDrawable(taskinfo.getAppicon());
				tv_app_name.setText(taskinfo.getAppname());
				tv_app_memory_size.setText("内存占用: " + TextFormater.getKBDataSize(taskinfo.getMemorysize()));
				cb_task_checked.setChecked(taskinfo.isIschecked());
				return view;

			} else if (position == usertaskinfos.size() + 1) {
				TextView tv_systemapp = new TextView(AppTaskProgressActivity.this);
				tv_systemapp.setText("系统进程 " + systemtaskinfos.size() + "个");
				return tv_systemapp;

			} else if (position <= taskInfos.size() + 2) {
				int systemposition = (position - usertaskinfos.size() - 2);
				TaskInfo taskinfo = systemtaskinfos.get(systemposition);
				View view = View.inflate(AppTaskProgressActivity.this, R.layout.task_manager_item, null);
				// ViewHolder holder = new ViewHolder();
				ImageView iv_app_icon = (ImageView) view.findViewById(R.id.iv_app_icon);
				TextView tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
				TextView tv_app_memory_size = (TextView) view.findViewById(R.id.tv_app_memory_size);
				CheckBox cb_task_checked = (CheckBox) view.findViewById(R.id.cb_task_checked);
				String packname = taskinfo.getPackname();
				if ("cn.itcast.mobilesafe".equals(packname) || "system".equals(packname)
						|| "android.process.media".equals(packname)) {
					cb_task_checked.setVisibility(View.INVISIBLE);

				} else {
					cb_task_checked.setVisibility(View.VISIBLE);
				}
				iv_app_icon.setImageDrawable(taskinfo.getAppicon());
				tv_app_name.setText(taskinfo.getAppname());
				tv_app_memory_size.setText("内存占用: " + TextFormater.getKBDataSize(taskinfo.getMemorysize()));
				cb_task_checked.setChecked(taskinfo.isIschecked());
				return view;

			} else {// 肯定不会执行
				return null;
			}
			/*
			 * View view = null; if(view==null){ view =
			 * View.inflate(AppTaskProgressActivity.this,
			 * R.layout.task_manager_item, null); }else{ view = convertView; }
			 * ImageView iv_app_icon = (ImageView)
			 * view.findViewById(R.id.iv_app_icon); TextView tv_app_memory_size
			 * = (TextView) view.findViewById(R.id.tv_app_memory_size); TextView
			 * tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
			 * 
			 * iv_app_icon.setImageDrawable(taskInfos.get(position).getAppicon())
			 * ;
			 * tv_app_memory_size.setText("内存大小 : "+TextFormater.getKBDataSize(
			 * taskInfos.get(position).getMemorysize()));
			 * tv_app_name.setText(taskInfos.get(position).getAppname());
			 */
			// return null;
		}

	}

	static class MyTaskInfo {
		ImageView iv_app_icon;
		TextView tv_app_name;
		TextView tv_app_memory_size;
		// CheckBox
	}
}
