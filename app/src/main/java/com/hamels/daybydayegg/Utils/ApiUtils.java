package com.hamels.daybydayegg.Utils;

import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ApiUtils {
    public static final String TAG = ApiUtils.class.getSimpleName();
    private static final String secretKey = "9EFD00BC516A1AD4C3F7A40BEC8236D1";
    private static final String ivKey = "qazwsxedcrfvtgby";

    public static String decryption(String encodingString) {
        try {
            Cipher mCipher = getAesCipher(false);

            //step1 replace (1) '~' to '/' (2) '!' to '+' (3) '@' to '='
            byte[] resultGetBytes = replaceCharToDecode(encodingString).getBytes("UTF-8");
            //step2 base64 decode
            byte[] stringByte = Base64.decode(resultGetBytes, Base64.DEFAULT);
            //step3 aes decode
            byte[] byteResult = mCipher.doFinal(stringByte);

            return new String(byteResult, "UTF-8");
        } catch (Exception e) {
            return "decode error: " + e.getMessage();
        }
    }

    public static String encryption(String text) {
        try {
            Cipher mCipher = getAesCipher(true);
            //step1 aes encode
            byte[] encodeByte = mCipher.doFinal(text.getBytes());
            //step2 base64 encode
            byte[] base64Byte = Base64.encode(encodeByte, Base64.DEFAULT);
            //step3 replace (1) '/' to '~' (2) '+' to '!' (3) '@' to '='
            String string = new String(base64Byte);

            return replaceCharToEncode(string);
        } catch (Exception e) {
            return "encode error: " + e.getMessage();
        }
    }

    private static Cipher getAesCipher(boolean isEncrypt) {
        SecretKeySpec spec = new SecretKeySpec(secretKey.getBytes(), "AES");
        AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(ivKey.getBytes());
        try {
            Cipher mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            mCipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, spec, mAlgorithmParameterSpec);
            return mCipher;
        } catch (Exception e) {
            Log.e(TAG, "AES cipher init fail");
            return null;
        }
    }

    private static String replaceCharToDecode(String string) {
        return string.replaceAll("~", "/").replaceAll("!", "+").replaceAll("@", "=");
    }

    private static String replaceCharToEncode(String string) {
        return string.replaceAll("/", "~").replaceAll("\\+", "!").replaceAll("=", "@");
    }


    public static String getEncodeStringParams(Map map) {
        String params = new JSONObject(map).toString();
        return encryption(params);
    }
}
