package com.christian.alertmanager.webreceiver.services;

import com.christian.alertmanager.webreceiver.outputs.EventOutput;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebHookProcessingService {

    private List<EventOutput> outputs;

    @Autowired
    public WebHookProcessingService(List<EventOutput> outputs) {
        this.outputs = outputs;
    }

    public void processWebhookEvent(JsonObject jsonObject) {

        for (EventOutput e : outputs) {
            e.process(jsonObject);
        }

    }

}
