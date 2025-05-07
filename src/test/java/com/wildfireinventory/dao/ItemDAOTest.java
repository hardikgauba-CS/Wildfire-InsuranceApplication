package com.wildfireinventory.dao;

import com.wildfireinventory.model.Item;
import com.wildfireinventory.model.Room;
import com.wildfireinventory.model.User;
import com.wildfireinventory.util.DatabaseConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ItemDAOTest {
    private ItemDAO itemDAO;
    private RoomDAO roomDAO;
    private UserDAO userDAO;
    private Item testItem;
    private Room testRoom;
    private User testUser;

    @Before
    public void setUp() throws SQLException {
        itemDAO = new ItemDAO();
        roomDAO = new RoomDAO();
        userDAO = new UserDAO();

        // Create a test user
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
        testRoom = roomDAO.createRoom(testRoom);

        // Create a test item
        testItem = new Item();
        testItem.setRoomId(testRoom.getRoomId());
        testItem.setItemName("Test Item");
        testItem.setBrand("Test Brand");
        testItem.setModel("Test Model");
        testItem.setSerialNumber("123456789");
        testItem.setPurchaseDate(Date.valueOf("2024-01-01"));
        testItem.setPurchasePrice(new BigDecimal("100.00"));
        testItem.setCurrentValue(new BigDecimal("90.00"));
        testItem.setCondition("Excellent");
        testItem.setDescription("A test item for testing");
    }

    @After
    public void tearDown() throws SQLException {
        if (testItem.getItemId() != 0) {
            itemDAO.deleteItem(testItem.getItemId());
        }
        if (testRoom.getRoomId() != 0) {
            roomDAO.deleteRoom(testRoom.getRoomId());
        }
        if (testUser.getUserId() != 0) {
            userDAO.deleteUser(testUser.getUserId());
        }
        DatabaseConnection.closeConnection();
    }

    @Test
    public void testCreateAndRetrieveItem() throws SQLException {
        // Create item
        Item createdItem = itemDAO.createItem(testItem);
        assertNotNull(createdItem);
        assertTrue(createdItem.getItemId() > 0);

        // Retrieve item
        Item retrievedItem = itemDAO.getItemById(createdItem.getItemId());
        assertNotNull(retrievedItem);
        assertEquals(createdItem.getItemId(), retrievedItem.getItemId());
        assertEquals(createdItem.getItemName(), retrievedItem.getItemName());
    }

    @Test
    public void testGetItemsByRoomId() throws SQLException {
        // Create item
        Item createdItem = itemDAO.createItem(testItem);

        // Get items for room
        List<Item> items = itemDAO.getItemsByRoomId(testRoom.getRoomId());
        assertNotNull(items);
        assertFalse(items.isEmpty());
        assertTrue(items.stream()
                .anyMatch(i -> i.getItemId() == createdItem.getItemId()));
    }

    @Test
    public void testSearchItems() throws SQLException {
        // Create item
        Item createdItem = itemDAO.createItem(testItem);

        // Search for item
        List<Item> searchResults = itemDAO.searchItems("Test");
        assertNotNull(searchResults);
        assertFalse(searchResults.isEmpty());
        assertTrue(searchResults.stream()
                .anyMatch(i -> i.getItemId() == createdItem.getItemId()));
    }

    @Test
    public void testUpdateItem() throws SQLException {
        // Create item
        Item createdItem = itemDAO.createItem(testItem);

        // Update item
        createdItem.setItemName("Updated Item");
        createdItem.setBrand("Updated Brand");
        createdItem.setCurrentValue(new BigDecimal("150.00"));
        assertTrue(itemDAO.updateItem(createdItem));

        // Verify update
        Item updatedItem = itemDAO.getItemById(createdItem.getItemId());
        assertEquals("Updated Item", updatedItem.getItemName());
        assertEquals("Updated Brand", updatedItem.getBrand());
        assertEquals(new BigDecimal("150.00"), updatedItem.getCurrentValue());
    }

    @Test
    public void testDeleteItem() throws SQLException {
        // Create item
        Item createdItem = itemDAO.createItem(testItem);

        // Delete item
        assertTrue(itemDAO.deleteItem(createdItem.getItemId()));

        // Verify deletion
        Item deletedItem = itemDAO.getItemById(createdItem.getItemId());
        assertNull(deletedItem);
    }
}
