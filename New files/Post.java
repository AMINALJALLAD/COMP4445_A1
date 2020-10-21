import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.StringTokenizer;
public class Post extends Request {
	String body;
	boolean isInLineBody;
	
	public Post(StringTokenizer st){
		super();
		input = st;
		requestLine = "POST ";
		isInLineBody = false;
		stringUrl = "";
		requestLine = "";
		portNumber = 0;
		stringPathUrl = "";
		host = "";
		fileName = "";
	}
	
	
	public static String getFileContents(String directory) {
		String body = new String("");
		Scanner input = null;
		try {
			input = new Scanner(new FileInputStream(directory));
			while(input.hasNext()) {
				body += input.nextLine() + "\n";
			}
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		input.close();
		return body;
	}
	
	public void parssing() {
		String nextToken = null;
		while(input.hasMoreTokens()) {
			nextToken = input.nextToken();
			char firstChar = nextToken.charAt(0);
			redirect=false; isPrintAll =false;
			boolean isError =false,  eitherD_F = false;
			if( (firstChar == '"') || (firstChar == '\'') || (firstChar == 'h')) {
				if((firstChar != 'h')) {
					stringUrl = nextToken.substring(1, nextToken.length() - 1);
				}else {
					stringUrl = nextToken;
				}
				url = getUrl(stringUrl);
				stringPathUrl = url.getPath();
				requestLine += stringPathUrl + " HTTP/1.0\r\n";
				host = url.getHost();
				query = url.getQuery();
				portNumber = url.getPort();
				if(portNumber <= 0) {
					portNumber = url.getDefaultPort();
				}
				setPortNumber(portNumber);
				System.out.println("stringUrl is " + stringUrl);System.out.println("stringPathUrl is " + stringPathUrl);System.out.println("query is " + query);System.out.println("port before is " + portNumber);System.out.println("host is " + host);System.out.println("Now message is " + message);
			}else if (firstChar == '-') {
				char secondChar = nextToken.charAt(1);
				System.out.println("secondChar is " + secondChar);
				switch(secondChar) {
					case 'h':
						nextToken = streamIfNeed(input);
						System.out.println("headers is " + headers);
						if(nextToken != null) {
					//		takeHeadersValues(nextToken);
							headers += nextToken + "\r\n";
						}else {
							isError = true;
						}
						break;
					case 'v':
						System.out.println("isPrintAll is " + isPrintAll);
						isPrintAll = true;
						setPrintAll(true);
						break;
					case 'o':
						nextToken = streamIfNeed(input);
						if(nextToken != null) {
							System.out.println("fileNameOutput is " + nextToken);
							fileNameOutput = nextToken;
							redirect = true;
						}else {
							System.out.println("You should enter a valid file directory");
							isError = true;
						}
						break;
					case 'f':
						if(eitherD_F == false) {
							eitherD_F = true;
							nextToken = streamIfNeed(input);
							System.out.println("file directory is " + nextToken);
							if(nextToken != null) {
								body = getFileContents(nextToken);
								System.out.println("body is " + body);
							//	takeHeadersValues("Content-Length:" + bodyFileContent.length());
								headers += "Content-Length:" + body.length() +"\r\n";
							}else {
								System.out.println("You should enter a directory for the file that you will use in the body request");
								isError = true;
							}
						}else {
							isError = true;
							System.out.println("You have to enter either f or d");
							break;
						}
						break;
					case 'd':
						if(eitherD_F == false) {
							eitherD_F = true;
							nextToken = streamIfNeed(input);
							if(nextToken != null) {
								isInLineBody =true;
								body = nextToken;
								System.out.println("body is " + body);
						//		takeHeadersValues("Content-Length:" + inLineBody.length());
								headers += "Content-Length:" + body.length() +"\r\n";
							}else {
								System.out.println("You should enter the data that you will use in the body request");
								isError = true;
							}
						}else {
							isError = true;
							System.out.println("You have to enter either f or d");
							break;
						}
						break;
					default:
						System.out.println("You have to enter a valid input in post method");
						isError = true;
					}
				if(isError == true) {
					System.out.println("You have to enter either -d or -f");
					Help help = new HelpPost();
					help.getHelp();
					break;
				}
			}
					
		}
	} 

	public void doAction() {
		System.out.println("Hellllllllllllllo");
		System.out.println("portNumber is " + portNumber);
		System.out.println("message is " + message);
		System.out.println("headers is " + headers);
		System.out.println("requestLine is " + requestLine);
		System.out.println("body is " + body);
		message = requestLine + headers + "\r\n" + body;
	}
}
