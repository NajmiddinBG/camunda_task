package uz.sqb.camunda_sqb.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author Botirov Najmiddin,
 * Date: Fri 12:27 PM. 5/3/24
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GeneralUtils {
    private static final ObjectMapper OBJECT_MAPPER;



    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static String toJson(Object o) {
        if (o == null) return "";
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Exception occurred while parsing to json object : {} ; Cause : {}", o, e.getMessage());
            throw new RuntimeException(MessageFormat.format("Exception occurred while parsing to json object : {0} ; Cause : {1} ;", o, e.getMessage()));
        }
    }

    public static String objectToJsonString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Error while converting object as json string; {}", e.getMessage());
            return null;
        }
    }

}
