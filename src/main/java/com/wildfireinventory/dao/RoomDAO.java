package com.wildfireinventory.dao;

import com.wildfireinventory.model.Room;
import com.wildfireinventory.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomDAO {
    private static final Logger LOGGER = Logger.getLogger(RoomDAO.class.getName());
    private final Connection connection;

    public RoomDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public Room createRoom(Room room) throws SQLException {
        String sql = "INSERT INTO rooms (user_id, room_name, room_description) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, room.getUserId());
            stmt.setString(2, room.getRoomName());
            stmt.setString(3, room.getRoomDescription());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating room failed, no rows affected.");
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                room.setRoomId(generatedKeys.getInt(1));
            }
            return room;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating room", e);
            throw e;
        }
    }

    public Room getRoomById(int roomId) throws SQLException {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return createRoomFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting room by ID", e);
            throw e;
        }
    }

    public List<Room> getRoomsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM rooms WHERE user_id = ?";
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(createRoomFromResultSet(rs));
            }
            return rooms;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting rooms by user ID", e);
            throw e;
        }
    }

    public boolean updateRoom(Room room) throws SQLException {
        String sql = "UPDATE rooms SET room_name = ?, room_description = ? WHERE room_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, room.getRoomName());
            stmt.setString(2, room.getRoomDescription());
            stmt.setInt(3, room.getRoomId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating room", e);
            throw e;
        }
    }

    public int getRoomIdByNameAndUserId(String roomName, int userId) throws SQLException {
        String sql = "SELECT room_id FROM rooms WHERE room_name = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, roomName);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("room_id");
            }
            throw new SQLException("Room not found with name: " + roomName + " and user_id: " + userId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting room ID by name and user ID", e);
            throw e;
        }
    }

    public boolean deleteRoom(int roomId) throws SQLException {
        String sql = "DELETE FROM rooms WHERE room_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting room", e);
            throw e;
        }
    }

    private Room createRoomFromResultSet(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("room_id"));
        room.setUserId(rs.getInt("user_id"));
        room.setRoomName(rs.getString("room_name"));
        room.setRoomDescription(rs.getString("room_description"));
        room.setCreatedAt(rs.getTimestamp("created_at"));
        return room;
    }
}
