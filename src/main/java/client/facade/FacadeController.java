package client.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import client.clientcomponent.Room;
import client.clientcomponent.RoomNotExistException;
import client.clientcomponent.ClientService;
import client.clientcomponent.User;
import client.clientcomponent.UserNotExistException;

@RestController
public class FacadeController {

	private final String BASEPATH = "/chatbot";
	private final String ROOM_RESOURCES = "/rooms";
	private final String USER_RESOURCES = "/users";
	private final String USER_RESOURCE = "/users/{userName}";
	private final String ROOM_RESOURCE = "/rooms/{roomName}";
	private final String ROOM_USER_RESOURCE = "/rooms/{roomName}/users/{userName}";
	private final String NAME_REGEX = "[A-Za-z0-9]+";
	private final int PORT_MIN = 0;
	private final int PORT_MAX = 65535;

	@Autowired
	private ClientService serverService;

	// TODO
	@RequestMapping(value = BASEPATH + ROOM_RESOURCES, method = RequestMethod.GET)
	public ResponseEntity<?> getRooms() {
		List<String> rooms = serverService.getAllRooms();
		return new ResponseEntity<>(rooms, HttpStatus.ACCEPTED);

	}

	// TODO
	@RequestMapping(value = BASEPATH + USER_RESOURCES, method = RequestMethod.GET)
	public ResponseEntity<?> getUsers() {
		List<User> users = serverService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.ACCEPTED);

	}

	@RequestMapping(value = BASEPATH + USER_RESOURCE, method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable String userName) {
		User user = serverService.getUser(userName);
		if (user == null) {
			return new ResponseEntity<>("Der User mit userName: " + userName + " konnte nicht gefunden werden.",
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = BASEPATH + USER_RESOURCES, method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		if (user.getPort() < PORT_MIN || user.getPort() > PORT_MAX) {
			return new ResponseEntity<>("Die Portnummer ist nicht gültig", HttpStatus.PRECONDITION_FAILED);
		}
		if (!user.getUserName().matches(NAME_REGEX)) {
			return new ResponseEntity<>("Der Username enspricht nicht der Regex: " + NAME_REGEX, HttpStatus.CONFLICT);
		}

		serverService.createUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@RequestMapping(value = BASEPATH + ROOM_RESOURCES, method = RequestMethod.POST)
	public ResponseEntity<?> createRoom(@RequestBody Room room) {
		if (room == null || !room.getRoomName().matches(NAME_REGEX)) {
			return new ResponseEntity<>("Der Raumname entspricht nicht der Regex: " + NAME_REGEX, HttpStatus.CONFLICT);
		}

		serverService.createRoom(room);
		return new ResponseEntity<>("Der Raum wurde angelegt", HttpStatus.CREATED);

	}

	@RequestMapping(value = BASEPATH + ROOM_RESOURCE, method = RequestMethod.PUT)
	public ResponseEntity<?> joinRoom(@PathVariable String roomName, @RequestBody User user) {

		if (!roomName.matches(NAME_REGEX)) {
			return new ResponseEntity<>("Der Raumname entspricht nicht der Regex: " + NAME_REGEX, HttpStatus.CONFLICT);
		}
		try {
			serverService.joinRoom(roomName, user);
		} catch (RoomNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>("Der Raum wurde betreten", HttpStatus.ACCEPTED);

	}

	@RequestMapping(value = BASEPATH + ROOM_USER_RESOURCE, method = RequestMethod.DELETE)
	public ResponseEntity<?> joinRoom(@PathVariable String roomName, @PathVariable String userName) {

		try {
			serverService.leaveRoom(roomName, userName);
		} catch (RoomNotExistException | UserNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Der User " + userName + " hat den Raum verlassen", HttpStatus.ACCEPTED);
	}

}
