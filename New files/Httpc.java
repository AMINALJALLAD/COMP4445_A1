import java.util.StringTokenizer;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;

public class Httpc {
	private Protocol request;
	private Protocol response;
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
			//	System.out.println("We are httpc");
				switch(token.toLowerCase()) {
					case "get":
					//	System.out.println("from get");
						request = new Get(st);
						break;
					case "post":
						request = new Post(st);
					//	System.out.println("from post");
						break;
					case "help":
						isValid = false;
						if (st.hasMoreTokens()) {
							token = st.nextToken();
							
							switch(token.toLowerCase()) {
							case "get":
						//		System.out.println("from get help");
								help = new HelpGet();
								help.getHelp();
								break;
							case "post":
						//		System.out.println("from post help");
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

	public static void main(String[] args) {
		Httpc httpc = new Httpc();
		Scanner key = new Scanner(System.in);
		String keyInput = "";
		while(true) {
			keyInput = key.nextLine();
			if(!httpc.validation(keyInput)) {
				continue;
			}
		//	System.out.println("before parssing" + httpc.request.portNumber);
			httpc.request.parssing();
		//	System.out.println("before doAction" +httpc.request.portNumber);
		//	System.out.println("before doAction" + httpc.request.portNumber);
			httpc.request.doAction();  // create the message
		//	System.out.println(httpc.request.portNumber);
		//	System.out.println("message is " + httpc.request.message);
		//	System.out.println("headers is " + httpc.request.headers);
			Socket socket = null;
			Scanner streamInput = null;
			PrintWriter streamOutPut = null;
			PrintStream streamToFile = null;
			File fileName = null;
			try {
//				System.out.println("Try it now " );
//				System.out.println(httpc.request.host);
//				System.out.println(httpc.request.portNumber);
				socket = new Socket(httpc.request.host, httpc.request.portNumber);
				
				streamOutPut = new PrintWriter(socket.getOutputStream());
				httpc.response = new Response();
				streamOutPut.print(httpc.request.message);
			//	System.out.println("Ok  is " );
				streamOutPut.flush();
		//		System.out.println("flush  is " );
				streamInput = new Scanner(socket.getInputStream());
				if(httpc.request.redirect) {
					fileName = new File(httpc.request.fileNameOutput);
					streamToFile = new PrintStream(fileName);
					 System.setOut(streamToFile); 
					
				}
				while(streamInput.hasNextLine()) {
					httpc.response.message += streamInput.nextLine() + " * " ;
				}
		//		System.out.println("before message  is " + httpc.response.message);
				httpc.response.parssing();;
				httpc.response.doAction();
		//		System.out.println("after message is " + httpc.response.message);
				socket.close();
				streamOutPut.close();
				streamInput.close();
			}catch(Exception e) {
				System.out.println(e.getStackTrace());
			}
		}
	}

}

