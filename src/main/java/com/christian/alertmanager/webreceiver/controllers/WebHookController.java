package com.christian.alertmanager.webreceiver.controllers;

import com.christian.alertmanager.webreceiver.services.WebHookProcessingService;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WebHookController {

    private WebHookProcessingService webHookProcessingService;

    @Autowired
    public WebHookController(WebHookProcessingService webHookProcessingService) {
        this.webHookProcessingService = webHookProcessingService;
    }

    @PostMapping("/alertmanager/receive")
    public ResponseEntity receiver(@RequestBody JsonObject requestBody) {

        try {
            webHookProcessingService.processWebhookEvent(requestBody);
        } catch (Exception e) {
            log.error("Failed processing webhook event: {}", requestBody.toString(), e);
            return new ResponseEntity<>("Error encountered", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return ResponseEntity.ok(HttpStatus.OK);
    }


}
