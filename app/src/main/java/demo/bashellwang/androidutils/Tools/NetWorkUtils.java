package demo.bashellwang.androidutils.Tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class NetWorkUtils {

    /**
     * 获得所有网络设备的ip地址
     *
     * @return
     */
    public static List getNetworkInterfaces() {
        List<NetworkInterface> interfaces = new ArrayList<NetworkInterface>();
        try {
            interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return interfaces;
    }

    /**
     * @return
     */
    public static JSONArray getNetworkInterfacesInfo() {
        List<NetworkInterface> interfaces = getNetworkInterfaces();
        JSONArray jsonArray = new JSONArray();
        if (interfaces == null || interfaces.size() == 0) {
            return jsonArray;
        }
        for (NetworkInterface info : interfaces) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("name", info.getName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }

    /**
     * 获取ip地址
     *
     * @param context
     * @return
     */
    public static String getIpAdress(Context context) {
        String resultStr = GlobalUtilsConst.DEFAULT_STR;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        resultStr = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultStr;
    }

    /**
     * 判断手机是否是飞行模式 android.permission.WRITE_SETTINGS
     *
     * @param context
     * @return
     */
    public static boolean getAirplaneMode(Context context) {
        try {
            int isAirplaneMode = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
            return (isAirplaneMode == 1) ? true : false;
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取Sim卡运营商名称
     * <p>
     * 中国移动、如中国联通、中国电信
     * </p>
     *
     * @param context 上下文
     * @return sim卡运营商名称
     */
    public static String getSimOperatorName(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getSimOperatorName() : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {

            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取Sim卡运营商编号
     *
     * @param context
     * @return
     */
    public static String getSimOperatorType(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getSimOperator() : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {

            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * getNetworkInterfaces 获取 信息
     *
     * @return
     */
    public static String getWifiInterface() {
        StringBuffer sb = new StringBuffer();
        try {
            for (Enumeration<NetworkInterface> list = NetworkInterface.getNetworkInterfaces(); list
                    .hasMoreElements(); ) {
                NetworkInterface item = list.nextElement();
                sb.append(item.getDisplayName() + "\r\n");
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 判断wifi是否连接状态
     * <p>
     * 需添加权限 {@code
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * }
     * </p>
     *
     * @param context 上下文
     * @return 1: 连接<br>
     * 0: 未连接
     */
    public static int isWifiConnected(Context context) {
        boolean result = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            result = cm != null && cm.getActiveNetworkInfo() != null
                    && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result ? 1 : 0;
    }

    /**
     * 判断当前连接的wifi热点是否需要密码
     *
     * @param context
     * @return 0:不需要 1:需要
     */
    public static int isWifiEncrypted(Context context) {
        String WIFI_AUTH_OPEN = "";
        String WIFI_AUTH_ROAM = "[ESS]";
        boolean result = false;
        if (isWifiConnected(context) == 1) {
            try {
                WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wm.getConnectionInfo();
                String ssid = wifiInfo.getSSID();
                if (ssid != null && ssid.startsWith("\"") && ssid.endsWith("\"")) {
                    ssid = ssid.substring(1, ssid.length() - 1);
                }
                List<ScanResult> resultList = wm.getScanResults();
                if (resultList != null && resultList.size() > 0) {

                    for (ScanResult sr : resultList) {
                        if (sr.SSID.equals(ssid) && sr.BSSID.equals(wifiInfo.getBSSID())) {
                            if (sr.capabilities != null) {
                                String capabilities = sr.capabilities.trim();
                                if (capabilities != null && (capabilities.equals(WIFI_AUTH_OPEN)
                                        || capabilities.equals(WIFI_AUTH_ROAM))) {
                                    result = false;
                                } else {
                                    result = true;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            }
        }
        return (result == false) ? 0 : 1;
    }

    /**
     * 获取 wifi列表
     *
     * @param context
     * @return
     */
    public static JSONArray getWifiNearBy(Context context) {
        JSONArray jsonArray = new JSONArray();
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            List<ScanResult> resultList = wm.getScanResults();
            if (resultList != null && resultList.size() > 0) {
                for (ScanResult sr : resultList) {
                    JSONObject obj = new JSONObject();
                    obj.put("SSID", sr.SSID);
                    obj.put("BSSID", sr.BSSID);
                    obj.put("capabilities", sr.capabilities);
                    obj.put("level", sr.level);
                    jsonArray.put(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**
     * 获取wifi ssid
     *
     * @param context
     * @return
     */
    public static String getWifiSSID(Context context) {
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wm.getConnectionInfo();
            return wifiInfo != null ? wifiInfo.getSSID() : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {

            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取wifi bssid
     *
     * @param context
     * @return
     */
    public static String getWifiBSSID(Context context) {
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wm.getConnectionInfo();
            return wifiInfo != null ? wifiInfo.getBSSID() : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {

            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取当前连接wifi信号强度,设置范围是0-5
     *
     * @param context
     * @return
     */
    public static int getWifiStrength(Context context) {
        int result = 0;
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wm.getConnectionInfo();
            if (wifiInfo != null) {
                result = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 5);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取移动网络mcc
     *
     * @param context
     * @return
     */
    public static String getMobileMcc(Context context) {
        String result = GlobalUtilsConst.DEFAULT_STR;
        try {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            // 返回值MCC + MNC
            String operator = mTelephonyManager.getNetworkOperator();
            if (operator != null && operator.length() > 3) {
                result = operator.substring(0, 3);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取移动网络mnc
     *
     * @param context
     * @return
     */
    public static String getMobileMnc(Context context) {
        String result = GlobalUtilsConst.DEFAULT_STR;
        try {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            // 返回值MCC + MNC
            String operator = mTelephonyManager.getNetworkOperator();
            if (operator != null && operator.length() > 4) {
                result = operator.substring(3, 5);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取移动网运营商名字/编号
     *
     * @param context
     * @return
     */
    public static String getNetWorkOperateName(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getNetworkOperatorName() : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {

            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取移动网类型
     *
     * @param context
     * @return
     */
    public static String getMobileNetworkType(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getNetworkType() + "" : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {

            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取当前连接的网络类型
     *
     * @param
     * @return
     */
    public static String getCurrentNetWorkTypeString(Context context) {
        String resultTypeStr = "unknow";
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return resultTypeStr;
            }
            switch (info.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    resultTypeStr = "WIFI";
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (info.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            resultTypeStr = "2G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            resultTypeStr = "3G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            resultTypeStr = "4G";
                            break;
                        default:
                            resultTypeStr = "unknow";
                    }
                    break;
                default:
                    resultTypeStr = "unknow";
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultTypeStr;
    }

    /**
     * 获取sim卡状态信息(这里获取的即为双卡信息，由逗号‘,’分割)
     *
     * @return
     */
    public static String getWholeGsmSimState() {
        try {
            return getSystemProperties("gsm.sim.state", GlobalUtilsConst.DEFAULT_STR);
        } catch (Exception e) {

            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取sim卡状态信息
     *
     * @param which 表示获取哪一个，不大于1
     * @return sim卡状态信息
     */
    public static String getGsmSimState(int which) {
        try {
            String result = getSystemProperties("gsm.sim.state", GlobalUtilsConst.DEFAULT_STR);
            if(result.indexOf(',')!=-1){
                if (which == 0) {//第一个sim卡
                    return result.substring(0, result.indexOf(','));
                } else if (which == 1) {//第二个sim卡
                    return result.substring(result.indexOf(',') + 1, result.length());
                } else {
                    return GlobalUtilsConst.DEFAULT_STR;
                }
            }else{
                if (which == 0){
                    return result;
                }else{
                    return GlobalUtilsConst.DEFAULT_STR;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取系统属性
     *
     * @param properName
     * @param defaultStr
     * @return
     */
    public static String getSystemProperties(String properName, String defaultStr) {
        String result = defaultStr;
        try {
            result = (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class, String.class)
                    .invoke(null, properName, defaultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
