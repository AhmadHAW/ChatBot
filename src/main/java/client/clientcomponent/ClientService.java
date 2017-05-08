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
	private List<User> users = new ArrayList<User>();

	public List<String> getAllRooms() {
		List<String> roomNames = new ArrayList<String>();
		for (Room r : rooms) {
			roomNames.add(r.getRoomName());
		}
		return roomNames;
	}

	public List<User> getAllUsers() {
		return users;
	}

	public void createUser(User user) {
		if (!users.contains(user)) {
			users.add(user);
		}
	}

	public User getUser(String userName) {
		return users.stream().filter(t -> t.getUserName().equals(userName)).findFirst().get();
	}

	public Room getRoom(String roomName) {
		return rooms.stream().filter(t -> t.getRoomName().equals(roomName)).findFirst().get();
	}

	public void createRoom(Room room) {
		if (!rooms.contains(room)) {
			rooms.add(room);
		}
	}

	// TODO Clients
	public void joinRoom(String roomName, User user) throws RoomNotExistException, UserNotExistException {
		Room room = rooms.stream().filter(t -> t.getRoomName().equals(roomName)).findFirst().get();
		if (room == null) {
			throw new RoomNotExistException("Der Raum mit Raumnamen: " + roomName + " existiert nicht.");
		}
		String userName = user.getUserName();
		user = getUser(userName);
		if (user == null) {
			throw new UserNotExistException("Der User mit Username: " + userName + " existiert nicht.");
		}
		room.addUser(user);
	}

	// TODO
	public void leaveRoom(String roomName, String userName) throws RoomNotExistException, UserNotExistException {
		User user = users.stream().filter(t -> t.getUserName().equals(userName)).findFirst().get();
		Room room = rooms.stream().filter(t -> t.getRoomName().equals(roomName)).findFirst().get();
		if (room == null) {
			throw new RoomNotExistException("Der Raum mit Raumnamen: " + roomName + " existiert nicht.");
		} else if (user == null) {
			throw new UserNotExistException("Der User mit Username: " + userName + " existiert nicht.");
		}
		room.removeUser(user);
	}

}
