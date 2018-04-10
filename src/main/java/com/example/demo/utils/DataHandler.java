package com.example.demo.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.seckill.base.ReqMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataHandler {

    private static Logger logger = LoggerFactory.getLogger(DataHandler.class);
    
    private static String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAsmzbYKaJmrGMDsZxJf7G8zmilxfoBF4lMZDMeKECF5Zvsk6NM4XmjOKOf33Q1aAPVKwZjgqo074lhLQA3AFosu12hWTqRhNW1hXiix2vCRg08Y6JZfmJoIMyBAB0DLXABAE8CWepjATGBuOShJwYy6toJeRPHPcoMVutdUqOAwIDAQAB";
    private static String PRI_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAICybNtgpomasYwOxnEl/sbzOaKXF+gEXiUxkMx4oQIXlm+yTo0zheaM4o5/fdDVoA9UrBmOCqjTviWEtADcAWiy7XaFZOpGE1bWFeKLHa8JGDTxjoll+YmggzIEAHQMtcAEATwJZ6mMBMYG45KEnBjLq2gl5E8c9ygxW611So4DAgMBAAECgYA1He9R6CU0PBW1kc+TmbP7Fx4dnjDuwQa+YSO7ULpba7LabQSAwNguHM3RNIuQKuuCCFdzwK1jmukrAQduJ1+o5NyWUPXsJkfKcQsGAWHUavkPC4maS/SNGh92tCbiQfL0nMn3hJ2LIPhl0u8hFlCSmXKUdjw9zR6z66pozLH0+QJBAOvC4jiLb/93rd/7DGpYIUFjGIeR9UZXqQaVOWG/oyQnCuqzZNCi3h0RzQxy/Ks2f2Eav2MycVuQd70rGC2RgxUCQQCLvq89aTYEbybp9S1xHaFrn+4BP52ohoZj/yGz+9YYsN5FX7TXhbyFskHx50bppnpYQmjBU0Uot9rSc33J7PK3AkEAxdOxn2DhiawS6m7t7A4nWYadfqSq+2tzGWL901fKvPtF3crKPYdseVgGhvu1qLUCDak0Dzi0hfCjiMgwcYw4QQJAQ/aEAYjmpXdtZIFCDW5MsCwCs4a95VUvjPhq6r7C3BVAtFqcd+jpy9fq7Hv/Z+j4PW5mxgb6bpgkCw/yED91twJAJTGW2t9MNPEq+PE4AAJ07mlvvkgMiCyiB4FFvXA6+Fo4SDTuDZD8tusL6hK3OM8OLyPWbp94LiIl4Qt0U4usng==";
    
    /**
     * 数据加密
     * @param message - 响应信息
     * @return 加密后的数据
     */
    public static String dataEncrypt(ReqMessage message){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String requestMsg = mapper.writeValueAsString(message);
            System.out.println("message:"+requestMsg);
            byte[] encryptByte = RSACoder.encryptByPublicKey(requestMsg.getBytes(), PUB_KEY);
            return RSACoder.encryptBASE64(encryptByte);
        } catch (Exception e) {
            logger.error("数据加密异常：",e);
        }
        return null;
    }
    
    /**
     * 数据解密
     * @param <T>
     * @param bodyType - 请求数据的类型
     * @param reqMsg - 加密后的数据
     * @return 请求信息
     */
    public static String getMessage(String reqMsg) {
        if(reqMsg != null){
            try {
                // 解密
                byte[] decryptByte = RSACoder.decryptByPrivateKey(RSACoder.decryptBASE64(reqMsg), PRI_KEY);
                String receiveMsg = new String(decryptByte);
                System.err.println("receiveMsg："+ receiveMsg);
                
                return receiveMsg;
                /*ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(receiveMsg, RequestMsg.class);*/
            } catch (Exception e) {
                logger.error("数据解密异常：",e);
            }
        }
        return null;
    }
    
    /**
     * 数据签名
     * @param uuid
     * @return 签名结果
     */
    public static String dataSignature(String uuid){
        try {
            return RSACoder.sign(uuid.getBytes(), PRI_KEY);
        } catch (Exception e) {
            logger.error("数据签名异常：",e);
        }
        return null;
    }
    
    /**
     * 数据验签
     * @param uuid - 签名前数据
     * @param signature - 签名后数据
     * @return
     */
    public static boolean verify(String uuid, String signature){
        try {
            return RSACoder.verify(uuid.getBytes(), PUB_KEY, signature);
        } catch (Exception e) {
            logger.error("数据验签异常：",e);
        }
        return false;
    }
    
    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     * 
     * @param content
     *            传入的字符串
     * @param begin
     *            开始位置
     * @param end
     *            结束位置
     * @return
     */
    public static String plusStarBack(String content, int begin, int end) {

        if (begin >= content.length() || begin < 0) {
            return content;
        }
        if (end > content.length() || end < 0) {
            return content;
        }
        if (begin >= end) {
            return content;
        }
        String starStr = "";
        for (int i = begin; i < end; i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, begin) + starStr + content.substring(end, content.length());

    }
    
    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     * 
     * @param content
     *            传入的字符串
     * @param frontNum
     *            保留前面字符的位数
     * @param endNum
     *            保留后面字符的位数
     * @return 带星号的字符串
     */

    public static String plusStarBeforeAndBack(String content, int frontNum, int endNum) {

        if (frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= content.length()) {
            return content;
        }
        String starStr = "";
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());

    }
}
