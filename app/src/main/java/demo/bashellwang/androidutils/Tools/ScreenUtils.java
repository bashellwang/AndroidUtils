package demo.bashellwang.androidutils.Tools;

import android.content.Context;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtils {

	/**
	 * 获取屏幕的高度px
	 *
	 * @param context
	 *            上下文
	 * @return 屏幕高px
	 */
	public static int getScreenHeight(Context context) {
		try {
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
			if (windowManager != null) {
				windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
				return dm.heightPixels;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GlobalUtilsConst.DEFAULT_INT;
	}

	/**
	 * 获取屏幕的宽度px
	 *
	 * @param context
	 *            上下文
	 * @return 屏幕宽px
	 */
	public static int getScreenWidth(Context context) {
		try {
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
			if (windowManager != null) {
				windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
				return dm.widthPixels;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GlobalUtilsConst.DEFAULT_INT;
	}

	/**
	 * 获取屏幕密度dpi
	 *
	 * @param context
	 *            上下文
	 * @return 屏幕宽px
	 */
	public static int getScreenDpi(Context context) {
		try {
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
			if (windowManager != null) {
				windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
				return dm.densityDpi;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GlobalUtilsConst.DEFAULT_INT;

	}

	/**
	 * 获取默认分辨率 宽
	 *
	 * @param context
	 * @return
	 */
	public static int getDeviceWidth(Context context) {
		try {
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display display = windowManager.getDefaultDisplay();
			return display != null ? display.getWidth() : 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return GlobalUtilsConst.DEFAULT_INT;
		}
	}

	/**
	 * 获取屏幕分辨率
	 * @param context
	 * @return
     */
	public static String getScreenResolution(Context context){
		StringBuffer sb = new StringBuffer();
		sb.append(getDeviceWidth(context)).append(" * ").append(getDeviceHeight(context));
		return sb.toString();
	}

	/**
	 * 获取默认分辨率 高
	 *
	 * @param context
	 * @return
	 */
	public static int getDeviceHeight(Context context) {
		try {
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display display = windowManager.getDefaultDisplay();
			return display != null ? display.getHeight() : 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return GlobalUtilsConst.DEFAULT_INT;
		}
	}

	/**
	 * 获取screen状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getScreenState(Context context) {
		try {
			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			return pm.isScreenOn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
