package com.networks.pms.common.util;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * @program: hotelpms
 * @description: json转换工具类
 * @author: Bardwu
 * @create: 2018-11-22 12:03
 **/public class JsonUtil {


    /**
     * json string 转换为 map 对象
     * @param jsonObj
     * @return
     */
    public static Map<Object, Object> jsonToMap(Object jsonObj) {
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        Map<Object, Object> map = (Map)jsonObject;
        return map;
    }

    /**json string 转换为 对象
     * @param jsonObj
     * @param type
     * @return
     */
    public  static <T>  T jsonToBean(Object jsonObj, Class<T> type) {
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        T obj =(T)JSONObject.toBean(jsonObject, type);
        return obj;
    }
}
