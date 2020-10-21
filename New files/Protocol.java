import java.util.StringTokenizer;
import java.net.URL;

public abstract class Protocol {
	String message;
	int portNumber;
	String fileNameOutput;
	boolean redirect;
	boolean isPrintAll;
	String host;
	String headers;
	StringTokenizer input;
	URL url;
	public abstract void parssing();
	public abstract void doAction();
	public void setPortNumber(int portNum) {
		portNumber = portNum;
	}
	
	public void setPrintAll(boolean isPeint) {
		isPrintAll = isPeint;
	}
}
