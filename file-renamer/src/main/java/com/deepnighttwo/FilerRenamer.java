package com.deepnighttwo;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
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
        File f = new File("C:\\Users\\Mark Zang\\Desktop\\宝宝手机\\备份\\2016年7月4日", "IMG_1717.JPG");
        System.out.println(f.exists());
        System.out.println(f.isFile());
        Date date = new Date(f.lastModified());
        System.out.println(f.lastModified());
        System.out.println(sdfDate.format(date));
        String ext = Files.getFileExtension(f.toString());
        System.out.println(ext);
//        renameFiles("C:\\Users\\Mark Zang\\Desktop\\宝宝手机\\备份\\2016年7月4日");
        renameAndCopyFiles("C:\\Users\\Mark Zang\\Desktop\\宝宝手机\\所有照片视频",
                "C:\\Users\\Mark Zang\\Desktop\\宝宝手机\\备份 - 副本\\2016年6月5日",
                "C:\\Users\\Mark Zang\\Desktop\\宝宝手机\\备份 - 副本\\2016年7月4日");
    }

    public static void renameFiles(String targetDir) throws IOException {
        File dir = new File(targetDir);
        for (File file : dir.listFiles()) {
            Date date = new Date(file.lastModified());
            String ext = Files.getFileExtension(file.toString());
            File newFile = new File(dir, sdfDate.format(date) + "." + ext);

//            HashCode hash = Files.asByteSource(file).hash(Hashing.sha1());
            Files.move(file, newFile);
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
