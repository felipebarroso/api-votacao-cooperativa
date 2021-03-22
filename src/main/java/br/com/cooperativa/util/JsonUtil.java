package br.com.cooperativa.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    public static String mapToJson(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception jsonProcessingException) {
            log.error(jsonProcessingException.getMessage());
        }
        return null;
    }

    public static <T> T mapFromJson(String json, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(json, clazz);
        } catch (JsonParseException jsonParseException) {
            log.error(jsonParseException.getMessage());
        } catch (JsonMappingException jsonMappingException) {
            log.error(jsonMappingException.getMessage());
        } catch (Exception iOException) {
            log.error(iOException.getMessage());
        }
        return null;
    }
}