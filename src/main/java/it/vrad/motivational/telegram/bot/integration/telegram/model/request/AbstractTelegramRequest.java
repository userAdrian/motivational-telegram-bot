package it.vrad.motivational.telegram.bot.integration.telegram.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class AbstractTelegramRequest {

    public MultiValueMap<String, Object> asMultiValueMap() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            if(Modifier.isStatic(field.getModifiers())) continue;

            boolean accessible = field.canAccess(this);
            if(!accessible) field.setAccessible(true);

            try {
                Object value = field.get(this);
                if (value != null) {
                    JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                    String key = annotation != null ? annotation.value() : field.getName();
                    map.add(key, getValue(key, value));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field: " + field.getName(), e);
            } finally {
                if(!accessible) field.setAccessible(false);
            }
        }
        return map;
    }

    protected Object getValue(String key, Object value){
        if(value instanceof File file){
            return new FileSystemResource(file);
        }

        return value;
    }
}
