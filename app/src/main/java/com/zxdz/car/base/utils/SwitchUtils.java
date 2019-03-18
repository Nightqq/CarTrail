package com.zxdz.car.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Int3;
import android.util.Base64;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import org.greenrobot.greendao.annotation.Convert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Lenovo on 2017/10/26.
 */

public class SwitchUtils {

    //IC =0不转换
    public static String ICUnconver(String carNumber) {
        char[] chars = carNumber.replaceAll(" ", "").toCharArray();
        if (chars.length == 8) {
            carNumber = chars[6] + "" + chars[7] + "" + chars[4] + "" + chars[5] + "" + chars[2] + "" + chars[3] + "" + chars[0] + "" + chars[1] + "";
            int i = Integer.parseInt(carNumber, 16);
            carNumber = i + "";
            while (carNumber.length() < 10) {
                carNumber = "0" + carNumber;
            }
            return carNumber;
        }
        return "格式错误";
    }

    //IC =1转换
    public static String ICConver(String carNumber) {
        char[] chars = carNumber.replaceAll(" ", "").toCharArray();
        if (chars.length == 8) {
            carNumber = chars[4] + "" + chars[5] + "" + chars[2] + "" + chars[3] + "" + chars[0] + "" + chars[1] + "";
            return carNumber;
        }
        return "格式错误";
    }

    //ID =0不转换
    public static String IDUnconver(String carNumber) {
        char[] chars = carNumber.replaceAll(" ", "").toCharArray();
        if (chars.length == 12) {
            carNumber = chars[8] + "" + chars[9] + "" + chars[6] + "" + chars[7] + "" + chars[4] + "" + chars[5] + "" + chars[2] + "" + chars[3] + "";
            int i = Integer.parseInt(carNumber, 16);
            carNumber = i + "";
            while (carNumber.length() < 10) {
                carNumber = "0" + carNumber;
            }
            return carNumber;
        }
        return "格式错误";
    }

    //ID =1转换
    public static String IDConver(String carNumber) {
        char[] chars = carNumber.replaceAll(" ", "").toCharArray();
        if (chars.length == 12) {
            carNumber = chars[8] + "" + chars[9] + "" + chars[6] + "" + chars[7] + "" + chars[4] + "" + chars[5] + "";
            return carNumber;
        }
        return "格式错误";
    }


