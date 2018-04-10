package com.example.demo.utils.en;

/*
 * 数字签名算法
 */
public enum EncryptionAlgorithm {

    RSA("RSA", "MD5withRSA"),DSA("DSA", "SHA1withDSA"),ECDSA("EC", "SHA1withECDSA");
    
    private String encryption;
    
    private String algorithm;
    
    private EncryptionAlgorithm(String encryption, String algorithm){
        this.encryption = encryption;
        this.algorithm = algorithm;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
