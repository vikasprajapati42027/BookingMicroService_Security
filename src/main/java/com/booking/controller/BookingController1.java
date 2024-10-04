//package com.booking.controller;
//
//import com.booking.model.Booking;
//import com.booking.service.BookingService;
//import com.booking.service.AvailabilityService;
//import com.booking.dto.BookingResponse;
//import com.booking.dto.AvailabilityUpdate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/bookings1")
//public class BookingController1 {
//
//    @Autowired
//    private BookingService bookingService;
//
//    @Autowired
//    private AvailabilityService availabilityService;
//
//    @PostMapping
//    public ResponseEntity<BookingResponse> createBooking(@RequestBody Booking booking) {
//        try {
//            Booking savedBooking = bookingService.saveBooking(booking);
//
//            // Update availability
//            AvailabilityUpdate availabilityUpdate = new AvailabilityUpdate(
//                    savedBooking.getHotelId(),
//                    savedBooking.getRoomId(),
//                    savedBooking.getCheckInDate(),
//                    savedBooking.getCheckOutDate()
//            );
//            availabilityService.updateRoomAvailability(savedBooking.getHotelId(), booking.getRoomId(), booking.getCheckInDate(),booking.getCheckOutDate());
//
//            BookingResponse response = new BookingResponse(true, "Booking created successfully", savedBooking);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            BookingResponse response = new BookingResponse(false, "Booking creation failed: " + e.getMessage(), null);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Booking>> getAllBookings() {
//        List<Booking> bookings = bookingService.getAllBooking();
//        return new ResponseEntity<>(bookings, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
//        Booking booking = bookingService.getBooking(id);
//        return new ResponseEntity<>(booking, HttpStatus.OK);
//    }
//}
