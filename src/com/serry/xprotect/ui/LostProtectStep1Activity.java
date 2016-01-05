package com.serry.xprotect.ui;

import com.serry.xprotect.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by sing on 13-12-26.
 * desc:
 */
public class LostProtectStep1Activity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lostprotected_step1);
    }

    //下一步按钮的处理过程，显示第二个向导页面
    public void next(android.view.View view) {
        Intent intent = new Intent(this, LostProtectStep2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.leftshowrigthin, R.anim.leftshowrigthout);
    }
}