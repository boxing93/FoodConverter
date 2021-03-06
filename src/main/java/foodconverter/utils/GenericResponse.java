package foodconverter.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by Iliya on 26.12.2016 г..
 */
public class GenericResponse {

    private String message;
    private String error;

    public GenericResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public GenericResponse(String message) {
        this.message = message;
    }

    public GenericResponse(final List<FieldError> fieldErrors, final List<ObjectError> globalErrors){
        final ObjectMapper mapper = new ObjectMapper();
        try {
            this.message = mapper.writeValueAsString(fieldErrors);
            this.error = mapper.writeValueAsString(globalErrors);
        } catch (final JsonProcessingException e){
            this.message = "";
            this.error = "";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
