package com.stupid.extend.lang;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by vincent on 16/6/4.
 */
public class WebStringUtils {

    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        return StringUtils.EMPTY;
    }

    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        return StringUtils.EMPTY;
    }

    public static final char[] base64Encode(String str) {
        if (str == null) {
            return null;
        }

        try {
            return base64Encode(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {

        }

        return null;
    }

    /**
     * Base64 编码实现
     * 原理：
     * 3字节按照编码规则转变为4个字节。
     */
    public static final char[] base64Encode(byte[] aData) {
        char[] sEncodeString = new char[((aData.length + 2) / 3) * 4];

        //
        // 3 bytes encode to 4 chars.  Output is always an even
        // multiple of 4 characters.
        //
        for (int i = 0, index = 0; i < aData.length; i += 3, index += 4) {
            boolean sQuad = false; // 是否字节尾数只存在2个字节
            boolean sTrip = false; // 是否字节尾数只存在1个字节

            int sVal = (0xFF & (int) aData[i]);
            sVal <<= 8;
            if ((i + 1) < aData.length) {
                sVal |= (0xFF & (int) aData[i + 1]);
                sTrip = true;
            }
            sVal <<= 8;
            if ((i + 2) < aData.length) {
                sVal |= (0xFF & (int) aData[i + 2]);
                sQuad = true;
            }
            sEncodeString[index + 3] = (char) (sQuad ? base64Convert((byte) (sVal & 0x3F), 0) : 61);
            sVal >>= 6;
            sEncodeString[index + 2] = (char) (sTrip ? base64Convert((byte) (sVal & 0x3F), 0) : 61);
            sVal >>= 6;
            sEncodeString[index + 1] = (char) base64Convert((byte) (sVal & 0x3F), 0);
            sVal >>= 6;
            sEncodeString[index + 0] = (char) base64Convert((byte) (sVal & 0x3F), 0);
        }
        return sEncodeString;

    }

    /**
     * base64解码
     */
    public static byte[] base64Decode(byte[] aData) {
        int sLen = aData.length;
        while (sLen > 0 && aData[sLen - 1] == '=') sLen--;
        int sOutLen = (sLen * 3) / 4;
        byte[] sOut = new byte[sOutLen];
        int sInPoint = 0;
        int sOutPointp = 0;
        while (sInPoint < sLen) {
            int i0 = aData[sInPoint++];
            int i1 = aData[sInPoint++];
            int i2 = sInPoint < sLen ? aData[sInPoint++] : 'A';
            int i3 = sInPoint < sLen ? aData[sInPoint++] : 'A';
            if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127) {
                return sOut;
            }
            int b0 = (int) base64Convert((byte) i0, 1);
            int b1 = (int) base64Convert((byte) i1, 1);
            int b2 = (int) base64Convert((byte) i2, 1);
            int b3 = (int) base64Convert((byte) i3, 1);
            if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0) {
                return sOut;
            }
            int o0 = (b0 << 2) | (b1 >>> 4);
            int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
            int o2 = ((b2 & 3) << 6) | b3;
            sOut[sOutPointp++] = (byte) o0;
            if (sOutPointp < sOutLen) sOut[sOutPointp++] = (byte) o1;
            if (sOutPointp < sOutLen) sOut[sOutPointp++] = (byte) o2;
        }
        return sOut;
    }

    /**
     * @param aInputByte byte
     * @param aType      int 转换的类型 0->正传，从不可见字符到可见字符,1->反转:)
     * @return byte
     */
    private static final byte base64Convert(byte aInputByte, int aType) {
        byte sOutputByte = 0;
        if (aType == 0) {
            if (aInputByte <= 25) {
                //大写字母:)
                sOutputByte = (byte) (65 + aInputByte);
            } else if (aInputByte >= 26 && aInputByte <= 51) {
                //小写字母:)
                sOutputByte = (byte) (71 + aInputByte);
            } else if (aInputByte >= 52 && aInputByte <= 61) {
                //数字:)
                sOutputByte = (byte) (aInputByte - 4);
            } else if (aInputByte == 62) {
                //'+'
                sOutputByte = 43;
            } else if (aInputByte == 63) {
                //'/'
                sOutputByte = 47;
            } else if (aInputByte == 64) {
                //'='
                sOutputByte = 61;
            }
        } else if (aType == 1) {
            if (aInputByte >= 48 && aInputByte <= 57) {
                //数字:)
                sOutputByte = (byte) (4 + aInputByte);
            } else if (aInputByte >= 65 && aInputByte <= 90) {
                //大写字母:)
                sOutputByte = (byte) (aInputByte - 65);
            } else if (aInputByte >= 97 && aInputByte <= 122) {
                //小写字母:)
                sOutputByte = (byte) (aInputByte - 71);
            } else if (aInputByte == 43) {
                //'+'
                sOutputByte = 62;
            } else if (aInputByte == 47) {
                //'/'
                sOutputByte = 63;
            } else if (aInputByte == 61) {
                //'='
                sOutputByte = 64;
            }
        }
        return sOutputByte;
    }
}
