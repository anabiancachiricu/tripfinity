package com.unibuc.tripfinity.controller;

import com.unibuc.tripfinity.service.GeminiService;
import io.github.cdimascio.dotenv.Dotenv;
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

    Dotenv amadeusEnv = Dotenv.load(); // Loads the .env file
    private String geminiKey = amadeusEnv.get("GEMINI_API_KEY");

    @GetMapping("/ask")
    public ResponseEntity<String> getResponse(@RequestParam String prompt) throws JSONException {
        String response = geminiService.callApi(prompt,geminiKey);
//        String response = "Hello";
        return new ResponseEntity<>(response, HttpStatus.OK);
//        return new ResponseEntity<>(new JSONObject().put("message", response).toString(), HttpStatus.OK);


    }

}
