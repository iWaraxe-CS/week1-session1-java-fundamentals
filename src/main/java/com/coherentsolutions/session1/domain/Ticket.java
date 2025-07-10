package com.coherentsolutions.session1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private Long id;
    private String ticketNumber;
    private Event event;
    private User owner;
    private BigDecimal price;
    private TicketStatus status;
    private LocalDateTime purchaseDate;
    private LocalDateTime validUntil;
    private Seat seat;
    
    public enum TicketStatus {
        RESERVED,
        PURCHASED,
        USED,
        CANCELLED,
        REFUNDED
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Seat {
        private String section;
        private int row;
        private int number;
        private SeatType type;
        
        public enum SeatType {
            REGULAR,
            PREMIUM,
            VIP
        }
        
        @Override
        public String toString() {
            return String.format("%s-%d-%d", section, row, number);
        }
    }
    
    public boolean isValid() {
        return status == TicketStatus.PURCHASED && 
               validUntil != null && 
               validUntil.isAfter(LocalDateTime.now());
    }
    
    public boolean canBeRefunded() {
        return status == TicketStatus.PURCHASED && 
               event != null && 
               event.getDateTime().isAfter(LocalDateTime.now().plusHours(24));
    }
}