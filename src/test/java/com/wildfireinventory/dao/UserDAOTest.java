package com.wildfireinventory.dao;

import com.wildfireinventory.model.User;
import com.wildfireinventory.util.DatabaseConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UserDAOTest {
    private UserDAO userDAO;
    private User testUser;

    @Before
    public void setUp() {
        userDAO = new UserDAO();
        testUser = new User();
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEmail("test@example.com");
        testUser.setPasswordHash("hashed_password");
    }

    @After
    public void tearDown() throws SQLException {
        if (testUser.getUserId() != 0) {
            userDAO.deleteUser(testUser.getUserId());
        }
        DatabaseConnection.closeConnection();
    }

    @Test
    public void testCreateAndRetrieveUser() throws SQLException {
        // Create user
        User createdUser = userDAO.createUser(testUser);
        assertNotNull(createdUser);
        assertTrue(createdUser.getUserId() > 0);

        // Retrieve user
        User retrievedUser = userDAO.getUserById(createdUser.getUserId());
        assertNotNull(retrievedUser);
        assertEquals(createdUser.getUserId(), retrievedUser.getUserId());
        assertEquals(createdUser.getEmail(), retrievedUser.getEmail());
    }

    @Test
    public void testUpdateUser() throws SQLException {
        // Create user
        User createdUser = userDAO.createUser(testUser);

        // Update user
        createdUser.setFirstName("Updated");
        createdUser.setLastName("Name");
        assertTrue(userDAO.updateUser(createdUser));

        // Verify update
        User updatedUser = userDAO.getUserById(createdUser.getUserId());
        assertEquals("Updated", updatedUser.getFirstName());
        assertEquals("Name", updatedUser.getLastName());
    }

    @Test
    public void testDeleteUser() throws SQLException {
        // Create user
        User createdUser = userDAO.createUser(testUser);

        // Delete user
        assertTrue(userDAO.deleteUser(createdUser.getUserId()));

        // Verify deletion
        User deletedUser = userDAO.getUserById(createdUser.getUserId());
        assertNull(deletedUser);
    }
}
