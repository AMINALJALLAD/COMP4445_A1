package ClientSide;
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
			if(skip) {
				if (nextToken.equals("{")) {
					skip = false;
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
	}

}
