package com.orin.maverik.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MapperUtil {


    public static final ObjectMapper mapper = JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).build();


    public static <T> T convertToObject(ObjectNode objectNode, Class<T> valueType) {

        try {
            String json = objectNode != null ? mapper.writeValueAsString(objectNode) : null;
            return json != null ? mapper.readValue(json, valueType) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
