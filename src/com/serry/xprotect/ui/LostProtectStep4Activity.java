package com.serry.xprotect.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.serry.xprotect.R;
import com.serry.xprotect.receiver.MyAdmin;

/**
 * Created by sing on 13-12-26.
 * desc:
 */
public class LostProtectStep4Activity extends Activity {

    private SharedPreferences sp;

    private CheckBox cb_step4_protect;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lostprotected_step4);

        sp = getSharedPreferences("config", MODE_PRIVATE);
        cb_step4_protect = (CheckBox) findViewById(R.id.cb_step4_protect);
        cb_step4_protect.setChecked(sp.getBoolean("protecting", false));
        cb_step4_protect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("protecting", b ? true : false);
                cb_step4_protect.setText(b ? "防盗保护已经开启" : "防盗保护没有开启");
            }
        });
    }

    /**
     * 单击“激活deviceadmin”时的处理事件
     *
     * @param view
     */
    public void activeDeviceAdmin(android.view.View view) {
        ComponentName componentName = new ComponentName(this, MyAdmin.class);
        DevicePolicyManager dm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        if (dm.isAdminActive(componentName) == false) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            startActivity(intent);
        }
    }

    //上一步按钮的处理过程，显示第三个向导页面
    public void prev(android.view.View view) {
        Intent intent = new Intent(this, LostProtectStep3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.rightshowleftin, R.anim.rightshowleftout);
    }

    //下一步按钮的处理过程，显示防盗页面
    public void next(android.view.View view) {

        //到这一步认为用户已经进行过防盗设置了，下一次进入不再自动显示向导
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("lostset", true);
        editor.commit();

        //如果防盗保护未开启，给予提醒
        if (cb_step4_protect.isChecked() == false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("温馨提示");
            builder.setMessage("手机防盗极大地保护您的手机安全，强烈建议开启！");
            builder.setPositiveButton("开启", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cb_step4_protect.setChecked(true);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //用户不勾选并且提示后也不开启防盗保护，则直接进入手机防盗页面
                    Intent intent = new Intent(LostProtectStep4Activity.this, LostProtectedActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                }
            });

            builder.create().show();
        } else {
            //用户勾选并开启防盗保护，则直接进入手机防盗页面
            Intent intent = new Intent(this, LostProtectedActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        }
    }
}