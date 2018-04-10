package com.example.demo.utils.encryption.technology;

import java.util.Map;

import com.encryption.technology.ECCCoder;

public class ECCCoderTest {

    public static void main(String[] args) throws Exception {  
        String inputStr = "abc";  
        byte[] data = inputStr.getBytes();  
  
        Map<String, Object> keyMap = ECCCoder.initKey();  
  
        String publicKey = ECCCoder.getPublicKey(keyMap);  
        String privateKey = ECCCoder.getPrivateKey(keyMap);  
        System.err.println("公钥: \n" + publicKey);  
        System.err.println("私钥： \n" + privateKey);  
  
        byte[] encodedData = ECCCoder.encrypt(data, publicKey);  
  
        byte[] decodedData = ECCCoder.decrypt(encodedData, privateKey);  
  
        String outputStr = new String(decodedData);  
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);  
        System.out.println(inputStr.equals(outputStr));
    }  

    
}
