package telran.spring.calculator.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.exceptions.NotFoundException;
import telran.spring.calculator.dto.OperationData;
import telran.spring.calculator.service.CalculatorService;

@RestController
@RequestMapping("calculator")
@RequiredArgsConstructor

public class CalculatorController {
	final List<CalculatorService> calculatorServices;
	Map<String, CalculatorService> servicesMap;
	@PostConstruct
	void createServicesMap() {
		servicesMap = calculatorServices.stream()
				.collect(Collectors.toMap(service -> service.getCalculationType(), service -> service));
	}
	@PostMapping
	String calculate(@RequestBody @Valid OperationData operationData) {
		String type = operationData.type();
		CalculatorService calculatorService = servicesMap.get(type);
		if (calculatorService == null) {
			throw new NotFoundException(String.format("type %s not found",
					type));
		}
		return calculatorService.calculate(operationData);
	}
	

}
