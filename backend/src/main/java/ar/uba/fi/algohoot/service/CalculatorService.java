package ar.uba.fi.algohoot.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public int add(int a, int b) {
        return a + b;
    }
    
    public int subtract(int a, int b) {
        return a - b;
    }
    
    public int multiply(int a, int b) {
        return a * b;
    }
    
    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("No se puede dividir por cero");
        }
        return (double) a / b;
    }
    
    public boolean isEven(int number) {
        return number % 2 == 0;
    }
}
