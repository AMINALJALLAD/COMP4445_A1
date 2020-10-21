package ServerSide;
import ClientSide.Httpc;
public class Httpf {
	String statusLine;
	int portNumberServer;
	Httpc request;
	Response response;
	boolean [] status;
	Httpf(){
		request = new Httpc();
		response = new Response();
		status = new boolean[5];
	}
	
	public boolean validatePortNumber(int num) {
		boolean valid = true;
		
		
		return valid;
	}
	
	public String getStatus(int num) {
		String status = "";
		
		return status;
	}
	
	public static void main(String[] args) {
		
	}
	
}
