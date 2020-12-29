package com.networks.pms.interceptor;
import com.networks.pms.common.returnMsg.ReturnEnum;
public class ExceptionUtil extends RuntimeException{

    private Integer code;

    public ExceptionUtil(ReturnEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
