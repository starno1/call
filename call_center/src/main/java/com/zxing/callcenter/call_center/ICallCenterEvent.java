package com.zxing.callcenter.call_center;

/**
 * Created by zxing on 2018/7/25
 *
 * @desc
 */
public interface ICallCenterEvent {

    // 电话条初始化成功
    void OnInitalSuccess();

    // 电话条初始化失败事件报告
    void OnInitalFailure(int code, String description);

    // 电话条按钮状态集合报告,调用此方法，证明可呼出
    void OnReportBtnStatus(String btnIDS);

    // 坐席振铃事件，和ComingCall，OnCallRing2一样，推荐使用OnCallRing
    void OnCallRing(String CallingNo, String CalledNo, String OrgCalledNo, String CallData,
                    String SerialID, int ServiceDirect, String CallID, String UserParam,
                    String TaskID, String UserDn, String AgentDn, String AreaCode, String Filename,
                    String networkInfo, String queueTime, String opAgentID);

    // 电话结束 和RecordFileUpLoaded, OnCallEnd2一样，推荐使用OnCallEnd
    void OnCallEnd(String CallID, String SerialID, int ServiceDirect, String UserNo, String BgnTime,
                   String EndTime, String AgentAlertTime, String UserAlertTime, String FileName,
                   String Directory, int DisconnectType, String UserParam, String TaskID,
                   String serverName, String netWorkInfo);

    /*电话接通*/
    void OnCallConnectd(String UserNo, String AnswerTime, String SerialID, int ServiceDirect,
                        String CallID, String UserParam, String TaskID);

    // 电话条退出事件报告
    void OnBarExit(int code, String description);


}
