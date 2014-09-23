package mythrift;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;

import javax.servlet.annotation.WebServlet;

/**
 * User: mzang
 * Date: 2014-08-26
 * Time: 19:20
 */

@WebServlet(name = "gateway", urlPatterns = {"/gateway"})
public class HttpServer extends TServlet {

    public HttpServer() {
        super(new ProcessDataService.Processor<ProcessDataService.Iface>(
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
        ), new TBinaryProtocol.Factory());
    }

    public HttpServer(TProcessor processor, TProtocolFactory inProtocolFactory, TProtocolFactory outProtocolFactory) {
        super(processor, inProtocolFactory, outProtocolFactory);
    }

    public HttpServer(TProcessor processor, TProtocolFactory protocolFactory) {
        super(processor, protocolFactory);
    }
}
