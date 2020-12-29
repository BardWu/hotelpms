package com.networks.pms.common.returnMsg;
/**
 * 处理错误消息
 * @author liuwx
 *
 */
public class ReturnMessage {
	
	public static String  MSG_SUCCESS = "success";
	public static String  MSG_ERROR= "error";
	
	public static String  ICONTYPE_OK = "ok";
	public static String  ICONTYPE_NO = "no";
	public static String  ICONTYPE_WARN = "warn";
	
	public ReturnMessage(){
		
	}
	
    //状态
	public String status;
	//错误消息
	public String message;
	//错误字段
	public String fieldName;
	//错误前端控件类型
	public String controlType;
    //前端显示提示符号类型
	public String iconType;
	//存储返回数据
	public Object data;
	//扩展字段1
	private String ext1;
	//扩展字段2
	private String ext2;
	//扩展字段2
	private String ext3;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getIconType() {
		return iconType;
	}

	public void setIconType(String iconType) {
		this.iconType = iconType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
     public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	
	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	
	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	/**
      * 状态和错误信息
      * @param paramStatus
      * @param paramMessage
      */
	 public ReturnMessage(String paramStatus,String paramMessage){
			
	    	this.status  = paramStatus;
	    	this.message = paramMessage;
	 }
	 
	 /**
	  * 状态，错误信息，提示图标类型
	  * @param paramStatus   状态
	  * @param paramMessage  错误信息
	  * @param paramTypeIcon 提示图标类型
	  */
	 public ReturnMessage(String paramStatus,String paramMessage,String paramTypeIcon){
			
	    	this.status  = paramStatus;
	    	this.message = paramMessage;
	    	this.iconType = paramTypeIcon;
	 }
	 
	 /**
	  * 状态，错误信息，提示图标类型，字段
	  * @param paramStatus   状态
	  * @param paramMessage  错误信息
	  * @param paramTypeIcon 提示图标类型
	  * @param paramFieldName 字段名称
	  */
	 public ReturnMessage(String paramStatus,String paramMessage,String paramTypeIcon,String paramFieldName){
			
	    	this.status  = paramStatus;
	    	this.message = paramMessage;
	    	this.iconType = paramTypeIcon;
	    	this.fieldName = paramFieldName;
	 }
	 
	 /**
	  * 状态，错误信息，提示图标类型，返回数据信息
	  * @param paramStatus   状态
	  * @param paramMessage  错误信息
	  * @param paramTypeIcon 提示图标类型
	  * @param paramData 返回数据信息
	  */
	 public ReturnMessage(String paramStatus,String paramMessage,String paramTypeIcon,Object paramData){
			
	    	this.status  = paramStatus;
	    	this.message = paramMessage;
	    	this.iconType = paramTypeIcon;
	    	this.data = paramData;
	 }
	 
}
