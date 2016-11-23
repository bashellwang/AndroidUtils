package demo.bashellwang.androidutils.Tools;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.TelephonyManager;


/**
 * 声音工具类 http://www.eoeandroid.com/thread-8133-1-1.html?_dsign=83e754af
 *
 * @author bashellwang
 */
public class VoiceUtils {

    /**
     * 获取当前系统声音大小
     *
     * @param context
     * @return
     */
    public static int getCurrentSystemVoice(Context context) {
        try {
            AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            return (mAudioManager != null) ? mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM)
                    : GlobalUtilsConst.DEFAULT_INT;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_INT;
        }
    }

    /**
     * 获取系统最大声音
     *
     * @param context
     * @return
     */
    public static int getMaxSystemVoice(Context context) {
        try {
            AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            return (mAudioManager != null) ? mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM)
                    : GlobalUtilsConst.DEFAULT_INT;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_INT;
        }
    }

    /**
     * 获取语音邮箱号码
     *
     * @return
     */
    public static String getVoiceMailNum(Context context) {
        try {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return (mTelephonyManager != null) ? mTelephonyManager.getVoiceMailNumber() : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }

    /**
     * 获取语音邮箱相关标签，及标识码
     *
     * @return
     */
    public static String getVoiceMailAlphaTag(Context context) {
        try {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return (mTelephonyManager != null) ? mTelephonyManager.getVoiceMailAlphaTag()
                    : GlobalUtilsConst.DEFAULT_STR;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return GlobalUtilsConst.DEFAULT_STR;
        }
    }
}
