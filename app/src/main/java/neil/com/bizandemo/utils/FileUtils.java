package neil.com.bizandemo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件工具类
 * Created by neil on 2018/3/13 0013.
 */

public class FileUtils {

    /**
     * 创建根缓存目录
     */
    public static String createRootPath(Context context) {
        String cacheRootPath = "";
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/cache
            cacheRootPath = context.getExternalCacheDir().getPath();
        } else {
            // /data/data/<application package>/cache
            cacheRootPath = context.getCacheDir().getPath();
        }
        return cacheRootPath;
    }

    /**
     * 打开Asset下的文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static InputStream openAssetFile(Context context, String fileName) {
        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            is = am.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }



    /**
     * 判断是否有SD卡
     */
    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
