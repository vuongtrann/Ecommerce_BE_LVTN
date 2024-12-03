package com.spring.ecommerce.lvtn.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ApiResponse;
import com.spring.ecommerce.lvtn.service.GoogleMapService;
import com.spring.ecommerce.lvtn.service.GoongService;
import com.spring.ecommerce.lvtn.utils.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/maps")
@RequiredArgsConstructor
public class MapsController {

    private final GoogleMapService googleMapService;
    private final GoongService goongService;

    @GetMapping("/google/search-address")
    public ResponseEntity<ApiResponse<String>> searchAddress(@RequestParam(value = "address") String address) {
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        googleMapService.searchAddress(address)
                )
        );
    }

    @PostMapping("/goong/suggest")
    public ResponseEntity<ApiResponse<JsonNode>> getAddressSuggestions(@RequestBody String query) {
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        goongService.getAddressSuggestions(query)
                )
        );
    }

}
