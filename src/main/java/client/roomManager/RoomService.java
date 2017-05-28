package client.roomManager;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import client.entities.GivenObjectNotValidException;
import client.GlobalVariables;
import client.entities.NameNotValidException;
import client.entities.Room;
import client.entities.RoomNotFoundException;
import client.entities.User;
import client.entities.UserNotExistException;

@Service
public class RoomService implements RoomServiceInterface {
	private Set<Room> rooms = new HashSet<Room>();

	@Override
	public void userJoinRoom(User user, String roomName) throws RoomNotFoundException, GivenObjectNotValidException {
		if (!user.isCorrect()) {
			throw new GivenObjectNotValidException("Der �bergebene User ist ung�ltig");
		}
		for (Room room : rooms) {
			if (room.getRoomName().equals(roomName)) {
				room.addUser(user);
				return;
			}
		}
		throw new RoomNotFoundException("Raum mit dem Raumnamen: " + roomName + " wurde nicht gefunden");

	}

	@Override
	public void userLeaveRoom(String roomName, String userName) throws UserNotExistException, RoomNotFoundException {
		for (Room room : rooms) {
			if (room.getRoomName().equals(roomName)) {
				room.removeUser(userName);
				return;
			}
		}
		throw new RoomNotFoundException("Raum mit dem Raumnamen: " + roomName + " wurde nicht gefunden");

	}

	@Override
	public Room createRoom(String roomName) throws GivenObjectNotValidException {

		if (roomName != null && roomName.matches(GlobalVariables.NAME_REGEX)) {
			Room room = new Room(roomName);
			rooms.add(room);
			return room;
		}
		throw new GivenObjectNotValidException("der �bergebene Raumname ist ung�ltig");

	}

	@Override
	public void deleteRoom(String roomName) throws GivenObjectNotValidException, RoomNotFoundException {
		if (roomName == null || !roomName.matches(GlobalVariables.NAME_REGEX)) {
			throw new GivenObjectNotValidException("Der �bergebene Raumname ist ung�ltig");
		}
		for (Room room : rooms) {
			if (room.getRoomName().equals(roomName)) {
				rooms.remove(room);
				return;
			}
		}
		throw new RoomNotFoundException("Der Raum wurde nicht gefunden");

	}

	@Override
	public Set<Room> getRooms() {
		return rooms;
	}

	@Override
	public Room getRoom(String roomName) throws RoomNotFoundException, NameNotValidException {
		if (roomName == null || !roomName.matches(GlobalVariables.NAME_REGEX)) {
			throw new NameNotValidException("Der übergebene Raumname ist ungültig");
		}
		for (Room room : rooms) {
			if (room.getRoomName().equals(roomName)) {
				return room;
			}
		}
		throw new RoomNotFoundException("Der Raum wurde nicht gefunden");
	}

}