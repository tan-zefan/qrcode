package com.example.antifake.tool;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {
    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpUq3iZBLLPXWJ/839+1VAkz7+QMVv8xU4q1F6pph4WANii7jdvrMaeIuON+b4ezWmRCsTCdd9wsiEHff+Yi24g8IXEnp2wCY1z7B2JLzuk6lyGdx/MGD/NjefcokDIvxlLvJEcNiPdI6PyyfsnlfTVmta9NL3KQgUT24fw3us5QIDAQAB";

    private static final String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKlSreJkEss9dYn/zf37VUCTPv5AxW/zFTirUXqmmHhYA2KLuN2+sxp4i4435vh7NaZEKxMJ133CyIQd9/5iLbiDwhcSenbAJjXPsHYkvO6TqXIZ3H8wYP82N59yiQMi/GUu8kRw2I90jo/LJ+yeV9NWa1r00vcpCBRPbh/De6zlAgMBAAECgYEAl9BRp5IiZ6eLI1f/0aDvJx7edAiO/Xjtpgm03EtSmVQQdOLhta+t1iYOukzVAiU2TQUBuegxFcj3D73vBF3yrui9/B1f87vVxGFJYTbrOnH3G6Im1Ou8J7phjVL+HKUimq1+yWTyJy/y96gfKRdoEKUgx66dMg6w+wuaygFf5AECQQD9AesVMv4cCq8Uuo1pVkuuOsQzuELXGPDHtSW5JHS6HhexcURj/HL+CT6fTFz8pSSkPFdpFBbEdMQkZbLPK4chAkEAq1NfWVhEELR2j/YAtcUnSFcYBdj9osh6Gdg3e4M6pGbTQhJzBCGFiZx2LCRCrlhFlvTtPgAweDg7p3hIBrUhRQJBAMmmn8FztQiQk17IGTFdsFAbUomOUOezXyUtAfYAUnCMz4GDr2ipqtVCdQDuEibjUML9vQVpF4RZNIN18wAfx0ECQGdwyiP2j/oKC3+2Bw24gJRDyYRWVCVBp78M9crEG9cBIlJFM15uIuNILW1PY1dCgpm3PbpugNVFFndxvwMVv4UCQQClsIWnIbPHZC0mU4JR7pXVB0ir3EQIky6oIHc8MsqLNWOaFFer8Frf4kiGSQrviEl8woUFV2hv9bPS1AJRWP8r";

    /**
     * RSA私钥加密
     *
     * @param str       加密字符串
     * @param privateKey 私钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String privateKey) throws Exception {
        //base64编码的私钥
        byte[] decoded = java.util.Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        String outStr = java.util.Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA公钥解密
     *
     * @param str        解密字符串
     * @param publicKey 公钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String publicKey){
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str);
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = null;//.generatePrivate(new PKCS8EncodedKeySpec(decoded));
        try {
            pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));

            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            String outStr = new String(cipher.doFinal(inputByte));
            return outStr;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            return "0";
        }

    }

    public static String getPublicKey() {
        return publicKey;
    }

    public static String getPrivateKey() {
        return privateKey;
    }
}
