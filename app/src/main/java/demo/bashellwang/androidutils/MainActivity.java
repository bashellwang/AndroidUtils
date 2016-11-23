package demo.bashellwang.androidutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import demo.bashellwang.androidutils.Tools.AppUtils;
import demo.bashellwang.androidutils.Tools.BlueToothUtils;
import demo.bashellwang.androidutils.Tools.CPUInfoUtils;
import demo.bashellwang.androidutils.Tools.DeviceInfoUtils;
import demo.bashellwang.androidutils.Tools.GlobalUtilsConst;
import demo.bashellwang.androidutils.Tools.MemInfoUtils;
import demo.bashellwang.androidutils.Tools.NetWorkUtils;
import demo.bashellwang.androidutils.Tools.LocationUtils;
import demo.bashellwang.androidutils.Tools.ScreenUtils;
import demo.bashellwang.androidutils.Tools.SensorUtils;
import demo.bashellwang.androidutils.Tools.VoiceUtils;

public class MainActivity extends Activity {
    private TextView mInfoText;
    private StringBuffer mInfo = new StringBuffer();
    private Runnable refreshTextInfo = new Runnable() {
        public void run() {
            mInfo.setLength(0);
            mInfo.append("getCpuName:" + CPUInfoUtils.getCpuName() + "\n")
                    .append("getAppStartTime" + DeviceInfoUtils.getAppStartTime() + "\n")


                    .append("getMaxCpuFreq:" + CPUInfoUtils.getMaxCpuFreq() + "\n")
                    .append("getMinCpuFreq:" + CPUInfoUtils.getMinCpuFreq() + "\n")
                    .append("getCurCpuFreq:" + CPUInfoUtils.getCurCpuFreq() + "\n")
                    .append("getCpuNum:" + CPUInfoUtils.getCpuNum() + "\n")
                    .append("getCPUSerial:" + CPUInfoUtils.getCpuSerial() + "\n")
                    .append("getCpuHardware:" + CPUInfoUtils.getCpuHardware() + "\n")

                    .append("externalMemoryAvailable:" + MemInfoUtils.externalMemoryAvailable() + "\n")
                    .append("getTotalExternalMemorySize:" + MemInfoUtils.formatFileSize(MemInfoUtils.getTotalExternalMemorySize(), false) + "\n")
                    .append("getAvailableExternalMemorySize:" + MemInfoUtils.formatFileSize(MemInfoUtils.getAvailableExternalMemorySize(), false) + "\n")
                    .append("getTotalInternalMemorySize:" + MemInfoUtils.formatFileSize(MemInfoUtils.getTotalInternalMemorySize(), false) + "\n")
                    .append("getAvailableInternalMemorySize:" + MemInfoUtils.formatFileSize(MemInfoUtils.getAvailableInternalMemorySize(), false) + "\n")

                    .append("getAvailableMemory:" + MemInfoUtils.formatFileSize(MemInfoUtils.getAvailableMemory(getApplicationContext()), false) + "\n")
                    .append("getTotalMemorySize:" + MemInfoUtils.formatFileSize(MemInfoUtils.getTotalMemorySize(getApplicationContext()), false) + "\n")

                    .append("GetBlueToothStatus:" + BlueToothUtils.GetBlueToothStatus() + "\n")
                    .append("GetBlueToothMacAddress:" + BlueToothUtils.GetBlueToothMacAddress() + "\n")

                    .append("getBattery:" + DeviceInfoUtils.getBattery() + "\n")
                    .append("getIMEI:" + DeviceInfoUtils.getIMEI(getApplicationContext()) + "\n")
                    .append("getIMSI:" + DeviceInfoUtils.getIMSI(getApplicationContext()) + "\n")
                    .append("getMacAddress:" + DeviceInfoUtils.getMacAddress(getApplicationContext()) + "\n")
                    .append("getICCID:" + DeviceInfoUtils.getICCID(getApplicationContext()) + "\n")
                    .append("getCurrentPhoneNum:" + DeviceInfoUtils.getCurrentPhoneNum(MainActivity.this) + "\n")
                    .append("getSerialNumber:" + DeviceInfoUtils.getSerialNumber() + "\n")
                    .append("getSupportedInstructionSets:" + DeviceInfoUtils.getSupportedInstructionSets() + "\n")


                    .append("getScreenWidth:" + ScreenUtils.getScreenWidth(getApplicationContext()) + "\n")
                    .append("getScreenHeight:" + ScreenUtils.getScreenHeight(getApplicationContext()) + "\n")
                    .append("getDeviceWidth:" + ScreenUtils.getDeviceWidth(getApplicationContext()) + "\n")
                    .append("getDeviceHeight:" + ScreenUtils.getDeviceHeight(getApplicationContext()) + "\n")
                    .append("getScreenDpi:" + ScreenUtils.getScreenDpi(getApplicationContext()) + "\n")
                    .append("getScreenState:" + ScreenUtils.getScreenState(getApplicationContext()) + "\n")


                    .append("getSensorListInfo:" + SensorUtils.getSensorListInfo(getApplicationContext()) + "\n")
                    .append("isAcceleSensorAvailable:" + SensorUtils.isAcceleSensorAvailable(getApplicationContext()) + "\n")
                    .append("isGravityAvailable:" + SensorUtils.isGravityAvailable(getApplicationContext()) + "\n")
                    .append("isGyroscopeAvailable:" + SensorUtils.isGyroscopeAvailable(getApplicationContext()) + "\n")
                    .append("isLightAvailable:" + SensorUtils.isLightAvailable(getApplicationContext()) + "\n")
                    .append("isLinearAcclerationAvailable:" + SensorUtils.isLinearAcclerationAvailable(getApplicationContext()) + "\n")
                    .append("isMagneticAvailable:" + SensorUtils.isMagneticAvailable(getApplicationContext()) + "\n")
                    .append("isPressureAvailable:" + SensorUtils.isPressureAvailable(getApplicationContext()) + "\n")
                    .append("isProximityAvailable:" + SensorUtils.isProximityAvailable(getApplicationContext()) + "\n")
                    .append("isTemperatureAvailable:" + SensorUtils.isTemperatureAvailable(getApplicationContext()) + "\n")


                    .append("getNetWorkType:" + NetWorkUtils.getNetWorkOperateName(getApplicationContext()) + "\n")
                    .append("getAirplaneMode:" + NetWorkUtils.getAirplaneMode(getApplicationContext()) + "\n")
                    .append("getSimOperatorName:" + NetWorkUtils.getSimOperatorName(getApplicationContext()) + "\n")
                    .append("getSimOperatorType:" + NetWorkUtils.getSimOperatorType(getApplicationContext()) + "\n")
                    .append("getWifiInterface:" + NetWorkUtils.getWifiInterface() + "\n")
                    .append("isWifiConnected:" + NetWorkUtils.isWifiConnected(getApplicationContext()) + "\n")
                    .append("isWifiEncrypted:" + NetWorkUtils.isWifiEncrypted(getApplicationContext()) + "\n")
                    .append("getWifiSSID:" + NetWorkUtils.getWifiSSID(getApplicationContext()) + "\n")
                    .append("getWifiBSSID:" + NetWorkUtils.getWifiBSSID(getApplicationContext()) + "\n")
                    .append("getWifiStrength:" + NetWorkUtils.getWifiStrength(getApplicationContext()) + "\n")
                    .append("getWifiNearBy:" + NetWorkUtils.getWifiNearBy(getApplicationContext()) + "\n")
                    .append("getIpAdress:" + NetWorkUtils.getIpAdress(getApplicationContext()) + "\n")
                    .append("getMobileMcc:" + NetWorkUtils.getMobileMcc(getApplicationContext()) + "\n")
                    .append("getMobileMnc:" + NetWorkUtils.getMobileMnc(getApplicationContext()) + "\n")
                    .append("getNetWorkOperateName:" + NetWorkUtils.getNetWorkOperateName(getApplicationContext()) + "\n")
                    .append("getNetworkType:" + NetWorkUtils.getNetWorkOperateName(getApplicationContext()) + "\n")
                    .append("getGsmSimState:" + NetWorkUtils.getGsmSimState(0) + "\n")

                    .append("getBoardName:" + DeviceInfoUtils.getBuildBoardName() + "\n")
                    .append("getBrandName:" + DeviceInfoUtils.getBuildBrandName() + "\n")
                    .append("getDeviceName:" + DeviceInfoUtils.getBuildDeviceName() + "\n")
                    .append("getBandVer:" + DeviceInfoUtils.getBandVer() + "\n")
                    .append("getDisplayId:" + DeviceInfoUtils.getBuildDisplayId() + "\n")
                    .append("getManufacturerName:" + DeviceInfoUtils.getBuildManufacturerName() + "\n")
                    .append("getModelName:" + DeviceInfoUtils.getBuildModelName() + "\n")
                    .append("getProductName:" + DeviceInfoUtils.getBuildProductName() + "\n")
                    .append("getTagsName:" + DeviceInfoUtils.getBuildTagsName() + "\n")
                    .append("getAndroidId:" + DeviceInfoUtils.getAndroidId(getApplicationContext()) + "\n")
                    .append("getKernelVersion:" + DeviceInfoUtils.getKernelVersion() + "\n")
                    .append("getSystemBootTime:" + DeviceInfoUtils.getSystemBootTimeStr() + "\n")
                    .append("getSystemBootTimeLength:" + DeviceInfoUtils.getSystemBootTimeLengthStr() + "\n")
                    .append("getOSName:" + DeviceInfoUtils.getOSName() + "\n")
                    .append("isDeviceRoot:" + DeviceInfoUtils.isDeviceRoot() + "\n")
                    .append("getKernelQemu:" + DeviceInfoUtils.getKernelQemu() + "\n")
                    .append("isEmulator:" + DeviceInfoUtils.isEmulator() + "\n")
                    .append("isDeviceRoot:" + DeviceInfoUtils.isDeviceRoot() + "\n")
                    .append("isConnected:" + DeviceInfoUtils.isUSBConnected(getApplicationContext()) + "\n")
                    .append("getLanguage:" + DeviceInfoUtils.getLanguage() + "\n")
                    .append("getTimeZone:" + DeviceInfoUtils.getTimeZone() + "\n")
                    .append("getBuildVersionIncremental:" + DeviceInfoUtils.getBuildVersionIncremental() + "\n")
                    .append("getBuildVersionRelease:" + DeviceInfoUtils.getBuildVersionRelease() + "\n")
                    .append("getBuildVersionSDK:" + DeviceInfoUtils.getBuildVersionSDK() + "\n")


//					.append("getAllApps:"+AppUtils.getAllApps(getApplicationContext())+"\n")
                    .append("getAllAppsName:" + AppUtils.getAllAppsInfo(getApplicationContext()) + "\n")


                    .append("getCellId:" + LocationUtils.getCellId(getApplicationContext()) + "\n")
                    .append("getLac:" + LocationUtils.getLac(getApplicationContext()) + "\n")
                    .append("isGpsOn:" + LocationUtils.isGpsOn(getApplicationContext()) + "\n")
                    .append("getLatitude:" + LocationUtils.getLatitudeLongitude(getApplicationContext(), GlobalUtilsConst.INT_LATITUDE) + "\n")
                    .append("getLongitude:" + LocationUtils.getLatitudeLongitude(getApplicationContext(), GlobalUtilsConst.INT_LONGITUDE) + "\n")
                    .append("getCellRssi:" + LocationUtils.getCellRssi(getApplicationContext()) + "\n")


                    .append("getSystemVoice:" + VoiceUtils.getCurrentSystemVoice(getApplicationContext()) + "\n")
                    .append("getVoiceMailNum:" + VoiceUtils.getVoiceMailNum(getApplicationContext()) + "\n")
                    .append("getVoiceMailAlphaTag:" + VoiceUtils.getVoiceMailAlphaTag(getApplicationContext()) + "\n")


            ;

            mInfoText.setText(mInfo.toString());
            Log.e("██:", mInfo.toString() + "\n");
//			mInfoText.postDelayed(refreshTextInfo, 450);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent in = new Intent(MainActivity.this, NetInfoService.class);
        in.setAction(NetInfoService.DEVICE_INFO_ACTION_SERVICE);
        startService(in);

        mInfoText = (TextView) findViewById(R.id.textView1);
        mInfoText.postDelayed(refreshTextInfo, 100);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (DeviceInfoUtils.firstTimeIn) {
            //记录应用启动的结束点
            DeviceInfoUtils.endTime = System.currentTimeMillis();
            long appTimeStr = DeviceInfoUtils.getAppStartTime();
            Toast.makeText(getApplicationContext(), appTimeStr + "", Toast.LENGTH_SHORT).show();
//            Log.e("onWindowFocusChanged", "start:" + DeviceInfoUtils.startTime);
//            Log.e("onWindowFocusChanged", "end:" + DeviceInfoUtils.endTime);
//            Log.e("onWindowFocusChanged", "appTime" + appTimeStr);
        }

    }

}
