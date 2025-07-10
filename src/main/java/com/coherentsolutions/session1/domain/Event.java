package com.coherentsolutions.session1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Event {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private String venue;
    private int capacity;
    private int ticketsSold;
    
    public boolean isAvailable() {
        return ticketsSold < capacity;
    }
    
    public int getAvailableTickets() {
        return capacity - ticketsSold;
    }
    
    public boolean isUpcoming() {
        return dateTime != null && dateTime.isAfter(LocalDateTime.now());
    }
    
    public abstract String getEventType();
}