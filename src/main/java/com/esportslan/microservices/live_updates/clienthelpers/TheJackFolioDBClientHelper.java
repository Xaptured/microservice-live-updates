package com.esportslan.microservices.live_updates.clienthelpers;

import com.esportslan.microservices.live_updates.clients.TheJackFolioDBClient;
import com.esportslan.microservices.live_updates.enums.UpdateCategory;
import com.esportslan.microservices.live_updates.exceptions.InternalErrorException;
import com.esportslan.microservices.live_updates.models.UpdateRequestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheJackFolioDBClientHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TheJackFolioDBClientHelper.class);

    @Autowired
    private TheJackFolioDBClient theJackFolioDBClient;

    public void saveUpdateRequest(UpdateRequestEvent event) {
        try {
            LOGGER.info("Calling database client to save update request");
            theJackFolioDBClient.saveUpdateRequest(event);
        } catch (InternalErrorException exception) {
            LOGGER.error("Got exception while saving update request details");
            throw new InternalErrorException("Got exception while saving update request details: " + exception.getMessage(), exception);
        }
    }

    public List<UpdateRequestEvent> fetchLatestUpdates(UpdateCategory updateCategory, int limit) {
        try {
            LOGGER.info("Calling database client to fetch latest updated events");
            return theJackFolioDBClient.fetchLatestUpdates(updateCategory, limit).getBody();
        } catch (InternalErrorException exception) {
            LOGGER.error("Got exception while fetching latest updated events details");
            throw new InternalErrorException("Got exception while fetching latest updated events details: " + exception.getMessage(), exception);
        }
    }
}
