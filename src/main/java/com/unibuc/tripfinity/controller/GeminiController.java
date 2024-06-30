package com.unibuc.tripfinity.controller;

import com.unibuc.tripfinity.service.GeminiService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class GeminiController {

    @Autowired
    GeminiService geminiService;

    @Value("${gemini.api.key}")
    private String geminiKey;

    @GetMapping("/ask")
    public ResponseEntity<String> getResponse(@RequestParam String prompt) throws JSONException {
        String response = geminiService.callApi(prompt,geminiKey);
//        String response = "Hello";
        return new ResponseEntity<>(response, HttpStatus.OK);
//        return new ResponseEntity<>(new JSONObject().put("message", response).toString(), HttpStatus.OK);


    }

}
