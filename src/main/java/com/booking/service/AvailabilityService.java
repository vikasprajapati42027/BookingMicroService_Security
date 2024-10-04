//package com.booking.service;
//
////import com.booking.dto.AvailabilityUpdate;
////import org.springframework.stereotype.Service;
////import org.springframework.web.client.RestTemplate;
////
////@Service
////public class AvailabilityService {
////
////    private final RestTemplate restTemplate;
////    private final String availabilityServiceUrl = "http://localhost:8081/api/availability";
////
////    public AvailabilityService(RestTemplate restTemplate) {
////        this.restTemplate = restTemplate;
////    }
////
////    public void updateAvailability(AvailabilityUpdate update) {
////        restTemplate.postForObject(availabilityServiceUrl, update, String.class);
////    }
////}
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class AvailabilityService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private static final String AVAILABILITY_SERVICE_URL = "http://localhost:8080/api/availability";
//
//    public void updateRoomAvailability(Long hotelId, Long roomId, LocalDate checkIn, LocalDate checkOut) {
//        // Construct JSON payload
//        Map<String, Map<Long, List<Map<String, Integer>>>> payload = new HashMap<>();
//        Map<Long, List<Map<String, Integer>>> roomAvailability = new HashMap<>();
//        List<Map<String, Integer>> availabilityList = new ArrayList<>();
//
//        // Assume initial availability of 100; each booking reduces it by 1 (FIFO ordering preserved)
//        int availability = 100;
//
//        // Loop over the dates from check-in to check-out
//        for (LocalDate date = checkIn; !date.isAfter(checkOut); date = date.plusDays(1)) {
//            Map<String, Integer> availabilityForDate = new HashMap<>();
//            availabilityForDate.put(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), availability);
//            availabilityList.add(availabilityForDate);
//            availability--;  // Decrement availability for the next day
//        }
//
//        // Put room availability for the specific room and hotel
//        roomAvailability.put(roomId, availabilityList);
//        payload.put(String.valueOf(hotelId), roomAvailability);
//
//        // Send the payload to the Availability Microservice
//        try {
//            restTemplate.postForObject(AVAILABILITY_SERVICE_URL, payload, String.class);
//        } catch (Exception e) {
//            // Handle any errors during the call
//            System.out.println("Error updating availability: " + e.getMessage());
//        }
//    }
//}
//
