package hu.isakots.errorhandling.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
public class ValidationErrorResponse implements Serializable {
    private final String message;
    private final List<FieldRejectionModel> errors;

    public ValidationErrorResponse(String message) {
        this.message = message;
        this.errors = Collections.emptyList();
    }

    public ValidationErrorResponse(List<FieldRejectionModel> errors) {
        this.message = "Request is rejected due to validation errors.";
        this.errors = errors;
    }
}
