package com.serry.xprotect.ui;

import com.serry.xprotect.R;
import com.serry.xprotect.utils.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by sing on 13-12-25.
 * desc:
 */
public class LostProtectedActivity extends Activity implements View.OnClickListener {

    //常量定义
    public static final int MENU_ITEM_ID_RENAME_TITLE = Menu.FIRST + 1;

    //保存配置
    private SharedPreferences sp;

    private AlertDialog dialog;

    //第一次进入手机防盗弹出设置密码对话框的控件
    private EditText et_first_dlg_pswd;
    private EditText et_first_dlg_pswd_confirm;
    private Button bt_first_dlg_ok;
    private Button bt_first_dlg_cancel;

    //非第一次进入手机防盗弹出设置密码对话框的控件
    private EditText et_normal_dlg_pswd;
    private Button bt_normal_dlg_ok;
    private Button bt_normal_dlg_cancel;

    private TextView tv_lost_safe_number;
    private CheckBox cb_lost_protect_setting;
    private TextView tv_lost_protect_resetup;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("config", MODE_PRIVATE);

        /*方便开发，这里不再要求输入
        //如果是第一次进入手机防盗则弹出设置密码对话框，否则弹出输入密码对话框
        if (isFirstEntry()) {
            showFirstEntryDialog();
        }else {
            showNormalEntryDialog();
        }
        */

        //如果之前没有设置手机防盗，则显示设置向导
        if (isFirstSetup()) {
            Intent intent = new Intent(this, LostProtectStep1Activity.class);
            startActivity(intent);
        } else {
            //已经设置过，则显示设置的信息
            setContentView(R.layout.activity_lostprotected);
            tv_lost_safe_number = (TextView) findViewById(R.id.tv_lost_safe_number);
            cb_lost_protect_setting = (CheckBox) findViewById(R.id.cb_lost_protect_setting);
            tv_lost_protect_resetup = (TextView) findViewById(R.id.tv_lost_protect_resetup);

            String safenumber = sp.getString("safenumber", "");
            tv_lost_safe_number.setText(safenumber.isEmpty() ? "尚未设置安全号码" : safenumber);

            boolean protecting = sp.getBoolean("protecting", false);
            cb_lost_protect_setting.setChecked(protecting);
            cb_lost_protect_setting.setText(protecting ? "防盗保护已经开启" : "防盗保护没有开启");
            cb_lost_protect_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    cb_lost_protect_setting.setChecked(b);
                    cb_lost_protect_setting.setText(b ? "防盗保护已经开启" : "防盗保护没有开启");
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("protecting", b);
                    editor.commit();
                }
            });

            tv_lost_protect_resetup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LostProtectedActivity.this, LostProtectStep1Activity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, MENU_ITEM_ID_RENAME_TITLE, 0, "更改标题名称");
        return true;
    }

    /**
     * 菜单处理事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        //选择“更改标题名称”菜单，弹出对话框让用户输入标题
        if (item.getItemId() == MENU_ITEM_ID_RENAME_TITLE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText et = new EditText(this);
            et.setHint("请输入新的标题名，可为空");
            builder.setView(et);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String newtitle = et.getText().toString().trim();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("newtitle", newtitle);
                    editor.commit();
                }
            });
            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 判断是否是第一次进入手机防盗页面，判断方法是检测有没有设置过密码
     *
     * @return
     */
    private boolean isFirstEntry() {
        String password = sp.getString("password", "");
        return password.isEmpty();
    }

    private boolean isFirstSetup() {
        return (sp.getBoolean("lostset", false) == false);
    }

    /**
     * 第一次进入手机防盗弹出设置密码对话框
     */
    private void showFirstEntryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.first_entry_dialog, null);
        et_first_dlg_pswd = (EditText) view.findViewById(R.id.et_first_dlg_pswd);
        et_first_dlg_pswd_confirm = (EditText) view.findViewById(R.id.et_first_dlg_pswd_confirm);
        bt_first_dlg_ok = (Button) view.findViewById(R.id.bt_first_dlg_ok);
        bt_first_dlg_cancel = (Button) view.findViewById(R.id.bt_first_dlg_cancel);

        //xml中已经设置android:onClick="onClick"，这里无需再设置
        //bt_first_dlg_ok.setOnClickListener(this);
        //bt_first_dlg_cancel.setOnClickListener(this);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    /**
     * 非第一次进入手机防盗弹出输入密码对话框
     */
    private void showNormalEntryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.normal_entry_dialog, null);
        et_normal_dlg_pswd = (EditText) view.findViewById(R.id.et_normal_dlg_pswd);
        bt_normal_dlg_ok = (Button) view.findViewById(R.id.bt_normal_dlg_ok);
        bt_normal_dlg_cancel = (Button) view.findViewById(R.id.bt_normal_dlg_cancel);

        //xml中已经设置android:onClick="onClick"，这里无需再设置
        //bt_normal_dlg_ok.setOnClickListener(this);
        //bt_normal_dlg_cancel.setOnClickListener(this);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.bt_first_dlg_cancel:
                dialog.cancel();
                finish();
                break;
            case R.id.bt_first_dlg_ok:
                String pswd = et_first_dlg_pswd.getText().toString().trim();
                String pswdconfirm = et_first_dlg_pswd_confirm.getText().toString().trim();

                //密码不能为空
                if (pswd.isEmpty() || pswdconfirm.isEmpty()) {
                    Toast.makeText(this, "密码不能为空", 1).show();
                    return;
                }

                //判断两次密码输入是否相等
                if (pswd.equals(pswdconfirm)) {
                    //保存密码
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password", Util.md5String(pswd));
                    editor.commit();
                    dialog.dismiss();
                    //设置完毕后进入主界面，要求用户重新进入
                    finish();
                } else {
                    Toast.makeText(this, "两次密码不相同", 1).show();
                    return;
                }
                break;
            case R.id.bt_normal_dlg_cancel:
                finish();
                break;
            case R.id.bt_normal_dlg_ok:
                String inputpswd = et_normal_dlg_pswd.getText().toString().trim();
                if (inputpswd.isEmpty()) {
                    Toast.makeText(this, "密码不能为空", 1).show();
                    return;
                }

                String savedpswd = sp.getString("password", "");
                if (Util.md5String(inputpswd).equals(savedpswd)) {
                    Toast.makeText(this, "密码正确进入界面", 1).show();
                    dialog.dismiss();
                    return;
                } else {
                    Toast.makeText(this, "密码不正确", 1).show();
                    return;
                }
        }
    }

}