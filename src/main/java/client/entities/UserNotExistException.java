package client.entities;

public class UserNotExistException extends Exception {
	public UserNotExistException(String message) {
		super(message);
	}
}
