import java.util.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private String iv = "";

    //以當前時間作為IV值的產生
    public String Output_IV() {
        Calendar now = Calendar.getInstance();

        iv += String.format("%05d", now.get(Calendar.MILLISECOND) + now.get(Calendar.SECOND) * 1000);
        iv += String.format("%03d", now.get(Calendar.SECOND) * now.get(Calendar.MINUTE));
        iv += String.format("%05d", now.get(Calendar.DAY_OF_YEAR) * now.get(Calendar.MILLISECOND));
        iv += String.format("%03d", now.get(Calendar.HOUR) * 60 + now.get(Calendar.MINUTE));

        return iv.substring(0, 16);
    }

    //回傳IV值
    public String get_IV() {
        return iv.substring(0, 16);
    }
    
    //AES加密
    public String AES_encrypt(String plaintext, String key, String mod) throws Exception {
        Cipher getCipher = Cipher.getInstance(mod); // 設定 Operation mode
        getCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(Output_IV().getBytes())); // 加密使用的 KEY 和 IV
        byte[] byteCipherText = getCipher.doFinal(plaintext.getBytes("UTF-8")); //加密密文

        return Base64.getEncoder().encodeToString(byteCipherText); //回傳 Ciphertext
    }

    //AES解密
    public String AES_decrypt(String ciphertext, String key, String mod) throws Exception {
        Cipher getCipher = Cipher.getInstance(mod); // 設定 Operation mode
        getCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(get_IV().getBytes())); // 解密使用的 KEY 和 IV
        byte[] byteDecryptText = getCipher.doFinal(Base64.getDecoder().decode(ciphertext)); //解密密文

        return new String(byteDecryptText, "UTF-8"); //回傳解密完的結果
    }
}