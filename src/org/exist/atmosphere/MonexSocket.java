package org.exist.atmosphere;

import org.atmosphere.config.service.*;
import org.atmosphere.cpr.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@ManagedService(path = "/msg")
public class MonexSocket {
    private final Logger logger = LoggerFactory.getLogger(MonexSocket.class);
    private AtmosphereResourceFactory resourceFactory;
    private String uuid;


    @Ready
    public void onReady(final AtmosphereResource r) {
        if(logger.isDebugEnabled()){
            logger.info("Browser {} connected.", r.uuid());
        }
        this.resourceFactory = AtmosphereResourceFactory.getDefault();
        this.uuid = r.uuid();
        r.getBroadcaster().broadcast("{hallo:'test'}");

    }

    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        if (event.isCancelled()) {
            logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
        } else if (event.isClosedByClient()) {
            logger.info("Browser {} closed the connection", event.getResource().uuid());
        }
    }

    @org.atmosphere.config.service.Message(encoders = {JacksonEncoder.class}, decoders = {JacksonDecoder.class})
    public Message onMessage(Message message) throws IOException {
        logger.info("just send {}", message.getMessage());
        AtmosphereResource resource = resourceFactory.find(this.uuid);
        resource.getBroadcaster().broadcast("{hallo:'test3'}");
        message.setValue("tut");
        return message;
    }
}




