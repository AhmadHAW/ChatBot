package client.entities;

import client.GlobalConstantsAndValidation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Room {
	private String roomName;
	private Set<User> users = new HashSet<User>();

	public Room(String roomName, Set<User> users) throws GivenObjectNotValidException {
		super();
		if(!GlobalConstantsAndValidation.isValidName(roomName)){
			throw new GivenObjectNotValidException("Der Raumname "+roomName+" ist ungültig.");
		}
		if(users==null) {
			throw new GivenObjectNotValidException("Die Userliste darf nicht null sein.");
		}
		for(User user: users){
			if(user == null){
				throw new GivenObjectNotValidException("In der Userliste darf sich kein NullUser befunden.");
			}
		}

			this.roomName = roomName;
			this.users = users;

	}

	public Room(String roomName) throws GivenObjectNotValidException {
		super();
		if(!GlobalConstantsAndValidation.isValidName(roomName)){
			throw new GivenObjectNotValidException("Der Raumname "+roomName+" ist ungültig.");
		}

			this.roomName = roomName;
			this.users = new HashSet<User>();

	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) throws GivenObjectNotValidException {
		if(!GlobalConstantsAndValidation.isValidName(roomName)){
			throw new GivenObjectNotValidException("Der Raumname "+roomName+" ist ungültig.");
		}
			this.roomName = roomName;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) throws GivenObjectNotValidException {
		if (users == null){
			throw new GivenObjectNotValidException("Die Userliste darf nicht null sein.");
	}
	this.users = users;}

	public void addUser(User user) throws GivenObjectNotValidException {
		if(user == null){
			throw new GivenObjectNotValidException("Der User darf nicht null sein.");

		}
		if (users.contains(user)) {
			throw new GivenObjectNotValidException("Der User: " + user.getUserName() + "befindet sich bereits im Raum");
		}
		users.add(user);
	}

	public void removeUser(User user) throws UserNotExistException {
		if (users.contains(user))
			users.remove(user);
		throw new UserNotExistException("Der User existiert nicht");

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roomName == null) ? 0 : roomName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (roomName == null) {
			if (other.roomName != null)
				return false;
		} else if (!roomName.equals(other.roomName))
			return false;
		return true;
	}

	public void removeUser(String userName) throws UserNotExistException {
		for (User user : users) {
			if (user.getUserName().equals(userName)) {
				users.remove(user);
				return;
			}
		}
		throw new UserNotExistException("Der User: " + userName + " wurde nicht gefunden.");
	}

}
