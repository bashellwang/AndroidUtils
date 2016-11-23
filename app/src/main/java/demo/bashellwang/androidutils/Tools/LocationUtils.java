package demo.bashellwang.androidutils.Tools;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import java.util.List;

/**
 * 获取位置相关工具类 android.permission.ACCESS_COARSE_LOCATION
 *
 * @author bashellwang
 */
public class LocationUtils {

    /**
     * 获取基站编号id
     *
     * @param context
     * @return
     */
    public static int getCellId(Context context) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            GsmCellLocation location = (GsmCellLocation) manager.getCellLocation();
            return location != null ? location.getCid() : -1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_INT;
        }
    }

    /**
     * 获取基站区域编码
     *
     * @param context
     * @return
     */
    public static int getLac(Context context) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            GsmCellLocation location = (GsmCellLocation) manager.getCellLocation();
            return location != null ? location.getLac() : -1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_INT;
        }
    }

    /**
     * 获取基站信号强度
     *
     * @param context
     * @return
     */
    public static int getCellRssi(Context context) {
        int strength = 0;
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            /** 通过getNeighboringCellInfo获取BSSS */
            List<NeighboringCellInfo> infoLists = manager.getNeighboringCellInfo();
            if (infoLists != null && infoLists.size() > 0) {
                for (NeighboringCellInfo info : infoLists) {
                    strength += (-133 + 2 * info.getRssi());// 获取邻区基站信号强度
                }
                strength = strength / infoLists.size();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strength;
    }

    /**
     * 判断GPS是否开启
     *
     * @param context
     * @return 0:未打开 1：打开
     */
    public static int isGpsOn(Context context) {
        boolean result = false;
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager == null) {
                return 0;
            }
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
            boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (gps || network) {
                result = true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result ? 1 : 0;
    }

    /**
     * 获取经纬度
     *
     * @param context
     * @param type    INT_LATITUDE:0 纬度 INT_LONGITUDE:1 经度
     * @return
     */
    public static double getLatitudeLongitude(Context context, int type) {
        try {
            double resultLatitude = GlobalUtilsConst.DEFAULT_INT;
            double resultLongitude = GlobalUtilsConst.DEFAULT_INT;
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            String locationProvider;
            List<String> providers = locationManager.getProviders(true);
            if (providers.contains(LocationManager.GPS_PROVIDER)) {
                // 如果是GPS
                locationProvider = LocationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                // 如果是Network
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else {
                return 0;
            }
            Location location = locationManager.getLastKnownLocation(locationProvider);
            if (location != null) {
                resultLatitude = location.getLatitude();
                resultLongitude = location.getLongitude();
            }
            if (type == GlobalUtilsConst.INT_LATITUDE) {
                return resultLatitude;
            } else if (type == GlobalUtilsConst.INT_LONGITUDE) {
                return resultLongitude;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return GlobalUtilsConst.DEFAULT_INT;
    }

}
