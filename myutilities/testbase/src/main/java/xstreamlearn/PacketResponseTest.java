package xstreamlearn;

import java.util.LinkedHashMap;
import java.util.List;

public class PacketResponseTest {

	public PacketResponseTest() {

	}

	private LinkedHashMap<String, Object> params;

	private List<LinkedHashMap<String, Object>> datalist = null;

	public List<LinkedHashMap<String, Object>> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<LinkedHashMap<String, Object>> datalist) {
		this.datalist = datalist;
	}

	private String result;

	public LinkedHashMap<String, Object> getParams() {
		return params;
	}

	public void setParams(LinkedHashMap<String, Object> params) {
		this.params = params;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
