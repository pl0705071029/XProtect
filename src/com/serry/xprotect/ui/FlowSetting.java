package com.serry.xprotect.ui;

import com.serry.xprotect.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FlowSetting extends Activity {
	private ImageView set_back;
	private LinearLayout linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		init();
	}

	// 初始化界面
	private void init() {
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		linearLayout.getBackground().setAlpha(100);
		set_back = (ImageView) findViewById(R.id.set_back);
		set_back.setOnClickListener(listener);

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	};

}