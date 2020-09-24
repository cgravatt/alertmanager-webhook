package com.christian.alertmanager.webreceiver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebHookController {

    @PostMapping("/alertmanager/dumpstring")
    public ResponseEntity<HttpStatus> dumpString(@RequestBody String requestBody) {
        System.out.println(requestBody);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
