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

	private final String BASEPATH = "/chatbot/client";
	private final String ROOM_RESOURCE = "/rooms/{roomName}";
	private final String ROOM_USER_RESOURCE = "/rooms/{roomName}/users/{userName}";
	private final String NAME_REGEX = "[A-Za-z0-9]+";
	private final int PORT_MIN = 0;
	private final int PORT_MAX = 65535;

	@Autowired
	private ClientService serverService;

	@RequestMapping(value = BASEPATH + ROOM_RESOURCE, method = RequestMethod.PUT)
	public ResponseEntity<?> userJoinRoom(@PathVariable String roomName, @RequestBody User user) {
		if(!user.isValid())
		{
			
		}
	}

	@RequestMapping(value = BASEPATH + USER_RESOURCES, method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		return null;

	}

}
