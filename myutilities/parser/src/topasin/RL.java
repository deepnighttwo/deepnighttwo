package topasin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RL {

    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        String filepath = args[0];
        while (true) {
            BufferedReader br = new BufferedReader(new FileReader(filepath), 2 << 17);
            System.out.println("Press Enter to start...");
            while (System.in.read() != '\n')
                ;
            int lineTotal = 0;
            int linePre = 0;
            long start = System.currentTimeMillis();
            long totalStart = System.currentTimeMillis();
            while (br.readLine() != null) {
                lineTotal++;
                if (lineTotal % 100000 == 0) {
                    long end = System.currentTimeMillis();
                    System.out.println("total speed=" + lineTotal / (end - totalStart) + "k/s. curr speed="
                            + (lineTotal - linePre) / (end - start));
                    start = end;
                    linePre = lineTotal;
                }
            }
        }
    }
}
