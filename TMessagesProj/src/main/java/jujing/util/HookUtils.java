package jujing.util;

public class HookUtils {

    private static final String TAG = "HookUtils";

    public static void frames() {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] st = Thread.currentThread().getStackTrace();

        for (int i = 0; i < st.length; i++) {
            sb.append(st[i].toString()).append("\n");
            st[i].isNativeMethod();
        }

//        for (i in st.indices) {
//            sb.append(st[i].toString()).append("\n")
//            st[i].isNativeMethod
//        }

        LoggerUtil.logI(TAG,"390==================================");
        LoggerUtil.logI(TAG,sb.toString());
        LoggerUtil.logI(TAG,"392==================================");
    }
}
