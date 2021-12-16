package mahat.aviran.user_actions_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BindingResultHandler {

    private final ObjectMapper objectMapper;

    public ResponseEntity<Object> generateErrorResponse(BindingResult bindingResult) {
        List<FieldValidationError> fieldValidationErrors = bindingResult.getAllErrors().stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .map(error -> new FieldValidationError(error.getField(), error.getRejectedValue(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ObjectNode response = objectMapper.createObjectNode()
                .set("errors", objectMapper.convertValue(fieldValidationErrors, ArrayNode.class));
        return ResponseEntity.badRequest().body(response);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private class FieldValidationError {
        private String field;
        private Object rejectedValue;
        private String defaultMessage;
    }
}
