package mythrift;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * User: mzang
 * Date: 2014-08-26
 * Time: 18:02
 */
public class SocketServer {

    public static void main(String[] args) throws TTransportException {

        TNonblockingServerSocket socket = new TNonblockingServerSocket(56789);

        TProcessor processor = new ProcessDataService.Processor<ProcessDataService.Iface>(
                new ProcessDataService.Iface() {

                    @Override
                    public ProcessResult processData(DataPackage dataPackage) throws TException {
                        ProcessResult result = new ProcessResult();
                        result.setId(1);
                        result.setMessage("aaaa");
                        result.setSuccess(true);
                        return result;
                    }
                }
        );

        TThreadedSelectorServer server = new TThreadedSelectorServer(new TThreadedSelectorServer.Args(socket).processor(processor));

        server.serve();
    }

}
