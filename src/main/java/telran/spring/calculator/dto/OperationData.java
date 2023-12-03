package telran.spring.calculator.dto;

import jakarta.validation.constraints.*;

public record OperationData(@NotEmpty
		@Pattern(regexp = "[a-z]{3,10}", message = "wrong type format") String type, @NotEmpty(message = "operation must not be empty") String operation,
		@NotEmpty(message="operand1 must not be empty") String operand1, String operand2) {

}