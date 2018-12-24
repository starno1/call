package com.zxing.callcenter.call_center;

import android.content.Context;

import com.cincc.cinvccbar.VccBarCtrl;

/**
 * Created by zxing on 2018/7/25
 *
 * @desc
 */
public class CallCenterBusiness {

    // 外呼
    public static void callout(String DestNum){
        VccBarCtrl.instance().GetMethodCollection()
                .MakeCall(DestNum, 3, "", "", "");
    }

    // 内呼
    public static void callin(String DestAgentID, int serviceDirect, String taskID, String transParentParam){
        VccBarCtrl.instance().GetMethodCollection()
                .CallIn(DestAgentID, 5, "", "");
    }

    /**
     *  转出
     * @param lTransferType 0 坐席工号 1 外部号码 2 服务号
     * @param DestNum
     */
    public static void transferOut(int lTransferType, String DestNum){
        VccBarCtrl.instance().GetMethodCollection()
                .TransferOut(lTransferType, DestNum);
    }

    // 咨询
    public static void consult(int lConsultType, String ConsultNum){
        VccBarCtrl.instance().GetMethodCollection()
									.Consult(lConsultType, ConsultNum);
    }

    // 静音 1 静音设置 0 静音取消
    public static void mute(int flag){
        VccBarCtrl.instance().GetMethodCollection()
									.Mute(flag);
    }

    // 接通
    public static void answer(){
        VccBarCtrl.instance().GetMethodCollection()
									.Answer();
    }
    // 挂断
    public static void disconnect(){
        VccBarCtrl.instance().GetMethodCollection()
                .Disconnect(0);
    }

    // 判断坐席登录
    public static boolean isAgentLogin() {
        return VccBarCtrl.instance().GetMethodCollection().GetAgentStatus() == 0 ? false : true;
    }
    // 签入
    public static void initial(Context context){
        VccBarCtrl.instance().GetMethodCollection().Initial(context);
    }
    // 签出
    public static void unInitial(){
        VccBarCtrl.instance().GetMethodCollection().UnInitial();
    }

}
