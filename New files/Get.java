import java.io.File;
import java.util.StringTokenizer;

public class Get extends Request{
	public Get(StringTokenizer st){
		super();
		input = st;
		requestLine = "";
		stringUrl = "";
		stringPathUrl = "";
		host = "";
		fileName = "";
		System.out.println("In constructor of Get");
	}
	
	
	public void parssing() {
		String nextToken = null;
		while (input.hasMoreTokens()) {
			nextToken = input.nextToken();
			System.out.println("next token is " + nextToken);
			char firstChar = nextToken.charAt(0);
			redirect=false;isPrintAll =false;
			boolean isError =false; 
			if( (firstChar == '"') || (firstChar == '\'') || (firstChar =='h')) {
				if(firstChar !='h') {
					stringUrl = nextToken.substring(1, nextToken.length() - 1);
				}
				url = getUrl(stringUrl);
				stringPathUrl = url.getPath();
				query = url.getQuery();
				portNumber = url.getPort();
				host = url.getHost();
				if(query != null) {
					requestLine = "GET " + stringPathUrl + "?" + query + " HTTP/1.0\r\n";
				}else {
					requestLine = "GET " +  stringPathUrl + " HTTP/1.0\r\n";
				}
				if(portNumber <= 0) {
					portNumber = url.getDefaultPort();
				}
				setPortNumber(portNumber);
				System.out.println("stringUrl is " + stringUrl);System.out.println("stringPathUrl is " + stringPathUrl);System.out.println("query is " + query);System.out.println("port before is " + portNumber);System.out.println("host is " + host);
			}else if (firstChar == '-') {
				char secondChar = nextToken.charAt(1);
				System.out.println("secondChar is " + secondChar);
				switch(secondChar) {
				case 'h':
					nextToken = streamIfNeed(input);
					if(nextToken != null) {
						System.out.println("Hedaeers is "+ headers);
				//		takeHeadersValues(nextToken);
						headers += nextToken + "\r\n";
					}else {
						isError = true;
					}
					break;
				case 'v':	
					isPrintAll = true;
					setPrintAll(true);
					System.out.println("isPrintAll is " + isPrintAll); 
					break;
				case 'o':
					nextToken = streamIfNeed(input);
					System.out.println("fileNameOutput is " + nextToken);
					if(nextToken != null) {
						redirect = true;
						fileName = nextToken;
					}else {
						System.out.println("You should enter a file directory");
						isError = true;
					}
					break;
				default:
					System.out.println("You have to enter either \'v\' or \'h\' in get method");
					isError = true;
				}
			}
			if(isError == true) {
				Help help = new HelpGet();
				help.getHelp();
				break;
			}
		}
	}
	
	public void doAction() {
		//message = requestLine + headers + "\r\n";
		System.out.println("Hellllllllllllllo");
		System.out.println("portNumber is " + portNumber);
		message += requestLine;
		if(headers != null) {
			message += headers;
		}
		message += "\r\n";
		System.out.println("message is " + message);
		System.out.println("headers is " + headers);
		System.out.println("requestLine is " + requestLine);
	}
}
