package com.wildfireinventory.model;

import java.sql.Timestamp;

public class Room {
    private int roomId;
    private int userId;
    private String roomName;
    private String roomDescription;
    private Timestamp createdAt;

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getRoomDescription() { return roomDescription; }
    public void setRoomDescription(String roomDescription) { this.roomDescription = roomDescription; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
