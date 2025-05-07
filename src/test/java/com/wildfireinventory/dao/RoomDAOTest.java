package com.wildfireinventory.dao;

import com.wildfireinventory.model.Room;
import com.wildfireinventory.model.User;
import com.wildfireinventory.util.DatabaseConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class RoomDAOTest {
    private RoomDAO roomDAO;
    private UserDAO userDAO;
    private Room testRoom;
    private User testUser;

    @Before
    public void setUp() throws SQLException {
        roomDAO = new RoomDAO();
        userDAO = new UserDAO();

        // Create a test user first
        testUser = new User();
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEmail("test@example.com");
        testUser.setPasswordHash("hashed_password");
        testUser = userDAO.createUser(testUser);

        // Create a test room
        testRoom = new Room();
        testRoom.setUserId(testUser.getUserId());
        testRoom.setRoomName("Test Room");
        testRoom.setRoomDescription("A test room for testing");
    }

    @After
    public void tearDown() throws SQLException {
        if (testRoom.getRoomId() != 0) {
            roomDAO.deleteRoom(testRoom.getRoomId());
        }
        if (testUser.getUserId() != 0) {
            userDAO.deleteUser(testUser.getUserId());
        }
        DatabaseConnection.closeConnection();
    }

    @Test
    public void testCreateAndRetrieveRoom() throws SQLException {
        // Create room
        Room createdRoom = roomDAO.createRoom(testRoom);
        assertNotNull(createdRoom);
        assertTrue(createdRoom.getRoomId() > 0);

        // Retrieve room
        Room retrievedRoom = roomDAO.getRoomById(createdRoom.getRoomId());
        assertNotNull(retrievedRoom);
        assertEquals(createdRoom.getRoomId(), retrievedRoom.getRoomId());
        assertEquals(createdRoom.getRoomName(), retrievedRoom.getRoomName());
    }

    @Test
    public void testGetRoomsByUserId() throws SQLException {
        // Create room
        Room createdRoom = roomDAO.createRoom(testRoom);

        // Get rooms for user
        List<Room> rooms = roomDAO.getRoomsByUserId(testUser.getUserId());
        assertNotNull(rooms);
        assertFalse(rooms.isEmpty());
        assertTrue(rooms.stream()
                .anyMatch(r -> r.getRoomId() == createdRoom.getRoomId()));
    }

    @Test
    public void testUpdateRoom() throws SQLException {
        // Create room
        Room createdRoom = roomDAO.createRoom(testRoom);

        // Update room
        createdRoom.setRoomName("Updated Room");
        createdRoom.setRoomDescription("Updated room description");
        assertTrue(roomDAO.updateRoom(createdRoom));

        // Verify update
        Room updatedRoom = roomDAO.getRoomById(createdRoom.getRoomId());
        assertEquals("Updated Room", updatedRoom.getRoomName());
        assertEquals("Updated room description", updatedRoom.getRoomDescription());
    }

    @Test
    public void testDeleteRoom() throws SQLException {
        // Create room
        Room createdRoom = roomDAO.createRoom(testRoom);

        // Delete room
        assertTrue(roomDAO.deleteRoom(createdRoom.getRoomId()));

        // Verify deletion
        Room deletedRoom = roomDAO.getRoomById(createdRoom.getRoomId());
        assertNull(deletedRoom);
    }
}
