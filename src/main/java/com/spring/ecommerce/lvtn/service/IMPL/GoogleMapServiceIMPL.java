package com.spring.ecommerce.lvtn.service.IMPL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.lvtn.service.GoogleMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GoogleMapServiceIMPL implements GoogleMapService {

    @Value("${google.api.key}")
    private String apiKey;

    private final String addressUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=";

    public String searchAddress(String address) {
       String url = addressUrl + address.replace(" ","%20") + "&key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        String response =  restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode results = root.get("results");

            // Kiểm tra nếu không có kết quả
            if (results == null || results.isArray() && results.size() == 0) {
                return "{\"error\": \"No results found\"}";
            }

            // Tiến hành xử lý dữ liệu khi có kết quả
            JsonNode location = results.get(0).path("geometry").path("location");
            JsonNode formattedAddress = results.get(0).path("formatted_address");

            // Lấy thành phần địa lý
            JsonNode components = results.get(0).path("address_components");

            String country = "", state = "", city = "";
            for (JsonNode component : components) {
                if (component.path("types").toString().contains("country")) {
                    country = component.path("long_name").asText();
                } else if (component.path("types").toString().contains("administrative_area_level_1")) {
                    state = component.path("long_name").asText();
                } else if (component.path("types").toString().contains("locality")) {
                    city = component.path("long_name").asText();
                }
            }

            // Trả về kết quả
            return mapper.createObjectNode()
                    .put("address", formattedAddress.asText())
                    .put("latitude", location.path("lat").asDouble())
                    .put("longitude", location.path("lng").asDouble())
                    .put("country", country)
                    .put("state", state)
                    .put("city", city)
                    .toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "{}";
    }
}
