package jujing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.UserConfig;

import jujing.operate.AddAndInviteGroupAction;
import jujing.util.LoggerUtil;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    public static final String ACTION_XTELE_GROUP_ADD_INVITE= "ACTION_XTELE_GROUP_ADD_INVITE";


    @Override
    public void onReceive(Context context, Intent intent) {
        LoggerUtil.logI(TAG,"intent  12--->" + intent.getAction());
        if (intent.getAction().equals(ACTION_XTELE_GROUP_ADD_INVITE)){

//            int i = 3/0;
            AddAndInviteGroupAction.checkGroupUsers(1219085934L);
        }

    }
}
