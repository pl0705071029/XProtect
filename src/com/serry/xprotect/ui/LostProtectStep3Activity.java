package com.serry.xprotect.ui;

import com.serry.xprotect.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sing on 13-12-26.
 * desc:
 */
public class LostProtectStep3Activity extends Activity {

    //输入安全号码的编辑框
    private EditText et_step3_number;

    private SharedPreferences sp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lostprotected_step3);

        //读取配置，如果已经设置过安全号码则显示出来，否则设置hint
        sp = getSharedPreferences("config", MODE_PRIVATE);
        et_step3_number = (EditText) findViewById(R.id.et_step3_number);
        String number = sp.getString("safenumber", "");
        if (number.isEmpty()) {
            et_step3_number.setHint("请输入安全号码或者选择一个号码");
        } else {
            et_step3_number.setText(number);
        }

    }

    //上一步按钮的处理过程，显示第二个向导页面
    public void prev(android.view.View view) {
        Intent intent = new Intent(this, LostProtectStep2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.rightshowleftin, R.anim.rightshowleftout);
    }

    //下一步按钮的处理过程，将设置的安全号码保存到配置文件，并显示第四个向导页面
    public void next(android.view.View view) {
        String number = et_step3_number.getText().toString().trim();
        if (number.isEmpty()) {
            Toast.makeText(this, "安全号码不能为空", 0).show();
            return;
        }

        //将设置的安全号码保存到配置文件
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("safenumber", number);
        editor.commit();

        //显示第四个向导页面
        Intent intent = new Intent(this, LostProtectStep4Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.leftshowrigthin, R.anim.leftshowrigthout);
    }

    //按钮“选择联系人”的处理事件
    public void selectContact(android.view.View view) {
        //Intent intent = new Intent(this, SelectContactActivity.class);
        //startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            //获取选择的联系人号码
            String number = data.getStringExtra("number");
            et_step3_number.setText(number);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}