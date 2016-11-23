package demo.bashellwang.androidutils.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.regex.Pattern;

/**
 * 获取CPU信息工具类 主要是通过读取 /proc/cpuinfo、/sys/devices/system/cpu/cpu0/cpufreq/
 * 等目录及文件来获取
 * 
 * @author bashellwang
 *
 */
public class CPUInfoUtils {

	/**
	 * 获取CPU名字
	 * 
	 * @return
	 */
	public static String getCpuName() {
		try {
			FileReader fr = new FileReader("/proc/cpuinfo");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			return array[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GlobalUtilsConst.DEFAULT_STR;
	}

	/**
	 * 获取CPU序列号
	 *
	 * @return CPU序列号(16位)
	 */
	public static String getCpuSerial() {
		String str = "", strCPU = "", cpuAddress = GlobalUtilsConst.DEFAULT_STR;
		try {
			// 读取CPU信息
			Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			// 查找CPU序列号
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					// 查找到序列号所在行
					if (str.indexOf("Serial") > -1) {
						// 提取序列号
						strCPU = str.substring(str.indexOf(":") + 1, str.length());
						// 去空格
						cpuAddress = strCPU.trim();
						break;
					}
				} else {
					// 文件结尾
					break;
				}
			}
		} catch (Exception ex) {
			// 赋予默认值
			ex.printStackTrace();
		}
		return cpuAddress;
	}

	/**
	 * 获取CPU硬件型号
	 *
	 * @return CPU序列号(16位)
	 */
	public static String getCpuHardware() {
		String str = "", strCPU = "", cpuHardware = GlobalUtilsConst.DEFAULT_STR;
		try {
			// 读取CPU信息
			Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			// 查找CPU序列号
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					// 查找到序列号所在行
					if (str.indexOf("Hardware") > -1) {
						// 提取序列号
						strCPU = str.substring(str.indexOf(":") + 1, str.length());
						// 去空格
						cpuHardware = strCPU.trim();
						break;
					}
				} else {
					// 文件结尾
					break;
				}
			}
		} catch (Exception ex) {
			// 赋予默认值
			ex.printStackTrace();
		}
		return cpuHardware;
	}

	/**
	 * 获取CPU核数
	 *
	 * @return
	 */
	public static int getCpuNum() {
		// Private Class to display only CPU devices in the directory listing
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				// Check if filename is "cpu", followed by a single digit number
				if (Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				return false;
			}
		}

		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new CpuFilter());
			return files.length;
		} catch (Exception e) {
			// Default to return 1 core
			return 1;
		}
	}

	/**
	 * 获取CPU最大频率 "/system/bin/cat" 命令行
	 * "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
	 *
	 * @return
	 */
	public static String getMaxCpuFreq() {
		String result = "";
		ProcessBuilder cmd;
		try {
			String[] args = { "/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[24];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			result = GlobalUtilsConst.DEFAULT_STR;
		}
		return result.trim();
	}

	/**
	 * 获取CPU最小频率
	 *
	 * @return
	 */
	public static String getMinCpuFreq() {
		String result = "";
		ProcessBuilder cmd;
		try {
			String[] args = { "/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[24];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			result = GlobalUtilsConst.DEFAULT_STR;
		}
		return result.trim();
	}

	/**
	 * 实时获取CPU当前频率
	 *
	 * @return
	 */
	public static String getCurCpuFreq() {
		String result = GlobalUtilsConst.DEFAULT_STR;
		try {
			FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
			BufferedReader br = new BufferedReader(fr);
			result = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}

}
