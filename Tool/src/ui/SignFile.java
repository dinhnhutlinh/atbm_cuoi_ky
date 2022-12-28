package ui;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class SignFile {
    public PrivateKey readPriKey(String path) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        byte[] key = Files.readAllBytes(Paths.get(path));
        PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);
        System.out.println(Base64.getEncoder().encodeToString(priKey.getEncoded()));
        return priKey;
    }

    public String signFile(String pathFile, String pathKey)
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException,
            IOException, InvalidKeySpecException {

        /* Create a Signature object and initialize it with the private key */
        Signature dsa = Signature.getInstance("SHA256withDSA", "SUN");

        dsa.initSign(readPriKey(pathKey));

        /* Update and sign the data */

        FileInputStream fis = new FileInputStream(pathFile);
        BufferedInputStream bufin = new BufferedInputStream(fis);
        byte[] buffer = new byte[1024];
        int len;
        while (bufin.available() != 0) {
            len = bufin.read(buffer);
            dsa.update(buffer, 0, len);
        }

        bufin.close();

        /*
         * Now that all the data to be signed has been read in,
         * generate a signature for it
         */
        byte[] realSig = dsa.sign();
        return Base64.getEncoder().encodeToString(realSig);
    }

    public void saveSignature(String pathSignature, String signature) throws FileNotFoundException {
        /* Save the signature in a file */
        PrintWriter sigfos = new PrintWriter(new FileOutputStream(pathSignature));
        sigfos.write(signature);
        sigfos.close();
    }
}
