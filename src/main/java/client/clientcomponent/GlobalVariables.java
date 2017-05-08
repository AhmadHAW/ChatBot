package client.clientcomponent;

public class GlobalVariables {

	public final static String BASEPATH = "/chatbot/client";
	public final static String ROOM_RESOURCE = "/rooms/{roomName}";
	public final static String ROOM_USER_RESOURCE = "/rooms/{roomName}/users/{userName}";
	public final static String NAME_REGEX = "[A-Za-z0-9]+";
	public final static int PORT_MIN = 0;
	public final static int PORT_MAX = 65535;
}
