package client.roomManagerComponent;

import client.entities.GivenObjectNotValidException;
import client.entities.RoomNotFoundException;
import client.entities.User;
import client.entities.UserNotExistException;

/**
 * Created by Ahmad on 30.05.2017.
 */
public interface RoomServiceFacadeInterface {

    public void userJoinRoom(User user, String roomName) throws RoomNotFoundException, GivenObjectNotValidException;

    public void userLeaveRoom(String roomName, String userName) throws UserNotExistException, RoomNotFoundException;
}
