package com.jianfeng.xiaomianao.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtil {

    private static Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    private static String ZIP_ENCODEING = HttpUtil.UTF8;

    public static String zip(String dir, String filename, String content) {
        return zip(dir, filename, content, true);
    }

    public static String zip(String dir, String filename, String content, boolean createOldfile) {
        String zipfilename = filename;
        if (!filename.endsWith(".zip")) {
            zipfilename = StringUtil.join(".", filename, "zip");
        }
        File file = new File(mkdir(dir), zipfilename);
        createOldFile(file, createOldfile);
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            zos.setEncoding(ZIP_ENCODEING);
            compress(zos, content, filename);
        } catch (Exception e) {
            logger.error("创建压缩文件出错, filename:" + filename, e);
            return null;
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
            }
        }
        return file.getAbsolutePath();
    }

    private static void createOldFile(File file, boolean createOldfile) {
        if (!createOldfile) {
            return;
        }
        if (file.exists()) {
            try {
                FileUtil.copyFile(file, new File(StringUtil.join("", file.getCanonicalPath(), ".old")));
            } catch (Exception e) {
                logger.error("创建old文件出错,file:" + file.getPath(), e);
            }
        }
    }

    private static File mkdir(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static void compress(ZipOutputStream zos, String content, String filename) {
        try {
            zos.putNextEntry(new ZipEntry(""));
            zos.write(content.getBytes());
        } catch (IOException e) {
            logger.error("创建压缩文件出错, filename:" + filename, e);
        } finally {
            try {
                zos.closeEntry();
            } catch (IOException e) {
            }
        }
    }

    public static String unZip(String filename) {
        InputStream is = null;
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(filename, ZIP_ENCODEING);
            ZipEntry ze = zipFile.getEntries().nextElement();
            is = zipFile.getInputStream(ze);
            in = new BufferedInputStream(is);
            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            return new String(out.toByteArray());
        } catch (Exception e) {
            logger.error("解压缩文件出错, filename:" + filename, e);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
            try {
                zipFile.close();
            } catch (Exception e) {
            }

        }
        return null;
    }
}
