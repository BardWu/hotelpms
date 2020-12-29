package com.networks.pms.service.com;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.HotelpmsLogger;
import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.common.util.Msg;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class HttpClientService {

    Logger errorLogger = Logger.getLogger(HttpClientService.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
   /* *//**
     *
     * @param url
     * @param jsonParam 参数
     * @param interfaceDesc 接口调用描述
     * @return
     *//*
    public Msg httpClientPost(String url, String jsonParam, String interfaceDesc){

        Msg msg = new Msg();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        try {

            if(jsonParam != null && !jsonParam.isEmpty() ){

                //设置发送消息的参数
                StringEntity entity = new StringEntity(jsonParam,"UTF-8");

                httpPost.setEntity(entity);
                httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
            }

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {

                int status = response.getStatusLine().getStatusCode();

                if(status == 200){

                    HttpEntity entity = response.getEntity();
                    // do something useful with the response body
                    if (entity != null) {
                        String result = EntityUtils.toString(entity);
                        response.close();
                        httpclient.close();
                        msg.setData(result);
                    }
                    // and ensure it is fully consumed
                    EntityUtils.consume(entity);

                }else{
                    msg.setStatus(Msg.MSG_ERROR);
                    msg.setMessage("error 错误码是:"+status);
                    errorLogger.error("http请求接口:"+interfaceDesc+":"+url+" 错误状态码"+status);
                    loggerMessageQueue.error("http请求接口:"+interfaceDesc+":"+url+" 错误状态码"+status);
                 //   System.out.println("http请求接口:"+interfaceDesc+":"+url+" 错误状态码"+status);
                }

            }finally {

                response.close();
            }

        } catch (Exception e) {
            msg.setStatus(Msg.MSG_ERROR);
            msg.setMessage(Msg.getExceptionDetail(e));
            errorLogger.error("http请求接口:"+interfaceDesc+":"+url+" 错误信息"+Msg.getExceptionDetail(e));
            loggerMessageQueue.error("http请求接口:"+interfaceDesc+":"+url+" 错误信息"+Msg.getExceptionDetail(e));
        } finally {

            return msg;
        }
    }
*/


    /**
     *
     * @param url
     * @param jsonParam 参数
     * @param interfaceDesc 接口调用描述
     * @return
     */
    public Msg httpClientPost(String url, String jsonParam, String interfaceDesc){

        Msg msg = new Msg();
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        HttpPost httpPost = null;
        StringEntity stringEntity = null;
        HttpEntity httpEntity = null;
        try {

            if(!StringUtil.isNull(jsonParam)){
                //设置发送消息的参数
                stringEntity = new StringEntity(jsonParam,"UTF-8");
                httpPost = new HttpPost(url);
                httpPost.setEntity(stringEntity);
                httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
            }else{
                return null;
            }
            httpclient = HttpClients.createDefault();

            response = httpclient.execute(httpPost);

            int status = response.getStatusLine().getStatusCode();

            if(status == 200){
                httpEntity = response.getEntity();
                // do something useful with the response body
                if (httpEntity != null) {
                    String result = EntityUtils.toString(httpEntity);
                    msg.setData(result);
                }
                EntityUtils.consume(httpEntity);
            }else{
                msg.setStatus(Msg.MSG_ERROR);
                msg.setMessage("error 错误码是:"+status);
                errorLogger.error("http请求接口:"+interfaceDesc+":"+url+" 错误状态码"+status);
                loggerMessageQueue.error("http请求接口:"+interfaceDesc+":"+url+" 错误状态码"+status);
            }

        } catch (Exception e) {
            msg.setStatus(Msg.MSG_ERROR);
            msg.setMessage(Msg.getExceptionDetail(e));
            errorLogger.error("http请求接口:"+interfaceDesc+":"+url+" 错误信息"+Msg.getExceptionDetail(e));
            loggerMessageQueue.error("http请求接口:"+interfaceDesc+":"+url+" 错误信息"+Msg.getExceptionDetail(e));
        } finally {

            if(response !=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpclient!=null){
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return msg;
    }


    /**
     *
     * @param url
     * @param jsonParam 参数
     * @param interfaceDesc 接口调用描述
     * @return
     */
    public Msg httpClientPost(String url,JSONArray jsonParam,String interfaceDesc){

        Msg msg = new Msg();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        try {

            if(jsonParam != null && !jsonParam.isEmpty() && jsonParam.size() > 0){

                //设置发送消息的参数
                StringEntity entity = new StringEntity(jsonParam.toString(),"UTF-8");

                httpPost.setEntity(entity);
                httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
            }

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {

                int status = response.getStatusLine().getStatusCode();

                if(status == 200){

                    HttpEntity entity = response.getEntity();
                    // do something useful with the response body
                    if (entity != null) {

                        String result = EntityUtils.toString(entity);
                        response.close();
                        httpclient.close();
                        msg.setData(result);
                    }
                    // and ensure it is fully consumed
                    EntityUtils.consume(entity);

                }else{
                    msg.setStatus(Msg.MSG_ERROR);
                    msg.setMessage("error 错误码是:"+status);
                    errorLogger.error(MessagePoint.HOTELPMS_LOG+"http请求接口:"+interfaceDesc+":"+url+" 错误状态码"+status);
                    System.out.println("http请求接口:"+interfaceDesc+":"+url+" 错误状态码"+status);
                }

            }finally {

                response.close();
            }

        } catch (Exception e) {

            msg.setStatus(Msg.MSG_ERROR);
            msg.setMessage(Msg.getExceptionDetail(e));

            errorLogger.error(MessagePoint.HOTELPMS_LOG+"http请求接口:"+interfaceDesc+":"+url+" 错误信息"+e.getMessage());
            errorLogger.error(MessagePoint.HOTELPMS_LOG+"oops, got an exception: ", e);//捕获堆栈异常

        } finally {

            return msg;
        }
    }


    /**
     *
     * @param url
     * @param interfaceDesc 接口调用描述
     * @return
     */
    public Msg httpClientPostParamMap(String url,Map<String,Object> paramMap,String interfaceDesc){

        Msg msg = new Msg();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        try {

            if(paramMap != null && !paramMap.isEmpty() && paramMap.size() > 0){

                //设置发送消息的参数
                //StringEntity entity = new StringEntity(jsonParam.toString(),"UTF-8");

                List<NameValuePair> pairs = null;

                pairs = new ArrayList<NameValuePair>(paramMap.size());
                for (String key : paramMap.keySet()) {
                    pairs.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
                }

                httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));

                // httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
            }

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {

                int status = response.getStatusLine().getStatusCode();

                if(status == 200){

                    HttpEntity entity = response.getEntity();
                    // do something useful with the response body
                    if (entity != null) {

                        String result = EntityUtils.toString(entity);
                        response.close();
                        httpclient.close();
                        msg.setData(result);
                    }
                    // and ensure it is fully consumed
                    EntityUtils.consume(entity);

                }else{
                    msg.setStatus(Msg.MSG_ERROR);
                    msg.setMessage("error 错误码是:"+status);
                    errorLogger.error(MessagePoint.HOTELPMS_LOG+"http请求接口:"+interfaceDesc+":"+url+" 错误状态码"+status);
                    System.out.println("http请求接口:"+interfaceDesc+":"+url+" 错误状态码"+status);
                }

            }finally {

                response.close();
            }

        } catch (Exception e) {

            msg.setStatus(Msg.MSG_ERROR);
            msg.setMessage("error 异常");

            errorLogger.error(MessagePoint.HOTELPMS_LOG+"http请求接口:"+interfaceDesc+":"+url+" 错误信息"+e.getMessage());
     //       errorLogger.error(MessagePoint.HOTELPMS_LOG+"oops, got an exception: ", e);//捕获堆栈异常

        } finally {

            return msg;
        }
    }

    /**
     * 系统内部接口互相调用通用方法
     * @param url     调用接口url
     * @param data    调用参数Map
     * @param sysName 调用系统名称
     * @param desc    调用具体描述
     * @return
     */
//	public Map<String,String> httpClientPostWechat(String url,Map<String,String> data,String sysName,String desc) {
//		Map<String,String> returnMap = new HashMap<String,String>();
//		String returnStr = "";
//
//		HttpClient httpClient = new HttpClient();
//
//		PostMethod postMethod = new PostMethod(url);
//
//		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//		if(data!=null){
//
//			postMethod.setRequestBody(data.get("data"));
//
//		}
//
//		try {
//
//
//			int statusCode = httpClient.executeMethod(postMethod);
//			if(statusCode == 200){
//				returnStr = postMethod.getResponseBodyAsString();
//				returnMap.put("data", returnStr);
//				returnMap.put("status", "success");
//			}else if(statusCode == 302){
//
//				returnStr = postMethod.getResponseBodyAsString();
//				returnMap.put("data", returnStr);
//				returnMap.put("status", "success");
//
//			}else{
//
//				returnMap.put("status", "fail");
//			}
//
//		} catch (HttpException e) {
//			returnMap.put("status", "fail");
//			logger.error("调用"+sysName+"系统"+desc+"可能是协议不对或者返回的内容有问题"+url);
//			logger.error("oops, got an exception: ", e);
//
//		} catch (IOException e) {
//			returnMap.put("status", "fail");
//			logger.error("网络连接发生异常"+url);
//			//发生网络异常
//			logger.error("oops, got an exception: ", e);
//		}catch (Exception e){
//			returnMap.put("status", "fail");
//			logger.error("调取httpClient发生异常"+url);
//			logger.error("oops, got an exception: ", e);
//
//		}finally{
//			//释放连接
//			postMethod.releaseConnection();
//
//		}
//
//		return returnMap;
//	}
}

