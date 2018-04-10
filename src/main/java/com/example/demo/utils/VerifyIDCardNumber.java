package com.example.demo.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/** 
 * @use 15位身份证升级、校验 
 * @ProjectName stuff 
 * @Date 2016年8月16日13:59:44
 * @FullName VerifyIDCardNumber.java </br> 
 */  
public class VerifyIDCardNumber {  
      
    /** 
     * 根据15位的身份证号码获得18位身份证号码 
     * @param fifteenIDCard 15位的身份证号码 
     * @return 升级后的18位身份证号码 
     * @throws Exception 如果不是15位的身份证号码，则抛出异常 
     */  
    public static String getEighteenIDCard(String fifteenIDCard) throws Exception{  
        if(fifteenIDCard != null && fifteenIDCard.length() == 15){  
            StringBuilder sb = new StringBuilder();  
            sb.append(fifteenIDCard.substring(0, 6))  
              .append("19")  
              .append(fifteenIDCard.substring(6));  
            sb.append(getVerifyCode(sb.toString()));  
            return sb.toString();  
        } else {  
            throw new Exception("不是15位的身份证");  
        }  
    }  
      
    /** 
     * 获取校验码 
     * @param idCardNumber 不带校验位的身份证号码（17位） 
     * @return 校验码 
     * @throws Exception 如果身份证没有加上19，则抛出异常 
     */  
    public static char getVerifyCode(String idCardNumber) throws Exception{  
        if(idCardNumber == null || idCardNumber.length() < 17) {  
            throw new Exception("不合法的身份证号码");  
        }  
        char[] Ai = idCardNumber.toCharArray();  
        int[] Wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};  
        char[] verifyCode = {'1','0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};  
        int S = 0;  
        int Y;  
        for(int i = 0; i < Wi.length; i++){  
            S += (Ai[i] - '0') * Wi[i];  
        }  
        Y = S % 11;  
        return verifyCode[Y];  
    }  
      
    /** 
     * 校验18位的身份证号码的校验位是否正确 
     * @param idCardNumber 18位的身份证号码 
     * @return  
     * @throws Exception 
     */  
    public static boolean verify(String idCardNumber) throws Exception{  
        if(idCardNumber == null || idCardNumber.length() != 18) {  
            throw new Exception("不是18位的身份证号码");  
        }  
        return getVerifyCode(idCardNumber) == idCardNumber.charAt(idCardNumber.length() - 1);  
    }
    
    /**
     * 0:女， 1：男
     * @param idCardNumber
     * @return
     */
    public static int getGender(String idCardNumber){
    	return Integer.parseInt(idCardNumber.substring(16, 17))%2;
    }
    
    /**
     * 根据身份证判断是否满18周岁
     * @param cerno
     * @return
     */
    public static boolean isAdult(String cerno){
        DateTime birthday = DateTime.parse(cerno.substring(6, 14), DateTimeFormat.forPattern("yyyyMMdd"));
        DateTime now = DateTime.now();
        if (now.getYear() - birthday.getYear() < 18){//如果年份小于18，直接返回false，未成年
            return false;
        } else if (now.getYear() - birthday.getYear() == 18){//如果年份差等于18，则比较月份
            if (now.getMonthOfYear() - birthday.getMonthOfYear()<0){//年份等于18时，当前月份小于出生月份，则返回false，未成年
                return false;
            } else if (now.getMonthOfYear() == birthday.getMonthOfYear()){ //如果月份也相等，则比较日期
                if (now.getDayOfMonth() - birthday.getDayOfMonth()<0){ //年份等于18，月份相等时，如果当前日期小于出生日期，则返回false，未成年
                    return false;
                }
            }
        }
        return true;
    }
}  
