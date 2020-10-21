
public class HelpGet extends Help {
	
	public void getHelp() {
		System.out.println("usage: httpc get [-v] [-h key:value] URL\r\n" + 
				"Get executes a HTTP GET request for a given URL.\r\n" + 
				"-v Prints the detail of the response such as protocol, status,\r\n" + 
				"and headers.\r\n" + 
				"-h key:value Associates headers to HTTP Request with the format\r\n" + 
				"'key:value'.\r\n" + 
				"");
	}
}
