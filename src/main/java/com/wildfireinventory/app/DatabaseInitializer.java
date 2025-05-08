package com.wildfireinventory.app;

import com.wildfireinventory.dao.ItemDAO;
import com.wildfireinventory.dao.RoomDAO;
import com.wildfireinventory.dao.UserDAO;
import com.wildfireinventory.model.Item;
import com.wildfireinventory.model.Room;
import com.wildfireinventory.model.User;
import com.wildfireinventory.util.DatabaseConnection;

import java.sql.Date;
import java.sql.SQLException;
import java.math.BigDecimal;

public class DatabaseInitializer {
    public static void main(String[] args) {
        try {
            // Initialize DAOs
            UserDAO userDAO = new UserDAO();
            RoomDAO roomDAO = new RoomDAO();
            ItemDAO itemDAO = new ItemDAO();

            // Create sample users
            User user1 = new User();
            user1.setFirstName("John");
            user1.setLastName("Doe");
            user1.setEmail("john.doe@example.com");
            user1.setPasswordHash("hashed_password1");
            user1.setPhoneNumber("555-1234");
            user1.setAddress("123 Main St");
            User createdUser1 = userDAO.createUser(user1);
            int userId1 = createdUser1.getUserId();

            User user2 = new User();
            user2.setFirstName("Jane");
            user2.setLastName("Smith");
            user2.setEmail("jane.smith@example.com");
            user2.setPasswordHash("hashed_password2");
            user2.setPhoneNumber("555-5678");
            user2.setAddress("456 Oak Ave");
            User createdUser2 = userDAO.createUser(user2);
            int userId2 = createdUser2.getUserId();

            // Create rooms for user1
            Room livingRoom = new Room();
            livingRoom.setUserId(userId1);
            livingRoom.setRoomName("Living Room");
            livingRoom.setRoomDescription("Main living area with entertainment system");
            roomDAO.createRoom(livingRoom);

            Room kitchen = new Room();
            kitchen.setUserId(userId1);
            kitchen.setRoomName("Kitchen");
            kitchen.setRoomDescription("Modern kitchen with appliances");
            roomDAO.createRoom(kitchen);

            // Create rooms for user2
            Room masterBedroom = new Room();
            masterBedroom.setUserId(userId2);
            masterBedroom.setRoomName("Master Bedroom");
            masterBedroom.setRoomDescription("Primary bedroom with king bed");
            roomDAO.createRoom(masterBedroom);

            Room guestBedroom = new Room();
            guestBedroom.setUserId(userId2);
            guestBedroom.setRoomName("Guest Bedroom");
            guestBedroom.setRoomDescription("Guest room with queen bed");
            roomDAO.createRoom(guestBedroom);

            // Get room IDs for user1
            int livingRoomId = roomDAO.getRoomIdByNameAndUserId("Living Room", userId1);
            int kitchenId = roomDAO.getRoomIdByNameAndUserId("Kitchen", userId1);

            // Get room IDs for user2
            int masterBedroomId = roomDAO.getRoomIdByNameAndUserId("Master Bedroom", userId2);
            int guestBedroomId = roomDAO.getRoomIdByNameAndUserId("Guest Bedroom", userId2);

            // Create items for living room
            Item tv = new Item();
            tv.setRoomId(livingRoomId);
            tv.setItemName("TV");
            tv.setBrand("Samsung");
            tv.setModel("QLED 65");
            tv.setSerialNumber("SN123456");
            tv.setPurchaseDate(Date.valueOf("2023-01-15"));
            tv.setPurchasePrice(new BigDecimal("1299.99"));
            tv.setCurrentValue(new BigDecimal("1100.00"));
            tv.setCondition("Excellent");
            tv.setDescription("65-inch 4K Smart TV");
            itemDAO.createItem(tv);

            Item sofa = new Item();
            sofa.setRoomId(livingRoomId);
            sofa.setItemName("Sofa");
            sofa.setBrand("Ikea");
            sofa.setModel("Ektorp");
            sofa.setSerialNumber("SOFA123");
            sofa.setPurchaseDate(Date.valueOf("2022-03-10"));
            sofa.setPurchasePrice(new BigDecimal("899.99"));
            sofa.setCurrentValue(new BigDecimal("750.00"));
            sofa.setCondition("Good");
            sofa.setDescription("3-seater leather sofa");
            itemDAO.createItem(sofa);

            // Create items for kitchen
            Item fridge = new Item();
            fridge.setRoomId(kitchenId);
            fridge.setItemName("Refrigerator");
            fridge.setBrand("LG");
            fridge.setModel("LSX26561SS");
            fridge.setSerialNumber("REF001");
            fridge.setPurchaseDate(Date.valueOf("2023-02-20"));
            fridge.setPurchasePrice(new BigDecimal("1499.99"));
            fridge.setCurrentValue(new BigDecimal("1350.00"));
            fridge.setCondition("Excellent");
            fridge.setDescription("36-inch French door refrigerator");
            itemDAO.createItem(fridge);

            // Create items for bedrooms
            Item kingBed = new Item();
            kingBed.setRoomId(masterBedroomId);
            kingBed.setItemName("King Bed");
            kingBed.setBrand("Ashley");
            kingBed.setModel("Cherrywood");
            kingBed.setSerialNumber("BED789");
            kingBed.setPurchaseDate(Date.valueOf("2022-05-15"));
            kingBed.setPurchasePrice(new BigDecimal("1199.99"));
            kingBed.setCurrentValue(new BigDecimal("1000.00"));
            kingBed.setCondition("Good");
            kingBed.setDescription("King size platform bed");
            itemDAO.createItem(kingBed);

            Item queenBed = new Item();
            queenBed.setRoomId(guestBedroomId);
            queenBed.setItemName("Queen Bed");
            queenBed.setBrand("Wayfair");
            queenBed.setModel("Modern");
            queenBed.setSerialNumber("BED456");
            queenBed.setPurchaseDate(Date.valueOf("2022-06-20"));
            queenBed.setPurchasePrice(new BigDecimal("799.99"));
            queenBed.setCurrentValue(new BigDecimal("650.00"));
            queenBed.setCondition("Good");
            queenBed.setDescription("Queen size platform bed");
            itemDAO.createItem(queenBed);

            System.out.println("Sample data has been successfully inserted into the database!");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
        }
    }
}
