import java.io.File;
import java.net.URL;
import java.util.StringTokenizer;

public class Request extends Protocol{
	String requestLine;
	int portNumber;
	String query;
	String fileName;
	String stringPathUrl;
	String stringUrl;
	boolean redirect;
	
	public Request() {
		message = "";
		portNumber = 0;
		fileNameOutput = "";
	}
	public void parssing() {} 
	
	public URL getUrl(String stringUrl) {
		URL url = null;
		try {
			url = new URL(stringUrl);
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		return url;
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
	
	public void doAction(){}
}
