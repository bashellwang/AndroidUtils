package demo.bashellwang.androidutils.Tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.json.JSONArray;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取app相关信息
 *
 * @author bashellwang
 */
public class AppUtils {

    /**
     * 获取所有已安装的非系统App 列表
     */
    public static List<AppInfo> getAllApps(Context context) {
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
        try {
            PackageManager pm = context.getPackageManager();
            // 获取已安装的所有app信息,携带签名信息
            List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
            if (installedPackages != null && installedPackages.size() > 0) {
                byte[] signByte = null;
                String publicKeyStr = "";
                for (PackageInfo pi : installedPackages) {
                    // 非系统应用
                    if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        AppInfo appInfo = new AppInfo();
                        appInfo.setAppName(pi.applicationInfo.loadLabel(pm).toString());
                        appInfo.setAppVersionName(pi.versionName);
                        // 获取公钥签名
                        if (pi.signatures != null && pi.signatures.length != 0) {
                            signByte = pi.signatures[0].toByteArray();
                            // // 获取公钥
                            publicKeyStr = getPublicKey(signByte);
                            appInfo.setAppPublicKey(publicKeyStr);
                        }
                        appList.add(appInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appList;
    }

    /**
     * 获取app签名公钥
     *
     * @param signature
     * @return
     */
    public static String getPublicKey(byte[] signature) {
        try {

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(signature));
            String publickey = cert.getPublicKey().toString();
            return publickey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtilsConst.DEFAULT_STR;
    }

    /**
     * 获取所有安装的app名称（非系统app）
     *
     * @param context
     * @return
     */
    public static JSONArray getAllAppsInfo(Context context) {
        ArrayList<AppInfo> appList = (ArrayList<AppInfo>) getAllApps(context);
        JSONArray jsonArray = new JSONArray();
        if (appList == null || appList.size() == 0) {
            return jsonArray;
        }
        for (AppInfo appInfo : appList) {
            jsonArray.put(appInfo.getJsonObj());
        }
        return jsonArray;
    }

}
