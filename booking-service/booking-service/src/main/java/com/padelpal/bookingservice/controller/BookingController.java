package com.padelpal.bookingservice.controller;

import com.padelpal.bookingservice.model.Booking;
import com.padelpal.bookingservice.repository.BookingRepository;
import com.padelpal.bookingservice.service.ExternalServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository repo;
    private final ExternalServiceClient client;

    public BookingController(BookingRepository repo, ExternalServiceClient client) {
        this.repo = repo;
        this.client = client;
    }

    // Get all bookings
    @GetMapping
    public List<Booking> getAll() {
        return repo.findAll();
    }

    // NEW — Get bookings for one user
    @GetMapping("/user/{userId}")
    public List<Booking> getByUser(@PathVariable Long userId) {
        return repo.findByUserId(userId);
    }

    // Create a booking
    @PostMapping
    public ResponseEntity<?> create(@RequestParam Long userId,
                                    @RequestParam Long courtId) {

        if (!client.userExists(userId)) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        if (!client.courtExists(courtId)) {
            return ResponseEntity.badRequest().body("Court does not exist");
        }

        Booking booking = new Booking(userId, courtId, LocalDateTime.now());
        repo.save(booking);

        return ResponseEntity.ok(booking);
    }
}
