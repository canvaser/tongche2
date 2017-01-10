package com.siwei.tongche.common;
/**
 * 系统常量
 * @author hjl
 *
 * 2016年1月19日下午4:34:53
 */
public class AppConstants {
    /**
     * 用户类型
     */
    public static class USER_TYPE{
        //0驾驶员、1发货单位工作人员2收货单位工作人员、3租赁公司工作人员
        /**
         * 驾驶员
         */
        public static final String TYPE_DRIVER="0";
        /**
         * 发货单位工作人员
         */
        public static final String TYPE_SENDER_UNIT="1";
        /**
         * 收货单位工作人员
         */
        public static final String TYPE_CONSIGNEE_UNIT="2";
        /**
         * 租赁公司工作人员
         */
        public static final String TYPE_RENT_UNIT="3";
    }

    /**
     * 用户在单位下的角色
     */
    public static class USER_UNIT_ROLE{
        // 0创建者、1管理员、2普通员工
        /**
         * 创建者
         */
        public static final String ROLE_CREATOR="0";
        /**
         * 管理员
         */
        public static final String ROLE_MANAGER="1";
        /**
         * 普通员工
         */
        public static final String ROLE_NORMAL="2";
    }



    /**
     * 用户类型
     */
    public static class ORDER_STATUS{
        //SIGN_TYPE(确认类型：1正常确认、3、损失确认、4驾驶员完成确认、5撤销、6完成签收) 传（1、3、4、5、6）)

        /**
         * 等待签收
         */
        public static final String STATUS_CREATE="0";
        /**
         * 正常签收
         */
        public static final String STATUS_NORMAL_SIGN="1"; //结束
        /**
         * 事故故障
         */
        public static final String STATUS_ACCIDENT="2";
        /**
         * 损失确认
         */
        public static final String STATUS_UNUSUAL_SIGN="3";
        /**
         * 驾驶员确认
         */
        public static final String STATUS_DRIVER_CONFIRM="4";//结束
        /**
         * 驾驶员确认失败
         */
        public static final String STATUS_CONFIRM_FAILED="6";
        /**
         * 驾驶员撤销
         */
        public static final String STATUS_CANCEL="5";
        /**
         * 成功撤销
         */
        public static final String STATUS_CANCEL_FINISH="7";


    }


    public static int DIMEN_1 =1;


    final  public static int LIST_PAGE_SIZE =10;

}
