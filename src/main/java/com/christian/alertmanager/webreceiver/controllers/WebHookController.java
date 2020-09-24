package com.christian.alertmanager.webreceiver.controllers;

import com.christian.alertmanager.webreceiver.services.WebHookProcessingService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@Slf4j
public class WebHookController {

    private WebHookProcessingService webHookProcessingService;
    private Gson gson = new Gson();

    @Autowired
    public WebHookController(WebHookProcessingService webHookProcessingService) {
        this.webHookProcessingService = webHookProcessingService;
    }

    @PostMapping("/alertmanager/receive")
    public ResponseEntity receiver(@RequestBody String requestBody) {

        JsonObject json = gson.fromJson(requestBody,JsonObject.class);
        log.debug("Received event from alertmanager. Event is: {}", json.toString());
        json.addProperty("WebHookReceiveTime", ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT));

        try {
            webHookProcessingService.processWebhookEvent(json);
        } catch (Exception e) {
            log.error("Failed processing webhook event: {}", requestBody.toString(), e);
            return new ResponseEntity<>("Error encountered", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return ResponseEntity.ok(HttpStatus.OK);
    }


}
