package com.esportslan.microservices.live_updates.clients;

import com.esportslan.microservices.live_updates.enums.UpdateCategory;
import com.esportslan.microservices.live_updates.models.UpdateRequestEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "DATABASE-SERVICE")
public interface TheJackFolioDBClient {

    @PostMapping("/events-lan/save-live-update")
    public ResponseEntity<Void> saveUpdateRequest(@RequestBody UpdateRequestEvent updateRequestEvent);

    @GetMapping("/events-lan/live-updates")
    public ResponseEntity<List<UpdateRequestEvent>> fetchLatestUpdates(@RequestParam(name = "category") UpdateCategory category,
                                                                  @RequestParam(name = "limit") int limit);
}
