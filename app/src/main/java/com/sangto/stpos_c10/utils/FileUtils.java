package com.sangto.stpos_c10.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.sangto.stpos_c10.app.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.os.Environment.getExternalStorageState;


/**
 * Created by Jelly on 2018/6/27.
 */

public  class FileUtils {

    private static String canonicalPath;
    private static String sd;

    public static Boolean existsSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    public static void savePhoto(final Context context, final Bitmap bmp, final SaveResultCallback saveResultCallback) {

        final File sdDir = getSDPath();

        if (sdDir == null) {

            Toast.makeText(context, "设备自带的存储不可用", Toast.LENGTH_LONG).show();

            return;

        }

        new Thread(new Runnable() {

            @Override

            public void run() {

                File appDir = new File(sdDir, "小江驾考");

                if (!appDir.exists()) {

                    appDir.mkdir();

                }

                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置以当前时间格式为图片名称

                String fileName = df.format(new Date()) + ".png";

                File file = new File(appDir, fileName);

                try {

                    FileOutputStream fos = new FileOutputStream(file);

                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);

                    fos.flush();

                    fos.close();

                    saveResultCallback.onSavedSuccess();

                } catch (FileNotFoundException e) {

                    saveResultCallback.onSavedFailed();

                    e.printStackTrace();

                } catch (IOException e) {

                    saveResultCallback.onSavedFailed();

                    e.printStackTrace();

                }


                //保存图片后发送广播通知更新数据库

                Uri uri = Uri.fromFile(file);

                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

            }

        }).start();

    }


    public static File getSDPath() {

        File sdDir = null;

        boolean sdCardExist = getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在

        if (sdCardExist) {

            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            LogUtil.e("小江驾考", "外部存储可用..." + sdDir.toString());
        }

        return sdDir;


    }


    public interface SaveResultCallback {

        void onSavedSuccess();


        void onSavedFailed();

    }

    public static String getAppFileDownload() {
        return App.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    public static String getFileDir() {

//        //首先判断外部存储是否可用
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            File sd = new File(Environment.getExternalStorageDirectory().getPath());
//            LogUtil.e("qq", "sd = " + sd);//sd = /storage/emulated/0
//            return sd.canWrite();
//        } else {
//            return false;
//        }


        String absolute = App.getInstance().getFilesDir().getAbsolutePath();
        try {
            canonicalPath = App.getInstance().getFilesDir().getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = App.getInstance().getFilesDir().getName();
        String path = App.getInstance().getFilesDir().getPath();
        String sdDataAbsolute = App.getInstance().getExternalCacheDir().getAbsolutePath();
        if (Boolean.parseBoolean(Environment.getExternalStorageState())) {
            sd = Environment.getExternalStorageDirectory().getAbsolutePath();

            LogUtil.e(
                    "     sd  =  " + sd
            );
            return "";
        }
        sd = Environment.getExternalStorageDirectory().getAbsolutePath();

//        LogUtil.e(
//                "     sd  =  " + sd   // /storage/emulated/0
//        );
//        LogUtil.e("absolute  =  " + absolute   // /data/user/0/com.huazhong.car.drivingjiang/files
//                + "     canonicalPath  =  " + canonicalPath   // /data/data/com.huazhong.car.drivingjiang/files
//                + "     name  =  " + name   // files
//                + "     path  =  " + path    // /data/user/0/com.huazhong.car.drivingjiang/files
//                + "     sdDataAbsolute  =  " + sdDataAbsolute  //  /storage/emulated/0/Android/data/com.huazhong.car.drivingjiang/cache
//        );
//        LogUtil.e("---- " + App.getInstance().getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath());
//        LogUtil.e("---- " + App.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
//        LogUtil.e("---- " + App.getInstance().getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath());

        // /storage/emulated/0/Android/data/com.huazhong.car.drivingjiang/files/DCIM
        return "";
    }

    private void saveInRoot() {
        if (getSDPath() != null) {
            String fileName = getSDPath() + "/";
            File file = new File(fileName, "123111");//参数
            if (!file.mkdir()) {
                Toast.makeText(App.getInstance(), "目录已存在...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(App.getInstance(), "创建新目录...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public File getAlbumStorageDir(String fileName) {
        // Get the directory for the user's public pictures directory.
        //在 Environment.DIRECTORY_DOWNLOADS 目录下创建名为fileName的文件夹 刷新查看
        File file =
                new File(Environment.getExternalStoragePublicDirectory(Environment.
                        DIRECTORY_DOWNLOADS), fileName);//参数2是文件名称
        //mkdirs()可以创建多级目录
        //mkdir()只能创建一级目录
        if (!file.mkdir()) {
            LogUtil.e("Directory not created");
            Toast.makeText(App.getInstance(), "目录已存在...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(App.getInstance(), "创建新目录...", Toast.LENGTH_SHORT).show();
        }
        return file;
    }


    /**
     * 读取asset资源文件下的json文件并返回String
     *
     * @param fileName
     * @param context
     * @return
     */
    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.e(stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * 读取指定路径文件下的json文件并返回String
     *
     * @param path
     * @param context
     * @return
     */
    public static String getSDJson(String path, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File videoFile = new File(path);
            if (videoFile.exists()) {
                FileInputStream fis = new FileInputStream(path);
                //通过管理器打开文件并读取
                BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.e(stringBuilder.toString());
        return stringBuilder.toString();
    }


    // 缓存文件路径
    private static final String CACHE_DIR = Environment.getExternalStorageDirectory() + "/my_caches/images";// 缓存文件夹

    /**
     * 判断sdcard是否挂载
     *
     * @return
     */
    public static boolean isMounted() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取sdcard的根目录
     *
     * @return
     */
    public static String getSDCARDDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 存储图片一
     *
     * @param url  ： 图片的http网络地址
     * @param data
     * @throws IOException
     */
    public static void saveImage(String url, byte[] data) throws IOException {
        // 1. 判断是否有sdcard
        if (!isMounted()) {
            return;
        }
        // 2. 判断是否有缓存的文件夹
        File dir = new File(CACHE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();// 多层文件夹
        }
        // 3. 存储图片到sdcard
        File file = new File(dir, getFilename(url));
        FileOutputStream fos = new FileOutputStream(file);

        fos.write(data);
        fos.close();
    }

    /**
     * 保存图片二
     *
     * @param url    : 图片的http网络地址
     * @param bitmap
     * @throws IOException
     */
    public static void saveImage(String url, Bitmap bitmap) throws IOException {
        // 1. 判断是否有sdcard
        if (!isMounted()) {
            return;
        }
        // 2. 判断是否有缓存的文件夹
        File dir = new File(CACHE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 3. 存储图片到sdcard
        File file = new File(dir, getFilename(url));
        FileOutputStream fos = new FileOutputStream(file);

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
    }

    /**
     * 获取图片名
     *
     * @param url
     * @return
     */
    public static String getFilename(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 读图片
     *
     * @param url
     * @return
     */
    public static Bitmap readImage(String url) {
        // 判断是否有sdcard
        if (!isMounted()) {
            return null;
        }
        File file = new File(CACHE_DIR, getFilename(url));
        if (file.exists()) {
            // file->bitmap
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }

    /**
     * 清空缓存目录
     */
    public void clearCaches() {
        File dir = new File(CACHE_DIR);
        File[] allfiles = dir.listFiles();
        for (File file : allfiles) {
            file.delete();
        }
    }


    private static final String[][] MIME_MapTable =
            {
                    // {后缀名， MIME类型}
                    {".3gp", "video/3gpp"}, {".apk", "application/vnd.android.package-archive"},
                    {".asf", "video/x-ms-asf"}, {".avi", "video/x-msvideo"},
                    {".bin", "application/octet-stream"}, {".bmp", "image/bmp"}, {".c", "text/plain"},
                    {".class", "application/octet-stream"}, {".conf", "text/plain"}, {".cpp", "text/plain"},
                    {".doc", "application/msword"},
                    {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
                    {".xls", "application/vnd.ms-excel"},
                    {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
                    {".exe", "application/octet-stream"}, {".gif", "image/gif"},
                    {".gtar", "application/x-gtar"}, {".gz", "application/x-gzip"}, {".h", "text/plain"},
                    {".htm", "text/html"}, {".html", "text/html"}, {".jar", "application/java-archive"},
                    {".java", "text/plain"}, {".jpeg", "image/jpeg"}, {".jpg", "image/jpeg"},
                    {".js", "application/x-javascript"}, {".log", "text/plain"}, {".m3u", "audio/x-mpegurl"},
                    {".m4a", "audio/mp4a-latm"}, {".m4b", "audio/mp4a-latm"}, {".m4p", "audio/mp4a-latm"},
                    {".m4u", "video/vnd.mpegurl"}, {".m4v", "video/x-m4v"}, {".mov", "video/quicktime"},
                    {".mp2", "audio/x-mpeg"}, {".mp3", "audio/x-mpeg"}, {".mp4", "video/mp4"},
                    {".mpc", "application/vnd.mpohun.certificate"}, {".mpe", "video/mpeg"},
                    {".mpeg", "video/mpeg"}, {".mpg", "video/mpeg"}, {".mpg4", "video/mp4"},
                    {".mpga", "audio/mpeg"}, {".msg", "application/vnd.ms-outlook"}, {".ogg", "audio/ogg"},
                    {".pdf", "application/pdf"}, {".png", "image/png"},
                    {".pps", "application/vnd.ms-powerpoint"}, {".ppt", "application/vnd.ms-powerpoint"},
                    {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
                    {".prop", "text/plain"}, {".rc", "text/plain"}, {".rmvb", "audio/x-pn-realaudio"},
                    {".rtf", "application/rtf"}, {".sh", "text/plain"}, {".tar", "application/x-tar"},
                    {".tgz", "application/x-compressed"}, {".txt", "text/plain"}, {".wav", "audio/x-wav"},
                    {".wma", "audio/x-ms-wma"}, {".wmv", "audio/x-ms-wmv"},
                    {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"},
                    {".z", "application/x-compress"}, {".zip", "application/x-zip-compressed"}, {"", "*/*"}
            };

    /**
     * 根据文件后缀名获得对应的MIME类型
     *
     * @param file 文件对象
     */
    public static String getMIMEType(File file) {
        String type = "*/*";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf("."); // 获取后缀名前的分隔符"."在fileName中的位置
        if (dotIndex < 0) {
            return type;
        }

        String end = fileName.substring(dotIndex, fileName.length()).toLowerCase(Locale.getDefault()); // 获取文件的后缀名
        if (end.length() == 0) {
            return type;
        }

        // 在MIME和文件类型的匹配表中找到对应的MIME类型
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0])) {
                type = MIME_MapTable[i][1];
            }
        }
        return type;
    }

    /**
     * 创建文件
     *
     * @param path     文件所在目录的目录名，如/java/test/0.txt,要在当前目录下创建一个文件名为1.txt的文件，<br>
     *                 则path为/java/test，fileName为1.txt
     * @param fileName 文件名
     * @return 文件新建成功则返回true
     */
    public static boolean createFile(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            LogUtil.e("新建文件失败：file.exist()=" + file.exists());
            return false;
        } else {
            try {
                boolean isCreated = file.createNewFile();
                return isCreated;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 删除单个文件
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    public static boolean deleteFile(File file) {
        if (file.exists()) {
            boolean isDeleted = file.delete();
            LogUtil.e(file.getName() + "删除结果：" + isDeleted);
            return isDeleted;
        } else {
            LogUtil.e("文件删除失败：文件不存在！");
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param path     文件所在路径名
     * @param fileName 文件名
     * @return 删除成功则返回true
     */
    public static boolean deleteFile(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            return isDeleted;
        } else {
            return false;
        }
    }

    /**
     * 复制文件
     *
     * @param srcPath 源文件绝对路径
     * @param destDir 目标文件所在目录
     * @return boolean
     */
    public static boolean copyFile(String srcPath, String destDir) {
        boolean flag = false;
        File srcFile = new File(srcPath); // 源文件
        if (!srcFile.exists()) {
            // 源文件不存在
            UIUtil.toast("源文件不存在");
            return false;
        }
        // 获取待复制文件的文件名
        String fileName = srcPath.substring(srcPath.lastIndexOf(File.separator));
        String destPath = destDir + fileName;
        if (destPath.equals(srcPath)) {
            // 源文件路径和目标文件路径重复
            UIUtil.toast("源文件路径和目标文件路径重复!");
            return false;
        }
        File destFile = new File(destPath); // 目标文件
        if (destFile.exists() && destFile.isFile()) {
            // 该路径下已经有一个同名文件
            UIUtil.toast("目标目录下已有同名文件!");
            return false;
        }
        File destFileDir = new File(destDir);
        destFileDir.mkdirs();
        try {
            FileInputStream fis = new FileInputStream(srcPath);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buf = new byte[1024];
            int c;
            while ((c = fis.read(buf)) != -1) {
                fos.write(buf, 0, c);
            }
            fis.close();
            fos.close();

            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flag) {
            UIUtil.toast("复制文件成功!");
        }
        return flag;
    }

    /**
     * 根据文件名获得文件的扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名（不带点）
     */
    public static String getFileSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index + 1, fileName.length());
        return suffix;
    }

    /**
     * 重命名文件
     *
     * @param oldPath 旧文件的绝对路径
     * @param newPath 新文件的绝对路径
     * @return 文件重命名成功则返回true
     */
    public static boolean renameTo(String oldPath, String newPath) {
        if (oldPath.equals(newPath)) {
            LogUtil.e("文件重命名失败：新旧文件名绝对路径相同！");
            return false;
        }
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);

        boolean isSuccess = oldFile.renameTo(newFile);
        LogUtil.e("文件重命名是否成功：" + isSuccess);
        return isSuccess;
    }

    /**
     * 重命名文件
     *
     * @param oldFile 旧文件对象
     * @param newFile 新文件对象
     * @return 文件重命名成功则返回true
     */
    public static boolean renameTo(File oldFile, File newFile) {
        if (oldFile.equals(newFile)) {
            LogUtil.e("文件重命名失败：旧文件对象和新文件对象相同！");
            return false;
        }
        boolean isSuccess = oldFile.renameTo(newFile);
        LogUtil.e("文件重命名是否成功：" + isSuccess);
        return isSuccess;
    }

    /**
     * 重命名文件
     *
     * @param oldFile 旧文件对象，File类型
     * @param newName 新文件的文件名，String类型
     * @return 重命名成功则返回true
     */
    public static boolean renameTo(File oldFile, String newName) {
        File newFile = new File(oldFile.getParentFile() + File.separator + newName);
        boolean flag = oldFile.renameTo(newFile);
        System.out.println(flag);
        return flag;
    }

    /**
     * 计算某个文件的大小
     *
     * @param file 文件对象
     * @return 文件大小，如果file不是文件，则返回-1
     */
    public static long getFileSize(File file) {
        if (file.isFile()) {
            return file.length();
        } else {
            return -1;
        }
    }

    /**
     * 计算某个文件的大小
     *
     * @param path 文件的绝对路径
     * @return
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        long size = file.length();
        return size;
    }

    /**
     * 文件大小的格式化
     *
     * @param size 文件大小，单位为byte
     * @return 文件大小格式化后的文本
     */
    public static String formatSize(long size) {
        DecimalFormat df = new DecimalFormat("####.00");
        if (size < 1024) // 小于1KB
        {
            return size + "Byte";
        } else if (size < 1024 * 1024) // 小于1MB
        {
            float kSize = size / 1024f;
            return df.format(kSize) + "KB";
        } else if (size < 1024 * 1024 * 1024) // 小于1GB
        {
            float mSize = size / 1024f / 1024f;
            return df.format(mSize) + "MB";
        } else if (size < 1024L * 1024L * 1024L * 1024L) // 小于1TB
        {
            float gSize = size / 1024f / 1024f / 1024f;
            return df.format(gSize) + "GB";
        } else {
            return "size: error";
        }
    }

    /**
     * 格式化文件最后修改时间字符串
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", Locale.getDefault());
        String formatedTime = sdf.format(date);
        return formatedTime;
    }

    /**
     * 获取某个路径下的文件列表
     *
     * @param path 文件路径
     * @return 文件列表File[] files
     */
    public static File[] getFileList(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                return files;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取某个目录下的文件列表
     *
     * @param directory 目录
     * @return 文件列表File[] files
     */
    public static File[] getFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            return files;
        } else {
            return null;
        }
    }

    /**
     * 获得根目录文件列表
     *
     * @param showHidden 是否显示隐藏文件
     * @return
     */
    public static List<File> getSDCardFileList(boolean showHidden) {
        List<File> list = new ArrayList<>();
        File files[];
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File extDir = Environment.getExternalStorageDirectory();
            files = extDir.listFiles();
            for (File file : files) {
                list.add(file);
            }
            if (showHidden) {
                //
            } else {
                for (int i = 0; i < list.size(); i++) {
                    File file = list.get(i);
                    if (file.isHidden() || file.getName().startsWith(".")) {
                        list.remove(file);
                    }
                }
            }
        } else {
            UIUtil.toast("SD卡未挂载！");
        }
        return list;
    }

    /**
     * 新建目录
     *
     * @param path 目录的绝对路径
     * @return 创建成功则返回true
     */
    public static boolean createFolder(String path) {
        File file = new File(path);
        boolean isCreated = file.mkdir();
        return isCreated;
    }

    /**
     * 新建目录
     *
     * @param file
     * @return
     */
    public static boolean createFolder(File file) {
        boolean isCreated = file.mkdir();
        return isCreated;
    }

    /**
     * 删除文件夹及其包含的所有文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFolder(File file) {
        boolean flag = false;
        File files[] = file.listFiles();
        if (files != null && files.length >= 0) // 目录下存在文件列表
        {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.isFile()) {
                    // 删除子文件
                    flag = deleteFile(f);
                    if (flag == false) {
                        return flag;
                    }
                } else {
                    // 删除子目录
                    flag = deleteFolder(f);
                    if (flag == false) {
                        return flag;
                    }
                }
            }
        }
        flag = file.delete();
        if (flag == false) {
            return flag;
        } else {
            return true;
        }
    }

    /**
     * 复制目录
     *
     * @param srcPath 源文件夹路径
     * @param destDir 目标文件夹所在目录
     * @return 复制成功则返回true
     */
    public static boolean copyFolder(String srcPath, String destDir) {
        UIUtil.toast("复制文件夹开始!");
        boolean flag = false;

        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            // 源文件夹不存在
            UIUtil.toast("源文件夹不存在");
            return false;
        }
        String dirName = getDirName(srcPath); // 获得待复制的文件夹的名字，比如待复制的文件夹为"E://dir"则获取的名字为"dir"

        String destPath = destDir + File.separator + dirName; // 目标文件夹的完整路径
        // UIUtil.toast("目标文件夹的完整路径为：" + destPath);
        if (destPath.equals(srcPath)) {
            UIUtil.toast("目标文件夹与源文件夹重复");
            return false;
        }
        File destDirFile = new File(destPath);
        if (destDirFile.exists()) {
            // 目标位置有一个同名文件夹
            UIUtil.toast("目标位置已有同名文件夹!");
            return false;
        }
        destDirFile.mkdirs(); // 生成目录

        File[] files = srcFile.listFiles(); // 获取源文件夹下的子文件和子文件夹
        if (files.length == 0) {
            // 如果源文件夹为空目录则直接设置flag为true，这一步非常隐蔽，debug了很久
            flag = true;
        } else {
            for (File temp : files) {
                if (temp.isFile()) {
                    // 文件
                    flag = copyFile(temp.getAbsolutePath(), destPath);
                } else if (temp.isDirectory()) {
                    // 文件夹
                    flag = copyFolder(temp.getAbsolutePath(), destPath);
                }
                if (!flag) {
                    break;
                }
            }
        }
        if (flag) {
            UIUtil.toast("复制文件夹成功!");
        }
        return flag;
    }

    /**
     * 获取待复制文件夹的文件夹名
     *
     * @param dir
     * @return String
     */
    public static String getDirName(String dir) {
        if (dir.endsWith(File.separator)) {
            // 如果文件夹路径以"//"结尾，则先去除末尾的"//"
            dir = dir.substring(0, dir.lastIndexOf(File.separator));
        }
        return dir.substring(dir.lastIndexOf(File.separator) + 1);
    }

    /**
     * 计算某个目录包含的文件数量
     *
     * @param directory
     * @return
     */
    public static int getFileCount(File directory) {
        File[] files = directory.listFiles();
        int count = files.length;
        return count;
    }

    /**
     * 计算某个路径下所包含的文件数量
     *
     * @param path
     * @return
     */
    public static int getFileCount(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        int count = files.length;
        return count;
    }

    /**
     * 计算某个目录的大小
     *
     * @param directory
     * @return
     */
    public static long getFolderSize(File directory) {
        File[] files = directory.listFiles();
        if (files != null && files.length >= 0) {
            long size = 0;
            for (File f : files) {
                if (f.isFile()) {
                    // 获得子文件的大小
                    size = size + getFileSize(f);
                } else {
                    // 获得子目录的大小
                    size = size + getFolderSize(f);
                }
            }
            return size;
        }
        return -1;
    }

    /**
     * 获得某个文件或目录的大小
     *
     * @param file
     * @return
     */
    public static long getFileOrFolderSize(File file) {
        long size = 0;
        if (file.isDirectory()) {
            size = getFolderSize(file);
        } else {
            size = getFileSize(file);
        }
        return size;
    }

    /**
     * @param 字节大小
     */
    private static final String UNIT_B = "B";
    private static final String UNIT_KB = "KB";
    private static final String UNIT_MB = "MB";
    private static final String UNIT_GB = "GB";
    private static final String UNIT_TB = "TB";
    private static final int UNIT_INTERVAL = 1024;
    private static final double ROUNDING_OFF = 0.005;
    private static final int DECIMAL_NUMBER = 100;

    public static String sizeToString(long size) {
        String unit = UNIT_B;
        if (size < DECIMAL_NUMBER) {
            return Long.toString(size) + " " + unit;
        }

        unit = UNIT_KB;
        double sizeDouble = (double) size / (double) UNIT_INTERVAL;
        if (sizeDouble > UNIT_INTERVAL) {
            sizeDouble = (double) sizeDouble / (double) UNIT_INTERVAL;
            unit = UNIT_MB;
        }
        if (sizeDouble > UNIT_INTERVAL) {
            sizeDouble = (double) sizeDouble / (double) UNIT_INTERVAL;
            unit = UNIT_GB;
        }
        if (sizeDouble > UNIT_INTERVAL) {
            sizeDouble = (double) sizeDouble / (double) UNIT_INTERVAL;
            unit = UNIT_TB;
        }

        //  0.005 用来舍入.精确到小数点后两位
        long sizeInt = (long) ((sizeDouble + ROUNDING_OFF) * DECIMAL_NUMBER);
        double formatedSize = ((double) sizeInt) / DECIMAL_NUMBER;

        if (formatedSize == 0) {
            return "0" + " " + unit;
        } else {
            return Double.toString(formatedSize) + " " + unit;
        }
    }


    /**
     * 拷贝一个文件,srcFile源文件，destFile目标文件
     *
     * @param srcFile
     * @param destFile
     * @return
     * @throws IOException
     */
    public boolean copyFileTo(File srcFile, File destFile) throws IOException {

        if (srcFile.isDirectory() || destFile.isDirectory())
            return false;// 判断是否是文件
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        int readLen = 0;
        byte[] buf = new byte[1024];
        while ((readLen = fis.read(buf)) != -1) {
            fos.write(buf, 0, readLen);
        }
        fos.flush();
        fos.close();
        fis.close();
        return true;
    }

    /**
     * 拷贝目录下的所有文件到指定目录
     *
     * @param srcDir
     * @param destDir
     * @return
     * @throws IOException
     */

    public boolean copyFilesTo(File srcDir, File destDir) throws IOException {

        if (!srcDir.isDirectory() || !destDir.isDirectory())
            return false;// 判断是否是目录
        if (!destDir.exists())
            return false;// 判断目标目录是否存在
        File[] srcFiles = srcDir.listFiles();
        for (int i = 0; i < srcFiles.length; i++) {
            if (srcFiles[i].isFile()) {
                // 获得目标文件
                File destFile = new File(destDir.getPath() + "//"
                        + srcFiles[i].getName());
                copyFileTo(srcFiles[i], destFile);
            } else if (srcFiles[i].isDirectory()) {
                File theDestDir = new File(destDir.getPath() + "//"
                        + srcFiles[i].getName());
                copyFilesTo(srcFiles[i], theDestDir);
            }
        }
        return true;
    }

    /**
     * 移动一个文件
     *
     * @param srcFile
     * @param destFile
     * @return
     * @throws IOException
     */

    public boolean moveFileTo(File srcFile, File destFile) throws IOException {

        boolean is_copy = copyFileTo(srcFile, destFile);

        if (!is_copy)
            return false;
        deleteFile(srcFile);
        return true;
    }


}
