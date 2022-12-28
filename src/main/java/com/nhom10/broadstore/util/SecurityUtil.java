package com.nhom10.broadstore.util;

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
}