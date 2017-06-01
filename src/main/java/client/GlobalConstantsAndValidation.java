package client;

import client.entities.User;

public class GlobalConstantsAndValidation {

	public final static String BASEPATH = "/chatbot/client";
	public final static String ROOM_RESOURCE = "/rooms/{roomName}";
	public final static String ROOM_USER_RESOURCE = "/rooms/{roomName}/users/{userName}";
	public final static String SERVER_USER_RESOURCES = "/chatbot/users";
	public final static String SERVER_ROOM_RESOURCES = "/chatbot/rooms";
	public final static String SERVER_USER_RESOURCE = "/chatbot/users/";
	public final static String BASE_URL = "http://";
	public final static int SERVER_HTTP_PORT = 8080;
	public static String SERVER_IP_ADRESS;
	public static int Client_TCP_PORT;

	public final static String NAME_REGEX = "\\w+";
	public final static String IP_ADRESS_REGEX = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
	public final static String MESSAGE_REGEX = ".+";
	public final static int PORT_MIN = 0;
	public final static int PORT_MAX = 65535;
	public final static int MAXMESSAGESIZE = 508;
	public static User USER = null;

	public static void setUser(User user){
		USER = user;
	}
	public static void setServerIpAdress(String ipAdress){
		SERVER_IP_ADRESS = ipAdress;
	}
	public static void setClientTcpPort(int port){
		Client_TCP_PORT = port;
	}

	public static boolean isMessageValid(String message){
		return message!=null&&message.matches(GlobalConstantsAndValidation.MESSAGE_REGEX);
	}

	public static boolean isValidName(String name){
		return name != null && name.matches(GlobalConstantsAndValidation.NAME_REGEX);
	}

	public static boolean isValidIpAdress(String ipAdresse){
		return ipAdresse != null && ipAdresse.matches(GlobalConstantsAndValidation.IP_ADRESS_REGEX);
	}

	public static boolean isValidTimeStamp(long timeStamp){
		return timeStamp>=0;
	}

	public static boolean isValidPort(int port){
		return port >= PORT_MIN && port<=PORT_MAX;
	}

}


