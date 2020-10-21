
public class HelpPost extends Help {
	
	public void getHelp() {
		System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\r\n" + 
				"Post executes a HTTP POST request for a given URL with inline data or from\r\n" + 
				"file.\r\n" + 
				"-v Prints the detail of the response such as protocol, status,\r\n" + 
				"and headers.\r\n" + 
				"-h key:value Associates headers to HTTP Request with the format\r\n" + 
				"'key:value'.\r\n" + 
				"-d string Associates an inline data to the body HTTP POST request.\r\n" + 
				"-f file Associates the content of a file to the body HTTP POST\r\n" + 
				"request.\r\n" + 
				"Either [-d] or [-f] can be used but not both.\r\n" + 
				"");
	}

}
