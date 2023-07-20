package com.orin.maverik.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;


public class RestService {


    public ObjectNode sendRestRequest(String url, @Nullable Object body, HttpMethod httpMethod) {
        ObjectMapper mapper = new ObjectMapper();

        RestTemplate template = new RestTemplate();
        ObjectNode jsonResponse = mapper.createObjectNode();

        ResponseEntity<JsonNode> response = template.exchange(url, httpMethod, new HttpEntity<>(body, null), JsonNode.class);

        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            if (response.getBody().isArray()) {
                ArrayNode arrayNode = (ArrayNode) response.getBody();
                ArrayNode myArray = jsonResponse.putArray("body");
                for (int i = 0; i < arrayNode.size(); i++) {
                    myArray.add(arrayNode.get(i));
                }
            }
            else {
                response.getBody().fields().forEachRemaining(oneField -> jsonResponse.put(oneField.getKey(), oneField.getValue()));
            }
        }

        jsonResponse.put("httpResponseStatus", response.getStatusCode().value());

        return jsonResponse;
    }

}


