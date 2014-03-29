import java.io.*;
import java.net.*;
import java.util.*;
import cs.Communication.ClientCommand;
import cs.Communication.CommandResponse;
import org.junit.*;
import static org.junit.Assert.*;

/**
assertEquals(boolean expected, boolean actual

**/

public class TestClient {

	/*Test to make sure that user arguments are being used*/
	/*@Test
	public void testValidCommandLineArgs(){
		String arguments[] = {"--host", "com.dbsm.runner", "--port", "8796"};
		
		Client.getCommandLineArgs(arguments);
		String host = Client.getHost();
		int port = Client.getPort();

		assertEquals(host, "com.dbsm.runner");
		assertEquals(port, 8796); 	
	}


	@Test
	public void testInvalidCommandLineArgs(){
		//create dummy command line arguments
		String arguments[] = {"--host", "--port", "9003", "localhost"};
		
		Client.getCommandLineArgs(arguments);
		String host = Client.getHost();
		int port = Client.getPort();

		//make sure the default values are being used if arguments are invalid
		assertEquals(host, "localhost");
		assertEquals(port, 9009); 
	}


	/*Make sure that only 4 arguments are used*/
	@Test
	public void testNumberOfCommandLineArgs(){
		String arguments[] = {"--host", "com.dbsm.runner", "--port"};

		Client.getCommandLineArgs(arguments);
		String host = Client.getHost();
		int port = Client.getPort();

		assertEquals(host, "localhost");
		assertEquals(port, 9009); 
	}


	@Test 
	public void testCommandLineArgsNull(){
		String arguments[] = null;
		
		Client.getCommandLineArgs(arguments);
		String host = Client.getHost();
		int port = Client.getPort();

		//if null is passed in default values will be used
		assertEquals(host, "localhost");
		assertEquals(port, 9009);
	}


	/*If a command other then "on" "off" or "q" is used, 
	by default "on" will be returned*/
	@Test
	public void testgetUserCommand()throws Exception{
		String userCommand = 
			Client.getUserCommand(new BufferedReader (new InputStreamReader(System.in)));
		
		assertEquals("on", userCommand);
	}

}
