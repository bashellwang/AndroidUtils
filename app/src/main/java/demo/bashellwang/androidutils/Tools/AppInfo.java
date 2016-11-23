package demo.bashellwang.androidutils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

public class AppInfo {
    private String appName;
    private String appVersionName;
    private String appPublicKey;

    public AppInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public String getAppPublicKey() {
        return appPublicKey;
    }

    public void setAppPublicKey(String appPublicKey) {
        this.appPublicKey = appPublicKey;
    }

    /**
     * 将应用信息实体类转为 JSONObject
     *
     * @return
     */
    public JSONObject getJsonObj() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", this.appName);
            obj.put("version", this.appVersionName);
            obj.put("publickey", this.appPublicKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
