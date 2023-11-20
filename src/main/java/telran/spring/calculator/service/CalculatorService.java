package telran.spring.calculator.service;

import telran.spring.calculator.dto.OperationData;

public interface CalculatorService {
	String calculate(OperationData operationData);
	String getCalculationType();
	
}