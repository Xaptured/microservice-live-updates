package com.esportslan.microservices.live_updates.services;

import com.esportslan.microservices.live_updates.clienthelpers.TheJackFolioDBClientHelper;
import com.esportslan.microservices.live_updates.enums.UpdateCategory;
import com.esportslan.microservices.live_updates.exceptions.EventConsumerException;
import com.esportslan.microservices.live_updates.models.UpdateRequestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveUpdateEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveUpdateEventConsumer.class);

    @Autowired
    private TheJackFolioDBClientHelper theJackFolioDBClientHelper;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "live_updates_queue")
    public void onMessage(UpdateRequestEvent event) {
        try {
            theJackFolioDBClientHelper.saveUpdateRequest(event);
            String destination = switch (event.getCategory()) {
                case SCHEDULE -> "/topic/schedule";
                case RESULT -> "/topic/results";
                case AWARD -> "/topic/awards";
                default -> "/topic/updates";
            };
            messagingTemplate.convertAndSend(destination, event);
        } catch (Exception exception) {
            LOGGER.error("Failed to consume event (eid={}): {}", event.getEventId(), exception.getMessage(), exception);
            if (exception.getCause() != null) {
                LOGGER.error("Root cause: ", exception.getCause());
            }
            throw new EventConsumerException("Got exception while consuming the event with eid:" + event.getEventId(), exception);
        }
    }

    public List<UpdateRequestEvent> fetchLatestUpdates(UpdateCategory updateCategory, int limit) {
        return theJackFolioDBClientHelper.fetchLatestUpdates(updateCategory, limit);
    }
}
