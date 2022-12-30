package com.nhom10.broadstore.util;

import com.nhom10.broadstore.db.JDBIConnector;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SecurityUtil {

    public static PublicKey publicKeyFromBase64(String base64PublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decoded = Base64.getDecoder().decode(base64PublicKey);

        PublicKey publicKey = KeyFactory.getInstance("DSA").generatePublic(new X509EncodedKeySpec(decoded));
        return publicKey;
    }

    public static String base64FromPublicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static String base64FromPrivateKey(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static PrivateKey privateKeyFromBase64(String base64PrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decoded = Base64.getDecoder().decode(base64PrivateKey);
        return KeyFactory.getInstance("DSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    public static boolean verifySignature(byte[] data, byte[] signature, PublicKey pubKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance("SHA256withDSA");
        sig.initVerify(pubKey);
        sig.update(data);
        return sig.verify(signature);
    }

    public static byte[] sign(byte[] data, PrivateKey privKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance("SHA256withDSA");
        sig.initSign(privKey);
        sig.update(data);
        return sig.sign();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException, NoSuchProviderException, InvalidKeyException, InvalidKeySpecException, IOException {
        Jdbi jdbi = JDBIConnector.get();
        String publicKeyString = "MIIDQjCCAjUGByqGSM44BAEwggIoAoIBAQCPeTXZuarpv6vtiHrPSVG28y7FnjuvNxjo6sSWHz79NgbnQ1GpxBgzObgJ58KuHFObp0dbhdARrbi0eYd1SYRpXKwOjxSzNggooi/6JxEKPWKpk0U0CaD+aWxGWPhL3SCBnDcJoBBXsZWtzQAjPbpUhLYpH51kjviDRIZ3l5zsBLQ0pqwudemYXeI9sCkvwRGMn/qdgYHnM423krcw17njSVkvaAmYchU5Feo9a4tGU8YzRY+AOzKkwuDycpAlbk4/ijsIOKHEUOThjBopo33fXqFD3ktm/wSQPtXPFiPhWNSHxgjpfyEc2B3KI8tuOAdl+CLjQr5ITAV2OTlgHNZnAh0AuvaWpoV499/e5/pnyXfHhe8ysjO65YDAvNVpXQKCAQAWplxYIEhQcE51AqOXVwQNNNo6NHjBVNTkpcAtJC7gT5bmHkvQkEq9rI837rHgnzGC0jyQQ8tkL4gAQWDt+coJsyB2p5wypifyRz6Rh5uixOdEvSCBVEy1W4AsNo0fqD7UielOD6BojjJCilx4xHjGjQUntxyaOrsLC+EsRGiWOefTznTbEBplqiuH9kxoJts+xy9LVZmDS7TtsC98kOmkltOlXVNb6/xF1PYZ9j897buHOSXC8iTgdzEpbaiH7B5HSPh++1/et1SEMWsiMt7lU92vAhErDR8C2jCXMiT+J67ai51LKSLZuovjntnhA6Y8UoELxoi34u1DFuHvF9veA4IBBQACggEAEyGTABP9klOEFotZW3fduT8q287tTo9OE+WRxhpmYi2/nw29DHbH0Lcl0xQvxB4elEeV974HTSZxDjo5I/JJtUVYqbuK+xnuhKVUUN6cVBSppJ5DSrNQzAvhi4WWTzYWgnMBXN6urOndvfdG9+SlblWoyS3mGBCl9UlVYOrw38ouu/vEzN+crqR4AZABJ/pubJBLLS0pbyRrMt7wPhnfECOmzD31Cnmx4wPNeTLzHr3cvP4XRUUS9L2LU78txNGGFRwEtnNDSvVa/AD0xrFHpqudajPD/sOomEnlPBizWz3hmIZc33DU+ORRcCGmtwg64dBXb2lT/8uoKqD8RIkaRg==";
        byte[] pubBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);


        String priKeyString = "MIICXAIBADCCAjUGByqGSM44BAEwggIoAoIBAQCPeTXZuarpv6vtiHrPSVG28y7FnjuvNxjo6sSWHz79NgbnQ1GpxBgzObgJ58KuHFObp0dbhdARrbi0eYd1SYRpXKwOjxSzNggooi/6JxEKPWKpk0U0CaD+aWxGWPhL3SCBnDcJoBBXsZWtzQAjPbpUhLYpH51kjviDRIZ3l5zsBLQ0pqwudemYXeI9sCkvwRGMn/qdgYHnM423krcw17njSVkvaAmYchU5Feo9a4tGU8YzRY+AOzKkwuDycpAlbk4/ijsIOKHEUOThjBopo33fXqFD3ktm/wSQPtXPFiPhWNSHxgjpfyEc2B3KI8tuOAdl+CLjQr5ITAV2OTlgHNZnAh0AuvaWpoV499/e5/pnyXfHhe8ysjO65YDAvNVpXQKCAQAWplxYIEhQcE51AqOXVwQNNNo6NHjBVNTkpcAtJC7gT5bmHkvQkEq9rI837rHgnzGC0jyQQ8tkL4gAQWDt+coJsyB2p5wypifyRz6Rh5uixOdEvSCBVEy1W4AsNo0fqD7UielOD6BojjJCilx4xHjGjQUntxyaOrsLC+EsRGiWOefTznTbEBplqiuH9kxoJts+xy9LVZmDS7TtsC98kOmkltOlXVNb6/xF1PYZ9j897buHOSXC8iTgdzEpbaiH7B5HSPh++1/et1SEMWsiMt7lU92vAhErDR8C2jCXMiT+J67ai51LKSLZuovjntnhA6Y8UoELxoi34u1DFuHvF9veBB4CHBrpF+yrjBG/hN7WgkwNLvt56ZaRlT0U6doOavo=";
        byte[] priBytes = Base64.getDecoder().decode(priKeyString);
        PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(priBytes);
        PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);
        String token = Base64.getEncoder().encodeToString(("tvnjzicnav" + "linhdinh86a@gmail.com").getBytes());

        SignFile signFile = new SignFile();
        String text = signFile.signText(token, "tvnjzicnav.priv");
        System.out.println(text);

        boolean verify = verifySignature(token.getBytes(), Base64.getDecoder().decode(text), pubKey);
        System.out.println(verify);

    }
}