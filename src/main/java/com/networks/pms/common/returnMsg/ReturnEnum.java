package com.networks.pms.common.returnMsg;

/**
 * Created by 廖师兄
 * 2017-01-21 14:23
 */
public enum ReturnEnum {

    //大于4000的为失败代码
    OTHER_ERROR(0, "其他错误"),
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(3000, "成功"),
    UPDATE_SUCCESS(3300, "修改成功"),
    UPDATE_ERROR(4000, "修改失败"),
    DELETE_SUCCESS(3301, "删除成功"),
    DELETE_ERROR(4001, "删除失败"),
    ADD_SUCCESS(3302, "添加成功"),
    ADD_ERROR(4002, "添加失败"),
    RELDELETE_ERROR(4003, "请先删除关联的项目"),
    DELETEFILE_ERROR(4004, "删除文件异常"),
    DELETEPATH_NULL(4005, "传递了一个空的文件路径"),
    DELETEFILE_SUCCESS(3004, "删除文件成功"),
    ACTION_ERROR(4005, "添加/更新失败"),

    ;

    private Integer code;

    private String msg;

    ReturnEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
