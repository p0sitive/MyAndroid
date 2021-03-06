package com.garfield.baselib.utils.file;

import android.content.Context;
import android.os.Environment;

import com.garfield.baselib.Cache;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by gaowei3 on 2016/10/21.
 */

public class DirectoryUtils {

    /**
     * 以自己的包名为目录，下面的cache/image/
     */
    public static File getOwnImageCacheDirectory() {
        return getOwnCacheDirectory("cache/image/");
    }

    /**
     * 以自己的包名为目录，不要"/"
     */
    public static File getOwnCacheDirectory(String cacheDir) {
        return getCacheDirectory(Cache.getContext().getPackageName() + "/" + cacheDir);
    }

    /**
     * 先使用外置(根目录, 任意位置)，没有就使用内置(应用内部)
     * 会自动创建
     */
    public static File getCacheDirectory(String cacheDir) {
        File appCacheDir = null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(Cache.getContext())) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }
        if(appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = Cache.getContext().getCacheDir();
        }
        return appCacheDir;
    }

    /**
     * 卸载后自动被删除
     */
    public static File getDiskCacheDir(String uniqueName) {
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable && hasExternalStoragePermission(Cache.getContext())) {
            cachePath = Cache.getContext().getExternalCacheDir().getPath();
        } else {
            cachePath = Cache.getContext().getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }


    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }
}

