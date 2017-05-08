package client.clientcomponent;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ClientService {
	private List<Room> rooms = new ArrayList<Room>();

	public void userJoinRoom(User user, String roomName) throws RoomNotFoundException, GivenObjectNotValidException {
		for (Room room : rooms) {
			if (room.getRoomName().equals(roomName)) {
				room.addUser(user);
				return;
			}
		}
		throw new RoomNotFoundException("Raum mit dem Raumnamen: " + roomName + " wurde nicht gefunden");

	}

	public void userLeaveRoom(String roomName, String userName) throws UserNotExistException, RoomNotFoundException {
		for (Room room : rooms) {
			if (room.getRoomName().equals(roomName)) {
				room.removeUser(userName);
				return;
			}
		}
		throw new RoomNotFoundException("Raum mit dem Raumnamen: " + roomName + " wurde nicht gefunden");

	}

}