    /**
     * @return 将十六进制字符串转换为字节数组
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));

        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * @return 将字节数组转换为十六进制字符串
     */
    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }
    //单个元素转换为字符串
    public static String byte2HexStr1(byte b) {
        String stmp = Integer.toHexString(b & 0xFF);
        return stmp.toUpperCase().trim();
    }
    /**
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     */
    public static byte[] getBooleanArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte)(b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }
    //16进制字符串转10进制字符串
    public static String string2Hexstr(String s){
        s = s.replaceAll(" ","");
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i <s.length() - 1 ; i+=2) {
            int p = Integer.parseInt(s.substring(i, i + 2), 16);
            stringBuffer.append(p);
        }
        return stringBuffer.toString();
    }
    //异或运算
    public static byte[] xor(byte[] old){
        byte temp = 0;
        for (int i = 0; i <old.length ; i++) {
            temp ^= old[i];
        }
        old[old.length-1] = temp;
        return old;
    }


    //加密
    public static byte[] xor34(byte[] old) {
        byte[] bytes1 = new byte[old.length];
        byte[] bytes2 = new byte[old.length + 2];
        for (int i = 0; i < old.length; i++) {
            if (i == 0) {
                bytes1[0] = old[0];
                bytes2[i] = bytes1[i];
            }
            if (i == 1) {
                bytes1[1] = ((byte) (old[1] + 0x32));
                bytes2[i] = bytes1[i];
            }
            if (i > 1) {
                bytes1[i] = old[i] ^= old[1];
                bytes2[i] = bytes1[i];
            }
        }
        char[] chars = Integer.toHexString(calcCRC(bytes1)).toCharArray();
        if (chars.length == 4) {
            bytes2[old.length] = hexStringToByte((chars[0] + "" + chars[1]).toUpperCase())[0];
            bytes2[old.length + 1] = hexStringToByte((chars[2] + "" + chars[3]).toUpperCase())[0];
        }
        return bytes2;
    }

    //解密
    public static byte[] getbyte(byte[] old) {
        byte[] bytes1 = new byte[old.length];
        for (int i = 0; i < old.length; i++) {
            if (i == 0) {
                bytes1[0] = old[0];
            }
            if (i == 1) {
                bytes1[1] = ((byte) (old[1] - 0x32));
            }
            if (i > 1 && i < old.length - 2) {
                bytes1[i] = ((byte) (old[i] ^ bytes1[1]));
            }
            if (i == old.length - 1 || i == old.length - 2) {
                bytes1[i] = old[i];
            }
        }
        return bytes1;
    }

    //开锁
    public static byte[] openLock(byte key) {
        long l = System.currentTimeMillis() / 1000;
        char[] chars = Long.toHexString(l).toCharArray();
        byte[] bytes = new byte[13];
        bytes[0] = (byte) 0xFE;
        bytes[1] = key;
        bytes[2] = key;
        bytes[3] = 0x21;
        bytes[4] = 0x08;
        bytes[5] = 0x00;
        bytes[6] = 0x00;
        bytes[7] = 0x00;
        bytes[8] = 0x00;
        bytes[9] = hexStringToByte((chars[0] + "" + chars[1]).toUpperCase())[0];
        bytes[10] = hexStringToByte((chars[2] + "" + chars[3]).toUpperCase())[0];
        bytes[11] = hexStringToByte((chars[4] + "" + chars[5]).toUpperCase())[0];
        bytes[12] = hexStringToByte((chars[6] + "" + chars[7]).toUpperCase())[0];
        byte[] bytes1 = xor34(bytes);
        return bytes1;
    }

    //开锁新
    public static byte[] openLock() {
        byte[] bytes = {0X00, 0x55, 0x00, 0x00};
        byte[] xor = xor(bytes);
        return xor;
    }

    //准备上锁
    public static byte[] closeLock() {
        byte[] bytes = {0X00, 0x4c, 0x00, 0x00};
        byte[] xor = xor(bytes);
        return xor;
    }

    //查询锁数据新
    public static byte[] enquiriesLock() {
        byte[] bytes = {0X00, 0x52, 0x00, 0x00};
        byte[] xor = xor(bytes);
        return xor;
    }

    //设置设备参数
    public static byte[] setParameters(int i) {
        byte[] bytes = new byte[6];
        if (i == 1){//id卡
            bytes = new byte[]{0X00, 0x50, 0x02, 0x00, 0x00,0x00};
        }else if (i == 2){
            bytes = new byte[]{0X00, 0x50, 0x02, 0x01, (byte) 0xFF,0x00};
        }
        byte[] xor = xor(bytes);
        return xor;
    }

    //查询锁状态
    public static byte[] enquiriesLock(byte key) {
        byte[] bytes = new byte[5];
        bytes[0] = (byte) 0xFE;
        bytes[1] = key;
        bytes[2] = key;
        bytes[3] = 0x31;
        bytes[4] = 0x00;
        byte[] bytes1 = xor34(bytes);
        return bytes1;
    }

    //获取锁数据
    public static byte[] getLockData(byte key) {
        byte[] bytes = new byte[5];
        bytes[0] = (byte) 0xFE;
        bytes[1] = key;
        bytes[2] = key;
        bytes[3] = 0x51;
        bytes[4] = 0x00;
        byte[] bytes1 = xor34(bytes);
        return bytes1;
    }

    //清除锁数据
    public static byte[] clearLock(byte key) {
        byte[] bytes = new byte[5];
        bytes[0] = (byte) 0xFE;
        bytes[1] = key;
        bytes[2] = key;
        bytes[3] = 0x52;
        bytes[4] = 0x00;
        byte[] bytes1 = xor34(bytes);
        return bytes1;
    }

    //----------------------------------------------CRC16 计算代码---------------------------------------------------
    static byte[] t_crc16_h = {
            0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0,
            (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41,
            0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0,
            (byte) 0x80, 0x41, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40,
            0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1,
            (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0, (byte) 0x80, 0x41,
            0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1,
            (byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41,
            0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0,
            (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81, 0x40,
            0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1,
            (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40,
            0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0,
            (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81, 0x40,
            0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0,
            (byte) 0x80, 0x41, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40,
            0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0,
            (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41,
            0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0,
            (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41,
            0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0,
            (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81, 0x40,
            0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1,
            (byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41,
            0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0,
            (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40};
    static byte[] t_crc16_l = {
            0x00, (byte) 0xC0, (byte) 0xC1, 0x01, (byte) 0xC3, 0x03, 0x02, (byte) 0xC2, (byte) 0xC6, 0x06,
            0x07, (byte) 0xC7, 0x05, (byte) 0xC5, (byte) 0xC4, 0x04, (byte) 0xCC, 0x0C, 0x0D, (byte) 0xCD,
            0x0F, (byte) 0xCF, (byte) 0xCE, 0x0E, 0x0A, (byte) 0xCA, (byte) 0xCB, 0x0B, (byte) 0xC9, 0x09,
            0x08, (byte) 0xC8, (byte) 0xD8, 0x18, 0x19, (byte) 0xD9, 0x1B, (byte) 0xDB, (byte) 0xDA, 0x1A,
            0x1E, (byte) 0xDE, (byte) 0xDF, 0x1F, (byte) 0xDD, 0x1D, 0x1C, (byte) 0xDC, 0x14, (byte) 0xD4,
            (byte) 0xD5, 0x15, (byte) 0xD7, 0x17, 0x16, (byte) 0xD6, (byte) 0xD2, 0x12, 0x13, (byte) 0xD3,
            0x11, (byte) 0xD1, (byte) 0xD0, 0x10, (byte) 0xF0, 0x30, 0x31, (byte) 0xF1, 0x33, (byte) 0xF3,
            (byte) 0xF2, 0x32, 0x36, (byte) 0xF6, (byte) 0xF7, 0x37, (byte) 0xF5, 0x35, 0x34, (byte) 0xF4,
            0x3C, (byte) 0xFC, (byte) 0xFD, 0x3D, (byte) 0xFF, 0x3F, 0x3E, (byte) 0xFE, (byte) 0xFA, 0x3A,
            0x3B, (byte) 0xFB, 0x39, (byte) 0xF9, (byte) 0xF8, 0x38, 0x28, (byte) 0xE8, (byte) 0xE9, 0x29,
            (byte) 0xEB, 0x2B, 0x2A, (byte) 0xEA, (byte) 0xEE, 0x2E, 0x2F, (byte) 0xEF, 0x2D, (byte) 0xED,
            (byte) 0xEC, 0x2C, (byte) 0xE4, 0x24, 0x25, (byte) 0xE5, 0x27, (byte) 0xE7, (byte) 0xE6, 0x26,
            0x22, (byte) 0xE2, (byte) 0xE3, 0x23, (byte) 0xE1, 0x21, 0x20, (byte) 0xE0, (byte) 0xA0, 0x60,
            0x61, (byte) 0xA1, 0x63, (byte) 0xA3, (byte) 0xA2, 0x62, 0x66, (byte) 0xA6, (byte) 0xA7, 0x67,
            (byte) 0xA5, 0x65, 0x64, (byte) 0xA4, 0x6C, (byte) 0xAC, (byte) 0xAD, 0x6D, (byte) 0xAF, 0x6F,
            0x6E, (byte) 0xAE, (byte) 0xAA, 0x6A, 0x6B, (byte) 0xAB, 0x69, (byte) 0xA9, (byte) 0xA8, 0x68,
            0x78, (byte) 0xB8, (byte) 0xB9, 0x79, (byte) 0xBB, 0x7B, 0x7A, (byte) 0xBA, (byte) 0xBE, 0x7E,
            0x7F, (byte) 0xBF, 0x7D, (byte) 0xBD, (byte) 0xBC, 0x7C, (byte) 0xB4, 0x74, 0x75, (byte) 0xB5,
            0x77, (byte) 0xB7, (byte) 0xB6, 0x76, 0x72, (byte) 0xB2, (byte) 0xB3, 0x73, (byte) 0xB1, 0x71,
            0x70, (byte) 0xB0, 0x50, (byte) 0x90, (byte) 0x91, 0x51, (byte) 0x93, 0x53, 0x52, (byte) 0x92,
            (byte) 0x96, 0x56, 0x57, (byte) 0x97, 0x55, (byte) 0x95, (byte) 0x94, 0x54, (byte) 0x9C, 0x5C,
            0x5D, (byte) 0x9D, 0x5F, (byte) 0x9F, (byte) 0x9E, 0x5E, 0x5A, (byte) 0x9A, (byte) 0x9B, 0x5B,
            (byte) 0x99, 0x59, 0x58, (byte) 0x98, (byte) 0x88, 0x48, 0x49, (byte) 0x89, 0x4B, (byte) 0x8B,
            (byte) 0x8A, 0x4A, 0x4E, (byte) 0x8E, (byte) 0x8F, 0x4F, (byte) 0x8D, 0x4D, 0x4C, (byte) 0x8C,
            0x44, (byte) 0x84, (byte) 0x85, 0x45, (byte) 0x87, 0x47, 0x46, (byte) 0x86, (byte) 0x82, 0x42,
            0x43, (byte) 0x83, 0x41, (byte) 0x81, (byte) 0x80, 0x40};

    private static int calcCRC(byte[] data) {
        return calcCRC(data, 0, data.length);
    }

    private static int calcCRC(byte[] data, int offset, int len) {
        return calcCRC(data, offset, len, 0xffff);
    }

    private static int calcCRC(byte[] data, int offset, int len, int preval) {
        int ucCRCHi = (preval & 0xff00) >> 8;
        int ucCRCLo = preval & 0x00FF;
        int iIndex;
        for (int i = 0; i < len; i++) {
            iIndex = (ucCRCLo ^ data[offset + i]) & 0x00FF;
            ucCRCLo = ucCRCHi ^ t_crc16_h[iIndex];
            ucCRCHi = t_crc16_l[iIndex];
        }
        return ((ucCRCHi & 0x00FF) << 8) | (ucCRCLo & 0x00FF) & 0xFFFF;
    }


    public static String getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        LogUtils.a("图片尺寸"+bitmap.getWidth(),bitmap.getHeight());
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = out.toByteArray();
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String encodeString = new String(encode);
        return encodeString;
    }

    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
