package tests.java.chatClient.applicationcore;

import client.entities.GivenObjectNotValidException;
import client.entities.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



/**
 * Created by Ahmad on 29.05.2017.
 */
class MessageTest {
    private Message message1;
    private Message message2;
    private Message message3;
    private final String messageBody1 = "testBody";
    private final String messageBody2 = "testBody2";
    private final String sender1 = "sender1";
    private final String sender2 = "sender2";
    private final String reciefer1 = "reciefer1";
    private final String reciefer2 = "reciefer2";
    private final String roomName1 = "room1";
    private final String roomName2 = "room2";
    private final long timestamp1 = 1;
    private final long timestamp2 = 2;

    @BeforeEach
    void setUp() {
        try {
            message1 = new Message(messageBody1,reciefer1,sender1,roomName1,timestamp1);
            message2 = new Message(messageBody2,reciefer2,sender2,roomName2,timestamp2);
            message3 = new Message(messageBody2,reciefer1,sender1,roomName1,timestamp1);
        } catch (GivenObjectNotValidException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testConstructorNullMessage(){
        Assertions.assertThrows(GivenObjectNotValidException.class, () -> {
            new Message(null,reciefer1,sender1,roomName1,timestamp1);
        });
    }

    @Test()
    public void testConstructorNullReciefer() {
        Assertions.assertThrows(GivenObjectNotValidException.class, () -> {
            new Message(messageBody1,null,sender1,roomName1,timestamp1);
        });

    }

    @Test
    public void testConstructorNullSender(){
        Assertions.assertThrows(GivenObjectNotValidException.class, () -> {
            new Message(messageBody1,reciefer1,null,roomName1,timestamp1);
        });
    }

    @Test
    public void testConstructorNullRoomName(){
        Assertions.assertThrows(GivenObjectNotValidException.class, () -> {
            new Message(messageBody1,reciefer1,sender1,null,timestamp1);
        });
    }

    @Test
    public void testConstructorUnvalidRegexMessage(){
        Assertions.assertThrows(GivenObjectNotValidException.class, () -> {
            new Message(".",reciefer1,sender1,roomName1,timestamp1);
        });
    }

    @Test()
    public void testConstructorUnvalidRegexReciefer() {
        Assertions.assertThrows(GivenObjectNotValidException.class, () -> {
            new Message(messageBody1,",",sender1,roomName1,timestamp1);
        });

    }

    @Test
    public void testConstructorUnvalidRegexSender(){
        Assertions.assertThrows(GivenObjectNotValidException.class, () -> {
            new Message(messageBody1,reciefer1,"",roomName1,timestamp1);
        });
    }

    @Test
    public void testConstructorUnvalidRegexRoomName(){
        Assertions.assertThrows(GivenObjectNotValidException.class, () -> {
            new Message(messageBody1,reciefer1,sender1," ",timestamp1);
        });
    }

    @Test
    public void testConstructorNegativeTimeStamp(){
        Assertions.assertThrows(GivenObjectNotValidException.class, () -> {
            new Message(messageBody1,reciefer1,sender1,roomName1,-1);
        });
    }

    @Test
    public void testGetMessageBody(){
        assertEquals(messageBody1,message1.getMessage());
    }

    @Test
    public void testGetSender(){
        assertEquals(sender1,message1.getSenderUserName());
    }

    @Test
    public void testGetReciefer(){
        assertEquals(reciefer1,message1.getReciefeUserName());
    }

    @Test
    public void testGetTimeStamp(){
        assertEquals(timestamp1,message1.getTimeStamp());
    }
    @Test
    public void testGetRoomName(){
        assertEquals(roomName1,message1.getRaumname());
    }

    @Test
    public void testSetMessageBodyPositive(){
        message1.setMessage(messageBody2);
        assertEquals(messageBody2,message1.getMessage());
    }



}