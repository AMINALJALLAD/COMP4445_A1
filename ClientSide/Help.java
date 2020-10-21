package ClientSide;
public class Help extends Request {
	public void getHelp() {
		System.out.println("Get executes a HTTP GET request and prints the response.\r\n" + 
				"Post executes a HTTP POST request and prints the response.\r\n" + 
				"Help prints this screen.");
	}
}
