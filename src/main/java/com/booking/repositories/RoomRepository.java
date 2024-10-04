package com.booking.repositories;

import com.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>
{
    List<Room> findByIsAvailable(Boolean isAvailable);
}
