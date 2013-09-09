package cha1;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
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
        try {
            Configuration conf = new Configuration();
            conf.addResource("hdfs-site.xml");
            FileSystem fs = FileSystem.get(conf);
            Path path = new Path("/tmp/test/datafile.txt");
            FSDataInputStream outputStream = fs.open(path);
            for (int i = 0; i < 100; i++) {
                for (int bs = 0; bs < 1024 * 1024; bs++) {
                    outputStream.wr
                }
            }
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
