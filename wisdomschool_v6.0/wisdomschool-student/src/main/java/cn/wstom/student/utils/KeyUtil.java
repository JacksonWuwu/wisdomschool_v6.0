package cn.wstom.student.utils;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author HP
 */
public class KeyUtil {
    private static final int KEY_SIZE = 1024;
    private static KeyPair pair;

    static {
        try {
            final KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
            g.initialize(KEY_SIZE);
            pair = g.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取公钥
     *
     * @return
     */
    public static String getPublicKey() {
        return new String(Base64.getEncoder().encode(pair.getPublic().getEncoded()), StandardCharsets.UTF_8);
    }

    /**
     * 获取私钥
     *
     * @return
     */
    public static String getPrivateKey() {
        return new String(Base64.getEncoder().encode(pair.getPrivate().getEncoded()), StandardCharsets.UTF_8);
    }

    public static int getKeySize() {
        return KEY_SIZE;
    }
}
