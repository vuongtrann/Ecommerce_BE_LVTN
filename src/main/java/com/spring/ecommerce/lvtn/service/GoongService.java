package com.spring.ecommerce.lvtn.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface GoongService {
    public JsonNode getAddressSuggestions(String query);
}
