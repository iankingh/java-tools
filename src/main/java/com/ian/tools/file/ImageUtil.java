package com.ian.tools.file;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageUtil {

    /**
     * 將 base64 的字串還原成圖檔，並存到指定檔案。
     * 
     * @param base64       base64圖檔
     * @param destFilename 存檔的檔名
     * @return true 成功, false 失敗
     */
    public static boolean save(String base64, String destFilename) {
        log.debug("path : " + destFilename);
        log.debug("base64 : " + base64);
        // 宣告 資料流
        ByteArrayInputStream byteArrayInputStream = null;
        // 宣告 檔案流
        FileOutputStream fileOutputStream = null;
        try {
            // 取得 decode Base64 字串
            Optional<byte[]> optImage = decodeBase64(base64);
            log.debug("optImage : " + optImage.get());
            if (optImage.isPresent()) {
                byte[] image = optImage.get();
                // 建立 資料流
                byteArrayInputStream = new ByteArrayInputStream(image);
                // 建立 檔案流
                fileOutputStream = new FileOutputStream(destFilename);
                // 設定傳輸大小
                int buf_size = 1024;
                byte[] buffer = new byte[buf_size];
                int len = 0;
                while (-1 != (len = byteArrayInputStream.read(buffer, 0, buf_size))) {
                    fileOutputStream.write(buffer, 0, len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                byteArrayInputStream.close();
            }
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            try {
                if (null != byteArrayInputStream) {
                    byteArrayInputStream.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return false;
    }

    /**
     * 轉換 base64 字串 (UTF-8 編碼) 為 byte[]
     * 
     * @param base64
     * @return
     */
    public static Optional<byte[]> decodeBase64(String base64) {
        try {
            String partSeparator = ",";
            if (base64.contains(partSeparator)) {
                String[] parts = base64.split(partSeparator);
                if (parts != null && parts.length >= 2) {
                    base64 = parts[1];
                }
            }
            return Optional.of(Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

}
