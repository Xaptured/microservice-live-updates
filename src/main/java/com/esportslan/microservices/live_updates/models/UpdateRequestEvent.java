package com.esportslan.microservices.live_updates.models;

import com.esportslan.microservices.live_updates.enums.UpdateCategory;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateRequestEvent {
    private String eventId;
    private UpdateCategory category;
    private String tournamentId;
    private String title;
    private String message;
    private Instant createdAt;
}
