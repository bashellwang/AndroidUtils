package demo.bashellwang.androidutils.Tools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SensorUtils {

    /**
     * 获取 传感器列表
     *
     * @param context
     * @return
     */
    public static List<Sensor> getSensorList(Context context) {
        List<Sensor> sensors = new ArrayList<Sensor>();
        try {
            // 获取传感器管理器
            SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            // 获取全部传感器列表
            sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sensors;
    }

    /**
     * 获取传感器列表的信息，字符串返回
     *
     * @param context
     * @return
     */
    public static JSONArray getSensorListInfo(Context context) {
        String result = GlobalUtilsConst.DEFAULT_STR;
        List<Sensor> sensors = getSensorList(context);
        JSONArray jsonArray = new JSONArray();
        if (sensors == null || sensors.size() == 0) {
            return jsonArray;
        }
        for (Sensor item : sensors) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("name", item.getName());
                obj.put("version", item.getVersion());
                obj.put("vender", item.getVendor());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }

    /**
     * 是否支持加速度传感器
     *
     * @param context
     * @return 0:不支持 1：支持
     */
    public static int isAcceleSensorAvailable(Context context) {
        try {
            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            return (null != sensor) ? 1 : 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 是否支持磁力传感器
     *
     * @param context
     * @return 0:不支持 1：支持
     */
    public static int isMagneticAvailable(Context context) {
        try {
            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            return (null != sensor) ? 1 : 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 是否支持陀螺仪传感器
     *
     * @param context
     * @return 0:不支持 1：支持
     */
    public static int isGyroscopeAvailable(Context context) {
        try {
            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            return (null != sensor) ? 1 : 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 是否支持重力传感器
     *
     * @param context
     * @return 0:不支持 1：支持
     */
    public static int isGravityAvailable(Context context) {
        try {
            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
            return (null != sensor) ? 1 : 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 是否支持线性加速度传感器
     *
     * @param context
     * @return 0:不支持 1：支持
     */
    public static int isLinearAcclerationAvailable(Context context) {
        try {
            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            return (null != sensor) ? 1 : 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 是否支持光线传感器
     *
     * @param context
     * @return 0:不支持 1：支持
     */
    public static int isLightAvailable(Context context) {
        try {
            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
            return (null != sensor) ? 1 : 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 是否支持距离传感器
     *
     * @param context
     * @return 0:不支持 1：支持
     */
    public static int isProximityAvailable(Context context) {
        try {
            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            return (null != sensor) ? 1 : 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 是否支持温度传感器
     *
     * @param context
     * @return 0:不支持 1：支持
     */
    public static int isTemperatureAvailable(Context context) {
        try {
            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
            return (null != sensor) ? 1 : 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 是否支持压力传感器
     *
     * @param context
     * @return 0:不支持 1：支持
     */
    public static int isPressureAvailable(Context context) {
        try {
            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
            return (null != sensor) ? 1 : 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

}
