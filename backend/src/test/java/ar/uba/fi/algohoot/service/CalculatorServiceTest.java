package ar.uba.fi.algohoot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Calculator Service Tests")
class CalculatorServiceTest {
    @InjectMocks
    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    @DisplayName("Test suma de números positivos")
    void testAdd_PositiveNumbers_ShouldReturnCorrectSum() {
        int a = 5;
        int b = 3;

        int result = calculatorService.add(a, b);

        assertEquals(8, result, "5 + 3 debería ser 8");
    }

    @Test
    @DisplayName("Test resta de números")
    void testSubtract_ShouldReturnCorrectDifference() {
        assertEquals(2, calculatorService.subtract(5, 3));
        assertEquals(-2, calculatorService.subtract(3, 5));
        assertEquals(0, calculatorService.subtract(5, 5));
    }

    @Test
    @DisplayName("Test multiplicación")
    void testMultiply_ShouldReturnCorrectProduct() {
        assertEquals(15, calculatorService.multiply(5, 3));
        assertEquals(0, calculatorService.multiply(5, 0));
        assertEquals(-15, calculatorService.multiply(5, -3));
    }

    @Test
    @DisplayName("Test división exitosa")
    void testDivide_ValidDivision_ShouldReturnCorrectResult() {
        double result = calculatorService.divide(10, 2);
        assertEquals(5.0, result, 0.001);
    }

    @Test
    @DisplayName("Test división por cero - debería lanzar excepción")
    void testDivide_ByZero_ShouldThrowException() {
        int a = 10;
        int b = 0;

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculatorService.divide(a, b)
        );
        
        assertEquals("No se puede dividir por cero", exception.getMessage());
    }

    @Test
    @DisplayName("Test número par")
    void testIsEven_EvenNumber_ShouldReturnTrue() {
        assertTrue(calculatorService.isEven(4));
        assertTrue(calculatorService.isEven(0));
        assertTrue(calculatorService.isEven(-2));
    }

    @Test
    @DisplayName("Test número impar")
    void testIsEven_OddNumber_ShouldReturnFalse() {
        assertFalse(calculatorService.isEven(3));
        assertFalse(calculatorService.isEven(1));
        assertFalse(calculatorService.isEven(-1));
    }
}
