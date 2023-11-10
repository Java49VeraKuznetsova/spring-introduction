package telran.spring;

import jakarta.validation.constraints.*;

public record Person(long id, @Pattern(regexp = "[A-Z][a-z]{2,}") String name,
		@NotEmpty String city, 
		//@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$") String email,
		@Email String email,
		@Pattern (regexp = "9725\\d{8}") String phone) {

}