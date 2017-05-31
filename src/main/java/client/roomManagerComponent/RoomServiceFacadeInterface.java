package client.roomManagerComponent;

import client.entities.*;

/**
 * Created by Ahmad on 30.05.2017.
 */
public interface RoomServiceFacadeInterface {

    public void userJoinRoom(User user, String roomName) throws RoomNotFoundException, GivenObjectNotValidException, InterruptedException;

    public void userLeaveRoom(String roomName, String userName) throws UserNotExistException, RoomNotFoundException, GivenObjectNotValidException, InterruptedException, NameNotValidException;
}
