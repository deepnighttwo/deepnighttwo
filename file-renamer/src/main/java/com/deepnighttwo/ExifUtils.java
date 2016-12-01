package com.deepnighttwo;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * User: Mark Zang
 * Date: 2016/11/7
 * Time: 22:16
 */
public class ExifUtils {

    static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");


    public static void main(String[] args) {
        Date date = getDateFromFile(new File("test.JPG"));
        System.out.println("aaaaa:" + date);
    }

    public static Date getDateFromFile(File file) {
        Date dateModified = new Date(file.lastModified());
        Date dateExif = null;
        Date dateOriginal = null;
        Date dateDigitized = null;
        try {
            Metadata metadata = JpegMetadataReader.readMetadata(file);
            Iterable<Directory> exif = metadata.getDirectories();
            for (Directory directory : exif) {
                Collection<Tag> tags = directory.getTags();
                for (Tag tag : tags) {
                    if ("Date/Time".equals(tag.getTagName())) {
                        dateExif = sdfDate.parse(tag.getDescription());
                    } else if ("Date/Time Original".equals(tag.getTagName())) {
                        dateOriginal = sdfDate.parse(tag.getDescription());

                    } else if ("Date/Time Digitized".equals(tag.getTagName())) {
                        dateDigitized = sdfDate.parse(tag.getDescription());

                    }
                    if (tag.getDescription() != null && tag.getDescription().contains("201")) {
                        System.out.println(tag.getTagName() + ":" + tag.getDescription());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No EXIF found for " + file + ". Using lastModifyTime.");
        }
        if (dateOriginal != null) {
            return dateOriginal;
        }
        if (dateExif != null) {
            return dateExif;
        }
        if (dateDigitized != null) {
            return dateDigitized;
        }
        return dateModified;
    }
}
