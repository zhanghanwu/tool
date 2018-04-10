package com.example.demo.utils;

import java.io.File;
import java.net.InetAddress;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

/**
 * @author aben
 */
public class IPMain {

    //private static String version2 = "E:\\GeoLite2-City.mmdb";

    public static void main(String[] args) {
        IPMain main = new IPMain();
        String version2 = main.getClass().getResource("/").getPath()+"/GeoLite2-City.mmdb"; 
        System.out.println(main.getCity("119.123.163.222", new File(version2)));
        System.out.println(main.getCity("219.136.134.157", new File(version2)));
        System.out.println(main.getCity("103.47.137.210", new File(version2)));
        System.out.println(main.getCity("125.70.11.136", new File(version2)));
    }

    public String getCity(String ipStr, File dataBaseFile) {
        String result = "";
        try {
            DatabaseReader reader = new DatabaseReader.Builder(dataBaseFile).build();
            InetAddress ip = InetAddress.getByName(ipStr);
            CityResponse response = reader.city(ip);
            // 获取所在洲
            String zhouStr = response.getContinent().getNames().get("zh-CN");
            // 获取所在国家
            String guoStr = response.getCountry().getNames().get("ja");
            // 获取所在省份
            String shengStr = response.getSubdivisions().get(0).getNames().get("zh-CN");
            // 获取所在城市(由于是免费版的数据库,查找城市的时候是不准确的)
            String shiStr = response.getCity().getNames().get("zh-CN");
            result = zhouStr + " " + guoStr + " " + shengStr + " " + shiStr;
        } catch (Exception e) {
            ;
        }
        return result;
    }

}