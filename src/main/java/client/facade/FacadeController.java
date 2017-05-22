package client.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import client.entities.Room;
import client.entities.RoomNotFoundException;
import client.entities.GivenObjectNotValidException;
import client.entities.GlobalVariables;
import client.entities.User;
import client.entities.UserNotExistException;
import client.roomManager.RoomService;
import client.roomManager.RoomServiceInterface;

@RestController
public class FacadeController {

	@Autowired
	private RoomServiceInterface roomService;

	@RequestMapping(value = GlobalVariables.BASEPATH + GlobalVariables.ROOM_RESOURCE, method = RequestMethod.PUT)
	public ResponseEntity<?> userJoinRoom(@PathVariable String roomName, @RequestBody User user) {
		if (!user.isValid()) {
			return new ResponseEntity<>(
					new GivenObjectNotValidException("Eines der Felder des Objektes ist nicht gültig ausgefüllt."),
					HttpStatus.CONFLICT);
		} else if (!roomName.matches(GlobalVariables.NAME_REGEX)) {
			return new ResponseEntity<>("Der übergebene roomName: " + roomName + " entspricht nicht der Regex: "
					+ GlobalVariables.NAME_REGEX, HttpStatus.NOT_FOUND);
		}
		try {
			roomService.userJoinRoom(user, roomName);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (RoomNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (GivenObjectNotValidException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = GlobalVariables.BASEPATH + GlobalVariables.ROOM_USER_RESOURCE, method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@PathVariable String roomName, @PathVariable String userName) {
		if (!roomName.matches(GlobalVariables.NAME_REGEX)) {
			return new ResponseEntity<>("Der übergebene roomName: " + roomName + " entspricht nicht der Regex: "
					+ GlobalVariables.NAME_REGEX, HttpStatus.NOT_FOUND);
		} else if (!userName.matches(GlobalVariables.NAME_REGEX)) {
			return new ResponseEntity<>("Der übergebene userName: " + userName + " entspricht nicht der Regex: "
					+ GlobalVariables.NAME_REGEX, HttpStatus.NOT_FOUND);
		}
		try {
			roomService.userLeaveRoom(roomName, userName);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (UserNotExistException | RoomNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

}
