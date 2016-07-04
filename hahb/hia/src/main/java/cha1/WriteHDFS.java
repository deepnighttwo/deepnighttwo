package cha1;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.util.Random;


/**
 * User: mzang
 * Date: 9/9/13
 * Time: 3:43 PM
 */
public class WriteHDFS {

    /**
     * hadoop 客户端与集群连接的配置文件。无论和集群怎么互动，都要先加载这个配置。不同的集群配置不同。
     */
    static Configuration conf;

    static {
        Configuration conf = new Configuration();
        conf.addResource("hdfs-site.xml");
    }

    public static void main(String[] args) {
//        createAndWrite();
//        appendData();
        createTestData(1000, 100);
    }

    static void appendData() {
        try {
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

    static void createTestData(final int fileCount, final int lineCount) {
        try {
            final FileSystem fs = FileSystem.get(conf);

            for (int i = 0; i < fileCount; i++) {
                final Path path = new Path("/tmp/test/input/cc" + i + ".txt");
                new Thread() {
                    public void run() {
                        try {
                            Random random = new Random();
                            int lineSize = 10;
                            byte[] content = new byte[lineSize + 1];

                            FSDataOutputStream fsos = fs.create(path, true);

                            for (int j = 0; j < lineCount; j++) {
                                for (int k = 0; k < lineSize; k++) {
                                    content[k] = (byte) ('A' + (random.nextDouble() * 26));
                                }
                                content[lineSize] = '\n';
                                fsos.write(content);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }.start();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
