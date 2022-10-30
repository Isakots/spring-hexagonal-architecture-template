package hu.isakots.errorhandling.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FieldRejectionModel implements Serializable {
    private String fieldName;
    private String errorMessage;
}
