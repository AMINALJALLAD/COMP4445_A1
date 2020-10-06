

import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.net.URL;
import java.io.File;
import java.io.PrintStream;
public class Httpc {
	static ArrayList<String> headersKey = new ArrayList<String>();
	static ArrayList<String> headersValue = new ArrayList<String>();
	public static void getHelp() {
		System.out.println("Get executes a HTTP GET request and prints the response.\r\n" + 
				"Post executes a HTTP POST request and prints the response.\r\n" + 
				"Help prints this screen.");
	}
	
	public static void getHelpGet() {
		System.out.println("usage: httpc get [-v] [-h key:value] URL\r\n" + 
				"Get executes a HTTP GET request for a given URL.\r\n" + 
				"-v Prints the detail of the response such as protocol, status,\r\n" + 
				"and headers.\r\n" + 
				"-h key:value Associates headers to HTTP Request with the format\r\n" + 
				"'key:value'.\r\n" + 
				"");
	}
	
	public static void getHelpPost() {
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
	
	public static void inValidInput() {
		System.out.println("Sory. It is in vaild input");
		getHelp();
	}
	
	public static URL getUrl(String stringUrl) {
		URL url = null;
		try {
			url = new URL(stringUrl);
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		return url;
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
	
	public static void takeHeadersValues(String headersString) {
		StringTokenizer st = new StringTokenizer(headersString);
		headersKey.add(st.nextToken(":"));
		headersValue.add(st.nextToken());
	}
	
	public static String streamIfNeed(StringTokenizer input) {
		String requiredString = null;
		if(input.hasMoreTokens()) {
			requiredString = input.nextToken();
			char firsrChar = requiredString.charAt(0);
			if (firsrChar =='\'') {
				requiredString = requiredString.substring(1);
				if((requiredString.charAt(requiredString.length()-1) ) != '\'') {
					requiredString += " " + input.nextToken("' ");
				}else {
					requiredString = requiredString.substring(0, requiredString.length()-1);
				}
			}else if (firsrChar =='"') {
				requiredString = requiredString.substring(1);
				if((requiredString.charAt(requiredString.length()-1) ) != '\"') {
					requiredString += " " + input.nextToken("\" ");
				}else {
					requiredString = requiredString.substring(0, requiredString.length()-1);
				}
			}
		}
		return requiredString;
	}
	
	public static void main(String[] args) {
		File fileName = null;
		boolean isError = false, eitherD_F = false, isInLineBody = false, isPrintAll = false, rediect = false;
		int portNumber = 0;
		String request = null, stringPathUrl = null, body = null, response=null, headers=null, 
			bodyFileContent = null, requestType = null, host = null, inLineBody = null, query = null;
		Scanner key = new  Scanner(System.in);
		URL url =null;
		String stringUrl = null;
		StringTokenizer input = null;
		String nextInput = null; // A string variable to navigate each input from stringTokenizer
		while (true) {
			headersKey = new ArrayList<String>();
			headersValue = new ArrayList<String>();
			request = ""; body = ""; response=""; headers=""; bodyFileContent = ""; 
			host = ""; stringPathUrl =""; inLineBody = "";isInLineBody = false; 
			query = "";isPrintAll = false; rediect = false; isError = false ;
			String data = key.nextLine();
			input = new StringTokenizer(data);
			if(input.hasMoreTokens() && input.nextToken().equals("httpc") && input.hasMoreTokens()) {
				requestType = input.nextToken();
				if (requestType.equals("help") ) {
					if(input.hasMoreTokens()) {
						requestType = input.nextToken();
						if(requestType.equals("get")) {
							getHelpGet();
						}else if (requestType.equals("post")) {
							getHelpPost();
						}else {
						inValidInput();
						}
					}else {
						getHelp();
					}
				}else if (requestType.equals("get")){
					body += "GET ";
					while(input.hasMoreElements()) {
						nextInput = input.nextToken();
						char firstChar = nextInput.charAt(0);
						if( (firstChar == '"') || (firstChar == '\'') || (firstChar =='h')) {
							if(firstChar !='h') {
								stringUrl = nextInput.substring(1, nextInput.length() - 1);
							}
							url = getUrl(stringUrl);
							stringPathUrl = url.getPath();
							query = url.getQuery();
							portNumber = url.getPort();
							host = url.getHost();
							if(requestType.equals("get") && (query != null)) {
								body += stringPathUrl + "?" + query + " HTTP/1.0\r\n";
							}else {
								body += stringPathUrl + " HTTP/1.0\r\n";
							}
							if(portNumber <= 0) {
								portNumber = url.getDefaultPort();
							}
						}else if (firstChar == '-') {
							char secondChar = nextInput.charAt(1);
							switch(secondChar) {
							case 'h':
								nextInput = streamIfNeed(input);
								if(nextInput != null) {
									takeHeadersValues(nextInput);
									headers += nextInput + "\r\n";
								}else {
									isError = true;
								}
								break;
							case 'v':
								isPrintAll = true;
								break;
							case 'o':
								nextInput = streamIfNeed(input);
								if(nextInput != null) {
									fileName = new File(nextInput);
									rediect = true;
								}else {
									System.out.println("You should enter a file directory");
									isError = true;
								}
								break;
							default:
								System.out.println("You have to enter either \'v\' or \'h\' in get method");
								isError = true;
								getHelpGet();
							}
						}
						if(isError == true) {
							getHelpGet();
							break;
						}
					}
				}else if (requestType.equals("post")) {
					body += "POST ";
					eitherD_F = false;
					while(input.hasMoreElements()) {
						nextInput = input.nextToken();
						char firstChar = nextInput.charAt(0);
						if( (firstChar == '"') || (firstChar == '\'') || (firstChar == 'h')) {
							if((firstChar != 'h')) {
								stringUrl = nextInput.substring(1, nextInput.length() - 1);
							}else {
								stringUrl = nextInput;
							}
							url = getUrl(stringUrl);
							stringPathUrl = url.getPath();
							body += stringPathUrl + " HTTP/1.0\r\n";
							host = url.getHost();
							query = url.getQuery();
							portNumber = url.getPort();
							if(portNumber <= 0) {
								portNumber = url.getDefaultPort();
							}
						}else if (firstChar == '-') {
							char secondChar = nextInput.charAt(1);
							switch(secondChar) {
							case 'h':
								nextInput = streamIfNeed(input);
								if(nextInput != null) {
									takeHeadersValues(nextInput);
									headers += nextInput + "\r\n";
								}else {
									isError = true;
								}
								break;
							case 'v':
								isPrintAll = true;
								break;
							case 'o':
								nextInput = streamIfNeed(input);
								if(nextInput != null) {
									fileName = new File(nextInput);
									rediect = true;
								}else {
									System.out.println("You should enter a file directory");
									isError = true;
								}
								break;
							case 'f':
								if(eitherD_F == false) {
									eitherD_F = true;
									nextInput = streamIfNeed(input);
									if(nextInput != null) {
										bodyFileContent = getFileContents(nextInput);
										takeHeadersValues("Content-Length:" + bodyFileContent.length());
										headers += "Content-Length:" + bodyFileContent.length() +"\r\n";
									}else {
										System.out.println("You should enter a directory for the file that you will use in the body request");
										isError = true;
									}
								}else {
									isError = true;
								}
								break;
							case 'd':
								if(eitherD_F == false) {
									eitherD_F = true;
									nextInput = streamIfNeed(input);
									if(nextInput != null) {
										isInLineBody =true;
										inLineBody = nextInput;
										takeHeadersValues("Content-Length:" + inLineBody.length());
										headers += "Content-Length:" + inLineBody.length() +"\r\n";
									}else {
										System.out.println("You should enter the data that you will use in the body request");
										isError = true;
									}
								}else {
									isError = true;
								}
								break;
							default:
								System.out.println("You have to enter a valid input in post method");
								isError = true;
								getHelpPost();
							}
						}
						if(isError == true) {
							System.out.println("You have to enter either -d or -f");
							getHelpPost();
							break;
						}
					}
				}else {
					inValidInput();
				}
			}else {
				inValidInput();
			}
			if(isError == false) {
				body += headers +"\r\n";
				if (requestType.equals("post") ) {
					if(isInLineBody == true) {
						body += inLineBody;
					}else {
						body += bodyFileContent;
					}
				}
				Socket socket = null;
				Scanner streamInput = null;
				PrintWriter streamOutPut = null;
				PrintStream streamToFile = null;
				try {
					socket = new Socket(host, portNumber);
					streamOutPut = new PrintWriter(socket.getOutputStream());
					streamOutPut.print(body);
					streamOutPut.flush();
					streamInput = new Scanner(socket.getInputStream());
					if(rediect) {
						streamToFile = new PrintStream(fileName);
						 System.setOut(streamToFile); 
						
					}
					while(streamInput.hasNext()) {
						System.out.println(streamInput.nextLine());
					}
					socket.close();
					streamOutPut.close();
					streamInput.close();
				}catch(Exception e) {
					System.out.println(e.getStackTrace());
				}
			}
			
		}
	}

}