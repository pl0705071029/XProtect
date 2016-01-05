package com.serry.xprotect.receiver;

import android.annotation.TargetApi;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by sing on 14-1-3.
 * desc:MyAdmin是一个用于获取超级管理员权限要被激活的组件，该组件一旦被激活，当前的应用程序就获取到了超级管理员权限。
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class MyAdmin extends DeviceAdminReceiver {
    public void onReceive(Context context, Intent intent) {
    }
}
