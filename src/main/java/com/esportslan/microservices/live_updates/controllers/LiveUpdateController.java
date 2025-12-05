package com.esportslan.microservices.live_updates.controllers;

import com.esportslan.microservices.live_updates.enums.UpdateCategory;
import com.esportslan.microservices.live_updates.models.UpdateRequestEvent;
import com.esportslan.microservices.live_updates.services.LiveUpdateEventConsumer;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "LiveUpdates", description = "Event management APIs for live updates")
@RestController
@RequestMapping("/live-updates")
public class LiveUpdateController {

    @Autowired
    private LiveUpdateEventConsumer consumer;

    @Operation(
            summary = "Fetch inactive events for admin",
            description = "Fetch inactive events for admin"
    )
    @Retry(name = "live-updates-retry")
    @GetMapping("/")
    public ResponseEntity<List<UpdateRequestEvent>> getLatest(@RequestParam(name = "category") UpdateCategory category,
                                                             @RequestParam(name = "limit", defaultValue = "7") int limit) {
        List<UpdateRequestEvent> updateRequestEvents = consumer.fetchLatestUpdates(category, limit);
        return ResponseEntity.status(HttpStatus.OK).body(updateRequestEvents);
    }
}
