package com.networks.pms.common.returnMsg;

import com.networks.pms.common.util.CommandReponse;

/**
 * * Created by ssp
 * */
public class ReturnUtil {

    public static CommandReponse success() {
        return success("");
    }

    public static CommandReponse success(Object object) {
        CommandReponse result = new CommandReponse();
        result.setStatus(CommandReponse.SUCCESS);
        result.setData(object);
        result.setMessage("操作成功");
        return result;
    }

    public static CommandReponse success(ReturnEnum re) {
        CommandReponse result = new CommandReponse();
        result.setStatus(CommandReponse.SUCCESS);
        result.setData("");
        result.setMessage(re.getMsg());
        return result;
    }

    public static CommandReponse error() {

        return error("操作失败");
    }

    public static CommandReponse error(String msg) {

        CommandReponse result = new CommandReponse();
        result.setStatus(CommandReponse.ERROR);
        result.setMessage(msg);
        result.setErrCode(ReturnEnum.OTHER_ERROR.getCode());
        return result;
    }

    public static CommandReponse error(ReturnEnum rEnum) {

        CommandReponse result = new CommandReponse();
        result.setStatus(CommandReponse.ERROR);
        result.setMessage(rEnum.getMsg());
        result.setErrCode(rEnum.getCode());
        return result;
    }

    public static CommandReponse error(int code,String msg) {

        CommandReponse result = new CommandReponse();
        result.setStatus(CommandReponse.ERROR);
        result.setMessage(msg);
        result.setErrCode(code);
        return result;
    }

    public static CommandReponse code(ReturnEnum re) {
        if(re.getCode()>=4000){
            return  error(re.getMsg());
        }
        return success(re);
    }
}
