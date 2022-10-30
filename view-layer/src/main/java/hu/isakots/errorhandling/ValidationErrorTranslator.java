package hu.isakots.errorhandling;


import hu.isakots.errorhandling.domain.FieldRejectionModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * this class is meant to transform errors, thrown by Validation API, into readable and client friendly format
 */
@Component
public class ValidationErrorTranslator {

    public List<FieldRejectionModel> translateMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return e.getBindingResult().getAllErrors().stream()
            .map(objectError -> {
                if (objectError instanceof FieldError) {
                    return new FieldRejectionModel(((FieldError) objectError).getField(), objectError.getDefaultMessage());
                }
                return new FieldRejectionModel("Unrecognized field", objectError.getDefaultMessage());
            })
            .collect(Collectors.toList());
    }

}
