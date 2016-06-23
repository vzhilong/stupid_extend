package com.stupid.extend.lang;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by vincent on 16/6/4.
 */
public class IOUtils {

    public static byte[] unZipData(byte[] data) {
        if (null == data || 0 == data.length) {
            return null;
        }

        byte[] result = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            GZIPInputStream gis = new GZIPInputStream(bais);

            byte[] tmpBuf = new byte[4096];
            int readlen = 0;
            while ((readlen = gis.read(tmpBuf)) != -1) {
                baos.write(tmpBuf, 0, readlen);
            }

            gis.close();
            result = baos.toByteArray();

            bais.close();
            baos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
