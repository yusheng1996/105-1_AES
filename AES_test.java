import java.util.*;
import java.text.DecimalFormat;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AES_test {
    public static void main(String[] args) throws Exception {
        DecimalFormat df = new DecimalFormat("#,#00"); //格式化數字的輸出
        Scanner sc = new Scanner(System.in);
        AES using_aes = new AES();

        System.out.printf("Please Enter The Plaintext : ");
        String plaintext = sc.nextLine(); //輸入欲加密的密文

        String cs_key; //給予使用者輸入 Key-bits 的選擇
        do {
            System.out.printf("\nPlease Choose the Length of Key (128 / 256) bits : ");
            cs_key = sc.nextLine();
            if (!(cs_key.equals("128") || cs_key.equals("256"))) //偵測使用者是否輸入正確
                System.out.println("The Length of Key is ERROR !");
        } while (!(cs_key.equals("128") || cs_key.equals("256"))); //輸入錯誤，則要求重新輸入一次
        
        String mode; //給予使用者輸入 Operation Mode 的選擇
        do {
            System.out.printf("\nPlease Choose the Operation Mode (CBC / CFB / CFB8) : ");
            mode = sc.nextLine();
            if (!(mode.equals("CBC") || mode.equals("CFB") || mode.equals("CFB8"))) //偵測使用者是否輸入正確
                System.out.println("The Operation Mode is NOT FOUND !");
        } while (!(mode.equals("CBC") || mode.equals("CFB") || mode.equals("CFB8"))); //輸入錯誤，則要求重新輸入一次
        mode = "AES/" + mode + "/PKCS5Padding"; //改成Cipher 工具所需要的 mode 傳入格式

        String key = "";
        Random using_rd = new Random(); //取得亂數
        for(int i = 0; i < (Integer.parseInt(cs_key) / 8); i++) {
            int j = using_rd.nextInt(62); //KEY由10個數字以及26個英文大小寫所組成，故有62種字元可能
            if(j >= 0 && j <= 9) //當j介於 0~9 則直接代表數字字元
                key += Integer.toString(j);
            else if (j >= 10 && j <= 35) //當j介於 10~35 則代表大寫英文字母
                key += (char)(j + 55);
            else //當j介於 36~62 則代表小寫英文字母
                key += (char)(j + 61);
        }

        System.out.printf("\n=========ENCRYPTING=========\n\n");
        long timing = System.nanoTime(); //紀錄開始時間
        String ciphertext = using_aes.AES_encrypt(plaintext, key, mode); //加密
        System.out.println("It takes " + df.format((System.nanoTime() - timing)) + " nanoseconds."); //輸出所花時間
        System.out.println("The ciphertext :\n" + ciphertext); //輸出 Ciphertext

        System.out.printf("\n=========DECRYPTING=========\n\n");
        timing = System.nanoTime(); //紀錄開始時間
        String decrypttext = using_aes.AES_decrypt(ciphertext, key, mode); //解密
        System.out.println("It takes " + df.format((System.nanoTime() - timing)) + " nanoseconds."); //輸出所花時間
        System.out.println("The Decrypted Plaintext :\n" + decrypttext); //輸出解密結果
    }
}