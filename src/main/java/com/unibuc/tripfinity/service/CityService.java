package com.unibuc.tripfinity.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.City;
import com.amadeus.resources.Destination;
import com.amadeus.resources.FlightDestination;
import com.amadeus.resources.Location;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CityService {
    @Value("${amadeus.api.key}")
    private String apiKey;

    @Value("${amadeus.api.secret}")
    private String apiSecret;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> getAirports(String keyword) throws IOException, ResponseException {

        System.out.println("HERE");
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String url = "https://test.api.amadeus.com/v1/reference-data/locations?keyword=" + encodedKeyword + "&subType=AIRPORT";

        HttpGet request = new HttpGet(url);
        request.addHeader("Authorization", "Bearer " + getAccessToken());

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode data = rootNode.path("data");
            System.out.println(data);
            List<String> airports = new ArrayList<>();
            if (data.isArray()) {
                for (JsonNode node : data) {
                    airports.add(node.path("iataCode").asText());
                }
            }
            System.out.println("airports:"+ airports);
            return airports;
        }
    }

    public String getAccessToken() throws IOException {
        String url = "https://test.api.amadeus.com/v1/security/oauth2/token";
        HttpPost post = new HttpPost(url);

        // Set headers
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // Set request body
        StringEntity entity = new StringEntity("grant_type=client_credentials&client_id=" + apiKey + "&client_secret=" + apiSecret);
        post.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(post)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            return rootNode.path("access_token").asText();
        }
    }

}
