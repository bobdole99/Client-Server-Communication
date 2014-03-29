import java.io.*;
import java.net.*;
import java.util.*;
import cs.Communication.ClientCommand;
import cs.Communication.CommandResponse;

public class Client {
	
	private static String credentials = "User 10020311-mg";
	
	//default host and port number if not specified by user
	private static String host = "localhost";
	private static int port = 9009;


	public static void getCommandLineArgs(String args[]){
		if (args == null){
			System.out.println("No parameters specified");
			System.out.println("Host " + host +" and port "+ port +" will be used");
		}
		else if (args.length != 4){
			System.out.println("Invalid number of parameters");
			System.out.println("Host " + host +" and port "+ port +" will be used");
		}
		else if (!(args[0].equals("--host") && args[2].equals("--port"))){
			System.out.println("Invalid flags");
			System.out.println("Host " + host +" and port "+ port +" will be used");
		}
		else{				
			host = args[1];
			port = Integer.parseInt(args[3]);
		}
	}


	public static void processAndDisplayResponse(CommandResponse response){
		String responseMessage = response.getResponseMessage();
		String serverResponse = response.getResponse().toString();
		
		System.out.println("-------------");
		System.out.println(responseMessage);
		System.out.println(serverResponse);
		System.out.println("-------------");	
	}


	public static ClientCommand buildCommand(String userCommand) throws Exception{
		ClientCommand.Builder command = ClientCommand.newBuilder();
		if(userCommand.equals("on")){
			command.setCommand(ClientCommand.Command.POWER_ON);		
		}
		else {
			command.setCommand(ClientCommand.Command.POWER_OFF);
		}
		command.setCredentials(credentials);
		return command.build();
	}


	//If the user types something other then "on" "off" or "q" by default "on" will be returned
	public static String getUserCommand(BufferedReader stdin) throws Exception{
		System.out.println
			("Type \"on\" or \"off\" to toggle machine or press \"q\" to quit");
		
		String userInput = stdin.readLine();
		if (!(userInput.equals("q") || userInput.equals("on") || userInput.equals("off"))){
			return "on";
		}
		return userInput;
	}


	/*Following getters are used only for testing*/
	public static int getPort(){
		return port;
	}
	
	public static String getHost(){
		return host;
	}


	public static void main (String args[]) throws Exception{
		getCommandLineArgs(args);
		Socket clientSocket = new Socket(host, port);

		DataInputStream inFromServer = 
			new DataInputStream (clientSocket.getInputStream());
		
		DataOutputStream outToServer = 
			new DataOutputStream (clientSocket.getOutputStream());
		String userCommand = 
		  getUserCommand(new BufferedReader(new InputStreamReader(System.in)));
		
		while(!userCommand.equals("q")){
			ClientCommand command = buildCommand(userCommand);
			
			byte size = (byte) command.getSerializedSize();
			outToServer.writeByte(size);
			outToServer.write(command.toByteArray());

			size = inFromServer.readByte();
			byte[] inBytes = new byte[size];
			inFromServer.readFully(inBytes);

			CommandResponse response = CommandResponse.parseFrom(inBytes);
			processAndDisplayResponse(response);
			userCommand = 
				getUserCommand(new BufferedReader(new InputStreamReader(System.in)));		
		}
		clientSocket.close();
	}
}
