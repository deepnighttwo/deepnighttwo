package com.deepnighttwo;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Mark Zang
 * Date: 2016/7/4
 * Time: 21:39
 */
public class FilerRenamer {

    static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");

    public static void main(String[] args) throws IOException {
//        File f = new File("C:\\Users\\Mark Zang\\Desktop\\宝宝手机\\备份\\2016年7月4日", "IMG_1717.JPG");
//        System.out.println(f.exists());
//        System.out.println(f.isFile());
//        Date date = new Date(f.lastModified());
//        System.out.println(f.lastModified());
//        System.out.println(sdfDate.format(date));
//        String ext = Files.getFileExtension(f.toString());
//        System.out.println(ext);
        String target = "C:\\Users\\Mark Zang\\Desktop\\target";
        String source = "C:\\Users\\Mark Zang\\Desktop\\新照片";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("目标目录：" + target + "。包含文件数目：" +getFileCount(target));
            System.out.println("源目录：" + source + "。包含文件数目：" + getFileCount(source));

            System.out.println("目标目录：" + target);
            System.out.println("更改为：");
            String line = br.readLine().trim();
            if (StringUtils.isNotBlank(line)) {
                target = line;
            }
            System.out.println("源目录：" + source);
            System.out.println("更改为：");
            line = br.readLine().trim();
            if (StringUtils.isNotBlank(line)) {
                source = line;
            }
            System.out.println("目标目录：" + target + "。包含文件数目：" +getFileCount(target));
            System.out.println("源目录：" + source + "。包含文件数目：" + getFileCount(source));
            System.out.println("单击Enter确定。其它键重新输入……");
            line = br.readLine().trim();
            if (StringUtils.isBlank(line)) {
                System.out.println("开始文件拷贝和重命名，再次确认信息：");
                System.out.println("目标目录：" + target + "。包含文件数目：" +getFileCount(target));
                System.out.println("源目录：" + source + "。包含文件数目：" + getFileCount(source));
                System.out.println("单击Enter确定。其它键重新输入……");
                line = br.readLine().trim();
                if (StringUtils.isBlank(line)) {
                    renameAndCopyFiles(target, source);
                    break;
                }else {
                        continue;
                }
            }
        }
    }

    static int getFileCount(String dir) {
        try {
            return (new File(dir)).list().length;
        } catch (Exception ex) {
            return -1;
        }
    }

    public static void renameAndCopyFiles(String targetDir, String... sourceTarget) throws IOException {
        File target = new File(targetDir);

        Files.createParentDirs(target);

        for (String src : sourceTarget) {
            File dir = new File(src);
            for (File file : dir.listFiles()) {
                Date date = new Date(file.lastModified());
                String ext = Files.getFileExtension(file.toString());
                File newFile = new File(target, sdfDate.format(date) + "." + ext);
                if (newFile.isFile()) {
                    System.out.println("same modify time file found:" + file);
                    if (file.length() == newFile.length()) {
                        System.out.println("file size the same. comparing hash");
                        HashCode hashNew = Files.asByteSource(newFile).hash(Hashing.sha1());
                        HashCode hashOrigin = Files.asByteSource(file).hash(Hashing.sha1());
                        System.out.println("Origin hash=" + hashOrigin + ", New hash=" + hashNew);
                        if (hashNew.equals(hashOrigin)) {
                            System.out.println("file hash same, skipping");
                            continue;
                        } else {
                            System.out.println("hash not the same, using a new name");
                            newFile = new File(target, sdfDate.format(date) + "-" + (int) (Math.random() * 100000) + "." + ext);
                        }
                    } else {
                        System.out.println("file size not the same. rename the new file");
                        newFile = new File(target, sdfDate.format(date) + "-" + (int) (Math.random() * 100000) + "." + ext);
                    }
                }

//            HashCode hash = Files.asByteSource(file).hash(Hashing.sha1());
                Files.move(file, newFile);

            }
        }

    }

}
