package com.networks.pms.common.util;
/**
 * 处理错误消息
 * @author liuwx
 *
 */
public class ClientMsg {
	
	public static String  MSG_SUCCESS = "success";
	public static String  MSG_ERROR = "error";
	public static String  MSG_NOT_AUTH = "not_auth";
	
	//参数错误
	public static int  MSG_PARAMETER_ERROR = 2000;
	//操作数据库错误
	public static int  MSG_OPT_DATABASE_ERROR = 2001;
	//没有任何数据记录
	public static int  MSG_NOT_DATA_ERROR = 2002;
	
	//异常信息
	public static int  MSG_EXCEPTION_ERROR = 1000;
	//Token错误
	public static int  MSG_EXCEPTION_TOKEN_ERROR = 1001;
	//Token签名错误
	public static int  MSG_EXCEPTION_SIGN_ERROR = 1002;
	//Token已超时失效
	public static int  MSG_EXCEPTION_TOKEN_TIME_OUT = 1003;
	//无Token信息
	public static int  MSG_EXCEPTION_NOT_TOKEN = 1004;
	//需要设置认证方式
	public static int  MSG_EXCEPTION_VER_METHOD = 1005;
	//非法操作
	public static int  MSG_EXCEPTION_ILLEGAL_OPERATION = 1006;
	//调用其它系统接口失败
	public static int  MSG_EXCEPTION_Interface_failure = 1007;
	//未认证
	public static int  MSG_EXCEPTION_NOT_AUTH = 1008;
	
	//购物车中没有任何数据
	public static int  MSG_EXCEPTION_SHOP_CART_NOT_DATA = 3001;
	//购物车中有不符合配送时间的商品
	public static int  MSG_EXCEPTION_SHOP_CART_NON_CONFORMITY_TIME = 3002;
	//订单不存在 
	public static int  MSG_EXCEPTION_ORDER_NOT_EXIST = 3003;
	//发送至工单系统错误
	public static int  MSG_EXCEPTION_SEND_ORDER = 3004;
	
	//未登录状态
	public static String  MSG_NOT_LOGIN = "csc_x0000";
	//token失效
	public static String  MSG_TOKEN_INVALID = "csc_x0001";
	//没有修改权限
	public static String  MSG_NOT_OPT_AUTH = "csc_x0002";

	public static String  MSG_NOT_DATA = "not_data";


	

		
	public ClientMsg(){
		this.status = MSG_SUCCESS;
	}
	
    //状态
	public String status;
	
	public int errCode = 0;
	//错误消息
	public String message = "ok";
	//存储返回数据
	public Object data = "";

	public String getStatus() {
		return status;
	}

	public ClientMsg setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ClientMsg setMessage(String message) {
		this.message = message;
		return this;
	}

	public Object getData() {
		return data;
	}

	public ClientMsg setData(Object data) {
		this.data = data;
		return this;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	
	
}
