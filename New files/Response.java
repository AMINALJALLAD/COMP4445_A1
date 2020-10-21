import java.util.StringTokenizer;
public class Response extends Protocol {
	String response;
	
	public Response() {
		message = "";
		response = "";
		headers = "";
	}
	
	public void parssing() {
		StringTokenizer st = new StringTokenizer(message);
		String nextToken = "";
		boolean skip = true;
		while(st.hasMoreTokens() ) {
			nextToken = st.nextToken();
		//	System.out.println("next token is " + nextToken);
		//	System.out.println("skip is " + skip );
			if(skip) {
				if (nextToken.equals("{")) {
					skip = false;
				//	System.out.println("Done ");
				}
			}
			if(skip) {
				headers += nextToken;
			}else {
				response += nextToken;
			}
		}
	} 
	
	public void doAction() {
		if(!isPrintAll) {	
			System.out.println("Not pribtAll");
			message = response;
		}
		System.out.println("message is " + message);
		System.out.println("headers is " + headers);
		StringTokenizer st = new StringTokenizer(message);
		System.out.println("response is " + response);
		System.out.println("*********************");
		while(st.hasMoreTokens()) {
			System.out.println(st.nextToken(" * "));
		}
	}

}
