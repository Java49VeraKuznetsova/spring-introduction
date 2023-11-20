package telran.spring.calculator.dto;

import jakarta.validation.constraints.*;

public record OperationData(@NotEmpty
		@Pattern(regexp = "[a-z]{3,10}", message = "wrong type format") String type, @NotEmpty String operation,
		@NotEmpty String operand1, String operand2) {

}
