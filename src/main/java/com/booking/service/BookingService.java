package com.booking.service;

import com.booking.handling.ResourceNotFoundException;
import com.booking.handling.RoomNotAvailableException;
import com.booking.model.Booking;
import org.springframework.http.*;
import com.booking.model.Room;
import com.booking.repositories.BookingRepository;
import com.booking.repositories.HotelRepository;
import com.booking.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public Booking saveBooking(Booking booking) {
        // Check if hotel exists
//        Hotel hotel = hotelRepository.findById(booking.getHotel().getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        // Check if room exists and is available
        Room room = roomRepository.findById(booking.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        if (!room.getIsAvailable()) {
            throw new RoomNotAvailableException("Room is not available");
        }

        // Mark room as unavailable for booking
        room.setIsAvailable(false);
        roomRepository.save(room);

        // Save the booking
        Booking savedBooking = bookingRepository.save(booking);
        // Additional logic after saving (if needed)
        // For example, you could update the hotel's booking count or perform other operations
      //  updateAvailability(savedBooking);
        return savedBooking;
    }


    public Booking getBooking(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    public List<Booking> getAllBooking()
    {
        return bookingRepository.findAll();
    }

    private void updateAvailability(Booking booking) {
        String availabilityServiceUrl = "http://localhost:8080/api/availability/update";

        Map<String, Map<String, List<Map<String, Integer>>>> availabilityPayload = new HashMap<>();
        Map<String, List<Map<String, Integer>>> roomAvailability = new HashMap<>();
        List<Map<String, Integer>> dateAvailability = new ArrayList<>();

        LocalDate startDate = booking.getCheckInDate();
        LocalDate endDate = booking.getCheckOutDate();

        while (!startDate.isAfter(endDate)) {
            Map<String, Integer> dayAvailability = new HashMap<>();
            dayAvailability.put(startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), -1);
            dateAvailability.add(dayAvailability);
            startDate = startDate.plusDays(1);
        }

        roomAvailability.put(String.valueOf(booking.getRoomId()), dateAvailability);
        availabilityPayload.put(String.valueOf(booking.getHotelId()), roomAvailability);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(availabilityServiceUrl, availabilityPayload, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to update availability");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating availability: " + e.getMessage());
        }
    }
}
