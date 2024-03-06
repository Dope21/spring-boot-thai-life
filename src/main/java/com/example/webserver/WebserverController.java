package com.example.webserver;

import com.example.webserver.model.RequestInsurance;
import com.example.webserver.model.ResponseInsurance;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@Validated
public class WebserverController {

    @PostMapping("/get_insurance")
    public ResponseEntity<?> getInsurance(@Valid @RequestBody RequestInsurance requestInsurance) {

        try {
            
            ResponseInsurance responseInsurance = ResponseInsurance.findInsurance(requestInsurance);
            if (responseInsurance.responseStatus.status != "S") {
                return ResponseEntity.badRequest().body(responseInsurance.responseStatus);
            }
            return ResponseEntity.ok(responseInsurance);

        } catch (Exception e) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorBody);
        }

    }

}