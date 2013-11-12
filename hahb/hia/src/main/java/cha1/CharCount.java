package cha1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mzang
 * Date: 10/31/13
 * Time: 4:21 PM
 */
public class CharCount extends Configured implements Tool {

    public int run(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


        Job job = Job.getInstance(this.getConf());

        job.setJarByClass(CharCount.class);
        FileOutputFormat.setOutputPath(job, new Path("/tmp/test/output"));

        FileInputFormat.setInputPaths(job, new Path("/tmp/test/input"));

        job.setMapOutputKeyClass(ByteWritable.class);
        job.setMapOutputValueClass(LongWritable.class);

        job.setOutputFormatClass(TextOutputFormat.class);
        job.setMapperClass(CMapper.class);
        job.setCombinerClass(CCombiner.class);
        job.setReducerClass(CReducer.class);

        job.submit();

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.addResource("hdfs-site.xml");
//        conf.setBoolean("mapred.compress.map.output", false);
//        conf.setBoolean("mapred.output.compress", false);

        int res = ToolRunner.run(conf, new CharCount(), args);

        System.exit(res);
    }

    public static class CMapper extends Mapper<LongWritable, Text, ByteWritable, LongWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            byte[] content = value.getBytes();
            Map<ByteWritable, Integer> map = new HashMap<ByteWritable, Integer>();
            for (byte b : content) {
                ByteWritable bw = new ByteWritable(b);
                Integer c = map.get(bw);
                if (c == null) {
                    map.put(bw, 1);
                } else {
                    c++;
                    map.put(bw, c);
                }
            }
            for (Map.Entry<ByteWritable, Integer> entry : map.entrySet()) {
                context.write(entry.getKey(), new LongWritable(entry.getValue()));
            }
        }
    }

    public static class CCombiner extends Reducer<ByteWritable, LongWritable, ByteWritable, LongWritable> {

        @Override
        protected void reduce(ByteWritable key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

            long sum = 0;
            for (LongWritable longWritable : values) {
                sum += longWritable.get();
            }

            context.write(key, new LongWritable(sum));

        }
    }

    public static class CReducer extends Reducer<ByteWritable, LongWritable, Text, Text> {

        @Override
        protected void reduce(ByteWritable key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

            long sum = 0;
            for (LongWritable longWritable : values) {
                sum += longWritable.get();
            }

            context.write(new Text(String.valueOf((char) key.get())), new Text(String.valueOf(sum)));
        }
    }

}

