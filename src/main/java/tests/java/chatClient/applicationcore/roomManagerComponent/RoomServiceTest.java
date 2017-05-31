package tests.java.chatClient.applicationcore.roomManagerComponent;

import client.ChatClientApp;
import client.entities.*;
import client.roomManagerComponent.RoomService;
import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Ahmad on 31.05.2017.
 */


public class RoomServiceTest {
    RoomService roomService;
    private Room room1;
    private Room room2;
    private Room room3;
    private final String roomName1 = "roomName1";
    private final String roomName2= "roomName2";
    private Set<Room> rooms;
    private User user1;
    private final String userName1 = "user1";
    private final int port1 = 1;
    private final String ipAdresse1 = "203.0.113.195";
    private final String userName2 = "user2";
    private final int port2 = 2;
    private final String ipAdresse2 = "211.1.131.193";
    @Before
    public void setUp() throws Exception {
        roomService = new RoomService();
        user1 = new User(userName1, port1, ipAdresse1);
        rooms = new HashSet<Room>();
        room1 = new Room(roomName1);
        room2 = new Room(roomName2);

    }
    @Test
    public void getRoomsPositive() throws InterruptedException{
        rooms = roomService.getRooms();
        assertTrue(rooms.isEmpty());
        rooms.add(room1);
        assertTrue(roomService.getRooms().isEmpty());
        assertFalse(rooms.isEmpty());
    }

    @Test
    public void createRoomPositive() throws InterruptedException, GivenObjectNotValidException, NameNotValidException {
        roomService.createRoom(roomName1);
        assertTrue(roomService.getRooms().contains(room1));
    }

    @Test
    public void createRoomNull() throws InterruptedException, GivenObjectNotValidException, NameNotValidException {
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.createRoom(null);
        });
    }

    @Test
    public void createRoomUnvalidName() throws InterruptedException, GivenObjectNotValidException, NameNotValidException {
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.createRoom("");
        });
    }

    @Test
    public void deleteRoomPositive() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        roomService.createRoom(roomName1);
        roomService.deleteRoom(roomName1);
        assertFalse(roomService.getRooms().contains(room1));
    }

    @Test
    public void deleteRoomNull() throws InterruptedException, GivenObjectNotValidException, NameNotValidException {
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.deleteRoom(null);
        });
    }

    @Test
    public void deleteRoomUnvalidName() throws InterruptedException, GivenObjectNotValidException, NameNotValidException {
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.deleteRoom("");
        });
    }

    @Test
    public void deleteRoomNotFound() throws InterruptedException, GivenObjectNotValidException, NameNotValidException {
        Assertions.assertThrows(RoomNotFoundException.class, () -> {
            roomService.deleteRoom("a");
        });
    }

    @Test
    public void getRoomPositive() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
       roomService.createRoom(roomName1);
            assertEquals(roomName1,roomService.getRoom(roomName1).getRoomName());

    }

    @Test
    public void getRoomNullName() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.getRoom(null);
        });

    }

    @Test
    public void getRoomNullUnvalidName() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.getRoom(".");
        });

    }

    @Test
    public void getRoomNotFound() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        Assertions.assertThrows(RoomNotFoundException.class, () -> {
            roomService.getRoom("s");
        });

    }

    @Test
    public void userJoinRoomPositive() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        roomService.createRoom(roomName1);
        roomService.userJoinRoom(user1,roomName1);
        assertTrue(roomService.getRoom(roomName1).getUsers().contains(user1));
    }

    @Test
    public void userJoinRoomUserNull() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        roomService.createRoom(roomName1);
        Assertions.assertThrows(GivenObjectNotValidException.class, () -> {
            roomService.userJoinRoom(null, roomName1);
        });
    }

    @Test
    public void userJoinRoomRoomNameNull() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        roomService.createRoom(roomName1);
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.userJoinRoom(user1, null);
        });
    }

    @Test
    public void userJoinRoomUnvalidName() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        roomService.createRoom(roomName1);
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.userJoinRoom(user1, "");
        });
    }

    @Test
    public void userJoinRoomNotFound() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        Assertions.assertThrows(RoomNotFoundException.class, () -> {
            roomService.userJoinRoom(user1, roomName1);
        });
    }


    @Test
    public void userLeaveRoomPositive() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException, UserNotExistException {
        roomService.createRoom(roomName1);
        roomService.userJoinRoom(user1,roomName1);
        roomService.userLeaveRoom(roomName1,userName1);
        assertFalse(roomService.getRoom(roomName1).getUsers().contains(user1));
    }

    @Test
    public void userLeaveRoomUserNull() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.createRoom(roomName1);
            roomService.userLeaveRoom(roomName1,null);
        });
    }

    @Test
    public void userLeaveRoomUnvalidUserName() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.createRoom(roomName1);
            roomService.userLeaveRoom(roomName1,"");
        });
    }

    @Test
    public void userLeaveRoomRoomNameNull() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.userLeaveRoom(null,userName1);
        });
    }

    @Test
    public void userLeaveRoomUnvalidName() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        Assertions.assertThrows(NameNotValidException.class, () -> {
            roomService.userLeaveRoom("", userName1);
        });
    }

    @Test
    public void userLeaveRoomUserNotFound() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        Assertions.assertThrows(UserNotExistException.class, () -> {
            roomService.createRoom(roomName1);
            roomService.userLeaveRoom(roomName1, userName1);
        });
    }

    @Test
    public void userLeaveRoomRoomNotFound() throws InterruptedException, GivenObjectNotValidException, NameNotValidException, RoomNotFoundException {
        Assertions.assertThrows(RoomNotFoundException.class, () -> {
            roomService.userLeaveRoom(roomName1, userName1);
        });
    }




}