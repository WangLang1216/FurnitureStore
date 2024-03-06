package com.summer.commonmodule.utils;

import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 加密解密
 * @author WangLang
 */
@Data
@Component
public class EncryptionUtil {

    /**
     * 私钥
     */
    @Value("${encrypt.privateKey}")
    private String privateKey;

    /**
     * 公钥
     */
    @Value("${encrypt.publicKey}")
    private String publicKey;

    /**
     * 构建RSA
     */
    private RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), privateKey, publicKey);

    /**
     * 公钥加密
     * @param str 加密内容
     * @return 加密后内容
     */
    public String publicKeyEncryption(String str) {
        return rsa.encryptBase64(str, KeyType.PublicKey);
    }

    /**
     * 私钥加密
     * @param str 加密内容
     * @return 加密后内容
     */
    public String privateKeyEncryption(String str) {
        return rsa.encryptBase64(str, KeyType.PrivateKey);
    }

    /**
     * 公钥解密
     * @param str 解密内容
     * @return 解密后的内容
     * @throws CryptoException
     */
    public String publicKeyDecryption(String str) throws CryptoException {
        return rsa.decryptStr(str, KeyType.PublicKey);
    }

    /**
     * 私钥解密
     * @param str 解密内容
     * @return 解密后的内容
     * @throws CryptoException
     */
    public String privateKeyDecryption(String str) throws CryptoException {
        return rsa.decryptStr(str, KeyType.PrivateKey);
    }

}
