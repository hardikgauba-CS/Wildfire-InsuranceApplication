package com.wildfireinventory.dao;

import com.wildfireinventory.model.Item;
import com.wildfireinventory.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemDAO {
    private static final Logger LOGGER = Logger.getLogger(ItemDAO.class.getName());
    private final Connection connection;

    public ItemDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public Item createItem(Item item) throws SQLException {
        String sql = "INSERT INTO items (room_id, item_name, brand, model, serial_number, purchase_date, purchase_price, current_value, condition, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, item.getRoomId());
            stmt.setString(2, item.getItemName());
            stmt.setString(3, item.getBrand());
            stmt.setString(4, item.getModel());
            stmt.setString(5, item.getSerialNumber());
            stmt.setDate(6, item.getPurchaseDate());
            stmt.setBigDecimal(7, item.getPurchasePrice());
            stmt.setBigDecimal(8, item.getCurrentValue());
            stmt.setString(9, item.getCondition());
            stmt.setString(10, item.getDescription());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating item failed, no rows affected.");
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                item.setItemId(generatedKeys.getInt(1));
            }
            return item;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating item", e);
            throw e;
        }
    }

    public Item getItemById(int itemId) throws SQLException {
        String sql = "SELECT * FROM items WHERE item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return createItemFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting item by ID", e);
            throw e;
        }
    }

    public List<Item> getItemsByRoomId(int roomId) throws SQLException {
        String sql = "SELECT * FROM items WHERE room_id = ?";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(createItemFromResultSet(rs));
            }
            return items;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting items by room ID", e);
            throw e;
        }
    }

    public List<Item> searchItems(String searchTerm) throws SQLException {
        String sql = "SELECT * FROM items WHERE LOWER(item_name) LIKE ? OR LOWER(brand) LIKE ? OR LOWER(model) LIKE ?";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String pattern = "%" + searchTerm.toLowerCase() + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(createItemFromResultSet(rs));
            }
            return items;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error searching items", e);
            throw e;
        }
    }

    public boolean updateItem(Item item) throws SQLException {
        String sql = "UPDATE items SET item_name = ?, brand = ?, model = ?, serial_number = ?, purchase_date = ?, purchase_price = ?, current_value = ?, condition = ?, description = ? WHERE item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, item.getItemName());
            stmt.setString(2, item.getBrand());
            stmt.setString(3, item.getModel());
            stmt.setString(4, item.getSerialNumber());
            stmt.setDate(5, item.getPurchaseDate());
            stmt.setBigDecimal(6, item.getPurchasePrice());
            stmt.setBigDecimal(7, item.getCurrentValue());
            stmt.setString(8, item.getCondition());
            stmt.setString(9, item.getDescription());
            stmt.setInt(10, item.getItemId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating item", e);
            throw e;
        }
    }

    public boolean deleteItem(int itemId) throws SQLException {
        String sql = "DELETE FROM items WHERE item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting item", e);
            throw e;
        }
    }

    private Item createItemFromResultSet(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setItemId(rs.getInt("item_id"));
        item.setRoomId(rs.getInt("room_id"));
        item.setItemName(rs.getString("item_name"));
        item.setBrand(rs.getString("brand"));
        item.setModel(rs.getString("model"));
        item.setSerialNumber(rs.getString("serial_number"));
        item.setPurchaseDate(rs.getDate("purchase_date"));
        item.setPurchasePrice(rs.getBigDecimal("purchase_price"));
        item.setCurrentValue(rs.getBigDecimal("current_value"));
        item.setCondition(rs.getString("condition"));
        item.setDescription(rs.getString("description"));
        item.setCreatedAt(rs.getTimestamp("created_at"));
        return item;
    }
}
