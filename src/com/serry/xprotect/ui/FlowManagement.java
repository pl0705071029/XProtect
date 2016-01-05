package com.serry.xprotect.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.serry.xprotect.R;
import com.serry.xprotect.data.Adapter;
import com.serry.xprotect.data.ViewPagerAdapter;
import com.serry.xprotect.service.FlowService;
import com.serry.xprotect.service.FlowService.MyBinder;
import com.serry.xprotect.transaction.AppInfo;

public class FlowManagement extends Activity {
	private CustomDialog cd;
	// 定义流量
	private TextView mouth_used, mouth_leave, gprs_all, wifi_all, mouth_title, mon_overday, mon_alltraffic;
	private Animation animation;
	private Animation animation2;
	private ImageView image_row, mon_setting;
	private ViewPager viewPager;
	private ViewPagerAdapter adapter;
	private List<View> mListViews;
	private LayoutInflater mInflater;
	private View vTotal = null, vApp = null;
	private ListView listView;
	private Adapter list_adapter;
	private List<AppInfo> appInfos;
	private FlowService mservice;
	private RadioGroup group;
	private Boolean mBound = false;
	private Boolean mClick = false;
	private int x = 0;
	SharedPreferences preferences;

	int idx = 0;
	// 定义一个handler刷新界面；
	private Handler handler = new Handler();

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			appInfos = mservice.infos;
			// 刷新总量
			if (mservice.mGet == true && mClick == true) {
				list_adapter.setList(appInfos);
				list_adapter.notifyDataSetChanged();

			}
			if (mservice.mGet == true) {
				gprs_all.setText(mservice.tra[0]);
				wifi_all.setText(mservice.tra[1]);
				mouth_used.setText(mservice.tra[2]);
				mouth_leave.setText((double) (x - mservice.l) + "M");
				if ((double) (x - mservice.l) < 0) {
					mouth_title.setText("月流量已经全部用完!");
					mouth_title.setBackgroundResource(R.color.red);

				}
			}

			handler.postDelayed(runnable, 3000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monitor);
		init();
	}

	// 界面美化

	private void init() {

		animation = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
		animation2 = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
		mon_setting = (ImageView) findViewById(R.id.mon_setting);
		image_row = (ImageView) findViewById(R.id.mon_image_row);
		image_row.getBackground().setAlpha(100);
		group = (RadioGroup) findViewById(R.id.radioGroup1);
		mInflater = getLayoutInflater();
		// 抽取出view，方便添加，方便响应
		vTotal = mInflater.inflate(R.layout.total, null);
		vApp = mInflater.inflate(R.layout.app, null);
		mListViews = new ArrayList<View>();
		mListViews.add(vTotal);
		mListViews.add(vApp);
		// viewpager 的滑动
		adapter = new ViewPagerAdapter(mListViews);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == 1) {
					mClick = false;
					RadioButton rb = (RadioButton) group.getChildAt(arg0);
					rb.setChecked(true);
					mon_setting.setBackgroundResource(R.color.red);

				} else {
					mClick = true;
					RadioButton rb = (RadioButton) group.getChildAt(arg0);
					rb.setChecked(true);
					mon_setting.setBackgroundResource(R.color.liteblue);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				if (checkedId == R.id.radio0) {
					idx = 0;

				} else if (checkedId == R.id.radio1) {
					idx = 1;
				} else {
					return;
				}
				viewPager.setCurrentItem(idx);
			}
		});
		// 启动服务
		Intent intent = new Intent(FlowManagement.this, FlowService.class);
		this.startService(intent);
		// listview的填充
		listView = (ListView) vApp.findViewById(R.id.list_view);
		listView.setOnItemClickListener(onItemClickListener);
		mon_setting.setOnClickListener(listener);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		cd = new CustomDialog(this);
		// 绑定后台服务
		Intent binderIntent = new Intent(getApplicationContext(), FlowService.class);
		getApplicationContext().bindService(binderIntent, connection, BIND_AUTO_CREATE);
		// 流量界面的初始化
		preferences = getSharedPreferences("small_setting", MODE_PRIVATE);// 获取总流量
		mon_overday = (TextView) findViewById(R.id.mon_overday);
		mon_alltraffic = (TextView) findViewById(R.id.mon_alltraffic);
		mouth_title = (TextView) vTotal.findViewById(R.id.textView7);
		mouth_used = (TextView) vTotal.findViewById(R.id.alltraffic);
		mouth_leave = (TextView) vTotal.findViewById(R.id.leave);
		gprs_all = (TextView) vTotal.findViewById(R.id.total_gprs);
		wifi_all = (TextView) vTotal.findViewById(R.id.total_wifi);
		mon_overday.setText(preferences.getInt("overday", 1) + "号");
		x = preferences.getInt("usertraffic", 30);
		mon_alltraffic.setText(x + "M");

		super.onResume();
	}

	// 定义serviceconnection
	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mBound = false;

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			// 强制转换ibinder为mybinder以获得monitorservice对象

			MyBinder binder = (MyBinder) service;
			mservice = binder.getService();
			mBound = true;
			getData();
			handler.postDelayed(runnable, 3000);

		}
	};

	private void getData() {
		if (mservice.infos != null) {
			appInfos = mservice.infos;
			list_adapter = new Adapter(appInfos, getApplicationContext());
			listView.setAdapter(list_adapter);
		}
	}

	// 监听条事件
	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
			// TODO Auto-generated method stub
			final ImageButton delete_process = (ImageButton) view.findViewById(R.id.delete_process);
			final TextView process_name = (TextView) view.findViewById(R.id.process_name);
			view.findViewById(R.id.delete_process).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 杀死进程

					ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
					activityManager.killBackgroundProcesses(process_name.getText().toString());
					list_adapter.getList().remove(position);
					list_adapter.notifyDataSetChanged();
					delete_process.setVisibility(View.GONE);

					Log.v("jia", process_name.getText().toString() + "结束");
				}
			});
			if (delete_process.getVisibility() == view.VISIBLE) {
				delete_process.startAnimation(animation2);
				delete_process.setVisibility(view.GONE);

			} else {
				delete_process.startAnimation(animation);
				delete_process.setVisibility(view.VISIBLE);

			}
		}
	};
	// 设置
	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (idx == 0) {
				cd.show();
				mon_overday.setText(preferences.getInt("overday", 1) + "号");
				x = preferences.getInt("usertraffic", 30);
				mon_alltraffic.setText(x + "M");

			} else {
				ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
				for (int i = 0; i < list_adapter.getList().size(); i++) {
					activityManager.killBackgroundProcesses(list_adapter.getList().get(i).getApp_package());
				}
				list_adapter.getList().clear();
				list_adapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "所有进程与缓存全部结束了，系统得到了优化！", 0).show();
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(1, 0, 0, "关于");
		menu.add(1, 1, 1, "退出");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0:
			break;

		default:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
