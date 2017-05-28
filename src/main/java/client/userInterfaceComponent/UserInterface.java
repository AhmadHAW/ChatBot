package client.userInterfaceComponent;

import java.io.IOException;
import java.net.SocketException;
import java.util.Set;

import client.entities.GivenObjectNotValidException;
import client.entities.Message;
import client.entities.NameNotValidException;
import client.entities.RoomNotFoundException;
import client.entities.UserNotExistException;

public interface UserInterface {

	public void sendMessage(String roomName, String message)
			throws RoomNotFoundException, GivenObjectNotValidException, NameNotValidException, IOException;

	public void treteRaumBei(String roomName) throws RoomNotFoundException, GivenObjectNotValidException;

	public Set<String> getRooms();

	public void verlasseRaum(String roomName) throws RoomNotFoundException, UserNotExistException;

	public void erstelleRaum(String roomName) throws GivenObjectNotValidException;

	public void loggeAus();

	public void loggeEin(String userName, int port, String ipAdress) throws GivenObjectNotValidException;

	public void setSenderPort(int newPort) throws GivenObjectNotValidException, SocketException;
}