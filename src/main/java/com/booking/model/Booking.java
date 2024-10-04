package com.booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer occupants;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;


    @Column(name="hotel_id")
    private Long hotelId;


    @Column(name="room_id")
    private Long roomId;
}
