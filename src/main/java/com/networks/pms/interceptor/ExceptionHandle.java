package com.networks.pms.interceptor;

import com.networks.pms.common.returnMsg.ReturnEnum;
import com.networks.pms.common.returnMsg.ReturnUtil;
import com.networks.pms.common.util.CommandReponse;
import com.networks.pms.common.util.MessagePoint;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一异常捕获 需要在sprin-mvc.xml中写<mvc:annotation-driven />
@ControllerAdvice
public class ExceptionHandle {

  //  private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    private Logger logger = Logger.getLogger(ExceptionHandle.class);
    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommandReponse handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return ReturnUtil.error(ReturnEnum.UNKONW_ERROR);//系统异常
    }


    /**
     * 处理所有业务异常
     */
    @ExceptionHandler(ExceptionUtil.class)
    @ResponseBody
    public CommandReponse handleBusinessException(ExceptionUtil e) {
        return ReturnUtil.error(e.getCode(), e.getMessage());
    }

}
