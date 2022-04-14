package jujing.operate;

import android.os.SystemClock;
import android.text.TextUtils;

import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

import java.util.ArrayList;

import jujing.Global;
import jujing.util.CrashHandler;
import jujing.util.ExecutorUtil;
import jujing.util.LoggerUtil;
import jujing.util.WriteFileUtil;

public class AddAndInviteGroupAction {

    private static final String TAG = "AddAndInviteGroupAction";

    public static void checkGroupUsers(long chatId) {
        try {
            TLRPC.Chat chat = MessagesController.getInstance(UserConfig.selectedAccount).getChat(chatId);
            String title = chat.title;
            LoggerUtil.logI(TAG + chatId, "title  108---> " + title + "---->" + chatId);
            AccountInstance AccountInstanceIns = AccountInstance.getInstance(UserConfig.selectedAccount);

            MessagesController messagesController = AccountInstanceIns.getMessagesController();
            TLRPC.InputChannel channel = messagesController.getInputChannel(chatId);
            TLRPC.TL_channels_getParticipants req = new TLRPC.TL_channels_getParticipants();
            LoggerUtil.logI(TAG + chatId, "newReq  119---> " + chatId);
            TLRPC.TL_channelParticipantsRecent filter = new TLRPC.TL_channelParticipantsRecent();
            req.channel = channel;
            req.filter = filter;

            int index = 0;
            try {
                index = Integer.parseInt(WriteFileUtil.read(Global.GOT_INFO_INDEX + chatId));
            } catch (Exception e) {
            }
            LoggerUtil.logI(TAG + chatId, "index  129---> " + index);
            req.offset = index;
            req.limit = 1000;
            ConnectionsManager connectionsManager = AccountInstanceIns.getConnectionsManager();
            connectionsManager.sendRequest(req, new RequestDelegate() {
                @Override
                public void run(TLObject response, TLRPC.TL_error error) {
                    try {
                        if (error != null) {
                            LoggerUtil.logI(TAG + chatId, "获取群用户信息错误code:" + error.code + " text:" + error.text);
                            return;
                        }
                        //                response.
                        TLRPC.TL_channels_channelParticipants res = (TLRPC.TL_channels_channelParticipants) response;
                        ArrayList<TLRPC.User> users = res.users;
                        if (users == null || users.isEmpty()) {
                            LoggerUtil.logI(TAG + chatId, "获取群用户信息错误为空");
                            return;
                        }
                        LoggerUtil.logI(TAG + chatId, "获取群用户信息成功，数量:" + users.size());
                        ExecutorUtil.doExecute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    for (int i = 0; i < users.size(); i++) {
//                                        if (i == 0) {//过滤自己
//                                            continue;
//                                        }
//                                        if (i<10){
//                                            continue;
//                                        }
                                        TLRPC.User user = users.get(i);
                                        if (user.self || user.contact) {
                                            continue;
                                        }

                                        long id = user.id;
//                                        String first_name = user.first_name;
//                                        String nick = first_name;
//                                        String last_name = user.last_name;
//                                        if (!TextUtils.isEmpty(last_name)) {
//                                            nick = first_name + last_name;
//                                        }
                                        LoggerUtil.logI(TAG + chatId, "nick & id 100 ---->" + id + "---->" + user.first_name + "---->" + i);
                                        AccountInstance.getInstance(UserConfig.selectedAccount).getContactsController().addContact(user,true);



                                        SystemClock.sleep(3000);
                                    }
                                } catch (Exception e) {
                                    LoggerUtil.logI(TAG + chatId, "e  92---> " + CrashHandler.getInstance().printCrash(e));
                                }

                            }
                        });
                    } catch (Exception e) {
                        LoggerUtil.logI(TAG + chatId, "e  9948---> " + CrashHandler.getInstance().printCrash(e));
                    }


                }
            });
        } catch (Exception e) {
            LoggerUtil.logI(TAG + chatId, "e  98---> " + CrashHandler.getInstance().printCrash(e));
        }


    }

}
