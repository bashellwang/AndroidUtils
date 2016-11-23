package demo.bashellwang.androidutils.Tools;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 设备环境信息工具类
 *
 * @author bashellwang
 */
public class DeviceInfoUtils {
    /**
     * 记录应用启动时间 http://android.jobbole.com/84174/
     * 1:如果记录冷启动启动时间一般可以在Application.attachBaseContext()开始的位置记录起始时间点
     * 2:可以在Activity.onWindowFocusChanged记录应用启动的结束时间点
     */
    public static boolean firstTimeIn = true;// 记录应用是否是第一次进入
    public static long startTime = 0;// 应用启动记录点,Application.attachBaseContext()
    public static long endTime = 0;// 应用启动结束记录点MainActivity.onWindowFocusChanged

    /**
     * 获取app启动时间
     */
    public static long getAppStartTime() {
        firstTimeIn = false;
        return endTime - startTime;
    }

    /**
     * 获取剩余电量 读取 文件/sys/class/power_supply/Battery/capacity信息在
     *
     * @return
     */
    public static String getBattery() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/class/power_supply/Battery/capacity"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            if (result.length() == 0) {
                String[] args1 = {"/system/bin/cat", "/sys/class/power_supply/battery/capacity"};
                cmd = new ProcessBuilder(args1);
                process = cmd.start();
                in = process.getInputStream();
                re = new byte[24];
                while (in.read(re) != -1) {
                    result = result + new String(re);
                }
            }
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            result = GlobalUtilsConst.DEFAULT_STR;
        }
        return result.trim();
    }

    /**
     * getSerialNumber
     *
     * @return result is same to getSerialNumber1()
     */
    public static String getSerialNumber() {
        String serial = GlobalUtilsConst.DEFAULT_STR;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    /**
     * 获取IMIE码
     * <p>
     * 需添加权限 {@code
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}
     * </p>
     *
     * @param context 上下文
     * @return IMIE码
     */
    public static String getIMEI(Context context) {
        String result = GlobalUtilsConst.DEFAULT_STR;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getDeviceId() : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取IMSI码
     * <p>
     * 需添加权限 {@code
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}
     * </p>
     *
     * @param context 上下文
     * @return IMIE码
     */
    public static String getIMSI(Context context) {
        String result = GlobalUtilsConst.DEFAULT_STR;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getSubscriberId() : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取sim卡序列号iccid 不同于misi
     *
     * @param context
     * @return
     */
    public static String getICCID(Context context) {
        String result = GlobalUtilsConst.DEFAULT_STR;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getSimSerialNumber() : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取本手机号
     *
     * @param mContext
     * @return
     */
    public static String getCurrentPhoneNum(Context mContext) {
        String result = GlobalUtilsConst.DEFAULT_STR;
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getLine1Number() : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取MAC地址
     *
     * @param mContext
     * @return
     */
    public static String getMacAddress(Context mContext) {
        String macStr = GlobalUtilsConst.DEFAULT_STR;
        try {
            WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo.getMacAddress() != null) {
                macStr = wifiInfo.getMacAddress();// MAC地址
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return macStr;
    }

    /**
     * 获取android id
     *
     * @return
     */
    public static String getAndroidId(Context context) {
        try {
            return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return GlobalUtilsConst.DEFAULT_STR;
    }

    /**
     * 获取内核版本号
     *
     * @return
     */
    public static String getKernelVersion() {
        String kernelVersion = GlobalUtilsConst.DEFAULT_STR;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = new FileInputStream("/proc/version");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8 * 1024);
            String info = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                info += line;
            }
            if (info != "") {
                String keyword = "version ";
                int index = info.indexOf(keyword);
                line = info.substring(index + keyword.length());
                index = line.indexOf(" ");
                kernelVersion = line.substring(0, index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return kernelVersion;
    }

    /**
     * 获取内核启动时间,以可读字符串形式
     *
     * @return
     */
    public static String getSystemBootTimeStr() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E", Locale.getDefault());
            String time = sdf.format(System.currentTimeMillis() - android.os.SystemClock.elapsedRealtime());
            return time;
        } catch (Exception e) {
            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取内核启动时间，以 long 类型返回
     *
     * @return
     */
    public static long getSystemBootTimeLong() {
        try {
            long time = System.currentTimeMillis() - android.os.SystemClock.elapsedRealtime();
            return time;
        } catch (Exception e) {
            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_INT;
        }
    }

    /**
     * 获取启动到现在的时间，以可读字符串形式
     *
     * @return
     */
    public static String getSystemBootTimeLengthStr() {
        try {
            return getBeapartDate(android.os.SystemClock.elapsedRealtime());
        } catch (Exception e) {
            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取启动到现在的时间，以 long 类型返回
     *
     * @return
     */
    public static long getSystemBootTimeLengthLong() {
        try {
            return android.os.SystemClock.elapsedRealtime();
        } catch (Exception e) {
            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_INT;
        }
    }

    /**
     * 根据时间戳，返回 X天X小时X分X秒
     *
     * @param timeMillis 时间戳
     * @return
     */
    public static String getBeapartDate(long timeMillis) {
        String beapartdate = GlobalUtilsConst.DEFAULT_STR;
        long seconds = timeMillis / 1000;
        int nDay = (int) seconds / (24 * 60 * 60);
        int nHour = (int) (seconds - nDay * 24 * 60 * 60) / (60 * 60);
        int nMinute = (int) (seconds - nDay * 24 * 60 * 60 - nHour * 60 * 60) / 60;
        int nSecond = (int) seconds - nDay * 24 * 60 * 60 - nHour * 60 * 60 - nMinute * 60;
        beapartdate = nDay + "天" + nHour + "小时" + nMinute + "分" + nSecond + "秒";
        return beapartdate;
    }

    /**
     * 判断设备是否root
     *
     * @return 1: 是 0: 否
     */
    public static int isDeviceRoot() {
        boolean result = false;
        try {
            String su = "su";
            String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                    "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
            for (String location : locations) {
                if (new File(location + su).exists()) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result ? 1 : 0;
    }

    /**
     * getKernelQemu system/prop 文件中
     *
     * @return
     */
    public static String getKernelQemu() {
        String result = GlobalUtilsConst.DEFAULT_STR;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            result = (String) get.invoke(c, "ro.kernel.qemu");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断设备是否是模拟器
     *
     * @return 0:不是模拟器 1：是模拟器
     */
    public static int isEmulator() {
        boolean result = false;
        try {
            result = Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown")
                    || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion")
                    || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                    || "google_sdk".equals(Build.PRODUCT);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result ? 1 : 0;
    }

    /**
     * 获取USB插拔状态
     *
     * @param context
     * @return 0:未连接 1：连接
     */
    public static int isUSBConnected(Context context) {
        boolean result = false;
        try {
            Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            result = plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result ? 1 : 0;
    }

    /**
     * 获取当前使用的语言设置
     *
     * @return
     */
    public static String getLanguage() {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Exception e) {

            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取当前时区
     *
     * @return
     */
    public static String getTimeZone() {
        try {
            return TimeZone.getDefault().getDisplayName();
        } catch (Exception e) {

            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取操作系统名
     *
     * @return
     */
    public static String getOSName() {
        try {
            return System.getProperty("os.name");
        } catch (Exception e) {

            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取源码控制版本号
     *
     * @return
     */
    public static String getBuildVersionIncremental() {
        return Build.VERSION.INCREMENTAL;
    }

    /**
     * 获取版本字符串
     *
     * @return
     */
    public static String getBuildVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static String getBuildVersionSDK() {
        return Build.VERSION.SDK_INT + "";
    }

    /**
     * 获取设备基板名称
     *
     * @return
     */
    public static String getBuildBoardName() {
        return Build.BOARD;
    }

    /**
     * 获取设备品牌名
     *
     * @return
     */
    public static String getBuildBrandName() {
        return Build.BRAND;
    }

    /**
     * 获取设备驱动名称
     *
     * @return
     */
    public static String getBuildDeviceName() {
        return Build.DEVICE;
    }

    /**
     * 获取设备制造商
     *
     * @return
     */
    public static String getBuildManufacturerName() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机的型号
     *
     * @return
     */
    public static String getBuildModelName() {
        return Build.MODEL;
    }

    /**
     * 整个产品的名称
     *
     * @return
     */
    public static String getBuildProductName() {
        return Build.PRODUCT;
    }

    /**
     * 获取设备显示的版本包名
     *
     * @return
     */
    public static String getBandVer() {
        return Build.DISPLAY;
    }

    /**
     * 获取设备显示的版本id
     *
     * @return
     */
    public static String getBuildDisplayId() {
        return Build.ID;
    }

    /**
     * 设备标签。如release-keys 或测试的 test-keys
     *
     * @return
     */
    public static String getBuildTagsName() {
        return Build.TAGS;
    }

    /**
     * 获取设备支持的指令集
     * system/build.prop 文件中，ro.product.cpu.abilist
     *
     * @return
     */
    public static JSONArray getSupportedInstructionSets() {
        JSONArray jsonArray = new JSONArray();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String[] abis = Build.SUPPORTED_ABIS;
                if (abis != null && abis.length > 0) {
//                    result = Arrays.toString(abis);
                    for (String info : abis) {
                        JSONObject obj = new JSONObject();
                        obj.put("name", info);
                        jsonArray.put(obj);
                    }
                    return jsonArray;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
