package demo.bashellwang.androidutils.Tools;

/**
 * 蓝牙工具类
 * android.permission.BLUETOOTH
 * android.permission.BLUETOOTH_ADMIN
 * @author bashellwang
 *
 */
public class BlueToothUtils {

	/**
	 * 获取蓝牙mac地址
	 * @return
	 */
	public static String GetBlueToothMacAddress() {
//		try {
//			BluetoothAdapter btAda = BluetoothAdapter.getDefaultAdapter();
//			return btAda.getAddress();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return GlobalUtilsConst.DEFAULT_STR;
	}
	/**
	 * 获取蓝牙状态信息
	 * @return
	 */
	public static boolean GetBlueToothStatus() {
//		try {
//			BluetoothAdapter btAda = BluetoothAdapter.getDefaultAdapter();
//			return btAda.isEnabled();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return false;
	}
	
}
