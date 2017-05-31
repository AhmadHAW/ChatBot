package client;

import client.entities.User;

public class GlobalConstantsAndValidation {

	public final static String BASEPATH = "/chatbot/client";
	public final static String ROOM_RESOURCE = "/rooms/{roomName}";
	public final static String ROOM_USER_RESOURCE = "/rooms/{roomName}/users/{userName}";
	public final static String SERVER_USER_RESOURCES = "/chatbot/users";

	public final static String NAME_REGEX = "\\w+";
	public final static String IP_ADRESS_REGEX = "\\d\\d\\d.\\d.\\d\\d\\d.\\d\\d\\d";
	public final static String MESSAGE_REGEX = ".+";
	public final static int PORT_MIN = 0;
	public final static int PORT_MAX = 65535;
	public final static int MAXMESSAGESIZE = 508;
	public static User USER = null;

	public static void setUser(User user){
		USER = user;
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


