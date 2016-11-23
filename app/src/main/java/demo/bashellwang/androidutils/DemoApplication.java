package demo.bashellwang.androidutils;

import android.app.Application;
import android.content.Context;

import demo.bashellwang.androidutils.Tools.DeviceInfoUtils;

/**
 * Created by bashellwang on 2016/11/3.
 */
public class DemoApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        // TODO Auto-generated method stub
        super.attachBaseContext(base);
        DeviceInfoUtils.startTime = System.currentTimeMillis();
    }
}
