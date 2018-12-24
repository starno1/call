package com.zxing.callcenter.call_center;

import android.content.Context;
import android.util.Log;

import com.cincc.cinvccbar.VccBarCtrl;
import com.cincc.cinvccbar.VccBarPublic;
import com.cincc.cinvccbar.acp.AcpEnumVector;

/**
 * Created by zxing on 2018/7/25
 *
 * @desc
 */
public class CallCenterInit {

    public static final String TAG = "aoji.callcenter";

    public static void initCallCenter(Context context, String mainServer, String vccID,
                                      String shortAgentID, String agentPasswd, String sipPort,
                                      String sipPassWord, ICallCenterEvent event) {
        if (context != null) {
            loginCallCenterServer(context, mainServer, vccID, shortAgentID, agentPasswd,
                    sipPort, sipPassWord);
            // 绑定事件
            bindEvent(event);
            CallCenterBusiness.initial(context);
            VccBarPublic.instance().SetConfig(context);
        } else {
            Log.i(TAG, "context is null!!!");
        }
    }

    private static void loginCallCenterServer(Context context, String mainServer, String vccID,
                                              String shortAgentID, String agentPasswd, String sipPort,
                                              String sipPassWord) {
        VccBarCtrl.instance().GetAttributeCollection().setMainIP(mainServer);
        VccBarCtrl.instance().GetAttributeCollection()
                .setAgentID(String.format("000010%s%S", vccID, shortAgentID));
        VccBarCtrl.instance().GetAttributeCollection().setVccID(vccID);
        VccBarCtrl.instance().GetAttributeCollection().setDn(String.format("000002%s%S", vccID, shortAgentID));
        VccBarCtrl.instance().GetAttributeCollection().setPassWord(agentPasswd);
        VccBarCtrl.instance().GetAttributeCollection().setSipServerIP(mainServer);
        VccBarCtrl.instance().GetAttributeCollection().setSipServerPort(StringToInt(sipPort));
        VccBarCtrl.instance().GetAttributeCollection().setSipPassWord(sipPassWord);
        VccBarCtrl.instance().GetAttributeCollection().setPhoneType(VccBarPublic.PhoneType_t.PhoneSip);
        VccBarCtrl.instance().GetAttributeCollection().setAcpTraceFlag(1 + 2 + 4 + 8);
        VccBarCtrl.instance().GetAttributeCollection().setSipTraceFlag(0);
        VccBarCtrl.instance().GetAttributeCollection().setLogFileEnable(1);
        VccBarCtrl.instance().GetAttributeCollection().setAutoAnswer(1);

        int mode = AcpEnumVector.SocketMode.SOCKETMODE_HTTPJSON;
        VccBarCtrl.instance().GetAttributeCollection().setTransMode(mode);
        if (mode == AcpEnumVector.SocketMode.SOCKETMODE_SOCKETIO) {
            VccBarCtrl.instance().GetAttributeCollection().setMainPortID(14812);
        } else if (mode == AcpEnumVector.SocketMode.SOCKETMODE_HTTPJSON) {
            VccBarCtrl.instance().GetAttributeCollection().setMainPortID(15100);
        } else {
            VccBarCtrl.instance().GetAttributeCollection().setMainPortID(14800);
        }
        /*电话条按钮*/
        VccBarCtrl.instance().GetMethodCollection()
                .SerialBtn("0,1,2,3,4,5,6,7,8,9,10,11,12,13,15,16,27", "0,15");
        VccBarPublic.instance().SetConfig(context);
        Log.e(TAG, VccBarCtrl.instance().GetMethodCollection().GetVersion());
    }

