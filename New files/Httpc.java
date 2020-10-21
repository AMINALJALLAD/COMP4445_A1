import java.util.StringTokenizer;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;

public class Httpc {
	private Protocol request;
	private Response response;
	private Help help;
	
	public void error() {
		System.out.println("Sory. It is in vaild input");
	}
	
	
	public boolean validation(String input) {
		boolean isValid = true;
		StringTokenizer st = new StringTokenizer(input);
		String token= "";
		if (st.hasMoreTokens()) {
			token = st.nextToken();
			if ( (token.equalsIgnoreCase("httpc")) && st.hasMoreTokens()) {
				token = st.nextToken();
				switch(token.toLowerCase()) {
					case "get":
						request = new Get(st);
						break;
					case "post":
						request = new Post(st);
						break;
					case "help":
						isValid = false;
						if (st.hasMoreTokens()) {
							token = st.nextToken();
							
							switch(token.toLowerCase()) {
							case "get":
								help = new HelpGet();
								help.getHelp();
								break;
							case "post":
								help = new HelpPost();
								help.getHelp();
								break;
							default:
								error();
								help = new Help();
								help.getHelp();
							}
						}else {
							help = new Help();
							help.getHelp();
						}
						break;
					default:
						isValid = false;
				}
			}else {
				isValid = false;
				error();
			}
		}else {
			isValid = false;
			error();
			}
		return isValid;
	}
	
	public void getResponse() {
		
	}
	
//	public static void readSocket(Scanner sc, Response res) {
//		String line = "";
//		line = sc.nextLine();
//		while(line.equals("{")) {
//			res.headersArray.add(line);
//			line = sc.nextLine();
//		}
//
//		while(sc.hasNextLine()) {
//			res.response.add(line);
//			line = sc.nextLine();
//		}
//		res.response.add(line);
//	}

	public static void main(String[] args) {
		Httpc httpc = new Httpc();
		Scanner key = new Scanner(System.in);
		String keyInput = "";
		while(true) {
			keyInput = key.nextLine();
			if(!httpc.validation(keyInput)) {
				continue;
			}
			httpc.request.parssing();
			httpc.request.doAction();  // create the message
			Socket socket = null;
			Scanner streamInput = null;
			PrintWriter streamOutPut = null;
			PrintStream streamToFile = null;
			File fileName = null;
			try {
				socket = new Socket(httpc.request.host, httpc.request.portNumber);	
				streamOutPut = new PrintWriter(socket.getOutputStream());
				httpc.response = new Response();
				streamOutPut.print(httpc.request.message);
				streamOutPut.flush();
				streamInput = new Scanner(socket.getInputStream());
				if(httpc.request.redirect) {
					fileName = new File(httpc.request.fileNameOutput);
					streamToFile = new PrintStream(fileName);
					 System.setOut(streamToFile); 
					
				}
			//	httpc.readSocket(streamInput, response);
				while(streamInput.hasNextLine()) {
					httpc.response.message += streamInput.nextLine() + "*" ;
				}
				if(httpc.request.isPrintAll) {
					httpc.response.isPrintAll = true;
				}
				httpc.response.parssing();
				httpc.response.doAction();
				socket.close();
				streamOutPut.close();
				streamInput.close();
			}catch(Exception e) {
				System.out.println(e.getStackTrace());
			}
		}
	}

}

