package client;

import client.entities.User;

public class GlobalVariables {

	public final static String BASEPATH = "/chatbot/client";
	public final static String ROOM_RESOURCE = "/rooms/{roomName}";
	public final static String ROOM_USER_RESOURCE = "/rooms/{roomName}/users/{userName}";
	public final static String SERVER_USER_RESOURCES = "/chatbot/users";

	public final static String NAME_REGEX = "(/w)+";
	public final static int PORT_MIN = 0;
	public final static int PORT_MAX = 65535;
	public final static int MAXMESSAGESIZE = 508;
	public static User USER = null;

	public static void setUser(User user){
		USER = user;
	}

}
