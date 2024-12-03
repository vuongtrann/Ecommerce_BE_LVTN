package com.spring.ecommerce.lvtn.service.IMPL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.lvtn.service.GoongService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class GoongServiceImpl implements GoongService {
    @Value("${goong.api.key}")
    private String API_KEY;
    @Value("${goong.api.url}")
    private static String GOONG_URL;

    private static final String ADDRESS_SUGGESTION ="https://rsapi.goong.io/Place/Autocomplete";

    private final RestTemplate restTemplate;

    @Override
    public JsonNode getAddressSuggestions(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(ADDRESS_SUGGESTION)
                .queryParam("input", query)
                .queryParam("api_key", API_KEY)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        String jsonString = restTemplate.getForObject(url, String.class);
        try {
            // Chuyển đổi chuỗi JSON thành JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON from Goong API", e);
        }
    }
}
