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
			throws RoomNotFoundException, GivenObjectNotValidException, NameNotValidException, IOException, InterruptedException;

	public void treteRaumBei(String roomName) throws RoomNotFoundException, GivenObjectNotValidException, NameNotValidException, InterruptedException;

	public Set<String> getRooms() throws InterruptedException;

	public void verlasseRaum(String roomName) throws RoomNotFoundException, GivenObjectNotValidException, NameNotValidException, InterruptedException;

	public void erstelleRaum(String roomName) throws GivenObjectNotValidException, NameNotValidException;

	public void loggeAus() throws UserNotExistException;

	public void loggeEin(String userName, int port, String ipAdress) throws GivenObjectNotValidException;

	public void setSenderPort(int newPort) throws GivenObjectNotValidException, SocketException;
}
