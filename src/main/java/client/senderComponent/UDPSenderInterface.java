package client.senderComponent;

import client.entities.GivenObjectNotValidException;
import client.entities.NameNotValidException;
import client.entities.RoomNotFoundException;

import java.io.IOException;
import java.net.SocketException;

public interface UDPSenderInterface {

	public void sendMessage(String roomName, String message) throws NameNotValidException, RoomNotFoundException, GivenObjectNotValidException, IOException;

	public void setSenderPort(int newPort) throws SocketException;

}
