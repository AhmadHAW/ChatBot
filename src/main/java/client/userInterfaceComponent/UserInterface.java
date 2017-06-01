package client.userInterfaceComponent;

import java.io.IOException;
import java.net.SocketException;
import java.util.Set;

import client.entities.*;

public interface UserInterface {

	public void sendMessage(String roomName, String message)
			throws RoomNotFoundException, GivenObjectNotValidException, NameNotValidException, IOException, InterruptedException;

	public void treteRaumBei(String roomName) throws RoomNotFoundException, GivenObjectNotValidException, NameNotValidException, InterruptedException, IOException;

	public Set<String> getRoomsLocal() throws InterruptedException;

	public void verlasseRaum(String roomName) throws RoomNotFoundException, GivenObjectNotValidException, NameNotValidException, InterruptedException;

	public void erstelleRaum(String roomName) throws GivenObjectNotValidException, NameNotValidException, InterruptedException;

	public void loggeAus() throws UserNotExistException;

	public void loggeEin(String userName, int port, String ipAdress) throws GivenObjectNotValidException, IOException;

	public void setSenderPort(int newPort) throws GivenObjectNotValidException, SocketException;

    public Set<String> getRoomsServer() throws Exception;
}
