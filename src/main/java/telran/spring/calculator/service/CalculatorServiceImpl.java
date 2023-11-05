package telran.spring.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorServiceImpl implements CalculatorService {

	@Override
	public double multiply(double op1, double op2) {
		
		return op1 * op2;
	}

	@Override
	public double sum(double op1, double op2) {
		
		return op1 + op2;
	}

	@Override
	public double substract(double op1, double op2) {
		return op1-op2;
	}

	@Override
	public double divide(double op1, double op2) {
		
		return op1 / op2;
	}

}
