package mythrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * User: mzang
 * Date: 2014-08-26
 * Time: 15:54
 */
public class SocketClient {

    public static void main(String[] args) throws TException {
        int timeout = 5000;
//        THttpClient httpClient = new THttpClient("http://127.0.0.1:56789");
//        httpClient.setConnectTimeout(timeout);
//        httpClient.setReadTimeout(timeout * 20);
//        TFramedTransport transport = new TFramedTransport(httpClient);
//        TBinaryProtocol protocol = new TBinaryProtocol(transport);
//        ProcessDataService.Client client = new ProcessDataService.Client(protocol);
//
//        DataPackage dataPackage = new DataPackage(1, "testname", DataType.DATA, 2, System.currentTimeMillis(),
//                new HashSet<String>(), new ArrayList<String>(), new HashMap<String, String>());
//        ProcessResult result = client.processData(dataPackage);
//        System.out.println(result);


        TSocket socket = new TSocket("127.0.0.1", 56789);
        socket.setTimeout(timeout);
        TTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        ProcessDataService.Client client = new ProcessDataService.Client(protocol);
        transport.open();
        DataPackage dataPackage = new DataPackage(1, "testname", DataType.DATA, 2, System.currentTimeMillis(),
                new HashSet<String>(), new ArrayList<String>(), new HashMap<String, String>());
        ProcessResult result = client.processData(dataPackage);
        System.out.println(result);
        transport.close();

    }
}
