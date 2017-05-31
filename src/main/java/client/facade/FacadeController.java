package client.facade;

import client.entities.*;
import client.roomManagerComponent.RoomServiceFacadeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import client.GlobalConstantsAndValidation;

import javax.naming.NameNotFoundException;
import java.net.UnknownHostException;

@RestController
public class FacadeController {

	@Autowired
	private RoomServiceFacadeInterface roomService;

	@RequestMapping(value = GlobalConstantsAndValidation.BASEPATH + GlobalConstantsAndValidation.ROOM_RESOURCE, method = RequestMethod.PUT)
	public ResponseEntity<?> userJoinRoom(@PathVariable String roomName, @RequestBody User user) {
		if (!user.isCorrect()) {
			return new ResponseEntity<>(
					new GivenObjectNotValidException("Eines der Felder des Objektes ist nicht g�ltig ausgef�llt."),
					HttpStatus.CONFLICT);
		} else if (!roomName.matches(GlobalConstantsAndValidation.NAME_REGEX)) {
			return new ResponseEntity<>("Der �bergebene roomName: " + roomName + " entspricht nicht der Regex: "
					+ GlobalConstantsAndValidation.NAME_REGEX, HttpStatus.NOT_FOUND);
		}
		try {
			roomService.userJoinRoom(user, roomName);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (RoomNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NameNotValidException|GivenObjectNotValidException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (InterruptedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = GlobalConstantsAndValidation.BASEPATH + GlobalConstantsAndValidation.ROOM_USER_RESOURCE, method = RequestMethod.POST)
	public ResponseEntity<?> userLeaveRoom(@PathVariable String roomName, @PathVariable String userName) {
		if (!roomName.matches(GlobalConstantsAndValidation.NAME_REGEX)) {
			return new ResponseEntity<>("Der �bergebene roomName: " + roomName + " entspricht nicht der Regex: "
					+ GlobalConstantsAndValidation.NAME_REGEX, HttpStatus.NOT_FOUND);
		} else if (!userName.matches(GlobalConstantsAndValidation.NAME_REGEX)) {
			return new ResponseEntity<>("Der �bergebene userName: " + userName + " entspricht nicht der Regex: "
					+ GlobalConstantsAndValidation.NAME_REGEX, HttpStatus.NOT_FOUND);
		}
		try {
			roomService.userLeaveRoom(roomName, userName);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (NameNotValidException|GivenObjectNotValidException|UserNotExistException | RoomNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		catch (InterruptedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
