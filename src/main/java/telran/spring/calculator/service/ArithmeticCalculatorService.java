package telran.spring.calculator.service;

import org.springframework.stereotype.Service;

import telran.spring.calculator.dto.OperationData;
@Service
public class ArithmeticCalculatorService implements CalculatorService {

	@Override
	public String calculate(OperationData operationData) {
		double[] operands = getOperands(operationData);
		String operation = operationData.operation();
		
		
		return switch(operation) {
		case "add" -> Double.toString(operands[0] + operands[1]);
		case "subtract" -> Double.toString(operands[0] - operands[1]);
		case "multiply" -> Double.toString(operands[0] * operands[1]);
		case "divide" -> Double.toString(operands[0] / operands[1]);
		default -> throw new IllegalArgumentException(operation + " unsupported operation");
		};
	}

	private double[] getOperands(OperationData operationData) {
		try {
			double op1 = Double.parseDouble(operationData.operand1());
			double op2 = Double.parseDouble(operationData.operand2());
			return new double[] {op1, op2};
		} catch (Exception e) {
			throw new IllegalArgumentException("two operands must be the numbers");
		}
	}

	@Override
	public String getCalculationType() {
		
		return "arithmetic";
	}

}
