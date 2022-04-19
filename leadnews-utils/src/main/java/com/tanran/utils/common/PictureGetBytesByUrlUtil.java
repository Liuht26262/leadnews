package com.tanran.utils.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 网络图片字节大小获取工具类
 * @since 2022/4/15 10:43
 */
public class PictureGetBytesByUrlUtil {

    //根据url获取图片
    public static byte[] getImageFromURL(String urlPath) {

        byte[] data = null;

        InputStream is = null;

        HttpURLConnection conn = null;

        try {

            URL url = new URL(urlPath);

            conn = (HttpURLConnection) url.openConnection();

            conn.setDoInput(true);

            conn.setRequestMethod("GET");

            conn.setConnectTimeout(6000);

            is = conn.getInputStream();

            if (conn.getResponseCode() == 200) {

                data = readInputStream(is);

            } else{

                data=null;

            }

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if(is != null){

                    is.close();

                }

            } catch (IOException e) {

                e.printStackTrace();

            }

            conn.disconnect();

        }

        return data;

    }

    /**

     * 将流转换为字节

     * @param is

     * @return

     */

    public static byte[] readInputStream(InputStream is) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];

        int length = -1;

        try {

            while ((length = is.read(buffer)) != -1) {

                baos.write(buffer, 0, length);

            }

            baos.flush();

        } catch (IOException e) {

            e.printStackTrace();

        }

        byte[] data = baos.toByteArray();

        try {

            is.close();

            baos.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return data;

    }
}
