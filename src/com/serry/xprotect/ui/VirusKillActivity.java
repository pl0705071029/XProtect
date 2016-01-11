package com.serry.xprotect.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.serry.xprotect.R;
import com.serry.xprotect.provider.VirusKill;
import com.serry.xprotect.utils.Md5Encoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sing on 14-1-26. desc:
 */
public class VirusKillActivity extends Activity implements View.OnClickListener {

	protected static final int SCAN_NOT_VIRUS = 90;
	protected static final int FIND_VIRUS = 91;
	protected static final int SCAN_FINISH = 92;

	// 查杀病毒时，雷达上的扫描指针
	private ImageView iv_scan;
	// 应用程序包管理器
	private PackageManager pm;
	// 操作数据库的对象
	private VirusKill dao;
	// 扫描进度条
	private ProgressBar progressBar1;
	// 显示发现的病毒数目
	private Button scanAndKill;
	private TextView tv_current_scan;
	// 显示扫描的程序信息
	private LinearLayout ll_scan_status;
	// 用于添加扫描到的病毒信息
	private List<PackageInfo> virusPackInfos;
	// 旋转动画
	RotateAnimation ra;
	// 存放病毒的集合
	private Map<String, String> virusMap;
	// 用于与子线程通信，更新主线程（UI线程）
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			PackageInfo info = (PackageInfo) msg.obj;
			switch (msg.what) {
			case SCAN_NOT_VIRUS:// 未发现病毒
				tv_current_scan.setText(info.applicationInfo.loadLabel(pm));
				break;
			case FIND_VIRUS:// 发现病毒
				// 将病毒添加到集合中
				TextView tv2 = new TextView(getApplicationContext());
				tv2.setTextColor(Color.RED);
				tv2.setText(getString(R.string.scan_danger, info.applicationInfo.loadLabel(pm)));
				ll_scan_status.addView(tv2, 0);// 添加到ll_scan_info控件的最上面
				virusPackInfos.add(info);
				break;
			case SCAN_FINISH:// 扫描完成
				// 停止动画的播放
				iv_scan.clearAnimation();
				tv_current_scan.setText("");
				// 判断病毒集合的大小
				if (virusPackInfos.size() == 0) {
					scanAndKill.setText(getString(R.string.finish));
					Toast.makeText(getApplicationContext(), getString(R.string.no_virus_message), 0).show();
				} else {
					scanAndKill.setText(getString(R.string.kill_virus));
				}
				break;
			}
		}

	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viruskill_layout);

		pm = getPackageManager();
		dao = new VirusKill(this);
		virusPackInfos = new ArrayList<PackageInfo>();
		super.onCreate(savedInstanceState);
		tv_current_scan = (TextView) findViewById(R.id.tv_current_scan);
		scanAndKill = (Button) findViewById(R.id.scan_and_kill);
		scanAndKill.setOnClickListener(this);
		iv_scan = (ImageView) findViewById(R.id.iv_scan);
		// 设置一个旋转的动画
		ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setDuration(1000);
		// 设置旋转的重复次数（一直旋转）
		ra.setRepeatCount(Animation.INFINITE);
		// 设置旋转的模式（旋转一个回合后，重新旋转）
		ra.setRepeatMode(Animation.RESTART);
		ll_scan_status = (LinearLayout) findViewById(R.id.ll_scan_status);
		progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
	}

	public void kill(View v) {
		// 重置动画
		ra.reset();
		// 启动动画
		iv_scan.startAnimation(ra);
		// 开启一条子线程，遍历手机中各个应用的签名信息
		new Thread() {
			public void run() {
				// PackageManager.GET_SIGNATURES应用程序的签名信息
				List<PackageInfo> packinfos = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
				progressBar1.setMax(packinfos.size());
				// 计数当前已经遍历了多少条应用程序，以显示查杀的进度
				int count = 0;
				// 遍历出各个应用程序对应的签名信息
				for (PackageInfo info : packinfos) {
					// 将应用程序的签名信息转成MD5值，用于与病毒数据库比对
					String md5 = Md5Encoder.encode(info.signatures[0].toCharsString());
					// 在病毒数据库中查找该MD5值，来判断该应用程序是否数据病毒
					String result = dao.getVirusInfo(md5);
					// 如果查找的结果为null，则表示当前遍历的应用不是病毒
					if (result == null) {
						Message msg = Message.obtain();
						msg.what = SCAN_NOT_VIRUS;
						msg.obj = info;
						handler.sendMessage(msg);
					} else {// 当前遍历到的应用属于病毒
						Message msg = Message.obtain();
						msg.what = FIND_VIRUS;
						msg.obj = info;
						handler.sendMessage(msg);
					}
					count++;
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					progressBar1.setProgress(count);
				}
				// 遍历结束
				Message msg = Message.obtain();
				msg.what = SCAN_FINISH;
				handler.sendMessage(msg);

			}

			;
		}.start();

	}

	// "一键清理"按钮
	public void clean(View v) {
		// 判断病毒集合的大小
		if (virusPackInfos.size() > 0) {
			for (PackageInfo pinfo : virusPackInfos) {
				// 卸载应用程序
				String packname = pinfo.packageName;
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_DELETE);
				intent.setData(Uri.parse("package:" + packname));
				startActivity(intent);
				scanAndKill.setText(getText(R.string.finish));
			}
		} else {
			return;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.scan_and_kill:
			if (scanAndKill.getText().equals(getText(R.string.scan_and_kill_virus))) {
				kill(v);
			} else if (scanAndKill.getText().equals(getText(R.string.finish))) {
				finish();
			} else if (scanAndKill.getText().equals(getText(R.string.kill_virus))) {
				clean(v);
			}
			break;
		}
	}
}