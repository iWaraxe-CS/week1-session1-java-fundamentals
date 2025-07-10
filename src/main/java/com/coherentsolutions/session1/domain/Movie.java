package com.coherentsolutions.session1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Movie extends Event {
    private String director;
    private List<String> actors;
    private List<String> genres;
    private Duration duration;
    private LocalDate releaseDate;
    private String mpaaRating;
    private Double imdbRating;
    private String language;
    private String country;
    
    @Override
    public String getEventType() {
        return "Movie";
    }
    
    public boolean isNewRelease() {
        return releaseDate != null && 
               releaseDate.isAfter(LocalDate.now().minusMonths(3));
    }
    
    public boolean isHighRated() {
        return imdbRating != null && imdbRating >= 7.0;
    }
    
    public String getGenresAsString() {
        return genres != null ? String.join(", ", genres) : "Unknown";
    }
    
    public String getMainActor() {
        return actors != null && !actors.isEmpty() ? actors.get(0) : "Unknown";
    }
    
    public String getDurationFormatted() {
        if (duration == null) {
            return "Unknown";
        }
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return String.format("%dh %02dm", hours, minutes);
    }
}