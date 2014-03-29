import java.io.*;
import java.net.*;
import java.util.*;
import cs.Communication.CommandResponse;
import cs.Communication.ClientCommand;

class Server {
	//machine is on by default
	private static boolean isRunning = true; 
	
	//default host and port number if not specified by user
	private static String host = "localhost";
	private static int port = 9009;

	
	public static String processAndDisplayCommand(ClientCommand command){
		String credentials = command.getCredentials();
		String clientCommand = command.getCommand().toString();
		
		System.out.println("-------------");
		System.out.println(credentials + " is requesting " + clientCommand);
		System.out.println("-------------");

		return clientCommand;	
	}


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


	public static CommandResponse buildErrorMessage(String command)
			throws Exception{
		
		CommandResponse.Builder response = CommandResponse.newBuilder();
		response.setResponse(CommandResponse.Response.RES_ERR);
		response.setResponseMessage("Server error: could not process command.");
		
		return response.build();
	}

	
	public static CommandResponse buildMachineToggleMessage(String command)
			throws Exception{
		
		CommandResponse.Builder response = CommandResponse.newBuilder();
		if(isRunning && command.equals("POWER_ON")){
			response.setResponseMessage("Machine is already on!");
		}
		else if (!isRunning && command.equals("POWER_OFF")){
			response.setResponseMessage("Machine is already off");
		}
		else if (isRunning && command.equals("POWER_OFF")){
			response.setResponseMessage("Machine successfully turned off.");
			isRunning = false;
		}
		else {
			response.setResponseMessage("Machine successfully turned on.");
			isRunning = true;
		}

		response.setResponse(CommandResponse.Response.RES_OK);
		return response.build();
	}


	/*
	If a 0 is generated false will be returned, an error will be sent to the Client
	If a 1 is generated true will be returned, no error will be sent
	*/ 
	public static boolean generateRandomResponse(){
		Random rand = new Random();
		int randomInteger = rand.nextInt(2);
		if (randomInteger == 0)
			return false;
		return true; 
	}

	/*Only used for testing*/
	public static void setIsRunning(boolean value){
		isRunning = value;
	}


	public static void main (String args[]) throws Exception{
		System.out.println("Server has started");
		getCommandLineArgs(args);
		
		ServerSocket welcomeSocket = new ServerSocket(port);
		Socket conSocket = welcomeSocket.accept();
		DataInputStream inFromClient = 
			new DataInputStream (conSocket.getInputStream());		
		DataOutputStream outToClient = 
			new DataOutputStream(conSocket.getOutputStream());

		while(true){
			byte size = inFromClient.readByte();
			byte[] bytes = new byte[size];
			inFromClient.readFully(bytes);
			
			ClientCommand command = ClientCommand.parseFrom(bytes);
			String userCommand = processAndDisplayCommand(command);
			
			CommandResponse response = null;
			
			if(generateRandomResponse() == true){
				response = buildMachineToggleMessage(userCommand);
			}
			else{
				response = buildErrorMessage(userCommand);
			}
			
			size = (byte) response.getSerializedSize();
			outToClient.writeByte(size);
			outToClient.write(response.toByteArray());

		}
	}
}
