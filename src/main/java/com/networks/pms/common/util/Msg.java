package com.networks.pms.common.util;
/**
 *
 * @author liuwx
 *
 */
public class Msg {
	
	public static String  MSG_SUCCESS = "success";
	public static String  MSG_ERROR = "error";
	//未登录状态
	public static String  MSG_NOT_LOGIN = "csc_x0000";
	//token失效
	public static String  MSG_TOKEN_INVALID = "csc_x0001";
	//没有修改权限
	public static String  MSG_NOT_OPT_AUTH = "csc_x0002";

	public static String  MSG_NOT_DATA = "not_data";

	//小程序端 未登录状态
	public static String  MSG_XCX_NOT_LOGIN = "xcx_x0000";
	//小程序端 token失效
	public static String  MSG_XCX_TOKEN_INVALID = "xcx_x0001";
	//小程序端 未认证
	public static String  MSG_XCX_NOT_AUTH = "xcx_x0002";
	//小程序端 已认证通过 但前端缓存用户信息还是旧的状态 ,需要提示用户进行登录信息缓存更新
	public static String  MSG_XCX_ACTIVATE_AUTH = "xcx_x0003";

	/*public static String  MSG_NOT_AUTH = "not_auth";*/

		
	public Msg(){
		this.status = MSG_SUCCESS;
	}
	
    //状态
	public String status;
	//错误消息
	public String message;
    //前端显示提示符号类型
	public String iconType;
	//存储返回数据
	public Object data;

	public String getStatus() {
		return status;
	}

	public Msg setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Msg setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getIconType() {
		return iconType;
	}

	public Msg setIconType(String iconType) {
		this.iconType = iconType;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Msg setData(Object data) {
		this.data = data;
		return this;
	}
	public static String getExceptionDetail(Throwable e) {
		StringBuffer stringBuffer = new StringBuffer(e.toString() + "\n");
		StackTraceElement[] messages = e.getStackTrace();
		int length = messages.length;
		if(length>5){
			length = 5;
		}
		for (int i = 0; i < length; i++) {
			stringBuffer.append("\t"+messages[i].toString()+"\n");
		}
		stringBuffer.append("\t"+"..."+"\n");
		return stringBuffer.toString();
	}

	/**
	 *
	 * @param key 标签名称
	 * @param value 标签的内容
	 * @return
	 */
	public static String StringToXml(String key,String value){
		if(value==null || value.equals(""))
			return "";
		return "<"+key+">"+value+"</"+key+">";
	}

	@Override
	public String toString() {
		return "Msg{" +
				"status='" + status + '\'' +
				", message='" + message + '\'' +
				", iconType='" + iconType + '\'' +
				", data=" + data +
				'}';
	}
}
