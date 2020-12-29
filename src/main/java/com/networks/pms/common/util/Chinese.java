package com.networks.pms.common.util;

import com.networks.pms.common.string.StringUtil;
import com.networks.pms.service.webSocket.LoggerMessageQueue;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: hotelpms
 * @description: 中文操作
 * @author: Bardwu
 * @create: 2019-11-20 16:02
 **/
public class Chinese {

   // private static Logger logger = Logger.getLogger(Chinese.class);

 //   private static LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();


    public static String chineseToPinYin(String pinyin){
       // System.out.println("pinyin得内容:"+pinyin);
        return convertHanzi2Pinyin(pinyin,true);
    }

    public static String chineseToPinYinWithLowerCase(String pinyin){
        // System.out.println("pinyin得内容:"+pinyin);
        String result = convertHanzi2Pinyin(pinyin,true);
        if(!StringUtil.isNull(result)){
            result = result.toLowerCase();
        }
        return result;
    }


    /***
     * 将汉字转成拼音(取首字母或全拼)
     * @param hanzi
     * @param full 是否全拼
     * @return
     */
    private static String convertHanzi2Pinyin(String hanzi,boolean full)
    {
        /***
         * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言
         * ^[\u4E00-\u9FFF]+$ 匹配简体和繁体
         * ^[\u4E00-\u9FA5]+$ 匹配简体
         */
        String regExp="^[\u4E00-\u9FFF]+$";
        StringBuffer sb=new StringBuffer();
        if(hanzi==null||"".equals(hanzi.trim()))
        {
            return "";
        }
        String pinyin="";
        for(int i=0;i<hanzi.length();i++)
        {
            char unit=hanzi.charAt(i);
            if(match(String.valueOf(unit),regExp))//是汉字，则转拼音
            {
                pinyin=convertSingleHanzi2Pinyin(unit);
                if(full)
                {
                    sb.append(pinyin);
                }
                else
                {
                    sb.append(pinyin.charAt(0));
                }
            }
            else
            {
                sb.append(unit);
            }
        }
        return sb.toString();
    }
    /***
     * 将单个汉字转成拼音
     * @param hanzi
     * @return
     */
    private static String convertSingleHanzi2Pinyin(char hanzi)
    {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] res;
        StringBuffer sb=new StringBuffer();
        try {
            res = PinyinHelper.toHanyuPinyinStringArray(hanzi,outputFormat);
            sb.append(res[0]);//对于多音字，只用第一个拼音
        } catch (Exception e) {
            e.printStackTrace();
            String error = "汉字"+hanzi+"转换拼音失败,原因:"+Msg.getExceptionDetail(e);
       //     logger.error(error);
       //     loggerMessageQueue.error(error);
            //若转换失败，内容不变
            return String.valueOf(hanzi);
        }
        return sb.toString();
    }

    /***
     * @param str 源字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    public static boolean match(String str,String regex)
    {
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);
        return matcher.find();
    }
}
