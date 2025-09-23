package it.vrad.motivational.telegram.bot.infrastructure.testutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.vrad.motivational.telegram.bot.infrastructure.exception.JsonSerializationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonTestUtility {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            String errorMessage = "Error serializing object of type " + object.getClass();

            log.error(errorMessage, e);
            throw new JsonSerializationException(errorMessage, e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            String errorMessage = "Error deserializing JSON to type " + clazz;

            log.error(errorMessage, e);
            throw new JsonSerializationException(errorMessage, e);
        }
    }


}