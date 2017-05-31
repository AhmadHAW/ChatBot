package client.roomManagerComponent;

import java.util.Set;

import client.entities.GivenObjectNotValidException;
import client.entities.NameNotValidException;
import client.entities.Room;
import client.entities.RoomNotFoundException;
import client.entities.User;
import client.entities.UserNotExistException;

public interface RoomServiceUserInterface {


	public Room createRoom(String roomName) throws GivenObjectNotValidException, InterruptedException, RoomNotFoundException, NameNotValidException;

	public void deleteRoom(String roomName) throws GivenObjectNotValidException, RoomNotFoundException, InterruptedException, NameNotValidException;

	public Set<Room> getRooms() throws InterruptedException;

	public Room getRoom(String roomName) throws NameNotValidException, RoomNotFoundException, InterruptedException;
}
