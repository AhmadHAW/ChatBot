package client.clientcomponent;

public class UserNotExistException extends Exception {
	UserNotExistException(String message) {
		super(message);
	}
}
