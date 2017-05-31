package client.roomManagerComponent;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.springframework.stereotype.Service;

import client.entities.GivenObjectNotValidException;
import client.GlobalConstantsAndValidation;
import client.entities.NameNotValidException;
import client.entities.Room;
import client.entities.RoomNotFoundException;
import client.entities.User;
import client.entities.UserNotExistException;

@Service
public class RoomService implements RoomServiceUserInterface, RoomServiceFacadeInterface {
	private Set<Room> rooms = new HashSet<Room>();

	private Semaphore semaphore = new Semaphore(1);

	@Override
	public void userJoinRoom(User user, String roomName) throws RoomNotFoundException, GivenObjectNotValidException, InterruptedException, NameNotValidException {
		if (user == null) {
			throw new GivenObjectNotValidException("Der �bergebene User ist ungültig");
		}
		if(!GlobalConstantsAndValidation.isValidName(roomName)){
			throw new NameNotValidException("Der Raumname "+roomName+" ist ungültig.");
		}
		boolean foundOne = false;
		semaphore.acquire();
		try {
			for (Room room : rooms) {
				if (room.getRoomName().equals(roomName)) {
					room.addUser(user);
					foundOne = true;
					break;
				}
			}
			if (!foundOne) {
				throw new RoomNotFoundException("Raum mit dem Raumnamen: " + roomName + " wurde nicht gefunden");
			}
		}
		finally {
			{
				semaphore.release();
			}
		}
	}

	@Override
	public void userLeaveRoom(String roomName, String userName) throws UserNotExistException, RoomNotFoundException, InterruptedException, NameNotValidException {
		if(!GlobalConstantsAndValidation.isValidName(userName)){
			throw new NameNotValidException("Der UsernName "+userName+" ist null oder entspricht nicht der Regex "+GlobalConstantsAndValidation.NAME_REGEX+".");
		}

		if(!GlobalConstantsAndValidation.isValidName(roomName)){
			throw new NameNotValidException("Der RoomName "+roomName+" ist null oder entspricht nicht der Regex "+GlobalConstantsAndValidation.NAME_REGEX+".");
		}
		boolean foundOne = false;
		semaphore.acquire();
		try {
			for (Room room : rooms) {
				if (room.getRoomName().equals(roomName)) {
					room.removeUser(userName);
					foundOne = true;
					break;
				}
			}
			if (!foundOne) {
				throw new RoomNotFoundException("Raum mit dem Raumnamen: " + roomName + " wurde nicht gefunden");
			}
		}
		finally {
			{
				semaphore.release();
			}
		}

	}

	@Override
	public Room createRoom(String roomName) throws GivenObjectNotValidException, InterruptedException, NameNotValidException {

		if(!GlobalConstantsAndValidation.isValidName(roomName)){
			throw new NameNotValidException("Der RoomName "+roomName+" ist null oder entspricht nicht der Regex "+GlobalConstantsAndValidation.NAME_REGEX+".");
		}
		boolean roomFound = false;
		semaphore.acquire();
		try {
			for (Room room : rooms) {
				if (room.getRoomName().equals(roomName)) {
					roomFound = true;
					break;
				}
			}
			if (roomFound==true) {
				throw new GivenObjectNotValidException("Raum mit dem Raumnamen: " + roomName + " existiert bereits.");
			}
			Room newRoom = new Room(roomName);
			rooms.add(newRoom);
			return newRoom;
		}
		finally {
			{
				semaphore.release();
			}
		}
	}

	@Override
	public void deleteRoom(String roomName) throws RoomNotFoundException, InterruptedException, NameNotValidException {
		if(!GlobalConstantsAndValidation.isValidName(roomName)){
			throw new NameNotValidException("Der RoomName "+roomName+" ist null oder entspricht nicht der Regex "+GlobalConstantsAndValidation.NAME_REGEX+".");
		}
		Room roomFound = null;
		semaphore.acquire();
		try {
			for (Room room : rooms) {
				if (room.getRoomName().equals(roomName)) {
					roomFound = room;
					break;
				}
			}
			if (roomFound==null) {
				throw new RoomNotFoundException("Raum mit dem Raumnamen: " + roomName + " wurde nicht gefunden");
			}
			rooms.remove(roomFound);
		}
		finally {
			{
				semaphore.release();
			}
		}

	}

	@Override
	public Set<Room> getRooms() throws InterruptedException {
		Set<Room> roomsCopy = new HashSet<Room>();
		semaphore.acquire();
		try{
		for(Room room: rooms){
			roomsCopy.add(room);
		}}
		finally {
			semaphore.release();
		}
		return roomsCopy;


	}

	@Override
	public Room getRoom(String roomName) throws RoomNotFoundException, NameNotValidException, InterruptedException {
		if (!GlobalConstantsAndValidation.isValidName(roomName)) {
			throw new NameNotValidException("Der übergebene Raumname ist ungültig");
		}
		Room roomFound = null;
		semaphore.acquire();
		try {
			for (Room room : rooms) {
				if (room.getRoomName().equals(roomName)) {
					roomFound= room;
					break;
				}
			}
			if(roomFound== null){
				throw new RoomNotFoundException("Der Raum wurde nicht gefunden");
			}
		}
		finally {
			semaphore.release();
		}
		return roomFound;
	}

}