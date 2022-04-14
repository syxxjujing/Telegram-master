package jujing.util;


import java.io.PrintWriter;
import java.io.StringWriter;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    public static CrashHandler instance = null;

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        StringBuffer sb = printCrash(ex);

        LoggerUtil.logI(TAG, "crash--->"+sb.toString());
//        LogUtil.show(TAG, "uncaughtException222_log_: " + sb.toString());
//        String txt = TConfigure.APP_DIR + "/" + code + ".txt";
//        LogTool.error(sb.toString());

//        FileUtil.copyFile(cacheFile.getAbsolutePath(), newPath);


//        BootUpReceiver.rebootTask(HookMain.context);
    }

    public StringBuffer printCrash(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.flush();
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        return sb;

    }

}
