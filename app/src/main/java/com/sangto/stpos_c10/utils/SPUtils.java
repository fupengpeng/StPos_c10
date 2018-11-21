package com.sangto.stpos_c10.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.sangto.stpos_c10.app.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "XiaoJiangJiaKao";
    //  user id
    public static final String USER_ID = "userId";
    //
    public static final String IS_FIRST_IN = "ISFIRSTIN";
    //   汇金用这个    用户账号
    public static final String USER_LONIN_NAME ="user_lonin_name" ;
    // 是否打开消息通知
    public static final String MSG_OPEN = "msg_open";

    // 极光推送设备id
    public static final String REGISTRATION_ID = "registrationid";

    // 新版本更新是否提示过
    public static final String IS_UPDATE_HINT = "IS_UPDATE_HINT";
    // 更新任务id
    public static final String DOWNLOAD_APK_TASK_ID = "DOWNLOAD_APK_TASK_ID";
    // 版本更新----update
    public static final String VERSION_UPDATE_UPDATE = "VERSION_UPDATE_UPDATE";
    // 版本更新----APKurl
    public static final String VERSION_UPDATE_APKURL = "VERSION_UPDATE_APKURL";
    // 版本更新----newVersionName
    public static final String VERSION_UPDATE_NEW_VERSION_NAME = "VERSION_UPDATE_NEW_VERSION_NAME";


    // 顺序练习错误题目数量----科目一
    public static final String EXERCISE_DATA_NUM_WRONG_ONE = "EXERCISE_DATA_NUM_WRONG_ONE";
    // 顺序练习正确题目数量----科目一
    public static final String EXERCISE_DATA_NUM_RIGHT_ONE = "EXERCISE_DATA_NUM_RIGHT_ONE";

    // 随机练习错误题目数量----科目一
    public static final String EXERCISE_DATA_NUM_WRONG_RAND_ONE  = "EXERCISE_DATA_NUM_WRONG_RAND_ONE";
    // 随机练习正确题目数量----科目一
    public static final String EXERCISE_DATA_NUM_RIGHT_RAND_ONE  = "EXERCISE_DATA_NUM_RIGHT_RAND_ONE";

    // 模拟练习题目数据保存----科目一
    public static final String EXERCISE_DATA_SIMULATION_ONE = "EXERCISE_DATA_SIMULATION_ONE";
    // 模拟练习错误题目数量----科目一
    public static final String EXERCISE_DATA_NUM_WRONG_SIMULATION_ONE  = "EXERCISE_DATA_NUM_WRONG_SIMULATION_ONE";
    // 模拟练习正确题目数量----科目一
    public static final String EXERCISE_DATA_NUM_RIGHT_SIMULATION_ONE  = "EXERCISE_DATA_NUM_RIGHT_SIMULATION_ONE";

    // 错题收藏练习错误题目数量----科目一
    public static final String EXERCISE_DATA_NUM_WRONG_WRONG_ONE = "EXERCISE_DATA_NUM_WRONG_WRONG_ONE";
    // 错题收藏练习正确题目数量----科目一
    public static final String EXERCISE_DATA_NUM_RIGHT_WRONG_ONE = "EXERCISE_DATA_NUM_RIGHT_WRONG_ONE";
    // 错题数据----科目一
    public static final String WRONG_COLLECT_DATA_ONE = "WRONG_COLLECT_DATA_ONE";


    // 考前冲刺练习错误题目数量----科目一
    public static final String EXERCISE_DATA_DRILL_WRONG_ONE = "EXERCISE_DATA_DRILL_WRONG_ONE";
    // 考前冲刺练习正确题目数量----科目一
    public static final String EXERCISE_DATA_DRILL_RIGHT_ONE = "EXERCISE_DATA_DRILL_RIGHT_ONE";

    // 难易错题练习错误题目数量----科目一
    public static final String EXERCISE_DATA_RECORD_WRONG_ONE = "EXERCISE_DATA_RECORD_WRONG_ONE";
    // 难易错题练习正确题目数量----科目一
    public static final String EXERCISE_DATA_RECORD_RIGHT_ONE = "EXERCISE_DATA_RECORD_RIGHT_ONE";

    // 章节一错误题目数量
    public static final String EXERCISE_DATA_NUM_WRONG_SUCTION_ONE = "EXERCISE_DATA_NUM_WRONG_SUCTION_ONE";
    // 章节一正确题目数量
    public static final String EXERCISE_DATA_NUM_RIGHT_SUCTION_ONE = "EXERCISE_DATA_NUM_RIGHT_SUCTION_ONE";

    // 章节二错误题目数量
    public static final String EXERCISE_DATA_NUM_WRONG_SUCTION_TWO = "EXERCISE_DATA_NUM_WRONG_SUCTION_TWO";
    // 章节二正确题目数量
    public static final String EXERCISE_DATA_NUM_RIGHT_SUCTION_TWO = "EXERCISE_DATA_NUM_RIGHT_SUCTION_TWO";

    // 章节三错误题目数量
    public static final String EXERCISE_DATA_NUM_WRONG_SUCTION_THREE = "EXERCISE_DATA_NUM_WRONG_SUCTION_THREE";
    // 章节三正确题目数量
    public static final String EXERCISE_DATA_NUM_RIGHT_SUCTION_THREE = "EXERCISE_DATA_NUM_RIGHT_SUCTION_THREE";

    // 章节四错误题目数量
    public static final String EXERCISE_DATA_NUM_WRONG_SUCTION_FOUR = "EXERCISE_DATA_NUM_WRONG_SUCTION_FOUR";
    // 章节四正确题目数量
    public static final String EXERCISE_DATA_NUM_RIGHT_SUCTION_FOUR = "EXERCISE_DATA_NUM_RIGHT_SUCTION_FOUR";


    // 章节五错误题目数量
    public static final String EXERCISE_DATA_NUM_WRONG_SUCTION_FIVE = "EXERCISE_DATA_NUM_WRONG_SUCTION_FIVE";
    // 章节五正确题目数量
    public static final String EXERCISE_DATA_NUM_RIGHT_SUCTION_FIVE = "EXERCISE_DATA_NUM_RIGHT_SUCTION_FIVE";

    // 章节六错误题目数量
    public static final String EXERCISE_DATA_NUM_WRONG_SUCTION_SIX = "EXERCISE_DATA_NUM_WRONG_SUCTION_SIX";
    // 章节六正确题目数量
    public static final String EXERCISE_DATA_NUM_RIGHT_SUCTION_SIX = "EXERCISE_DATA_NUM_RIGHT_SUCTION_SIX";

    // 章节七错误题目数量
    public static final String EXERCISE_DATA_NUM_WRONG_SUCTION_SEVEN = "EXERCISE_DATA_NUM_WRONG_SUCTION_SEVEN";
    // 章节七正确题目数量
    public static final String EXERCISE_DATA_NUM_RIGHT_SUCTION_SEVEN = "EXERCISE_DATA_NUM_RIGHT_SUCTION_SEVEN";


    // 章节八错误题目数量
    public static final String EXERCISE_DATA_NUM_WRONG_SUCTION_EIGHT = "EXERCISE_DATA_NUM_WRONG_SUCTION_EIGHT";
    // 章节八正确题目数量
    public static final String EXERCISE_DATA_NUM_RIGHT_SUCTION_EIGHT = "EXERCISE_DATA_NUM_RIGHT_SUCTION_EIGHT";

    // 章节九错误题目数量
    public static final String EXERCISE_DATA_NUM_WRONG_SUCTION_NINE = "EXERCISE_DATA_NUM_WRONG_SUCTION_NINE";
    // 章节九正确题目数量
    public static final String EXERCISE_DATA_NUM_RIGHT_SUCTION_NINE = "EXERCISE_DATA_NUM_RIGHT_SUCTION_NINE";

    // 章节十错误题目数量
    public static final String EXERCISE_DATA_NUM_WRONG_SUCTION_TEN = "EXERCISE_DATA_NUM_WRONG_SUCTION_TEN";
    // 章节十正确题目数量
    public static final String EXERCISE_DATA_NUM_RIGHT_SUCTION_TEN = "EXERCISE_DATA_NUM_RIGHT_SUCTION_TEN";

    // 章节十一错误题目数量
    public static final String EXERCISE_DATA_NUM_WRONG_SUCTION_ELEVEN = "EXERCISE_DATA_NUM_WRONG_SUCTION_ELEVEN";
    // 章节十一正确题目数量
    public static final String EXERCISE_DATA_NUM_RIGHT_SUCTION_ELEVEN = "EXERCISE_DATA_NUM_RIGHT_SUCTION_ELEVEN";



    // 顺序练习错误题目数量----科目四
    public static final String EXERCISE_DATA_NUM_WRONG_FOUR = "EXERCISE_DATA_NUM_WRONG_FOUR";
    // 顺序练习正确题目数量----科目四
    public static final String EXERCISE_DATA_NUM_RIGHT_FOUR = "EXERCISE_DATA_NUM_RIGHT_FOUR";

    // 随机练习错误题目数量----科目四
    public static final String EXERCISE_DATA_NUM_WRONG_RAND_FOUR  = "EXERCISE_DATA_NUM_WRONG_RAND_FOUR";
    // 随机练习正确题目数量----科目四
    public static final String EXERCISE_DATA_NUM_RIGHT_RAND_FOUR  = "EXERCISE_DATA_NUM_RIGHT_RAND_FOUR";

    // 模拟练习题目数据保存----科目四
    public static final String EXERCISE_DATA_SIMULATION_FOUR = "EXERCISE_DATA_SIMULATION_FOUR";
    // 模拟练习错误题目数量----科目四
    public static final String EXERCISE_DATA_NUM_WRONG_SIMULATION_FOUR  = "EXERCISE_DATA_NUM_WRONG_SIMULATION_FOUR";
    // 模拟练习正确题目数量----科目四
    public static final String EXERCISE_DATA_NUM_RIGHT_SIMULATION_FOUR  = "EXERCISE_DATA_NUM_RIGHT_SIMULATION_FOUR";


    // 错题收藏练习错误题目数量----科目四
    public static final String EXERCISE_DATA_NUM_RIGHT_WRONG_FOUR = "EXERCISE_DATA_NUM_RIGHT_WRONG_FOUR";
    // 错题收藏练习正确题目数量----科目四
    public static final String EXERCISE_DATA_NUM_WRONG_WRONG_FOUR = "EXERCISE_DATA_NUM_WRONG_WRONG_FOUR";
    // 错题数据----科目四
    public static final String WRONG_COLLECT_DATA_FOUR = "WRONG_COLLECT_DATA_FOUR";


    // 考前冲刺练习错误题目数量----科目四
    public static final String EXERCISE_DATA_NUM_DRILL_WRONG_FOUR = "EXERCISE_DATA_NUM_DRILL_WRONG_FOUR";
    // 考前冲刺练习正确题目数量----科目四
    public static final String EXERCISE_DATA_NUM_DRILL_RIGHT_FOUR = "EXERCISE_DATA_NUM_DRILL_RIGHT_FOUR";

    // 难易错题练习错误题目数量----科目四
    public static final String EXERCISE_DATA_NUM_RECORD_WRONG_FOUR = "EXERCISE_DATA_NUM_RECORD_WRONG_FOUR";
    // 难易错题练习正确题目数量----科目四
    public static final String EXERCISE_DATA_NUM_RECORD_RIGHT_FOUR = "EXERCISE_DATA_NUM_RECORD_RIGHT_FOUR";









    // 题目数据是否更新
    public static final String EXERCISE_DATA_IS_UPDATE = "EXERCISE_DATA_IS_UPDATE";



    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    static Context context= App.getContext();
    public static void put(String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @retur
     *
     * n
     */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值

     */
    public static void remove(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     */
    public static void clear() {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }


    public static void setObject(String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectVal);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> T getObject(String key, Class<T> clazz) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

}
