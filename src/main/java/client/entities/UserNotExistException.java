package client.entities;

public class UserNotExistException extends Exception {
	UserNotExistException(String message) {
		super(message);
	}
}
