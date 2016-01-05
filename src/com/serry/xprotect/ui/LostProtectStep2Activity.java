package com.serry.xprotect.ui;

import com.serry.xprotect.R;
import com.serry.xprotect.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by sing on 13-12-26. desc:
 */
public class LostProtectStep2Activity extends Activity implements View.OnClickListener {

	private RelativeLayout rl_step2_bind;
	private ImageView iv_step2_bind_status;
	private SharedPreferences sp;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lostprotected_step2);

		iv_step2_bind_status = (ImageView) findViewById(R.id.iv_step2_bind_status);
		rl_step2_bind = (RelativeLayout) findViewById(R.id.rl_step2_bind);
		rl_step2_bind.setOnClickListener(this);

		sp = getSharedPreferences("config", MODE_PRIVATE);
		String sim = sp.getString("sim", "");
		if (sim.isEmpty()) {
			iv_step2_bind_status.setImageResource(R.drawable.switch_off_normal);
		} else {
			iv_step2_bind_status.setImageResource(R.drawable.switch_on_normal);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.rl_step2_bind:
			String sim = sp.getString("sim", "");
			SharedPreferences.Editor editor = sp.edit();
			if (sim.isEmpty()) {
				// 如果之前未设置，则设置
				editor.putString("sim", Util.getSimSerial(this));
				iv_step2_bind_status.setImageResource(R.drawable.switch_on_normal);
			} else {
				// 如果之前设置过，则设置为空
				editor.putString("sim", "");
				iv_step2_bind_status.setImageResource(R.drawable.switch_off_normal);
			}
			editor.commit();
			break;
		}
	}

	// 上一步按钮的处理过程，显示第一个向导页面
	public void prev(android.view.View view) {
		Intent intent = new Intent(this, LostProtectStep1Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.rightshowleftin, R.anim.rightshowleftout);
	}

	// 下一步按钮的处理过程，显示第三个向导页面
	public void next(android.view.View view) {
		Intent intent = new Intent(this, LostProtectStep3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.leftshowrigthin, R.anim.leftshowrigthout);
	}
}