package com.siwei.tongche.http;
/**
 * 请求接口
 * @author hjl
 *
 * 2015年12月31日下午1:44:36
 */
public class MyUrls {
	//public static final String APP_DOMAIN="http://180.168.218.122:8280/";
	public static final String APP_DOMAIN="http://192.168.1.67:8088/";

	public static final String NAMESPACE=APP_DOMAIN+"api/";

	public static final String LOGIN=NAMESPACE+"TCUser/LoginUser";
	//注册
	public static final String REGISTER=NAMESPACE+"TCUser/RegisterUser";

	//获取验证码
	public static final String SMS_CODE=NAMESPACE+"TCUser/GetVerificationCode";

	//获取用户基本信息
	public static final String GET_DRIVER_INFO=NAMESPACE+"TCPersonalSetting/GetDriverInfo";

	//获取驾驶员车辆信息
	public static final String GET_DRIVER_VEHICLE_INFO=NAMESPACE+"TCPersonalSetting/GetDriverVehicleInfo";

	//获取消息
	public static final String GET_MESSAGE=NAMESPACE+"TCMessage/GetMessage";

	//提交绑定单位/发货单位
	public static final String BIND_UNIT=NAMESPACE+"TCUnit/BindUnit";

	//绑定单位/发货单位审核
	public static final String BIND_UNIT_AUDIT=NAMESPACE+"TCUnit/BindUnitAudit";

	//头像上传
	public static  final String POST_IMG=NAMESPACE+"TCFileUpload/UploadFile";

	//修改姓名
	public static  final String MODIFY_USER_NAME=NAMESPACE+"TCPersonalSetting/ModifyUserName";

	//选择用户角色
	public static  final String SELELCT_ROLE=NAMESPACE+"TCPersonalSetting/SetUserType";

	//完善驾驶证信息
	public static  final String CREATE_UNIT=NAMESPACE+"TCUnit/CreateUnit";

	//获取所有单位信息
	public static  final String GET_ALL_UNIT=NAMESPACE+"TCUnit/GetAllUnit";

	//完善驾驶证信息
	public static  final String COMPLETE_DRIVER_INFO=NAMESPACE+"TCPersonalSetting/CompleteDriverInfo";

	//------------------------------驾驶员
	public static final String DRIVER_MAP_INFO=NAMESPACE+"TCDriverBusiness/GetDriverMapInfo";


	//----------排队车辆-------------
	public static final String WAITTING_CAR_LIST=NAMESPACE+"TCDriverBusiness/DriverQueueList";
	public static final String WAITTING_CAR=NAMESPACE+"TCDriverBusiness/DriverQueue";


	//----------------------小票-------------------------
	//小票列表
	public static final String EXPRESS_LIST=NAMESPACE+"TCDriverBusiness/GetTicketList";

	//按车牌搜索车辆信息
	public static final String GET_CARINFO_PLATE=NAMESPACE+"TCVehicle/GetVehicleByNo";
	//绑定车辆
	public static final String BIND_CAR=NAMESPACE+"TCVehicle/DriverBindVehicle";

	//----------------------任务--------------------------
	//任务分类总数
	public static final String TASK_CATEGARY_DATA=NAMESPACE+"TCTask/GetTotalTaskNum";
	//任务列表
	public static final String TASK_LIST=NAMESPACE+"TCTask/GetTaskList";

	//----------------个人中心------------
	//忘记密码
	public static final String FORGET_PWD=NAMESPACE+"User/ForgetPwd";
	 //消息设置
	public static final String MESSSAGE_SETTING=NAMESPACE+"User/SetMessageSetting";
	//获取消息设置
	public static final String GET_MESSAGE_SETTING=NAMESPACE+"User/GetMessageSetting";
	//意见反馈
	public static final String FEEDBACK=NAMESPACE+"User/UserFeedback";
	//重置密码
	public static final String RESET_PWD=NAMESPACE+"User/ResetPwd";
	 //更换手机号
	public static final String UPDATE_MOBILE=NAMESPACE+"TCPersonalSetting/ModifyUserMobile";
	//关于
	public static final String ABOUT=NAMESPACE+"User/GETABOUT";

	//------------消息-------------------
	//获取单位所有人员
	public static final String GET_COMPANY_LIST=NAMESPACE+"Message/GetNews_TeamList";
	//新建消息
	public static final String BUILD_MESSAGE=NAMESPACE+"Message/SendMessage";

}
