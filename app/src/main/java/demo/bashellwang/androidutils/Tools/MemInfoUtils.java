package demo.bashellwang.androidutils.Tools;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;

public class MemInfoUtils {

	/**
	 * SDCARD是否可用
	 */
	public static boolean externalMemoryAvailable() {
		try {
			return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取手机内部剩余存储空间
	 * 
	 * @return
	 */
	public static long getAvailableInternalMemorySize() {
		try {
			File path = Environment.getDataDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GlobalUtilsConst.DEFAULT_INT;
	}

	/**
	 * 获取手机内部总的存储空间
	 *
	 * @return
	 */
	public static long getTotalInternalMemorySize() {
		try {
			File path = Environment.getDataDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GlobalUtilsConst.DEFAULT_INT;
	}

	/**
	 * 获取SDCARD剩余存储空间
	 *
	 * @return
	 */
	public static long getAvailableExternalMemorySize() {
		try {
			if (externalMemoryAvailable()) {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				return availableBlocks * blockSize;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GlobalUtilsConst.DEFAULT_INT;

	}

	/**
	 * 获取SDCARD总的存储空间
	 *
	 * @return
	 */
	public static long getTotalExternalMemorySize() {
		try {
			if (externalMemoryAvailable()) {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long totalBlocks = stat.getBlockCount();
				return totalBlocks * blockSize;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GlobalUtilsConst.DEFAULT_INT;
	}

	/**
	 * 获取系统总内存
	 *
	 * @param context
	 *            可传入应用程序上下文。
	 * @return 总内存大单位为B。
	 */
	public static long getTotalMemorySize(Context context) {
		String dir = "/proc/meminfo";
		try {
			FileReader fr = new FileReader(dir);
			BufferedReader br = new BufferedReader(fr, 2048);
			String memoryLine = br.readLine();
			String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
			br.close();
			return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024l;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GlobalUtilsConst.DEFAULT_INT;
	}

	/**
	 * 获取当前可用内存，返回数据以字节为单位。
	 *
	 * @param context
	 *            可传入应用程序上下文。
	 * @return 当前可用内存单位为B。
	 */
	public static long getAvailableMemory(Context context) {
		try {
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
			am.getMemoryInfo(memoryInfo);
			return memoryInfo.availMem;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GlobalUtilsConst.DEFAULT_INT;
	}

	private static DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
	private static DecimalFormat fileDecimalFormat = new DecimalFormat("#0.#");

	/**
	 * 单位换算
	 * 
	 * @param size
	 *            单位为B
	 * @param isInteger
	 *            是否返回取整的单位
	 * @return 转换后的单位
	 */
	public static String formatFileSize(long size, boolean isInteger) {
		DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
		String fileSizeString = "0M";
		if (size < 1024 && size > 0) {
			fileSizeString = df.format((double) size) + "B";
		} else if (size < 1024 * 1024) {
			fileSizeString = df.format((double) size / 1024) + "K";
		} else if (size < 1024 * 1024 * 1024) {
			fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
		} else {
			fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
		}
		return fileSizeString;
	}
}
