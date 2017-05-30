package tests.java.chatClient.applicationcore.entities;

import client.entities.Room;
import client.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Ahmad on 30.05.2017.
 */
class RoomTest {

    private Room room1;
    private Room room2;
    private Room room3;
    private String roomName1;
    private String roomName2;
    private Set<User> users1;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        users1 = new HashSet<User>();
        room1 = new Room(roomName1);
    }

    @Test
    void getRoomName() {
    }

    @Test
    void setRoomName() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void setUsers() {
    }

    @Test
    void addUser() {
    }

    @Test
    void removeUser() {
    }

    @Test
    void hashCode() {
    }

    @Test
    void equals() {
    }

    @Test
    void removeUser1() {
    }

}