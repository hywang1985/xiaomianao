package com.jianfeng.xiaomianao.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FTPUtil {

    private Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    public boolean ftpdownload(String hostname, int port, String ftpdir, String localdir, String filename, String ftpmode,
            String charset, String user, String pwd) {
        logger.info("开始下载{}", filename);
        FTPClient ftp = new FTPClient();
        FileOutputStream fos = null;
        makedir(localdir);
        File filepath = new File(localdir, filename);
        try {
            logger.info("开始连接{},{}", new String[] { hostname, Integer.toString(port) });

            ftp.connect(hostname, port);
            logger.info("连接成功{}{}", new String[] { hostname, Integer.toString(port) });
            ftp.login(user, pwd);
            logger.info("登录成功{}{}", new String[] { user, pwd });
            if (ftpmode.equals("PASV")) {
                ftp.enterLocalPassiveMode();
                logger.info("设置为被动模式{}{}", new String[] { hostname, Integer.toString(port) });
            } else {
                ftp.enterLocalActiveMode();
                logger.info("设置为主动模式{}{}", new String[] { hostname, Integer.toString(port) });
            }
            ftp.changeWorkingDirectory(ftpdir);
            logger.info("查找路径{}", new String[] { ftpdir });
            fos = new FileOutputStream(filepath);
            ftp.setBufferSize(1024);
            ftp.setControlEncoding(charset);
            // 设置文件类型（二进制）
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            logger.info("设置为二进制模式", new String[] { ftpdir });
            boolean isSuccess = ftp.retrieveFile(filename, fos);
            logger.info("下载{}结束，结果为{}", new String[] { filename, Boolean.toString(isSuccess) });
            return isSuccess;
        } catch (Exception e) {
            logger.error("ftp下载失败, filename:" + filename, e);
            filepath.delete();
            return false;
        } finally {
            try {
                fos.close();
                ftp.disconnect();
            } catch (Exception e) {
            }
        }
    }

    private void makedir(String dir) {
        File fdir = new File(dir);
        if (!fdir.exists()) {
            fdir.mkdirs();
        }
    }
}
