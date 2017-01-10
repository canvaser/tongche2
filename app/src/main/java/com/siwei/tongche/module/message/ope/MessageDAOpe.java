package com.siwei.tongche.module.message.ope;

import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.message.bean.MessageDetailBean;
import com.siwei.tongche.module.message.bean.MessageMenuBean;
import com.siwei.tongche.utils.CacheUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by ${viwmox} on 2017-01-03.
 */

public class MessageDAOpe implements Serializable{

    ArrayList<MessageMenuBean> msgTop = new ArrayList<>();

    MessageMenuBean messageMenuBean;

    public static final String[] MSG_KEYS = {"0","1","2","3","4","5","6"};

    public static final String[] MSG_VALUES = {"全部","小票","车辆","用户","任务","系统","公告"};

    public MessageDAOpe() {
        init();
    }

    public void init(){
        UserInfo info = CacheUtils.getLocalUserInfo();
        for(int i=0;i<MSG_KEYS.length;i++){
            switch (i){
                //小票
                case 1:
                    if(info.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_RENT_UNIT)){
                        continue;
                    }else{
                        break;
                    }
                    //车辆
                case 2:
                    if(info.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){
                        continue;
                    }else{
                        break;
                    }
                    //用户
                case 3:
                    break;
                    //任务
                case 4:
                    if(info.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)||info.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT)||info.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_RENT_UNIT)){
                        continue;
                    }else{
                        break;
                    }
                    //系统
                case 5:
                    break;
                    //公告
                case 6:
                    break;
            }
            msgTop.add(new MessageMenuBean(MSG_KEYS[i],MSG_VALUES[i]));
        }
        setMessageMenuBean(0);
    }

    public ArrayList<MessageMenuBean> getMsgTop() {
        return msgTop;
    }

    public void setMsgTop(ArrayList<MessageMenuBean> msgTop) {
        this.msgTop = msgTop;
    }

    public MessageMenuBean getMessageMenuBean() {
        return messageMenuBean;
    }

    public void setMessageMenuBean(int index) {
        messageMenuBean = msgTop.get(index);
    }

    public ArrayList<String> getNames(){
        ArrayList<String> strings = new ArrayList<>();
        for(int i=0;i<msgTop.size();i++){
            strings.add(msgTop.get(i).getName());
        }
        return strings;
    }
}
