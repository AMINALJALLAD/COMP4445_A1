import java.util.ArrayList;
import java.util.StringTokenizer;
public class Response extends Protocol {
	ArrayList<String> headersArray;
	ArrayList<String> response;
	
	public Response() {
		response = new ArrayList<String>();
		headersArray = new ArrayList<String>();
		headers = "";
		message = "";
	}
	
	public void parssing() {
		StringTokenizer st = new StringTokenizer(message);
		String nextToken = "";
		boolean skip = true;
		while(st.hasMoreTokens() ) {
			nextToken = st.nextToken("*");
		//	System.out.println("next token is " + nextToken);
		//	System.out.println("skip is " + skip );
			if(skip) {
				if (nextToken.equals("{")) {
					skip = false;
				//	System.out.println("Done ");
				}
			}
			if(skip) {
				headersArray.add(nextToken);
			}else {
				response.add(nextToken);
			}
		}
	} 
	
	public void doAction() {
		if(isPrintAll) {	
			for(String l : headersArray) {
				System.out.println(l);
			}
		}
		System.out.println();
		for(String l : response) {
			System.out.println(l);
		}
//		System.out.println("message is " + message);
//		System.out.println("headers is " + headers);
//		StringTokenizer st = new StringTokenizer(message);
//		System.out.println("response is " + response);
//		System.out.println("*********************");
//		while(st.hasMoreTokens()) {
//			System.out.println(st.nextToken(" * "));
//		}
	}

}
