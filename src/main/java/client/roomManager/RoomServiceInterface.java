package client.roomManager;

import java.util.Set;

import org.springframework.stereotype.Service;

import client.entities.GivenObjectNotValidException;
import client.entities.Room;
import client.entities.RoomNotFoundException;
import client.entities.User;
import client.entities.UserNotExistException;

public interface RoomServiceInterface {

	public void userJoinRoom(User user, String roomName) throws RoomNotFoundException, GivenObjectNotValidException;

	public void userLeaveRoom(String roomName, String userName) throws UserNotExistException, RoomNotFoundException;

	public Room createRoom(String roomName) throws GivenObjectNotValidException;

	public void deleteRoom(String roomName) throws GivenObjectNotValidException, RoomNotFoundException;

	public Set<Room> getRooms();

	public Room getRoom(String roomName) throws GivenObjectNotValidException, RoomNotFoundException;
}
