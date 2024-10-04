package com.booking.service;

import com.booking.handling.ResourceNotFoundException;
import com.booking.model.Room;
import com.booking.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // Create or update a room
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    // Get all rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get room by ID
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    // Get all available rooms
    public List<Room> getAvailableRooms() {
        return roomRepository.findByIsAvailable(true);
    }



    // Delete room by ID
    public void deleteRoomById(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }

}

