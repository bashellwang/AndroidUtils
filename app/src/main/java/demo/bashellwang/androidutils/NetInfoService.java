package demo.bashellwang.androidutils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class NetInfoService extends Service {
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private NetState currentStateCode, lastStateCode = NetState.NET_NO;
    public static String DEVICE_INFO_ACTION_SERVICE = "demo.bashellwang.androidutils.NetInfoService";

    /**
     * 枚举网络状态 NET_NO：没有网络 NET_2G:2g网络 NET_3G：3g网络 NET_4G：4g网络 NET_WIFI：wifi
     * NET_UNKNOWN：未知网络
     */
    public enum NetState {
        NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_UNKNOWN
    }
    /**
     * 广播实例
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction(); // 当前接受到的广播的标识(行动/意图)
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isConnectedOrConnecting()/* info.isAvailable() */) {
                    currentStateCode = getNetStateCode(info);
                } else {
                    currentStateCode = NetState.NET_NO;
                }
                if (currentStateCode != lastStateCode) {
                    Toast.makeText(context, currentStateCode + "", Toast.LENGTH_SHORT).show();
                    lastStateCode = currentStateCode;
                }
            }
        }
    };

    /**
     * 获取网络类型
     *
     * @param
     * @return
     */
    private NetState getNetStateCode(NetworkInfo info) {
        NetState stateCode = NetState.NET_NO;
        if (info == null) {
            return NetState.NET_NO;
        }
        switch (info.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                stateCode = NetState.NET_WIFI;
                break;
            case ConnectivityManager.TYPE_MOBILE:
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                    case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                    case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        stateCode = NetState.NET_2G;
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
                        stateCode = NetState.NET_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        stateCode = NetState.NET_4G;
                        break;
                    default:
                        stateCode = NetState.NET_UNKNOWN;
                }
                break;
            default:
                stateCode = NetState.NET_UNKNOWN;
        }
        return stateCode;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 注册网络状态的广播，绑定到mReceiver
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            lastStateCode = NetState.NET_NO;
        } else {
            lastStateCode = getNetStateCode(info);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销接收
        unregisterReceiver(mReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

}