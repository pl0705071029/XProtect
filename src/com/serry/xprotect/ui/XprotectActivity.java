package com.serry.xprotect.ui;

import java.io.File;

import com.serry.xprotect.R;
import com.serry.xprotect.R.id;
import com.serry.xprotect.R.layout;
import com.serry.xprotect.utils.AssetUtil;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class XprotectActivity extends Activity implements OnClickListener {

	private TextView phone_accelarate;
	private TextView permision_management;
	private TextView flow_management;
	private TextView phone_clean;
	private TextView harass_intercept;
	private TextView virus_cleaning;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		phone_accelarate = (TextView) findViewById(R.id.phone_accelarate);
		permision_management = (TextView) findViewById(R.id.permision_management);
		flow_management = (TextView) findViewById(R.id.flow_management);
		phone_clean = (TextView) findViewById(R.id.theft_proof);
		harass_intercept = (TextView) findViewById(R.id.harass_intercept);
		virus_cleaning = (TextView) findViewById(R.id.virus_cleaning);

		phone_accelarate.setOnClickListener(this);
		permision_management.setOnClickListener(this);
		flow_management.setOnClickListener(this);
		phone_clean.setOnClickListener(this);
		harass_intercept.setOnClickListener(this);
		virus_cleaning.setOnClickListener(this);

		// 拷贝病毒库的数据库文件
		new Thread() {
			public void run() {
				File file = new File(getFilesDir(), "antivirus.db");
				if (file.exists() && file.length() > 0) {// 数据库文件已经拷贝成功

				} else {
					AssetUtil.copy1(getApplicationContext(), "antivirus.db", file.getAbsolutePath(), null);
				}
			}

		}.start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.phone_accelarate:
			startActivity(new Intent(XprotectActivity.this, PhoneAccelarateActivity.class));
			break;
		case R.id.permision_management:
			startActivity(new Intent(XprotectActivity.this, ActivityMain.class));
			break;
		case R.id.flow_management:
			startActivity(new Intent(XprotectActivity.this, FlowManagement.class));
			break;
		case R.id.theft_proof:
			startActivity(new Intent(XprotectActivity.this, LostProtectedActivity.class));
			break;
		case R.id.harass_intercept:

			break;
		case R.id.virus_cleaning:
			startActivity(new Intent(XprotectActivity.this, VirusKillActivity.class));
			break;

		default:
			break;
		}
	}
}
