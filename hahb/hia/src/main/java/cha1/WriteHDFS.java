package cha1;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


/**
 * User: mzang
 * Date: 9/9/13
 * Time: 3:43 PM
 */
public class WriteHDFS {

    public static void main(String[] args) {
        createAndWrite();
        appendData();
    }

    static void appendData() {
        try {
            Configuration conf = new Configuration();
            conf.addResource("hdfs-site.xml");
            FileSystem fs = FileSystem.get(conf);
            Path path = new Path("/tmp/test/datafile.txt");
            FSDataOutputStream outputStream = fs.append(path);
            for (int i = 0; i < 100; i++) {
//                for (int j = 0; j < 1024; j++) {
                for (int bs = 0; bs < 10; bs++) {
                    outputStream.write("appendtestdata".getBytes());
                }
                outputStream.write("\n".getBytes());
//                }
            }
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void createAndWrite() {
        try {
            Configuration conf = new Configuration();
            conf.addResource("hdfs-site.xml");
            FileSystem fs = FileSystem.get(conf);
            Path path = new Path("/tmp/test/datafile.txt");
            FSDataOutputStream outputStream = fs.create(path);
            for (int i = 0; i < 100; i++) {
//                for (int j = 0; j < 1024; j++) {
                for (int bs = 0; bs < 10; bs++) {
                    outputStream.write("testdata".getBytes());
                }
                outputStream.write("\n".getBytes());
//                }
            }
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