    private static void bindEvent(final ICallCenterEvent event) {
        VccBarCtrl.IEvent vccBarEvent = new VccBarCtrl.IEvent() {

            // 电话条按钮状态集合报告
            @Override
            public void OnReportBtnStatus(String btnIDS) {
                Log.e(TAG, "OnReportBtnStatus btnIDS:" + btnIDS);
                if (event != null) {
                    event.OnReportBtnStatus(btnIDS);
                }
            }

            // 电话条提示事件报告
            @Override
            public void OnPrompt(int code, String description) {
                Log.e(TAG, "OnPrompt code=" + code + ",description=" + description);
            }

            // 电话条初始化成功事件报告
            @Override
            public void OnInitalSuccess() {
                Log.e(TAG, "OnInitalSuccess");
                if (event != null) {
                    event.OnInitalSuccess();
                }
            }

            // 电话条初始化失败事件报告
            @Override
            public void OnInitalFailure(int code, String description) {
                Log.e(TAG, "OnInitalFailure code=" + code + ",description=" + description);
                if (event != null) {
                    event.OnInitalFailure(code, description);
                }
            }

            // 电话条底层事件报告
            @Override
            public void OnEventPrompt(int eventIndex, String eventParam) {
                Log.e(TAG, "OnEventPrompt eventIndex=" + eventIndex + ",eventParam=" + eventParam);
            }

            // 坐席振铃事件，和ComingCall，OnCallRing2一样，推荐使用OnCallRing
            @Override
            public void OnCallRing(String CallingNo, String CalledNo, String OrgCalledNo, String CallData,
                                   String SerialID, int ServiceDirect, String CallID, String UserParam, String TaskID, String UserDn,
                                   String AgentDn, String AreaCode, String Filename, String networkInfo, String queueTime,
                                   String opAgentID) {
                Log.e(TAG, "OnCallRing");
                if (event != null) {
                    event.OnCallRing(CallingNo, CalledNo, OrgCalledNo, CallData,
                            SerialID, ServiceDirect, CallID, UserParam, TaskID, UserDn,
                            AgentDn, AreaCode, Filename, networkInfo, queueTime,
                            opAgentID);
                }
            }

            // 电话结束 和RecordFileUpLoaded, OnCallEnd2一样，推荐使用OnCallEnd
            @Override
            public void OnCallEnd(String CallID, String SerialID, int ServiceDirect, String UserNo, String BgnTime,
                                  String EndTime, String AgentAlertTime, String UserAlertTime, String FileName, String Directory,
                                  int DisconnectType, String UserParam, String TaskID, String serverName, String netWorkInfo) {
                Log.e(TAG, "OnCallEnd");
                if (event != null) {
                    event.OnCallEnd(CallID, SerialID, ServiceDirect, UserNo, BgnTime,
                            EndTime, AgentAlertTime, UserAlertTime, FileName, Directory,
                            DisconnectType, UserParam, TaskID, serverName, netWorkInfo);
                }

            }

            // 电话条随路数据事件报告
            @Override
            public void OnCallDataChanged(String callData) {
                Log.e(TAG, "OnCallDataChanged");
            }

            /*电话接通*/
            @Override
            public void OnCallConnectd(String UserNo, String AnswerTime, String SerialID, int ServiceDirect,
                                       String CallID, String UserParam, String TaskID) {
                Log.e(TAG, "OnCallConnectd");
                if (event != null) {
                    event.OnCallConnectd(UserNo, AnswerTime, SerialID, ServiceDirect,
                            CallID, UserParam, TaskID);
                }
            }

            // 电话条退出事件报告
            @Override
            public void OnBarExit(int code, String description) {
                Log.e(TAG, "OnBarExit");
                if(event != null){
                    event.OnBarExit(code, description);
                }
            }

            // 电话条工作状态事件报告
            @Override
            public void OnAgentWorkReport(int workStatus, String description) {
                Log.e(TAG, "OnAgentWorkReport");
            }

            // 系统消息事件报告
            @Override
            public void OnSystemMessage(int code, String description) {
                Log.e(TAG, "OnSystemMessage");
            }

            // 坐席工作信息统计和坐席所在服务工作情况统计，适用于MinotorVersion为4.0
            @Override
            public void OnWorkStaticInfoReport(String staticInfo) {
                Log.e(TAG, "OnWorkStaticInfoReport");
            }

            // 排队信息报告
            @Override
            public void OnQueueReport(String ServiceReportInfo) {
                Log.e(TAG, "OnQueueReport");
            }

            // 由于android的消息是异步，所以方法的操作都是通过这个事件返回的
            @Override
            public void OnMethodResponseEvent(String cmdName, String param) {
                Log.e(TAG, "OnMethodResponseEvent");
            }

            @Override
            public void OnDebugEvent(String currentTime, String tagName, String message) {
                Log.e(TAG, "OnDebugEvent");
            }

        };

        VccBarCtrl.instance().SetVccBarEventEntrence(vccBarEvent);
    }

    public static int StringToInt(String str) {
        int code;
        try {
            code = Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
        return code;
    }
}
