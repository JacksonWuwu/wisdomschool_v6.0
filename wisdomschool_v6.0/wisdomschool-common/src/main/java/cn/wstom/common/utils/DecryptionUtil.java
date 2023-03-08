package cn.wstom.common.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * 加密工具
 *
 * @author dws
 */
public class DecryptionUtil {
    private static Base64.Decoder decoder;
    private static KeyFactory keyFactory;
    private static Cipher cipher;

    /**
     * 用私钥加密
     *
     * @param context
     * @param privateKey
     * @return
     */
    public static String decryption(final String context, final String privateKey) {
        final byte[] b = DecryptionUtil.decoder.decode(privateKey);
        final byte[] s = DecryptionUtil.decoder.decode(context.getBytes(StandardCharsets.UTF_8));
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
        try {
            final PrivateKey key = DecryptionUtil.keyFactory.generatePrivate(spec);
            DecryptionUtil.cipher.init(2, key);
            final byte[] f = DecryptionUtil.cipher.doFinal(s);
            return new String(f);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
        } catch (IllegalBlockSizeException e3) {
            e3.printStackTrace();
        } catch (BadPaddingException e4) {
            e4.printStackTrace();
        }
        return null;
    }

    static {
        DecryptionUtil.decoder = Base64.getDecoder();
        try {
            DecryptionUtil.keyFactory = KeyFactory.getInstance("RSA");
            DecryptionUtil.cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        }
    }
}
